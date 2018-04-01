package devs.team.net.service.impl;

import devs.team.net.service.EscalasDelMedidorService;
import devs.team.net.domain.EscalasDelMedidor;
import devs.team.net.repository.EscalasDelMedidorRepository;
import devs.team.net.repository.search.EscalasDelMedidorSearchRepository;
import devs.team.net.service.dto.EscalasDelMedidorDTO;
import devs.team.net.service.mapper.EscalasDelMedidorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing EscalasDelMedidor.
 */
@Service
@Transactional
public class EscalasDelMedidorServiceImpl implements EscalasDelMedidorService {

    private final Logger log = LoggerFactory.getLogger(EscalasDelMedidorServiceImpl.class);

    private final EscalasDelMedidorRepository escalasDelMedidorRepository;

    private final EscalasDelMedidorMapper escalasDelMedidorMapper;

    private final EscalasDelMedidorSearchRepository escalasDelMedidorSearchRepository;

    public EscalasDelMedidorServiceImpl(EscalasDelMedidorRepository escalasDelMedidorRepository, EscalasDelMedidorMapper escalasDelMedidorMapper, EscalasDelMedidorSearchRepository escalasDelMedidorSearchRepository) {
        this.escalasDelMedidorRepository = escalasDelMedidorRepository;
        this.escalasDelMedidorMapper = escalasDelMedidorMapper;
        this.escalasDelMedidorSearchRepository = escalasDelMedidorSearchRepository;
    }

    /**
     * Save a escalasDelMedidor.
     *
     * @param escalasDelMedidorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EscalasDelMedidorDTO save(EscalasDelMedidorDTO escalasDelMedidorDTO) {
        log.debug("Request to save EscalasDelMedidor : {}", escalasDelMedidorDTO);
        EscalasDelMedidor escalasDelMedidor = escalasDelMedidorMapper.toEntity(escalasDelMedidorDTO);
        escalasDelMedidor = escalasDelMedidorRepository.save(escalasDelMedidor);
        EscalasDelMedidorDTO result = escalasDelMedidorMapper.toDto(escalasDelMedidor);
        escalasDelMedidorSearchRepository.save(escalasDelMedidor);
        return result;
    }

    /**
     * Get all the escalasDelMedidors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EscalasDelMedidorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EscalasDelMedidors");
        return escalasDelMedidorRepository.findAll(pageable)
            .map(escalasDelMedidorMapper::toDto);
    }

    /**
     * Get one escalasDelMedidor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EscalasDelMedidorDTO findOne(Long id) {
        log.debug("Request to get EscalasDelMedidor : {}", id);
        EscalasDelMedidor escalasDelMedidor = escalasDelMedidorRepository.findOne(id);
        return escalasDelMedidorMapper.toDto(escalasDelMedidor);
    }

    /**
     * Delete the escalasDelMedidor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EscalasDelMedidor : {}", id);
        escalasDelMedidorRepository.delete(id);
        escalasDelMedidorSearchRepository.delete(id);
    }

    /**
     * Search for the escalasDelMedidor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EscalasDelMedidorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of EscalasDelMedidors for query {}", query);
        Page<EscalasDelMedidor> result = escalasDelMedidorSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(escalasDelMedidorMapper::toDto);
    }
}
