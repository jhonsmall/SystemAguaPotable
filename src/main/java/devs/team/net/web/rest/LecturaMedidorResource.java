package devs.team.net.web.rest;

import com.codahale.metrics.annotation.Timed;
import devs.team.net.service.LecturaMedidorService;
import devs.team.net.web.rest.errors.BadRequestAlertException;
import devs.team.net.web.rest.util.HeaderUtil;
import devs.team.net.web.rest.util.PaginationUtil;
import devs.team.net.service.dto.LecturaMedidorDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing LecturaMedidor.
 */
@RestController
@RequestMapping("/api")
public class LecturaMedidorResource {

    private final Logger log = LoggerFactory.getLogger(LecturaMedidorResource.class);

    private static final String ENTITY_NAME = "lecturaMedidor";

    private final LecturaMedidorService lecturaMedidorService;

    public LecturaMedidorResource(LecturaMedidorService lecturaMedidorService) {
        this.lecturaMedidorService = lecturaMedidorService;
    }

    /**
     * POST  /lectura-medidors : Create a new lecturaMedidor.
     *
     * @param lecturaMedidorDTO the lecturaMedidorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lecturaMedidorDTO, or with status 400 (Bad Request) if the lecturaMedidor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lectura-medidors")
    @Timed
    public ResponseEntity<LecturaMedidorDTO> createLecturaMedidor(@Valid @RequestBody LecturaMedidorDTO lecturaMedidorDTO) throws URISyntaxException {
        log.debug("REST request to save LecturaMedidor : {}", lecturaMedidorDTO);
        if (lecturaMedidorDTO.getId() != null) {
            throw new BadRequestAlertException("A new lecturaMedidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LecturaMedidorDTO result = lecturaMedidorService.save(lecturaMedidorDTO);
        return ResponseEntity.created(new URI("/api/lectura-medidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lectura-medidors : Updates an existing lecturaMedidor.
     *
     * @param lecturaMedidorDTO the lecturaMedidorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lecturaMedidorDTO,
     * or with status 400 (Bad Request) if the lecturaMedidorDTO is not valid,
     * or with status 500 (Internal Server Error) if the lecturaMedidorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lectura-medidors")
    @Timed
    public ResponseEntity<LecturaMedidorDTO> updateLecturaMedidor(@Valid @RequestBody LecturaMedidorDTO lecturaMedidorDTO) throws URISyntaxException {
        log.debug("REST request to update LecturaMedidor : {}", lecturaMedidorDTO);
        if (lecturaMedidorDTO.getId() == null) {
            return createLecturaMedidor(lecturaMedidorDTO);
        }
        LecturaMedidorDTO result = lecturaMedidorService.save(lecturaMedidorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lecturaMedidorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lectura-medidors : get all the lecturaMedidors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of lecturaMedidors in body
     */
    @GetMapping("/lectura-medidors")
    @Timed
    public ResponseEntity<List<LecturaMedidorDTO>> getAllLecturaMedidors(Pageable pageable) {
        log.debug("REST request to get a page of LecturaMedidors");
        Page<LecturaMedidorDTO> page = lecturaMedidorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lectura-medidors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lectura-medidors/:id : get the "id" lecturaMedidor.
     *
     * @param id the id of the lecturaMedidorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lecturaMedidorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/lectura-medidors/{id}")
    @Timed
    public ResponseEntity<LecturaMedidorDTO> getLecturaMedidor(@PathVariable Long id) {
        log.debug("REST request to get LecturaMedidor : {}", id);
        LecturaMedidorDTO lecturaMedidorDTO = lecturaMedidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lecturaMedidorDTO));
    }

    /**
     * DELETE  /lectura-medidors/:id : delete the "id" lecturaMedidor.
     *
     * @param id the id of the lecturaMedidorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lectura-medidors/{id}")
    @Timed
    public ResponseEntity<Void> deleteLecturaMedidor(@PathVariable Long id) {
        log.debug("REST request to delete LecturaMedidor : {}", id);
        lecturaMedidorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/lectura-medidors?query=:query : search for the lecturaMedidor corresponding
     * to the query.
     *
     * @param query the query of the lecturaMedidor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/lectura-medidors")
    @Timed
    public ResponseEntity<List<LecturaMedidorDTO>> searchLecturaMedidors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LecturaMedidors for query {}", query);
        Page<LecturaMedidorDTO> page = lecturaMedidorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/lectura-medidors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
