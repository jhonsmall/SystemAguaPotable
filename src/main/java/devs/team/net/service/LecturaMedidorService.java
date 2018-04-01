package devs.team.net.service;

import devs.team.net.service.dto.LecturaMedidorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LecturaMedidor.
 */
public interface LecturaMedidorService {

    /**
     * Save a lecturaMedidor.
     *
     * @param lecturaMedidorDTO the entity to save
     * @return the persisted entity
     */
    LecturaMedidorDTO save(LecturaMedidorDTO lecturaMedidorDTO);

    /**
     * Get all the lecturaMedidors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LecturaMedidorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" lecturaMedidor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LecturaMedidorDTO findOne(Long id);

    /**
     * Delete the "id" lecturaMedidor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the lecturaMedidor corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LecturaMedidorDTO> search(String query, Pageable pageable);
}
