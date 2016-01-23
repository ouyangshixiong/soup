package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaDistrict;
import com.youai.repository.YaDistrictRepository;

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
 * Test class for the YaDistrictResource REST controller.
 *
 * @see YaDistrictResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaDistrictResourceIntTest {


    private static final Integer DEFAULT_DISTRICT_CODE = 1;
    private static final Integer UPDATED_DISTRICT_CODE = 2;
    private static final String DEFAULT_DISTRICT_NAME = "AAAAA";
    private static final String UPDATED_DISTRICT_NAME = "BBBBB";

    @Inject
    private YaDistrictRepository yaDistrictRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaDistrictMockMvc;

    private YaDistrict yaDistrict;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaDistrictResource yaDistrictResource = new YaDistrictResource();
        ReflectionTestUtils.setField(yaDistrictResource, "yaDistrictRepository", yaDistrictRepository);
        this.restYaDistrictMockMvc = MockMvcBuilders.standaloneSetup(yaDistrictResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaDistrict = new YaDistrict();
        yaDistrict.setDistrictCode(DEFAULT_DISTRICT_CODE);
        yaDistrict.setDistrictName(DEFAULT_DISTRICT_NAME);
    }

    @Test
    @Transactional
    public void createYaDistrict() throws Exception {
        int databaseSizeBeforeCreate = yaDistrictRepository.findAll().size();

        // Create the YaDistrict

        restYaDistrictMockMvc.perform(post("/api/yaDistricts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaDistrict)))
                .andExpect(status().isCreated());

        // Validate the YaDistrict in the database
        List<YaDistrict> yaDistricts = yaDistrictRepository.findAll();
        assertThat(yaDistricts).hasSize(databaseSizeBeforeCreate + 1);
        YaDistrict testYaDistrict = yaDistricts.get(yaDistricts.size() - 1);
        assertThat(testYaDistrict.getDistrictCode()).isEqualTo(DEFAULT_DISTRICT_CODE);
        assertThat(testYaDistrict.getDistrictName()).isEqualTo(DEFAULT_DISTRICT_NAME);
    }

    @Test
    @Transactional
    public void getAllYaDistricts() throws Exception {
        // Initialize the database
        yaDistrictRepository.saveAndFlush(yaDistrict);

        // Get all the yaDistricts
        restYaDistrictMockMvc.perform(get("/api/yaDistricts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaDistrict.getId().intValue())))
                .andExpect(jsonPath("$.[*].districtCode").value(hasItem(DEFAULT_DISTRICT_CODE)))
                .andExpect(jsonPath("$.[*].districtName").value(hasItem(DEFAULT_DISTRICT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getYaDistrict() throws Exception {
        // Initialize the database
        yaDistrictRepository.saveAndFlush(yaDistrict);

        // Get the yaDistrict
        restYaDistrictMockMvc.perform(get("/api/yaDistricts/{id}", yaDistrict.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaDistrict.getId().intValue()))
            .andExpect(jsonPath("$.districtCode").value(DEFAULT_DISTRICT_CODE))
            .andExpect(jsonPath("$.districtName").value(DEFAULT_DISTRICT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingYaDistrict() throws Exception {
        // Get the yaDistrict
        restYaDistrictMockMvc.perform(get("/api/yaDistricts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaDistrict() throws Exception {
        // Initialize the database
        yaDistrictRepository.saveAndFlush(yaDistrict);

		int databaseSizeBeforeUpdate = yaDistrictRepository.findAll().size();

        // Update the yaDistrict
        yaDistrict.setDistrictCode(UPDATED_DISTRICT_CODE);
        yaDistrict.setDistrictName(UPDATED_DISTRICT_NAME);

        restYaDistrictMockMvc.perform(put("/api/yaDistricts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaDistrict)))
                .andExpect(status().isOk());

        // Validate the YaDistrict in the database
        List<YaDistrict> yaDistricts = yaDistrictRepository.findAll();
        assertThat(yaDistricts).hasSize(databaseSizeBeforeUpdate);
        YaDistrict testYaDistrict = yaDistricts.get(yaDistricts.size() - 1);
        assertThat(testYaDistrict.getDistrictCode()).isEqualTo(UPDATED_DISTRICT_CODE);
        assertThat(testYaDistrict.getDistrictName()).isEqualTo(UPDATED_DISTRICT_NAME);
    }

    @Test
    @Transactional
    public void deleteYaDistrict() throws Exception {
        // Initialize the database
        yaDistrictRepository.saveAndFlush(yaDistrict);

		int databaseSizeBeforeDelete = yaDistrictRepository.findAll().size();

        // Get the yaDistrict
        restYaDistrictMockMvc.perform(delete("/api/yaDistricts/{id}", yaDistrict.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaDistrict> yaDistricts = yaDistrictRepository.findAll();
        assertThat(yaDistricts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
