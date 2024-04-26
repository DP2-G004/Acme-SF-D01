
package acme.features.authenticated.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risk.Risk;

@Service
public class AuthenticatedRiskShowService extends AbstractService<Authenticated, Risk> {

	@Autowired
	protected AuthenticatedRiskRepository adminRiskRepository;


	@Override
	public void authorise() {

		int riskId;
		riskId = super.getRequest().getData("id", int.class);

		Risk risk;
		risk = this.adminRiskRepository.findOneRiskById(riskId);

		Boolean status;
		status = risk != null && super.getRequest().getPrincipal().hasRole(Authenticated.class);

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
		dataset = super.unbind(object, "identificationDate", "impact", "probability", "description", "link");
		super.getResponse().addData(dataset);
	}

}
