
package acme.features.auditor.codeaudit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.code_audit.CodeAudit;
import acme.roles.Auditor;

@Controller
public class AuditorCodeAuditController extends AbstractController<Auditor, CodeAudit> {

	@Autowired
	AuditorCodeAuditListMineService	listMineService;

	@Autowired
	AuditorCodeAuditShowService		showService;

	@Autowired
	AuditorCodeAuditCreateService	createService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}
}
