
package acme.features.authenticated.objective;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.objective.Objective;

@Service
public class AuthenticatedObjectiveShowService extends AbstractService<Authenticated, Objective> {
	// Internal state ----------------------------------------

	@Autowired
	private AuthenticatedObjectiveRepository repository;

	// AbstractService interface -----------------------------


	@Override
	public void authorise() {

		int objectiveId;
		objectiveId = super.getRequest().getData("id", int.class);

		Objective objective;
		objective = this.repository.findObjectiveById(objectiveId);

		boolean status;
		status = objective != null && super.getRequest().getPrincipal().hasRole(Authenticated.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int objectiveId;
		objectiveId = super.getRequest().getData("id", int.class);

		Objective objective;
		objective = this.repository.findObjectiveById(objectiveId);

		super.getBuffer().addData(objective);

	}

	@Override
	public void unbind(final Objective object) {

		System.out.println("BBBBBBBBBB");

		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "title", "description", "priority", "status", "startDate", "endDate", "link");
		super.getResponse().addData(dataset);
	}

}
