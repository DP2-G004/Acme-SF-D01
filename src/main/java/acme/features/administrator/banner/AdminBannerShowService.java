
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

		int bannerId = super.getRequest().getData("id", int.class);
		Banner banner = this.adminBannerRepository.findOneBannerById(bannerId);
		Boolean status = banner != null && super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int bannerId = super.getRequest().getData("id", int.class);
		Banner banner = this.adminBannerRepository.findOneBannerById(bannerId);

		super.getBuffer().addData(banner);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "lastInstantiationMoment", "endOfInstantiation", "pictureLink", "slogan", "link");
		super.getResponse().addData(dataset);
	}

}
