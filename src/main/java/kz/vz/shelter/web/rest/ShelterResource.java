package kz.vz.shelter.web.rest;

import com.codahale.metrics.annotation.Timed;
import kz.vz.shelter.service.ShelterService;
import kz.vz.shelter.web.rest.util.HeaderUtil;
import kz.vz.shelter.service.dto.ShelterDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Shelter.
 */
@RestController
@RequestMapping("/api")
public class ShelterResource {

    private final Logger log = LoggerFactory.getLogger(ShelterResource.class);

    private static final String ENTITY_NAME = "shelter";

    private final ShelterService shelterService;

    public ShelterResource(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    /**
     * POST  /shelters : Create a new shelter.
     *
     * @param shelterDTO the shelterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shelterDTO, or with status 400 (Bad Request) if the shelter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shelters")
    @Timed
    public ResponseEntity<ShelterDTO> createShelter(@RequestBody ShelterDTO shelterDTO) throws URISyntaxException {
        log.debug("REST request to save Shelter : {}", shelterDTO);
        if (shelterDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new shelter cannot already have an ID")).body(null);
        }
        ShelterDTO result = shelterService.save(shelterDTO);
        return ResponseEntity.created(new URI("/api/shelters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shelters : Updates an existing shelter.
     *
     * @param shelterDTO the shelterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shelterDTO,
     * or with status 400 (Bad Request) if the shelterDTO is not valid,
     * or with status 500 (Internal Server Error) if the shelterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shelters")
    @Timed
    public ResponseEntity<ShelterDTO> updateShelter(@RequestBody ShelterDTO shelterDTO) throws URISyntaxException {
        log.debug("REST request to update Shelter : {}", shelterDTO);
        if (shelterDTO.getId() == null) {
            return createShelter(shelterDTO);
        }
        ShelterDTO result = shelterService.save(shelterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shelterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shelters : get all the shelters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shelters in body
     */
    @GetMapping("/shelters")
    @Timed
    public List<ShelterDTO> getAllShelters() {
        log.debug("REST request to get all Shelters");
        return shelterService.findAll();
    }

    /**
     * GET  /shelters/:id : get the "id" shelter.
     *
     * @param id the id of the shelterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shelterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shelters/{id}")
    @Timed
    public ResponseEntity<ShelterDTO> getShelter(@PathVariable String id) {
        log.debug("REST request to get Shelter : {}", id);
        ShelterDTO shelterDTO = shelterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shelterDTO));
    }

    /**
     * DELETE  /shelters/:id : delete the "id" shelter.
     *
     * @param id the id of the shelterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shelters/{id}")
    @Timed
    public ResponseEntity<Void> deleteShelter(@PathVariable String id) {
        log.debug("REST request to delete Shelter : {}", id);
        shelterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
