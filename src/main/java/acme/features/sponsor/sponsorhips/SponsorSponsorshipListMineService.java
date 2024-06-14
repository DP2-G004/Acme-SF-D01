
package acme.features.sponsor.sponsorhips;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipListMineService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {

		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Sponsorship> sponsorships;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		sponsorships = this.repository.findSponsorshipBySponsor(principal.getActiveRoleId());

		super.getBuffer().addData(sponsorships);
	}

	@Override
	public void unbind(final Sponsorship object) {

		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "moment", "amount", "type");

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

}
