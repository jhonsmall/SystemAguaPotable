package devs.team.net.web.rest;

import com.codahale.metrics.annotation.Timed;
import devs.team.net.service.ReciboService;
import devs.team.net.web.rest.errors.BadRequestAlertException;
import devs.team.net.web.rest.util.HeaderUtil;
import devs.team.net.web.rest.util.PaginationUtil;
import devs.team.net.service.dto.ReciboDTO;
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
 * REST controller for managing Recibo.
 */
@RestController
@RequestMapping("/api")
public class ReciboResource {

    private final Logger log = LoggerFactory.getLogger(ReciboResource.class);

    private static final String ENTITY_NAME = "recibo";

    private final ReciboService reciboService;

    public ReciboResource(ReciboService reciboService) {
        this.reciboService = reciboService;
    }

    /**
     * POST  /recibos : Create a new recibo.
     *
     * @param reciboDTO the reciboDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new reciboDTO, or with status 400 (Bad Request) if the recibo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recibos")
    @Timed
    public ResponseEntity<ReciboDTO> createRecibo(@Valid @RequestBody ReciboDTO reciboDTO) throws URISyntaxException {
        log.debug("REST request to save Recibo : {}", reciboDTO);
        if (reciboDTO.getId() != null) {
            throw new BadRequestAlertException("A new recibo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReciboDTO result = reciboService.save(reciboDTO);
        return ResponseEntity.created(new URI("/api/recibos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recibos : Updates an existing recibo.
     *
     * @param reciboDTO the reciboDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated reciboDTO,
     * or with status 400 (Bad Request) if the reciboDTO is not valid,
     * or with status 500 (Internal Server Error) if the reciboDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recibos")
    @Timed
    public ResponseEntity<ReciboDTO> updateRecibo(@Valid @RequestBody ReciboDTO reciboDTO) throws URISyntaxException {
        log.debug("REST request to update Recibo : {}", reciboDTO);
        if (reciboDTO.getId() == null) {
            return createRecibo(reciboDTO);
        }
        ReciboDTO result = reciboService.save(reciboDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reciboDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recibos : get all the recibos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recibos in body
     */
    @GetMapping("/recibos")
    @Timed
    public ResponseEntity<List<ReciboDTO>> getAllRecibos(Pageable pageable) {
        log.debug("REST request to get a page of Recibos");
        Page<ReciboDTO> page = reciboService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recibos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recibos/:id : get the "id" recibo.
     *
     * @param id the id of the reciboDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the reciboDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recibos/{id}")
    @Timed
    public ResponseEntity<ReciboDTO> getRecibo(@PathVariable Long id) {
        log.debug("REST request to get Recibo : {}", id);
        ReciboDTO reciboDTO = reciboService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reciboDTO));
    }

    /**
     * DELETE  /recibos/:id : delete the "id" recibo.
     *
     * @param id the id of the reciboDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recibos/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecibo(@PathVariable Long id) {
        log.debug("REST request to delete Recibo : {}", id);
        reciboService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/recibos?query=:query : search for the recibo corresponding
     * to the query.
     *
     * @param query the query of the recibo search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/recibos")
    @Timed
    public ResponseEntity<List<ReciboDTO>> searchRecibos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Recibos for query {}", query);
        Page<ReciboDTO> page = reciboService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/recibos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
