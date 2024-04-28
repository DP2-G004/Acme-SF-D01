
package acme.features.any.code_audit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.code_audit.CodeAudit;

@Controller
public class AnyCodeAuditController extends AbstractController<Any, CodeAudit> {

	@Autowired
	private AnyCodeAuditListPublishedService	listPublisehdService;

	@Autowired
	private AnyCodeAuditShowService				showService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-published", "list", this.listPublisehdService);
		super.addBasicCommand("show", this.showService);
	}
}
