
package acme.features.sponsor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {

	@Autowired
	private SponsorDashboardRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SponsorDashboard dashboard;
		Integer sponsorId;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		dashboard = new SponsorDashboard();

		int numInvoicesWithTaxLessOrEqualThan21 = this.repository.numOfInvoicesLessThan21Tax(sponsorId);
		int numSponshorshipWithLink = this.repository.numOfLinkedSponsorships(sponsorId);

		double minInvoiceQuantity = this.repository.minQuantityInvoice(sponsorId);
		double maxInvoiceQuantity = this.repository.maxQuantityInvoice(sponsorId);
		double minSponsorshipAmount = this.repository.minAmountSponsorship(sponsorId);
		double maxSponsorshipAmount = this.repository.maxAmountSponsorship(sponsorId);

		double avgSponsorshipAmount = this.repository.averageAmountSponsorship(sponsorId);
		double deviationSponsorshipAmount = this.repository.deviationAmountSponsorship(sponsorId);
		double avgInvoiceQuantity = this.repository.averageQuantityInvoice(sponsorId);
		double deviationInvoiceQuantity = this.repository.deviationQuantityInvoice(sponsorId);

		dashboard.setNumInvoicesWithTaxLessOrEqualThan21(numInvoicesWithTaxLessOrEqualThan21);
		dashboard.setNumSponshorshipWithLink(numSponshorshipWithLink);

		dashboard.setMinInvoiceQuantity(minInvoiceQuantity);
		dashboard.setMaxInvoiceQuantity(maxInvoiceQuantity);
		dashboard.setMinSponsorshipAmount(minSponsorshipAmount);
		dashboard.setMaxSponsorshipAmount(maxSponsorshipAmount);

		dashboard.setAvgSponsorshipAmount(avgSponsorshipAmount);
		dashboard.setDeviationSponsorshipAmount(deviationSponsorshipAmount);
		dashboard.setAvgInvoiceQuantity(avgInvoiceQuantity);
		dashboard.setDeviationInvoiceQuantity(deviationInvoiceQuantity);

		super.getBuffer().addData(dashboard);

	}
	@Override
	public void unbind(final SponsorDashboard object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "numInvoicesWithTaxLessOrEqualThan21", "numSponshorshipWithLink", "minInvoiceQuantity", "maxInvoiceQuantity", "minSponsorshipAmount", "maxSponsorshipAmount", "avgSponsorshipAmount", "deviationSponsorshipAmount",
			"avgInvoiceQuantity", "deviationInvoiceQuantity");
		super.getResponse().addData(dataset);
	}
}