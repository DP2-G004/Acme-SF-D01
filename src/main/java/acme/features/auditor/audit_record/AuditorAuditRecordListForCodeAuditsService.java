
package acme.features.auditor.audit_record;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_record.AuditRecord;
import acme.entities.code_audit.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListForCodeAuditsService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository repository;


	@Override
	public void authorise() {

		int id = super.getRequest().getData("codeAuditId", int.class);
		CodeAudit codeAudit = this.repository.findOneCodeAuditById(id);

		Auditor auditor = codeAudit == null ? null : codeAudit.getAuditor();
		boolean status = codeAudit != null && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int codeAuditId = super.getRequest().getData("codeAuditId", int.class);
		Collection<AuditRecord> objects = this.repository.findManyAuditRecordsByCodeAuditId(codeAuditId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Collection<AuditRecord> objects) {
		assert objects != null;

		int codeAuditId;

		codeAuditId = this.getRequest().getData("codeAuditId", int.class);
		Boolean codeAuditDraftMode = this.repository.findOneCodeAuditById(codeAuditId).isDraftMode();
		super.getResponse().addGlobal("codeAuditId", codeAuditId);
		super.getResponse().addGlobal("codeAuditDraftMode", codeAuditDraftMode);

	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "mark");

		super.getResponse().addData(dataset);
	}

}
