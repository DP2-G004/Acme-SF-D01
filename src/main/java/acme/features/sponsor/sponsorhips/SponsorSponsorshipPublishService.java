
package acme.features.sponsor.sponsorhips;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int id;
		Sponsorship sponsorship;
		Sponsor sponsor;

		id = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findSponsorshipById(id);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}
	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;
		super.bind(object, "code", "moment", "startDate", "endDate", "amount", "type", "email", "link", "project");
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;
		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {

		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "type", "email", "link", "project", "draftMode");

		super.getResponse().addData(dataset);
	}

}
