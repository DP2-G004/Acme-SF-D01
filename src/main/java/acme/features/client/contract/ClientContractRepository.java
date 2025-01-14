
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.contract.Progress;
import acme.entities.project.Project;
import acme.roles.client.Client;

@Repository
public interface ClientContractRepository extends AbstractRepository {

	@Query("SELECT c FROM Contract c WHERE c.client.userAccount.id = :id")
	Collection<Contract> findAllContractsByClientId(int id);

	@Query("SELECT c FROM Contract c WHERE c.id = :id")
	Contract findContractById(int id);

	@Query("SELECT a FROM Progress a WHERE a.contract.id = :id")
	Collection<Progress> findProgressByContractId(int id);

	@Query("SELECT d FROM Client d WHERE d.id = :id")
	Client findClientById(int id);

	@Query("SELECT t FROM Contract t")
	Collection<Contract> findAllContracts();

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.id = :id and p.draftMode = false")
	Project findProjectById(int id);

	@Query("select c from Contract c where c.project.id = :id and c.draftMode = false")
	Collection<Contract> findAllContractsByProjectId(int id);
	@Query("select p from Progress p where p.contract.id=:id and p.draftMode = true")
	Collection<Progress> findAllDraftProgress(int id);
	@Query("select p from Progress p where p.contract.id=:id and p.draftMode = false")
	Collection<Progress> findAllProgress(int id);

}
