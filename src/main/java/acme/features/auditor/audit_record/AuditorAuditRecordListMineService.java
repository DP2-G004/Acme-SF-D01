
package acme.features.auditor.audit_record;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_record.AuditRecord;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordListMineService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository repository;


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		Principal principal = super.getRequest().getPrincipal();
		Collection<AuditRecord> objects = this.repository.findManyAuditRecordsByAuditorId(principal.getActiveRoleId());

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "mark");

		super.getResponse().addData(dataset);
	}
}
