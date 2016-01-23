package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaPot;
import com.youai.repository.YaPotRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.youai.domain.enumeration.PotStatus;

/**
 * Test class for the YaPotResource REST controller.
 *
 * @see YaPotResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaPotResourceIntTest {


    private static final Boolean DEFAULT_IS_PRIVATE_POT = false;
    private static final Boolean UPDATED_IS_PRIVATE_POT = true;
    private static final String DEFAULT_POT_QRCODE = "AAAAA";
    private static final String UPDATED_POT_QRCODE = "BBBBB";
    private static final String DEFAULT_POT_LABEL = "AAAA";
    private static final String UPDATED_POT_LABEL = "BBBB";
    
    private static final PotStatus DEFAULT_POT_STATUS = PotStatus.CLEANED;
    private static final PotStatus UPDATED_POT_STATUS = PotStatus.NEEDCLEAN;
    private static final String DEFAULT_POT_COLOR = "AAAAA";
    private static final String UPDATED_POT_COLOR = "BBBBB";

    private static final Integer DEFAULT_POT_CAPACITY = 1;
    private static final Integer UPDATED_POT_CAPACITY = 2;

    private static final LocalDate DEFAULT_POT_BUY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POT_BUY_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private YaPotRepository yaPotRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaPotMockMvc;

    private YaPot yaPot;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaPotResource yaPotResource = new YaPotResource();
        ReflectionTestUtils.setField(yaPotResource, "yaPotRepository", yaPotRepository);
        this.restYaPotMockMvc = MockMvcBuilders.standaloneSetup(yaPotResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaPot = new YaPot();
        yaPot.setIsPrivatePot(DEFAULT_IS_PRIVATE_POT);
        yaPot.setPotQrcode(DEFAULT_POT_QRCODE);
        yaPot.setPotLabel(DEFAULT_POT_LABEL);
        yaPot.setPotStatus(DEFAULT_POT_STATUS);
        yaPot.setPotColor(DEFAULT_POT_COLOR);
        yaPot.setPotCapacity(DEFAULT_POT_CAPACITY);
        yaPot.setPotBuyDate(DEFAULT_POT_BUY_DATE);
    }

    @Test
    @Transactional
    public void createYaPot() throws Exception {
        int databaseSizeBeforeCreate = yaPotRepository.findAll().size();

        // Create the YaPot

        restYaPotMockMvc.perform(post("/api/yaPots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaPot)))
                .andExpect(status().isCreated());

        // Validate the YaPot in the database
        List<YaPot> yaPots = yaPotRepository.findAll();
        assertThat(yaPots).hasSize(databaseSizeBeforeCreate + 1);
        YaPot testYaPot = yaPots.get(yaPots.size() - 1);
        assertThat(testYaPot.getIsPrivatePot()).isEqualTo(DEFAULT_IS_PRIVATE_POT);
        assertThat(testYaPot.getPotQrcode()).isEqualTo(DEFAULT_POT_QRCODE);
        assertThat(testYaPot.getPotLabel()).isEqualTo(DEFAULT_POT_LABEL);
        assertThat(testYaPot.getPotStatus()).isEqualTo(DEFAULT_POT_STATUS);
        assertThat(testYaPot.getPotColor()).isEqualTo(DEFAULT_POT_COLOR);
        assertThat(testYaPot.getPotCapacity()).isEqualTo(DEFAULT_POT_CAPACITY);
        assertThat(testYaPot.getPotBuyDate()).isEqualTo(DEFAULT_POT_BUY_DATE);
    }

    @Test
    @Transactional
    public void checkIsPrivatePotIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaPotRepository.findAll().size();
        // set the field null
        yaPot.setIsPrivatePot(null);

        // Create the YaPot, which fails.

        restYaPotMockMvc.perform(post("/api/yaPots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaPot)))
                .andExpect(status().isBadRequest());

        List<YaPot> yaPots = yaPotRepository.findAll();
        assertThat(yaPots).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPotQrcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaPotRepository.findAll().size();
        // set the field null
        yaPot.setPotQrcode(null);

        // Create the YaPot, which fails.

        restYaPotMockMvc.perform(post("/api/yaPots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaPot)))
                .andExpect(status().isBadRequest());

        List<YaPot> yaPots = yaPotRepository.findAll();
        assertThat(yaPots).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPotStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaPotRepository.findAll().size();
        // set the field null
        yaPot.setPotStatus(null);

        // Create the YaPot, which fails.

        restYaPotMockMvc.perform(post("/api/yaPots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaPot)))
                .andExpect(status().isBadRequest());

        List<YaPot> yaPots = yaPotRepository.findAll();
        assertThat(yaPots).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYaPots() throws Exception {
        // Initialize the database
        yaPotRepository.saveAndFlush(yaPot);

        // Get all the yaPots
        restYaPotMockMvc.perform(get("/api/yaPots?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaPot.getId().intValue())))
                .andExpect(jsonPath("$.[*].isPrivatePot").value(hasItem(DEFAULT_IS_PRIVATE_POT.booleanValue())))
                .andExpect(jsonPath("$.[*].potQrcode").value(hasItem(DEFAULT_POT_QRCODE.toString())))
                .andExpect(jsonPath("$.[*].potLabel").value(hasItem(DEFAULT_POT_LABEL.toString())))
                .andExpect(jsonPath("$.[*].potStatus").value(hasItem(DEFAULT_POT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].potColor").value(hasItem(DEFAULT_POT_COLOR.toString())))
                .andExpect(jsonPath("$.[*].potCapacity").value(hasItem(DEFAULT_POT_CAPACITY)))
                .andExpect(jsonPath("$.[*].potBuyDate").value(hasItem(DEFAULT_POT_BUY_DATE.toString())));
    }

    @Test
    @Transactional
    public void getYaPot() throws Exception {
        // Initialize the database
        yaPotRepository.saveAndFlush(yaPot);

        // Get the yaPot
        restYaPotMockMvc.perform(get("/api/yaPots/{id}", yaPot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaPot.getId().intValue()))
            .andExpect(jsonPath("$.isPrivatePot").value(DEFAULT_IS_PRIVATE_POT.booleanValue()))
            .andExpect(jsonPath("$.potQrcode").value(DEFAULT_POT_QRCODE.toString()))
            .andExpect(jsonPath("$.potLabel").value(DEFAULT_POT_LABEL.toString()))
            .andExpect(jsonPath("$.potStatus").value(DEFAULT_POT_STATUS.toString()))
            .andExpect(jsonPath("$.potColor").value(DEFAULT_POT_COLOR.toString()))
            .andExpect(jsonPath("$.potCapacity").value(DEFAULT_POT_CAPACITY))
            .andExpect(jsonPath("$.potBuyDate").value(DEFAULT_POT_BUY_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingYaPot() throws Exception {
        // Get the yaPot
        restYaPotMockMvc.perform(get("/api/yaPots/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaPot() throws Exception {
        // Initialize the database
        yaPotRepository.saveAndFlush(yaPot);

		int databaseSizeBeforeUpdate = yaPotRepository.findAll().size();

        // Update the yaPot
        yaPot.setIsPrivatePot(UPDATED_IS_PRIVATE_POT);
        yaPot.setPotQrcode(UPDATED_POT_QRCODE);
        yaPot.setPotLabel(UPDATED_POT_LABEL);
        yaPot.setPotStatus(UPDATED_POT_STATUS);
        yaPot.setPotColor(UPDATED_POT_COLOR);
        yaPot.setPotCapacity(UPDATED_POT_CAPACITY);
        yaPot.setPotBuyDate(UPDATED_POT_BUY_DATE);

        restYaPotMockMvc.perform(put("/api/yaPots")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaPot)))
                .andExpect(status().isOk());

        // Validate the YaPot in the database
        List<YaPot> yaPots = yaPotRepository.findAll();
        assertThat(yaPots).hasSize(databaseSizeBeforeUpdate);
        YaPot testYaPot = yaPots.get(yaPots.size() - 1);
        assertThat(testYaPot.getIsPrivatePot()).isEqualTo(UPDATED_IS_PRIVATE_POT);
        assertThat(testYaPot.getPotQrcode()).isEqualTo(UPDATED_POT_QRCODE);
        assertThat(testYaPot.getPotLabel()).isEqualTo(UPDATED_POT_LABEL);
        assertThat(testYaPot.getPotStatus()).isEqualTo(UPDATED_POT_STATUS);
        assertThat(testYaPot.getPotColor()).isEqualTo(UPDATED_POT_COLOR);
        assertThat(testYaPot.getPotCapacity()).isEqualTo(UPDATED_POT_CAPACITY);
        assertThat(testYaPot.getPotBuyDate()).isEqualTo(UPDATED_POT_BUY_DATE);
    }

    @Test
    @Transactional
    public void deleteYaPot() throws Exception {
        // Initialize the database
        yaPotRepository.saveAndFlush(yaPot);

		int databaseSizeBeforeDelete = yaPotRepository.findAll().size();

        // Get the yaPot
        restYaPotMockMvc.perform(delete("/api/yaPots/{id}", yaPot.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaPot> yaPots = yaPotRepository.findAll();
        assertThat(yaPots).hasSize(databaseSizeBeforeDelete - 1);
    }
}
