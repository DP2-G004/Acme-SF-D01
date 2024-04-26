
package acme.features.sponsor.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceShowService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	SponsorInvoiceRepository repository;


	@Override
	public void authorise() {

		int invoiceId;
		invoiceId = super.getRequest().getData("id", int.class);

		Invoice invoice;
		invoice = this.repository.findInvoiceById(invoiceId);

		boolean status;
		status = invoice != null && super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int invoiceId;
		invoiceId = super.getRequest().getData("id", int.class);

		Invoice invoice;
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
