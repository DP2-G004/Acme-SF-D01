
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.code_audit.CodeAudit;
import acme.entities.code_audit.CodeAuditType;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	AuditorCodeAuditRepository repository;


	@Override
	public void authorise() {
		boolean status;
		status = super.getRequest().getPrincipal().hasRole(Auditor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object = new CodeAudit();

		object.setDraftMode(false);

		Auditor auditor = this.repository.findAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		object.setAuditor(auditor);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		int projectId = super.getRequest().getData("project", int.class);
		Project project = this.repository.findOneProjectById(projectId);
		object.setProject(project);
		super.bind(object, "code", "type", "link", "draftMode", "executionDate", "correctiveActions");

	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final boolean duplicatedCode = this.repository.findAllCodeAudits().stream().anyMatch(e -> e.getCode().equals(object.getCode()));
			super.state(!duplicatedCode, "code", "validation.codeaudit.code.duplicate");
		}

	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Project> allProjects = this.repository.findAllProjects();
		SelectChoices projects = SelectChoices.from(allProjects, "code", object.getProject());
		SelectChoices choices = SelectChoices.from(CodeAuditType.class, object.getType());

		Dataset dataset = super.unbind(object, "code", "draftMode", "execution", "type", "correctiveActions", "link");
		dataset.put("project", projects.getSelected().getKey());
		dataset.put("projects", projects);
		dataset.put("auditTypes", choices);

		super.getResponse().addData(dataset);
	}

}
