
package acme.features.sponsor.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceShowService extends AbstractService<Sponsor, Invoice> {

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
		status = invoice != null && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int invoiceId;
		Invoice invoice;

		invoiceId = super.getRequest().getData("id", int.class);
		invoice = this.repository.findInvoiceById(invoiceId);

		super.getBuffer().addData(invoice);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode");
		dataset.put("totalAmount", object.getTotalAmount());
		dataset.put("sponsorshipDraftMode", object.getSponsorship().isDraftMode());

		super.getResponse().addData(dataset);
	}

}
