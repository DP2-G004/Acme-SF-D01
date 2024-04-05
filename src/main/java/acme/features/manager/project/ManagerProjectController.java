
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
	ManagerProjectListMineService	listMineService;
	@Autowired
	ManagerProjectShowService		showService;


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("list-mine", this.listMineService);
		super.addBasicCommand("show", this.showService);
	}
}
