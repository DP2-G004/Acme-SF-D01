
package acme.features.authenticated.sponsor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.roles.Sponsor;

@Repository
public interface AuthenticatedSponsorRepository extends AbstractRepository {

	@Query("SELECT s FROM Sponsor s WHERE s.userAccount.id = :userAccountId")
	Sponsor findSponsorByUserAccount(int userAccountId);

	@Query("SELECT ua FROM UserAccount ua WHERE ua.id = :userAccountId")
	UserAccount findUserAccountById(int userAccountId);
}
