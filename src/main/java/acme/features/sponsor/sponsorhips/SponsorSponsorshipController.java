
package acme.features.sponsor.sponsorhips;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Controller
public class SponsorSponsorshipController extends AbstractController<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipListMineService	listMineService;

	@Autowired
	private SponsorSponsorshipShowService		showService;

	@Autowired
	private SponsorSponsorshipCreateService		createService;

	@Autowired
	private SponsorSponsorshipUpdateService		updateService;

	@Autowired
	private SponsorSponsorshipPublishService	publishService;

	@Autowired
	private SponsorSponsorshipDeleteService		deleteService;


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
