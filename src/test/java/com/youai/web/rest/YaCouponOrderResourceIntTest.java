package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaCouponOrder;
import com.youai.repository.YaCouponOrderRepository;

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

import com.youai.domain.enumeration.CouponOrderStatus;

/**
 * Test class for the YaCouponOrderResource REST controller.
 *
 * @see YaCouponOrderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaCouponOrderResourceIntTest {

    private static final String DEFAULT_COUPON_ORDER_ID = "AAAAA";
    private static final String UPDATED_COUPON_ORDER_ID = "BBBBB";

    private static final LocalDate DEFAULT_COUPON_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COUPON_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_COUPON_EXPIRE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COUPON_EXPIRE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_COUPON_NEED_PAY = false;
    private static final Boolean UPDATED_COUPON_NEED_PAY = true;

    private static final Float DEFAULT_COUPON_ORDER_PRICE = 1F;
    private static final Float UPDATED_COUPON_ORDER_PRICE = 2F;
    
    private static final CouponOrderStatus DEFAULT_COUPON_ORDER_STATUS = CouponOrderStatus.ACTIVE;
    private static final CouponOrderStatus UPDATED_COUPON_ORDER_STATUS = CouponOrderStatus.DISCARD;

    private static final Long DEFAULT_ORDER_STATUS_CHANGE_TIME = 1L;
    private static final Long UPDATED_ORDER_STATUS_CHANGE_TIME = 2L;
    private static final String DEFAULT_PARENTCOUPON_ORDER_ID = "AAAAA";
    private static final String UPDATED_PARENTCOUPON_ORDER_ID = "BBBBB";

    private static final Boolean DEFAULT_IS_PARENT_COUPON_ORDER = false;
    private static final Boolean UPDATED_IS_PARENT_COUPON_ORDER = true;

    @Inject
    private YaCouponOrderRepository yaCouponOrderRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaCouponOrderMockMvc;

    private YaCouponOrder yaCouponOrder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaCouponOrderResource yaCouponOrderResource = new YaCouponOrderResource();
        ReflectionTestUtils.setField(yaCouponOrderResource, "yaCouponOrderRepository", yaCouponOrderRepository);
        this.restYaCouponOrderMockMvc = MockMvcBuilders.standaloneSetup(yaCouponOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaCouponOrder = new YaCouponOrder();
        yaCouponOrder.setCouponOrderId(DEFAULT_COUPON_ORDER_ID);
        yaCouponOrder.setCouponOrderDate(DEFAULT_COUPON_ORDER_DATE);
        yaCouponOrder.setCouponExpireDate(DEFAULT_COUPON_EXPIRE_DATE);
        yaCouponOrder.setCouponNeedPay(DEFAULT_COUPON_NEED_PAY);
        yaCouponOrder.setCouponOrderPrice(DEFAULT_COUPON_ORDER_PRICE);
        yaCouponOrder.setCouponOrderStatus(DEFAULT_COUPON_ORDER_STATUS);
        yaCouponOrder.setOrderStatusChangeTime(DEFAULT_ORDER_STATUS_CHANGE_TIME);
        yaCouponOrder.setParentcouponOrderId(DEFAULT_PARENTCOUPON_ORDER_ID);
        yaCouponOrder.setIsParentCouponOrder(DEFAULT_IS_PARENT_COUPON_ORDER);
    }

    @Test
    @Transactional
    public void createYaCouponOrder() throws Exception {
        int databaseSizeBeforeCreate = yaCouponOrderRepository.findAll().size();

        // Create the YaCouponOrder

        restYaCouponOrderMockMvc.perform(post("/api/yaCouponOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCouponOrder)))
                .andExpect(status().isCreated());

        // Validate the YaCouponOrder in the database
        List<YaCouponOrder> yaCouponOrders = yaCouponOrderRepository.findAll();
        assertThat(yaCouponOrders).hasSize(databaseSizeBeforeCreate + 1);
        YaCouponOrder testYaCouponOrder = yaCouponOrders.get(yaCouponOrders.size() - 1);
        assertThat(testYaCouponOrder.getCouponOrderId()).isEqualTo(DEFAULT_COUPON_ORDER_ID);
        assertThat(testYaCouponOrder.getCouponOrderDate()).isEqualTo(DEFAULT_COUPON_ORDER_DATE);
        assertThat(testYaCouponOrder.getCouponExpireDate()).isEqualTo(DEFAULT_COUPON_EXPIRE_DATE);
        assertThat(testYaCouponOrder.getCouponNeedPay()).isEqualTo(DEFAULT_COUPON_NEED_PAY);
        assertThat(testYaCouponOrder.getCouponOrderPrice()).isEqualTo(DEFAULT_COUPON_ORDER_PRICE);
        assertThat(testYaCouponOrder.getCouponOrderStatus()).isEqualTo(DEFAULT_COUPON_ORDER_STATUS);
        assertThat(testYaCouponOrder.getOrderStatusChangeTime()).isEqualTo(DEFAULT_ORDER_STATUS_CHANGE_TIME);
        assertThat(testYaCouponOrder.getParentcouponOrderId()).isEqualTo(DEFAULT_PARENTCOUPON_ORDER_ID);
        assertThat(testYaCouponOrder.getIsParentCouponOrder()).isEqualTo(DEFAULT_IS_PARENT_COUPON_ORDER);
    }

    @Test
    @Transactional
    public void checkCouponOrderIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaCouponOrderRepository.findAll().size();
        // set the field null
        yaCouponOrder.setCouponOrderId(null);

        // Create the YaCouponOrder, which fails.

        restYaCouponOrderMockMvc.perform(post("/api/yaCouponOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCouponOrder)))
                .andExpect(status().isBadRequest());

        List<YaCouponOrder> yaCouponOrders = yaCouponOrderRepository.findAll();
        assertThat(yaCouponOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCouponNeedPayIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaCouponOrderRepository.findAll().size();
        // set the field null
        yaCouponOrder.setCouponNeedPay(null);

        // Create the YaCouponOrder, which fails.

        restYaCouponOrderMockMvc.perform(post("/api/yaCouponOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCouponOrder)))
                .andExpect(status().isBadRequest());

        List<YaCouponOrder> yaCouponOrders = yaCouponOrderRepository.findAll();
        assertThat(yaCouponOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCouponOrderStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaCouponOrderRepository.findAll().size();
        // set the field null
        yaCouponOrder.setCouponOrderStatus(null);

        // Create the YaCouponOrder, which fails.

        restYaCouponOrderMockMvc.perform(post("/api/yaCouponOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCouponOrder)))
                .andExpect(status().isBadRequest());

        List<YaCouponOrder> yaCouponOrders = yaCouponOrderRepository.findAll();
        assertThat(yaCouponOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsParentCouponOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaCouponOrderRepository.findAll().size();
        // set the field null
        yaCouponOrder.setIsParentCouponOrder(null);

        // Create the YaCouponOrder, which fails.

        restYaCouponOrderMockMvc.perform(post("/api/yaCouponOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCouponOrder)))
                .andExpect(status().isBadRequest());

        List<YaCouponOrder> yaCouponOrders = yaCouponOrderRepository.findAll();
        assertThat(yaCouponOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYaCouponOrders() throws Exception {
        // Initialize the database
        yaCouponOrderRepository.saveAndFlush(yaCouponOrder);

        // Get all the yaCouponOrders
        restYaCouponOrderMockMvc.perform(get("/api/yaCouponOrders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaCouponOrder.getId().intValue())))
                .andExpect(jsonPath("$.[*].couponOrderId").value(hasItem(DEFAULT_COUPON_ORDER_ID.toString())))
                .andExpect(jsonPath("$.[*].couponOrderDate").value(hasItem(DEFAULT_COUPON_ORDER_DATE.toString())))
                .andExpect(jsonPath("$.[*].couponExpireDate").value(hasItem(DEFAULT_COUPON_EXPIRE_DATE.toString())))
                .andExpect(jsonPath("$.[*].couponNeedPay").value(hasItem(DEFAULT_COUPON_NEED_PAY.booleanValue())))
                .andExpect(jsonPath("$.[*].couponOrderPrice").value(hasItem(DEFAULT_COUPON_ORDER_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].couponOrderStatus").value(hasItem(DEFAULT_COUPON_ORDER_STATUS.toString())))
                .andExpect(jsonPath("$.[*].orderStatusChangeTime").value(hasItem(DEFAULT_ORDER_STATUS_CHANGE_TIME.intValue())))
                .andExpect(jsonPath("$.[*].parentcouponOrderId").value(hasItem(DEFAULT_PARENTCOUPON_ORDER_ID.toString())))
                .andExpect(jsonPath("$.[*].isParentCouponOrder").value(hasItem(DEFAULT_IS_PARENT_COUPON_ORDER.booleanValue())));
    }

    @Test
    @Transactional
    public void getYaCouponOrder() throws Exception {
        // Initialize the database
        yaCouponOrderRepository.saveAndFlush(yaCouponOrder);

        // Get the yaCouponOrder
        restYaCouponOrderMockMvc.perform(get("/api/yaCouponOrders/{id}", yaCouponOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaCouponOrder.getId().intValue()))
            .andExpect(jsonPath("$.couponOrderId").value(DEFAULT_COUPON_ORDER_ID.toString()))
            .andExpect(jsonPath("$.couponOrderDate").value(DEFAULT_COUPON_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.couponExpireDate").value(DEFAULT_COUPON_EXPIRE_DATE.toString()))
            .andExpect(jsonPath("$.couponNeedPay").value(DEFAULT_COUPON_NEED_PAY.booleanValue()))
            .andExpect(jsonPath("$.couponOrderPrice").value(DEFAULT_COUPON_ORDER_PRICE.doubleValue()))
            .andExpect(jsonPath("$.couponOrderStatus").value(DEFAULT_COUPON_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.orderStatusChangeTime").value(DEFAULT_ORDER_STATUS_CHANGE_TIME.intValue()))
            .andExpect(jsonPath("$.parentcouponOrderId").value(DEFAULT_PARENTCOUPON_ORDER_ID.toString()))
            .andExpect(jsonPath("$.isParentCouponOrder").value(DEFAULT_IS_PARENT_COUPON_ORDER.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingYaCouponOrder() throws Exception {
        // Get the yaCouponOrder
        restYaCouponOrderMockMvc.perform(get("/api/yaCouponOrders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaCouponOrder() throws Exception {
        // Initialize the database
        yaCouponOrderRepository.saveAndFlush(yaCouponOrder);

		int databaseSizeBeforeUpdate = yaCouponOrderRepository.findAll().size();

        // Update the yaCouponOrder
        yaCouponOrder.setCouponOrderId(UPDATED_COUPON_ORDER_ID);
        yaCouponOrder.setCouponOrderDate(UPDATED_COUPON_ORDER_DATE);
        yaCouponOrder.setCouponExpireDate(UPDATED_COUPON_EXPIRE_DATE);
        yaCouponOrder.setCouponNeedPay(UPDATED_COUPON_NEED_PAY);
        yaCouponOrder.setCouponOrderPrice(UPDATED_COUPON_ORDER_PRICE);
        yaCouponOrder.setCouponOrderStatus(UPDATED_COUPON_ORDER_STATUS);
        yaCouponOrder.setOrderStatusChangeTime(UPDATED_ORDER_STATUS_CHANGE_TIME);
        yaCouponOrder.setParentcouponOrderId(UPDATED_PARENTCOUPON_ORDER_ID);
        yaCouponOrder.setIsParentCouponOrder(UPDATED_IS_PARENT_COUPON_ORDER);

        restYaCouponOrderMockMvc.perform(put("/api/yaCouponOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaCouponOrder)))
                .andExpect(status().isOk());

        // Validate the YaCouponOrder in the database
        List<YaCouponOrder> yaCouponOrders = yaCouponOrderRepository.findAll();
        assertThat(yaCouponOrders).hasSize(databaseSizeBeforeUpdate);
        YaCouponOrder testYaCouponOrder = yaCouponOrders.get(yaCouponOrders.size() - 1);
        assertThat(testYaCouponOrder.getCouponOrderId()).isEqualTo(UPDATED_COUPON_ORDER_ID);
        assertThat(testYaCouponOrder.getCouponOrderDate()).isEqualTo(UPDATED_COUPON_ORDER_DATE);
        assertThat(testYaCouponOrder.getCouponExpireDate()).isEqualTo(UPDATED_COUPON_EXPIRE_DATE);
        assertThat(testYaCouponOrder.getCouponNeedPay()).isEqualTo(UPDATED_COUPON_NEED_PAY);
        assertThat(testYaCouponOrder.getCouponOrderPrice()).isEqualTo(UPDATED_COUPON_ORDER_PRICE);
        assertThat(testYaCouponOrder.getCouponOrderStatus()).isEqualTo(UPDATED_COUPON_ORDER_STATUS);
        assertThat(testYaCouponOrder.getOrderStatusChangeTime()).isEqualTo(UPDATED_ORDER_STATUS_CHANGE_TIME);
        assertThat(testYaCouponOrder.getParentcouponOrderId()).isEqualTo(UPDATED_PARENTCOUPON_ORDER_ID);
        assertThat(testYaCouponOrder.getIsParentCouponOrder()).isEqualTo(UPDATED_IS_PARENT_COUPON_ORDER);
    }

    @Test
    @Transactional
    public void deleteYaCouponOrder() throws Exception {
        // Initialize the database
        yaCouponOrderRepository.saveAndFlush(yaCouponOrder);

		int databaseSizeBeforeDelete = yaCouponOrderRepository.findAll().size();

        // Get the yaCouponOrder
        restYaCouponOrderMockMvc.perform(delete("/api/yaCouponOrders/{id}", yaCouponOrder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaCouponOrder> yaCouponOrders = yaCouponOrderRepository.findAll();
        assertThat(yaCouponOrders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
