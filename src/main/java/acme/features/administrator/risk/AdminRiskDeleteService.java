
package acme.features.administrator.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risk.Risk;

@Service
public class AdminRiskDeleteService extends AbstractService<Administrator, Risk> {

	@Autowired
	protected AdminRiskRepository repository;


	@Override
	public void authorise() {

		int riskId = super.getRequest().getData("id", int.class);
		Risk risk = this.repository.findOneRiskById(riskId);

		Boolean status = risk != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int id = super.getRequest().getData("id", int.class);
		Risk object = this.repository.findOneRiskById(id);

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
	}

	@Override
	public void perform(final Risk object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "identificationDate", "impact", "probability", "description", "link");
		dataset.put("readonly", false);
		super.getResponse().addData(dataset);
	}

}
