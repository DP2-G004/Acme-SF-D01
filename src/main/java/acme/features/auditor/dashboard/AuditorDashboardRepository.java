
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.code_audit.CodeAudit;
import acme.entities.code_audit.CodeAuditType;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select c from CodeAudit c where c.auditor.id = :auditorId and c.draftMode = false")
	Collection<CodeAudit> findPublishedCodeAudits(int auditorId);

	default Map<CodeAuditType, Integer> totalTypes(final int auditorId) {
		Map<CodeAuditType, Integer> result = new EnumMap<>(CodeAuditType.class);
		Collection<CodeAudit> CodeAudits = this.findPublishedCodeAudits(auditorId);

		for (CodeAuditType type : CodeAuditType.values())
			result.put(type, 0);
		for (CodeAudit c : CodeAudits)
			result.put(c.getType(), result.get(c.getType()) + 1);
		return result;
	}

	@Query("select (select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId and a.draftMode = false")
	Collection<Double> auditingRecordsPerAudit(int auditorId);

	@Query("select max(select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId and a.draftMode = false")
	Integer maxAuditingRecords(int auditorId);

	@Query("select min(select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId and a.draftMode = false")
	Integer minAuditingRecords(int auditorId);

	@Query("select max(time_to_sec(timediff(ar.endInstant, ar.startInstant)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId and ar.codeAudit.draftMode = false")
	Double maximumRecordPeriod(int auditorId);

	@Query("select avg(time_to_sec(timediff(ar.endInstant, ar.startInstant)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId and ar.codeAudit.draftMode = false")
	Double averageRecordPeriod(int auditorId);

	@Query("select avg(select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId and a.draftMode = false")
	Double averageAuditingRecords(int auditorId);

	@Query("select stddev(time_to_sec(timediff(ar.endInstant, ar.startInstant)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId and ar.codeAudit.draftMode = false")
	Double deviationRecordPeriod(int auditorId);

	@Query("select min(time_to_sec(timediff(ar.endInstant, ar.startInstant)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId and ar.codeAudit.draftMode = false")
	Double minimumRecordPeriod(int auditorId);

	@Query("select count(c) from CodeAudit c where c.auditor.id = :auditorId and c.draftMode = false")
	int totalCodeAudits(int auditorId);
}
