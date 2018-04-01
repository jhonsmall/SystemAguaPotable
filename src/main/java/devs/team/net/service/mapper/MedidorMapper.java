package devs.team.net.service.mapper;

import devs.team.net.domain.*;
import devs.team.net.service.dto.MedidorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Medidor and its DTO MedidorDTO.
 */
@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, SectorMapper.class, ClasificacionMapper.class})
public interface MedidorMapper extends EntityMapper<MedidorDTO, Medidor> {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "sector.id", target = "sectorId")
    @Mapping(source = "clasificacion.id", target = "clasificacionId")
    MedidorDTO toDto(Medidor medidor);

    @Mapping(target = "medidorCostoMedidors", ignore = true)
    @Mapping(target = "medidorLecturaMedidors", ignore = true)
    @Mapping(source = "usuarioId", target = "usuario")
    @Mapping(source = "sectorId", target = "sector")
    @Mapping(source = "clasificacionId", target = "clasificacion")
    Medidor toEntity(MedidorDTO medidorDTO);

    default Medidor fromId(Long id) {
        if (id == null) {
            return null;
        }
        Medidor medidor = new Medidor();
        medidor.setId(id);
        return medidor;
    }
}
