
package acme.features.administrator.risk;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risk.Risk;

@Service
public class AdminRiskUpdateService extends AbstractService<Administrator, Risk> {

	@Autowired
	AdminRiskRepository repository;


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

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Risk r;
			Collection<Risk> risks;
			boolean b = false;
			int id;
			id = super.getRequest().getData("id", int.class);
			r = this.repository.findOneRiskById(id);
			risks = this.repository.findAllRisks();
			for (Risk risk : risks)
				if (risk.getCode().equals(object.getCode()))
					b = true;
			if (!r.getCode().equals(object.getCode()) && b)
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
		dataset.put("readonly", false);
		super.getResponse().addData(dataset);
	}

}
