
package acme.features.authenticated.progressLog;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.contract.Progress;

@Repository
public interface AuthenticatedProgressLogRepository extends AbstractRepository {

	@Query("SELECT c FROM Contract c WHERE c.id = :id")
	Contract findContractById(int id);

	@Query("SELECT a FROM Progress a WHERE a.contract.id = :id")
	Collection<Progress> findProgressByContractId(int id);
	@Query("Select p from Progress p WHERE p.id = :id")
	Progress findProgressById(int id);

	@Query("SELECT t FROM Contract t")
	Collection<Contract> findAllContracts();

}
