
package acme.features.authenticated.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
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
		Notice n;
		n = new Notice();

		super.getBuffer().addData(n);
	}

	@Override
	public void bind(final Notice n) {
		assert n != null;
		super.bind(n, "instantiationMoment", "title", "author", "message", "email", "link");
	}

	@Override
	public void validate(final Notice object) {
		assert object != null;
	}

	@Override
	public void perform(final Notice object) {
		assert object != null;
		this.createRepository.save(object);
	}

	@Override
	public void unbind(final Notice object) {
		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "title", "author", "message", "email", "link");
		super.getResponse().addData(dataset);
	}
}
