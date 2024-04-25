
package acme.features.developer.dashboard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(tm) FROM TrainingModule tm WHERE tm.developer.id = :developerId AND tm.updateMoment IS NOT null")
	Integer totalNumberOfTrainingModulesWithAnUpdateMoment(int developerId);

	@Query("SELECT COUNT(ts) FROM TrainingSession ts WHERE ts.trainingModule.developer.id = :developerId AND ts.link IS NOT null")
	Integer totalNumberOfTrainingSessionsWithALink(int developerId);

	@Query("SELECT AVG(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = :developerId")
	Double averageTimeOfTheTrainingModules(int developerId);

	@Query("SELECT STDDEV(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = :developerId")
	Double deviationTimeOfTheTrainingModules(int developerId);
	@Query("SELECT MIN(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = :developerId")
	Double minimumTimeOfTheTrainingModules(int developerId);
	@Query("SELECT MAX(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = :developerId")
	Double maximumTimeOfTheTrainingModules(int developerId);
}
