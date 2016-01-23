package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaBanner;
import com.youai.repository.YaBannerRepository;
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
 * REST controller for managing YaBanner.
 */
@RestController
@RequestMapping("/api")
public class YaBannerResource {

    private final Logger log = LoggerFactory.getLogger(YaBannerResource.class);
        
    @Inject
    private YaBannerRepository yaBannerRepository;
    
    /**
     * POST  /yaBanners -> Create a new yaBanner.
     */
    @RequestMapping(value = "/yaBanners",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaBanner> createYaBanner(@Valid @RequestBody YaBanner yaBanner) throws URISyntaxException {
        log.debug("REST request to save YaBanner : {}", yaBanner);
        if (yaBanner.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaBanner", "idexists", "A new yaBanner cannot already have an ID")).body(null);
        }
        YaBanner result = yaBannerRepository.save(yaBanner);
        return ResponseEntity.created(new URI("/api/yaBanners/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaBanner", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaBanners -> Updates an existing yaBanner.
     */
    @RequestMapping(value = "/yaBanners",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaBanner> updateYaBanner(@Valid @RequestBody YaBanner yaBanner) throws URISyntaxException {
        log.debug("REST request to update YaBanner : {}", yaBanner);
        if (yaBanner.getId() == null) {
            return createYaBanner(yaBanner);
        }
        YaBanner result = yaBannerRepository.save(yaBanner);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaBanner", yaBanner.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaBanners -> get all the yaBanners.
     */
    @RequestMapping(value = "/yaBanners",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaBanner> getAllYaBanners() {
        log.debug("REST request to get all YaBanners");
        return yaBannerRepository.findAll();
            }

    /**
     * GET  /yaBanners/:id -> get the "id" yaBanner.
     */
    @RequestMapping(value = "/yaBanners/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaBanner> getYaBanner(@PathVariable Long id) {
        log.debug("REST request to get YaBanner : {}", id);
        YaBanner yaBanner = yaBannerRepository.findOne(id);
        return Optional.ofNullable(yaBanner)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaBanners/:id -> delete the "id" yaBanner.
     */
    @RequestMapping(value = "/yaBanners/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaBanner(@PathVariable Long id) {
        log.debug("REST request to delete YaBanner : {}", id);
        yaBannerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaBanner", id.toString())).build();
    }
}
