
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	int							numProgressLogsCompletenessBelow25;
	int							numProgressLogsCompletenessBelow50Above25;
	int							numProgressLogsCompletenessBelow75Above50;
	int							numProgressLogsCompletenessAbove75;

	double						averageBudgetContracts;
	double						deviationBudgetContracts;
	int							maxBudgetContracts;
	int							minBudgetContracts;
}
