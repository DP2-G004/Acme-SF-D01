
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


	@PostConstruct
	public void initialise() {
		super.addBasicCommand("show", this.showService);

		super.addCustomCommand("list-mine", "list", this.listMineService);
	}

}
