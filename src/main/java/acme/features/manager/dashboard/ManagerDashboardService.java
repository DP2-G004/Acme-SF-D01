
package acme.features.manager.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardService extends AbstractService<Manager, ManagerDashboard> {

	@Autowired
	private ManagerDashboardRepository repository;


	@Override
	public void authorise() {
		//		boolean status;
		//		int managerId;
		//		Manager manager;
		//
		//		managerId = super.getRequest().getData("id", int.class);
		//		manager = this.repository.findManagerById(managerId);
		//
		//		status = super.getRequest().getPrincipal().hasRole(manager);
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ManagerDashboard dashboard;
		Manager manager;
		int managerId;
		int id;
		// the information I want to show in the dashboard
		int totalMustPriorityUserStories;
		int totalShouldPriorityUserStories;
		int totalCouldPriorityUserStories;
		int totalWontPriorityUserStories;

		Double averageUserStoryCost;
		Double deviationUserStoryCost;
		double minimumUserStoryCost;
		double maximumUserStoryCost;

		Double averageProjectCost;
		Double deviationProjectCost;
		double minimumProjectCost;
		double maximumProjectCost;
		// getting the manager whose data I want to show

		id = super.getRequest().getData("id", int.class);
		manager = this.repository.findManagerById(id);
		managerId = manager.getId();

		dashboard = new ManagerDashboard();

		totalMustPriorityUserStories = this.repository.numMustPriorityUserStories(managerId);
		totalShouldPriorityUserStories = this.repository.numShouldPriorityUserStories(managerId);
		totalCouldPriorityUserStories = this.repository.numCouldPriorityUserStories(managerId);
		totalWontPriorityUserStories = this.repository.numWontPriorityUserStories(managerId);
		averageUserStoryCost = this.repository.avgUserStoryCost(managerId);
		deviationUserStoryCost = this.repository.deviationUserStoryCostByManager(managerId);
		minimumUserStoryCost = this.repository.minUserStoryCost(managerId);
		maximumUserStoryCost = this.repository.maxUserStoryCost(managerId);
		averageProjectCost = this.repository.averageProjectCost(managerId);
		deviationProjectCost = this.repository.deviationProjectCost(managerId);
		minimumProjectCost = this.repository.minimumProjectCost(managerId);
		maximumProjectCost = this.repository.maximumProjectCost(managerId);
		// Saving the data
		dashboard.setTotalMustPriorityUserStories(totalMustPriorityUserStories);
		dashboard.setTotalShouldPriorityUserStories(totalShouldPriorityUserStories);
		dashboard.setTotalCouldPriorityUserStories(totalCouldPriorityUserStories);
		dashboard.setTotalWontPriorityUserStories(totalWontPriorityUserStories);
		dashboard.setAverageUserStoryCost(averageUserStoryCost);
		dashboard.setDeviationUserStoryCost(deviationUserStoryCost);
		dashboard.setMinimumUserStoryCost(minimumUserStoryCost);
		dashboard.setMaximumUserStoryCost(maximumUserStoryCost);
		dashboard.setAverageProjectCost(averageProjectCost);
		dashboard.setDeviationProjectCost(deviationProjectCost);
		dashboard.setMinimumProjectCost(minimumProjectCost);
		dashboard.setMaximumProjectCost(maximumProjectCost);

		super.getBuffer().addData(dashboard);

	}
	@Override
	public void unbind(final ManagerDashboard object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "total-must-priority-user-stories", "total-should-priority-user-stories", "total-could-priority-user-stories", "total-wont-priority-user-stories", "average-user-story-cost", "deviation-user-story-cost",
			"minimum-user-story-cost", "maximum-user-story-cost", "average-project-cost", "deviation-project-cost", "minimum-project-cost", "maximum-project-cost");
		super.getResponse().addData(dataset);
	}
}
