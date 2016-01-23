package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaCouponOrder;
import com.youai.repository.YaCouponOrderRepository;
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
 * REST controller for managing YaCouponOrder.
 */
@RestController
@RequestMapping("/api")
public class YaCouponOrderResource {

    private final Logger log = LoggerFactory.getLogger(YaCouponOrderResource.class);
        
    @Inject
    private YaCouponOrderRepository yaCouponOrderRepository;
    
    /**
     * POST  /yaCouponOrders -> Create a new yaCouponOrder.
     */
    @RequestMapping(value = "/yaCouponOrders",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaCouponOrder> createYaCouponOrder(@Valid @RequestBody YaCouponOrder yaCouponOrder) throws URISyntaxException {
        log.debug("REST request to save YaCouponOrder : {}", yaCouponOrder);
        if (yaCouponOrder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaCouponOrder", "idexists", "A new yaCouponOrder cannot already have an ID")).body(null);
        }
        YaCouponOrder result = yaCouponOrderRepository.save(yaCouponOrder);
        return ResponseEntity.created(new URI("/api/yaCouponOrders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaCouponOrder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaCouponOrders -> Updates an existing yaCouponOrder.
     */
    @RequestMapping(value = "/yaCouponOrders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaCouponOrder> updateYaCouponOrder(@Valid @RequestBody YaCouponOrder yaCouponOrder) throws URISyntaxException {
        log.debug("REST request to update YaCouponOrder : {}", yaCouponOrder);
        if (yaCouponOrder.getId() == null) {
            return createYaCouponOrder(yaCouponOrder);
        }
        YaCouponOrder result = yaCouponOrderRepository.save(yaCouponOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaCouponOrder", yaCouponOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaCouponOrders -> get all the yaCouponOrders.
     */
    @RequestMapping(value = "/yaCouponOrders",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaCouponOrder> getAllYaCouponOrders() {
        log.debug("REST request to get all YaCouponOrders");
        return yaCouponOrderRepository.findAll();
            }

    /**
     * GET  /yaCouponOrders/:id -> get the "id" yaCouponOrder.
     */
    @RequestMapping(value = "/yaCouponOrders/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaCouponOrder> getYaCouponOrder(@PathVariable Long id) {
        log.debug("REST request to get YaCouponOrder : {}", id);
        YaCouponOrder yaCouponOrder = yaCouponOrderRepository.findOne(id);
        return Optional.ofNullable(yaCouponOrder)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaCouponOrders/:id -> delete the "id" yaCouponOrder.
     */
    @RequestMapping(value = "/yaCouponOrders/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaCouponOrder(@PathVariable Long id) {
        log.debug("REST request to delete YaCouponOrder : {}", id);
        yaCouponOrderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaCouponOrder", id.toString())).build();
    }
}
