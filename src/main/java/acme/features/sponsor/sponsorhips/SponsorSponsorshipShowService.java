
package acme.features.sponsor.sponsorhips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {

		int sponsorshipId;
		sponsorshipId = super.getRequest().getData("id", int.class);

		Sponsorship sponsorship;
		sponsorship = this.repository.findSponsorshipById(sponsorshipId);

		boolean status;
		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int sponsorshipId;
		sponsorshipId = super.getRequest().getData("id", int.class);

		Sponsorship sponsorship;
		sponsorship = this.repository.findSponsorshipById(sponsorshipId);

		super.getBuffer().addData(sponsorship);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "type", "email", "link");
		super.getResponse().addData(dataset);
	}
}
