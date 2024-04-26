
package acme.features.manager.project_user_story_link;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryLinkListService extends AbstractService<Manager, ProjectUserStoryLink> {

	@Autowired
	private ManagerProjectUserStoryLinkRepository listRepository;


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		int id = super.getRequest().getPrincipal().getActiveRoleId();
		Collection<ProjectUserStoryLink> links = this.listRepository.findLinksByManagerId(id);
		super.getBuffer().addData(links);
	}

	@Override
	public void unbind(final ProjectUserStoryLink object) {
		assert object != null;

		Project project;
		UserStory userStory;
		int projectUserStoryId;
		Dataset dataset;

		projectUserStoryId = object.getId();
		project = this.listRepository.findOneProjectByProjectUserStoryId(projectUserStoryId);
		userStory = this.listRepository.findOneUserStoryByProjectUserStoryId(projectUserStoryId);

		dataset = super.unbind(object, "userStory", "project");
		dataset.put("project", project.getCode());
		dataset.put("userStory", userStory.getTitle());

		super.getResponse().addData(dataset);
	}
}
