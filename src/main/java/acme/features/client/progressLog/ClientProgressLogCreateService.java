
package acme.features.client.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.contract.Progress;
import acme.roles.client.Client;

@Service
public class ClientProgressLogCreateService extends AbstractService<Client, Progress> {

	@Autowired
	private ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int contractId;
		Contract contract;

		contractId = super.getRequest().getData("contractId", int.class);
		contract = this.repository.findContractById(contractId);

		Client c = contract != null ? contract.getClient() : null;

		int activeClientId = super.getRequest().getPrincipal().getActiveRoleId();
		Client activeClient = this.repository.findClientById(activeClientId);
		boolean clientOwnsContract = c == activeClient;

		status = clientOwnsContract && contract != null && contract.isDraftMode() && super.getRequest().getPrincipal().hasRole(contract.getClient());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Progress object;
		int contractId;
		Contract contract;

		contractId = super.getRequest().getData("contractId", int.class);
		contract = this.repository.findContractById(contractId);

		object = new Progress();
		object.setDraftMode(true);
		object.setContract(contract);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Progress object) {

		assert object != null;

		super.bind(object, "record", "completeness", "comment", "registration", "responsable");
	}

	@Override
	public void validate(final Progress object) {

		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("recordId")) {
			Progress existing;

			existing = this.repository.findProgressByRecord(object.getRecord());
			super.state(existing == null, "record", "client.progress.form.error.duplicated");
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

		object.setDraftMode(true);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Progress object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "record", "completeness", "comment", "registration", "responsable", "draftMode");

		dataset.put("contractId", object.getContract().getId());
		dataset.put("draftMode", object.isDraftMode());

		super.getResponse().addData(dataset);
	}

}
