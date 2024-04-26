
package acme.features.manager.userstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.entities.userstory.Priority;
import acme.entities.userstory.UserStory;
import acme.features.manager.project_user_story_link.ManagerProjectUserStoryLinkRepository;
import acme.roles.Manager;

@Service
public class ManagerUserStoryCreateInProjectService extends AbstractService<Manager, UserStory> {

	private static final String						PROJECT_ID	= "projectId";
	@Autowired
	private ManagerUserStoryRepository				createRepository;
	@Autowired
	private ManagerProjectUserStoryLinkRepository	linkRepository;


	@Override
	public void authorise() {
		boolean status;
		int projectId;
		Project project;
		Manager manager;

		projectId = super.getRequest().getData(ManagerUserStoryCreateInProjectService.PROJECT_ID, int.class);
		project = this.createRepository.findProjectById(projectId);
		manager = this.createRepository.findManagerById(super.getRequest().getPrincipal().getActiveRoleId());
		status = project != null && project.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		UserStory object;
		Manager manager;

		manager = this.createRepository.findManagerById(super.getRequest().getPrincipal().getActiveRoleId());

		object = new UserStory();
		object.setDraftMode(true);
		object.setManager(manager);

		super.getBuffer().addData(object);
	}
	@Override
	public void bind(final UserStory object) {
		assert object != null;
		super.bind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
	}
	@Override
	public void validate(final UserStory object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("estimated-cost"))
			super.state(object.getEstimatedCost() > 0, "estimated-cost", "manager.user-story.form.error.negative-cost");
	}
	@Override
	public void perform(final UserStory object) {
		assert object != null;

		ProjectUserStoryLink pus = new ProjectUserStoryLink();
		int projectId;
		Project project;

		projectId = super.getRequest().getData(ManagerUserStoryCreateInProjectService.PROJECT_ID, int.class);
		project = this.createRepository.findProjectById(projectId);

		pus.setProject(project);
		pus.setUserStory(object);

		this.createRepository.save(object);
		this.linkRepository.save(pus);
	}
	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choices;

		int projectId;

		choices = SelectChoices.from(Priority.class, object.getPriority());

		projectId = super.getRequest().getData(ManagerUserStoryCreateInProjectService.PROJECT_ID, int.class);

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draft-mode");
		dataset.put(ManagerUserStoryCreateInProjectService.PROJECT_ID, super.getRequest().getData(ManagerUserStoryCreateInProjectService.PROJECT_ID, int.class));
		dataset.put("priorities", choices);

		super.getResponse().addGlobal("projectId", projectId);
		super.getResponse().addData(dataset);
	}
}
