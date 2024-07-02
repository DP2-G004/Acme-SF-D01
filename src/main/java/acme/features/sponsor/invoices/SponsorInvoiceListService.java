
package acme.features.sponsor.invoices;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


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
		Collection<Invoice> invoices;

		int id = super.getRequest().getData("sponsorshipId", int.class);
		invoices = this.repository.findAllInvoicesBySponsorshipId(id);

		super.getBuffer().addData(invoices);
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

		dataset = super.unbind(object, "code", "dueDate");
		dataset.put("totalAmount", object.getTotalAmount());
		super.getResponse().addGlobal("showCreate", object.getSponsorship().isDraftMode());
		super.getResponse().addData(dataset);
	}

}
