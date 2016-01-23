package com.youai.web.rest;

import com.youai.Application;
import com.youai.domain.YaUser;
import com.youai.repository.YaUserRepository;

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

import com.youai.domain.enumeration.Gender;
import com.youai.domain.enumeration.LoginStatus;

/**
 * Test class for the YaUserResource REST controller.
 *
 * @see YaUserResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class YaUserResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAA";
    private static final String UPDATED_NAME = "BBBBBB";
    private static final String DEFAULT_MOBILE_NUMBER = "AAAAA";
    private static final String UPDATED_MOBILE_NUMBER = "BBBBB";
    private static final String DEFAULT_FACEBOOK = "AAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_PASSWORD = "AAAAA";
    private static final String UPDATED_PASSWORD = "BBBBB";
    
    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;
    
    private static final LoginStatus DEFAULT_LOGIN_STATUS = LoginStatus.LOGINED;
    private static final LoginStatus UPDATED_LOGIN_STATUS = LoginStatus.NOTLOGIN;

    private static final Boolean DEFAULT_HAS_PRIVATE_POT = false;
    private static final Boolean UPDATED_HAS_PRIVATE_POT = true;
    private static final String DEFAULT_AUTH_TOKEN = "AAAAA";
    private static final String UPDATED_AUTH_TOKEN = "BBBBB";

    private static final LocalDate DEFAULT_AUTH_TOKEN_UPDATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AUTH_TOKEN_UPDATE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_SM_USED_COUNT = 1;
    private static final Integer UPDATED_SM_USED_COUNT = 2;

    private static final LocalDate DEFAULT_SM_LAST_CHECK_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SM_LAST_CHECK_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_SM_LAST_CONTENT = "AAAAA";
    private static final String UPDATED_SM_LAST_CONTENT = "BBBBB";

    private static final LocalDate DEFAULT_SM_EXPIRE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SM_EXPIRE_TIME = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private YaUserRepository yaUserRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restYaUserMockMvc;

    private YaUser yaUser;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        YaUserResource yaUserResource = new YaUserResource();
        ReflectionTestUtils.setField(yaUserResource, "yaUserRepository", yaUserRepository);
        this.restYaUserMockMvc = MockMvcBuilders.standaloneSetup(yaUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        yaUser = new YaUser();
        yaUser.setName(DEFAULT_NAME);
        yaUser.setMobileNumber(DEFAULT_MOBILE_NUMBER);
        yaUser.setFacebook(DEFAULT_FACEBOOK);
        yaUser.setEmail(DEFAULT_EMAIL);
        yaUser.setPassword(DEFAULT_PASSWORD);
        yaUser.setGender(DEFAULT_GENDER);
        yaUser.setLoginStatus(DEFAULT_LOGIN_STATUS);
        yaUser.setHasPrivatePot(DEFAULT_HAS_PRIVATE_POT);
        yaUser.setAuthToken(DEFAULT_AUTH_TOKEN);
        yaUser.setAuthTokenUpdateTime(DEFAULT_AUTH_TOKEN_UPDATE_TIME);
        yaUser.setSmUsedCount(DEFAULT_SM_USED_COUNT);
        yaUser.setSmLastCheckDate(DEFAULT_SM_LAST_CHECK_DATE);
        yaUser.setSmLastContent(DEFAULT_SM_LAST_CONTENT);
        yaUser.setSmExpireTime(DEFAULT_SM_EXPIRE_TIME);
    }

    @Test
    @Transactional
    public void createYaUser() throws Exception {
        int databaseSizeBeforeCreate = yaUserRepository.findAll().size();

        // Create the YaUser

        restYaUserMockMvc.perform(post("/api/yaUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaUser)))
                .andExpect(status().isCreated());

        // Validate the YaUser in the database
        List<YaUser> yaUsers = yaUserRepository.findAll();
        assertThat(yaUsers).hasSize(databaseSizeBeforeCreate + 1);
        YaUser testYaUser = yaUsers.get(yaUsers.size() - 1);
        assertThat(testYaUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testYaUser.getMobileNumber()).isEqualTo(DEFAULT_MOBILE_NUMBER);
        assertThat(testYaUser.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testYaUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testYaUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testYaUser.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testYaUser.getLoginStatus()).isEqualTo(DEFAULT_LOGIN_STATUS);
        assertThat(testYaUser.getHasPrivatePot()).isEqualTo(DEFAULT_HAS_PRIVATE_POT);
        assertThat(testYaUser.getAuthToken()).isEqualTo(DEFAULT_AUTH_TOKEN);
        assertThat(testYaUser.getAuthTokenUpdateTime()).isEqualTo(DEFAULT_AUTH_TOKEN_UPDATE_TIME);
        assertThat(testYaUser.getSmUsedCount()).isEqualTo(DEFAULT_SM_USED_COUNT);
        assertThat(testYaUser.getSmLastCheckDate()).isEqualTo(DEFAULT_SM_LAST_CHECK_DATE);
        assertThat(testYaUser.getSmLastContent()).isEqualTo(DEFAULT_SM_LAST_CONTENT);
        assertThat(testYaUser.getSmExpireTime()).isEqualTo(DEFAULT_SM_EXPIRE_TIME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaUserRepository.findAll().size();
        // set the field null
        yaUser.setName(null);

        // Create the YaUser, which fails.

        restYaUserMockMvc.perform(post("/api/yaUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaUser)))
                .andExpect(status().isBadRequest());

        List<YaUser> yaUsers = yaUserRepository.findAll();
        assertThat(yaUsers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = yaUserRepository.findAll().size();
        // set the field null
        yaUser.setPassword(null);

        // Create the YaUser, which fails.

        restYaUserMockMvc.perform(post("/api/yaUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaUser)))
                .andExpect(status().isBadRequest());

        List<YaUser> yaUsers = yaUserRepository.findAll();
        assertThat(yaUsers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllYaUsers() throws Exception {
        // Initialize the database
        yaUserRepository.saveAndFlush(yaUser);

        // Get all the yaUsers
        restYaUserMockMvc.perform(get("/api/yaUsers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(yaUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].mobileNumber").value(hasItem(DEFAULT_MOBILE_NUMBER.toString())))
                .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
                .andExpect(jsonPath("$.[*].loginStatus").value(hasItem(DEFAULT_LOGIN_STATUS.toString())))
                .andExpect(jsonPath("$.[*].hasPrivatePot").value(hasItem(DEFAULT_HAS_PRIVATE_POT.booleanValue())))
                .andExpect(jsonPath("$.[*].authToken").value(hasItem(DEFAULT_AUTH_TOKEN.toString())))
                .andExpect(jsonPath("$.[*].authTokenUpdateTime").value(hasItem(DEFAULT_AUTH_TOKEN_UPDATE_TIME.toString())))
                .andExpect(jsonPath("$.[*].smUsedCount").value(hasItem(DEFAULT_SM_USED_COUNT)))
                .andExpect(jsonPath("$.[*].smLastCheckDate").value(hasItem(DEFAULT_SM_LAST_CHECK_DATE.toString())))
                .andExpect(jsonPath("$.[*].smLastContent").value(hasItem(DEFAULT_SM_LAST_CONTENT.toString())))
                .andExpect(jsonPath("$.[*].smExpireTime").value(hasItem(DEFAULT_SM_EXPIRE_TIME.toString())));
    }

    @Test
    @Transactional
    public void getYaUser() throws Exception {
        // Initialize the database
        yaUserRepository.saveAndFlush(yaUser);

        // Get the yaUser
        restYaUserMockMvc.perform(get("/api/yaUsers/{id}", yaUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(yaUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.mobileNumber").value(DEFAULT_MOBILE_NUMBER.toString()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.loginStatus").value(DEFAULT_LOGIN_STATUS.toString()))
            .andExpect(jsonPath("$.hasPrivatePot").value(DEFAULT_HAS_PRIVATE_POT.booleanValue()))
            .andExpect(jsonPath("$.authToken").value(DEFAULT_AUTH_TOKEN.toString()))
            .andExpect(jsonPath("$.authTokenUpdateTime").value(DEFAULT_AUTH_TOKEN_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.smUsedCount").value(DEFAULT_SM_USED_COUNT))
            .andExpect(jsonPath("$.smLastCheckDate").value(DEFAULT_SM_LAST_CHECK_DATE.toString()))
            .andExpect(jsonPath("$.smLastContent").value(DEFAULT_SM_LAST_CONTENT.toString()))
            .andExpect(jsonPath("$.smExpireTime").value(DEFAULT_SM_EXPIRE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingYaUser() throws Exception {
        // Get the yaUser
        restYaUserMockMvc.perform(get("/api/yaUsers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateYaUser() throws Exception {
        // Initialize the database
        yaUserRepository.saveAndFlush(yaUser);

		int databaseSizeBeforeUpdate = yaUserRepository.findAll().size();

        // Update the yaUser
        yaUser.setName(UPDATED_NAME);
        yaUser.setMobileNumber(UPDATED_MOBILE_NUMBER);
        yaUser.setFacebook(UPDATED_FACEBOOK);
        yaUser.setEmail(UPDATED_EMAIL);
        yaUser.setPassword(UPDATED_PASSWORD);
        yaUser.setGender(UPDATED_GENDER);
        yaUser.setLoginStatus(UPDATED_LOGIN_STATUS);
        yaUser.setHasPrivatePot(UPDATED_HAS_PRIVATE_POT);
        yaUser.setAuthToken(UPDATED_AUTH_TOKEN);
        yaUser.setAuthTokenUpdateTime(UPDATED_AUTH_TOKEN_UPDATE_TIME);
        yaUser.setSmUsedCount(UPDATED_SM_USED_COUNT);
        yaUser.setSmLastCheckDate(UPDATED_SM_LAST_CHECK_DATE);
        yaUser.setSmLastContent(UPDATED_SM_LAST_CONTENT);
        yaUser.setSmExpireTime(UPDATED_SM_EXPIRE_TIME);

        restYaUserMockMvc.perform(put("/api/yaUsers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(yaUser)))
                .andExpect(status().isOk());

        // Validate the YaUser in the database
        List<YaUser> yaUsers = yaUserRepository.findAll();
        assertThat(yaUsers).hasSize(databaseSizeBeforeUpdate);
        YaUser testYaUser = yaUsers.get(yaUsers.size() - 1);
        assertThat(testYaUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testYaUser.getMobileNumber()).isEqualTo(UPDATED_MOBILE_NUMBER);
        assertThat(testYaUser.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testYaUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testYaUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testYaUser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testYaUser.getLoginStatus()).isEqualTo(UPDATED_LOGIN_STATUS);
        assertThat(testYaUser.getHasPrivatePot()).isEqualTo(UPDATED_HAS_PRIVATE_POT);
        assertThat(testYaUser.getAuthToken()).isEqualTo(UPDATED_AUTH_TOKEN);
        assertThat(testYaUser.getAuthTokenUpdateTime()).isEqualTo(UPDATED_AUTH_TOKEN_UPDATE_TIME);
        assertThat(testYaUser.getSmUsedCount()).isEqualTo(UPDATED_SM_USED_COUNT);
        assertThat(testYaUser.getSmLastCheckDate()).isEqualTo(UPDATED_SM_LAST_CHECK_DATE);
        assertThat(testYaUser.getSmLastContent()).isEqualTo(UPDATED_SM_LAST_CONTENT);
        assertThat(testYaUser.getSmExpireTime()).isEqualTo(UPDATED_SM_EXPIRE_TIME);
    }

    @Test
    @Transactional
    public void deleteYaUser() throws Exception {
        // Initialize the database
        yaUserRepository.saveAndFlush(yaUser);

		int databaseSizeBeforeDelete = yaUserRepository.findAll().size();

        // Get the yaUser
        restYaUserMockMvc.perform(delete("/api/yaUsers/{id}", yaUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<YaUser> yaUsers = yaUserRepository.findAll();
        assertThat(yaUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
