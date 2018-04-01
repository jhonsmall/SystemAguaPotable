package devs.team.net.service;

import devs.team.net.service.dto.EscalasDelMedidorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EscalasDelMedidor.
 */
public interface EscalasDelMedidorService {

    /**
     * Save a escalasDelMedidor.
     *
     * @param escalasDelMedidorDTO the entity to save
     * @return the persisted entity
     */
    EscalasDelMedidorDTO save(EscalasDelMedidorDTO escalasDelMedidorDTO);

    /**
     * Get all the escalasDelMedidors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EscalasDelMedidorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" escalasDelMedidor.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EscalasDelMedidorDTO findOne(Long id);

    /**
     * Delete the "id" escalasDelMedidor.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the escalasDelMedidor corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EscalasDelMedidorDTO> search(String query, Pageable pageable);
}
