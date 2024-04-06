
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectListMineService extends AbstractService<Manager, Project> {

	@Autowired
	private ManagerProjectRepository listMineRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Project> projects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		projects = this.listMineRepository.findProjectsByManagerId(principal.getActiveRoleId());

		super.getBuffer().addData(projects);
	}

	@Override
	public void unbind(final Project object) {
		//assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "title", "summary", "indication", "cost", "link");

		super.getResponse().addData(dataset);
	}
}
