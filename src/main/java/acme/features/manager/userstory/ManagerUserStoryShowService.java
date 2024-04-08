
package acme.features.manager.userstory;

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
		//		boolean status;
		//		int id;
		//		Manager manager;
		//		UserStory us;
		//
		//		id = super.getRequest().getData("id", int.class);
		//		us = this.showRepository.findUserStoryById(id);
		//		manager = us == null ? null : us.getManager();
		//		status = us != null && us.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(true);
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
		dataset = super.unbind(object, "title", "description", "estimated-cost", "acceptance-criteria", "priority", "link", "draft-mode");
		super.getResponse().addData(dataset);
	}
}
