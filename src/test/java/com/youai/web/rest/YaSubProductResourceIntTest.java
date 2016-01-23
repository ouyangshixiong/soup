package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaSubProduct;
import com.youai.repository.YaSubProductRepository;

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
 * Test class for the YaSubProductResource REST controller.
 *
 * @see YaSubProductResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaSubProductResourceIntTest {


    private static final Long DEFAULT_SERIAL_PRODUCT_ID = 1L;
    private static final Long UPDATED_SERIAL_PRODUCT_ID = 2L;
    private static final String DEFAULT_CAPACITY = "AAAAA";
    private static final String UPDATED_CAPACITY = "BBBBB";

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    @Inject
    private YaSubProductRepository yaSubProductRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaSubProductMockMvc;

    private YaSubProduct yaSubProduct;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaSubProductResource yaSubProductResource = new YaSubProductResource();
        ReflectionTestUtils.setField(yaSubProductResource, "yaSubProductRepository", yaSubProductRepository);
        this.restYaSubProductMockMvc = MockMvcBuilders.standaloneSetup(yaSubProductResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaSubProduct = new YaSubProduct();
        yaSubProduct.setSerialProductId(DEFAULT_SERIAL_PRODUCT_ID);
        yaSubProduct.setCapacity(DEFAULT_CAPACITY);
        yaSubProduct.setPrice(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createYaSubProduct() throws Exception {
        int databaseSizeBeforeCreate = yaSubProductRepository.findAll().size();

        // Create the YaSubProduct

        restYaSubProductMockMvc.perform(post("/api/yaSubProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaSubProduct)))
                .andExpect(status().isCreated());

        // Validate the YaSubProduct in the database
        List<YaSubProduct> yaSubProducts = yaSubProductRepository.findAll();
        assertThat(yaSubProducts).hasSize(databaseSizeBeforeCreate + 1);
        YaSubProduct testYaSubProduct = yaSubProducts.get(yaSubProducts.size() - 1);
        assertThat(testYaSubProduct.getSerialProductId()).isEqualTo(DEFAULT_SERIAL_PRODUCT_ID);
        assertThat(testYaSubProduct.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testYaSubProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void checkSerialProductIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaSubProductRepository.findAll().size();
        // set the field null
        yaSubProduct.setSerialProductId(null);

        // Create the YaSubProduct, which fails.

        restYaSubProductMockMvc.perform(post("/api/yaSubProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaSubProduct)))
                .andExpect(status().isBadRequest());

        List<YaSubProduct> yaSubProducts = yaSubProductRepository.findAll();
        assertThat(yaSubProducts).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYaSubProducts() throws Exception {
        // Initialize the database
        yaSubProductRepository.saveAndFlush(yaSubProduct);

        // Get all the yaSubProducts
        restYaSubProductMockMvc.perform(get("/api/yaSubProducts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaSubProduct.getId().intValue())))
                .andExpect(jsonPath("$.[*].serialProductId").value(hasItem(DEFAULT_SERIAL_PRODUCT_ID.intValue())))
                .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY.toString())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getYaSubProduct() throws Exception {
        // Initialize the database
        yaSubProductRepository.saveAndFlush(yaSubProduct);

        // Get the yaSubProduct
        restYaSubProductMockMvc.perform(get("/api/yaSubProducts/{id}", yaSubProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaSubProduct.getId().intValue()))
            .andExpect(jsonPath("$.serialProductId").value(DEFAULT_SERIAL_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingYaSubProduct() throws Exception {
        // Get the yaSubProduct
        restYaSubProductMockMvc.perform(get("/api/yaSubProducts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaSubProduct() throws Exception {
        // Initialize the database
        yaSubProductRepository.saveAndFlush(yaSubProduct);

		int databaseSizeBeforeUpdate = yaSubProductRepository.findAll().size();

        // Update the yaSubProduct
        yaSubProduct.setSerialProductId(UPDATED_SERIAL_PRODUCT_ID);
        yaSubProduct.setCapacity(UPDATED_CAPACITY);
        yaSubProduct.setPrice(UPDATED_PRICE);

        restYaSubProductMockMvc.perform(put("/api/yaSubProducts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaSubProduct)))
                .andExpect(status().isOk());

        // Validate the YaSubProduct in the database
        List<YaSubProduct> yaSubProducts = yaSubProductRepository.findAll();
        assertThat(yaSubProducts).hasSize(databaseSizeBeforeUpdate);
        YaSubProduct testYaSubProduct = yaSubProducts.get(yaSubProducts.size() - 1);
        assertThat(testYaSubProduct.getSerialProductId()).isEqualTo(UPDATED_SERIAL_PRODUCT_ID);
        assertThat(testYaSubProduct.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testYaSubProduct.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void deleteYaSubProduct() throws Exception {
        // Initialize the database
        yaSubProductRepository.saveAndFlush(yaSubProduct);

		int databaseSizeBeforeDelete = yaSubProductRepository.findAll().size();

        // Get the yaSubProduct
        restYaSubProductMockMvc.perform(delete("/api/yaSubProducts/{id}", yaSubProduct.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaSubProduct> yaSubProducts = yaSubProductRepository.findAll();
        assertThat(yaSubProducts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
