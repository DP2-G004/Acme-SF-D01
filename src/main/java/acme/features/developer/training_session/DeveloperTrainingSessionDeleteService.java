
package acme.features.developer.training_session;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionDeleteService extends AbstractService<Developer, TrainingSession> {

	@Autowired
	protected DeveloperTrainingSessionRepository repository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		TrainingSession trainingSession = this.repository.findTrainingSessionById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = trainingSession != null && trainingSession.getDraftMode() && trainingSession.getTrainingModule().getDeveloper().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		TrainingSession trainingSession = this.repository.findTrainingSessionById(id);

		super.getBuffer().addData(trainingSession);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "email", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;
		int res = 0;
		boolean b = true;
		Collection<TrainingSession> ts = this.repository.findAllTrainingSession();
		for (TrainingSession trainingSession : ts)
			if (trainingSession.getTrainingModule().getId() == object.getTrainingModule().getId())
				res += 1;
		if (res <= 1)
			b = false;
		super.state(b, "*", "developer.trainingSession.form.error.one-training-session-minimum");

	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "email", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
