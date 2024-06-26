
package acme.features.sponsor.invoices;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceCreateService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int sponsorshipId;
		Sponsorship sponsorship;
		Sponsor sponsor;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		sponsorship = this.repository.findSponsorshipById(sponsorshipId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Invoice object;
		Sponsorship sponsorship;
		int sponsorshipId;

		sponsorshipId = this.getRequest().getData("sponsorshipId", int.class);
		sponsorship = this.repository.findSponsorshipById(sponsorshipId);

		object = new Invoice();
		object.setDraftMode(true);
		object.setSponsorship(sponsorship);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {

		assert object != null;
		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
		// Code constraint
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice invoice;
			invoice = this.repository.findInvoiceByCode(object.getCode());
			super.state(invoice == null, "code", "sponsor.invoice.form.error.code");
		}
		// Dates constraint
		if (!(super.getBuffer().getErrors().hasErrors("dueDate") || super.getBuffer().getErrors().hasErrors("registrationTime"))) {
			Date minimunDuration;
			minimunDuration = MomentHelper.deltaFromMoment(object.getRegistrationTime(), 30, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), minimunDuration), "dueDate", "sponsor.invoice.form.error.invalid-due-date");
		}
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Collection<Invoice> objects) {
		assert objects != null;

		int sponsorshipId;

		sponsorshipId = this.getRequest().getData("sponsorshipId", int.class);

		super.getResponse().addGlobal("sponsorshipId", sponsorshipId);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode");
		dataset.put("sponsorshipDraftMode", object.getSponsorship().isDraftMode());
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {

		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
