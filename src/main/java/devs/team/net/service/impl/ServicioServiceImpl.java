package devs.team.net.service.impl;

import devs.team.net.service.ServicioService;
import devs.team.net.domain.Servicio;
import devs.team.net.repository.ServicioRepository;
import devs.team.net.repository.search.ServicioSearchRepository;
import devs.team.net.service.dto.ServicioDTO;
import devs.team.net.service.mapper.ServicioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Servicio.
 */
@Service
@Transactional
public class ServicioServiceImpl implements ServicioService {

    private final Logger log = LoggerFactory.getLogger(ServicioServiceImpl.class);

    private final ServicioRepository servicioRepository;

    private final ServicioMapper servicioMapper;

    private final ServicioSearchRepository servicioSearchRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository, ServicioMapper servicioMapper, ServicioSearchRepository servicioSearchRepository) {
        this.servicioRepository = servicioRepository;
        this.servicioMapper = servicioMapper;
        this.servicioSearchRepository = servicioSearchRepository;
    }

    /**
     * Save a servicio.
     *
     * @param servicioDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ServicioDTO save(ServicioDTO servicioDTO) {
        log.debug("Request to save Servicio : {}", servicioDTO);
        Servicio servicio = servicioMapper.toEntity(servicioDTO);
        servicio = servicioRepository.save(servicio);
        ServicioDTO result = servicioMapper.toDto(servicio);
        servicioSearchRepository.save(servicio);
        return result;
    }

    /**
     * Get all the servicios.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServicioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Servicios");
        return servicioRepository.findAll(pageable)
            .map(servicioMapper::toDto);
    }

    /**
     * Get one servicio by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ServicioDTO findOne(Long id) {
        log.debug("Request to get Servicio : {}", id);
        Servicio servicio = servicioRepository.findOne(id);
        return servicioMapper.toDto(servicio);
    }

    /**
     * Delete the servicio by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Servicio : {}", id);
        servicioRepository.delete(id);
        servicioSearchRepository.delete(id);
    }

    /**
     * Search for the servicio corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServicioDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Servicios for query {}", query);
        Page<Servicio> result = servicioSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(servicioMapper::toDto);
    }
}
