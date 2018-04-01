package devs.team.net.service.mapper;

import devs.team.net.domain.*;
import devs.team.net.service.dto.ClasificacionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Clasificacion and its DTO ClasificacionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClasificacionMapper extends EntityMapper<ClasificacionDTO, Clasificacion> {


    @Mapping(target = "clasificacionCostos", ignore = true)
    @Mapping(target = "clasificacionEscalasDelMedidors", ignore = true)
    @Mapping(target = "clasificacionMedidors", ignore = true)
    Clasificacion toEntity(ClasificacionDTO clasificacionDTO);

    default Clasificacion fromId(Long id) {
        if (id == null) {
            return null;
        }
        Clasificacion clasificacion = new Clasificacion();
        clasificacion.setId(id);
        return clasificacion;
    }
}
