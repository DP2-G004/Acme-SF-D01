
package acme.features.manager.userstory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Controller
public class ManagerUserStoryController extends AbstractController<Manager, UserStory> {

	@Autowired
	ManagerUserStoryListMineService	listMineService;
	@Autowired
	ManagerUserStoryShowService		showService;


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
	}
}
