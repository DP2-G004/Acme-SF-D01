
package acme.features.any.audit_record;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_record.AuditRecord;
import acme.entities.code_audit.CodeAudit;

@Service
public class AnyAuditRecordListForPublishedCodeAuditService extends AbstractService<Any, AuditRecord> {

	@Autowired
	private AnyAuditRecordRepository repository;


	@Override
	public void authorise() {

		int id = super.getRequest().getData("codeAuditId", int.class);
		CodeAudit codeAudit = this.repository.findOneCodeAuditById(id);

		boolean status = codeAudit != null;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int codeAuditId = super.getRequest().getData("codeAuditId", int.class);
		Collection<AuditRecord> objects = this.repository.findManyAuditRecordsByCodeAuditId(codeAuditId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "mark");

		super.getResponse().addData(dataset);
	}
}
