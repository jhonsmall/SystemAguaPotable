package devs.team.net.service.impl;

import devs.team.net.service.CostoService;
import devs.team.net.domain.Costo;
import devs.team.net.repository.CostoRepository;
import devs.team.net.repository.search.CostoSearchRepository;
import devs.team.net.service.dto.CostoDTO;
import devs.team.net.service.mapper.CostoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Costo.
 */
@Service
@Transactional
public class CostoServiceImpl implements CostoService {

    private final Logger log = LoggerFactory.getLogger(CostoServiceImpl.class);

    private final CostoRepository costoRepository;

    private final CostoMapper costoMapper;

    private final CostoSearchRepository costoSearchRepository;

    public CostoServiceImpl(CostoRepository costoRepository, CostoMapper costoMapper, CostoSearchRepository costoSearchRepository) {
        this.costoRepository = costoRepository;
        this.costoMapper = costoMapper;
        this.costoSearchRepository = costoSearchRepository;
    }

    /**
     * Save a costo.
     *
     * @param costoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CostoDTO save(CostoDTO costoDTO) {
        log.debug("Request to save Costo : {}", costoDTO);
        Costo costo = costoMapper.toEntity(costoDTO);
        costo = costoRepository.save(costo);
        CostoDTO result = costoMapper.toDto(costo);
        costoSearchRepository.save(costo);
        return result;
    }

    /**
     * Get all the costos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CostoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Costos");
        return costoRepository.findAll(pageable)
            .map(costoMapper::toDto);
    }

    /**
     * Get one costo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CostoDTO findOne(Long id) {
        log.debug("Request to get Costo : {}", id);
        Costo costo = costoRepository.findOne(id);
        return costoMapper.toDto(costo);
    }

    /**
     * Delete the costo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Costo : {}", id);
        costoRepository.delete(id);
        costoSearchRepository.delete(id);
    }

    /**
     * Search for the costo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CostoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Costos for query {}", query);
        Page<Costo> result = costoSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(costoMapper::toDto);
    }
}
