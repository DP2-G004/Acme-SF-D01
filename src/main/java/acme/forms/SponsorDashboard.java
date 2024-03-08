
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	int							numInvoicesWithTaxLessOrEqualThan21;
	int							numSponshorshipWithLink;
	int							minSponsorshipAmount;
	int							maxSponsorshipAmount;
	int							minInvoiceQuantity;
	int							maxInvoiceQuantity;

	double						avgSponsorshipAmount;
	double						deviationSponsorshipAmount;
	double						avgInvoiceQuantity;
	double						deviationInvoiceQuantity;

}
