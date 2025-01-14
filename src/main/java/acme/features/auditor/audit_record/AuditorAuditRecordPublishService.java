
package acme.features.auditor.audit_record;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit_record.AuditRecord;
import acme.entities.audit_record.Mark;
import acme.entities.code_audit.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordPublishService extends AbstractService<Auditor, AuditRecord> {

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

		super.bind(object, "publish");
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		object.setDraftMode(false);
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
