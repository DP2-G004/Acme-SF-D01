
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
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority=:p ")
	Integer numUserStoriesByPriority(int managerId, Priority p);
	@Query("select avg(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	Double avgUserStoryCost(int managerId);
	@Query("select stddev(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	Double deviationUserStoryCostByManager(int managerId);
	@Query("select min(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	Double minUserStoryCost(int managerId);
	@Query("select max(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	Double maxUserStoryCost(int managerId);
	@Query("select avg(p.cost) from Project p where p.manager.id = :managerId")
	Double averageProjectCost(int managerId);
	@Query("select stddev(p.cost) from Project p where p.manager.id = :managerId")
	Double deviationProjectCost(int managerId);
	@Query("select min(p.cost) from Project p where p.manager.id = :managerId")
	Double minimumProjectCost(int managerId);
	@Query("select max(p.cost) from Project p where p.manager.id = :managerId")
	Double maximumProjectCost(int managerId);

	@Query("select p from Project p where p.manager.id=:managerId")
	Collection<Project> findProjectsByManagerId(int managerId);
	@Query("select us from UserStory us where us.manager.id=:userStoryId")
	Collection<UserStory> findUserStoriesByManagerId(int userStoryId);
}
