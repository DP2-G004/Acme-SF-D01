
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectListMineService extends AbstractService<Manager, Project> {

	@Autowired
	ManagerProjectRepository listMineRepository;


	@Override
	public void authorise() {
		boolean status;
		int id;
		Manager manager;
		Project p;

		id = super.getRequest().getData("id", int.class);
		p = this.listMineRepository.findProjectById(id);
		manager = p == null ? null : p.getManager();

		status = p != null && super.getRequest().getPrincipal().hasRole(manager);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int managerId;
		managerId = super.getRequest().getData("id", int.class);
		Collection<Project> projects = this.listMineRepository.findAllProjectsByManagerId(managerId);
		super.getBuffer().addData(projects);
	}

	@Override
	public void unbind(final Project object) {
		Dataset dataset;
		dataset = super.unbind(object, "code", "title", "summary", "indication", "cost", "link");
		super.getResponse().addData(dataset);
	}
}
