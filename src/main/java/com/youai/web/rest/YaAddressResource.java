package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaAddress;
import com.youai.repository.YaAddressRepository;
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
 * REST controller for managing YaAddress.
 */
@RestController
@RequestMapping("/api")
public class YaAddressResource {

    private final Logger log = LoggerFactory.getLogger(YaAddressResource.class);
        
    @Inject
    private YaAddressRepository yaAddressRepository;
    
    /**
     * POST  /yaAddresss -> Create a new yaAddress.
     */
    @RequestMapping(value = "/yaAddresss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaAddress> createYaAddress(@Valid @RequestBody YaAddress yaAddress) throws URISyntaxException {
        log.debug("REST request to save YaAddress : {}", yaAddress);
        if (yaAddress.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaAddress", "idexists", "A new yaAddress cannot already have an ID")).body(null);
        }
        YaAddress result = yaAddressRepository.save(yaAddress);
        return ResponseEntity.created(new URI("/api/yaAddresss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaAddress", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaAddresss -> Updates an existing yaAddress.
     */
    @RequestMapping(value = "/yaAddresss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaAddress> updateYaAddress(@Valid @RequestBody YaAddress yaAddress) throws URISyntaxException {
        log.debug("REST request to update YaAddress : {}", yaAddress);
        if (yaAddress.getId() == null) {
            return createYaAddress(yaAddress);
        }
        YaAddress result = yaAddressRepository.save(yaAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaAddress", yaAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaAddresss -> get all the yaAddresss.
     */
    @RequestMapping(value = "/yaAddresss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaAddress> getAllYaAddresss() {
        log.debug("REST request to get all YaAddresss");
        return yaAddressRepository.findAll();
            }

    /**
     * GET  /yaAddresss/:id -> get the "id" yaAddress.
     */
    @RequestMapping(value = "/yaAddresss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaAddress> getYaAddress(@PathVariable Long id) {
        log.debug("REST request to get YaAddress : {}", id);
        YaAddress yaAddress = yaAddressRepository.findOne(id);
        return Optional.ofNullable(yaAddress)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaAddresss/:id -> delete the "id" yaAddress.
     */
    @RequestMapping(value = "/yaAddresss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaAddress(@PathVariable Long id) {
        log.debug("REST request to delete YaAddress : {}", id);
        yaAddressRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaAddress", id.toString())).build();
    }
}
