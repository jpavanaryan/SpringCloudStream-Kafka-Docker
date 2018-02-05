package com.chargecodes.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.chargecodes.domain.ChargeCode;

import com.chargecodes.repository.ChargeCodeRepository;
import com.chargecodes.web.rest.errors.BadRequestAlertException;
import com.chargecodes.web.rest.util.HeaderUtil;
import com.chargecodes.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ChargeCode.
 */
@RestController
@RequestMapping("/api")
public class ChargeCodeResource {

    private final Logger log = LoggerFactory.getLogger(ChargeCodeResource.class);

    private static final String ENTITY_NAME = "chargeCode";

    private final ChargeCodeRepository chargeCodeRepository;

    public ChargeCodeResource(ChargeCodeRepository chargeCodeRepository) {
        this.chargeCodeRepository = chargeCodeRepository;
    }

    /**
     * POST  /charge-codes : Create a new chargeCode.
     *
     * @param chargeCode the chargeCode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new chargeCode, or with status 400 (Bad Request) if the chargeCode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/charge-codes")
    @Timed
    public ResponseEntity<ChargeCode> createChargeCode(@Valid @RequestBody ChargeCode chargeCode) throws URISyntaxException {
        log.debug("REST request to save ChargeCode : {}", chargeCode);
        if (chargeCode.getId() != null) {
            throw new BadRequestAlertException("A new chargeCode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ChargeCode result = chargeCodeRepository.save(chargeCode);
        return ResponseEntity.created(new URI("/api/charge-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /charge-codes : Updates an existing chargeCode.
     *
     * @param chargeCode the chargeCode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated chargeCode,
     * or with status 400 (Bad Request) if the chargeCode is not valid,
     * or with status 500 (Internal Server Error) if the chargeCode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/charge-codes")
    @Timed
    public ResponseEntity<ChargeCode> updateChargeCode(@Valid @RequestBody ChargeCode chargeCode) throws URISyntaxException {
        log.debug("REST request to update ChargeCode : {}", chargeCode);
        if (chargeCode.getId() == null) {
            return createChargeCode(chargeCode);
        }
        ChargeCode result = chargeCodeRepository.save(chargeCode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, chargeCode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /charge-codes : get all the chargeCodes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of chargeCodes in body
     */
    @GetMapping("/charge-codes")
    @Timed
    public ResponseEntity<List<ChargeCode>> getAllChargeCodes(Pageable pageable) {
        log.debug("REST request to get a page of ChargeCodes");
        Page<ChargeCode> page = chargeCodeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/charge-codes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /charge-codes/:id : get the "id" chargeCode.
     *
     * @param id the id of the chargeCode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the chargeCode, or with status 404 (Not Found)
     */
    @GetMapping("/charge-codes/{id}")
    @Timed
    public ResponseEntity<ChargeCode> getChargeCode(@PathVariable Long id) {
        log.debug("REST request to get ChargeCode : {}", id);
        ChargeCode chargeCode = chargeCodeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(chargeCode));
    }

    /**
     * DELETE  /charge-codes/:id : delete the "id" chargeCode.
     *
     * @param id the id of the chargeCode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/charge-codes/{id}")
    @Timed
    public ResponseEntity<Void> deleteChargeCode(@PathVariable Long id) {
        log.debug("REST request to delete ChargeCode : {}", id);
        chargeCodeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
