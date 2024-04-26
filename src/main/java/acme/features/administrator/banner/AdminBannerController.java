
package acme.features.administrator.banner;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Administrator;
import acme.entities.banner.Banner;

@Controller
public class AdminBannerController extends AbstractController<Administrator, Banner> {

	@Autowired
	private AdminBannerListService		listService;

	@Autowired
	private AdminBannerShowService		showService;

	@Autowired
	private AdminBannerDeleteService	deleteService;

	@Autowired
	private AdminBannerUpdateService	updateService;

	@Autowired
	private AdminBannerCreateService	createService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("create", this.createService);
	}
}
