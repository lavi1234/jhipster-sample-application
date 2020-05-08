package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.EmailTrackingService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.EmailTrackingDTO;
import com.mycompany.myapp.service.dto.EmailTrackingCriteria;
import com.mycompany.myapp.service.EmailTrackingQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.EmailTracking}.
 */
@RestController
@RequestMapping("/api")
public class EmailTrackingResource {

    private final Logger log = LoggerFactory.getLogger(EmailTrackingResource.class);

    private static final String ENTITY_NAME = "emailTracking";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailTrackingService emailTrackingService;

    private final EmailTrackingQueryService emailTrackingQueryService;

    public EmailTrackingResource(EmailTrackingService emailTrackingService, EmailTrackingQueryService emailTrackingQueryService) {
        this.emailTrackingService = emailTrackingService;
        this.emailTrackingQueryService = emailTrackingQueryService;
    }

    /**
     * {@code POST  /email-trackings} : Create a new emailTracking.
     *
     * @param emailTrackingDTO the emailTrackingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailTrackingDTO, or with status {@code 400 (Bad Request)} if the emailTracking has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/email-trackings")
    public ResponseEntity<EmailTrackingDTO> createEmailTracking(@Valid @RequestBody EmailTrackingDTO emailTrackingDTO) throws URISyntaxException {
        log.debug("REST request to save EmailTracking : {}", emailTrackingDTO);
        if (emailTrackingDTO.getId() != null) {
            throw new BadRequestAlertException("A new emailTracking cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailTrackingDTO result = emailTrackingService.save(emailTrackingDTO);
        return ResponseEntity.created(new URI("/api/email-trackings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /email-trackings} : Updates an existing emailTracking.
     *
     * @param emailTrackingDTO the emailTrackingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailTrackingDTO,
     * or with status {@code 400 (Bad Request)} if the emailTrackingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailTrackingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/email-trackings")
    public ResponseEntity<EmailTrackingDTO> updateEmailTracking(@Valid @RequestBody EmailTrackingDTO emailTrackingDTO) throws URISyntaxException {
        log.debug("REST request to update EmailTracking : {}", emailTrackingDTO);
        if (emailTrackingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailTrackingDTO result = emailTrackingService.save(emailTrackingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emailTrackingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /email-trackings} : get all the emailTrackings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailTrackings in body.
     */
    @GetMapping("/email-trackings")
    public ResponseEntity<List<EmailTrackingDTO>> getAllEmailTrackings(EmailTrackingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EmailTrackings by criteria: {}", criteria);
        Page<EmailTrackingDTO> page = emailTrackingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /email-trackings/count} : count all the emailTrackings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/email-trackings/count")
    public ResponseEntity<Long> countEmailTrackings(EmailTrackingCriteria criteria) {
        log.debug("REST request to count EmailTrackings by criteria: {}", criteria);
        return ResponseEntity.ok().body(emailTrackingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /email-trackings/:id} : get the "id" emailTracking.
     *
     * @param id the id of the emailTrackingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailTrackingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/email-trackings/{id}")
    public ResponseEntity<EmailTrackingDTO> getEmailTracking(@PathVariable Long id) {
        log.debug("REST request to get EmailTracking : {}", id);
        Optional<EmailTrackingDTO> emailTrackingDTO = emailTrackingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailTrackingDTO);
    }

    /**
     * {@code DELETE  /email-trackings/:id} : delete the "id" emailTracking.
     *
     * @param id the id of the emailTrackingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/email-trackings/{id}")
    public ResponseEntity<Void> deleteEmailTracking(@PathVariable Long id) {
        log.debug("REST request to delete EmailTracking : {}", id);
        emailTrackingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
