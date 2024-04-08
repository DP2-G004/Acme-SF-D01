
package acme.features.manager.userstory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("select m from Manager m where m.id = :id")
	Manager findManagerById(int id);
	@Query("select us from UserStory us where us.manager.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);
	@Query("select us from UserStory us WHERE us.id = :id")
	UserStory findUserStoryById(int id);
	@Query("select pus from ProjectUserStoryLink pus where pus.id = :userStoryId")
	Collection<ProjectUserStoryLink> findProjectsByUserStoryId(int userStoryId);
}
