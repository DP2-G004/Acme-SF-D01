
package acme.features.any.audit_record;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit_record.AuditRecord;
import acme.entities.code_audit.CodeAudit;

@Repository
public interface AnyAuditRecordRepository extends AbstractRepository {

	@Query("select a from AuditRecord a where a.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findManyAuditRecordsByCodeAuditId(int codeAuditId);

	@Query("select c from CodeAudit c where c.id = :codeAuditId")
	CodeAudit findOneCodeAuditById(int codeAuditId);

	@Query("select a from AuditRecord a where a.id = :auditRecordId")
	AuditRecord findOneAuditRecordById(int auditRecordId);

	@Query("select c from CodeAudit c")
	Collection<CodeAudit> findAllCodeAudits();
}
