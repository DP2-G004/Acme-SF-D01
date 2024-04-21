
package acme.features.administrator.risk;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.risk.Risk;

@Service
public class AdminRiskListService extends AbstractService<Administrator, Risk> {

	@Autowired
	protected AdminRiskRepository adminRiskRepository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Risk> objects;
		objects = this.adminRiskRepository.findAllRisks();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "impact", "probability", "description");
		super.getResponse().addData(dataset);
	}
}
