
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
	//Algunos valores son Integer para admitir valores nulos mientras no se pueda calcular nada
	Integer								numTotalCodeAudits;

	double								averageNumAuditRecords;
	double								deviationNumAuditRecords;
	Integer								minNumAuditRecords;
	Integer								maxNumAuditRecords;

	double								averagePeriodLength;
	double								deviationPeriodLength;
	double								minPeriodLength;
	double								maxPeriodLength;

	private Map<CodeAuditType, Integer>	numAuditsPorTipo;
}
