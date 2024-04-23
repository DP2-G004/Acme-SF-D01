
package acme.features.client.clientDashboards;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.client.Client;

@Repository
public interface ClientDashboardsRepository extends AbstractRepository {

	@Query("SELECT c FROM Client c WHERE c.userAccount.id = :id")
	Client findClientById(int id);

	@Query("SELECT COUNT(p) FROM Progress p WHERE p.contract.customerName.userAccount.id = :id AND p.completeness BETWEEN 0 AND 24.9 AND p.draftMode = false")
	int totalNumProgressLogLessThan25(int id);

	@Query("SELECT COUNT(p) FROM Progress p WHERE p.contract.customerName.userAccount.id = :id AND p.completeness between 25 AND 49.9 AND p.draftMode = false")
	int totalNumProgressLogLessBetween25And50(int id);

	@Query("SELECT COUNT(p) FROM Progress p WHERE p.contract.customerName.userAccount.id = :id AND p.completeness BETWEEN 50 AND 74.9 AND p.draftMode = false")
	int totalNumProgressLogLessBetween50And75(int id);

	@Query("SELECT COUNT(p) FROM Progress p WHERE p.contract.customerName.userAccount.id = :id AND p.completeness > 75 AND p.draftMode = false")
	int totalNumProgressLogAbove75(int id);

	@Query("SELECT AVG(c.budget.amount) FROM Contract c WHERE c.customerName.userAccount.id = :id AND c.draftMode = false")
	Double findAverageContractBudget(int id);

	@Query("SELECT STDDEV(c.budget.amount) FROM Contract c WHERE c.customerName.userAccount.id = :id AND c.draftMode = false")
	Double findDeviationContractBudget(int id);

	@Query("SELECT MAX(c.budget.amount) FROM Contract c WHERE c.customerName.userAccount.id = :id AND c.draftMode = false")
	Double findMaximumContractBudget(int id);

	@Query("SELECT MIN(c.budget.amount) FROM Contract c WHERE c.customerName.userAccount.id = :id AND c.draftMode = false")
	Double findMinimumContractBudget(int id);

}
