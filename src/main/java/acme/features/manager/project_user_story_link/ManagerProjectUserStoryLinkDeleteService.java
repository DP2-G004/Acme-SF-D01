
package acme.features.manager.project_user_story_link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.roles.Manager;

@Service
public class ManagerProjectUserStoryLinkDeleteService extends AbstractService<Manager, ProjectUserStoryLink> {

	@Autowired
	private ManagerProjectUserStoryLinkRepository deleteRepository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStoryLink link = this.deleteRepository.findLinkById(id);

		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();

		boolean status = link != null && principal.hasRole(Manager.class) && link.getProject().getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ProjectUserStoryLink link = this.deleteRepository.findLinkById(id);
		super.getBuffer().addData(link);
	}
	@Override
	public void bind(final ProjectUserStoryLink object) {
		assert object != null;
		super.bind(object, "userStory");
	}
	@Override
	public void validate(final ProjectUserStoryLink object) {
		assert object != null;
	}
	@Override
	public void perform(final ProjectUserStoryLink object) {
		assert object != null;
		this.deleteRepository.delete(object);
	}
	@Override
	public void unbind(final ProjectUserStoryLink object) {
		assert object != null;
		Dataset dataset = super.unbind(object, "userStory");
		super.getResponse().addData(dataset);
	}
}
