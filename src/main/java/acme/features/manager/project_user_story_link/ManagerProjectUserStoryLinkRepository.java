
package acme.features.manager.project_user_story_link;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.project_userstory_link.ProjectUserStoryLink;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerProjectUserStoryLinkRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);
	@Query("select us from UserStory us where us.id = :id")
	UserStory findUserStoryById(int id);
	@Query("select pus from ProjectUserStoryLink pus where pus.id = :id")
	ProjectUserStoryLink findLinkById(int id);
	@Query("select us from UserStory us where us.manager.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);
	@Query("select p from Project p where p.manager.id = :id")
	Collection<Project> findProjectsByManagerId(int id);
	@Query("select pus from ProjectUserStoryLink pus where pus.project.id = :id")
	Collection<ProjectUserStoryLink> findLinksByProjectId(int id);
	@Query("select pus.project.manager from ProjectUserStoryLink pus where pus.id =:id")
	Manager findProjectManagerByLinkId(int id);
}
