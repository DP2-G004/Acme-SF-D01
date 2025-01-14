
package acme.features.authenticated.objective;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.objective.Objective;

@Service
public class AuthenticatedObjectiveListService extends AbstractService<Authenticated, Objective> {

	// Internal state ----------------------------------------

	@Autowired
	private AuthenticatedObjectiveRepository repository;

	// AbstractService interface -----------------------------


	@Override
	public void authorise() {

		boolean status;
		// We check the user is logged as Authenticated
		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Collection<Objective> objects;
		objects = this.repository.findAllObjective();

		super.getBuffer().addData(objects);

	}

	@Override
	public void unbind(final Objective object) {

		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "title", "description", "status", "link");
		super.getResponse().addData(dataset);
	}

}
