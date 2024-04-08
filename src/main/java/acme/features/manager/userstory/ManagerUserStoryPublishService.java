
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryPublishService extends AbstractService<Manager, UserStory> {

	@Autowired
	ManagerUserStoryRepository publishRepository;


	@Override
	public void authorise() {
		Boolean status;
		int id;
		UserStory us;
		Manager manager;

		id = super.getRequest().getData("id", int.class);
		us = this.publishRepository.findUserStoryById(id);
		manager = us == null ? null : us.getManager();
		status = us != null && us.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.publishRepository.findUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory us) {
		assert us != null;
		super.bind(us, "title", "description", "estimated-cost", "acceptance-criteria", "priority", "link");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
		//At least one project
		Collection<ProjectUserStoryLink> projects = this.publishRepository.findProjectsByUserStoryId(object.getId());
		int numProjects = projects.size();
		super.state(numProjects > 0, "*", "manager.user-story.error.not-enough-projects");
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;
		object.setDraftMode(false);
		this.publishRepository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "estimated-cost", "acceptance-criteria", "priority", "link", "draft-mode");
		super.getResponse().addData(dataset);
	}
}
