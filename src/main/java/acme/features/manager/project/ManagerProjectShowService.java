
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectShowService extends AbstractService<Manager, Project> {

	@Autowired
	ManagerProjectRepository showRepository;


	@Override
	public void authorise() {
		//		Boolean status;
		//		int id;
		//		Project pr;
		//		Manager manager;
		//
		//		id = super.getRequest().getData("id", int.class);
		//		pr = this.showRepository.findProjectById(id);
		//		manager = pr == null ? null : pr.getManager();
		//		status = pr != null && pr.isDraftMode() && super.getRequest().getPrincipal().hasRole(manager);

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Project object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.showRepository.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "summary", "indication", "cost", "link", "draft-mode");
		super.getResponse().addData(dataset);
	}
}
