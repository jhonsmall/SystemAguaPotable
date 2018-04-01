package devs.team.net.repository;

import devs.team.net.domain.Medidor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Medidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedidorRepository extends JpaRepository<Medidor, Long> {

}
