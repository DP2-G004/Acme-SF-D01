
package acme.features.auditor.codeaudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.code_audit.CodeAudit;
import acme.entities.code_audit.CodeAuditType;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		CodeAudit object;
		Auditor auditor;

		auditor = this.repository.findAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
		object = new CodeAudit();
		object.setDraftMode(true);
		object.setAuditor(auditor);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "code", "executionDate", "type", "correctiveActions", "link", "project");
	}
	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			CodeAudit existing;

			existing = this.repository.findOneCodeAuditByCode(object.getCode());
			super.state(existing == null, "code", "Auditor.CodeAudit.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("executionDate"))
			super.state(MomentHelper.isPast(object.getExecutionDate()), "executionDate", "Auditor.CodeAudit.form.error.too-close");

		if (!super.getBuffer().getErrors().hasErrors("project"))
			super.state(!(object.getProject() == null), "project", "Auditor.CodeAudit.form.unchosen-published-project");
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		//Para evitar que se modifique la peticion y crear un objeto de la entidad con un id ya existente
		object.setId(0);
		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Collection<Project> projects;
		SelectChoices choices;
		SelectChoices types;
		Dataset dataset;

		types = SelectChoices.from(CodeAuditType.class, object.getType());

		projects = this.repository.findAllPublishedProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "code", "executionDate", "correctiveActions", "link", "draftMode");
		dataset.put("type", types.getSelected().getKey());
		dataset.put("auditTypes", types);
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}

}
