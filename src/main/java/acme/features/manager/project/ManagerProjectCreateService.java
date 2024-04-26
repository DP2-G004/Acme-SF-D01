
package acme.features.manager.project;

import java.util.Locale;

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
		super.bind(p, "code", "title", "summary", "indication", "cost", "link");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Project p;
			p = this.createRepository.findProjectByCode(object.getCode());
			super.state(p == null, "code", "manager.project.form.error.duplicated");

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
		dataset = super.unbind(object, "code", "title", "summary", "indication", "cost", "link", "draft-mode");

		if (object.isIndication()) {
			final Locale local = super.getRequest().getLocale();

			dataset.put("indication", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("indication", "No");
		super.getResponse().addData(dataset);
	}
}
