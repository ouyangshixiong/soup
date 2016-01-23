package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaProduct;
import com.youai.repository.YaProductRepository;
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
 * REST controller for managing YaProduct.
 */
@RestController
@RequestMapping("/api")
public class YaProductResource {

    private final Logger log = LoggerFactory.getLogger(YaProductResource.class);
        
    @Inject
    private YaProductRepository yaProductRepository;
    
    /**
     * POST  /yaProducts -> Create a new yaProduct.
     */
    @RequestMapping(value = "/yaProducts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaProduct> createYaProduct(@Valid @RequestBody YaProduct yaProduct) throws URISyntaxException {
        log.debug("REST request to save YaProduct : {}", yaProduct);
        if (yaProduct.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaProduct", "idexists", "A new yaProduct cannot already have an ID")).body(null);
        }
        YaProduct result = yaProductRepository.save(yaProduct);
        return ResponseEntity.created(new URI("/api/yaProducts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaProduct", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaProducts -> Updates an existing yaProduct.
     */
    @RequestMapping(value = "/yaProducts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaProduct> updateYaProduct(@Valid @RequestBody YaProduct yaProduct) throws URISyntaxException {
        log.debug("REST request to update YaProduct : {}", yaProduct);
        if (yaProduct.getId() == null) {
            return createYaProduct(yaProduct);
        }
        YaProduct result = yaProductRepository.save(yaProduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaProduct", yaProduct.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaProducts -> get all the yaProducts.
     */
    @RequestMapping(value = "/yaProducts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaProduct> getAllYaProducts() {
        log.debug("REST request to get all YaProducts");
        return yaProductRepository.findAll();
            }

    /**
     * GET  /yaProducts/:id -> get the "id" yaProduct.
     */
    @RequestMapping(value = "/yaProducts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaProduct> getYaProduct(@PathVariable Long id) {
        log.debug("REST request to get YaProduct : {}", id);
        YaProduct yaProduct = yaProductRepository.findOne(id);
        return Optional.ofNullable(yaProduct)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaProducts/:id -> delete the "id" yaProduct.
     */
    @RequestMapping(value = "/yaProducts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaProduct(@PathVariable Long id) {
        log.debug("REST request to delete YaProduct : {}", id);
        yaProductRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaProduct", id.toString())).build();
    }
}
