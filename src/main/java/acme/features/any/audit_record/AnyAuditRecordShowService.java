
package acme.features.any.audit_record;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit_record.AuditRecord;
import acme.entities.audit_record.Mark;
import acme.entities.code_audit.CodeAudit;

@Service
public class AnyAuditRecordShowService extends AbstractService<Any, AuditRecord> {

	@Autowired
	private AnyAuditRecordRepository repository;


	@Override
	public void authorise() {

		int id = super.getRequest().getData("id", int.class);
		AuditRecord auditRecord = this.repository.findOneAuditRecordById(id);

		boolean status = auditRecord != null;

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

		Collection<CodeAudit> unpublishedCodeAudits = this.repository.findAllCodeAudits();
		SelectChoices codeAudits = SelectChoices.from(unpublishedCodeAudits, "code", object.getCodeAudit());
		SelectChoices choices = SelectChoices.from(Mark.class, object.getMark());

		Dataset dataset = super.unbind(object, "code", "draftMode", "link", "mark", "startInstant", "endInstant");
		dataset.put("codeAudit", codeAudits.getSelected().getKey());
		dataset.put("codeaudits", codeAudits);
		dataset.put("marks", choices);

		super.getResponse().addData(dataset);
	}
}
