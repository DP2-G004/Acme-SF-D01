
package acme.features.administrator.banner;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdminBannerListService extends AbstractService<Administrator, Banner> {

	@Autowired
	protected AdminBannerRepository adminBannerRepository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Banner> objects;
		objects = this.adminBannerRepository.findAllBanners();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "pictureLink", "slogan", "link");
		super.getResponse().addData(dataset);
	}

}
