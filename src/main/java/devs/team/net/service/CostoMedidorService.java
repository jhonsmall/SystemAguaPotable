package devs.team.net.service;

import devs.team.net.service.dto.CostoMedidorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CostoMedidor.
 */
public interface CostoMedidorService {

    /**
     * Save a costoMedidor.
     *
     * @param costoMedidorDTO the entity to save
     * @return the persisted entity
     */
    CostoMedidorDTO save(CostoMedidorDTO costoMedidorDTO);

    /**
     * Get all the costoMedidors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CostoMedidorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" costoMedidor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CostoMedidorDTO findOne(Long id);

    /**
     * Delete the "id" costoMedidor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the costoMedidor corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CostoMedidorDTO> search(String query, Pageable pageable);
}
