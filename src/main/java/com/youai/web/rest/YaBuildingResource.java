package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaBuilding;
import com.youai.repository.YaBuildingRepository;
import com.youai.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing YaBuilding.
 */
@RestController
@RequestMapping("/api")
public class YaBuildingResource {

    private final Logger log = LoggerFactory.getLogger(YaBuildingResource.class);
        
    @Inject
    private YaBuildingRepository yaBuildingRepository;
    
    /**
     * POST  /yaBuildings -> Create a new yaBuilding.
     */
    @RequestMapping(value = "/yaBuildings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaBuilding> createYaBuilding(@RequestBody YaBuilding yaBuilding) throws URISyntaxException {
        log.debug("REST request to save YaBuilding : {}", yaBuilding);
        if (yaBuilding.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaBuilding", "idexists", "A new yaBuilding cannot already have an ID")).body(null);
        }
        YaBuilding result = yaBuildingRepository.save(yaBuilding);
        return ResponseEntity.created(new URI("/api/yaBuildings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaBuilding", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaBuildings -> Updates an existing yaBuilding.
     */
    @RequestMapping(value = "/yaBuildings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaBuilding> updateYaBuilding(@RequestBody YaBuilding yaBuilding) throws URISyntaxException {
        log.debug("REST request to update YaBuilding : {}", yaBuilding);
        if (yaBuilding.getId() == null) {
            return createYaBuilding(yaBuilding);
        }
        YaBuilding result = yaBuildingRepository.save(yaBuilding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaBuilding", yaBuilding.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaBuildings -> get all the yaBuildings.
     */
    @RequestMapping(value = "/yaBuildings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaBuilding> getAllYaBuildings() {
        log.debug("REST request to get all YaBuildings");
        return yaBuildingRepository.findAll();
            }

    /**
     * GET  /yaBuildings/:id -> get the "id" yaBuilding.
     */
    @RequestMapping(value = "/yaBuildings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaBuilding> getYaBuilding(@PathVariable Long id) {
        log.debug("REST request to get YaBuilding : {}", id);
        YaBuilding yaBuilding = yaBuildingRepository.findOne(id);
        return Optional.ofNullable(yaBuilding)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaBuildings/:id -> delete the "id" yaBuilding.
     */
    @RequestMapping(value = "/yaBuildings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaBuilding(@PathVariable Long id) {
        log.debug("REST request to delete YaBuilding : {}", id);
        yaBuildingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaBuilding", id.toString())).build();
    }
}
