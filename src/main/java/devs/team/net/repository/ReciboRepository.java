package devs.team.net.repository;

import devs.team.net.domain.Recibo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Recibo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Long> {

}
