
package acme.features.auditor.audit_record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit_record.AuditRecord;
import acme.entities.audit_record.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordShowService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository repository;


	@Override
	public void authorise() {

		int id = super.getRequest().getData("id", int.class);
		AuditRecord auditRecord = this.repository.findOneAuditRecordById(id);
		boolean auditBelongsToAuditor = auditRecord.getCodeAudit().getAuditor().getId() == super.getRequest().getPrincipal().getActiveRoleId();
		boolean status = auditRecord != null && super.getRequest().getPrincipal().hasRole(auditRecord.getCodeAudit().getAuditor()) && auditBelongsToAuditor;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int id = super.getRequest().getData("id", int.class);
		AuditRecord object = this.repository.findOneAuditRecordById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		SelectChoices marks;
		Dataset dataset;

		marks = SelectChoices.from(Mark.class, object.getMark());

		dataset = super.unbind(object, "code", "startInstant", "endInstant", "mark", "link", "draftMode");
		dataset.put("marks", marks);
		dataset.put("codeAuditDraftMode", object.getCodeAudit().isDraftMode());

		super.getResponse().addData(dataset);
	}
}
