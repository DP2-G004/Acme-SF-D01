
package acme.features.manager.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.userstory.Priority;
import acme.entities.userstory.UserStory;
import acme.roles.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select m from Manager m WHERE m.id = :id")
	Manager findManagerById(int id);
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority=:p AND us.draftMode=false")
	Integer numUserStoriesByPriority(int managerId, Priority p);
	@Query("select avg(us.estimatedCost) from UserStory us where us.manager.id = :managerId AND us.draftMode=false")
	Double avgUserStoryCost(int managerId);
	@Query("select stddev(us.estimatedCost) from UserStory us where us.manager.id = :managerId AND us.draftMode=false")
	Double deviationUserStoryCostByManager(int managerId);
	@Query("select min(us.estimatedCost) from UserStory us where us.manager.id = :managerId AND us.draftMode=false")
	Integer minUserStoryCost(int managerId);
	@Query("select max(us.estimatedCost) from UserStory us where us.manager.id = :managerId AND us.draftMode=false")
	Integer maxUserStoryCost(int managerId);
	@Query("select avg(p.cost) from Project p where p.manager.id = :managerId AND p.draftMode=false")
	Double averageProjectCost(int managerId);
	@Query("select stddev(p.cost) from Project p where p.manager.id = :managerId AND p.draftMode=false")
	Double deviationProjectCost(int managerId);
	@Query("select min(p.cost) from Project p where p.manager.id = :managerId AND p.draftMode=false")
	Integer minimumProjectCost(int managerId);
	@Query("select max(p.cost) from Project p where p.manager.id = :managerId AND p.draftMode=false")
	Integer maximumProjectCost(int managerId);

	@Query("select p from Project p where p.manager.id=:managerId and p.draftMode=false")
	Collection<Project> findProjectsByManagerId(int managerId);
	@Query("select us from UserStory us where us.manager.id=:userStoryId and us.draftMode=false")
	Collection<UserStory> findUserStoriesByManagerId(int userStoryId);
}
