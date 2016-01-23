package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaQuestion;
import com.youai.repository.YaQuestionRepository;

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
 * Test class for the YaQuestionResource REST controller.
 *
 * @see YaQuestionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaQuestionResourceIntTest {

    private static final String DEFAULT_YA_Q = "AAAAAA";
    private static final String UPDATED_YA_Q = "BBBBBB";
    private static final String DEFAULT_YA_A = "AAAAA";
    private static final String UPDATED_YA_A = "BBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;
    private static final String DEFAULT_STATUS = "AAAAA";
    private static final String UPDATED_STATUS = "BBBBB";

    @Inject
    private YaQuestionRepository yaQuestionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaQuestionMockMvc;

    private YaQuestion yaQuestion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaQuestionResource yaQuestionResource = new YaQuestionResource();
        ReflectionTestUtils.setField(yaQuestionResource, "yaQuestionRepository", yaQuestionRepository);
        this.restYaQuestionMockMvc = MockMvcBuilders.standaloneSetup(yaQuestionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaQuestion = new YaQuestion();
        yaQuestion.setYaQ(DEFAULT_YA_Q);
        yaQuestion.setYaA(DEFAULT_YA_A);
        yaQuestion.setPriority(DEFAULT_PRIORITY);
        yaQuestion.setStatus(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createYaQuestion() throws Exception {
        int databaseSizeBeforeCreate = yaQuestionRepository.findAll().size();

        // Create the YaQuestion

        restYaQuestionMockMvc.perform(post("/api/yaQuestions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaQuestion)))
                .andExpect(status().isCreated());

        // Validate the YaQuestion in the database
        List<YaQuestion> yaQuestions = yaQuestionRepository.findAll();
        assertThat(yaQuestions).hasSize(databaseSizeBeforeCreate + 1);
        YaQuestion testYaQuestion = yaQuestions.get(yaQuestions.size() - 1);
        assertThat(testYaQuestion.getYaQ()).isEqualTo(DEFAULT_YA_Q);
        assertThat(testYaQuestion.getYaA()).isEqualTo(DEFAULT_YA_A);
        assertThat(testYaQuestion.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testYaQuestion.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void checkYaQIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaQuestionRepository.findAll().size();
        // set the field null
        yaQuestion.setYaQ(null);

        // Create the YaQuestion, which fails.

        restYaQuestionMockMvc.perform(post("/api/yaQuestions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaQuestion)))
                .andExpect(status().isBadRequest());

        List<YaQuestion> yaQuestions = yaQuestionRepository.findAll();
        assertThat(yaQuestions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYaQuestions() throws Exception {
        // Initialize the database
        yaQuestionRepository.saveAndFlush(yaQuestion);

        // Get all the yaQuestions
        restYaQuestionMockMvc.perform(get("/api/yaQuestions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaQuestion.getId().intValue())))
                .andExpect(jsonPath("$.[*].yaQ").value(hasItem(DEFAULT_YA_Q.toString())))
                .andExpect(jsonPath("$.[*].yaA").value(hasItem(DEFAULT_YA_A.toString())))
                .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getYaQuestion() throws Exception {
        // Initialize the database
        yaQuestionRepository.saveAndFlush(yaQuestion);

        // Get the yaQuestion
        restYaQuestionMockMvc.perform(get("/api/yaQuestions/{id}", yaQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaQuestion.getId().intValue()))
            .andExpect(jsonPath("$.yaQ").value(DEFAULT_YA_Q.toString()))
            .andExpect(jsonPath("$.yaA").value(DEFAULT_YA_A.toString()))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingYaQuestion() throws Exception {
        // Get the yaQuestion
        restYaQuestionMockMvc.perform(get("/api/yaQuestions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaQuestion() throws Exception {
        // Initialize the database
        yaQuestionRepository.saveAndFlush(yaQuestion);

		int databaseSizeBeforeUpdate = yaQuestionRepository.findAll().size();

        // Update the yaQuestion
        yaQuestion.setYaQ(UPDATED_YA_Q);
        yaQuestion.setYaA(UPDATED_YA_A);
        yaQuestion.setPriority(UPDATED_PRIORITY);
        yaQuestion.setStatus(UPDATED_STATUS);

        restYaQuestionMockMvc.perform(put("/api/yaQuestions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaQuestion)))
                .andExpect(status().isOk());

        // Validate the YaQuestion in the database
        List<YaQuestion> yaQuestions = yaQuestionRepository.findAll();
        assertThat(yaQuestions).hasSize(databaseSizeBeforeUpdate);
        YaQuestion testYaQuestion = yaQuestions.get(yaQuestions.size() - 1);
        assertThat(testYaQuestion.getYaQ()).isEqualTo(UPDATED_YA_Q);
        assertThat(testYaQuestion.getYaA()).isEqualTo(UPDATED_YA_A);
        assertThat(testYaQuestion.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testYaQuestion.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void deleteYaQuestion() throws Exception {
        // Initialize the database
        yaQuestionRepository.saveAndFlush(yaQuestion);

		int databaseSizeBeforeDelete = yaQuestionRepository.findAll().size();

        // Get the yaQuestion
        restYaQuestionMockMvc.perform(delete("/api/yaQuestions/{id}", yaQuestion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaQuestion> yaQuestions = yaQuestionRepository.findAll();
        assertThat(yaQuestions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
