
package acme.features.administrator.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risk.Risk;

@Service
public class AdminRiskCreateService extends AbstractService<Administrator, Risk> {

	@Autowired
	AdminRiskRepository repository;


	@Override
	public void authorise() {

		Boolean status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Risk object = new Risk();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Risk object) {
		assert object != null;

		super.bind(object, "code", "identificationDate", "impact", "probability", "description", "link");
	}

	@Override
	public void validate(final Risk object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Risk r;
			r = this.repository.findRiskByCode(object.getCode());
			super.state(r == null, "code", "administrator.risk.form.error.duplicated-code");
		}

	}

	@Override
	public void perform(final Risk object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "identificationDate", "impact", "probability", "description", "link");
		super.getResponse().addData(dataset);
	}

}
