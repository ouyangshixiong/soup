package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaSubProduct;
import com.youai.repository.YaSubProductRepository;
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
 * REST controller for managing YaSubProduct.
 */
@RestController
@RequestMapping("/api")
public class YaSubProductResource {

    private final Logger log = LoggerFactory.getLogger(YaSubProductResource.class);
        
    @Inject
    private YaSubProductRepository yaSubProductRepository;
    
    /**
     * POST  /yaSubProducts -> Create a new yaSubProduct.
     */
    @RequestMapping(value = "/yaSubProducts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaSubProduct> createYaSubProduct(@Valid @RequestBody YaSubProduct yaSubProduct) throws URISyntaxException {
        log.debug("REST request to save YaSubProduct : {}", yaSubProduct);
        if (yaSubProduct.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaSubProduct", "idexists", "A new yaSubProduct cannot already have an ID")).body(null);
        }
        YaSubProduct result = yaSubProductRepository.save(yaSubProduct);
        return ResponseEntity.created(new URI("/api/yaSubProducts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaSubProduct", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaSubProducts -> Updates an existing yaSubProduct.
     */
    @RequestMapping(value = "/yaSubProducts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaSubProduct> updateYaSubProduct(@Valid @RequestBody YaSubProduct yaSubProduct) throws URISyntaxException {
        log.debug("REST request to update YaSubProduct : {}", yaSubProduct);
        if (yaSubProduct.getId() == null) {
            return createYaSubProduct(yaSubProduct);
        }
        YaSubProduct result = yaSubProductRepository.save(yaSubProduct);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaSubProduct", yaSubProduct.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaSubProducts -> get all the yaSubProducts.
     */
    @RequestMapping(value = "/yaSubProducts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaSubProduct> getAllYaSubProducts() {
        log.debug("REST request to get all YaSubProducts");
        return yaSubProductRepository.findAll();
            }

    /**
     * GET  /yaSubProducts/:id -> get the "id" yaSubProduct.
     */
    @RequestMapping(value = "/yaSubProducts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaSubProduct> getYaSubProduct(@PathVariable Long id) {
        log.debug("REST request to get YaSubProduct : {}", id);
        YaSubProduct yaSubProduct = yaSubProductRepository.findOne(id);
        return Optional.ofNullable(yaSubProduct)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaSubProducts/:id -> delete the "id" yaSubProduct.
     */
    @RequestMapping(value = "/yaSubProducts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaSubProduct(@PathVariable Long id) {
        log.debug("REST request to delete YaSubProduct : {}", id);
        yaSubProductRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaSubProduct", id.toString())).build();
    }
}
