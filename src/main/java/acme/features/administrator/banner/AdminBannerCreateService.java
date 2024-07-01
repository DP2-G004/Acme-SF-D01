
package acme.features.administrator.banner;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdminBannerCreateService extends AbstractService<Administrator, Banner> {

	@Autowired
	AdminBannerRepository adminBannerRepository;


	@Override
	public void authorise() {

		Boolean status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Banner object = new Banner();

		object.setSlogan("");
		object.setLink("");

		object.setPictureLink("");

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;
		//Date initialLastInstantiationMoment = object.getInstantiationMoment();
		//object.setDisplayMoment(initialLastInstantiationMoment);
		super.bind(object, "instantiationMoment", "displayMoment", "endOfDisplay", "pictureLink", "slogan", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("displayMoment") && !super.getBuffer().getErrors().hasErrors("endOfDisplay")) {
			super.state(MomentHelper.isAfterOrEqual(object.getDisplayMoment(), object.getInstantiationMoment()), "displayMoment", "administrator.banner.form.error.last-instantiation-moment.invalid-date");
			super.state(MomentHelper.isAfter(object.getEndOfDisplay(), object.getDisplayMoment()), "displayMoment", "administrator.banner.form.error.last-instantiation-moment.invalid");

			//if (!super.getBuffer().getErrors().hasErrors("endOfInstantiation")) {

			//Display period must last for at least one week
			Date maximumDeadline = MomentHelper.deltaFromMoment(object.getDisplayMoment(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getEndOfDisplay(), maximumDeadline), "endOfDisplay", "administrator.banner.form.error.period.invalid");
			//}
		}
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;
		this.adminBannerRepository.save(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "instantiationMoment", "displayMoment", "endOfDisplay", "pictureLink", "slogan", "link");
		super.getResponse().addData(dataset);
	}

}
