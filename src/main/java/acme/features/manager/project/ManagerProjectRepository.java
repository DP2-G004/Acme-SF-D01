
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_record.AuditRecord;
import acme.entities.code_audit.CodeAudit;
import acme.entities.contract.Contract;
import acme.entities.contract.Progress;
import acme.entities.invoice.Invoice;
import acme.entities.project.Project;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findManagerById(int managerId);

	@Query("select p from Project p where p.code = :projectCode")
	Project findProjectByCode(String projectCode);

	@Query("select pusl from ProjectUserStoryLink pusl where pusl.project.id = :projectId")
	Collection<ProjectUserStoryLink> findLinkedUserStoriesByProjectId(int projectId);

	@Query("select pusl.userStory from ProjectUserStoryLink pusl where pusl.project.id = :projectId")
	Collection<UserStory> findUserStoriesByProjectId(int projectId);
	//
	//
	@Query("select c from CodeAudit c where c.project.id = :projectId")
	Collection<CodeAudit> findAllCodeAuditsFromProjectId(int projectId);

	@Query("select a from AuditRecord a where a.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findAllAuditRecordsFromCodeAuditId(int codeAuditId);

	@Query("select c from Contract c where c.project.id = :projectId")
	Collection<Contract> findAllContractsByProjectId(int projectId);

	@Query("select p from Progress p where p.contract.id = :contractId")
	Collection<Progress> findAllProgressLogsByContractId(int contractId);

	@Query("select s from Sponsorship s where s.project.id = :projectId")
	Collection<Sponsorship> findAllSponsorshipsByProjectId(int projectId);

	@Query("select i from Invoice i where i.sponsorship.id = :sponsorshipId")
	Collection<Invoice> findAllInvoicesBySponsorshipId(int sponsorshipId);

	@Query("select tm from TrainingModule tm where tm.project.id = :projectId")
	Collection<TrainingModule> findAllTrainingModulesByProjectId(int projectId);

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :tmId")
	Collection<TrainingSession> findAllTrainingSessionsByTrainingModuleId(int tmId);
}
