package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaUser;
import com.youai.repository.YaUserRepository;
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
 * REST controller for managing YaUser.
 */
@RestController
@RequestMapping("/api")
public class YaUserResource {

    private final Logger log = LoggerFactory.getLogger(YaUserResource.class);
        
    @Inject
    private YaUserRepository yaUserRepository;
    
    /**
     * POST  /yaUsers -> Create a new yaUser.
     */
    @RequestMapping(value = "/yaUsers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaUser> createYaUser(@Valid @RequestBody YaUser yaUser) throws URISyntaxException {
        log.debug("REST request to save YaUser : {}", yaUser);
        if (yaUser.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaUser", "idexists", "A new yaUser cannot already have an ID")).body(null);
        }
        YaUser result = yaUserRepository.save(yaUser);
        return ResponseEntity.created(new URI("/api/yaUsers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaUsers -> Updates an existing yaUser.
     */
    @RequestMapping(value = "/yaUsers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaUser> updateYaUser(@Valid @RequestBody YaUser yaUser) throws URISyntaxException {
        log.debug("REST request to update YaUser : {}", yaUser);
        if (yaUser.getId() == null) {
            return createYaUser(yaUser);
        }
        YaUser result = yaUserRepository.save(yaUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaUser", yaUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaUsers -> get all the yaUsers.
     */
    @RequestMapping(value = "/yaUsers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaUser> getAllYaUsers() {
        log.debug("REST request to get all YaUsers");
        return yaUserRepository.findAll();
            }

    /**
     * GET  /yaUsers/:id -> get the "id" yaUser.
     */
    @RequestMapping(value = "/yaUsers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaUser> getYaUser(@PathVariable Long id) {
        log.debug("REST request to get YaUser : {}", id);
        YaUser yaUser = yaUserRepository.findOne(id);
        return Optional.ofNullable(yaUser)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaUsers/:id -> delete the "id" yaUser.
     */
    @RequestMapping(value = "/yaUsers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaUser(@PathVariable Long id) {
        log.debug("REST request to delete YaUser : {}", id);
        yaUserRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaUser", id.toString())).build();
    }
}
