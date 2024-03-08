
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	int							totalNumberOfPrincipalsWithEachRole;

	double						ratioOfNoticesWithBothAnEmailAddressAndALink;

	double						ratiosOfCriticalAndNonCriticalObjectives;

	double						averageDeviationOfTheValueInTheRisks;
	double						minimumDeviationOfTheValueInTheRisks;
	double						maximumDeviationOfTheValueInTheRisks;
	double						standardDeviationOfTheValueInTheRisks;

	double						averageDeviationOfTheNumberOfClaimsPostedOverTheLast10weeks;
	double						minimumDeviationOfTheNumberOfClaimsPostedOverTheLast10weeks;
	double						maximumDeviationOfTheNumberOfClaimsPostedOverTheLast10weeks;
	double						standardDeviationOfTheNumberOfClaimsPostedOverTheLast10weeks;

}
