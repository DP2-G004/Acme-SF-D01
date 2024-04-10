
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select m from Manager m WHERE m.id = :id")
	Manager findManagerById(int id);
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority='MUST' ")
	int numMustPriorityUserStories(int managerId);
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority='SHOULD' ")
	int numShouldPriorityUserStories(int managerId);
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority='COULD' ")
	int numCouldPriorityUserStories(int managerId);
	@Query("select count(us) from UserStory us where us.manager.id = :managerId AND us.priority='WONT' ")
	int numWontPriorityUserStories(int managerId);
	@Query("select avg(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	Double avgUserStoryCost(int managerId);
	@Query("select stddev(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	Double deviationUserStoryCostByManager(int managerId);
	@Query("select min(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	double minUserStoryCost(int managerId);
	@Query("select max(us.estimatedCost) from UserStory us where us.manager.id = :managerId")
	double maxUserStoryCost(int managerId);
	@Query("select avg(p.cost) from Project p where p.manager.id = :managerId")
	Double averageProjectCost(int managerId);
	@Query("select stddev(p.cost) from Project p where p.manager.id = :managerId")
	Double deviationProjectCost(int managerId);
	@Query("select min(p.cost) from Project p where p.manager.id = :managerId")
	double minimumProjectCost(int managerId);
	@Query("select max(p.cost) from Project p where p.manager.id = :managerId")
	double maximumProjectCost(int managerId);
}
