
package acme.features.manager.project_user_story_link;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.roles.Manager;

@Controller
public class ManagerProjectUserStoryLinkController extends AbstractController<Manager, ProjectUserStoryLink> {

	@Autowired
	private ManagerProjectUserStoryLinkListService		listService;
	@Autowired
	private ManagerProjectUserStoryLinkShowService		showService;
	@Autowired
	private ManagerProjectUserStoryLinkShowService		createService;
	@Autowired
	private ManagerProjectUserStoryLinkShowService		deleteService;
	@Autowired
	private ManagerProjectUserStoryLinkUpdateService	updateService;


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
	}
}
