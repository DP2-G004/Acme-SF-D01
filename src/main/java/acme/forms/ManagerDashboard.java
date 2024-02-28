
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

	Integer						totalMustPriorityUserStories;
	Integer						totalShouldPriorityUserStories;
	Integer						totalCouldPriorityUserStories;
	Integer						totalWontPriorityUserStories;

	Double						averageUserStoryCost;
	Double						deviationUserStoryCost;
	Double						minimumUserStoryCost;
	Double						maximumUserStoryCost;

	Double						averageProjectCost;
	Double						deviationProjectCost;
	Double						minimumProjectCost;
	Double						maximumProjectCost;

}
