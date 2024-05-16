
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
public class AdminBannerUpdateService extends AbstractService<Administrator, Banner> {

	@Autowired
	AdminBannerRepository adminBannerRepository;


	@Override
	public void authorise() {

		int bannerId = super.getRequest().getData("id", int.class);
		Banner banner = this.adminBannerRepository.findOneBannerById(bannerId);

		Boolean status = banner != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int id = super.getRequest().getData("id", int.class);
		Banner object = this.adminBannerRepository.findOneBannerById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "lastInstantiationMoment", "endOfInstantiation", "pictureLink", "slogan", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("lastInstantiationMoment") && !super.getBuffer().getErrors().hasErrors("endOfInstantiation")) {
			super.state(MomentHelper.isAfter(object.getEndOfInstantiation(), object.getLastInstantiationMoment()), "lastInstantiationMoment", "administrator.banner.form.error.last-instantiation-moment.invalid");

			//if (!super.getBuffer().getErrors().hasErrors("endOfInstantiation")) {

			//Display period must last for at least one week
			Date maximumDeadline = MomentHelper.deltaFromMoment(object.getLastInstantiationMoment(), 7, ChronoUnit.DAYS);
			super.state(MomentHelper.isAfter(object.getEndOfInstantiation(), maximumDeadline), "endOfInstantiation", "administrator.banner.form.error.period.invalid");
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

		Dataset dataset = super.unbind(object, "lastInstantiationMoment", "endOfInstantiation", "pictureLink", "slogan", "link");
		super.getResponse().addData(dataset);
	}

}
