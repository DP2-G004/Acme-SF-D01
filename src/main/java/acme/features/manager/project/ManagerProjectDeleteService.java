
package acme.features.manager.project;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_record.AuditRecord;
import acme.entities.code_audit.CodeAudit;
import acme.entities.contract.Contract;
import acme.entities.invoice.Invoice;
import acme.entities.progress_log.ProgressLog;
import acme.entities.project.Project;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {

	@Autowired
	ManagerProjectRepository deleteRepository;


	@Override
	public void authorise() {
		Boolean status;
		int projectId;
		Project p;
		Manager manager;

		projectId = super.getRequest().getData("id", int.class);
		p = this.deleteRepository.findProjectById(projectId);
		manager = p == null ? null : p.getManager();
		status = p != null && p.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.deleteRepository.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;
		super.bind(object, "code", "title", "summary", "indication", "cost", "link");

	}

	@Override
	public void validate(final Project object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("draft-mode"))
			super.state(object.isDraftMode(), "draft-mode", "manager.project.form.error.draft-mode");
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		int projectId = super.getRequest().getData("id", int.class);

		Collection<ProjectUserStoryLink> userStories;
		userStories = this.deleteRepository.findLinkedUserStoriesByProjectId(projectId);

		Collection<CodeAudit> codeAudits = this.deleteRepository.findAllCodeAuditsFromProjectId(projectId);
		for (CodeAudit codeAudit : codeAudits) {
			Collection<AuditRecord> auditRecords = this.deleteRepository.findAllAuditRecordsFromCodeAuditId(codeAudit.getId());
			this.deleteRepository.deleteAll(auditRecords);
		}

		this.deleteRepository.deleteAll(codeAudits);

		Collection<Contract> contracts = this.deleteRepository.findAllContractsByProjectId(projectId);
		for (Contract contract : contracts) {
			Collection<ProgressLog> progressLogs = this.deleteRepository.findAllProgressLogsByContractId(contract.getId());
			this.deleteRepository.deleteAll(progressLogs);
		}
		this.deleteRepository.deleteAll(contracts);

		Collection<Sponsorship> sponsorships = this.deleteRepository.findAllSponsorshipsByProjectId(projectId);
		for (Sponsorship sponsorship : sponsorships) {
			Collection<Invoice> invoices = this.deleteRepository.findAllInvoicesBySponsorshipId(sponsorship.getId());
			this.deleteRepository.deleteAll(invoices);
		}
		this.deleteRepository.deleteAll(sponsorships);

		Collection<TrainingModule> trainingModules = this.deleteRepository.findAllTrainingModulesByProjectId(projectId);
		for (TrainingModule trainingModule : trainingModules) {
			Collection<TrainingSession> trainingSessions = this.deleteRepository.findAllTrainingSessionsByTrainingModuleId(trainingModule.getId());
			this.deleteRepository.deleteAll(trainingSessions);
		}
		this.deleteRepository.deleteAll(trainingModules);

		this.deleteRepository.deleteAll(userStories);
		this.deleteRepository.delete(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "summary", "indication", "cost", "link");
		if (object.isIndication()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("indication", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("indication", "No");

		super.getResponse().addData(dataset);
	}
}
