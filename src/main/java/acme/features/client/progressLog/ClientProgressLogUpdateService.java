
package acme.features.client.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Progress;
import acme.roles.client.Client;

@Service
public class ClientProgressLogUpdateService extends AbstractService<Client, Progress> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		Progress progress = this.repository.findProgressById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

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

		if (!super.getBuffer().getErrors().hasErrors("record")) {
			final int progressId = super.getRequest().getData("id", int.class);
			final boolean duplicatedCode = this.repository.findAllProgress().stream().filter(e -> e.getId() != progressId).anyMatch(e -> e.getRecord().equals(object.getRecord()));

			super.state(!duplicatedCode, "record", "client.progress.form.error.duplicated");
		}
		if (!super.getBuffer().getErrors().hasErrors("completeness")) {
			Double existing;
			existing = this.repository.findMaxCompletenessPublished(object.getContract().getId()).or(0.);
			super.state(object.getCompleteness() >= existing, "completeness", "client.progress.form.error.completeness-too-low");
		}
		if (!super.getBuffer().getErrors().hasErrors("registration"))
			if (object.getRegistration() == null)
				super.state(false, "registration", "client.progress.form.error.registration-format");
			else {
				final boolean registrationTooSoon = this.repository.findAllProgress().stream().filter(e -> e.getContract().getId() == object.getContract().getId())
					.anyMatch(e -> e.getRegistration() != null && e.getRegistration().after(object.getRegistration()) && !e.isDraftMode()) || !object.getRegistration().after(object.getContract().getInstantiation());
				super.state(!registrationTooSoon, "registration",
					object.getRegistration().before(object.getContract().getInstantiation()) ? "client.progress.form.error.registration-moment-must-be-later-than-instantiation" : "client.progress.form.error.registration-moment-must-be-later");
			}
	}

	@Override
	public void perform(final Progress object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Progress object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "record", "completeness", "comment", "registration", "responsable", "draftMode");

		super.getResponse().addData(dataset);
	}

}
