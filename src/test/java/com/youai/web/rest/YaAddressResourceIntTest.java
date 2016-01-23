package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaAddress;
import com.youai.repository.YaAddressRepository;

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
 * Test class for the YaAddressResource REST controller.
 *
 * @see YaAddressResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaAddressResourceIntTest {

    private static final String DEFAULT_RECEIVER_NAME = "AAAAAA";
    private static final String UPDATED_RECEIVER_NAME = "BBBBBB";
    private static final String DEFAULT_RECEIVER_MOBILE = "AAAAA";
    private static final String UPDATED_RECEIVER_MOBILE = "BBBBB";
    private static final String DEFAULT_RECEIVER_DISTRICT = "AAAAA";
    private static final String UPDATED_RECEIVER_DISTRICT = "BBBBB";
    private static final String DEFAULT_RECEIVER_BUILDING = "AAAAA";
    private static final String UPDATED_RECEIVER_BUILDING = "BBBBB";
    private static final String DEFAULT_RECEIVER_FLOOR = "AAAAA";
    private static final String UPDATED_RECEIVER_FLOOR = "BBBBB";
    private static final String DEFAULT_RECEIVER_COMPANY = "AAAAA";
    private static final String UPDATED_RECEIVER_COMPANY = "BBBBB";

    private static final Boolean DEFAULT_IS_DEFAULT = false;
    private static final Boolean UPDATED_IS_DEFAULT = true;

    @Inject
    private YaAddressRepository yaAddressRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaAddressMockMvc;

    private YaAddress yaAddress;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaAddressResource yaAddressResource = new YaAddressResource();
        ReflectionTestUtils.setField(yaAddressResource, "yaAddressRepository", yaAddressRepository);
        this.restYaAddressMockMvc = MockMvcBuilders.standaloneSetup(yaAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaAddress = new YaAddress();
        yaAddress.setReceiverName(DEFAULT_RECEIVER_NAME);
        yaAddress.setReceiverMobile(DEFAULT_RECEIVER_MOBILE);
        yaAddress.setReceiverDistrict(DEFAULT_RECEIVER_DISTRICT);
        yaAddress.setReceiverBuilding(DEFAULT_RECEIVER_BUILDING);
        yaAddress.setReceiverFloor(DEFAULT_RECEIVER_FLOOR);
        yaAddress.setReceiverCompany(DEFAULT_RECEIVER_COMPANY);
        yaAddress.setIsDefault(DEFAULT_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void createYaAddress() throws Exception {
        int databaseSizeBeforeCreate = yaAddressRepository.findAll().size();

        // Create the YaAddress

        restYaAddressMockMvc.perform(post("/api/yaAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaAddress)))
                .andExpect(status().isCreated());

        // Validate the YaAddress in the database
        List<YaAddress> yaAddresss = yaAddressRepository.findAll();
        assertThat(yaAddresss).hasSize(databaseSizeBeforeCreate + 1);
        YaAddress testYaAddress = yaAddresss.get(yaAddresss.size() - 1);
        assertThat(testYaAddress.getReceiverName()).isEqualTo(DEFAULT_RECEIVER_NAME);
        assertThat(testYaAddress.getReceiverMobile()).isEqualTo(DEFAULT_RECEIVER_MOBILE);
        assertThat(testYaAddress.getReceiverDistrict()).isEqualTo(DEFAULT_RECEIVER_DISTRICT);
        assertThat(testYaAddress.getReceiverBuilding()).isEqualTo(DEFAULT_RECEIVER_BUILDING);
        assertThat(testYaAddress.getReceiverFloor()).isEqualTo(DEFAULT_RECEIVER_FLOOR);
        assertThat(testYaAddress.getReceiverCompany()).isEqualTo(DEFAULT_RECEIVER_COMPANY);
        assertThat(testYaAddress.getIsDefault()).isEqualTo(DEFAULT_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void checkReceiverNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaAddressRepository.findAll().size();
        // set the field null
        yaAddress.setReceiverName(null);

        // Create the YaAddress, which fails.

        restYaAddressMockMvc.perform(post("/api/yaAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaAddress)))
                .andExpect(status().isBadRequest());

        List<YaAddress> yaAddresss = yaAddressRepository.findAll();
        assertThat(yaAddresss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsDefaultIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaAddressRepository.findAll().size();
        // set the field null
        yaAddress.setIsDefault(null);

        // Create the YaAddress, which fails.

        restYaAddressMockMvc.perform(post("/api/yaAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaAddress)))
                .andExpect(status().isBadRequest());

        List<YaAddress> yaAddresss = yaAddressRepository.findAll();
        assertThat(yaAddresss).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYaAddresss() throws Exception {
        // Initialize the database
        yaAddressRepository.saveAndFlush(yaAddress);

        // Get all the yaAddresss
        restYaAddressMockMvc.perform(get("/api/yaAddresss?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaAddress.getId().intValue())))
                .andExpect(jsonPath("$.[*].receiverName").value(hasItem(DEFAULT_RECEIVER_NAME.toString())))
                .andExpect(jsonPath("$.[*].receiverMobile").value(hasItem(DEFAULT_RECEIVER_MOBILE.toString())))
                .andExpect(jsonPath("$.[*].receiverDistrict").value(hasItem(DEFAULT_RECEIVER_DISTRICT.toString())))
                .andExpect(jsonPath("$.[*].receiverBuilding").value(hasItem(DEFAULT_RECEIVER_BUILDING.toString())))
                .andExpect(jsonPath("$.[*].receiverFloor").value(hasItem(DEFAULT_RECEIVER_FLOOR.toString())))
                .andExpect(jsonPath("$.[*].receiverCompany").value(hasItem(DEFAULT_RECEIVER_COMPANY.toString())))
                .andExpect(jsonPath("$.[*].isDefault").value(hasItem(DEFAULT_IS_DEFAULT.booleanValue())));
    }

    @Test
    @Transactional
    public void getYaAddress() throws Exception {
        // Initialize the database
        yaAddressRepository.saveAndFlush(yaAddress);

        // Get the yaAddress
        restYaAddressMockMvc.perform(get("/api/yaAddresss/{id}", yaAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaAddress.getId().intValue()))
            .andExpect(jsonPath("$.receiverName").value(DEFAULT_RECEIVER_NAME.toString()))
            .andExpect(jsonPath("$.receiverMobile").value(DEFAULT_RECEIVER_MOBILE.toString()))
            .andExpect(jsonPath("$.receiverDistrict").value(DEFAULT_RECEIVER_DISTRICT.toString()))
            .andExpect(jsonPath("$.receiverBuilding").value(DEFAULT_RECEIVER_BUILDING.toString()))
            .andExpect(jsonPath("$.receiverFloor").value(DEFAULT_RECEIVER_FLOOR.toString()))
            .andExpect(jsonPath("$.receiverCompany").value(DEFAULT_RECEIVER_COMPANY.toString()))
            .andExpect(jsonPath("$.isDefault").value(DEFAULT_IS_DEFAULT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingYaAddress() throws Exception {
        // Get the yaAddress
        restYaAddressMockMvc.perform(get("/api/yaAddresss/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaAddress() throws Exception {
        // Initialize the database
        yaAddressRepository.saveAndFlush(yaAddress);

		int databaseSizeBeforeUpdate = yaAddressRepository.findAll().size();

        // Update the yaAddress
        yaAddress.setReceiverName(UPDATED_RECEIVER_NAME);
        yaAddress.setReceiverMobile(UPDATED_RECEIVER_MOBILE);
        yaAddress.setReceiverDistrict(UPDATED_RECEIVER_DISTRICT);
        yaAddress.setReceiverBuilding(UPDATED_RECEIVER_BUILDING);
        yaAddress.setReceiverFloor(UPDATED_RECEIVER_FLOOR);
        yaAddress.setReceiverCompany(UPDATED_RECEIVER_COMPANY);
        yaAddress.setIsDefault(UPDATED_IS_DEFAULT);

        restYaAddressMockMvc.perform(put("/api/yaAddresss")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaAddress)))
                .andExpect(status().isOk());

        // Validate the YaAddress in the database
        List<YaAddress> yaAddresss = yaAddressRepository.findAll();
        assertThat(yaAddresss).hasSize(databaseSizeBeforeUpdate);
        YaAddress testYaAddress = yaAddresss.get(yaAddresss.size() - 1);
        assertThat(testYaAddress.getReceiverName()).isEqualTo(UPDATED_RECEIVER_NAME);
        assertThat(testYaAddress.getReceiverMobile()).isEqualTo(UPDATED_RECEIVER_MOBILE);
        assertThat(testYaAddress.getReceiverDistrict()).isEqualTo(UPDATED_RECEIVER_DISTRICT);
        assertThat(testYaAddress.getReceiverBuilding()).isEqualTo(UPDATED_RECEIVER_BUILDING);
        assertThat(testYaAddress.getReceiverFloor()).isEqualTo(UPDATED_RECEIVER_FLOOR);
        assertThat(testYaAddress.getReceiverCompany()).isEqualTo(UPDATED_RECEIVER_COMPANY);
        assertThat(testYaAddress.getIsDefault()).isEqualTo(UPDATED_IS_DEFAULT);
    }

    @Test
    @Transactional
    public void deleteYaAddress() throws Exception {
        // Initialize the database
        yaAddressRepository.saveAndFlush(yaAddress);

		int databaseSizeBeforeDelete = yaAddressRepository.findAll().size();

        // Get the yaAddress
        restYaAddressMockMvc.perform(delete("/api/yaAddresss/{id}", yaAddress.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaAddress> yaAddresss = yaAddressRepository.findAll();
        assertThat(yaAddresss).hasSize(databaseSizeBeforeDelete - 1);
    }
}
