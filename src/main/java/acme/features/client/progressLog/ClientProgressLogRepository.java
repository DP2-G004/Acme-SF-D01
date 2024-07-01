
package acme.features.client.progressLog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.contract.Progress;
import acme.entities.project.Project;
import acme.roles.client.Client;

@Repository
public interface ClientProgressLogRepository extends AbstractRepository {

	@Query("SELECT a FROM Progress a WHERE a.contract.id = :id")
	Collection<Progress> findProgressByContractId(int id);

	@Query("SELECT us FROM Progress us WHERE us.id = :id")
	Progress findProgressById(int id);

	@Query("SELECT p FROM Progress p WHERE p.record = :record")
	Progress findProgressByRecord(String record);

	@Query("SELECT t FROM Contract t WHERE t.id = :id")
	Contract findContractById(int id);

	@Query("Select p.contract from Progress p WHERE p-id = :id")
	Contract findContractByProgressId(int id);

	@Query("SELECT us FROM Progress us WHERE us.contract.client.userAccount.id = :id")
	Collection<Progress> findProgressByClientId(int id);

	@Query("SELECT d FROM Client d WHERE d.id = :id")
	Client findClientById(int id);

	@Query("SELECT t FROM Progress t")
	Collection<Progress> findAllProgress();

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select c from Contract c")
	Collection<Contract> findAllContract();

	@Query("select max(p.completeness) from Progress p where p.contract.id = :id and p.draftMode = false")
	Optional<Double> findMaxCompletenessPublished(int id);

}
