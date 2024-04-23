
package acme.features.manager.project_user_story_link;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
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
		int id = super.getRequest().getData("id", int.class);
		Collection<ProjectUserStoryLink> links = this.listRepository.findLinksById(id);
		super.getBuffer().addData(links);
	}
	@Override
	public void unbind(final ProjectUserStoryLink object) {
		assert object != null;
		Dataset dataset = super.unbind(object, "project", "userStory");
		super.getResponse().addData(dataset);
	}
}