
package acme.features.developer.training_module;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.training_module.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleListMineService extends AbstractService<Developer, TrainingModule> {

	@Autowired
	protected DeveloperTrainingModuleRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Developer.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<TrainingModule> trainingModules;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		trainingModules = this.repository.findAllTrainingModuleByDeveloper(principal.getActiveRoleId());

		super.getBuffer().addData(trainingModules);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "difficultyLevel", "totalTime");

		if (Boolean.TRUE.equals(object.getDraftMode())) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

}
