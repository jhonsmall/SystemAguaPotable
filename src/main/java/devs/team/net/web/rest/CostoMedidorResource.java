package devs.team.net.web.rest;

import com.codahale.metrics.annotation.Timed;
import devs.team.net.service.CostoMedidorService;
import devs.team.net.web.rest.errors.BadRequestAlertException;
import devs.team.net.web.rest.util.HeaderUtil;
import devs.team.net.web.rest.util.PaginationUtil;
import devs.team.net.service.dto.CostoMedidorDTO;
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
 * REST controller for managing CostoMedidor.
 */
@RestController
@RequestMapping("/api")
public class CostoMedidorResource {

    private final Logger log = LoggerFactory.getLogger(CostoMedidorResource.class);

    private static final String ENTITY_NAME = "costoMedidor";

    private final CostoMedidorService costoMedidorService;

    public CostoMedidorResource(CostoMedidorService costoMedidorService) {
        this.costoMedidorService = costoMedidorService;
    }

    /**
     * POST  /costo-medidors : Create a new costoMedidor.
     *
     * @param costoMedidorDTO the costoMedidorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new costoMedidorDTO, or with status 400 (Bad Request) if the costoMedidor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/costo-medidors")
    @Timed
    public ResponseEntity<CostoMedidorDTO> createCostoMedidor(@Valid @RequestBody CostoMedidorDTO costoMedidorDTO) throws URISyntaxException {
        log.debug("REST request to save CostoMedidor : {}", costoMedidorDTO);
        if (costoMedidorDTO.getId() != null) {
            throw new BadRequestAlertException("A new costoMedidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CostoMedidorDTO result = costoMedidorService.save(costoMedidorDTO);
        return ResponseEntity.created(new URI("/api/costo-medidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /costo-medidors : Updates an existing costoMedidor.
     *
     * @param costoMedidorDTO the costoMedidorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated costoMedidorDTO,
     * or with status 400 (Bad Request) if the costoMedidorDTO is not valid,
     * or with status 500 (Internal Server Error) if the costoMedidorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/costo-medidors")
    @Timed
    public ResponseEntity<CostoMedidorDTO> updateCostoMedidor(@Valid @RequestBody CostoMedidorDTO costoMedidorDTO) throws URISyntaxException {
        log.debug("REST request to update CostoMedidor : {}", costoMedidorDTO);
        if (costoMedidorDTO.getId() == null) {
            return createCostoMedidor(costoMedidorDTO);
        }
        CostoMedidorDTO result = costoMedidorService.save(costoMedidorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, costoMedidorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /costo-medidors : get all the costoMedidors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of costoMedidors in body
     */
    @GetMapping("/costo-medidors")
    @Timed
    public ResponseEntity<List<CostoMedidorDTO>> getAllCostoMedidors(Pageable pageable) {
        log.debug("REST request to get a page of CostoMedidors");
        Page<CostoMedidorDTO> page = costoMedidorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/costo-medidors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /costo-medidors/:id : get the "id" costoMedidor.
     *
     * @param id the id of the costoMedidorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the costoMedidorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/costo-medidors/{id}")
    @Timed
    public ResponseEntity<CostoMedidorDTO> getCostoMedidor(@PathVariable Long id) {
        log.debug("REST request to get CostoMedidor : {}", id);
        CostoMedidorDTO costoMedidorDTO = costoMedidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(costoMedidorDTO));
    }

    /**
     * DELETE  /costo-medidors/:id : delete the "id" costoMedidor.
     *
     * @param id the id of the costoMedidorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/costo-medidors/{id}")
    @Timed
    public ResponseEntity<Void> deleteCostoMedidor(@PathVariable Long id) {
        log.debug("REST request to delete CostoMedidor : {}", id);
        costoMedidorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/costo-medidors?query=:query : search for the costoMedidor corresponding
     * to the query.
     *
     * @param query the query of the costoMedidor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/costo-medidors")
    @Timed
    public ResponseEntity<List<CostoMedidorDTO>> searchCostoMedidors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CostoMedidors for query {}", query);
        Page<CostoMedidorDTO> page = costoMedidorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/costo-medidors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
