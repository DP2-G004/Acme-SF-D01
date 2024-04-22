
package acme.features.any.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.claim.Claim;

@Service
public class ClaimCreateService extends AbstractService<Any, Claim> {

	@Autowired
	private ClaimRepository createRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		Claim c = new Claim();
		c.setConfirmation(false);
		super.getBuffer().addData(c);
	}
	@Override
	public void bind(final Claim c) {
		assert c != null;
		super.bind(c, "code", "instantiationMoment", "heading", "description", "department", "email", "link");
	}
	@Override
	public void validate(final Claim object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("confirmation"))
			super.state(!object.isConfirmation(), "draft", "manager.project.form.error.draft");
	}
	@Override
	public void perform(final Claim object) {
		assert object != null;
		object.setConfirmation(true);
		this.createRepository.save(object);
	}
	@Override
	public void unbind(final Claim object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "instantiationMoment", "heading", "description", "department", "email", "link", "confirmation");
		super.getResponse().addData(dataset);
	}
}
