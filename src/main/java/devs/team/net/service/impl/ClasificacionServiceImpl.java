package devs.team.net.service.impl;

import devs.team.net.service.ClasificacionService;
import devs.team.net.domain.Clasificacion;
import devs.team.net.repository.ClasificacionRepository;
import devs.team.net.repository.search.ClasificacionSearchRepository;
import devs.team.net.service.dto.ClasificacionDTO;
import devs.team.net.service.mapper.ClasificacionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Clasificacion.
 */
@Service
@Transactional
public class ClasificacionServiceImpl implements ClasificacionService {

    private final Logger log = LoggerFactory.getLogger(ClasificacionServiceImpl.class);

    private final ClasificacionRepository clasificacionRepository;

    private final ClasificacionMapper clasificacionMapper;

    private final ClasificacionSearchRepository clasificacionSearchRepository;

    public ClasificacionServiceImpl(ClasificacionRepository clasificacionRepository, ClasificacionMapper clasificacionMapper, ClasificacionSearchRepository clasificacionSearchRepository) {
        this.clasificacionRepository = clasificacionRepository;
        this.clasificacionMapper = clasificacionMapper;
        this.clasificacionSearchRepository = clasificacionSearchRepository;
    }

    /**
     * Save a clasificacion.
     *
     * @param clasificacionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClasificacionDTO save(ClasificacionDTO clasificacionDTO) {
        log.debug("Request to save Clasificacion : {}", clasificacionDTO);
        Clasificacion clasificacion = clasificacionMapper.toEntity(clasificacionDTO);
        clasificacion = clasificacionRepository.save(clasificacion);
        ClasificacionDTO result = clasificacionMapper.toDto(clasificacion);
        clasificacionSearchRepository.save(clasificacion);
        return result;
    }

    /**
     * Get all the clasificacions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClasificacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clasificacions");
        return clasificacionRepository.findAll(pageable)
            .map(clasificacionMapper::toDto);
    }

    /**
     * Get one clasificacion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClasificacionDTO findOne(Long id) {
        log.debug("Request to get Clasificacion : {}", id);
        Clasificacion clasificacion = clasificacionRepository.findOne(id);
        return clasificacionMapper.toDto(clasificacion);
    }

    /**
     * Delete the clasificacion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Clasificacion : {}", id);
        clasificacionRepository.delete(id);
        clasificacionSearchRepository.delete(id);
    }

    /**
     * Search for the clasificacion corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClasificacionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Clasificacions for query {}", query);
        Page<Clasificacion> result = clasificacionSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(clasificacionMapper::toDto);
    }
}
