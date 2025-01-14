
package acme.features.authenticated.objective;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.entities.objective.Objective;

@Service
public class AuthenticatedObjectiveCreateService extends AbstractService<Authenticated, Objective> {

	@Autowired
	AuthenticatedObjectiveRepository repository;


	@Override
	public void authorise() {

		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		Objective object;

		object = new Objective();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Objective object) {

		assert object != null;
		super.bind(object, "instantiationMoment", "title", "description", "priority", "status", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final Objective object) {
		assert object != null;
	}

	@Override
	public void perform(final Objective object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Objective object) {

		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "title", "description", "priority", "status", "startDate", "endDate", "link");
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {

		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}
