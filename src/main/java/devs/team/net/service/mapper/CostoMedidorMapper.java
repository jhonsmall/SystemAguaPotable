package devs.team.net.service.mapper;

import devs.team.net.domain.*;
import devs.team.net.service.dto.CostoMedidorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CostoMedidor and its DTO CostoMedidorDTO.
 */
@Mapper(componentModel = "spring", uses = {CostoMapper.class, MedidorMapper.class})
public interface CostoMedidorMapper extends EntityMapper<CostoMedidorDTO, CostoMedidor> {

    @Mapping(source = "costo.id", target = "costoId")
    @Mapping(source = "medidor.id", target = "medidorId")
    CostoMedidorDTO toDto(CostoMedidor costoMedidor);

    @Mapping(source = "costoId", target = "costo")
    @Mapping(source = "medidorId", target = "medidor")
    CostoMedidor toEntity(CostoMedidorDTO costoMedidorDTO);

    default CostoMedidor fromId(Long id) {
        if (id == null) {
            return null;
        }
        CostoMedidor costoMedidor = new CostoMedidor();
        costoMedidor.setId(id);
        return costoMedidor;
    }
}
