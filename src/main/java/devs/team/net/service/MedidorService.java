package devs.team.net.service;

import devs.team.net.service.dto.MedidorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Medidor.
 */
public interface MedidorService {

    /**
     * Save a medidor.
     *
     * @param medidorDTO the entity to save
     * @return the persisted entity
     */
    MedidorDTO save(MedidorDTO medidorDTO);

    /**
     * Get all the medidors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MedidorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" medidor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MedidorDTO findOne(Long id);

    /**
     * Delete the "id" medidor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the medidor corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MedidorDTO> search(String query, Pageable pageable);
}
