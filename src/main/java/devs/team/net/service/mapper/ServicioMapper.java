package devs.team.net.service.mapper;

import devs.team.net.domain.*;
import devs.team.net.service.dto.ServicioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Servicio and its DTO ServicioDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServicioMapper extends EntityMapper<ServicioDTO, Servicio> {


    @Mapping(target = "servicioCostos", ignore = true)
    Servicio toEntity(ServicioDTO servicioDTO);

    default Servicio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Servicio servicio = new Servicio();
        servicio.setId(id);
        return servicio;
    }
}
