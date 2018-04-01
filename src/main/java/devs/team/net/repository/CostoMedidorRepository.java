package devs.team.net.repository;

import devs.team.net.domain.CostoMedidor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CostoMedidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CostoMedidorRepository extends JpaRepository<CostoMedidor, Long> {

}
