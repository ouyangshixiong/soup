package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaOrderSequence;
import com.youai.repository.YaOrderSequenceRepository;
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
 * REST controller for managing YaOrderSequence.
 */
@RestController
@RequestMapping("/api")
public class YaOrderSequenceResource {

    private final Logger log = LoggerFactory.getLogger(YaOrderSequenceResource.class);
        
    @Inject
    private YaOrderSequenceRepository yaOrderSequenceRepository;
    
    /**
     * POST  /yaOrderSequences -> Create a new yaOrderSequence.
     */
    @RequestMapping(value = "/yaOrderSequences",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaOrderSequence> createYaOrderSequence(@RequestBody YaOrderSequence yaOrderSequence) throws URISyntaxException {
        log.debug("REST request to save YaOrderSequence : {}", yaOrderSequence);
        if (yaOrderSequence.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaOrderSequence", "idexists", "A new yaOrderSequence cannot already have an ID")).body(null);
        }
        YaOrderSequence result = yaOrderSequenceRepository.save(yaOrderSequence);
        return ResponseEntity.created(new URI("/api/yaOrderSequences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaOrderSequence", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaOrderSequences -> Updates an existing yaOrderSequence.
     */
    @RequestMapping(value = "/yaOrderSequences",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaOrderSequence> updateYaOrderSequence(@RequestBody YaOrderSequence yaOrderSequence) throws URISyntaxException {
        log.debug("REST request to update YaOrderSequence : {}", yaOrderSequence);
        if (yaOrderSequence.getId() == null) {
            return createYaOrderSequence(yaOrderSequence);
        }
        YaOrderSequence result = yaOrderSequenceRepository.save(yaOrderSequence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaOrderSequence", yaOrderSequence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaOrderSequences -> get all the yaOrderSequences.
     */
    @RequestMapping(value = "/yaOrderSequences",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaOrderSequence> getAllYaOrderSequences() {
        log.debug("REST request to get all YaOrderSequences");
        return yaOrderSequenceRepository.findAll();
            }

    /**
     * GET  /yaOrderSequences/:id -> get the "id" yaOrderSequence.
     */
    @RequestMapping(value = "/yaOrderSequences/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaOrderSequence> getYaOrderSequence(@PathVariable Long id) {
        log.debug("REST request to get YaOrderSequence : {}", id);
        YaOrderSequence yaOrderSequence = yaOrderSequenceRepository.findOne(id);
        return Optional.ofNullable(yaOrderSequence)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaOrderSequences/:id -> delete the "id" yaOrderSequence.
     */
    @RequestMapping(value = "/yaOrderSequences/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaOrderSequence(@PathVariable Long id) {
        log.debug("REST request to delete YaOrderSequence : {}", id);
        yaOrderSequenceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaOrderSequence", id.toString())).build();
    }
}
