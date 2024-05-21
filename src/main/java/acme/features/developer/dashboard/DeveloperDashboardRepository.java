
package acme.features.developer.dashboard;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;

@Repository
public interface DeveloperDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(tm) FROM TrainingModule tm WHERE tm.developer.id = :developerId AND tm.updateMoment IS NOT null and tm.draftMode is not true")
	Integer totalNumberOfTrainingModulesWithAnUpdateMoment(int developerId);

	@Query("SELECT COUNT(ts) FROM TrainingSession ts WHERE ts.trainingModule.developer.id = :developerId AND ts.link IS NOT null and ts.draftMode is not true")
	Integer totalNumberOfTrainingSessionsWithALink(int developerId);

	@Query("SELECT AVG(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = :developerId and tm.draftMode is not true")
	Double averageTimeOfTheTrainingModules(int developerId);

	@Query("SELECT STDDEV(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = :developerId and tm.draftMode is not true")
	Double deviationTimeOfTheTrainingModules(int developerId);
	@Query("SELECT MIN(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = :developerId and tm.draftMode is not true")
	Double minimumTimeOfTheTrainingModules(int developerId);
	@Query("SELECT MAX(tm.totalTime) FROM TrainingModule tm WHERE tm.developer.id = :developerId and tm.draftMode is not true")
	Double maximumTimeOfTheTrainingModules(int developerId);

	@Query("select tm from TrainingModule tm where tm.developer.id = :developerId and tm.draftMode is not true")
	Collection<TrainingModule> findTrainingModulesByDeveloperId(int developerId);
	@Query("select ts from TrainingSession ts where ts.trainingModule.developer.id = :developerId and ts.draftMode is not true")
	Collection<TrainingSession> findTrainingSessionByDeveloperId(int developerId);
}
