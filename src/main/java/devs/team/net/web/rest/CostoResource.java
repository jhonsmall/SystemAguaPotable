package devs.team.net.web.rest;

import com.codahale.metrics.annotation.Timed;
import devs.team.net.service.CostoService;
import devs.team.net.web.rest.errors.BadRequestAlertException;
import devs.team.net.web.rest.util.HeaderUtil;
import devs.team.net.web.rest.util.PaginationUtil;
import devs.team.net.service.dto.CostoDTO;
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
 * REST controller for managing Costo.
 */
@RestController
@RequestMapping("/api")
public class CostoResource {

    private final Logger log = LoggerFactory.getLogger(CostoResource.class);

    private static final String ENTITY_NAME = "costo";

    private final CostoService costoService;

    public CostoResource(CostoService costoService) {
        this.costoService = costoService;
    }

    /**
     * POST  /costos : Create a new costo.
     *
     * @param costoDTO the costoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new costoDTO, or with status 400 (Bad Request) if the costo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/costos")
    @Timed
    public ResponseEntity<CostoDTO> createCosto(@Valid @RequestBody CostoDTO costoDTO) throws URISyntaxException {
        log.debug("REST request to save Costo : {}", costoDTO);
        if (costoDTO.getId() != null) {
            throw new BadRequestAlertException("A new costo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CostoDTO result = costoService.save(costoDTO);
        return ResponseEntity.created(new URI("/api/costos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /costos : Updates an existing costo.
     *
     * @param costoDTO the costoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated costoDTO,
     * or with status 400 (Bad Request) if the costoDTO is not valid,
     * or with status 500 (Internal Server Error) if the costoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/costos")
    @Timed
    public ResponseEntity<CostoDTO> updateCosto(@Valid @RequestBody CostoDTO costoDTO) throws URISyntaxException {
        log.debug("REST request to update Costo : {}", costoDTO);
        if (costoDTO.getId() == null) {
            return createCosto(costoDTO);
        }
        CostoDTO result = costoService.save(costoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, costoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /costos : get all the costos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of costos in body
     */
    @GetMapping("/costos")
    @Timed
    public ResponseEntity<List<CostoDTO>> getAllCostos(Pageable pageable) {
        log.debug("REST request to get a page of Costos");
        Page<CostoDTO> page = costoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/costos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /costos/:id : get the "id" costo.
     *
     * @param id the id of the costoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the costoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/costos/{id}")
    @Timed
    public ResponseEntity<CostoDTO> getCosto(@PathVariable Long id) {
        log.debug("REST request to get Costo : {}", id);
        CostoDTO costoDTO = costoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(costoDTO));
    }

    /**
     * DELETE  /costos/:id : delete the "id" costo.
     *
     * @param id the id of the costoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/costos/{id}")
    @Timed
    public ResponseEntity<Void> deleteCosto(@PathVariable Long id) {
        log.debug("REST request to delete Costo : {}", id);
        costoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/costos?query=:query : search for the costo corresponding
     * to the query.
     *
     * @param query the query of the costo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/costos")
    @Timed
    public ResponseEntity<List<CostoDTO>> searchCostos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Costos for query {}", query);
        Page<CostoDTO> page = costoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/costos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
