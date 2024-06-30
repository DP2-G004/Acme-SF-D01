
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit_record.AuditRecord;
import acme.entities.audit_record.Mark;
import acme.entities.code_audit.CodeAudit;
import acme.entities.code_audit.CodeAuditType;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	private AuditorCodeAuditRepository repository;


	@Override
	public void authorise() {

		int id = super.getRequest().getData("id", int.class);
		CodeAudit codeAudit = this.repository.findOneCodeAuditById(id);

		Auditor auditor = codeAudit == null ? null : codeAudit.getAuditor();
		boolean status = codeAudit != null && codeAudit.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int id = super.getRequest().getData("id", int.class);
		CodeAudit object = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "publish");
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		Collection<Mark> marks = this.repository.findMarksByAuditId(object.getId());
		String markMode = MarkMode.calculateMode(marks);

		//Revisar que la mark mode es C o mas para ser publicado
		if (!super.getBuffer().getErrors().hasErrors("markMode"))
			if (markMode != null) {
				boolean isMarkAtLeastC = markMode.equals("C") || markMode.equals("B") || markMode.equals("A") || markMode.equals("A+");
				super.state(isMarkAtLeastC, "markMode", "validation.codeaudit.mark.mode.less-than-c");
			} else
				super.state(false, "markMode", "validation.codeaudit.mark.mode.less-than-c");

		Collection<AuditRecord> auditRecords;

		auditRecords = this.repository.findAuditRecordsByCodeAuditId(object.getId());

		super.state(auditRecords.stream().noneMatch(AuditRecord::isDraftMode), "*", "validation.codeaudit.publish.unpublished-audit-records");
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Mark> marks = this.repository.findMarksByAuditId(object.getId());
		String markMode = MarkMode.calculateMode(marks);

		Collection<Project> allProjects = this.repository.findAllPublishedProjects();
		SelectChoices projects = SelectChoices.from(allProjects, "code", object.getProject());
		SelectChoices choices = SelectChoices.from(CodeAuditType.class, object.getType());

		Dataset dataset = super.unbind(object, "code", "draftMode", "executionDate", "type", "correctiveActions", "link");
		dataset.put("project", projects.getSelected().getKey());
		dataset.put("projects", projects);
		dataset.put("auditTypes", choices);
		dataset.put("markMode", markMode);

		super.getResponse().addData(dataset);
	}
}
