
package acme.features.manager.userstory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.userstory.Priority;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryUpdateService extends AbstractService<Manager, UserStory> {

	@Autowired
	ManagerUserStoryRepository updateRepository;


	@Override
	public void authorise() {
		//		Boolean status;
		//		int masterId;
		//		UserStory us;
		//		Manager manager;
		//
		//		masterId = super.getRequest().getData("id", int.class);
		//		us = this.updateRepository.findUserStoryById(masterId);
		//		manager = us == null ? null : us.getManager();
		//		status = us != null && us.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);
		//
		//		super.getResponse().setAuthorised(status);
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		UserStory object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.updateRepository.findUserStoryById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final UserStory us) {
		assert us != null;
		super.bind(us, "title", "description", "estimatedCost", "acceptanceCriteria", "priority", "link");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("draft-mode"))
			super.state(object.isDraftMode(), "draft-mode", "manager.user-story.form.error.draft-mode");

	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;
		this.updateRepository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;
		Dataset dataset;
		SelectChoices choices;
		choices = SelectChoices.from(Priority.class, object.getPriority());

		dataset = super.unbind(object, "title", "description", "estimated-cost", "acceptance-criteria", "priority", "link", "draft-mode");
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);
	}
}
