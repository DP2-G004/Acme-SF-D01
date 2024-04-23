
package acme.features.manager.project;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
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
	@Query("select c from Contract c where c.project.id = :id")
	Collection<Contract> findManyContractsByProjectId(int id);
	@Query("select pl from ProgressLog pl where pl.contract.id IN :ids")
	Collection<ProgressLog> findManyProgressLogsByContractIds(Set<Integer> ids);

	@Query("select ss from Sponsorship ss where ss.project.id = :id")
	Collection<Sponsorship> findManySponsorshipsByProjectId(int id);
	@Query("select i from Invoice i where i.sponsorship.id IN :ids")
	Collection<Invoice> findManyInvoicesBySponsorshipIds(Set<Integer> ids);

	@Query("select ca from CodeAudit ca where ca.project.id = :id")
	Collection<CodeAudit> findManyCodeAuditsByProjectId(int id);
	@Query("select ar from AuditRecord ar where ar.codeAudit.id IN :id")
	Collection<AuditRecord> findManyAuditsRecordsByCodeAuditsId(Set<Integer> id);

	@Query("select tm from TrainingModule tm where tm.project.id = :id")
	Collection<TrainingModule> findManyTrainingModuleByProjectId(int id);
	@Query("select ts from TrainingSession ts where ts.trainingModule.id IN :id")
	Collection<TrainingSession> findManyTrainingSessionByTrainingModuleId(Set<Integer> id);

}
