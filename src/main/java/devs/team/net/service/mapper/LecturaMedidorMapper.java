package devs.team.net.service.mapper;

import devs.team.net.domain.*;
import devs.team.net.service.dto.LecturaMedidorDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LecturaMedidor and its DTO LecturaMedidorDTO.
 */
@Mapper(componentModel = "spring", uses = {ReciboMapper.class, MedidorMapper.class})
public interface LecturaMedidorMapper extends EntityMapper<LecturaMedidorDTO, LecturaMedidor> {

    @Mapping(source = "medidor.id", target = "medidorId")
    LecturaMedidorDTO toDto(LecturaMedidor lecturaMedidor);

    @Mapping(source = "medidorId", target = "medidor")
    LecturaMedidor toEntity(LecturaMedidorDTO lecturaMedidorDTO);

    default LecturaMedidor fromId(Long id) {
        if (id == null) {
            return null;
        }
        LecturaMedidor lecturaMedidor = new LecturaMedidor();
        lecturaMedidor.setId(id);
        return lecturaMedidor;
    }
}
