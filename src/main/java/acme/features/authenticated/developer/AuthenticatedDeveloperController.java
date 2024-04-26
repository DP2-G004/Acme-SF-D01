
package acme.features.authenticated.developer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.roles.Developer;

@Controller
public class AuthenticatedDeveloperController extends AbstractController<Authenticated, Developer> {

	@Autowired
	private AuthenticatedDeveloperCreateService createService;


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("create", this.createService);
	}

}
