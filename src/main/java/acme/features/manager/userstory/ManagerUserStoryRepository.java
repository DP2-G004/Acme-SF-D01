
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("SELECT m FROM Manager m WHERE m.id =: id")
	Manager findManagerById(int id);
	@Query("SELECT us FROM UserStory us WHERE us.manager.id =: id")
	Collection<UserStory> findAllUserStoriesByManagerId(int id);
	@Query("SELECT us FROM UserStory us WHERE us.id =: id")
	UserStory findUserStoryById(int id);
}
