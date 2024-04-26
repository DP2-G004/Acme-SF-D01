
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
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority=:p AND us.draftMode=:draftMode")
	Integer numUserStoriesByPriority(int managerId, Priority p, boolean draftMode);
	@Query("select avg(us.estimatedCost) from UserStory us where us.manager.id = :managerId AND us.draftMode=:draftMode")
	Double avgUserStoryCost(int managerId, boolean draftMode);
	@Query("select stddev(us.estimatedCost) from UserStory us where us.manager.id = :managerId AND us.draftMode=:draftMode")
	Double deviationUserStoryCostByManager(int managerId, boolean draftMode);
	@Query("select min(us.estimatedCost) from UserStory us where us.manager.id = :managerId AND us.draftMode=:draftMode")
	Double minUserStoryCost(int managerId, boolean draftMode);
	@Query("select max(us.estimatedCost) from UserStory us where us.manager.id = :managerId AND us.draftMode=:draftMode")
	Double maxUserStoryCost(int managerId, boolean draftMode);
	@Query("select avg(p.cost) from Project p where p.manager.id = :managerId AND p.draftMode=:draftMode")
	Double averageProjectCost(int managerId, boolean draftMode);
	@Query("select stddev(p.cost) from Project p where p.manager.id = :managerId AND p.draftMode=:draftMode")
	Double deviationProjectCost(int managerId, boolean draftMode);
	@Query("select min(p.cost) from Project p where p.manager.id = :managerId AND p.draftMode=:draftMode")
	Double minimumProjectCost(int managerId, boolean draftMode);
	@Query("select max(p.cost) from Project p where p.manager.id = :managerId AND p.draftMode=:draftMode")
	Double maximumProjectCost(int managerId, boolean draftMode);

	@Query("select p from Project p where p.manager.id=:managerId")
	Collection<Project> findProjectsByManagerId(int managerId);
	@Query("select us from UserStory us where us.manager.id=:userStoryId")
	Collection<UserStory> findUserStoriesByManagerId(int userStoryId);
}
