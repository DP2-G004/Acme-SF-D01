
package acme.features.sponsor.sponsorhips;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoice.Invoice;
import acme.entities.project.Project;
import acme.entities.sistem_currency.SystemCurrency;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("SELECT s FROM Sponsorship s WHERE s.sponsor.id = :sponsorId")
	Collection<Sponsorship> findSponsorshipBySponsor(int sponsorId);

	@Query("SELECT s FROM Sponsorship s WHERE s.id = :sponsorshipId")
	Sponsorship findSponsorshipById(int sponsorshipId);

	@Query("SELECT s FROM Sponsor s WHERE s.id = :sponsorId")
	Sponsor findSponsorById(int sponsorId);

	@Query("SELECT s FROM Sponsorship s WHERE s.code = :code")
	Sponsorship findSponsorshipByCode(String code);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id = :sponsorshipId")
	Collection<Invoice> findInvoicesBySponsorshipId(int sponsorshipId);

	@Query("SELECT DISTINCT p FROM Project p")
	Collection<Project> findAllProjects();

	@Query("SELECT DISTINCT p FROM Project p WHERE p.draftMode = true")
	Collection<Project> findAllProjectsDraftModeTrue();

	@Query("SELECT sc FROM SystemCurrency sc")
	SystemCurrency findSystemCurrency();
}
