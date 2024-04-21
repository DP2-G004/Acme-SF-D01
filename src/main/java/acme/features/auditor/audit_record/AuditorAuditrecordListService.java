
package acme.features.auditor.audit_record;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audit_record.AuditRecord;
import acme.roles.Auditor;

public class AuditorAuditrecordListService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		//CORREGIR
		Collection<AuditRecord> objects = this.repository.findAllAuditRecords();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "lastInstantiationMoment", "endOfInstantiation", "pictureLink", "slogan", "link");
		super.getResponse().addData(dataset);
	}

}
