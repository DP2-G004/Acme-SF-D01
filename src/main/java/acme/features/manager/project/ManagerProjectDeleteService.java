
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	@Autowired
	ManagerProjectRepository deleteRepository;


	@Override
	public void authorise() {
		Boolean status;
		int projectId;
		Project p;
		Manager manager;

		projectId = super.getRequest().getData("id", int.class);
		p = this.deleteRepository.findProjectById(projectId);
		manager = p == null ? null : p.getManager();
		status = p != null && p.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.deleteRepository.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;
		super.bind(object, "code", "title", "summary", "indication", "cost", "link");

	}

	@Override
	public void validate(final Project object) {
		assert object != null;
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		int projectId = super.getRequest().getData("id", int.class);
		Collection<ProjectUserStoryLink> userStories;

		userStories = this.deleteRepository.findLinkedUserStoriesByProjectId(projectId);
		this.deleteRepository.deleteAll(userStories);
		this.deleteRepository.delete(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "summary", "indication", "cost", "link", "draft-mode");

		super.getResponse().addData(dataset);
	}
}
