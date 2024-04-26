
package acme.features.authenticated.notice;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.notice.Notice;

@Service
public class NoticeListService extends AbstractService<Authenticated, Notice> {

	@Autowired
	NoticeRepository listRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Notice> claims;

		claims = this.listRepository.findAllNotices();

		super.getBuffer().addData(claims);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "instantiationMoment", "title", "author", "message", "email", "link");

		super.getResponse().addData(dataset);
	}
}
