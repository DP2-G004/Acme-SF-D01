
package acme.features.administrator.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risk.Risk;

@Service
public class AdminRiskShowService extends AbstractService<Administrator, Risk> {

	@Autowired
	protected AdminRiskRepository adminRiskRepository;


	@Override
	public void authorise() {

		int riskId;
		riskId = super.getRequest().getData("id", int.class);

		Risk risk;
		risk = this.adminRiskRepository.findOneRiskById(riskId);

		Boolean status;
		status = risk != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int riskId;
		riskId = super.getRequest().getData("id", int.class);

		Risk risk;
		risk = this.adminRiskRepository.findOneRiskById(riskId);

		super.getBuffer().addData(risk);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "identificationDate", "impact", "probability", "description", "link");
		super.getResponse().addData(dataset);
	}

}
