
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {

	@Autowired
	ManagerProjectRepository createRepository;


	@Override
	public void authorise() {
		// validation so as to let the creation be done
		super.getResponse().setAuthorised(true);
	}
	@Override
	public void load() {
		Project p;
		Manager m;
		Principal principal;
		principal = super.getRequest().getPrincipal();
		m = this.createRepository.findManagerById(principal.getActiveRoleId());
		p = new Project();
		p.setManager(m);
		p.setDraftMode(true);

		super.getBuffer().addData(p);
	}

	@Override
	public void bind(final Project p) {
		assert p != null;
		super.bind(p, "code", "title", "summary", "indication", "cost", "link", "draftMode");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project p;
			p = this.createRepository.findProjectByCode(object.getCode());
			super.state(p == null, "code", "manager.project.form.error.duplicated");

		}
		if (!super.getBuffer().getErrors().hasErrors("indication")) {
			Project p;
			p = this.createRepository.findProjectById(object.getId());
			super.state(p.isIndication() == false, "indication", "manager.project.form.error.containing-fatal-errors");

		}
	}

	@Override
	public void perform(final Project object) {
		assert object != null;
		this.createRepository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "summary", "indication", "cost", "link", "draftMode");
		super.getResponse().addData(dataset);
	}
}
