package devs.team.net.service.impl;

import devs.team.net.service.SectorService;
import devs.team.net.domain.Sector;
import devs.team.net.repository.SectorRepository;
import devs.team.net.repository.search.SectorSearchRepository;
import devs.team.net.service.dto.SectorDTO;
import devs.team.net.service.mapper.SectorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Sector.
 */
@Service
@Transactional
public class SectorServiceImpl implements SectorService {

    private final Logger log = LoggerFactory.getLogger(SectorServiceImpl.class);

    private final SectorRepository sectorRepository;

    private final SectorMapper sectorMapper;

    private final SectorSearchRepository sectorSearchRepository;

    public SectorServiceImpl(SectorRepository sectorRepository, SectorMapper sectorMapper, SectorSearchRepository sectorSearchRepository) {
        this.sectorRepository = sectorRepository;
        this.sectorMapper = sectorMapper;
        this.sectorSearchRepository = sectorSearchRepository;
    }

    /**
     * Save a sector.
     *
     * @param sectorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SectorDTO save(SectorDTO sectorDTO) {
        log.debug("Request to save Sector : {}", sectorDTO);
        Sector sector = sectorMapper.toEntity(sectorDTO);
        sector = sectorRepository.save(sector);
        SectorDTO result = sectorMapper.toDto(sector);
        sectorSearchRepository.save(sector);
        return result;
    }

    /**
     * Get all the sectors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SectorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sectors");
        return sectorRepository.findAll(pageable)
            .map(sectorMapper::toDto);
    }

    /**
     * Get one sector by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SectorDTO findOne(Long id) {
        log.debug("Request to get Sector : {}", id);
        Sector sector = sectorRepository.findOne(id);
        return sectorMapper.toDto(sector);
    }

    /**
     * Delete the sector by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sector : {}", id);
        sectorRepository.delete(id);
        sectorSearchRepository.delete(id);
    }

    /**
     * Search for the sector corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SectorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Sectors for query {}", query);
        Page<Sector> result = sectorSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(sectorMapper::toDto);
    }
}
