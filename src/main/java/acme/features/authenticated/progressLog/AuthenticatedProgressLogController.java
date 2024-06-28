
package acme.features.authenticated.progressLog;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.contract.Progress;

@Controller
public class AuthenticatedProgressLogController extends AbstractController<Authenticated, Progress> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedProgressLogListByContractService listByContractService;
	
	@Autowired
	protected AuthenticatedProgressLogShowService showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-by-contract", "list", this.listByContractService);
		super.addBasicCommand("show", showService);
	}

}
