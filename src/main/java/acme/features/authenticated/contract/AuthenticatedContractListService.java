
package acme.features.authenticated.contract;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.project.Project;

@Service
public class AuthenticatedContractListService extends AbstractService<Authenticated, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedContractRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		Collection<Contract> contracts;
		contracts = this.repository.findAllContracts();

		super.getBuffer().addData(contracts);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		final Dataset dataset = super.unbind(object, "contractCode", "project");
		dataset.put("project", projectsChoices.getSelected().getLabel());
		dataset.put("projects", projectsChoices);

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

}
