package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaBanner;
import com.youai.repository.YaBannerRepository;

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


/**
 * Test class for the YaBannerResource REST controller.
 *
 * @see YaBannerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaBannerResourceIntTest {

    private static final String DEFAULT_BANNER_PICTURES = "AAAAA";
    private static final String UPDATED_BANNER_PICTURES = "BBBBB";
    private static final String DEFAULT_BANNER_NAME = "AAAAA";
    private static final String UPDATED_BANNER_NAME = "BBBBB";

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private YaBannerRepository yaBannerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaBannerMockMvc;

    private YaBanner yaBanner;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaBannerResource yaBannerResource = new YaBannerResource();
        ReflectionTestUtils.setField(yaBannerResource, "yaBannerRepository", yaBannerRepository);
        this.restYaBannerMockMvc = MockMvcBuilders.standaloneSetup(yaBannerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaBanner = new YaBanner();
        yaBanner.setBannerPictures(DEFAULT_BANNER_PICTURES);
        yaBanner.setBannerName(DEFAULT_BANNER_NAME);
        yaBanner.setUpdateDate(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void createYaBanner() throws Exception {
        int databaseSizeBeforeCreate = yaBannerRepository.findAll().size();

        // Create the YaBanner

        restYaBannerMockMvc.perform(post("/api/yaBanners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaBanner)))
                .andExpect(status().isCreated());

        // Validate the YaBanner in the database
        List<YaBanner> yaBanners = yaBannerRepository.findAll();
        assertThat(yaBanners).hasSize(databaseSizeBeforeCreate + 1);
        YaBanner testYaBanner = yaBanners.get(yaBanners.size() - 1);
        assertThat(testYaBanner.getBannerPictures()).isEqualTo(DEFAULT_BANNER_PICTURES);
        assertThat(testYaBanner.getBannerName()).isEqualTo(DEFAULT_BANNER_NAME);
        assertThat(testYaBanner.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void checkBannerPicturesIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaBannerRepository.findAll().size();
        // set the field null
        yaBanner.setBannerPictures(null);

        // Create the YaBanner, which fails.

        restYaBannerMockMvc.perform(post("/api/yaBanners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaBanner)))
                .andExpect(status().isBadRequest());

        List<YaBanner> yaBanners = yaBannerRepository.findAll();
        assertThat(yaBanners).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYaBanners() throws Exception {
        // Initialize the database
        yaBannerRepository.saveAndFlush(yaBanner);

        // Get all the yaBanners
        restYaBannerMockMvc.perform(get("/api/yaBanners?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaBanner.getId().intValue())))
                .andExpect(jsonPath("$.[*].bannerPictures").value(hasItem(DEFAULT_BANNER_PICTURES.toString())))
                .andExpect(jsonPath("$.[*].bannerName").value(hasItem(DEFAULT_BANNER_NAME.toString())))
                .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getYaBanner() throws Exception {
        // Initialize the database
        yaBannerRepository.saveAndFlush(yaBanner);

        // Get the yaBanner
        restYaBannerMockMvc.perform(get("/api/yaBanners/{id}", yaBanner.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaBanner.getId().intValue()))
            .andExpect(jsonPath("$.bannerPictures").value(DEFAULT_BANNER_PICTURES.toString()))
            .andExpect(jsonPath("$.bannerName").value(DEFAULT_BANNER_NAME.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingYaBanner() throws Exception {
        // Get the yaBanner
        restYaBannerMockMvc.perform(get("/api/yaBanners/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaBanner() throws Exception {
        // Initialize the database
        yaBannerRepository.saveAndFlush(yaBanner);

		int databaseSizeBeforeUpdate = yaBannerRepository.findAll().size();

        // Update the yaBanner
        yaBanner.setBannerPictures(UPDATED_BANNER_PICTURES);
        yaBanner.setBannerName(UPDATED_BANNER_NAME);
        yaBanner.setUpdateDate(UPDATED_UPDATE_DATE);

        restYaBannerMockMvc.perform(put("/api/yaBanners")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaBanner)))
                .andExpect(status().isOk());

        // Validate the YaBanner in the database
        List<YaBanner> yaBanners = yaBannerRepository.findAll();
        assertThat(yaBanners).hasSize(databaseSizeBeforeUpdate);
        YaBanner testYaBanner = yaBanners.get(yaBanners.size() - 1);
        assertThat(testYaBanner.getBannerPictures()).isEqualTo(UPDATED_BANNER_PICTURES);
        assertThat(testYaBanner.getBannerName()).isEqualTo(UPDATED_BANNER_NAME);
        assertThat(testYaBanner.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
    }

    @Test
    @Transactional
    public void deleteYaBanner() throws Exception {
        // Initialize the database
        yaBannerRepository.saveAndFlush(yaBanner);

		int databaseSizeBeforeDelete = yaBannerRepository.findAll().size();

        // Get the yaBanner
        restYaBannerMockMvc.perform(delete("/api/yaBanners/{id}", yaBanner.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaBanner> yaBanners = yaBannerRepository.findAll();
        assertThat(yaBanners).hasSize(databaseSizeBeforeDelete - 1);
    }
}
