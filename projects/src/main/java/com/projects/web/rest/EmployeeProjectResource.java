package com.projects.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.projects.domain.EmployeeProject;

import com.projects.repository.EmployeeProjectRepository;
import com.projects.web.rest.errors.BadRequestAlertException;
import com.projects.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EmployeeProject.
 */
@RestController
@RequestMapping("/api")
public class EmployeeProjectResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeProjectResource.class);

    private static final String ENTITY_NAME = "employeeProject";

    private final EmployeeProjectRepository employeeProjectRepository;

    public EmployeeProjectResource(EmployeeProjectRepository employeeProjectRepository) {
        this.employeeProjectRepository = employeeProjectRepository;
    }

    /**
     * POST  /employee-projects : Create a new employeeProject.
     *
     * @param employeeProject the employeeProject to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeProject, or with status 400 (Bad Request) if the employeeProject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-projects")
    @Timed
    public ResponseEntity<EmployeeProject> createEmployeeProject(@RequestBody EmployeeProject employeeProject) throws URISyntaxException {
        log.debug("REST request to save EmployeeProject : {}", employeeProject);
        if (employeeProject.getId() != null) {
            throw new BadRequestAlertException("A new employeeProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeProject result = employeeProjectRepository.save(employeeProject);
        return ResponseEntity.created(new URI("/api/employee-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-projects : Updates an existing employeeProject.
     *
     * @param employeeProject the employeeProject to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeProject,
     * or with status 400 (Bad Request) if the employeeProject is not valid,
     * or with status 500 (Internal Server Error) if the employeeProject couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-projects")
    @Timed
    public ResponseEntity<EmployeeProject> updateEmployeeProject(@RequestBody EmployeeProject employeeProject) throws URISyntaxException {
        log.debug("REST request to update EmployeeProject : {}", employeeProject);
        if (employeeProject.getId() == null) {
            return createEmployeeProject(employeeProject);
        }
        EmployeeProject result = employeeProjectRepository.save(employeeProject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeProject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-projects : get all the employeeProjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeeProjects in body
     */
    @GetMapping("/employee-projects")
    @Timed
    public List<EmployeeProject> getAllEmployeeProjects() {
        log.debug("REST request to get all EmployeeProjects");
        return employeeProjectRepository.findAll();
        }

    /**
     * GET  /employee-projects/:id : get the "id" employeeProject.
     *
     * @param id the id of the employeeProject to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeProject, or with status 404 (Not Found)
     */
    @GetMapping("/employee-projects/{id}")
    @Timed
    public ResponseEntity<EmployeeProject> getEmployeeProject(@PathVariable Long id) {
        log.debug("REST request to get EmployeeProject : {}", id);
        EmployeeProject employeeProject = employeeProjectRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeProject));
    }

    /**
     * DELETE  /employee-projects/:id : delete the "id" employeeProject.
     *
     * @param id the id of the employeeProject to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-projects/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeProject(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeProject : {}", id);
        employeeProjectRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
