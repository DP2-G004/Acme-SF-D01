
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
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModulePublishService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		Boolean status;
		int trainingModuleId;
		TrainingModule tm;
		Developer developer;

		trainingModuleId = super.getRequest().getData("id", int.class);
		tm = this.repository.findOneTrainingModuleById(trainingModuleId);
		developer = tm == null ? null : tm.getDeveloper();
		status = tm != null && tm.getDraftMode() && super.getRequest().getPrincipal().hasRole(developer);

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

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final int trainingModuleId = super.getRequest().getData("id", int.class);
			final boolean duplicatedCode = this.repository.findAllTrainingModule().stream().filter(e -> e.getId() != trainingModuleId).anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "developer.trainingModule.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("totalTime")) {
			final boolean duplicatedCode = object.getTotalTime() < 0;

			super.state(!duplicatedCode, "totalTime", "developer.trainingModule.form.error.negative-total-time");
		}

		Collection<TrainingSession> trainingSessions = this.repository.findTrainingSessionsByTrainingModuleId(object.getId());
		super.state(!trainingSessions.isEmpty(), "*", "developer.trainingModule.error.not-enough-training-sessions");

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
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		SelectChoices choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());
		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		Dataset dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "draftMode", "project");

		dataset.put("difficultyLevelOptions", choices);
		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		super.getResponse().addData(dataset);
	}

}
