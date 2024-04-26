
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audit_record.Mark;
import acme.entities.code_audit.CodeAudit;
import acme.entities.code_audit.CodeAuditType;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditShowService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	AuditorCodeAuditRepository repository;


	@Override
	public void authorise() {

		int id = super.getRequest().getData("id", int.class);
		CodeAudit codeAudit = this.repository.findOneCodeAuditById(id);

		boolean status = codeAudit != null && super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int id = super.getRequest().getData("id", int.class);
		CodeAudit object = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final CodeAudit object) {

		SelectChoices choices;
		SelectChoices projects;
		Dataset dataset;
		String markMode;

		Collection<Mark> marks = this.repository.findMarksByAuditId(object.getId());
		markMode = MarkMode.calculateMode(marks);
		Collection<Project> allProjects = this.repository.findAllPublishedProjects();
		projects = SelectChoices.from(allProjects, "code", object.getProject());
		choices = SelectChoices.from(CodeAuditType.class, object.getType());

		dataset = super.unbind(object, "code", "draftMode", "executionDate", "type", "correctiveActions", "link");
		dataset.put("project", projects.getSelected().getKey());
		dataset.put("projects", projects);
		dataset.put("auditTypes", choices);
		dataset.put("markMode", markMode);

		assert object != null;

		super.getResponse().addData(dataset);
	}
}
