package devs.team.net.repository;

import devs.team.net.domain.EscalasDelMedidor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EscalasDelMedidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EscalasDelMedidorRepository extends JpaRepository<EscalasDelMedidor, Long> {

}
