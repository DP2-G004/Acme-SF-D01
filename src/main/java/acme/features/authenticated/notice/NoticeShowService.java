
package acme.features.authenticated.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.notice.Notice;

@Service
public class NoticeShowService extends AbstractService<Authenticated, Notice> {

	@Autowired
	NoticeRepository showRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Notice notice;
		int id = super.getRequest().getData("id", int.class);
		notice = this.showRepository.findNoticeByAnyone(id);

		super.getBuffer().addData(notice);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "lastInstantiationMoment", "title", "author", "message", "email", "link");

		super.getResponse().addData(dataset);
	}

}
