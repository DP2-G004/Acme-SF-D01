
package acme.features.manager.userstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.userstory.Priority;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryShowService extends AbstractService<Manager, UserStory> {

	@Autowired
	ManagerUserStoryRepository showRepository;


	@Override
	public void authorise() {
		Boolean status;
		int id;
		UserStory us;
		Manager manager;

		id = super.getRequest().getData("id", int.class);
		us = this.showRepository.findUserStoryById(id);
		manager = us == null ? null : us.getManager();
		status = us != null && super.getRequest().getPrincipal().hasRole(manager) && us.getManager().equals(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		UserStory us;
		int id;
		id = super.getRequest().getData("id", int.class);
		us = this.showRepository.findUserStoryById(id);
		super.getBuffer().addData(us);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choices;
		choices = SelectChoices.from(Priority.class, object.getPriority());

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draft-mode");
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);
	}
}
