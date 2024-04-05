
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("SELECT m FROM Manager m WHERE m.id =: id")
	Manager findManagerById(int id);
	@Query("SELECT p FROM Project p WHERE p.id =: id")
	Project findProjectById(int id);
	@Query("SELECT p FROM Project p WHERE p.manager.id =: id")
	Collection<Project> findAllProjectsByManagerId(int id);

}
