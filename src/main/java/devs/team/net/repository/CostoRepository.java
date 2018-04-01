package devs.team.net.repository;

import devs.team.net.domain.Costo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Costo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CostoRepository extends JpaRepository<Costo, Long> {

}
