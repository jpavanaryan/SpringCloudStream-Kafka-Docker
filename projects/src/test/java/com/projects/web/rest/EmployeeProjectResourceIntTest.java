package com.projects.web.rest;

import com.projects.ProjectsApp;

import com.projects.domain.EmployeeProject;
import com.projects.repository.EmployeeProjectRepository;
import com.projects.web.rest.errors.ExceptionTranslator;

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

import static com.projects.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EmployeeProjectResource REST controller.
 *
 * @see EmployeeProjectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjectsApp.class)
public class EmployeeProjectResourceIntTest {

    @Autowired
    private EmployeeProjectRepository employeeProjectRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEmployeeProjectMockMvc;

    private EmployeeProject employeeProject;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeProjectResource employeeProjectResource = new EmployeeProjectResource(employeeProjectRepository);
        this.restEmployeeProjectMockMvc = MockMvcBuilders.standaloneSetup(employeeProjectResource)
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
    public static EmployeeProject createEntity(EntityManager em) {
        EmployeeProject employeeProject = new EmployeeProject();
        return employeeProject;
    }

    @Before
    public void initTest() {
        employeeProject = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeProject() throws Exception {
        int databaseSizeBeforeCreate = employeeProjectRepository.findAll().size();

        // Create the EmployeeProject
        restEmployeeProjectMockMvc.perform(post("/api/employee-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeProject)))
            .andExpect(status().isCreated());

        // Validate the EmployeeProject in the database
        List<EmployeeProject> employeeProjectList = employeeProjectRepository.findAll();
        assertThat(employeeProjectList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeProject testEmployeeProject = employeeProjectList.get(employeeProjectList.size() - 1);
    }

    @Test
    @Transactional
    public void createEmployeeProjectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeProjectRepository.findAll().size();

        // Create the EmployeeProject with an existing ID
        employeeProject.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeProjectMockMvc.perform(post("/api/employee-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeProject)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeProject in the database
        List<EmployeeProject> employeeProjectList = employeeProjectRepository.findAll();
        assertThat(employeeProjectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEmployeeProjects() throws Exception {
        // Initialize the database
        employeeProjectRepository.saveAndFlush(employeeProject);

        // Get all the employeeProjectList
        restEmployeeProjectMockMvc.perform(get("/api/employee-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeProject.getId().intValue())));
    }

    @Test
    @Transactional
    public void getEmployeeProject() throws Exception {
        // Initialize the database
        employeeProjectRepository.saveAndFlush(employeeProject);

        // Get the employeeProject
        restEmployeeProjectMockMvc.perform(get("/api/employee-projects/{id}", employeeProject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeProject.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeProject() throws Exception {
        // Get the employeeProject
        restEmployeeProjectMockMvc.perform(get("/api/employee-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeProject() throws Exception {
        // Initialize the database
        employeeProjectRepository.saveAndFlush(employeeProject);
        int databaseSizeBeforeUpdate = employeeProjectRepository.findAll().size();

        // Update the employeeProject
        EmployeeProject updatedEmployeeProject = employeeProjectRepository.findOne(employeeProject.getId());
        // Disconnect from session so that the updates on updatedEmployeeProject are not directly saved in db
        em.detach(updatedEmployeeProject);

        restEmployeeProjectMockMvc.perform(put("/api/employee-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeProject)))
            .andExpect(status().isOk());

        // Validate the EmployeeProject in the database
        List<EmployeeProject> employeeProjectList = employeeProjectRepository.findAll();
        assertThat(employeeProjectList).hasSize(databaseSizeBeforeUpdate);
        EmployeeProject testEmployeeProject = employeeProjectList.get(employeeProjectList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeProject() throws Exception {
        int databaseSizeBeforeUpdate = employeeProjectRepository.findAll().size();

        // Create the EmployeeProject

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEmployeeProjectMockMvc.perform(put("/api/employee-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeProject)))
            .andExpect(status().isCreated());

        // Validate the EmployeeProject in the database
        List<EmployeeProject> employeeProjectList = employeeProjectRepository.findAll();
        assertThat(employeeProjectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEmployeeProject() throws Exception {
        // Initialize the database
        employeeProjectRepository.saveAndFlush(employeeProject);
        int databaseSizeBeforeDelete = employeeProjectRepository.findAll().size();

        // Get the employeeProject
        restEmployeeProjectMockMvc.perform(delete("/api/employee-projects/{id}", employeeProject.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EmployeeProject> employeeProjectList = employeeProjectRepository.findAll();
        assertThat(employeeProjectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeProject.class);
        EmployeeProject employeeProject1 = new EmployeeProject();
        employeeProject1.setId(1L);
        EmployeeProject employeeProject2 = new EmployeeProject();
        employeeProject2.setId(employeeProject1.getId());
        assertThat(employeeProject1).isEqualTo(employeeProject2);
        employeeProject2.setId(2L);
        assertThat(employeeProject1).isNotEqualTo(employeeProject2);
        employeeProject1.setId(null);
        assertThat(employeeProject1).isNotEqualTo(employeeProject2);
    }
}
