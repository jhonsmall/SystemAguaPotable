package devs.team.net.service.impl;

import devs.team.net.service.CostoMedidorService;
import devs.team.net.domain.CostoMedidor;
import devs.team.net.repository.CostoMedidorRepository;
import devs.team.net.repository.search.CostoMedidorSearchRepository;
import devs.team.net.service.dto.CostoMedidorDTO;
import devs.team.net.service.mapper.CostoMedidorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CostoMedidor.
 */
@Service
@Transactional
public class CostoMedidorServiceImpl implements CostoMedidorService {

    private final Logger log = LoggerFactory.getLogger(CostoMedidorServiceImpl.class);

    private final CostoMedidorRepository costoMedidorRepository;

    private final CostoMedidorMapper costoMedidorMapper;

    private final CostoMedidorSearchRepository costoMedidorSearchRepository;

    public CostoMedidorServiceImpl(CostoMedidorRepository costoMedidorRepository, CostoMedidorMapper costoMedidorMapper, CostoMedidorSearchRepository costoMedidorSearchRepository) {
        this.costoMedidorRepository = costoMedidorRepository;
        this.costoMedidorMapper = costoMedidorMapper;
        this.costoMedidorSearchRepository = costoMedidorSearchRepository;
    }

    /**
     * Save a costoMedidor.
     *
     * @param costoMedidorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CostoMedidorDTO save(CostoMedidorDTO costoMedidorDTO) {
        log.debug("Request to save CostoMedidor : {}", costoMedidorDTO);
        CostoMedidor costoMedidor = costoMedidorMapper.toEntity(costoMedidorDTO);
        costoMedidor = costoMedidorRepository.save(costoMedidor);
        CostoMedidorDTO result = costoMedidorMapper.toDto(costoMedidor);
        costoMedidorSearchRepository.save(costoMedidor);
        return result;
    }

    /**
     * Get all the costoMedidors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CostoMedidorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CostoMedidors");
        return costoMedidorRepository.findAll(pageable)
            .map(costoMedidorMapper::toDto);
    }

    /**
     * Get one costoMedidor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CostoMedidorDTO findOne(Long id) {
        log.debug("Request to get CostoMedidor : {}", id);
        CostoMedidor costoMedidor = costoMedidorRepository.findOne(id);
        return costoMedidorMapper.toDto(costoMedidor);
    }

    /**
     * Delete the costoMedidor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CostoMedidor : {}", id);
        costoMedidorRepository.delete(id);
        costoMedidorSearchRepository.delete(id);
    }

    /**
     * Search for the costoMedidor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CostoMedidorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CostoMedidors for query {}", query);
        Page<CostoMedidor> result = costoMedidorSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(costoMedidorMapper::toDto);
    }
}
