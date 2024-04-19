
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
public class ManagerProjectUserStoryLinkCreateService extends AbstractService<Manager, ProjectUserStoryLink> {

	@Autowired
	private ManagerProjectUserStoryLinkRepository createRepository;


	@Override
	public void authorise() {
		int projectId = super.getRequest().getData("id", int.class);
		Project project = this.createRepository.findProjectById(projectId);

		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();

		boolean status = project != null && project.isDraftMode() && principal.hasRole(Manager.class) && project.getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		Project project = this.createRepository.findProjectById(projectId);

		ProjectUserStoryLink object = new ProjectUserStoryLink();
		object.setProject(project);

		super.getBuffer().addData(object);
	}
	@Override
	public void bind(final ProjectUserStoryLink object) {
		assert object != null;
		super.bind(object, "userStory");
	}
	@Override
	public void validate(final ProjectUserStoryLink object) {
		assert object != null;
		int projectId = super.getRequest().getData("projectId", int.class);

		if (!super.getBuffer().getErrors().hasErrors("userStory")) {
			boolean duplicatedUserStory = this.createRepository.findLinksByProjectId(projectId).stream().anyMatch(x -> x.getUserStory().equals(object.getUserStory()));
			super.state(!duplicatedUserStory, "userStory", "manager.project.form.error.duplicated-user-story");
		}
	}
	@Override
	public void perform(final ProjectUserStoryLink object) {
		assert object != null;
		this.createRepository.save(object);
	}
	@Override
	public void unbind(final ProjectUserStoryLink object) {
		assert object != null;
		int projectId = super.getRequest().getData("projectId", int.class);

		int managerId = object.getProject().getManager().getUserAccount().getId();
		Collection<UserStory> userStories = this.createRepository.findUserStoriesByManagerId(managerId).stream()
			.filter(x -> !this.createRepository.findLinksByProjectId(projectId).stream().map(ProjectUserStoryLink::getUserStory).anyMatch(x2 -> x2.equals(x))).toList();

		SelectChoices choices = SelectChoices.from(userStories, "title", object.getUserStory());

		Dataset dataset = super.unbind(object, "userStory");

		dataset.put("userStories", choices);
		dataset.put("projectId", projectId);

		super.getResponse().addData(dataset);
	}
}
