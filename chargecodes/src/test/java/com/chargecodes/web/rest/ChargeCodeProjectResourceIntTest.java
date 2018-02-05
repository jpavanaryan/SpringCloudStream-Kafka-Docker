package com.chargecodes.web.rest;

import com.chargecodes.ChargecodesApp;

import com.chargecodes.domain.ChargeCodeProject;
import com.chargecodes.repository.ChargeCodeProjectRepository;
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
import java.util.List;

import static com.chargecodes.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChargeCodeProjectResource REST controller.
 *
 * @see ChargeCodeProjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChargecodesApp.class)
public class ChargeCodeProjectResourceIntTest {

    @Autowired
    private ChargeCodeProjectRepository chargeCodeProjectRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChargeCodeProjectMockMvc;

    private ChargeCodeProject chargeCodeProject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChargeCodeProjectResource chargeCodeProjectResource = new ChargeCodeProjectResource(chargeCodeProjectRepository);
        this.restChargeCodeProjectMockMvc = MockMvcBuilders.standaloneSetup(chargeCodeProjectResource)
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
    public static ChargeCodeProject createEntity(EntityManager em) {
        ChargeCodeProject chargeCodeProject = new ChargeCodeProject();
        return chargeCodeProject;
    }

    @Before
    public void initTest() {
        chargeCodeProject = createEntity(em);
    }

    @Test
    @Transactional
    public void createChargeCodeProject() throws Exception {
        int databaseSizeBeforeCreate = chargeCodeProjectRepository.findAll().size();

        // Create the ChargeCodeProject
        restChargeCodeProjectMockMvc.perform(post("/api/charge-code-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargeCodeProject)))
            .andExpect(status().isCreated());

        // Validate the ChargeCodeProject in the database
        List<ChargeCodeProject> chargeCodeProjectList = chargeCodeProjectRepository.findAll();
        assertThat(chargeCodeProjectList).hasSize(databaseSizeBeforeCreate + 1);
        ChargeCodeProject testChargeCodeProject = chargeCodeProjectList.get(chargeCodeProjectList.size() - 1);
    }

    @Test
    @Transactional
    public void createChargeCodeProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chargeCodeProjectRepository.findAll().size();

        // Create the ChargeCodeProject with an existing ID
        chargeCodeProject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChargeCodeProjectMockMvc.perform(post("/api/charge-code-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargeCodeProject)))
            .andExpect(status().isBadRequest());

        // Validate the ChargeCodeProject in the database
        List<ChargeCodeProject> chargeCodeProjectList = chargeCodeProjectRepository.findAll();
        assertThat(chargeCodeProjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllChargeCodeProjects() throws Exception {
        // Initialize the database
        chargeCodeProjectRepository.saveAndFlush(chargeCodeProject);

        // Get all the chargeCodeProjectList
        restChargeCodeProjectMockMvc.perform(get("/api/charge-code-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chargeCodeProject.getId().intValue())));
    }

    @Test
    @Transactional
    public void getChargeCodeProject() throws Exception {
        // Initialize the database
        chargeCodeProjectRepository.saveAndFlush(chargeCodeProject);

        // Get the chargeCodeProject
        restChargeCodeProjectMockMvc.perform(get("/api/charge-code-projects/{id}", chargeCodeProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chargeCodeProject.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingChargeCodeProject() throws Exception {
        // Get the chargeCodeProject
        restChargeCodeProjectMockMvc.perform(get("/api/charge-code-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChargeCodeProject() throws Exception {
        // Initialize the database
        chargeCodeProjectRepository.saveAndFlush(chargeCodeProject);
        int databaseSizeBeforeUpdate = chargeCodeProjectRepository.findAll().size();

        // Update the chargeCodeProject
        ChargeCodeProject updatedChargeCodeProject = chargeCodeProjectRepository.findOne(chargeCodeProject.getId());
        // Disconnect from session so that the updates on updatedChargeCodeProject are not directly saved in db
        em.detach(updatedChargeCodeProject);

        restChargeCodeProjectMockMvc.perform(put("/api/charge-code-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChargeCodeProject)))
            .andExpect(status().isOk());

        // Validate the ChargeCodeProject in the database
        List<ChargeCodeProject> chargeCodeProjectList = chargeCodeProjectRepository.findAll();
        assertThat(chargeCodeProjectList).hasSize(databaseSizeBeforeUpdate);
        ChargeCodeProject testChargeCodeProject = chargeCodeProjectList.get(chargeCodeProjectList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingChargeCodeProject() throws Exception {
        int databaseSizeBeforeUpdate = chargeCodeProjectRepository.findAll().size();

        // Create the ChargeCodeProject

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChargeCodeProjectMockMvc.perform(put("/api/charge-code-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chargeCodeProject)))
            .andExpect(status().isCreated());

        // Validate the ChargeCodeProject in the database
        List<ChargeCodeProject> chargeCodeProjectList = chargeCodeProjectRepository.findAll();
        assertThat(chargeCodeProjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteChargeCodeProject() throws Exception {
        // Initialize the database
        chargeCodeProjectRepository.saveAndFlush(chargeCodeProject);
        int databaseSizeBeforeDelete = chargeCodeProjectRepository.findAll().size();

        // Get the chargeCodeProject
        restChargeCodeProjectMockMvc.perform(delete("/api/charge-code-projects/{id}", chargeCodeProject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChargeCodeProject> chargeCodeProjectList = chargeCodeProjectRepository.findAll();
        assertThat(chargeCodeProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChargeCodeProject.class);
        ChargeCodeProject chargeCodeProject1 = new ChargeCodeProject();
        chargeCodeProject1.setId(1L);
        ChargeCodeProject chargeCodeProject2 = new ChargeCodeProject();
        chargeCodeProject2.setId(chargeCodeProject1.getId());
        assertThat(chargeCodeProject1).isEqualTo(chargeCodeProject2);
        chargeCodeProject2.setId(2L);
        assertThat(chargeCodeProject1).isNotEqualTo(chargeCodeProject2);
        chargeCodeProject1.setId(null);
        assertThat(chargeCodeProject1).isNotEqualTo(chargeCodeProject2);
    }
}
