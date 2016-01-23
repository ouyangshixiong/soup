package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaCoupon;
import com.youai.repository.YaCouponRepository;
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
 * REST controller for managing YaCoupon.
 */
@RestController
@RequestMapping("/api")
public class YaCouponResource {

    private final Logger log = LoggerFactory.getLogger(YaCouponResource.class);
        
    @Inject
    private YaCouponRepository yaCouponRepository;
    
    /**
     * POST  /yaCoupons -> Create a new yaCoupon.
     */
    @RequestMapping(value = "/yaCoupons",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaCoupon> createYaCoupon(@Valid @RequestBody YaCoupon yaCoupon) throws URISyntaxException {
        log.debug("REST request to save YaCoupon : {}", yaCoupon);
        if (yaCoupon.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaCoupon", "idexists", "A new yaCoupon cannot already have an ID")).body(null);
        }
        YaCoupon result = yaCouponRepository.save(yaCoupon);
        return ResponseEntity.created(new URI("/api/yaCoupons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaCoupon", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaCoupons -> Updates an existing yaCoupon.
     */
    @RequestMapping(value = "/yaCoupons",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaCoupon> updateYaCoupon(@Valid @RequestBody YaCoupon yaCoupon) throws URISyntaxException {
        log.debug("REST request to update YaCoupon : {}", yaCoupon);
        if (yaCoupon.getId() == null) {
            return createYaCoupon(yaCoupon);
        }
        YaCoupon result = yaCouponRepository.save(yaCoupon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaCoupon", yaCoupon.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaCoupons -> get all the yaCoupons.
     */
    @RequestMapping(value = "/yaCoupons",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaCoupon> getAllYaCoupons() {
        log.debug("REST request to get all YaCoupons");
        return yaCouponRepository.findAll();
            }

    /**
     * GET  /yaCoupons/:id -> get the "id" yaCoupon.
     */
    @RequestMapping(value = "/yaCoupons/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaCoupon> getYaCoupon(@PathVariable Long id) {
        log.debug("REST request to get YaCoupon : {}", id);
        YaCoupon yaCoupon = yaCouponRepository.findOne(id);
        return Optional.ofNullable(yaCoupon)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaCoupons/:id -> delete the "id" yaCoupon.
     */
    @RequestMapping(value = "/yaCoupons/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaCoupon(@PathVariable Long id) {
        log.debug("REST request to delete YaCoupon : {}", id);
        yaCouponRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaCoupon", id.toString())).build();
    }
}
