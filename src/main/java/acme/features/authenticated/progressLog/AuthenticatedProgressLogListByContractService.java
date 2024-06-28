
package acme.features.authenticated.progressLog;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.contract.Progress;

@Service
public class AuthenticatedProgressLogListByContractService extends AbstractService<Authenticated, Progress> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedProgressLogRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {
		final int contractId = super.getRequest().getData("contractId", int.class);
		Contract contract = this.repository.findContractById(contractId);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = contract != null && principal.hasRole(Any.class);

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		Collection<Progress> progress;

		final int contractId = super.getRequest().getData("contractId", int.class);
		progress = this.repository.findProgressByContractId(contractId).stream().toList();

		super.getBuffer().addData(progress);
	}

	@Override
	public void unbind(final Progress object) {
		assert object != null;

		Collection<Contract> contracts = this.repository.findAllContracts();
		SelectChoices contractsChoices = SelectChoices.from(contracts, "contractCode", object.getContract());

		final Dataset dataset = super.unbind(object, "record", "contract");
		dataset.put("contract", contractsChoices.getSelected().getLabel());
		dataset.put("contracts", contractsChoices);

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		int contractId;

		contractId = super.getRequest().getData("contractId", int.class);

		super.getResponse().addGlobal("contractId", contractId);

		super.getResponse().addData(dataset);
	}

}
