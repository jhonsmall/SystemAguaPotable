package devs.team.net.service;

import devs.team.net.service.dto.SectorDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Sector.
 */
public interface SectorService {

    /**
     * Save a sector.
     *
     * @param sectorDTO the entity to save
     * @return the persisted entity
     */
    SectorDTO save(SectorDTO sectorDTO);

    /**
     * Get all the sectors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SectorDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sector.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SectorDTO findOne(Long id);

    /**
     * Delete the "id" sector.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the sector corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SectorDTO> search(String query, Pageable pageable);
}
