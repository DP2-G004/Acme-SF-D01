
package acme.features.authenticated.notice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.notice.Notice;

@Repository
public interface NoticeRepository extends AbstractRepository {

	@Query("select n from Notice n where n.id = :id")
	Collection<Notice> findNoticesByAnyone(int id);
	@Query("select n from Notice n where n.id = :id")
	Notice findNoticeByAnyone(int id);
	@Query("select n from Notice n")
	Collection<Notice> findAllNotices();
}
