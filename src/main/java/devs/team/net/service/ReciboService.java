package devs.team.net.service;

import devs.team.net.service.dto.ReciboDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Recibo.
 */
public interface ReciboService {

    /**
     * Save a recibo.
     *
     * @param reciboDTO the entity to save
     * @return the persisted entity
     */
    ReciboDTO save(ReciboDTO reciboDTO);

    /**
     * Get all the recibos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ReciboDTO> findAll(Pageable pageable);

    /**
     * Get the "id" recibo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ReciboDTO findOne(Long id);

    /**
     * Delete the "id" recibo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the recibo corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ReciboDTO> search(String query, Pageable pageable);
}
