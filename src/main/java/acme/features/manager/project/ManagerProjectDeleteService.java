
package acme.features.manager.project;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.AbstractEntity;
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
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		int projectId = super.getRequest().getData("id", int.class);

		List<Contract> contracts;
		List<ProgressLog> progressLogs;

		List<Sponsorship> sponsorShips;
		List<Invoice> invoices;

		List<CodeAudit> codeAudits;
		List<AuditRecord> auditRecords;

		List<TrainingModule> trainingModule;
		List<TrainingSession> trainingSession;

		Collection<ProjectUserStoryLink> userStories;
		userStories = this.deleteRepository.findLinkedUserStoriesByProjectId(projectId);

		// SponsorShip e invoices
		sponsorShips = (List<Sponsorship>) this.deleteRepository.findManySponsorshipsByProjectId(projectId);
		if (sponsorShips != null) {
			Set<Integer> sponsorShipIds = sponsorShips.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			invoices = (List<Invoice>) this.deleteRepository.findManyInvoicesBySponsorshipIds(sponsorShipIds);
			this.deleteRepository.deleteAll(invoices);
			this.deleteRepository.deleteAll(sponsorShips);
		}

		// Contracts y progressLogs
		contracts = (List<Contract>) this.deleteRepository.findManyContractsByProjectId(projectId);
		if (contracts != null) {
			Set<Integer> contractIds = contracts.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			progressLogs = (List<ProgressLog>) this.deleteRepository.findManyProgressLogsByContractIds(contractIds);
			this.deleteRepository.deleteAll(progressLogs);
			this.deleteRepository.deleteAll(contracts);
		}

		//CodeAudtis y auditRecords
		codeAudits = (List<CodeAudit>) this.deleteRepository.findManyCodeAuditsByProjectId(projectId);
		if (codeAudits != null) {
			Set<Integer> codeAuditsIds = codeAudits.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			auditRecords = (List<AuditRecord>) this.deleteRepository.findManyAuditsRecordsByCodeAuditsId(codeAuditsIds);
			this.deleteRepository.deleteAll(auditRecords);
			this.deleteRepository.deleteAll(codeAudits);
		}

		//TrainingModule y TrainingSession 
		trainingModule = (List<TrainingModule>) this.deleteRepository.findManyTrainingModuleByProjectId(projectId);
		if (trainingModule != null) {
			Set<Integer> trainingModuleIds = trainingModule.stream().map(AbstractEntity::getId).collect(Collectors.toSet());
			trainingSession = (List<TrainingSession>) this.deleteRepository.findManyTrainingSessionByTrainingModuleId(trainingModuleIds);
			this.deleteRepository.deleteAll(trainingSession);
			this.deleteRepository.deleteAll(trainingModule);
		}
		//		Collection<Contract> contracts;
		//		Collection<ProgressLog> progressLogs;
		//
		//		Collection<Sponsorship> sponsorShips;
		//		Collection<Invoice> invoices;
		//
		//		Collection<CodeAudit> codeAudits;
		//		Collection<AuditRecord> auditRecords;
		//
		//		Collection<TrainingModule> trainingModule;
		//		Collection<TrainingSession> trainingSession;

		//		progressLogs = this.deleteRepository.findAllProgressLogsByProjectId(object.getId());
		//		contracts = this.deleteRepository.findManyContractsByProjectId(object.getId());
		//
		//		auditRecords = this.deleteRepository.findAllAuditRecordsOfAProjectById(object.getId());
		//		codeAudits = this.deleteRepository.findAllCodeAuditsOfAProjectById(object.getId());
		//
		//		sponsorShips = this.deleteRepository.findManySponsorshipsByProjectId(object.getId());
		//
		//		trainingSession = this.deleteRepository.findAllTrainingSessionsOfAProjectById(object.getId());
		//		trainingModule = this.deleteRepository.findAllTrainingModuleOfAProjectById(object.getId());

		//		this.deleteRepository.deleteAll(contracts);
		//		this.deleteRepository.deleteAll(auditRecords);
		//		this.deleteRepository.deleteAll(sponsorShips);
		//		this.deleteRepository.deleteAll(codeAudits);
		this.deleteRepository.deleteAll(userStories);
		this.deleteRepository.delete(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "summary", "indication", "cost", "link", "draft-mode");

		super.getResponse().addData(dataset);
	}
}
