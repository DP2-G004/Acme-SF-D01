
package acme.features.sponsor.sponsorhips;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.sponsorship.Sponsorship;
import acme.entities.sponsorship.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {

		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Sponsorship object;
		Principal principal;
		Sponsor sponsor;

		principal = super.getRequest().getPrincipal();
		sponsor = this.repository.findSponsorById(principal.getActiveRoleId());

		object = new Sponsorship();
		object.setSponsor(sponsor);
		object.setDraftMode(true);

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

		// Code already exists
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship sponsorship;
			sponsorship = this.repository.findSponsorshipByCode(object.getCode());
			super.state(sponsorship == null || sponsorship.equals(object), "code", "sponsor.sponsorship.form.error.code-already-exists");
		}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			// Amount must be positive
			super.state(object.getAmount().getAmount() > 0., "amount", "sponsor.sponsorship.form.error.amount-must-be-positive");

			// Currency not supported
			List<Object> acceptedCurrencies = Arrays.asList(this.repository.findSystemCurrency().getAcceptedCurrencies().split("\\s*,\\s*"));
			super.state(acceptedCurrencies.contains(object.getAmount().getCurrency()), "amount", "sponsor.sponsorship.form.error.currency-not-supported");
		}

		// startDate must be after moment
		if (object.getMoment() != null && object.getStartDate() != null)
			if (!super.getBuffer().getErrors().hasErrors("startDate"))
				super.state(MomentHelper.isAfter(object.getStartDate(), object.getMoment()), "startDate", "sponsor.sponsorship.form.error.start-date-must-be-after-moment");

		if (object.getEndDate() != null && object.getStartDate() != null)
			if (!super.getBuffer().getErrors().hasErrors("endDate")) {
				// End date must be after start date
				super.state(MomentHelper.isAfter(object.getEndDate(), object.getStartDate()), "startDate", "sponsor.sponsorship.form.error.endDate-must-be-after-startDate");

				// At least 1 month between start and end
				super.state(MomentHelper.isLongEnough(object.getStartDate(), object.getEndDate(), 1, ChronoUnit.MONTHS), "startDate", "sponsor.sponsorship.form.error.duration-not-enough");
			}
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsorship object) {

		assert object != null;

		Dataset dataset;
		SelectChoices projects;
		SelectChoices types;

		types = SelectChoices.from(SponsorshipType.class, object.getType());
		projects = SelectChoices.from(this.repository.findAllProjectsDraftModeTrue(), "code", object.getProject());

		dataset = super.unbind(object, "code", "moment", "startDate", "endDate", "amount", "type", "email", "link", "draftMode");

		dataset.put("types", types);
		dataset.put("projects", projects);
		dataset.put("project", projects.getSelected().getKey());

		super.getResponse().addData(dataset);
	}

}
