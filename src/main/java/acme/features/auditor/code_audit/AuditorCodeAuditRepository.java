
package acme.features.auditor.code_audit;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.code_audit.CodeAudit;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select a from CodeAudit a where a.auditor.id = :auditorId")
	Collection<CodeAudit> findCodeAuditsByAuditor(int auditorId);

}
