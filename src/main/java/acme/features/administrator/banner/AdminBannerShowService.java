
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdminBannerShowService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdminBannerRepository adminBannerRepository;


	@Override
	public void authorise() {

		int bannerId;
		bannerId = super.getRequest().getData("id", int.class);

		Banner banner;
		banner = this.adminBannerRepository.findOneBannerById(bannerId);

		Boolean status;
		status = banner != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int bannerId;
		bannerId = super.getRequest().getData("id", int.class);

		Banner banner;
		banner = this.adminBannerRepository.findOneBannerById(bannerId);

		super.getBuffer().addData(banner);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "instantiationMoment", "displayMoment", "endOfDisplay", "pictureLink", "slogan", "link");
		super.getResponse().addData(dataset);
	}

}
