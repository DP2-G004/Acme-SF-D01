
package acme.features.authenticated.contract;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.entities.contract.Contract;

@Controller
public class AuthenticatedContractController extends AbstractController<Authenticated, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedContractListService	listService;
	@Autowired
	protected AuthenticatedContractShowService	showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
