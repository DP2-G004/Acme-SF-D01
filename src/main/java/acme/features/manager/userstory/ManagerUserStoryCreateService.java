
package acme.features.manager.userstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.userstory.Priority;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryCreateService extends AbstractService<Manager, UserStory> {

	@Autowired
	ManagerUserStoryRepository createRepository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Manager.class);
		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		UserStory us;
		Manager m;
		Principal principal;
		principal = super.getRequest().getPrincipal();
		m = this.createRepository.findManagerById(principal.getActiveRoleId());
		us = new UserStory();
		us.setManager(m);
		us.setDraftMode(true);

		super.getBuffer().addData(us);
	}

	@Override
	public void bind(final UserStory us) {
		assert us != null;
		super.bind(us, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
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
		this.createRepository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choices;
		choices = SelectChoices.from(Priority.class, object.getPriority());

		dataset = super.unbind(object, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link", "draft-mode");
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);
	}
}
