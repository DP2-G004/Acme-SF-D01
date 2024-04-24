
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.entities.code_audit.CodeAuditType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuditorDashboard extends AbstractForm {

	private static final long			serialVersionUID	= 1L;

	int									numTotalCodeAudits;

	double								averageNumAuditRecords;
	double								derivationNumAuditRecords;
	int									minNumAuditRecords;
	int									maxNumAuditRecords;

	double								averagePeriodLength;
	double								derivationPeriodLength;
	int									minPeriodLength;
	int									maxPeriodLength;

	private Map<CodeAuditType, Integer>	numAuditsPerType;
}
