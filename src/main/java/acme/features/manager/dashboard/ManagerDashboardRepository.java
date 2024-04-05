
package acme.features.manager.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("SELECT m FROM Manager m WHERE m.id =: id")
	Manager findManagerById(int id);
	@Query("SELECT COUNT(us) FROM UserStory us WHERE us.manager.id =: managerId AND us.priority='MUST' ")
	int numMustPriorityUserStories(int managerId);
	@Query("SELECT COUNT(us) FROM UserStory us WHERE us.manager.id =: managerId AND us.priority='SHOULD' ")
	int numShouldPriorityUserStories(int managerId);
	@Query("SELECT COUNT(us) FROM UserStory us WHERE us.manager.id =: managerId AND us.priority='COULD' ")
	int numCouldPriorityUserStories(int managerId);
	@Query("SELECT COUNT(us) FROM UserStory us WHERE us.manager.id =: managerId AND us.priority='WONT' ")
	int numWontPriorityUserStories(int managerId);
	@Query("SELECT AVG(us.estimatedCost) FROM UserStory us WHERE us.manager.id =: managerId")
	Double avgUserStoryCost(int managerId);
	@Query("SELECT STDDEV(us.estimatedCost) FROM UserStory us WHERE us.manager.id = :managerId")
	Double deviationUserStoryCostByManager(int managerId);
	@Query("SELECT MIN(us.estimatedCost) FROM UserStory us WHERE us.manager.id =: managerId")
	double minUserStoryCost(int managerId);
	@Query("SELECT MAX(us.estimatedCost) FROM UserStory us WHERE us.manager.id =: managerId")
	double maxUserStoryCost(int managerId);
	@Query("SELECT AVG(p.cost) FROM Project p WHERE p.manager.id =: managerId")
	Double averageProjectCost(int managerId);
	@Query("SELECT STDDEV(p.cost) FROM Project p WHERE p.manager.id =: managerId")
	Double deviationProjectCost(int managerId);
	@Query("SELECT MIN(p.cost) FROM Project p WHERE p.manager.id =: managerId")
	double minimumProjectCost(int managerId);
	@Query("SELECT MAX(p.cost) FROM Project p WHERE p.manager.id =: managerId")
	double maximumProjectCost(int managerId);
}
