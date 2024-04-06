
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryShowService extends AbstractService<Manager, UserStory> {

	@Autowired
	ManagerUserStoryRepository showRepository;


	@Override
	public void authorise() {
		boolean status;
		int id;
		Manager manager;
		UserStory us;

		id = super.getRequest().getData("id", int.class);
		us = this.showRepository.findUserStoryById(id);
		manager = us == null ? null : us.getManager();

		status = us != null && super.getRequest().getPrincipal().hasRole(manager);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int managerId;
		managerId = super.getRequest().getData("id", int.class);
		Collection<UserStory> userStories = this.showRepository.findAllUserStoriesByManagerId(managerId);
		super.getBuffer().addData(userStories);
	}

	@Override
	public void unbind(final UserStory object) {
		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
		super.getResponse().addData(dataset);
	}
}
