package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaProduct;
import com.youai.repository.YaProductRepository;

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
 * Test class for the YaProductResource REST controller.
 *
 * @see YaProductResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaProductResourceIntTest {

    private static final String DEFAULT_PRODUCT_NAME = "AAAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBBB";
    private static final String DEFAULT_PRODUCT_PICTURES = "AAAAA";
    private static final String UPDATED_PRODUCT_PICTURES = "BBBBB";

    private static final Float DEFAULT_ORGINAL_PRICE = 1F;
    private static final Float UPDATED_ORGINAL_PRICE = 2F;

    private static final Float DEFAULT_DISCOUNT_PRICE = 1F;
    private static final Float UPDATED_DISCOUNT_PRICE = 2F;

    private static final Float DEFAULT_MEMBERSHIP_PRICE = 1F;
    private static final Float UPDATED_MEMBERSHIP_PRICE = 2F;

    private static final Boolean DEFAULT_IS_SERIAL = false;
    private static final Boolean UPDATED_IS_SERIAL = true;
    private static final String DEFAULT_KEYWORDS = "AAAAA";
    private static final String UPDATED_KEYWORDS = "BBBBB";
    private static final String DEFAULT_PRODUCT_DISCRIPTION = "AAAAA";
    private static final String UPDATED_PRODUCT_DISCRIPTION = "BBBBB";

    private static final Integer DEFAULT_PRODUCT_AMOUNT = 1;
    private static final Integer UPDATED_PRODUCT_AMOUNT = 2;

    private static final Integer DEFAULT_PRODUCT_INVENTORY = 1;
    private static final Integer UPDATED_PRODUCT_INVENTORY = 2;

    private static final LocalDate DEFAULT_ON_SHELVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ON_SHELVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_OFF_SHELVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OFF_SHELVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_PRODUCT_STATUS = false;
    private static final Boolean UPDATED_PRODUCT_STATUS = true;

    @Inject
    private YaProductRepository yaProductRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaProductMockMvc;

    private YaProduct yaProduct;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaProductResource yaProductResource = new YaProductResource();
        ReflectionTestUtils.setField(yaProductResource, "yaProductRepository", yaProductRepository);
        this.restYaProductMockMvc = MockMvcBuilders.standaloneSetup(yaProductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaProduct = new YaProduct();
        yaProduct.setProductName(DEFAULT_PRODUCT_NAME);
        yaProduct.setProductPictures(DEFAULT_PRODUCT_PICTURES);
        yaProduct.setOrginalPrice(DEFAULT_ORGINAL_PRICE);
        yaProduct.setDiscountPrice(DEFAULT_DISCOUNT_PRICE);
        yaProduct.setMembershipPrice(DEFAULT_MEMBERSHIP_PRICE);
        yaProduct.setIsSerial(DEFAULT_IS_SERIAL);
        yaProduct.setKeywords(DEFAULT_KEYWORDS);
        yaProduct.setProductDiscription(DEFAULT_PRODUCT_DISCRIPTION);
        yaProduct.setProductAmount(DEFAULT_PRODUCT_AMOUNT);
        yaProduct.setProductInventory(DEFAULT_PRODUCT_INVENTORY);
        yaProduct.setOnShelveDate(DEFAULT_ON_SHELVE_DATE);
        yaProduct.setOffShelveDate(DEFAULT_OFF_SHELVE_DATE);
        yaProduct.setProductStatus(DEFAULT_PRODUCT_STATUS);
    }

    @Test
    @Transactional
    public void createYaProduct() throws Exception {
        int databaseSizeBeforeCreate = yaProductRepository.findAll().size();

        // Create the YaProduct

        restYaProductMockMvc.perform(post("/api/yaProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaProduct)))
                .andExpect(status().isCreated());

        // Validate the YaProduct in the database
        List<YaProduct> yaProducts = yaProductRepository.findAll();
        assertThat(yaProducts).hasSize(databaseSizeBeforeCreate + 1);
        YaProduct testYaProduct = yaProducts.get(yaProducts.size() - 1);
        assertThat(testYaProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testYaProduct.getProductPictures()).isEqualTo(DEFAULT_PRODUCT_PICTURES);
        assertThat(testYaProduct.getOrginalPrice()).isEqualTo(DEFAULT_ORGINAL_PRICE);
        assertThat(testYaProduct.getDiscountPrice()).isEqualTo(DEFAULT_DISCOUNT_PRICE);
        assertThat(testYaProduct.getMembershipPrice()).isEqualTo(DEFAULT_MEMBERSHIP_PRICE);
        assertThat(testYaProduct.getIsSerial()).isEqualTo(DEFAULT_IS_SERIAL);
        assertThat(testYaProduct.getKeywords()).isEqualTo(DEFAULT_KEYWORDS);
        assertThat(testYaProduct.getProductDiscription()).isEqualTo(DEFAULT_PRODUCT_DISCRIPTION);
        assertThat(testYaProduct.getProductAmount()).isEqualTo(DEFAULT_PRODUCT_AMOUNT);
        assertThat(testYaProduct.getProductInventory()).isEqualTo(DEFAULT_PRODUCT_INVENTORY);
        assertThat(testYaProduct.getOnShelveDate()).isEqualTo(DEFAULT_ON_SHELVE_DATE);
        assertThat(testYaProduct.getOffShelveDate()).isEqualTo(DEFAULT_OFF_SHELVE_DATE);
        assertThat(testYaProduct.getProductStatus()).isEqualTo(DEFAULT_PRODUCT_STATUS);
    }

    @Test
    @Transactional
    public void checkProductNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaProductRepository.findAll().size();
        // set the field null
        yaProduct.setProductName(null);

        // Create the YaProduct, which fails.

        restYaProductMockMvc.perform(post("/api/yaProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaProduct)))
                .andExpect(status().isBadRequest());

        List<YaProduct> yaProducts = yaProductRepository.findAll();
        assertThat(yaProducts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductPicturesIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaProductRepository.findAll().size();
        // set the field null
        yaProduct.setProductPictures(null);

        // Create the YaProduct, which fails.

        restYaProductMockMvc.perform(post("/api/yaProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaProduct)))
                .andExpect(status().isBadRequest());

        List<YaProduct> yaProducts = yaProductRepository.findAll();
        assertThat(yaProducts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrginalPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaProductRepository.findAll().size();
        // set the field null
        yaProduct.setOrginalPrice(null);

        // Create the YaProduct, which fails.

        restYaProductMockMvc.perform(post("/api/yaProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaProduct)))
                .andExpect(status().isBadRequest());

        List<YaProduct> yaProducts = yaProductRepository.findAll();
        assertThat(yaProducts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaProductRepository.findAll().size();
        // set the field null
        yaProduct.setProductAmount(null);

        // Create the YaProduct, which fails.

        restYaProductMockMvc.perform(post("/api/yaProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaProduct)))
                .andExpect(status().isBadRequest());

        List<YaProduct> yaProducts = yaProductRepository.findAll();
        assertThat(yaProducts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProductInventoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaProductRepository.findAll().size();
        // set the field null
        yaProduct.setProductInventory(null);

        // Create the YaProduct, which fails.

        restYaProductMockMvc.perform(post("/api/yaProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaProduct)))
                .andExpect(status().isBadRequest());

        List<YaProduct> yaProducts = yaProductRepository.findAll();
        assertThat(yaProducts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOnShelveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaProductRepository.findAll().size();
        // set the field null
        yaProduct.setOnShelveDate(null);

        // Create the YaProduct, which fails.

        restYaProductMockMvc.perform(post("/api/yaProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaProduct)))
                .andExpect(status().isBadRequest());

        List<YaProduct> yaProducts = yaProductRepository.findAll();
        assertThat(yaProducts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOffShelveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaProductRepository.findAll().size();
        // set the field null
        yaProduct.setOffShelveDate(null);

        // Create the YaProduct, which fails.

        restYaProductMockMvc.perform(post("/api/yaProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaProduct)))
                .andExpect(status().isBadRequest());

        List<YaProduct> yaProducts = yaProductRepository.findAll();
        assertThat(yaProducts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYaProducts() throws Exception {
        // Initialize the database
        yaProductRepository.saveAndFlush(yaProduct);

        // Get all the yaProducts
        restYaProductMockMvc.perform(get("/api/yaProducts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaProduct.getId().intValue())))
                .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
                .andExpect(jsonPath("$.[*].productPictures").value(hasItem(DEFAULT_PRODUCT_PICTURES.toString())))
                .andExpect(jsonPath("$.[*].orginalPrice").value(hasItem(DEFAULT_ORGINAL_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].discountPrice").value(hasItem(DEFAULT_DISCOUNT_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].membershipPrice").value(hasItem(DEFAULT_MEMBERSHIP_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].isSerial").value(hasItem(DEFAULT_IS_SERIAL.booleanValue())))
                .andExpect(jsonPath("$.[*].keywords").value(hasItem(DEFAULT_KEYWORDS.toString())))
                .andExpect(jsonPath("$.[*].productDiscription").value(hasItem(DEFAULT_PRODUCT_DISCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].productAmount").value(hasItem(DEFAULT_PRODUCT_AMOUNT)))
                .andExpect(jsonPath("$.[*].productInventory").value(hasItem(DEFAULT_PRODUCT_INVENTORY)))
                .andExpect(jsonPath("$.[*].onShelveDate").value(hasItem(DEFAULT_ON_SHELVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].offShelveDate").value(hasItem(DEFAULT_OFF_SHELVE_DATE.toString())))
                .andExpect(jsonPath("$.[*].productStatus").value(hasItem(DEFAULT_PRODUCT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    public void getYaProduct() throws Exception {
        // Initialize the database
        yaProductRepository.saveAndFlush(yaProduct);

        // Get the yaProduct
        restYaProductMockMvc.perform(get("/api/yaProducts/{id}", yaProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaProduct.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.productPictures").value(DEFAULT_PRODUCT_PICTURES.toString()))
            .andExpect(jsonPath("$.orginalPrice").value(DEFAULT_ORGINAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.discountPrice").value(DEFAULT_DISCOUNT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.membershipPrice").value(DEFAULT_MEMBERSHIP_PRICE.doubleValue()))
            .andExpect(jsonPath("$.isSerial").value(DEFAULT_IS_SERIAL.booleanValue()))
            .andExpect(jsonPath("$.keywords").value(DEFAULT_KEYWORDS.toString()))
            .andExpect(jsonPath("$.productDiscription").value(DEFAULT_PRODUCT_DISCRIPTION.toString()))
            .andExpect(jsonPath("$.productAmount").value(DEFAULT_PRODUCT_AMOUNT))
            .andExpect(jsonPath("$.productInventory").value(DEFAULT_PRODUCT_INVENTORY))
            .andExpect(jsonPath("$.onShelveDate").value(DEFAULT_ON_SHELVE_DATE.toString()))
            .andExpect(jsonPath("$.offShelveDate").value(DEFAULT_OFF_SHELVE_DATE.toString()))
            .andExpect(jsonPath("$.productStatus").value(DEFAULT_PRODUCT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingYaProduct() throws Exception {
        // Get the yaProduct
        restYaProductMockMvc.perform(get("/api/yaProducts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaProduct() throws Exception {
        // Initialize the database
        yaProductRepository.saveAndFlush(yaProduct);

		int databaseSizeBeforeUpdate = yaProductRepository.findAll().size();

        // Update the yaProduct
        yaProduct.setProductName(UPDATED_PRODUCT_NAME);
        yaProduct.setProductPictures(UPDATED_PRODUCT_PICTURES);
        yaProduct.setOrginalPrice(UPDATED_ORGINAL_PRICE);
        yaProduct.setDiscountPrice(UPDATED_DISCOUNT_PRICE);
        yaProduct.setMembershipPrice(UPDATED_MEMBERSHIP_PRICE);
        yaProduct.setIsSerial(UPDATED_IS_SERIAL);
        yaProduct.setKeywords(UPDATED_KEYWORDS);
        yaProduct.setProductDiscription(UPDATED_PRODUCT_DISCRIPTION);
        yaProduct.setProductAmount(UPDATED_PRODUCT_AMOUNT);
        yaProduct.setProductInventory(UPDATED_PRODUCT_INVENTORY);
        yaProduct.setOnShelveDate(UPDATED_ON_SHELVE_DATE);
        yaProduct.setOffShelveDate(UPDATED_OFF_SHELVE_DATE);
        yaProduct.setProductStatus(UPDATED_PRODUCT_STATUS);

        restYaProductMockMvc.perform(put("/api/yaProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaProduct)))
                .andExpect(status().isOk());

        // Validate the YaProduct in the database
        List<YaProduct> yaProducts = yaProductRepository.findAll();
        assertThat(yaProducts).hasSize(databaseSizeBeforeUpdate);
        YaProduct testYaProduct = yaProducts.get(yaProducts.size() - 1);
        assertThat(testYaProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testYaProduct.getProductPictures()).isEqualTo(UPDATED_PRODUCT_PICTURES);
        assertThat(testYaProduct.getOrginalPrice()).isEqualTo(UPDATED_ORGINAL_PRICE);
        assertThat(testYaProduct.getDiscountPrice()).isEqualTo(UPDATED_DISCOUNT_PRICE);
        assertThat(testYaProduct.getMembershipPrice()).isEqualTo(UPDATED_MEMBERSHIP_PRICE);
        assertThat(testYaProduct.getIsSerial()).isEqualTo(UPDATED_IS_SERIAL);
        assertThat(testYaProduct.getKeywords()).isEqualTo(UPDATED_KEYWORDS);
        assertThat(testYaProduct.getProductDiscription()).isEqualTo(UPDATED_PRODUCT_DISCRIPTION);
        assertThat(testYaProduct.getProductAmount()).isEqualTo(UPDATED_PRODUCT_AMOUNT);
        assertThat(testYaProduct.getProductInventory()).isEqualTo(UPDATED_PRODUCT_INVENTORY);
        assertThat(testYaProduct.getOnShelveDate()).isEqualTo(UPDATED_ON_SHELVE_DATE);
        assertThat(testYaProduct.getOffShelveDate()).isEqualTo(UPDATED_OFF_SHELVE_DATE);
        assertThat(testYaProduct.getProductStatus()).isEqualTo(UPDATED_PRODUCT_STATUS);
    }

    @Test
    @Transactional
    public void deleteYaProduct() throws Exception {
        // Initialize the database
        yaProductRepository.saveAndFlush(yaProduct);

		int databaseSizeBeforeDelete = yaProductRepository.findAll().size();

        // Get the yaProduct
        restYaProductMockMvc.perform(delete("/api/yaProducts/{id}", yaProduct.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaProduct> yaProducts = yaProductRepository.findAll();
        assertThat(yaProducts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
