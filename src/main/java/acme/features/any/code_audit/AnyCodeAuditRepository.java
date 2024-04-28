
package acme.features.any.code_audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_record.Mark;
import acme.entities.code_audit.CodeAudit;
import acme.entities.project.Project;

@Repository
public interface AnyCodeAuditRepository extends AbstractRepository {

	@Query("select c from CodeAudit c where c.draftMode = false")
	Collection<CodeAudit> findAllPublishedCodeAuditsByAuditor();

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("select a.mark from AuditRecord a where a.codeAudit.id = :auditId and a.draftMode = false")
	Collection<Mark> findMarksByAuditId(int auditId);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllPublishedProjects();
}
