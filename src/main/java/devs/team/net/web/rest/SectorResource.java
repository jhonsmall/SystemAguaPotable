package devs.team.net.web.rest;

import com.codahale.metrics.annotation.Timed;
import devs.team.net.service.SectorService;
import devs.team.net.web.rest.errors.BadRequestAlertException;
import devs.team.net.web.rest.util.HeaderUtil;
import devs.team.net.web.rest.util.PaginationUtil;
import devs.team.net.service.dto.SectorDTO;
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
 * REST controller for managing Sector.
 */
@RestController
@RequestMapping("/api")
public class SectorResource {

    private final Logger log = LoggerFactory.getLogger(SectorResource.class);

    private static final String ENTITY_NAME = "sector";

    private final SectorService sectorService;

    public SectorResource(SectorService sectorService) {
        this.sectorService = sectorService;
    }

    /**
     * POST  /sectors : Create a new sector.
     *
     * @param sectorDTO the sectorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sectorDTO, or with status 400 (Bad Request) if the sector has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sectors")
    @Timed
    public ResponseEntity<SectorDTO> createSector(@Valid @RequestBody SectorDTO sectorDTO) throws URISyntaxException {
        log.debug("REST request to save Sector : {}", sectorDTO);
        if (sectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new sector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SectorDTO result = sectorService.save(sectorDTO);
        return ResponseEntity.created(new URI("/api/sectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sectors : Updates an existing sector.
     *
     * @param sectorDTO the sectorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sectorDTO,
     * or with status 400 (Bad Request) if the sectorDTO is not valid,
     * or with status 500 (Internal Server Error) if the sectorDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sectors")
    @Timed
    public ResponseEntity<SectorDTO> updateSector(@Valid @RequestBody SectorDTO sectorDTO) throws URISyntaxException {
        log.debug("REST request to update Sector : {}", sectorDTO);
        if (sectorDTO.getId() == null) {
            return createSector(sectorDTO);
        }
        SectorDTO result = sectorService.save(sectorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sectorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sectors : get all the sectors.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sectors in body
     */
    @GetMapping("/sectors")
    @Timed
    public ResponseEntity<List<SectorDTO>> getAllSectors(Pageable pageable) {
        log.debug("REST request to get a page of Sectors");
        Page<SectorDTO> page = sectorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sectors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sectors/:id : get the "id" sector.
     *
     * @param id the id of the sectorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sectorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sectors/{id}")
    @Timed
    public ResponseEntity<SectorDTO> getSector(@PathVariable Long id) {
        log.debug("REST request to get Sector : {}", id);
        SectorDTO sectorDTO = sectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(sectorDTO));
    }

    /**
     * DELETE  /sectors/:id : delete the "id" sector.
     *
     * @param id the id of the sectorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sectors/{id}")
    @Timed
    public ResponseEntity<Void> deleteSector(@PathVariable Long id) {
        log.debug("REST request to delete Sector : {}", id);
        sectorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/sectors?query=:query : search for the sector corresponding
     * to the query.
     *
     * @param query the query of the sector search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/sectors")
    @Timed
    public ResponseEntity<List<SectorDTO>> searchSectors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Sectors for query {}", query);
        Page<SectorDTO> page = sectorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/sectors");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
