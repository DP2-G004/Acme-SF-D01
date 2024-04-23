
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
public class ManagerProjectUserStoryLinkUpdateService extends AbstractService<Manager, ProjectUserStoryLink> {

	@Autowired
	private ManagerProjectUserStoryLinkRepository updateRepository;


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int id = super.getRequest().getData("id", int.class);
		ProjectUserStoryLink link = this.updateRepository.findLinkById(id);

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
		this.updateRepository.save(object);
	}

	@Override
	public void unbind(final ProjectUserStoryLink object) {
		assert object != null;

		Dataset dataset;

		int id = super.getRequest().getPrincipal().getActiveRoleId();
		SelectChoices projectChoices;
		SelectChoices userStoriesChoices;

		Collection<Project> projects = this.updateRepository.findProjectsByManagerId(id);
		Collection<UserStory> userStories = this.updateRepository.findUserStoriesByManagerId(id);

		projectChoices = SelectChoices.from(projects, "code", object.getProject());
		userStoriesChoices = SelectChoices.from(userStories, "title", object.getUserStory());

		dataset = super.unbind(object, "project", "userStory");

		dataset.put("projects", projectChoices);
		dataset.put("userStories", userStoriesChoices);

		super.getResponse().addData(dataset);
	}
}