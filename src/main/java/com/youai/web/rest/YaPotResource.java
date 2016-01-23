package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaPot;
import com.youai.repository.YaPotRepository;
import com.youai.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing YaPot.
 */
@RestController
@RequestMapping("/api")
public class YaPotResource {

    private final Logger log = LoggerFactory.getLogger(YaPotResource.class);
        
    @Inject
    private YaPotRepository yaPotRepository;
    
    /**
     * POST  /yaPots -> Create a new yaPot.
     */
    @RequestMapping(value = "/yaPots",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaPot> createYaPot(@Valid @RequestBody YaPot yaPot) throws URISyntaxException {
        log.debug("REST request to save YaPot : {}", yaPot);
        if (yaPot.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaPot", "idexists", "A new yaPot cannot already have an ID")).body(null);
        }
        YaPot result = yaPotRepository.save(yaPot);
        return ResponseEntity.created(new URI("/api/yaPots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaPot", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaPots -> Updates an existing yaPot.
     */
    @RequestMapping(value = "/yaPots",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaPot> updateYaPot(@Valid @RequestBody YaPot yaPot) throws URISyntaxException {
        log.debug("REST request to update YaPot : {}", yaPot);
        if (yaPot.getId() == null) {
            return createYaPot(yaPot);
        }
        YaPot result = yaPotRepository.save(yaPot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaPot", yaPot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaPots -> get all the yaPots.
     */
    @RequestMapping(value = "/yaPots",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaPot> getAllYaPots() {
        log.debug("REST request to get all YaPots");
        return yaPotRepository.findAll();
            }

    /**
     * GET  /yaPots/:id -> get the "id" yaPot.
     */
    @RequestMapping(value = "/yaPots/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaPot> getYaPot(@PathVariable Long id) {
        log.debug("REST request to get YaPot : {}", id);
        YaPot yaPot = yaPotRepository.findOne(id);
        return Optional.ofNullable(yaPot)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaPots/:id -> delete the "id" yaPot.
     */
    @RequestMapping(value = "/yaPots/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaPot(@PathVariable Long id) {
        log.debug("REST request to delete YaPot : {}", id);
        yaPotRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaPot", id.toString())).build();
    }
}
