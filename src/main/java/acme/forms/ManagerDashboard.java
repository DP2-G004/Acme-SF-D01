
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {
	//Serialisation identifier

	private static final long	serialVersionUID	= 1L;

	//Attributes

	int							totalMustPriorityUserStories;
	int							totalShouldPriorityUserStories;
	int							totalCouldPriorityUserStories;
	int							totalWontPriorityUserStories;

	double						averageUserStoryCost;
	double						deviationUserStoryCost;
	double						minimumUserStoryCost;
	double						maximumUserStoryCost;

	double						averageProjectCost;
	double						deviationProjectCost;
	double						minimumProjectCost;
	double						maximumProjectCost;

}
