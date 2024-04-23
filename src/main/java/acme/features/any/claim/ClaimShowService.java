
package acme.features.any.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.claim.Claim;

@Service
public class ClaimShowService extends AbstractService<Any, Claim> {

	@Autowired
	ClaimRepository showRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Claim claim;
		int id = super.getRequest().getData("id", int.class);
		claim = this.showRepository.findClaimByAnyone(id);

		super.getBuffer().addData(claim);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiation-moment", "heading", "description", "department", "email", "link", "publishIndication");

		super.getResponse().addData(dataset);
	}

}
