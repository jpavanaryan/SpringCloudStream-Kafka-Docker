package com.chargecodes.web.rest;

import com.chargecodes.ChargecodesApp;

import com.chargecodes.domain.ChargeCode;
import com.chargecodes.repository.ChargeCodeRepository;
import com.chargecodes.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.chargecodes.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChargeCodeResource REST controller.
 *
 * @see ChargeCodeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChargecodesApp.class)
public class ChargeCodeResourceIntTest {

    private static final String DEFAULT_CHARGE_CODE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHARGE_CODE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHARGE_CODE_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_CHARGE_CODE_LOCATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CHARGE_CODE_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHARGE_CODE_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CHARGE_CODE_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CHARGE_CODE_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ChargeCodeRepository chargeCodeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChargeCodeMockMvc;

    private ChargeCode chargeCode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChargeCodeResource chargeCodeResource = new ChargeCodeResource(chargeCodeRepository);
        this.restChargeCodeMockMvc = MockMvcBuilders.standaloneSetup(chargeCodeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChargeCode createEntity(EntityManager em) {
        ChargeCode chargeCode = new ChargeCode()
            .chargeCodeName(DEFAULT_CHARGE_CODE_NAME)
            .chargeCodeLocation(DEFAULT_CHARGE_CODE_LOCATION)
            .chargeCodeStartDate(DEFAULT_CHARGE_CODE_START_DATE)
            .chargeCodeEndDate(DEFAULT_CHARGE_CODE_END_DATE);
        return chargeCode;
    }

    @Before
    public void initTest() {
        chargeCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createChargeCode() throws Exception {
        int databaseSizeBeforeCreate = chargeCodeRepository.findAll().size();

        // Create the ChargeCode
        restChargeCodeMockMvc.perform(post("/api/charge-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargeCode)))
            .andExpect(status().isCreated());

        // Validate the ChargeCode in the database
        List<ChargeCode> chargeCodeList = chargeCodeRepository.findAll();
        assertThat(chargeCodeList).hasSize(databaseSizeBeforeCreate + 1);
        ChargeCode testChargeCode = chargeCodeList.get(chargeCodeList.size() - 1);
        assertThat(testChargeCode.getChargeCodeName()).isEqualTo(DEFAULT_CHARGE_CODE_NAME);
        assertThat(testChargeCode.getChargeCodeLocation()).isEqualTo(DEFAULT_CHARGE_CODE_LOCATION);
        assertThat(testChargeCode.getChargeCodeStartDate()).isEqualTo(DEFAULT_CHARGE_CODE_START_DATE);
        assertThat(testChargeCode.getChargeCodeEndDate()).isEqualTo(DEFAULT_CHARGE_CODE_END_DATE);
    }

    @Test
    @Transactional
    public void createChargeCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chargeCodeRepository.findAll().size();

        // Create the ChargeCode with an existing ID
        chargeCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChargeCodeMockMvc.perform(post("/api/charge-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargeCode)))
            .andExpect(status().isBadRequest());

        // Validate the ChargeCode in the database
        List<ChargeCode> chargeCodeList = chargeCodeRepository.findAll();
        assertThat(chargeCodeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkChargeCodeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargeCodeRepository.findAll().size();
        // set the field null
        chargeCode.setChargeCodeName(null);

        // Create the ChargeCode, which fails.

        restChargeCodeMockMvc.perform(post("/api/charge-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargeCode)))
            .andExpect(status().isBadRequest());

        List<ChargeCode> chargeCodeList = chargeCodeRepository.findAll();
        assertThat(chargeCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChargeCodeLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = chargeCodeRepository.findAll().size();
        // set the field null
        chargeCode.setChargeCodeLocation(null);

        // Create the ChargeCode, which fails.

        restChargeCodeMockMvc.perform(post("/api/charge-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargeCode)))
            .andExpect(status().isBadRequest());

        List<ChargeCode> chargeCodeList = chargeCodeRepository.findAll();
        assertThat(chargeCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChargeCodes() throws Exception {
        // Initialize the database
        chargeCodeRepository.saveAndFlush(chargeCode);

        // Get all the chargeCodeList
        restChargeCodeMockMvc.perform(get("/api/charge-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chargeCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].chargeCodeName").value(hasItem(DEFAULT_CHARGE_CODE_NAME.toString())))
            .andExpect(jsonPath("$.[*].chargeCodeLocation").value(hasItem(DEFAULT_CHARGE_CODE_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].chargeCodeStartDate").value(hasItem(DEFAULT_CHARGE_CODE_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].chargeCodeEndDate").value(hasItem(DEFAULT_CHARGE_CODE_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getChargeCode() throws Exception {
        // Initialize the database
        chargeCodeRepository.saveAndFlush(chargeCode);

        // Get the chargeCode
        restChargeCodeMockMvc.perform(get("/api/charge-codes/{id}", chargeCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chargeCode.getId().intValue()))
            .andExpect(jsonPath("$.chargeCodeName").value(DEFAULT_CHARGE_CODE_NAME.toString()))
            .andExpect(jsonPath("$.chargeCodeLocation").value(DEFAULT_CHARGE_CODE_LOCATION.toString()))
            .andExpect(jsonPath("$.chargeCodeStartDate").value(DEFAULT_CHARGE_CODE_START_DATE.toString()))
            .andExpect(jsonPath("$.chargeCodeEndDate").value(DEFAULT_CHARGE_CODE_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChargeCode() throws Exception {
        // Get the chargeCode
        restChargeCodeMockMvc.perform(get("/api/charge-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChargeCode() throws Exception {
        // Initialize the database
        chargeCodeRepository.saveAndFlush(chargeCode);
        int databaseSizeBeforeUpdate = chargeCodeRepository.findAll().size();

        // Update the chargeCode
        ChargeCode updatedChargeCode = chargeCodeRepository.findOne(chargeCode.getId());
        // Disconnect from session so that the updates on updatedChargeCode are not directly saved in db
        em.detach(updatedChargeCode);
        updatedChargeCode
            .chargeCodeName(UPDATED_CHARGE_CODE_NAME)
            .chargeCodeLocation(UPDATED_CHARGE_CODE_LOCATION)
            .chargeCodeStartDate(UPDATED_CHARGE_CODE_START_DATE)
            .chargeCodeEndDate(UPDATED_CHARGE_CODE_END_DATE);

        restChargeCodeMockMvc.perform(put("/api/charge-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChargeCode)))
            .andExpect(status().isOk());

        // Validate the ChargeCode in the database
        List<ChargeCode> chargeCodeList = chargeCodeRepository.findAll();
        assertThat(chargeCodeList).hasSize(databaseSizeBeforeUpdate);
        ChargeCode testChargeCode = chargeCodeList.get(chargeCodeList.size() - 1);
        assertThat(testChargeCode.getChargeCodeName()).isEqualTo(UPDATED_CHARGE_CODE_NAME);
        assertThat(testChargeCode.getChargeCodeLocation()).isEqualTo(UPDATED_CHARGE_CODE_LOCATION);
        assertThat(testChargeCode.getChargeCodeStartDate()).isEqualTo(UPDATED_CHARGE_CODE_START_DATE);
        assertThat(testChargeCode.getChargeCodeEndDate()).isEqualTo(UPDATED_CHARGE_CODE_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingChargeCode() throws Exception {
        int databaseSizeBeforeUpdate = chargeCodeRepository.findAll().size();

        // Create the ChargeCode

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChargeCodeMockMvc.perform(put("/api/charge-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargeCode)))
            .andExpect(status().isCreated());

        // Validate the ChargeCode in the database
        List<ChargeCode> chargeCodeList = chargeCodeRepository.findAll();
        assertThat(chargeCodeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChargeCode() throws Exception {
        // Initialize the database
        chargeCodeRepository.saveAndFlush(chargeCode);
        int databaseSizeBeforeDelete = chargeCodeRepository.findAll().size();

        // Get the chargeCode
        restChargeCodeMockMvc.perform(delete("/api/charge-codes/{id}", chargeCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChargeCode> chargeCodeList = chargeCodeRepository.findAll();
        assertThat(chargeCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargeCode.class);
        ChargeCode chargeCode1 = new ChargeCode();
        chargeCode1.setId(1L);
        ChargeCode chargeCode2 = new ChargeCode();
        chargeCode2.setId(chargeCode1.getId());
        assertThat(chargeCode1).isEqualTo(chargeCode2);
        chargeCode2.setId(2L);
        assertThat(chargeCode1).isNotEqualTo(chargeCode2);
        chargeCode1.setId(null);
        assertThat(chargeCode1).isNotEqualTo(chargeCode2);
    }
}
