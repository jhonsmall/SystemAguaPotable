package devs.team.net.service.mapper;

import devs.team.net.domain.*;
import devs.team.net.service.dto.CostoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Costo and its DTO CostoDTO.
 */
@Mapper(componentModel = "spring", uses = {ServicioMapper.class, SectorMapper.class, ClasificacionMapper.class})
public interface CostoMapper extends EntityMapper<CostoDTO, Costo> {

    @Mapping(source = "servicio.id", target = "servicioId")
    @Mapping(source = "sector.id", target = "sectorId")
    @Mapping(source = "clasificacion.id", target = "clasificacionId")
    CostoDTO toDto(Costo costo);

    @Mapping(target = "costoCostoMedidors", ignore = true)
    @Mapping(source = "servicioId", target = "servicio")
    @Mapping(source = "sectorId", target = "sector")
    @Mapping(source = "clasificacionId", target = "clasificacion")
    Costo toEntity(CostoDTO costoDTO);

    default Costo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Costo costo = new Costo();
        costo.setId(id);
        return costo;
    }
}
