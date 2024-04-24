
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

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectUserStoryLinkRepository repository;

	// AbstractService<Manager, ProjectUserStoryLink> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		ProjectUserStoryLink pus;
		Manager manager;

		masterId = super.getRequest().getData("id", int.class);
		pus = this.repository.findLinkById(masterId);
		manager = pus == null ? null : this.repository.findOneManagerByLinkId(masterId);
		status = pus != null && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ProjectUserStoryLink object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findLinkById(id);

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
	}

	@Override
	public void perform(final ProjectUserStoryLink object) {
		assert object != null;

		this.repository.delete(object);
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

		userStories = this.repository.findUserStoriesByManagerId(managerId);
		choicesUS = SelectChoices.from(userStories, "title", object.getUserStory());

		projects = this.repository.findProjectsByManagerId(managerId);
		choicesP = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "userStory", "project");
		dataset.put("userStory", choicesUS.getSelected().getKey());
		dataset.put("userStories", choicesUS);
		dataset.put("project", choicesP.getSelected().getKey());
		dataset.put("projects", choicesP);

		super.getResponse().addData(dataset);
	}

}
