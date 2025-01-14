
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
	private ManagerUserStoryListService				listService;
	@Autowired
	private ManagerUserStoryListByProjectService	listByProject;
	@Autowired
	private ManagerUserStoryShowService				showService;
	@Autowired
	private ManagerUserStoryCreateService			createService;
	@Autowired
	private ManagerUserStoryUpdateService			updateService;
	@Autowired
	private ManagerUserStoryDeleteService			deleteService;
	@Autowired
	private ManagerUserStoryPublishService			publishService;


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("list-by-project", "list", this.listByProject);
		super.addBasicCommand("list", this.listService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
