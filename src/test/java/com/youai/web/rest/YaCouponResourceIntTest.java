package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaCoupon;
import com.youai.repository.YaCouponRepository;

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

import com.youai.domain.enumeration.CouponType;

/**
 * Test class for the YaCouponResource REST controller.
 *
 * @see YaCouponResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaCouponResourceIntTest {

    private static final String DEFAULT_COUPON_NAME = "AAAAAA";
    private static final String UPDATED_COUPON_NAME = "BBBBBB";
    
    private static final CouponType DEFAULT_COUPON_TYPE = CouponType.DISCOUNT;
    private static final CouponType UPDATED_COUPON_TYPE = CouponType.FREEONE;
    private static final String DEFAULT_COUPON_PICTURES = "AAAAA";
    private static final String UPDATED_COUPON_PICTURES = "BBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;
    private static final String DEFAULT_KEYWORDS = "AAAAA";
    private static final String UPDATED_KEYWORDS = "BBBBB";
    private static final String DEFAULT_COUPON_DISCRIPTION = "AAAAA";
    private static final String UPDATED_COUPON_DISCRIPTION = "BBBBB";

    private static final Integer DEFAULT_COUPON_AMOUNT = 1;
    private static final Integer UPDATED_COUPON_AMOUNT = 2;

    private static final Integer DEFAULT_COUPON_INVENTORY = 1;
    private static final Integer UPDATED_COUPON_INVENTORY = 2;

    private static final LocalDate DEFAULT_ON_SHELVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ON_SHELVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_OFF_SHELVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OFF_SHELVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_COUPON_STATUS = false;
    private static final Boolean UPDATED_COUPON_STATUS = true;

    @Inject
    private YaCouponRepository yaCouponRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaCouponMockMvc;

    private YaCoupon yaCoupon;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaCouponResource yaCouponResource = new YaCouponResource();
        ReflectionTestUtils.setField(yaCouponResource, "yaCouponRepository", yaCouponRepository);
        this.restYaCouponMockMvc = MockMvcBuilders.standaloneSetup(yaCouponResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaCoupon = new YaCoupon();
        yaCoupon.setCouponName(DEFAULT_COUPON_NAME);
        yaCoupon.setCouponType(DEFAULT_COUPON_TYPE);
        yaCoupon.setCouponPictures(DEFAULT_COUPON_PICTURES);
        yaCoupon.setPrice(DEFAULT_PRICE);
        yaCoupon.setKeywords(DEFAULT_KEYWORDS);
        yaCoupon.setCouponDiscription(DEFAULT_COUPON_DISCRIPTION);
        yaCoupon.setCouponAmount(DEFAULT_COUPON_AMOUNT);
        yaCoupon.setCouponInventory(DEFAULT_COUPON_INVENTORY);
        yaCoupon.setOnShelveDate(DEFAULT_ON_SHELVE_DATE);
        yaCoupon.setOffShelveDate(DEFAULT_OFF_SHELVE_DATE);
        yaCoupon.setCouponStatus(DEFAULT_COUPON_STATUS);
    }

    @Test
    @Transactional
    public void createYaCoupon() throws Exception {
        int databaseSizeBeforeCreate = yaCouponRepository.findAll().size();

        // Create the YaCoupon

        restYaCouponMockMvc.perform(post("/api/yaCoupons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCoupon)))
                .andExpect(status().isCreated());

        // Validate the YaCoupon in the database
        List<YaCoupon> yaCoupons = yaCouponRepository.findAll();
        assertThat(yaCoupons).hasSize(databaseSizeBeforeCreate + 1);
        YaCoupon testYaCoupon = yaCoupons.get(yaCoupons.size() - 1);
        assertThat(testYaCoupon.getCouponName()).isEqualTo(DEFAULT_COUPON_NAME);
        assertThat(testYaCoupon.getCouponType()).isEqualTo(DEFAULT_COUPON_TYPE);
        assertThat(testYaCoupon.getCouponPictures()).isEqualTo(DEFAULT_COUPON_PICTURES);
        assertThat(testYaCoupon.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testYaCoupon.getKeywords()).isEqualTo(DEFAULT_KEYWORDS);
        assertThat(testYaCoupon.getCouponDiscription()).isEqualTo(DEFAULT_COUPON_DISCRIPTION);
        assertThat(testYaCoupon.getCouponAmount()).isEqualTo(DEFAULT_COUPON_AMOUNT);
        assertThat(testYaCoupon.getCouponInventory()).isEqualTo(DEFAULT_COUPON_INVENTORY);
        assertThat(testYaCoupon.getOnShelveDate()).isEqualTo(DEFAULT_ON_SHELVE_DATE);
        assertThat(testYaCoupon.getOffShelveDate()).isEqualTo(DEFAULT_OFF_SHELVE_DATE);
        assertThat(testYaCoupon.getCouponStatus()).isEqualTo(DEFAULT_COUPON_STATUS);
    }

    @Test
    @Transactional
    public void checkCouponNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaCouponRepository.findAll().size();
        // set the field null
        yaCoupon.setCouponName(null);

        // Create the YaCoupon, which fails.

        restYaCouponMockMvc.perform(post("/api/yaCoupons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCoupon)))
                .andExpect(status().isBadRequest());

        List<YaCoupon> yaCoupons = yaCouponRepository.findAll();
        assertThat(yaCoupons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCouponTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaCouponRepository.findAll().size();
        // set the field null
        yaCoupon.setCouponType(null);

        // Create the YaCoupon, which fails.

        restYaCouponMockMvc.perform(post("/api/yaCoupons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCoupon)))
                .andExpect(status().isBadRequest());

        List<YaCoupon> yaCoupons = yaCouponRepository.findAll();
        assertThat(yaCoupons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCouponPicturesIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaCouponRepository.findAll().size();
        // set the field null
        yaCoupon.setCouponPictures(null);

        // Create the YaCoupon, which fails.

        restYaCouponMockMvc.perform(post("/api/yaCoupons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCoupon)))
                .andExpect(status().isBadRequest());

        List<YaCoupon> yaCoupons = yaCouponRepository.findAll();
        assertThat(yaCoupons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCouponAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaCouponRepository.findAll().size();
        // set the field null
        yaCoupon.setCouponAmount(null);

        // Create the YaCoupon, which fails.

        restYaCouponMockMvc.perform(post("/api/yaCoupons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCoupon)))
                .andExpect(status().isBadRequest());

        List<YaCoupon> yaCoupons = yaCouponRepository.findAll();
        assertThat(yaCoupons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCouponInventoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaCouponRepository.findAll().size();
        // set the field null
        yaCoupon.setCouponInventory(null);

        // Create the YaCoupon, which fails.

        restYaCouponMockMvc.perform(post("/api/yaCoupons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCoupon)))
                .andExpect(status().isBadRequest());

        List<YaCoupon> yaCoupons = yaCouponRepository.findAll();
        assertThat(yaCoupons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOnShelveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaCouponRepository.findAll().size();
        // set the field null
        yaCoupon.setOnShelveDate(null);

        // Create the YaCoupon, which fails.

        restYaCouponMockMvc.perform(post("/api/yaCoupons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCoupon)))
                .andExpect(status().isBadRequest());

        List<YaCoupon> yaCoupons = yaCouponRepository.findAll();
        assertThat(yaCoupons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOffShelveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaCouponRepository.findAll().size();
        // set the field null
        yaCoupon.setOffShelveDate(null);

        // Create the YaCoupon, which fails.

        restYaCouponMockMvc.perform(post("/api/yaCoupons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCoupon)))
                .andExpect(status().isBadRequest());

        List<YaCoupon> yaCoupons = yaCouponRepository.findAll();
        assertThat(yaCoupons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYaCoupons() throws Exception {
        // Initialize the database
        yaCouponRepository.saveAndFlush(yaCoupon);

        // Get all the yaCoupons
        restYaCouponMockMvc.perform(get("/api/yaCoupons?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaCoupon.getId().intValue())))
                .andExpect(jsonPath("$.[*].couponName").value(hasItem(DEFAULT_COUPON_NAME.toString())))
                .andExpect(jsonPath("$.[*].couponType").value(hasItem(DEFAULT_COUPON_TYPE.toString())))
                .andExpect(jsonPath("$.[*].couponPictures").value(hasItem(DEFAULT_COUPON_PICTURES.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS.toString())))
                .andExpect(jsonPath("$.[*].couponDiscription").value(hasItem(DEFAULT_COUPON_DISCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].couponAmount").value(hasItem(DEFAULT_COUPON_AMOUNT)))
                .andExpect(jsonPath("$.[*].couponInventory").value(hasItem(DEFAULT_COUPON_INVENTORY)))
                .andExpect(jsonPath("$.[*].onShelveDate").value(hasItem(DEFAULT_ON_SHELVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].offShelveDate").value(hasItem(DEFAULT_OFF_SHELVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].couponStatus").value(hasItem(DEFAULT_COUPON_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getYaCoupon() throws Exception {
        // Initialize the database
        yaCouponRepository.saveAndFlush(yaCoupon);

        // Get the yaCoupon
        restYaCouponMockMvc.perform(get("/api/yaCoupons/{id}", yaCoupon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaCoupon.getId().intValue()))
            .andExpect(jsonPath("$.couponName").value(DEFAULT_COUPON_NAME.toString()))
            .andExpect(jsonPath("$.couponType").value(DEFAULT_COUPON_TYPE.toString()))
            .andExpect(jsonPath("$.couponPictures").value(DEFAULT_COUPON_PICTURES.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.keywords").value(DEFAULT_KEYWORDS.toString()))
            .andExpect(jsonPath("$.couponDiscription").value(DEFAULT_COUPON_DISCRIPTION.toString()))
            .andExpect(jsonPath("$.couponAmount").value(DEFAULT_COUPON_AMOUNT))
            .andExpect(jsonPath("$.couponInventory").value(DEFAULT_COUPON_INVENTORY))
            .andExpect(jsonPath("$.onShelveDate").value(DEFAULT_ON_SHELVE_DATE.toString()))
            .andExpect(jsonPath("$.offShelveDate").value(DEFAULT_OFF_SHELVE_DATE.toString()))
            .andExpect(jsonPath("$.couponStatus").value(DEFAULT_COUPON_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingYaCoupon() throws Exception {
        // Get the yaCoupon
        restYaCouponMockMvc.perform(get("/api/yaCoupons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaCoupon() throws Exception {
        // Initialize the database
        yaCouponRepository.saveAndFlush(yaCoupon);

		int databaseSizeBeforeUpdate = yaCouponRepository.findAll().size();

        // Update the yaCoupon
        yaCoupon.setCouponName(UPDATED_COUPON_NAME);
        yaCoupon.setCouponType(UPDATED_COUPON_TYPE);
        yaCoupon.setCouponPictures(UPDATED_COUPON_PICTURES);
        yaCoupon.setPrice(UPDATED_PRICE);
        yaCoupon.setKeywords(UPDATED_KEYWORDS);
        yaCoupon.setCouponDiscription(UPDATED_COUPON_DISCRIPTION);
        yaCoupon.setCouponAmount(UPDATED_COUPON_AMOUNT);
        yaCoupon.setCouponInventory(UPDATED_COUPON_INVENTORY);
        yaCoupon.setOnShelveDate(UPDATED_ON_SHELVE_DATE);
        yaCoupon.setOffShelveDate(UPDATED_OFF_SHELVE_DATE);
        yaCoupon.setCouponStatus(UPDATED_COUPON_STATUS);

        restYaCouponMockMvc.perform(put("/api/yaCoupons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCoupon)))
                .andExpect(status().isOk());

        // Validate the YaCoupon in the database
        List<YaCoupon> yaCoupons = yaCouponRepository.findAll();
        assertThat(yaCoupons).hasSize(databaseSizeBeforeUpdate);
        YaCoupon testYaCoupon = yaCoupons.get(yaCoupons.size() - 1);
        assertThat(testYaCoupon.getCouponName()).isEqualTo(UPDATED_COUPON_NAME);
        assertThat(testYaCoupon.getCouponType()).isEqualTo(UPDATED_COUPON_TYPE);
        assertThat(testYaCoupon.getCouponPictures()).isEqualTo(UPDATED_COUPON_PICTURES);
        assertThat(testYaCoupon.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testYaCoupon.getKeywords()).isEqualTo(UPDATED_KEYWORDS);
        assertThat(testYaCoupon.getCouponDiscription()).isEqualTo(UPDATED_COUPON_DISCRIPTION);
        assertThat(testYaCoupon.getCouponAmount()).isEqualTo(UPDATED_COUPON_AMOUNT);
        assertThat(testYaCoupon.getCouponInventory()).isEqualTo(UPDATED_COUPON_INVENTORY);
        assertThat(testYaCoupon.getOnShelveDate()).isEqualTo(UPDATED_ON_SHELVE_DATE);
        assertThat(testYaCoupon.getOffShelveDate()).isEqualTo(UPDATED_OFF_SHELVE_DATE);
        assertThat(testYaCoupon.getCouponStatus()).isEqualTo(UPDATED_COUPON_STATUS);
    }

    @Test
    @Transactional
    public void deleteYaCoupon() throws Exception {
        // Initialize the database
        yaCouponRepository.saveAndFlush(yaCoupon);

		int databaseSizeBeforeDelete = yaCouponRepository.findAll().size();

        // Get the yaCoupon
        restYaCouponMockMvc.perform(delete("/api/yaCoupons/{id}", yaCoupon.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaCoupon> yaCoupons = yaCouponRepository.findAll();
        assertThat(yaCoupons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
