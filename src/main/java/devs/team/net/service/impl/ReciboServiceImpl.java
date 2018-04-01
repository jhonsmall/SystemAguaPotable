package devs.team.net.service.impl;

import devs.team.net.service.ReciboService;
import devs.team.net.domain.Recibo;
import devs.team.net.repository.ReciboRepository;
import devs.team.net.repository.search.ReciboSearchRepository;
import devs.team.net.service.dto.ReciboDTO;
import devs.team.net.service.mapper.ReciboMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Recibo.
 */
@Service
@Transactional
public class ReciboServiceImpl implements ReciboService {

    private final Logger log = LoggerFactory.getLogger(ReciboServiceImpl.class);

    private final ReciboRepository reciboRepository;

    private final ReciboMapper reciboMapper;

    private final ReciboSearchRepository reciboSearchRepository;

    public ReciboServiceImpl(ReciboRepository reciboRepository, ReciboMapper reciboMapper, ReciboSearchRepository reciboSearchRepository) {
        this.reciboRepository = reciboRepository;
        this.reciboMapper = reciboMapper;
        this.reciboSearchRepository = reciboSearchRepository;
    }

    /**
     * Save a recibo.
     *
     * @param reciboDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ReciboDTO save(ReciboDTO reciboDTO) {
        log.debug("Request to save Recibo : {}", reciboDTO);
        Recibo recibo = reciboMapper.toEntity(reciboDTO);
        recibo = reciboRepository.save(recibo);
        ReciboDTO result = reciboMapper.toDto(recibo);
        reciboSearchRepository.save(recibo);
        return result;
    }

    /**
     * Get all the recibos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReciboDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recibos");
        return reciboRepository.findAll(pageable)
            .map(reciboMapper::toDto);
    }

    /**
     * Get one recibo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ReciboDTO findOne(Long id) {
        log.debug("Request to get Recibo : {}", id);
        Recibo recibo = reciboRepository.findOne(id);
        return reciboMapper.toDto(recibo);
    }

    /**
     * Delete the recibo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recibo : {}", id);
        reciboRepository.delete(id);
        reciboSearchRepository.delete(id);
    }

    /**
     * Search for the recibo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ReciboDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Recibos for query {}", query);
        Page<Recibo> result = reciboSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(reciboMapper::toDto);
    }
}
