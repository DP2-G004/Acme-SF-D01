
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.project.Project;
import acme.roles.client.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	@Autowired
	protected ClientContractRepository repository;

	// AbstractService<Authenticated, Contract> ---------------------------


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		int masterId = super.getRequest().getData("id", int.class);
		Contract contract = this.repository.findContractById(masterId);

		boolean status = contract != null && contract.isDraftMode() && principal.hasRole(contract.getClient()) && contract.getClient().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		Contract object = this.repository.findContractById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int projectId = super.getRequest().getData("project", int.class);
		Project project = this.repository.findProjectById(projectId);

		super.bind(object, "contractCode", "instantiation", "providerName", "customerName", "goals", "budget", "project");
		object.setProject(project);
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			boolean duplicatedCode = this.repository.findAllContracts().stream().anyMatch(e -> e.getContractCode().equals(object.getContractCode()));
			if (duplicatedCode)
				duplicatedCode = !this.repository.findContractById(object.getId()).getContractCode().equals(object.getContractCode());
			super.state(!duplicatedCode, "contractCode", "client.contract.form.error.duplicated-code");
		}

		final boolean emptyProject = object.getProject() != null;
		if (!emptyProject)
			super.state(!emptyProject, "project", "client.contract.form.error.choose-project");

		if (!super.getBuffer().getErrors().hasErrors("budget") && emptyProject) {
			Collection<Contract> contracts = this.repository.findAllContractsByProjectId(object.getProject().getId());
			final boolean budget = object.getBudget().getAmount() + contracts.stream().mapToDouble(x -> x.getBudget().getAmount()).sum() > object.getProject().getCost() || object.getBudget().getAmount() < 0;
			super.state(!budget, "budget", object.getBudget().getAmount() < 0 ? "client.contract.form.error.budget-negative" : "client.contract.form.error.budget-total-cost");
		}
		if (!super.getBuffer().getErrors().hasErrors("instantiation")) {
			final boolean draftLogs = this.repository.findAllDraftProgress(object.getId()).isEmpty();
			final boolean tooLate = !this.repository.findAllProgress(object.getId()).stream().anyMatch(e -> e.getRegistration().after(object.getInstantiation()));
			super.state(draftLogs && tooLate, "instantiation", draftLogs ? "client.contract.form.error.published-log" : "client.contract.form.error.draft-progress");
		}
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		object.setDraftMode(false);

		this.repository.save(object);
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
