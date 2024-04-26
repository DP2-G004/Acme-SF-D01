
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

	@Query("select pus from ProjectUserStoryLink pus where pus.project.manager.id = :id and pus.userStory.manager.id = :id")
	Collection<ProjectUserStoryLink> findLinksByManagerId(int id);

	@Query("select pus from ProjectUserStoryLink pus where pus.id=:id")
	Collection<ProjectUserStoryLink> findLinksById(int id);

	@Query("select distinct pus From ProjectUserStoryLink pus WHERE pus.project.id =:id")
	Collection<ProjectUserStoryLink> findAllAssignmentsOfAProjectById(int id);

	@Query("select m from Manager m where m.id = :managerId")
	Manager findOneManagerById(int managerId);

	@Query("SELECT pus FROM ProjectUserStoryLink pus WHERE pus.project.id = :projectId AND pus.userStory.id = :userStoryId")
	ProjectUserStoryLink findOneLinkByProjectIdAndUserStoryId(int projectId, int userStoryId);

	@Query("SELECT pus.project.manager FROM ProjectUserStoryLink pus WHERE pus.id = :id")
	Manager findOneManagerByLinkId(int id);

	@Query("SELECT pus.project FROM ProjectUserStoryLink pus WHERE pus.id = :id")
	Project findOneProjectByProjectUserStoryId(int id);

	@Query("SELECT pus.userStory FROM ProjectUserStoryLink pus WHERE pus.id = :id")
	UserStory findOneUserStoryByProjectUserStoryId(int id);

	@Query("select p from Project p where p.manager.id = :id and p.draftMode = :draftMode")
	Collection<Project> findNotPublishedProjectsByManagerId(int id, boolean draftMode);

	@Query("select us from UserStory us where us.manager.id = :id and us.draftMode = :draftMode")
	Collection<UserStory> findPublishedUserStoriesByManagerId(int id, boolean draftMode);
}
