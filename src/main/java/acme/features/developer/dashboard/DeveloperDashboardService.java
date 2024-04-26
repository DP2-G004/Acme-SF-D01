
package acme.features.developer.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.DeveloperDashboard;
import acme.roles.Developer;

@Service
public class DeveloperDashboardService extends AbstractService<Developer, DeveloperDashboard> {

	@Autowired
	private DeveloperDashboardRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Developer.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		DeveloperDashboard dashboard;
		int developerId;

		int totalNumberOfTrainingModulesWithAnUpdateMoment;

		int totalNumberOfTrainingSessionsWithALink;

		double averageTimeOfTheTrainingModules;
		double deviationTimeOfTheTrainingModules;
		double minimumTimeOfTheTrainingModules;
		double maximumTimeOfTheTrainingModules;

		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		dashboard = new DeveloperDashboard();

		totalNumberOfTrainingModulesWithAnUpdateMoment = this.repository.totalNumberOfTrainingModulesWithAnUpdateMoment(developerId);
		totalNumberOfTrainingSessionsWithALink = this.repository.totalNumberOfTrainingSessionsWithALink(developerId);
		averageTimeOfTheTrainingModules = this.repository.averageTimeOfTheTrainingModules(developerId);
		deviationTimeOfTheTrainingModules = this.repository.deviationTimeOfTheTrainingModules(developerId);
		minimumTimeOfTheTrainingModules = this.repository.minimumTimeOfTheTrainingModules(developerId);
		maximumTimeOfTheTrainingModules = this.repository.maximumTimeOfTheTrainingModules(developerId);

		dashboard.setTotalNumberOfTrainingModulesWithAnUpdateMoment(totalNumberOfTrainingModulesWithAnUpdateMoment);
		dashboard.setTotalNumberOfTrainingSessionsWithALink(totalNumberOfTrainingSessionsWithALink);
		dashboard.setAverageTimeOfTheTrainingModules(averageTimeOfTheTrainingModules);
		dashboard.setDeviationTimeOfTheTrainingModules(deviationTimeOfTheTrainingModules);
		dashboard.setMinimumTimeOfTheTrainingModules(minimumTimeOfTheTrainingModules);
		dashboard.setMaximumTimeOfTheTrainingModules(maximumTimeOfTheTrainingModules);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final DeveloperDashboard object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "totalNumberOfTrainingModulesWithAnUpdateMoment", "totalNumberOfTrainingSessionsWithALink", "averageTimeOfTheTrainingModules", "deviationTimeOfTheTrainingModules", "minimumTimeOfTheTrainingModules",
			"maximumTimeOfTheTrainingModules");
		super.getResponse().addData(dataset);
	}

}
