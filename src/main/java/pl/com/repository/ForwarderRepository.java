package pl.com.repository;

import pl.com.domain.Forwarder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Forwarder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ForwarderRepository extends JpaRepository<Forwarder, Long> {

}
