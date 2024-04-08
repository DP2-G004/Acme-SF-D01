
package acme.features.manager.project;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id = :managerId")
	List<Project> findProjectsByManagerId(int managerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findManagerById(int managerId);

	@Query("select p from Project p where p.code = :projectCode")
	Project findProjectByCode(String projectCode);

	@Query("select pusl from ProjectUserStoryLink pusl where pusl.project.id = :projectId")
	Collection<ProjectUserStoryLink> findUserStoriesByProjectId(int projectId);
}
