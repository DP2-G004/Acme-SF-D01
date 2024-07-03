
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

	Double						averageUserStoryCost;
	Double						deviationUserStoryCost;
	int							minimumUserStoryCost;
	int							maximumUserStoryCost;

	Double						averageProjectCost;
	Double						deviationProjectCost;
	int							minimumProjectCost;
	int							maximumProjectCost;

}
