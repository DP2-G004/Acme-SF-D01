
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	int							numTotalCodeAudits;

	double						averageNumAuditRecords;
	double						derivationNumAuditRecords;
	int							minNumAuditRecords;
	int							maxNumAuditRecords;

	double						averagePeriodLength;
	double						derivationPeriodLength;
	int							minPeriodLength;
	int							maxPeriodLength;
}
