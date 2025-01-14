
package acme.features.any.claim;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.claim.Claim;

@Service
public class ClaimListService extends AbstractService<Any, Claim> {

	@Autowired
	ClaimRepository listRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Claim> claims;

		claims = this.listRepository.findAllClaims();

		super.getBuffer().addData(claims);
	}

	@Override
	public void unbind(final Claim object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "instantiation-moment", "heading", "description", "department", "email", "link");

		super.getResponse().addData(dataset);
	}
}
