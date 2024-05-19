
package acme.features.auditor.audit_record;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit_record.AuditRecord;
import acme.entities.audit_record.Mark;
import acme.entities.code_audit.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordUpdateService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository repository;


	@Override
	public void authorise() {

		int id = super.getRequest().getData("id", int.class);
		AuditRecord auditRecord = this.repository.findOneAuditRecordById(id);

		boolean status = auditRecord != null && super.getRequest().getPrincipal().hasRole(auditRecord.getCodeAudit().getAuditor());

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

		int codeAuditId = super.getRequest().getData("codeAudit", int.class);
		CodeAudit codeAudit = this.repository.findOneCodeAuditById(codeAuditId);

		object.setCodeAudit(codeAudit);
		super.bind(object, "code", "link", "mark", "startInstant", "endInstant");
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("codeAudit"))
			super.state(object.getCodeAudit().isDraftMode(), "codeAudit", "validation.audit-record.published.audit-is-published");

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode(), "draftMode", "validation.audit-record.published");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord isCodeUnique;
			isCodeUnique = this.repository.findAuditRecordByCodeDifferentId(object.getCode(), object.getId());
			super.state(isCodeUnique == null, "code", "validation.audit-record.code.duplicate");
		}
		if (!super.getBuffer().getErrors().hasErrors("startInstant") && !super.getBuffer().getErrors().hasErrors("endInstant")) {
			super.state(MomentHelper.isAfter(object.getEndInstant(), object.getStartInstant()), "startInstant", "validation.audit-record.moment.initial-after-final");

			Date minimumEnd = MomentHelper.deltaFromMoment(object.getStartInstant(), 1, ChronoUnit.HOURS);
			super.state(MomentHelper.isAfterOrEqual(object.getEndInstant(), minimumEnd), "endInstant", "validation.audit-record.moment.minimum-one-hour");
		}
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Collection<CodeAudit> allCodeAudits = this.repository.findAllCodeAudits();
		SelectChoices codeAudits = SelectChoices.from(allCodeAudits, "code", object.getCodeAudit());
		SelectChoices choices = SelectChoices.from(Mark.class, object.getMark());

		Dataset dataset = super.unbind(object, "code", "draftMode", "link", "mark", "startInstant", "endInstant");
		dataset.put("codeAudit", codeAudits.getSelected().getKey());
		dataset.put("codeaudits", codeAudits);
		dataset.put("marks", choices);
		dataset.put("mark", choices.getSelected().getKey());

		super.getResponse().addData(dataset);
	}
}
