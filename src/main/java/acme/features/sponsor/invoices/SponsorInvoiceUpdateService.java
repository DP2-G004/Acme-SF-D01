
package acme.features.sponsor.invoices;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	SponsorInvoiceRepository repository;


	@Override
	public void authorise() {

		boolean status;
		int id;
		Invoice invoice;
		Sponsorship sponsorship;
		Sponsor sponsor;

		id = super.getRequest().getData("id", int.class);
		invoice = this.repository.findInvoiceById(id);
		sponsorship = invoice == null ? null : invoice.getSponsorship();
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = invoice != null && invoice.getDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findInvoiceById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;
		super.bind(object, "code", "dueDate", "quantity", "tax", "link");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;

		// Code already exists
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice invoice;
			invoice = this.repository.findInvoiceByCode(object.getCode());
			super.state(invoice == null || invoice.equals(object), "code", "sponsor.invoice.form.error.code-already-exists");
		}

		// Dates constraint
		if (!(super.getBuffer().getErrors().hasErrors("dueDate") || !super.getBuffer().getErrors().hasErrors("registrationTime"))) {
			super.state(MomentHelper.isLongEnough(object.getRegistrationTime(), object.getDueDate(), 1, ChronoUnit.MONTHS), "dueDate", "sponsor.invoice.form.error.duration-not-enough");
			super.state(MomentHelper.isAfter(object.getDueDate(), object.getRegistrationTime()), "dueDate", "sponsor.invoice.form.error.dueDate-must-be-after-registration");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity")) {
			// Quantity must be positive
			super.state(object.getQuantity().getAmount() > 0., "quantity", "sponsor.invoice.form.error.amount-must-be-positive");

			// Currency not supported
			List<Object> acceptedCurrencies = Arrays.asList(this.repository.findSystemCurrency().getAcceptedCurrencies().split("\\s*,\\s*"));
			super.state(acceptedCurrencies.contains(object.getQuantity().getCurrency()), "amount", "sponsor.invoice.form.error.currency-not-supported");

			// Sponsorship currency must match invoice currency
			super.state(object.getQuantity().getCurrency().equals(object.getSponsorship().getAmount().getCurrency()), "quantity", "sponsor.invoice.form.error.sponsorship-currency-must-match-invoice-currency");
		}

	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {

		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode");
		dataset.put("sponsorshipDraftMode", object.getSponsorship().isDraftMode());
		dataset.put("sponsorshipId", object.getSponsorship().getId());

		super.getResponse().addData(dataset);
	}

}
