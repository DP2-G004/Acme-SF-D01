
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_record.AuditRecord;
import acme.entities.audit_record.Mark;
import acme.entities.code_audit.CodeAudit;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select a from CodeAudit a where a.auditor.id = :auditorId")
	Collection<CodeAudit> findCodeAuditsByAuditor(int auditorId);

	@Query("select a from Auditor a  where a.id = :auditorId")
	Auditor findAuditorById(int auditorId);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select c from CodeAudit c")
	Collection<CodeAudit> findAllCodeAudits();

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	//Para calcular la moda
	@Query("select a.mark from AuditRecord a where a.codeAudit.id = :auditId")
	Collection<Mark> findMarksByAuditId(int auditId);

	//Para el update, mira si existe otro codeaudit con ese codigo que no sea el codeaudit que actualizamos
	@Query("select c from CodeAudit c where c.code = :code and c.id != :auditId")
	CodeAudit findDifferentCodeAuditByCodeAndId(String code, int auditId);

	@Query("select c from CodeAudit c where c.code = :code")
	CodeAudit findOneCodeAuditByCode(String code);

	@Query("select a from AuditRecord a where a.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findAuditRecordsByCodeAuditId(int codeAuditId);

}
