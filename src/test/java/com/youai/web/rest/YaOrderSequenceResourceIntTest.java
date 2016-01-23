package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaOrderSequence;
import com.youai.repository.YaOrderSequenceRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the YaOrderSequenceResource REST controller.
 *
 * @see YaOrderSequenceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaOrderSequenceResourceIntTest {


    @Inject
    private YaOrderSequenceRepository yaOrderSequenceRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaOrderSequenceMockMvc;

    private YaOrderSequence yaOrderSequence;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaOrderSequenceResource yaOrderSequenceResource = new YaOrderSequenceResource();
        ReflectionTestUtils.setField(yaOrderSequenceResource, "yaOrderSequenceRepository", yaOrderSequenceRepository);
        this.restYaOrderSequenceMockMvc = MockMvcBuilders.standaloneSetup(yaOrderSequenceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaOrderSequence = new YaOrderSequence();
    }

    @Test
    @Transactional
    public void createYaOrderSequence() throws Exception {
        int databaseSizeBeforeCreate = yaOrderSequenceRepository.findAll().size();

        // Create the YaOrderSequence

        restYaOrderSequenceMockMvc.perform(post("/api/yaOrderSequences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaOrderSequence)))
                .andExpect(status().isCreated());

        // Validate the YaOrderSequence in the database
        List<YaOrderSequence> yaOrderSequences = yaOrderSequenceRepository.findAll();
        assertThat(yaOrderSequences).hasSize(databaseSizeBeforeCreate + 1);
        YaOrderSequence testYaOrderSequence = yaOrderSequences.get(yaOrderSequences.size() - 1);
    }

    @Test
    @Transactional
    public void getAllYaOrderSequences() throws Exception {
        // Initialize the database
        yaOrderSequenceRepository.saveAndFlush(yaOrderSequence);

        // Get all the yaOrderSequences
        restYaOrderSequenceMockMvc.perform(get("/api/yaOrderSequences?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaOrderSequence.getId().intValue())));
    }

    @Test
    @Transactional
    public void getYaOrderSequence() throws Exception {
        // Initialize the database
        yaOrderSequenceRepository.saveAndFlush(yaOrderSequence);

        // Get the yaOrderSequence
        restYaOrderSequenceMockMvc.perform(get("/api/yaOrderSequences/{id}", yaOrderSequence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaOrderSequence.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingYaOrderSequence() throws Exception {
        // Get the yaOrderSequence
        restYaOrderSequenceMockMvc.perform(get("/api/yaOrderSequences/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaOrderSequence() throws Exception {
        // Initialize the database
        yaOrderSequenceRepository.saveAndFlush(yaOrderSequence);

		int databaseSizeBeforeUpdate = yaOrderSequenceRepository.findAll().size();

        // Update the yaOrderSequence

        restYaOrderSequenceMockMvc.perform(put("/api/yaOrderSequences")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaOrderSequence)))
                .andExpect(status().isOk());

        // Validate the YaOrderSequence in the database
        List<YaOrderSequence> yaOrderSequences = yaOrderSequenceRepository.findAll();
        assertThat(yaOrderSequences).hasSize(databaseSizeBeforeUpdate);
        YaOrderSequence testYaOrderSequence = yaOrderSequences.get(yaOrderSequences.size() - 1);
    }

    @Test
    @Transactional
    public void deleteYaOrderSequence() throws Exception {
        // Initialize the database
        yaOrderSequenceRepository.saveAndFlush(yaOrderSequence);

		int databaseSizeBeforeDelete = yaOrderSequenceRepository.findAll().size();

        // Get the yaOrderSequence
        restYaOrderSequenceMockMvc.perform(delete("/api/yaOrderSequences/{id}", yaOrderSequence.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaOrderSequence> yaOrderSequences = yaOrderSequenceRepository.findAll();
        assertThat(yaOrderSequences).hasSize(databaseSizeBeforeDelete - 1);
    }
}
