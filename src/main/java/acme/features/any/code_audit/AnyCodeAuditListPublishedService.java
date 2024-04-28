
package acme.features.any.code_audit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.code_audit.CodeAudit;

@Service
public class AnyCodeAuditListPublishedService extends AbstractService<Any, CodeAudit> {

	@Autowired
	private AnyCodeAuditRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;

		objects = this.repository.findAllPublishedCodeAuditsByAuditor();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "type", "link", "draft-mode");
		dataset.put("project", object.getProject().getCode());

		super.getResponse().addData(dataset);
	}
}
