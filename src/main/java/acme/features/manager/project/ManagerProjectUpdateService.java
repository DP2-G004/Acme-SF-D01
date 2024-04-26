
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {

	@Autowired
	ManagerProjectRepository updateRepository;


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		Project p;
		Manager manager;

		masterId = super.getRequest().getData("id", int.class);
		p = this.updateRepository.findProjectById(masterId);
		manager = p == null ? null : p.getManager();
		status = p != null && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.updateRepository.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project p) {
		assert p != null;
		super.bind(p, "code", "title", "summary", "indication", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project p;
			p = this.updateRepository.findProjectByCode(object.getCode());
			super.state(p == null, "code", "manager.project.form.error.duplicated");

		}
		if (!super.getBuffer().getErrors().hasErrors("draft-mode"))
			super.state(object.isDraftMode(), "draft-mode", "manager.project.form.error.draft-mode");

	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		this.updateRepository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "summary", "indication", "cost", "link", "draft-mode");
		super.getResponse().addData(dataset);
	}
}
