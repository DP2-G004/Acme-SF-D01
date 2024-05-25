
package acme.features.developer.training_module;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.training_module.DifficultyLevel;
import acme.entities.training_module.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleShowService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	protected DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		TrainingModule trainingModule = this.repository.findOneTrainingModuleById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = trainingModule != null && trainingModule.getDeveloper().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int trainingModuleId;
		trainingModuleId = super.getRequest().getData("id", int.class);

		TrainingModule trainingModule;
		trainingModule = this.repository.findOneTrainingModuleById(trainingModuleId);

		super.getBuffer().addData(trainingModule);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;
		Dataset dataset;

		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		projectsChoices = SelectChoices.from(projects, "code", object.getProject());
		SelectChoices choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "draft-mode", "project");

		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		dataset.put("difficultyLevels", choices);

		super.getResponse().addData(dataset);
	}

}
