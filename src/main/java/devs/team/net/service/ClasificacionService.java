package devs.team.net.service;

import devs.team.net.service.dto.ClasificacionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Clasificacion.
 */
public interface ClasificacionService {

    /**
     * Save a clasificacion.
     *
     * @param clasificacionDTO the entity to save
     * @return the persisted entity
     */
    ClasificacionDTO save(ClasificacionDTO clasificacionDTO);

    /**
     * Get all the clasificacions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClasificacionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" clasificacion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ClasificacionDTO findOne(Long id);

    /**
     * Delete the "id" clasificacion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the clasificacion corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClasificacionDTO> search(String query, Pageable pageable);
}
