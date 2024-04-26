
package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.userstory.Priority;
import acme.entities.userstory.UserStory;
import acme.forms.ManagerDashboard;
import acme.roles.Manager;

@Service
public class ManagerDashboardService extends AbstractService<Manager, ManagerDashboard> {

	@Autowired
	private ManagerDashboardRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Manager.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ManagerDashboard dashboard;
		int managerId;
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

		managerId = super.getRequest().getPrincipal().getActiveRoleId();
		dashboard = new ManagerDashboard();

		Collection<Project> projects = this.repository.findProjectsByManagerId(managerId);
		Collection<UserStory> userStories = this.repository.findUserStoriesByManagerId(managerId);

		if (!projects.isEmpty()) {
			averageProjectCost = this.repository.averageProjectCost(managerId, false);
			deviationProjectCost = this.repository.deviationProjectCost(managerId, false);
			minimumProjectCost = this.repository.minimumProjectCost(managerId, false);
			maximumProjectCost = this.repository.maximumProjectCost(managerId, false);

			dashboard.setAverageProjectCost(averageProjectCost);
			dashboard.setDeviationProjectCost(deviationProjectCost);
			dashboard.setMinimumProjectCost(minimumProjectCost);
			dashboard.setMaximumProjectCost(maximumProjectCost);
		} else {
			dashboard.setAverageProjectCost(Double.NaN);
			dashboard.setDeviationProjectCost(Double.NaN);
			dashboard.setMinimumProjectCost(Double.NaN);
			dashboard.setMaximumProjectCost(Double.NaN);
		}
		if (!userStories.isEmpty()) {
			totalMustPriorityUserStories = this.repository.numUserStoriesByPriority(managerId, Priority.MUST, false);
			totalShouldPriorityUserStories = this.repository.numUserStoriesByPriority(managerId, Priority.SHOULD, false);
			totalCouldPriorityUserStories = this.repository.numUserStoriesByPriority(managerId, Priority.COULD, false);
			totalWontPriorityUserStories = this.repository.numUserStoriesByPriority(managerId, Priority.WONT, false);

			averageUserStoryCost = this.repository.avgUserStoryCost(managerId, false);
			deviationUserStoryCost = this.repository.deviationUserStoryCostByManager(managerId, false);
			minimumUserStoryCost = this.repository.minUserStoryCost(managerId, false);
			maximumUserStoryCost = this.repository.maxUserStoryCost(managerId, false);

			dashboard.setTotalMustPriorityUserStories(totalMustPriorityUserStories);
			dashboard.setTotalShouldPriorityUserStories(totalShouldPriorityUserStories);
			dashboard.setTotalCouldPriorityUserStories(totalCouldPriorityUserStories);
			dashboard.setTotalWontPriorityUserStories(totalWontPriorityUserStories);
			dashboard.setAverageUserStoryCost(averageUserStoryCost);
			dashboard.setDeviationUserStoryCost(deviationUserStoryCost);
			dashboard.setMinimumUserStoryCost(minimumUserStoryCost);
			dashboard.setMaximumUserStoryCost(maximumUserStoryCost);
		} else {
			dashboard.setTotalMustPriorityUserStories(0);
			dashboard.setTotalShouldPriorityUserStories(0);
			dashboard.setTotalCouldPriorityUserStories(0);
			dashboard.setTotalWontPriorityUserStories(0);
			dashboard.setAverageUserStoryCost(Double.NaN);
			dashboard.setDeviationUserStoryCost(Double.NaN);
			dashboard.setMinimumUserStoryCost(Double.NaN);
			dashboard.setMaximumUserStoryCost(Double.NaN);
		}

		// Saving the data

		super.getBuffer().addData(dashboard);

	}
	@Override
	public void unbind(final ManagerDashboard object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "totalMustPriorityUserStories", "totalShouldPriorityUserStories", "totalCouldPriorityUserStories", "totalWontPriorityUserStories", "averageUserStoryCost", "deviationUserStoryCost", "minimumUserStoryCost",
			"maximumUserStoryCost", "averageProjectCost", "deviationProjectCost", "minimumProjectCost", "maximumProjectCost");
		super.getResponse().addData(dataset);
	}
}
