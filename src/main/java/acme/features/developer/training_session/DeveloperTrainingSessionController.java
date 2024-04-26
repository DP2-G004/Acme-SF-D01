
package acme.features.developer.training_session;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingSessionController extends AbstractController<Developer, TrainingSession> {

	@Autowired
	private DeveloperTrainingSessionListMineService	listMineService;

	@Autowired
	private DeveloperTrainingSessionShowService		showService;

	@Autowired
	private DeveloperTrainingSessionCreateService	createService;

	@Autowired
	private DeveloperTrainingSessionPublishService	publishService;

	@Autowired
	private DeveloperTrainingSessionDeleteService	deleteService;

	@Autowired
	private DeveloperTrainingSessionUpdateService	updateService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-by-training-module", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);

	}

}
