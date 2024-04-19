
package acme.features.manager.project_user_story_link;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.entities.userstory.UserStory;

@Repository
public interface ManagerProjectUserStoryLinkRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
	@Query("select us from UserStory us where us.id = :id")
	UserStory findUserStoryById(int id);
	@Query("select pus from ProjectUserStoryLink pus where pus.id = :id")
	ProjectUserStoryLink findLinkById(int id);
	@Query("select us from UserStory us where us.manager.userAccount.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);
	@Query("select pus from ProjectUserStoryLink pus where pus.project.id = :id")
	Collection<ProjectUserStoryLink> findLinksByProjectId(int id);
}
