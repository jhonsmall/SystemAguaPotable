package devs.team.net.web.rest;

import com.codahale.metrics.annotation.Timed;
import devs.team.net.service.MedidorService;
import devs.team.net.web.rest.errors.BadRequestAlertException;
import devs.team.net.web.rest.util.HeaderUtil;
import devs.team.net.web.rest.util.PaginationUtil;
import devs.team.net.service.dto.MedidorDTO;
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
 * REST controller for managing Medidor.
 */
@RestController
@RequestMapping("/api")
public class MedidorResource {

    private final Logger log = LoggerFactory.getLogger(MedidorResource.class);

    private static final String ENTITY_NAME = "medidor";

    private final MedidorService medidorService;

    public MedidorResource(MedidorService medidorService) {
        this.medidorService = medidorService;
    }

    /**
     * POST  /medidors : Create a new medidor.
     *
     * @param medidorDTO the medidorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medidorDTO, or with status 400 (Bad Request) if the medidor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medidors")
    @Timed
    public ResponseEntity<MedidorDTO> createMedidor(@Valid @RequestBody MedidorDTO medidorDTO) throws URISyntaxException {
        log.debug("REST request to save Medidor : {}", medidorDTO);
        if (medidorDTO.getId() != null) {
            throw new BadRequestAlertException("A new medidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedidorDTO result = medidorService.save(medidorDTO);
        return ResponseEntity.created(new URI("/api/medidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medidors : Updates an existing medidor.
     *
     * @param medidorDTO the medidorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medidorDTO,
     * or with status 400 (Bad Request) if the medidorDTO is not valid,
     * or with status 500 (Internal Server Error) if the medidorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medidors")
    @Timed
    public ResponseEntity<MedidorDTO> updateMedidor(@Valid @RequestBody MedidorDTO medidorDTO) throws URISyntaxException {
        log.debug("REST request to update Medidor : {}", medidorDTO);
        if (medidorDTO.getId() == null) {
            return createMedidor(medidorDTO);
        }
        MedidorDTO result = medidorService.save(medidorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medidorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medidors : get all the medidors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of medidors in body
     */
    @GetMapping("/medidors")
    @Timed
    public ResponseEntity<List<MedidorDTO>> getAllMedidors(Pageable pageable) {
        log.debug("REST request to get a page of Medidors");
        Page<MedidorDTO> page = medidorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/medidors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /medidors/:id : get the "id" medidor.
     *
     * @param id the id of the medidorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medidorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/medidors/{id}")
    @Timed
    public ResponseEntity<MedidorDTO> getMedidor(@PathVariable Long id) {
        log.debug("REST request to get Medidor : {}", id);
        MedidorDTO medidorDTO = medidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medidorDTO));
    }

    /**
     * DELETE  /medidors/:id : delete the "id" medidor.
     *
     * @param id the id of the medidorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medidors/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedidor(@PathVariable Long id) {
        log.debug("REST request to delete Medidor : {}", id);
        medidorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/medidors?query=:query : search for the medidor corresponding
     * to the query.
     *
     * @param query the query of the medidor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/medidors")
    @Timed
    public ResponseEntity<List<MedidorDTO>> searchMedidors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Medidors for query {}", query);
        Page<MedidorDTO> page = medidorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/medidors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
