
package acme.features.developer.training_module;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.training_module.DifficultyLevel;
import acme.entities.training_module.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleCreateService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Developer.class);

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		TrainingModule tm;
		Developer developerId;
		Principal principal;
		principal = super.getRequest().getPrincipal();
		developerId = this.repository.findDeveloperById(principal.getActiveRoleId());
		tm = new TrainingModule();
		tm.setDeveloper(developerId);
		tm.setDraftMode(true);

		super.getBuffer().addData(tm);
		super.getResponse().addGlobal("developerId", developerId);

	}

	@Override
	public void bind(final TrainingModule tm) {
		assert tm != null;

		int projectId = super.getRequest().getData("project", int.class);
		Project project = this.repository.findProjectById(projectId);

		Principal principal;
		principal = super.getRequest().getPrincipal();
		Developer developer = this.repository.findDeveloperById(principal.getActiveRoleId());

		super.bind(tm, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "project", "developer");

		tm.setProject(project);
		tm.setDeveloper(developer);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			TrainingModule tm;
			tm = this.repository.findTrainingModuleByCode(object.getCode());
			super.state(tm == null, "code", "developer.TrainingModule.form.error.duplicated-code");
		}

		final String CREATION_MOMENT = "creationMoment";
		final String UPDATE_MOMENT = "updateMoment";

		if (!super.getBuffer().getErrors().hasErrors(CREATION_MOMENT) && !super.getBuffer().getErrors().hasErrors(UPDATE_MOMENT)) {
			final boolean startBeforeEnd = MomentHelper.isAfter(object.getUpdateMoment(), object.getCreationMoment());
			super.state(startBeforeEnd, UPDATE_MOMENT, "developer.trainingModule.form.error.end-before-start");
		}
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		Dataset dataset;

		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		SelectChoices choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "draft-mode", "project", "developer");

		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		dataset.put("difficultyLevels", choices);

		super.getResponse().addData(dataset);
	}

}
