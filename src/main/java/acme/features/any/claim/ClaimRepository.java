
package acme.features.any.claim;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.claim.Claim;

@Repository
public interface ClaimRepository extends AbstractRepository {

	@Query("select c from Claim c where c.id = :id")
	Collection<Claim> findClaimsByAnyone(int id);
	@Query("select c from Claim c where c.id = :id")
	Claim findClaimByAnyone(int id);
	@Query("select c from Claim c")
	Collection<Claim> findAllClaims();
	@Query("select c from Claim c where c.code = :code")
	Claim findClaimByCode(String code);
}
