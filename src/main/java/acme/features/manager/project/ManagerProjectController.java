
package acme.features.manager.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.Project;
import acme.roles.Manager;

@Controller
public class ManagerProjectController extends AbstractController<Manager, Project> {

	@Autowired
	private ManagerProjectListMineService	listMineService;
	@Autowired
	private ManagerProjectShowService		showService;
	@Autowired
	private ManagerProjectCreateService		createService;
	@Autowired
	private ManagerProjectUpdateService		updateService;
	@Autowired
	private ManagerProjectDeleteService		deleteService;
	@Autowired
	private ManagerProjectPublishService	publishService;


	@PostConstruct
	public void initialise() {
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
	}
}
