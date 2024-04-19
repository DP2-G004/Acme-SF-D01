
package acme.features.any.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.claim.Claim;

@Service
public class ClaimPublishService extends AbstractService<Any, Claim> {

	@Autowired
	ClaimRepository publishRepository;


	@Override
	public void authorise() {
		Boolean status;
		int id;
		Claim c;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		id = super.getRequest().getData("id", int.class);
		c = this.publishRepository.findClaimByAnyone(id);
		status = c != null && super.getRequest().getPrincipal().hasRole(principal.getActiveRole());

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		Claim object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.publishRepository.findClaimByAnyone(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Claim c) {
		assert c != null;
		super.bind(c, "code", "instantiation-moment", "heading", "description", "department", "email", "link");
	}

	@Override
	public void validate(final Claim object) {
		assert object != null;
		//ask who validates the publish action
	}

	@Override
	public void perform(final Claim object) {
		assert object != null;
		this.publishRepository.save(object);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "instantiation-moment", "heading", "description", "department", "email", "link");
		super.getResponse().addData(dataset);
	}
}
