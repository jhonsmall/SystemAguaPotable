package devs.team.net.repository;

import devs.team.net.domain.Clasificacion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Clasificacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClasificacionRepository extends JpaRepository<Clasificacion, Long> {

}
