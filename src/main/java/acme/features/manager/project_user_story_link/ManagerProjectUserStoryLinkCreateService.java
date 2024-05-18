
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
public class ManagerProjectUserStoryLinkCreateService extends AbstractService<Manager, ProjectUserStoryLink> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryLinkRepository repository;

	// AbstractService<Manager, ProjectUserStoryLink> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectUserStoryLink object;

		object = new ProjectUserStoryLink();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final ProjectUserStoryLink object) {
		assert object != null;

		super.bind(object, "userStory", "project");
	}

	@Override
	public void validate(final ProjectUserStoryLink object) {
		assert object != null;
		Project project;
		UserStory userStory;

		project = object.getProject();
		userStory = object.getUserStory();

		super.state(object.getProject() != null, "*", "manager.project-user-story.create.error.null-project");
		super.state(object.getUserStory() != null, "*", "manager.project-user-story.create.error.null-user-story");

		if (!super.getBuffer().getErrors().hasErrors("project") && !super.getBuffer().getErrors().hasErrors("userStory")) {
			ProjectUserStoryLink existing;
			existing = this.repository.findOneLinkByProjectIdAndUserStoryId(project.getId(), userStory.getId());
			super.state(existing == null, "*", "manager.link.form.error.existing-project-assignment");
			super.state(project.isDraftMode() || !userStory.isDraftMode(), "project", "manager.link.form.error.published-project");
		}

	}

	@Override
	public void perform(final ProjectUserStoryLink object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final ProjectUserStoryLink object) {
		assert object != null;

		Collection<UserStory> userStories;
		Collection<Project> projects;
		SelectChoices choicesUS;
		SelectChoices choicesP;
		Dataset dataset;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRoleId();

		userStories = this.repository.findPublishedUserStoriesByManagerId(managerId, false);
		choicesUS = SelectChoices.from(userStories, "title", object.getUserStory());

		projects = this.repository.findNotPublishedProjectsByManagerId(managerId, true);
		choicesP = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "userStory", "project");
		dataset.put("userStory", choicesUS.getSelected().getKey());
		dataset.put("userStories", choicesUS);
		dataset.put("project", choicesP.getSelected().getKey());
		dataset.put("projects", choicesP);

		super.getResponse().addData(dataset);
	}

}
