package devs.team.net.web.rest;

import com.codahale.metrics.annotation.Timed;
import devs.team.net.service.ClasificacionService;
import devs.team.net.web.rest.errors.BadRequestAlertException;
import devs.team.net.web.rest.util.HeaderUtil;
import devs.team.net.web.rest.util.PaginationUtil;
import devs.team.net.service.dto.ClasificacionDTO;
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
 * REST controller for managing Clasificacion.
 */
@RestController
@RequestMapping("/api")
public class ClasificacionResource {

    private final Logger log = LoggerFactory.getLogger(ClasificacionResource.class);

    private static final String ENTITY_NAME = "clasificacion";

    private final ClasificacionService clasificacionService;

    public ClasificacionResource(ClasificacionService clasificacionService) {
        this.clasificacionService = clasificacionService;
    }

    /**
     * POST  /clasificacions : Create a new clasificacion.
     *
     * @param clasificacionDTO the clasificacionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clasificacionDTO, or with status 400 (Bad Request) if the clasificacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clasificacions")
    @Timed
    public ResponseEntity<ClasificacionDTO> createClasificacion(@Valid @RequestBody ClasificacionDTO clasificacionDTO) throws URISyntaxException {
        log.debug("REST request to save Clasificacion : {}", clasificacionDTO);
        if (clasificacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new clasificacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClasificacionDTO result = clasificacionService.save(clasificacionDTO);
        return ResponseEntity.created(new URI("/api/clasificacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clasificacions : Updates an existing clasificacion.
     *
     * @param clasificacionDTO the clasificacionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clasificacionDTO,
     * or with status 400 (Bad Request) if the clasificacionDTO is not valid,
     * or with status 500 (Internal Server Error) if the clasificacionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clasificacions")
    @Timed
    public ResponseEntity<ClasificacionDTO> updateClasificacion(@Valid @RequestBody ClasificacionDTO clasificacionDTO) throws URISyntaxException {
        log.debug("REST request to update Clasificacion : {}", clasificacionDTO);
        if (clasificacionDTO.getId() == null) {
            return createClasificacion(clasificacionDTO);
        }
        ClasificacionDTO result = clasificacionService.save(clasificacionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clasificacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clasificacions : get all the clasificacions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clasificacions in body
     */
    @GetMapping("/clasificacions")
    @Timed
    public ResponseEntity<List<ClasificacionDTO>> getAllClasificacions(Pageable pageable) {
        log.debug("REST request to get a page of Clasificacions");
        Page<ClasificacionDTO> page = clasificacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clasificacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clasificacions/:id : get the "id" clasificacion.
     *
     * @param id the id of the clasificacionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clasificacionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/clasificacions/{id}")
    @Timed
    public ResponseEntity<ClasificacionDTO> getClasificacion(@PathVariable Long id) {
        log.debug("REST request to get Clasificacion : {}", id);
        ClasificacionDTO clasificacionDTO = clasificacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(clasificacionDTO));
    }

    /**
     * DELETE  /clasificacions/:id : delete the "id" clasificacion.
     *
     * @param id the id of the clasificacionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clasificacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteClasificacion(@PathVariable Long id) {
        log.debug("REST request to delete Clasificacion : {}", id);
        clasificacionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/clasificacions?query=:query : search for the clasificacion corresponding
     * to the query.
     *
     * @param query the query of the clasificacion search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/clasificacions")
    @Timed
    public ResponseEntity<List<ClasificacionDTO>> searchClasificacions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Clasificacions for query {}", query);
        Page<ClasificacionDTO> page = clasificacionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/clasificacions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
