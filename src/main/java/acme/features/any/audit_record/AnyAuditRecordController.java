
package acme.features.any.audit_record;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.audit_record.AuditRecord;

@Controller
public class AnyAuditRecordController extends AbstractController<Any, AuditRecord> {

	@Autowired
	private AnyAuditRecordListForPublishedCodeAuditService	listPublishedService;

	@Autowired
	private AnyAuditRecordShowService						showService;


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-published", "list", this.listPublishedService);
		super.addBasicCommand("show", this.showService);
	}
}
