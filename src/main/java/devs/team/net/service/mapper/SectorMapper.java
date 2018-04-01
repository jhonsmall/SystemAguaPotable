package devs.team.net.service.mapper;

import devs.team.net.domain.*;
import devs.team.net.service.dto.SectorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Sector and its DTO SectorDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SectorMapper extends EntityMapper<SectorDTO, Sector> {


    @Mapping(target = "sectorCostos", ignore = true)
    @Mapping(target = "sectorMedidors", ignore = true)
    Sector toEntity(SectorDTO sectorDTO);

    default Sector fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sector sector = new Sector();
        sector.setId(id);
        return sector;
    }
}
