
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	int							totalNumberOfTrainingModulesWithAnUpdateMoment;

	int							totalNumberOfTrainingSessionsWithALink;

	double						averageTimeOfTheTrainingModules;
	double						deviationTimeOfTheTrainingModules;
	double						minimumTimeOfTheTrainingModules;
	double						maximumTimeOfTheTrainingModules;

}
