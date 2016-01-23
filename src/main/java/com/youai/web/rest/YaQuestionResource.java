package com.youai.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.youai.domain.YaQuestion;
import com.youai.repository.YaQuestionRepository;
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
 * REST controller for managing YaQuestion.
 */
@RestController
@RequestMapping("/api")
public class YaQuestionResource {

    private final Logger log = LoggerFactory.getLogger(YaQuestionResource.class);
        
    @Inject
    private YaQuestionRepository yaQuestionRepository;
    
    /**
     * POST  /yaQuestions -> Create a new yaQuestion.
     */
    @RequestMapping(value = "/yaQuestions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaQuestion> createYaQuestion(@Valid @RequestBody YaQuestion yaQuestion) throws URISyntaxException {
        log.debug("REST request to save YaQuestion : {}", yaQuestion);
        if (yaQuestion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("yaQuestion", "idexists", "A new yaQuestion cannot already have an ID")).body(null);
        }
        YaQuestion result = yaQuestionRepository.save(yaQuestion);
        return ResponseEntity.created(new URI("/api/yaQuestions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("yaQuestion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /yaQuestions -> Updates an existing yaQuestion.
     */
    @RequestMapping(value = "/yaQuestions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaQuestion> updateYaQuestion(@Valid @RequestBody YaQuestion yaQuestion) throws URISyntaxException {
        log.debug("REST request to update YaQuestion : {}", yaQuestion);
        if (yaQuestion.getId() == null) {
            return createYaQuestion(yaQuestion);
        }
        YaQuestion result = yaQuestionRepository.save(yaQuestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("yaQuestion", yaQuestion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /yaQuestions -> get all the yaQuestions.
     */
    @RequestMapping(value = "/yaQuestions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<YaQuestion> getAllYaQuestions() {
        log.debug("REST request to get all YaQuestions");
        return yaQuestionRepository.findAll();
            }

    /**
     * GET  /yaQuestions/:id -> get the "id" yaQuestion.
     */
    @RequestMapping(value = "/yaQuestions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<YaQuestion> getYaQuestion(@PathVariable Long id) {
        log.debug("REST request to get YaQuestion : {}", id);
        YaQuestion yaQuestion = yaQuestionRepository.findOne(id);
        return Optional.ofNullable(yaQuestion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /yaQuestions/:id -> delete the "id" yaQuestion.
     */
    @RequestMapping(value = "/yaQuestions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteYaQuestion(@PathVariable Long id) {
        log.debug("REST request to delete YaQuestion : {}", id);
        yaQuestionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("yaQuestion", id.toString())).build();
    }
}
