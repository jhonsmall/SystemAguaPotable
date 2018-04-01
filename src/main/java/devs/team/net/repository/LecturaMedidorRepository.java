package devs.team.net.repository;

import devs.team.net.domain.LecturaMedidor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the LecturaMedidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LecturaMedidorRepository extends JpaRepository<LecturaMedidor, Long> {
    @Query("select distinct lectura_medidor from LecturaMedidor lectura_medidor left join fetch lectura_medidor.lecturamedidorRecibos")
    List<LecturaMedidor> findAllWithEagerRelationships();

    @Query("select lectura_medidor from LecturaMedidor lectura_medidor left join fetch lectura_medidor.lecturamedidorRecibos where lectura_medidor.id =:id")
    LecturaMedidor findOneWithEagerRelationships(@Param("id") Long id);

}
