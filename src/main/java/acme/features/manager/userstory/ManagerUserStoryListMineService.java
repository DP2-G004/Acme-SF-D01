
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListMineService extends AbstractService<Manager, UserStory> {

	@Autowired
	ManagerUserStoryRepository listMineRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<UserStory> userStories;
		int id = super.getRequest().getData("projectId", int.class);
		userStories = this.listMineRepository.findUserStoriesByProjectId(id);

		super.getBuffer().addData(userStories);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;

		dataset = super.unbind(object, "title", "description", "estimated-cost", "acceptance-criteria", "priority", "link", "draft-mode");
		super.getResponse().addData(dataset);
	}
}
