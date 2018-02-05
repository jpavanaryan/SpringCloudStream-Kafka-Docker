package com.chargecodes.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chargecodes.domain.ChargeCodeProject;

import com.chargecodes.repository.ChargeCodeProjectRepository;
import com.chargecodes.web.rest.errors.BadRequestAlertException;
import com.chargecodes.web.rest.util.HeaderUtil;
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
 * REST controller for managing ChargeCodeProject.
 */
@RestController
@RequestMapping("/api")
public class ChargeCodeProjectResource {

    private final Logger log = LoggerFactory.getLogger(ChargeCodeProjectResource.class);

    private static final String ENTITY_NAME = "chargeCodeProject";

    private final ChargeCodeProjectRepository chargeCodeProjectRepository;

    public ChargeCodeProjectResource(ChargeCodeProjectRepository chargeCodeProjectRepository) {
        this.chargeCodeProjectRepository = chargeCodeProjectRepository;
    }

    /**
     * POST  /charge-code-projects : Create a new chargeCodeProject.
     *
     * @param chargeCodeProject the chargeCodeProject to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chargeCodeProject, or with status 400 (Bad Request) if the chargeCodeProject has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/charge-code-projects")
    @Timed
    public ResponseEntity<ChargeCodeProject> createChargeCodeProject(@RequestBody ChargeCodeProject chargeCodeProject) throws URISyntaxException {
        log.debug("REST request to save ChargeCodeProject : {}", chargeCodeProject);
        if (chargeCodeProject.getId() != null) {
            throw new BadRequestAlertException("A new chargeCodeProject cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChargeCodeProject result = chargeCodeProjectRepository.save(chargeCodeProject);
        return ResponseEntity.created(new URI("/api/charge-code-projects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /charge-code-projects : Updates an existing chargeCodeProject.
     *
     * @param chargeCodeProject the chargeCodeProject to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chargeCodeProject,
     * or with status 400 (Bad Request) if the chargeCodeProject is not valid,
     * or with status 500 (Internal Server Error) if the chargeCodeProject couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/charge-code-projects")
    @Timed
    public ResponseEntity<ChargeCodeProject> updateChargeCodeProject(@RequestBody ChargeCodeProject chargeCodeProject) throws URISyntaxException {
        log.debug("REST request to update ChargeCodeProject : {}", chargeCodeProject);
        if (chargeCodeProject.getId() == null) {
            return createChargeCodeProject(chargeCodeProject);
        }
        ChargeCodeProject result = chargeCodeProjectRepository.save(chargeCodeProject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chargeCodeProject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /charge-code-projects : get all the chargeCodeProjects.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of chargeCodeProjects in body
     */
    @GetMapping("/charge-code-projects")
    @Timed
    public List<ChargeCodeProject> getAllChargeCodeProjects() {
        log.debug("REST request to get all ChargeCodeProjects");
        return chargeCodeProjectRepository.findAll();
        }

    /**
     * GET  /charge-code-projects/:id : get the "id" chargeCodeProject.
     *
     * @param id the id of the chargeCodeProject to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chargeCodeProject, or with status 404 (Not Found)
     */
    @GetMapping("/charge-code-projects/{id}")
    @Timed
    public ResponseEntity<ChargeCodeProject> getChargeCodeProject(@PathVariable Long id) {
        log.debug("REST request to get ChargeCodeProject : {}", id);
        ChargeCodeProject chargeCodeProject = chargeCodeProjectRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chargeCodeProject));
    }

    /**
     * DELETE  /charge-code-projects/:id : delete the "id" chargeCodeProject.
     *
     * @param id the id of the chargeCodeProject to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/charge-code-projects/{id}")
    @Timed
    public ResponseEntity<Void> deleteChargeCodeProject(@PathVariable Long id) {
        log.debug("REST request to delete ChargeCodeProject : {}", id);
        chargeCodeProjectRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
