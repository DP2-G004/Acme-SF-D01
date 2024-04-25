
package acme.features.developer.training_module;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.training_module.DifficultyLevel;
import acme.entities.training_module.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleUpdateService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		TrainingModule tm;
		Developer developer;

		masterId = super.getRequest().getData("id", int.class);
		tm = this.repository.findOneTrainingModuleById(masterId);
		developer = tm == null ? null : tm.getDeveloper();
		status = tm != null && super.getRequest().getPrincipal().hasRole(developer);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrainingModule object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule tm) {
		assert tm != null;
		super.bind(tm, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "project");
	}

	//NO OLVIDAR RELLENAR LOS MENSAJES DE ERROR
	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule tm;
			Collection<TrainingModule> t;
			boolean b = false;
			int id;
			id = super.getRequest().getData("id", int.class);
			tm = this.repository.findOneTrainingModuleById(id);
			t = this.repository.findAllTrainingModule();
			for (TrainingModule trainingModule : t)
				if (trainingModule.getCode().equals(object.getCode()))
					b = true;
			if (!tm.getCode().equals(object.getCode()) && b)
				super.state(tm == null, "code", "developer.trainingModule.form.error.duplicated");
		}

		final String CREATION_MOMENT = "creationMoment";
		final String UPDATE_MOMENT = "updateMoment";
		if (!super.getBuffer().getErrors().hasErrors(CREATION_MOMENT) && !super.getBuffer().getErrors().hasErrors(UPDATE_MOMENT)) {
			final boolean startBeforeEnd = MomentHelper.isAfter(object.getUpdateMoment(), object.getCreationMoment());
			super.state(startBeforeEnd, UPDATE_MOMENT, "developer.trainingModule.form.error.end-before-start");
		}

		if (!super.getBuffer().getErrors().hasErrors("project")) {
			int id;
			id = super.getRequest().getData("id", int.class);
			TrainingModule tm = this.repository.findOneTrainingModuleById(id);
			super.state(object.getProject().getId() == tm.getProject().getId(), "project", "developer.trainingModule.form.error.null-project");
		}
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		Dataset dataset;

		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		SelectChoices choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "project", "draft-mode");

		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		dataset.put("difficultyLevels", choices);

		super.getResponse().addData(dataset);
	}

}
