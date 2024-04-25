
package acme.features.developer.training_module;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.training_module.TrainingModule;
import acme.entities.training_session.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("select t from TrainingModule t where t.developer.id = :developerId")
	Collection<TrainingModule> findAllTrainingModuleByDeveloper(int developerId);

	@Query("select tm from TrainingModule tm where tm.id = :trainingModuleId")
	TrainingModule findOneTrainingModuleById(int trainingModuleId);

	@Query("select tm.developer from TrainingModule tm where tm.developer.id = :activeRoleId")
	Developer findDeveloperById(int activeRoleId);

	@Query("select tm from TrainingModule tm where tm.code = :code")
	TrainingModule findTrainingModuleByCode(String code);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select tm from TrainingModule tm")
	Collection<TrainingModule> findAllTrainingModule();

	@Query("select ts from TrainingSession ts where ts.trainingModule.id = :id")
	Collection<TrainingSession> findTrainingSessionsByTrainingModuleId(int id);

}
