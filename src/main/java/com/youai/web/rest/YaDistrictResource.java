package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaDistrict;
import com.youai.repository.YaDistrictRepository;
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
 * REST controller for managing YaDistrict.
 */
@RestController
@RequestMapping("/api")
public class YaDistrictResource {

    private final Logger log = LoggerFactory.getLogger(YaDistrictResource.class);
        
    @Inject
    private YaDistrictRepository yaDistrictRepository;
    
    /**
     * POST  /yaDistricts -> Create a new yaDistrict.
     */
    @RequestMapping(value = "/yaDistricts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaDistrict> createYaDistrict(@RequestBody YaDistrict yaDistrict) throws URISyntaxException {
        log.debug("REST request to save YaDistrict : {}", yaDistrict);
        if (yaDistrict.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaDistrict", "idexists", "A new yaDistrict cannot already have an ID")).body(null);
        }
        YaDistrict result = yaDistrictRepository.save(yaDistrict);
        return ResponseEntity.created(new URI("/api/yaDistricts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaDistrict", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaDistricts -> Updates an existing yaDistrict.
     */
    @RequestMapping(value = "/yaDistricts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaDistrict> updateYaDistrict(@RequestBody YaDistrict yaDistrict) throws URISyntaxException {
        log.debug("REST request to update YaDistrict : {}", yaDistrict);
        if (yaDistrict.getId() == null) {
            return createYaDistrict(yaDistrict);
        }
        YaDistrict result = yaDistrictRepository.save(yaDistrict);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaDistrict", yaDistrict.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaDistricts -> get all the yaDistricts.
     */
    @RequestMapping(value = "/yaDistricts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaDistrict> getAllYaDistricts() {
        log.debug("REST request to get all YaDistricts");
        return yaDistrictRepository.findAll();
            }

    /**
     * GET  /yaDistricts/:id -> get the "id" yaDistrict.
     */
    @RequestMapping(value = "/yaDistricts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaDistrict> getYaDistrict(@PathVariable Long id) {
        log.debug("REST request to get YaDistrict : {}", id);
        YaDistrict yaDistrict = yaDistrictRepository.findOne(id);
        return Optional.ofNullable(yaDistrict)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaDistricts/:id -> delete the "id" yaDistrict.
     */
    @RequestMapping(value = "/yaDistricts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaDistrict(@PathVariable Long id) {
        log.debug("REST request to delete YaDistrict : {}", id);
        yaDistrictRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaDistrict", id.toString())).build();
    }
}
