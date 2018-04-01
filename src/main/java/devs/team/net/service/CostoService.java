package devs.team.net.service;

import devs.team.net.service.dto.CostoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Costo.
 */
public interface CostoService {

    /**
     * Save a costo.
     *
     * @param costoDTO the entity to save
     * @return the persisted entity
     */
    CostoDTO save(CostoDTO costoDTO);

    /**
     * Get all the costos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CostoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" costo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CostoDTO findOne(Long id);

    /**
     * Delete the "id" costo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the costo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CostoDTO> search(String query, Pageable pageable);
}
