package devs.team.net.service.impl;

import devs.team.net.service.LecturaMedidorService;
import devs.team.net.domain.LecturaMedidor;
import devs.team.net.repository.LecturaMedidorRepository;
import devs.team.net.repository.search.LecturaMedidorSearchRepository;
import devs.team.net.service.dto.LecturaMedidorDTO;
import devs.team.net.service.mapper.LecturaMedidorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing LecturaMedidor.
 */
@Service
@Transactional
public class LecturaMedidorServiceImpl implements LecturaMedidorService {

    private final Logger log = LoggerFactory.getLogger(LecturaMedidorServiceImpl.class);

    private final LecturaMedidorRepository lecturaMedidorRepository;

    private final LecturaMedidorMapper lecturaMedidorMapper;

    private final LecturaMedidorSearchRepository lecturaMedidorSearchRepository;

    public LecturaMedidorServiceImpl(LecturaMedidorRepository lecturaMedidorRepository, LecturaMedidorMapper lecturaMedidorMapper, LecturaMedidorSearchRepository lecturaMedidorSearchRepository) {
        this.lecturaMedidorRepository = lecturaMedidorRepository;
        this.lecturaMedidorMapper = lecturaMedidorMapper;
        this.lecturaMedidorSearchRepository = lecturaMedidorSearchRepository;
    }

    /**
     * Save a lecturaMedidor.
     *
     * @param lecturaMedidorDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LecturaMedidorDTO save(LecturaMedidorDTO lecturaMedidorDTO) {
        log.debug("Request to save LecturaMedidor : {}", lecturaMedidorDTO);
        LecturaMedidor lecturaMedidor = lecturaMedidorMapper.toEntity(lecturaMedidorDTO);
        lecturaMedidor = lecturaMedidorRepository.save(lecturaMedidor);
        LecturaMedidorDTO result = lecturaMedidorMapper.toDto(lecturaMedidor);
        lecturaMedidorSearchRepository.save(lecturaMedidor);
        return result;
    }

    /**
     * Get all the lecturaMedidors.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LecturaMedidorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LecturaMedidors");
        return lecturaMedidorRepository.findAll(pageable)
            .map(lecturaMedidorMapper::toDto);
    }

    /**
     * Get one lecturaMedidor by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LecturaMedidorDTO findOne(Long id) {
        log.debug("Request to get LecturaMedidor : {}", id);
        LecturaMedidor lecturaMedidor = lecturaMedidorRepository.findOneWithEagerRelationships(id);
        return lecturaMedidorMapper.toDto(lecturaMedidor);
    }

    /**
     * Delete the lecturaMedidor by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LecturaMedidor : {}", id);
        lecturaMedidorRepository.delete(id);
        lecturaMedidorSearchRepository.delete(id);
    }

    /**
     * Search for the lecturaMedidor corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LecturaMedidorDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LecturaMedidors for query {}", query);
        Page<LecturaMedidor> result = lecturaMedidorSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(lecturaMedidorMapper::toDto);
    }
}
