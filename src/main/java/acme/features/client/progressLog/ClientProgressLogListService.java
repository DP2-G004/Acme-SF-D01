
package acme.features.client.progressLog;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.contract.Progress;
import acme.roles.client.Client;

@Service
public class ClientProgressLogListService extends AbstractService<Client, Progress> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ClientProgressLogRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();

		int id = super.getRequest().getData("contractId", int.class);
		Contract c = this.repository.findContractById(id);
		final boolean authorise = c != null && principal.hasRole(Client.class) && c.getClient().getUserAccount().getId() == principal.getAccountId();
		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("contractId", int.class);
		Collection<Progress> progress = this.repository.findProgressByContractId(id);
		Boolean draft = super.getRequest().getData("draft", boolean.class);
		super.getResponse().addGlobal("draft", draft == null ? false : draft);
		super.getBuffer().addData(progress);
	}

	@Override
	public void unbind(final Progress object) {
		assert object != null;

		final Dataset dataset = super.unbind(object, "record", "contract");

		Collection<Contract> contracts = this.repository.findAllContract();
		SelectChoices trainingModulesChoices = SelectChoices.from(contracts, "contractCode", object.getContract());
		dataset.put("contract", trainingModulesChoices.getSelected().getLabel());
		dataset.put("contracts", trainingModulesChoices);

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");
		super.getResponse().addGlobal("masterId", super.getRequest().getData("contractId", int.class));
		super.getResponse().addData(dataset);
	}

}
