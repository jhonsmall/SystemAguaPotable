package devs.team.net.web.rest;

import com.codahale.metrics.annotation.Timed;
import devs.team.net.service.EscalasDelMedidorService;
import devs.team.net.web.rest.errors.BadRequestAlertException;
import devs.team.net.web.rest.util.HeaderUtil;
import devs.team.net.web.rest.util.PaginationUtil;
import devs.team.net.service.dto.EscalasDelMedidorDTO;
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
 * REST controller for managing EscalasDelMedidor.
 */
@RestController
@RequestMapping("/api")
public class EscalasDelMedidorResource {

    private final Logger log = LoggerFactory.getLogger(EscalasDelMedidorResource.class);

    private static final String ENTITY_NAME = "escalasDelMedidor";

    private final EscalasDelMedidorService escalasDelMedidorService;

    public EscalasDelMedidorResource(EscalasDelMedidorService escalasDelMedidorService) {
        this.escalasDelMedidorService = escalasDelMedidorService;
    }

    /**
     * POST  /escalas-del-medidors : Create a new escalasDelMedidor.
     *
     * @param escalasDelMedidorDTO the escalasDelMedidorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new escalasDelMedidorDTO, or with status 400 (Bad Request) if the escalasDelMedidor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/escalas-del-medidors")
    @Timed
    public ResponseEntity<EscalasDelMedidorDTO> createEscalasDelMedidor(@Valid @RequestBody EscalasDelMedidorDTO escalasDelMedidorDTO) throws URISyntaxException {
        log.debug("REST request to save EscalasDelMedidor : {}", escalasDelMedidorDTO);
        if (escalasDelMedidorDTO.getId() != null) {
            throw new BadRequestAlertException("A new escalasDelMedidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EscalasDelMedidorDTO result = escalasDelMedidorService.save(escalasDelMedidorDTO);
        return ResponseEntity.created(new URI("/api/escalas-del-medidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /escalas-del-medidors : Updates an existing escalasDelMedidor.
     *
     * @param escalasDelMedidorDTO the escalasDelMedidorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated escalasDelMedidorDTO,
     * or with status 400 (Bad Request) if the escalasDelMedidorDTO is not valid,
     * or with status 500 (Internal Server Error) if the escalasDelMedidorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/escalas-del-medidors")
    @Timed
    public ResponseEntity<EscalasDelMedidorDTO> updateEscalasDelMedidor(@Valid @RequestBody EscalasDelMedidorDTO escalasDelMedidorDTO) throws URISyntaxException {
        log.debug("REST request to update EscalasDelMedidor : {}", escalasDelMedidorDTO);
        if (escalasDelMedidorDTO.getId() == null) {
            return createEscalasDelMedidor(escalasDelMedidorDTO);
        }
        EscalasDelMedidorDTO result = escalasDelMedidorService.save(escalasDelMedidorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, escalasDelMedidorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /escalas-del-medidors : get all the escalasDelMedidors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of escalasDelMedidors in body
     */
    @GetMapping("/escalas-del-medidors")
    @Timed
    public ResponseEntity<List<EscalasDelMedidorDTO>> getAllEscalasDelMedidors(Pageable pageable) {
        log.debug("REST request to get a page of EscalasDelMedidors");
        Page<EscalasDelMedidorDTO> page = escalasDelMedidorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/escalas-del-medidors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /escalas-del-medidors/:id : get the "id" escalasDelMedidor.
     *
     * @param id the id of the escalasDelMedidorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the escalasDelMedidorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/escalas-del-medidors/{id}")
    @Timed
    public ResponseEntity<EscalasDelMedidorDTO> getEscalasDelMedidor(@PathVariable Long id) {
        log.debug("REST request to get EscalasDelMedidor : {}", id);
        EscalasDelMedidorDTO escalasDelMedidorDTO = escalasDelMedidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(escalasDelMedidorDTO));
    }

    /**
     * DELETE  /escalas-del-medidors/:id : delete the "id" escalasDelMedidor.
     *
     * @param id the id of the escalasDelMedidorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/escalas-del-medidors/{id}")
    @Timed
    public ResponseEntity<Void> deleteEscalasDelMedidor(@PathVariable Long id) {
        log.debug("REST request to delete EscalasDelMedidor : {}", id);
        escalasDelMedidorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/escalas-del-medidors?query=:query : search for the escalasDelMedidor corresponding
     * to the query.
     *
     * @param query the query of the escalasDelMedidor search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/escalas-del-medidors")
    @Timed
    public ResponseEntity<List<EscalasDelMedidorDTO>> searchEscalasDelMedidors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EscalasDelMedidors for query {}", query);
        Page<EscalasDelMedidorDTO> page = escalasDelMedidorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/escalas-del-medidors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
