package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaBuilding;
import com.youai.repository.YaBuildingRepository;

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
 * Test class for the YaBuildingResource REST controller.
 *
 * @see YaBuildingResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaBuildingResourceIntTest {


    private static final Integer DEFAULT_BUILDING_CODE = 1;
    private static final Integer UPDATED_BUILDING_CODE = 2;
    private static final String DEFAULT_BUILDING_NAME = "AAAAA";
    private static final String UPDATED_BUILDING_NAME = "BBBBB";

    @Inject
    private YaBuildingRepository yaBuildingRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaBuildingMockMvc;

    private YaBuilding yaBuilding;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaBuildingResource yaBuildingResource = new YaBuildingResource();
        ReflectionTestUtils.setField(yaBuildingResource, "yaBuildingRepository", yaBuildingRepository);
        this.restYaBuildingMockMvc = MockMvcBuilders.standaloneSetup(yaBuildingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaBuilding = new YaBuilding();
        yaBuilding.setBuildingCode(DEFAULT_BUILDING_CODE);
        yaBuilding.setBuildingName(DEFAULT_BUILDING_NAME);
    }

    @Test
    @Transactional
    public void createYaBuilding() throws Exception {
        int databaseSizeBeforeCreate = yaBuildingRepository.findAll().size();

        // Create the YaBuilding

        restYaBuildingMockMvc.perform(post("/api/yaBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaBuilding)))
                .andExpect(status().isCreated());

        // Validate the YaBuilding in the database
        List<YaBuilding> yaBuildings = yaBuildingRepository.findAll();
        assertThat(yaBuildings).hasSize(databaseSizeBeforeCreate + 1);
        YaBuilding testYaBuilding = yaBuildings.get(yaBuildings.size() - 1);
        assertThat(testYaBuilding.getBuildingCode()).isEqualTo(DEFAULT_BUILDING_CODE);
        assertThat(testYaBuilding.getBuildingName()).isEqualTo(DEFAULT_BUILDING_NAME);
    }

    @Test
    @Transactional
    public void getAllYaBuildings() throws Exception {
        // Initialize the database
        yaBuildingRepository.saveAndFlush(yaBuilding);

        // Get all the yaBuildings
        restYaBuildingMockMvc.perform(get("/api/yaBuildings?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaBuilding.getId().intValue())))
                .andExpect(jsonPath("$.[*].buildingCode").value(hasItem(DEFAULT_BUILDING_CODE)))
                .andExpect(jsonPath("$.[*].buildingName").value(hasItem(DEFAULT_BUILDING_NAME.toString())));
    }

    @Test
    @Transactional
    public void getYaBuilding() throws Exception {
        // Initialize the database
        yaBuildingRepository.saveAndFlush(yaBuilding);

        // Get the yaBuilding
        restYaBuildingMockMvc.perform(get("/api/yaBuildings/{id}", yaBuilding.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaBuilding.getId().intValue()))
            .andExpect(jsonPath("$.buildingCode").value(DEFAULT_BUILDING_CODE))
            .andExpect(jsonPath("$.buildingName").value(DEFAULT_BUILDING_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingYaBuilding() throws Exception {
        // Get the yaBuilding
        restYaBuildingMockMvc.perform(get("/api/yaBuildings/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaBuilding() throws Exception {
        // Initialize the database
        yaBuildingRepository.saveAndFlush(yaBuilding);

		int databaseSizeBeforeUpdate = yaBuildingRepository.findAll().size();

        // Update the yaBuilding
        yaBuilding.setBuildingCode(UPDATED_BUILDING_CODE);
        yaBuilding.setBuildingName(UPDATED_BUILDING_NAME);

        restYaBuildingMockMvc.perform(put("/api/yaBuildings")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaBuilding)))
                .andExpect(status().isOk());

        // Validate the YaBuilding in the database
        List<YaBuilding> yaBuildings = yaBuildingRepository.findAll();
        assertThat(yaBuildings).hasSize(databaseSizeBeforeUpdate);
        YaBuilding testYaBuilding = yaBuildings.get(yaBuildings.size() - 1);
        assertThat(testYaBuilding.getBuildingCode()).isEqualTo(UPDATED_BUILDING_CODE);
        assertThat(testYaBuilding.getBuildingName()).isEqualTo(UPDATED_BUILDING_NAME);
    }

    @Test
    @Transactional
    public void deleteYaBuilding() throws Exception {
        // Initialize the database
        yaBuildingRepository.saveAndFlush(yaBuilding);

		int databaseSizeBeforeDelete = yaBuildingRepository.findAll().size();

        // Get the yaBuilding
        restYaBuildingMockMvc.perform(delete("/api/yaBuildings/{id}", yaBuilding.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaBuilding> yaBuildings = yaBuildingRepository.findAll();
        assertThat(yaBuildings).hasSize(databaseSizeBeforeDelete - 1);
    }
}
