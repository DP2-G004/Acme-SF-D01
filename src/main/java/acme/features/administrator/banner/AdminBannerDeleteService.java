
package acme.features.administrator.banner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.banner.Banner;

@Service
public class AdminBannerDeleteService extends AbstractService<Administrator, Banner> {

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

		int id = super.getRequest().getData("id", int.class);
		Banner object = this.adminBannerRepository.findOneBannerById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Banner object) {
		assert object != null;

		super.bind(object, "instantiationMoment", "displayMoment", "endOfDisplay", "pictureLink", "slogan", "link");
	}

	@Override
	public void validate(final Banner object) {
		assert object != null;
	}

	@Override
	public void perform(final Banner object) {
		assert object != null;

		this.adminBannerRepository.delete(object);
	}

	@Override
	public void unbind(final Banner object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "instantiationUpdate", "linkPicture", "slogan", "linkDocumento", "periodoInicial", "periodoFinal");
		dataset.put("readonly", false);
		super.getResponse().addData(dataset);
	}
}
