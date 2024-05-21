
package acme.features.developer.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
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

		Collection<TrainingModule> tm = this.repository.findTrainingModulesByDeveloperId(developerId);
		Collection<TrainingSession> ts = this.repository.findTrainingSessionByDeveloperId(developerId);

		if (!tm.isEmpty()) {
			totalNumberOfTrainingModulesWithAnUpdateMoment = this.repository.totalNumberOfTrainingModulesWithAnUpdateMoment(developerId);
			averageTimeOfTheTrainingModules = this.repository.averageTimeOfTheTrainingModules(developerId);
			deviationTimeOfTheTrainingModules = this.repository.deviationTimeOfTheTrainingModules(developerId);
			minimumTimeOfTheTrainingModules = this.repository.minimumTimeOfTheTrainingModules(developerId);
			maximumTimeOfTheTrainingModules = this.repository.maximumTimeOfTheTrainingModules(developerId);

			dashboard.setTotalNumberOfTrainingModulesWithAnUpdateMoment(totalNumberOfTrainingModulesWithAnUpdateMoment);
			dashboard.setAverageTimeOfTheTrainingModules(averageTimeOfTheTrainingModules);
			dashboard.setDeviationTimeOfTheTrainingModules(deviationTimeOfTheTrainingModules);
			dashboard.setMinimumTimeOfTheTrainingModules(minimumTimeOfTheTrainingModules);
			dashboard.setMaximumTimeOfTheTrainingModules(maximumTimeOfTheTrainingModules);
		} else {

			dashboard.setTotalNumberOfTrainingModulesWithAnUpdateMoment(0);
			dashboard.setAverageTimeOfTheTrainingModules(Double.NaN);
			dashboard.setDeviationTimeOfTheTrainingModules(Double.NaN);
			dashboard.setMinimumTimeOfTheTrainingModules(Double.NaN);
			dashboard.setMaximumTimeOfTheTrainingModules(Double.NaN);
		}

		if (!ts.isEmpty()) {
			totalNumberOfTrainingSessionsWithALink = this.repository.totalNumberOfTrainingSessionsWithALink(developerId);

			dashboard.setTotalNumberOfTrainingSessionsWithALink(totalNumberOfTrainingSessionsWithALink);
		} else
			dashboard.setTotalNumberOfTrainingSessionsWithALink(0);

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
