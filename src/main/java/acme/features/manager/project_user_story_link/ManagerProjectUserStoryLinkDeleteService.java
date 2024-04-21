
package acme.features.manager.project_user_story_link;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryLinkDeleteService extends AbstractService<Manager, ProjectUserStoryLink> {

	@Autowired
	private ManagerProjectUserStoryLinkRepository deleteRepository;


	@Override
	public void authorise() {
		boolean status;
		int id = super.getRequest().getData("id", int.class);
		Manager manager = this.deleteRepository.findProjectManagerByLinkId(id);
		status = super.getRequest().getPrincipal().getActiveRoleId() == manager.getId();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectUserStoryLink link;
		int id;

		id = super.getRequest().getData("id", int.class);
		link = this.deleteRepository.findLinkById(id);

		super.getBuffer().addData(link);
	}

	@Override
	public void bind(final ProjectUserStoryLink object) {
		assert object != null;
		super.bind(object, "project", "userStory");
	}

	@Override
	public void validate(final ProjectUserStoryLink object) {
		assert object != null;
	}

	@Override
	public void perform(final ProjectUserStoryLink object) {
		assert object != null;
		this.deleteRepository.delete(object);
	}

	@Override
	public void unbind(final ProjectUserStoryLink object) {
		assert object != null;

		Dataset dataset;

		int id = super.getRequest().getPrincipal().getActiveRoleId();
		SelectChoices projectChoices;
		SelectChoices userStoriesChoices;

		Collection<Project> projects = this.deleteRepository.findProjectsByManagerId(id);
		Collection<UserStory> userStories = this.deleteRepository.findUserStoriesByManagerId(id);

		projectChoices = SelectChoices.from(projects, "title", object.getProject());
		userStoriesChoices = SelectChoices.from(userStories, "title", object.getUserStory());

		dataset = super.unbind(object, "project", "userStory");

		dataset.put("projects", projectChoices);
		dataset.put("userStories", userStoriesChoices);

		super.getResponse().addData(dataset);
	}

	//	@Override
	//	public void authorise() {
	//		int id = super.getRequest().getData("id", int.class);
	//		ProjectUserStoryLink link = this.deleteRepository.findLinkById(id);
	//
	//		Principal principal = super.getRequest().getPrincipal();
	//		int userAccountId = principal.getAccountId();
	//
	//		boolean status = link != null && principal.hasRole(Manager.class) && link.getProject().getManager().getUserAccount().getId() == userAccountId;
	//
	//		super.getResponse().setAuthorised(status);
	//	}
	//	@Override
	//	public void load() {
	//		int id = super.getRequest().getData("id", int.class);
	//		ProjectUserStoryLink link = this.deleteRepository.findLinkById(id);
	//		super.getBuffer().addData(link);
	//	}
	//	@Override
	//	public void bind(final ProjectUserStoryLink object) {
	//		assert object != null;
	//		super.bind(object, "project", "userStory");
	//	}
	//	@Override
	//	public void validate(final ProjectUserStoryLink object) {
	//		assert object != null;
	//	}
	//	@Override
	//	public void perform(final ProjectUserStoryLink object) {
	//		assert object != null;
	//		this.deleteRepository.delete(object);
	//	}
	//	@Override
	//	public void unbind(final ProjectUserStoryLink object) {
	//		//		assert object != null;
	//		//		Dataset dataset = super.unbind(object, "userStory");
	//		//		super.getResponse().addData(dataset);
	//		assert object != null;
	//
	//		Dataset dataset;
	//
	//		int id = super.getRequest().getPrincipal().getActiveRoleId();
	//		SelectChoices projectChoices;
	//		SelectChoices userStoriesChoices;
	//
	//		Collection<Project> projects = this.deleteRepository.findProjectsByManagerId(id);
	//		Collection<UserStory> userStories = this.deleteRepository.findUserStoriesByManagerId(id);
	//
	//		projectChoices = SelectChoices.from(projects, "title", object.getProject());
	//		userStoriesChoices = SelectChoices.from(userStories, "title", object.getUserStory());
	//
	//		dataset = super.unbind(object, "project", "userStory");
	//
	//		dataset.put("projects", projectChoices);
	//		dataset.put("userStories", userStoriesChoices);
	//
	//		super.getResponse().addData(dataset);
	//	}
}
