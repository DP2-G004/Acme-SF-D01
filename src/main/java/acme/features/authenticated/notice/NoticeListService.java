
package acme.features.authenticated.notice;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.notice.Notice;

@Service
public class NoticeListService extends AbstractService<Authenticated, Notice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private NoticeRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Notice> objects;
		Date thirtyDaysAgo;

		thirtyDaysAgo = MomentHelper.deltaFromCurrentMoment(-30, ChronoUnit.DAYS);

		objects = this.repository.findNoticesBeforeTime(thirtyDaysAgo);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Notice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "lastInstantiationMoment", "title", "message", "email", "link", "author");

		super.getResponse().addData(dataset);
	}

}
