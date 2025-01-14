
package acme.features.manager.project_user_story_link;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryLinkShowService extends AbstractService<Manager, ProjectUserStoryLink> {

	@Autowired
	private ManagerProjectUserStoryLinkRepository showRepository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStoryLink link = this.showRepository.findLinkById(id);

		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();

		boolean status = link != null && principal.hasRole(Manager.class) && link.getProject().getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStoryLink link = this.showRepository.findLinkById(id);
		super.getBuffer().addData(link);
	}
	@Override
	public void unbind(final ProjectUserStoryLink object) {
		assert object != null;
		int id = super.getRequest().getPrincipal().getActiveRoleId();
		SelectChoices projectChoices;
		SelectChoices userStoriesChoices;

		Collection<Project> projects = this.showRepository.findProjectsByManagerId(id);
		Collection<UserStory> userStories = this.showRepository.findUserStoriesByManagerId(id);

		Dataset dataset;

		projectChoices = SelectChoices.from(projects, "code", object.getProject());
		userStoriesChoices = SelectChoices.from(userStories, "title", object.getUserStory());

		dataset = super.unbind(object, "project", "userStory");

		dataset.put("projects", projectChoices);
		dataset.put("userStories", userStoriesChoices);
		dataset.put("draftMode", object.getProject().isDraftMode());

		super.getResponse().addData(dataset);
	}
}
