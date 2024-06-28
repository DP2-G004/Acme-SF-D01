
package acme.features.authenticated.notice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.notice.Notice;

@Service
public class NoticeCreateService extends AbstractService<Authenticated, Notice> {

	@Autowired
	NoticeRepository createRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		Notice object;
		String username;
		String name;
		String author;
		int accountId;

		accountId = this.getRequest().getPrincipal().getAccountId();
		name = this.createRepository.findOneUserAccountById(accountId).getIdentity().getFullName();
		username = super.getRequest().getPrincipal().getUsername();
		author = username + "-" + name;

		Date currentMoment = MomentHelper.getCurrentMoment();
		Date updateMoment = new Date(currentMoment.getTime() - 1000); //Substracts one second to ensure the moment is in the past

		object = new Notice();
		object.setLastInstantiationMoment(updateMoment);
		object.setAuthor(author);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Notice n) {
		assert n != null;
		super.bind(n, "title", "message", "email", "link");
	}

	@Override
	public void validate(final Notice object) {
		assert object != null;

		boolean confirmation;

		confirmation = super.getRequest().getData("confirmation", boolean.class);
		super.state(confirmation, "confirmation", "javax.validation.constraints.AssertTrue.message");
	}

	@Override
	public void perform(final Notice object) {
		assert object != null;
		this.createRepository.save(object);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "title", "lastInstantiationMoment", "message", "email", "link", "author");
		dataset.put("confirmation", false);

		super.getResponse().addData(dataset);
	}
}
