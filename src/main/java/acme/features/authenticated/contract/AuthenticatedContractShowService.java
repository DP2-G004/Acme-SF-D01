
package acme.features.authenticated.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.project.Project;

@Service
public class AuthenticatedContractShowService extends AbstractService<Authenticated, Contract> {

	@Autowired
	protected AuthenticatedContractRepository repository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		Contract contract = this.repository.findContractById(id);

		final Principal principal = super.getRequest().getPrincipal();

		final boolean authorise = contract != null && principal.hasRole(Any.class);

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Contract contract = this.repository.findContractById(id);

		super.getBuffer().addData(contract);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		Dataset dataset = super.unbind(object, "contractCode", "instantiation", "providerName", "customerName", "goals", "budget", "project", "draftMode");

		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);

		super.getResponse().addData(dataset);
	}

}
