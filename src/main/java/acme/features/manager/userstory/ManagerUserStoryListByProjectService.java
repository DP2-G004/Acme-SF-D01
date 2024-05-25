
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryListByProjectService extends AbstractService<Manager, UserStory> {

	@Autowired
	ManagerUserStoryRepository listMineRepository;


	@Override
	public void authorise() {
		boolean status;
		int projectId = super.getRequest().getData("projectId", int.class);
		Project p = this.listMineRepository.findProjectById(projectId);
		Principal principal = super.getRequest().getPrincipal();
		Manager m = this.listMineRepository.findManagerById(principal.getActiveRoleId());
		status = super.getRequest().getPrincipal().hasRole(Manager.class) && p.getManager().equals(m);
		super.getResponse().setAuthorised(status);
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

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draft-mode");
		int projectId = super.getRequest().getData("projectId", int.class);

		super.getResponse().addGlobal("projectId", projectId);
		super.getResponse().addData(dataset);
	}
}
