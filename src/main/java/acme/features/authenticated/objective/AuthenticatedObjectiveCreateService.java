
package acme.features.authenticated.objective;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.objective.Objective;

@Service
public class AuthenticatedObjectiveCreateService extends AbstractService<Authenticated, Objective> {

	@Autowired
	AuthenticatedObjectiveRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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
		super.unbind(object, "instantiationMoment", "title", "description", "priority", "status", "startDate", "endDate", "link");
	}

	@Override
	public void validate(final Objective object) {

		assert object != null;

		//custom validates
		//		if(!super.getBuffer().getErrors().hasErrors("property")) { // e.g.: salary
		//			super.state(object.getProperty() < 0, "property", "authenticated.objective.form.error.error-name");
		//		}
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

}
