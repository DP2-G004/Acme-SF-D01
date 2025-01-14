
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Sponsorship> sponsorships;

		sponsorships = this.repository.findAllPublishedSponsorships();

		super.getBuffer().addData(sponsorships);
	}

	@Override
	public void unbind(final Sponsorship object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "moment", "amount", "type");

		super.getResponse().addData(dataset);
	}

}
