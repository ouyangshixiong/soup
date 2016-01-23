package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaOrder;
import com.youai.repository.YaOrderRepository;
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
 * REST controller for managing YaOrder.
 */
@RestController
@RequestMapping("/api")
public class YaOrderResource {

    private final Logger log = LoggerFactory.getLogger(YaOrderResource.class);
        
    @Inject
    private YaOrderRepository yaOrderRepository;
    
    /**
     * POST  /yaOrders -> Create a new yaOrder.
     */
    @RequestMapping(value = "/yaOrders",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaOrder> createYaOrder(@Valid @RequestBody YaOrder yaOrder) throws URISyntaxException {
        log.debug("REST request to save YaOrder : {}", yaOrder);
        if (yaOrder.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaOrder", "idexists", "A new yaOrder cannot already have an ID")).body(null);
        }
        YaOrder result = yaOrderRepository.save(yaOrder);
        return ResponseEntity.created(new URI("/api/yaOrders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaOrder", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaOrders -> Updates an existing yaOrder.
     */
    @RequestMapping(value = "/yaOrders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaOrder> updateYaOrder(@Valid @RequestBody YaOrder yaOrder) throws URISyntaxException {
        log.debug("REST request to update YaOrder : {}", yaOrder);
        if (yaOrder.getId() == null) {
            return createYaOrder(yaOrder);
        }
        YaOrder result = yaOrderRepository.save(yaOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaOrder", yaOrder.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaOrders -> get all the yaOrders.
     */
    @RequestMapping(value = "/yaOrders",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaOrder> getAllYaOrders() {
        log.debug("REST request to get all YaOrders");
        return yaOrderRepository.findAll();
            }

    /**
     * GET  /yaOrders/:id -> get the "id" yaOrder.
     */
    @RequestMapping(value = "/yaOrders/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaOrder> getYaOrder(@PathVariable Long id) {
        log.debug("REST request to get YaOrder : {}", id);
        YaOrder yaOrder = yaOrderRepository.findOne(id);
        return Optional.ofNullable(yaOrder)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaOrders/:id -> delete the "id" yaOrder.
     */
    @RequestMapping(value = "/yaOrders/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaOrder(@PathVariable Long id) {
        log.debug("REST request to delete YaOrder : {}", id);
        yaOrderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaOrder", id.toString())).build();
    }
}
