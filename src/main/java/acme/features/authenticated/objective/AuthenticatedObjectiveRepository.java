
package acme.features.authenticated.objective;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.objective.Objective;

@Repository
public interface AuthenticatedObjectiveRepository extends AbstractRepository {

	@Query("Select o from Objective o where o.id = :id")
	Objective findObjectiveById(int id);

	@Query("Select o from Objective o")
	Collection<Objective> findAllObjective();

}
