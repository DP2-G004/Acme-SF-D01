
package acme.features.client.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Progress;
import acme.roles.client.Client;

@Service
public class ClientProgressLogPublishService extends AbstractService<Client, Progress> {

	@Autowired
	protected ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		int id = super.getRequest().getData("id", int.class);
		Progress progress = this.repository.findProgressById(id);

		final boolean authorise = progress != null && progress.isDraftMode() && principal.hasRole(Client.class) && progress.getContract().getClient().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Progress progress = this.repository.findProgressById(id);

		super.getBuffer().addData(progress);
	}

	@Override
	public void bind(final Progress object) {
		assert object != null;

		super.bind(object, "record", "completeness", "comment", "registration", "responsable");
	}

	@Override
	public void validate(final Progress object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("completeness")) {
			Double existing;
			existing = this.repository.findMaxCompletenessPublished(object.getContract().getId()).or(0.);
			super.state(object.getCompleteness() >= existing, "completeness", "client.progress.form.error.completeness-too-low");
		}
		if (!super.getBuffer().getErrors().hasErrors("registration")) {
			final int progressId = super.getRequest().getData("id", int.class);
			Progress progress = this.repository.findProgressById(progressId);
			final boolean registrationTooSoon = this.repository.findAllProgress().stream().filter(e -> e.getContract().getId() == progress.getContract().getId())
				.anyMatch(e -> e.getRegistration() != null && e.getRegistration().after(object.getRegistration()) && e.getId() != progress.getId()) || !object.getRegistration().after(object.getContract().getInstantiation());
			super.state(!registrationTooSoon, "registration",
				object.getRegistration().before(object.getContract().getInstantiation()) ? "client.progress.form.error.registration-moment-must-be-later-than-instantiation" : "client.progress.form.error.registration-moment-must-be-later");
		}
	}

	@Override
	public void perform(final Progress object) {
		assert object != null;

		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Progress object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "record", "completeness", "comment", "registration", "responsable", "draftMode");

		super.getResponse().addData(dataset);
	}

}
