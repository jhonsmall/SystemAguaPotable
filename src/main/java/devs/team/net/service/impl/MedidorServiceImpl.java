package devs.team.net.service.impl;

import devs.team.net.service.MedidorService;
import devs.team.net.domain.Medidor;
import devs.team.net.repository.MedidorRepository;
import devs.team.net.repository.search.MedidorSearchRepository;
import devs.team.net.service.dto.MedidorDTO;
import devs.team.net.service.mapper.MedidorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Medidor.
 */
@Service
@Transactional
public class MedidorServiceImpl implements MedidorService {

    private final Logger log = LoggerFactory.getLogger(MedidorServiceImpl.class);

    private final MedidorRepository medidorRepository;

    private final MedidorMapper medidorMapper;

    private final MedidorSearchRepository medidorSearchRepository;

    public MedidorServiceImpl(MedidorRepository medidorRepository, MedidorMapper medidorMapper, MedidorSearchRepository medidorSearchRepository) {
        this.medidorRepository = medidorRepository;
        this.medidorMapper = medidorMapper;
        this.medidorSearchRepository = medidorSearchRepository;
    }

    /**
     * Save a medidor.
     *
     * @param medidorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MedidorDTO save(MedidorDTO medidorDTO) {
        log.debug("Request to save Medidor : {}", medidorDTO);
        Medidor medidor = medidorMapper.toEntity(medidorDTO);
        medidor = medidorRepository.save(medidor);
        MedidorDTO result = medidorMapper.toDto(medidor);
        medidorSearchRepository.save(medidor);
        return result;
    }

    /**
     * Get all the medidors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedidorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Medidors");
        return medidorRepository.findAll(pageable)
            .map(medidorMapper::toDto);
    }

    /**
     * Get one medidor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MedidorDTO findOne(Long id) {
        log.debug("Request to get Medidor : {}", id);
        Medidor medidor = medidorRepository.findOne(id);
        return medidorMapper.toDto(medidor);
    }

    /**
     * Delete the medidor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medidor : {}", id);
        medidorRepository.delete(id);
        medidorSearchRepository.delete(id);
    }

    /**
     * Search for the medidor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedidorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Medidors for query {}", query);
        Page<Medidor> result = medidorSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(medidorMapper::toDto);
    }
}
