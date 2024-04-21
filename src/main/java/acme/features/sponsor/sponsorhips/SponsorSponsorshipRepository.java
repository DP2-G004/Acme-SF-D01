
package acme.features.sponsor.sponsorhips;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorship.Sponsorship;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("SELECT s FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Collection<Sponsorship> findSponsorshipBySponsor(int sponsorId);

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :sponsorshipId")
	Sponsorship findSponsorshipById(int sponsorshipId);

}
