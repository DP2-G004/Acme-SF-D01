
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
public class AuditorAuditRecordDeleteService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository repository;


	@Override
	public void authorise() {

		int id = super.getRequest().getData("id", int.class);
		AuditRecord auditRecord = this.repository.findOneAuditRecordById(id);

		boolean status = auditRecord != null && auditRecord.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditRecord.getCodeAudit().getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int id = super.getRequest().getData("id", int.class);
		AuditRecord object = this.repository.findOneAuditRecordById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		String markString = this.getRequest().getData("mark", String.class);
		Mark mark = Mark.parseAuditMark(markString);
		//object.setMark(mark);

		object.setMark(mark);
		super.bind(object, "code", "startInstant", "endInstant", "link");
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("codeAudit"))
			super.state(object.getCodeAudit().isDraftMode(), "codeAudit", "validation.auditrecord.published.audit-is-published");

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode(), "draftMode", "validation.auditrecord.draftMode");

	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		SelectChoices marks;
		Dataset dataset;

		marks = SelectChoices.from(Mark.class, object.getMark());

		dataset = super.unbind(object, "code", "startInstant", "mark", "endInstant", "link", "draftMode");
		dataset.put("marks", marks);
		//dataset.put("mark", marks.getSelected().getKey());

		super.getResponse().addData(dataset);
	}
}
