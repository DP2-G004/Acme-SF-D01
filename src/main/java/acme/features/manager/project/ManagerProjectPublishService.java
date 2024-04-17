
package acme.features.manager.project;

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
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	@Autowired
	ManagerProjectRepository publishRepository;


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		Project pr;
		Manager manager;

		masterId = super.getRequest().getData("id", int.class);
		pr = this.publishRepository.findProjectById(masterId);
		manager = pr == null ? null : pr.getManager();
		status = pr != null && pr.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.publishRepository.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project p) {
		assert p != null;
		super.bind(p, "code", "title", "summary", "indication", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("indication")) {
			boolean containsFatalErrors = object.isIndication();
			super.state(!containsFatalErrors, "indication", "manager.project.form.error.containing-fatal-errors");

		}
		//At least one user story
		Collection<ProjectUserStoryLink> userStoriesLinkedToProject = this.publishRepository.findLinkedUserStoriesByProjectId(object.getId());
		int numUserStories = userStoriesLinkedToProject.size();
		super.state(numUserStories > 0, "*", "manager.project.error.not-enough-user-stories");
		//check all of them are published
		Collection<UserStory> userStories = this.publishRepository.findUserStoriesByProjectId(object.getId());
		boolean checkAllUserStoriesPublished = userStories.stream().anyMatch(x -> x.isDraftMode());
		super.state(!checkAllUserStoriesPublished, "*", "manager.project.error.not-all-published-user-stories");
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		object.setDraftMode(false);
		this.publishRepository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "summary", "indication", "cost", "link", "draft-mode");
		super.getResponse().addData(dataset);
	}
}
