
package acme.features.client.clientDashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.ClientDashboard;
import acme.roles.client.Client;

@Controller
public class ClientDashboardController extends AbstractController<Client, ClientDashboard> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected ClientDashboardShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}

}
