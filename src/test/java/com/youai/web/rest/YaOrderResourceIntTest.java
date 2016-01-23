package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaOrder;
import com.youai.repository.YaOrderRepository;

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

import com.youai.domain.enumeration.OrderStatus;
import com.youai.domain.enumeration.PotReturnMethod;

/**
 * Test class for the YaOrderResource REST controller.
 *
 * @see YaOrderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaOrderResourceIntTest {

    private static final String DEFAULT_ORDER_ID = "AAAAA";
    private static final String UPDATED_ORDER_ID = "BBBBB";

    private static final Boolean DEFAULT_USE_COUPON = false;
    private static final Boolean UPDATED_USE_COUPON = true;

    private static final LocalDate DEFAULT_ORDER_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDER_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PRODUCT_AMOUNT = 1;
    private static final Integer UPDATED_PRODUCT_AMOUNT = 2;

    private static final Float DEFAULT_ORDER_TOTAL_PRICE = 1F;
    private static final Float UPDATED_ORDER_TOTAL_PRICE = 2F;
    
    private static final OrderStatus DEFAULT_ORDER_STATUS = OrderStatus.ACTIVE;
    private static final OrderStatus UPDATED_ORDER_STATUS = OrderStatus.DISCARD;

    private static final Long DEFAULT_ORDER_STATUS_CHANGE_TIME = 1L;
    private static final Long UPDATED_ORDER_STATUS_CHANGE_TIME = 2L;

    private static final LocalDate DEFAULT_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DELIVERY_PERIOD = "AAAAA";
    private static final String UPDATED_DELIVERY_PERIOD = "BBBBB";
    private static final String DEFAULT_DELIVERY_MAN_NAME = "AA";
    private static final String UPDATED_DELIVERY_MAN_NAME = "BB";
    private static final String DEFAULT_DELIVERY_PHONE = "AAAAA";
    private static final String UPDATED_DELIVERY_PHONE = "BBBBB";
    
    private static final PotReturnMethod DEFAULT_POT_RETURN_METHOD = PotReturnMethod.SELF;
    private static final PotReturnMethod UPDATED_POT_RETURN_METHOD = PotReturnMethod.DELIVERYMAN;

    private static final Boolean DEFAULT_HAS_PRIVATE_POT = false;
    private static final Boolean UPDATED_HAS_PRIVATE_POT = true;

    private static final Boolean DEFAULT_USE_PRIVATE_POT = false;
    private static final Boolean UPDATED_USE_PRIVATE_POT = true;
    private static final String DEFAULT_PARENT_ORDER_ID = "AAAAA";
    private static final String UPDATED_PARENT_ORDER_ID = "BBBBB";

    private static final Boolean DEFAULT_IS_PARENT_ORDER = false;
    private static final Boolean UPDATED_IS_PARENT_ORDER = true;

    private static final Float DEFAULT_ORDER_REAL_PRICE = 1F;
    private static final Float UPDATED_ORDER_REAL_PRICE = 2F;

    @Inject
    private YaOrderRepository yaOrderRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaOrderMockMvc;

    private YaOrder yaOrder;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaOrderResource yaOrderResource = new YaOrderResource();
        ReflectionTestUtils.setField(yaOrderResource, "yaOrderRepository", yaOrderRepository);
        this.restYaOrderMockMvc = MockMvcBuilders.standaloneSetup(yaOrderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaOrder = new YaOrder();
        yaOrder.setOrderId(DEFAULT_ORDER_ID);
        yaOrder.setUseCoupon(DEFAULT_USE_COUPON);
        yaOrder.setOrderDate(DEFAULT_ORDER_DATE);
        yaOrder.setProductAmount(DEFAULT_PRODUCT_AMOUNT);
        yaOrder.setOrderTotalPrice(DEFAULT_ORDER_TOTAL_PRICE);
        yaOrder.setOrderStatus(DEFAULT_ORDER_STATUS);
        yaOrder.setOrderStatusChangeTime(DEFAULT_ORDER_STATUS_CHANGE_TIME);
        yaOrder.setDeliveryDate(DEFAULT_DELIVERY_DATE);
        yaOrder.setDeliveryPeriod(DEFAULT_DELIVERY_PERIOD);
        yaOrder.setDeliveryManName(DEFAULT_DELIVERY_MAN_NAME);
        yaOrder.setDeliveryPhone(DEFAULT_DELIVERY_PHONE);
        yaOrder.setPotReturnMethod(DEFAULT_POT_RETURN_METHOD);
        yaOrder.setHasPrivatePot(DEFAULT_HAS_PRIVATE_POT);
        yaOrder.setUsePrivatePot(DEFAULT_USE_PRIVATE_POT);
        yaOrder.setParentOrderId(DEFAULT_PARENT_ORDER_ID);
        yaOrder.setIsParentOrder(DEFAULT_IS_PARENT_ORDER);
        yaOrder.setOrderRealPrice(DEFAULT_ORDER_REAL_PRICE);
    }

    @Test
    @Transactional
    public void createYaOrder() throws Exception {
        int databaseSizeBeforeCreate = yaOrderRepository.findAll().size();

        // Create the YaOrder

        restYaOrderMockMvc.perform(post("/api/yaOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaOrder)))
                .andExpect(status().isCreated());

        // Validate the YaOrder in the database
        List<YaOrder> yaOrders = yaOrderRepository.findAll();
        assertThat(yaOrders).hasSize(databaseSizeBeforeCreate + 1);
        YaOrder testYaOrder = yaOrders.get(yaOrders.size() - 1);
        assertThat(testYaOrder.getOrderId()).isEqualTo(DEFAULT_ORDER_ID);
        assertThat(testYaOrder.getUseCoupon()).isEqualTo(DEFAULT_USE_COUPON);
        assertThat(testYaOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testYaOrder.getProductAmount()).isEqualTo(DEFAULT_PRODUCT_AMOUNT);
        assertThat(testYaOrder.getOrderTotalPrice()).isEqualTo(DEFAULT_ORDER_TOTAL_PRICE);
        assertThat(testYaOrder.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testYaOrder.getOrderStatusChangeTime()).isEqualTo(DEFAULT_ORDER_STATUS_CHANGE_TIME);
        assertThat(testYaOrder.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testYaOrder.getDeliveryPeriod()).isEqualTo(DEFAULT_DELIVERY_PERIOD);
        assertThat(testYaOrder.getDeliveryManName()).isEqualTo(DEFAULT_DELIVERY_MAN_NAME);
        assertThat(testYaOrder.getDeliveryPhone()).isEqualTo(DEFAULT_DELIVERY_PHONE);
        assertThat(testYaOrder.getPotReturnMethod()).isEqualTo(DEFAULT_POT_RETURN_METHOD);
        assertThat(testYaOrder.getHasPrivatePot()).isEqualTo(DEFAULT_HAS_PRIVATE_POT);
        assertThat(testYaOrder.getUsePrivatePot()).isEqualTo(DEFAULT_USE_PRIVATE_POT);
        assertThat(testYaOrder.getParentOrderId()).isEqualTo(DEFAULT_PARENT_ORDER_ID);
        assertThat(testYaOrder.getIsParentOrder()).isEqualTo(DEFAULT_IS_PARENT_ORDER);
        assertThat(testYaOrder.getOrderRealPrice()).isEqualTo(DEFAULT_ORDER_REAL_PRICE);
    }

    @Test
    @Transactional
    public void checkOrderIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaOrderRepository.findAll().size();
        // set the field null
        yaOrder.setOrderId(null);

        // Create the YaOrder, which fails.

        restYaOrderMockMvc.perform(post("/api/yaOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaOrder)))
                .andExpect(status().isBadRequest());

        List<YaOrder> yaOrders = yaOrderRepository.findAll();
        assertThat(yaOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderTotalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaOrderRepository.findAll().size();
        // set the field null
        yaOrder.setOrderTotalPrice(null);

        // Create the YaOrder, which fails.

        restYaOrderMockMvc.perform(post("/api/yaOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaOrder)))
                .andExpect(status().isBadRequest());

        List<YaOrder> yaOrders = yaOrderRepository.findAll();
        assertThat(yaOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaOrderRepository.findAll().size();
        // set the field null
        yaOrder.setOrderStatus(null);

        // Create the YaOrder, which fails.

        restYaOrderMockMvc.perform(post("/api/yaOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaOrder)))
                .andExpect(status().isBadRequest());

        List<YaOrder> yaOrders = yaOrderRepository.findAll();
        assertThat(yaOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsParentOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaOrderRepository.findAll().size();
        // set the field null
        yaOrder.setIsParentOrder(null);

        // Create the YaOrder, which fails.

        restYaOrderMockMvc.perform(post("/api/yaOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaOrder)))
                .andExpect(status().isBadRequest());

        List<YaOrder> yaOrders = yaOrderRepository.findAll();
        assertThat(yaOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderRealPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaOrderRepository.findAll().size();
        // set the field null
        yaOrder.setOrderRealPrice(null);

        // Create the YaOrder, which fails.

        restYaOrderMockMvc.perform(post("/api/yaOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaOrder)))
                .andExpect(status().isBadRequest());

        List<YaOrder> yaOrders = yaOrderRepository.findAll();
        assertThat(yaOrders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYaOrders() throws Exception {
        // Initialize the database
        yaOrderRepository.saveAndFlush(yaOrder);

        // Get all the yaOrders
        restYaOrderMockMvc.perform(get("/api/yaOrders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaOrder.getId().intValue())))
                .andExpect(jsonPath("$.[*].orderId").value(hasItem(DEFAULT_ORDER_ID.toString())))
                .andExpect(jsonPath("$.[*].useCoupon").value(hasItem(DEFAULT_USE_COUPON.booleanValue())))
                .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
                .andExpect(jsonPath("$.[*].productAmount").value(hasItem(DEFAULT_PRODUCT_AMOUNT)))
                .andExpect(jsonPath("$.[*].orderTotalPrice").value(hasItem(DEFAULT_ORDER_TOTAL_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
                .andExpect(jsonPath("$.[*].orderStatusChangeTime").value(hasItem(DEFAULT_ORDER_STATUS_CHANGE_TIME.intValue())))
                .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
                .andExpect(jsonPath("$.[*].deliveryPeriod").value(hasItem(DEFAULT_DELIVERY_PERIOD.toString())))
                .andExpect(jsonPath("$.[*].deliveryManName").value(hasItem(DEFAULT_DELIVERY_MAN_NAME.toString())))
                .andExpect(jsonPath("$.[*].deliveryPhone").value(hasItem(DEFAULT_DELIVERY_PHONE.toString())))
                .andExpect(jsonPath("$.[*].potReturnMethod").value(hasItem(DEFAULT_POT_RETURN_METHOD.toString())))
                .andExpect(jsonPath("$.[*].hasPrivatePot").value(hasItem(DEFAULT_HAS_PRIVATE_POT.booleanValue())))
                .andExpect(jsonPath("$.[*].usePrivatePot").value(hasItem(DEFAULT_USE_PRIVATE_POT.booleanValue())))
                .andExpect(jsonPath("$.[*].parentOrderId").value(hasItem(DEFAULT_PARENT_ORDER_ID.toString())))
                .andExpect(jsonPath("$.[*].isParentOrder").value(hasItem(DEFAULT_IS_PARENT_ORDER.booleanValue())))
                .andExpect(jsonPath("$.[*].orderRealPrice").value(hasItem(DEFAULT_ORDER_REAL_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getYaOrder() throws Exception {
        // Initialize the database
        yaOrderRepository.saveAndFlush(yaOrder);

        // Get the yaOrder
        restYaOrderMockMvc.perform(get("/api/yaOrders/{id}", yaOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderId").value(DEFAULT_ORDER_ID.toString()))
            .andExpect(jsonPath("$.useCoupon").value(DEFAULT_USE_COUPON.booleanValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.productAmount").value(DEFAULT_PRODUCT_AMOUNT))
            .andExpect(jsonPath("$.orderTotalPrice").value(DEFAULT_ORDER_TOTAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.orderStatusChangeTime").value(DEFAULT_ORDER_STATUS_CHANGE_TIME.intValue()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.deliveryPeriod").value(DEFAULT_DELIVERY_PERIOD.toString()))
            .andExpect(jsonPath("$.deliveryManName").value(DEFAULT_DELIVERY_MAN_NAME.toString()))
            .andExpect(jsonPath("$.deliveryPhone").value(DEFAULT_DELIVERY_PHONE.toString()))
            .andExpect(jsonPath("$.potReturnMethod").value(DEFAULT_POT_RETURN_METHOD.toString()))
            .andExpect(jsonPath("$.hasPrivatePot").value(DEFAULT_HAS_PRIVATE_POT.booleanValue()))
            .andExpect(jsonPath("$.usePrivatePot").value(DEFAULT_USE_PRIVATE_POT.booleanValue()))
            .andExpect(jsonPath("$.parentOrderId").value(DEFAULT_PARENT_ORDER_ID.toString()))
            .andExpect(jsonPath("$.isParentOrder").value(DEFAULT_IS_PARENT_ORDER.booleanValue()))
            .andExpect(jsonPath("$.orderRealPrice").value(DEFAULT_ORDER_REAL_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingYaOrder() throws Exception {
        // Get the yaOrder
        restYaOrderMockMvc.perform(get("/api/yaOrders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaOrder() throws Exception {
        // Initialize the database
        yaOrderRepository.saveAndFlush(yaOrder);

		int databaseSizeBeforeUpdate = yaOrderRepository.findAll().size();

        // Update the yaOrder
        yaOrder.setOrderId(UPDATED_ORDER_ID);
        yaOrder.setUseCoupon(UPDATED_USE_COUPON);
        yaOrder.setOrderDate(UPDATED_ORDER_DATE);
        yaOrder.setProductAmount(UPDATED_PRODUCT_AMOUNT);
        yaOrder.setOrderTotalPrice(UPDATED_ORDER_TOTAL_PRICE);
        yaOrder.setOrderStatus(UPDATED_ORDER_STATUS);
        yaOrder.setOrderStatusChangeTime(UPDATED_ORDER_STATUS_CHANGE_TIME);
        yaOrder.setDeliveryDate(UPDATED_DELIVERY_DATE);
        yaOrder.setDeliveryPeriod(UPDATED_DELIVERY_PERIOD);
        yaOrder.setDeliveryManName(UPDATED_DELIVERY_MAN_NAME);
        yaOrder.setDeliveryPhone(UPDATED_DELIVERY_PHONE);
        yaOrder.setPotReturnMethod(UPDATED_POT_RETURN_METHOD);
        yaOrder.setHasPrivatePot(UPDATED_HAS_PRIVATE_POT);
        yaOrder.setUsePrivatePot(UPDATED_USE_PRIVATE_POT);
        yaOrder.setParentOrderId(UPDATED_PARENT_ORDER_ID);
        yaOrder.setIsParentOrder(UPDATED_IS_PARENT_ORDER);
        yaOrder.setOrderRealPrice(UPDATED_ORDER_REAL_PRICE);

        restYaOrderMockMvc.perform(put("/api/yaOrders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaOrder)))
                .andExpect(status().isOk());

        // Validate the YaOrder in the database
        List<YaOrder> yaOrders = yaOrderRepository.findAll();
        assertThat(yaOrders).hasSize(databaseSizeBeforeUpdate);
        YaOrder testYaOrder = yaOrders.get(yaOrders.size() - 1);
        assertThat(testYaOrder.getOrderId()).isEqualTo(UPDATED_ORDER_ID);
        assertThat(testYaOrder.getUseCoupon()).isEqualTo(UPDATED_USE_COUPON);
        assertThat(testYaOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testYaOrder.getProductAmount()).isEqualTo(UPDATED_PRODUCT_AMOUNT);
        assertThat(testYaOrder.getOrderTotalPrice()).isEqualTo(UPDATED_ORDER_TOTAL_PRICE);
        assertThat(testYaOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testYaOrder.getOrderStatusChangeTime()).isEqualTo(UPDATED_ORDER_STATUS_CHANGE_TIME);
        assertThat(testYaOrder.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testYaOrder.getDeliveryPeriod()).isEqualTo(UPDATED_DELIVERY_PERIOD);
        assertThat(testYaOrder.getDeliveryManName()).isEqualTo(UPDATED_DELIVERY_MAN_NAME);
        assertThat(testYaOrder.getDeliveryPhone()).isEqualTo(UPDATED_DELIVERY_PHONE);
        assertThat(testYaOrder.getPotReturnMethod()).isEqualTo(UPDATED_POT_RETURN_METHOD);
        assertThat(testYaOrder.getHasPrivatePot()).isEqualTo(UPDATED_HAS_PRIVATE_POT);
        assertThat(testYaOrder.getUsePrivatePot()).isEqualTo(UPDATED_USE_PRIVATE_POT);
        assertThat(testYaOrder.getParentOrderId()).isEqualTo(UPDATED_PARENT_ORDER_ID);
        assertThat(testYaOrder.getIsParentOrder()).isEqualTo(UPDATED_IS_PARENT_ORDER);
        assertThat(testYaOrder.getOrderRealPrice()).isEqualTo(UPDATED_ORDER_REAL_PRICE);
    }

    @Test
    @Transactional
    public void deleteYaOrder() throws Exception {
        // Initialize the database
        yaOrderRepository.saveAndFlush(yaOrder);

		int databaseSizeBeforeDelete = yaOrderRepository.findAll().size();

        // Get the yaOrder
        restYaOrderMockMvc.perform(delete("/api/yaOrders/{id}", yaOrder.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaOrder> yaOrders = yaOrderRepository.findAll();
        assertThat(yaOrders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
