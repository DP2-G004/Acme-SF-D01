
package acme.features.auditor.audit_record;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.PrincipalHelper;
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
		int auditorId = super.getRequest().getPrincipal().getActiveRoleId();
		CodeAudit object = auditRecord.getCodeAudit();

		boolean status = auditRecord != null && super.getRequest().getPrincipal().hasRole(auditRecord.getCodeAudit().getAuditor()) && object.getAuditor().getId() == auditorId;

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
		int id = super.getRequest().getData("id", int.class);
		AuditRecord auditRecord = this.repository.findOneAuditRecordById(id);
		CodeAudit existingCodeAudit = auditRecord.getCodeAudit();

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord isCodeUnique;
			isCodeUnique = this.repository.findAuditRecordByCodeDifferentId(object.getCode(), object.getId());
			super.state(isCodeUnique == null, "code", "validation.audit-record.code.duplicate");
		}

		if (!super.getBuffer().getErrors().hasErrors("startInstant")) {
			Date minDate = existingCodeAudit.getExecutionDate();
			//Date maxDate = new Date(122, 6, 29, 23, 01); //29/07/2022 23:01
			super.state(MomentHelper.isAfterOrEqual(object.getStartInstant(), minDate), "startInstant", "auditor.auditRecord.form.error.startInstan-out-of-range");
		}

		if (!super.getBuffer().getErrors().hasErrors("endInstant")) {
			Date minDate = MomentHelper.deltaFromMoment(existingCodeAudit.getExecutionDate(), 1, ChronoUnit.HOURS);
			//Date maxDate = new Date(122, 6, 30, 00, 01); //30/07/2022 00:01
			super.state(MomentHelper.isAfterOrEqual(object.getEndInstant(), minDate), "endInstant", "auditor.auditRecord.form.error.endDate-out-of-range");
		}

		if (!(super.getBuffer().getErrors().hasErrors("startInstant") || super.getBuffer().getErrors().hasErrors("endInstant"))) {
			Date minimunDuration;
			minimunDuration = MomentHelper.deltaFromMoment(object.getStartInstant(), 1, ChronoUnit.HOURS);
			super.state(MomentHelper.isAfterOrEqual(object.getEndInstant(), minimunDuration), "endInstant", "auditor.auditRecord.form.error.invalid-dates");
		}

		if (!super.getBuffer().getErrors().hasErrors("codeAudit"))
			super.state(existingCodeAudit != null && existingCodeAudit.isDraftMode() && !existingCodeAudit.getProject().isDraftMode(), "codeAudit", "auditor.codeAudit.form.error.codeAudit-draft-mode-is-set-to-false");
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		this.repository.save(object);
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

	@Override
	public void onSuccess() {
		//El framework usa post tambien para el update 
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
