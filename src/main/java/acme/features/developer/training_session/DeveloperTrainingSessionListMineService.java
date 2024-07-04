
package acme.features.developer.training_session;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionListMineService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		final int trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		TrainingModule trainingModule = this.repository.findTrainingModuleById(trainingModuleId);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = trainingModule != null && trainingModule.getDeveloper().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		Collection<TrainingSession> trainingSessions;
		int trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		trainingSessions = this.repository.findTrainingSessionsByTrainingModuleId(trainingModuleId);

		super.getBuffer().addData(trainingSessions);
		super.getResponse().addGlobal("trainingModuleId", trainingModuleId);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;
		Collection<TrainingModule> trainingModules = this.repository.findAllTrainingModules();
		SelectChoices trainingModulesChoices = SelectChoices.from(trainingModules, "code", object.getTrainingModule());

		final Dataset dataset = super.unbind(object, "code", "trainingModule", "location", "instructor");
		dataset.put("trainingModule", trainingModulesChoices.getSelected().getLabel());
		dataset.put("trainingModules", trainingModulesChoices);

		if (Boolean.TRUE.equals(object.getDraftMode())) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		int trainingModuleId;

		trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);

		super.getResponse().addGlobal("trainingModuleId", trainingModuleId);

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<TrainingSession> objects) {
		assert objects != null;

		int trainingModuleId;
		TrainingModule trainingModule;
		final boolean showCreate;

		trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		trainingModule = this.repository.findTrainingModuleById(trainingModuleId);
		showCreate = trainingModule.getDraftMode();

		super.getResponse().addGlobal("trainingModuleId", trainingModuleId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}
}
