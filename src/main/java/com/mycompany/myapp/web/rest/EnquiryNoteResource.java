package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.EnquiryNoteService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.EnquiryNoteDTO;
import com.mycompany.myapp.service.dto.EnquiryNoteCriteria;
import com.mycompany.myapp.service.EnquiryNoteQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.EnquiryNote}.
 */
@RestController
@RequestMapping("/api")
public class EnquiryNoteResource {

    private final Logger log = LoggerFactory.getLogger(EnquiryNoteResource.class);

    private static final String ENTITY_NAME = "enquiryNote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnquiryNoteService enquiryNoteService;

    private final EnquiryNoteQueryService enquiryNoteQueryService;

    public EnquiryNoteResource(EnquiryNoteService enquiryNoteService, EnquiryNoteQueryService enquiryNoteQueryService) {
        this.enquiryNoteService = enquiryNoteService;
        this.enquiryNoteQueryService = enquiryNoteQueryService;
    }

    /**
     * {@code POST  /enquiry-notes} : Create a new enquiryNote.
     *
     * @param enquiryNoteDTO the enquiryNoteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enquiryNoteDTO, or with status {@code 400 (Bad Request)} if the enquiryNote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enquiry-notes")
    public ResponseEntity<EnquiryNoteDTO> createEnquiryNote(@Valid @RequestBody EnquiryNoteDTO enquiryNoteDTO) throws URISyntaxException {
        log.debug("REST request to save EnquiryNote : {}", enquiryNoteDTO);
        if (enquiryNoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new enquiryNote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnquiryNoteDTO result = enquiryNoteService.save(enquiryNoteDTO);
        return ResponseEntity.created(new URI("/api/enquiry-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enquiry-notes} : Updates an existing enquiryNote.
     *
     * @param enquiryNoteDTO the enquiryNoteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enquiryNoteDTO,
     * or with status {@code 400 (Bad Request)} if the enquiryNoteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enquiryNoteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enquiry-notes")
    public ResponseEntity<EnquiryNoteDTO> updateEnquiryNote(@Valid @RequestBody EnquiryNoteDTO enquiryNoteDTO) throws URISyntaxException {
        log.debug("REST request to update EnquiryNote : {}", enquiryNoteDTO);
        if (enquiryNoteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnquiryNoteDTO result = enquiryNoteService.save(enquiryNoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enquiryNoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enquiry-notes} : get all the enquiryNotes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enquiryNotes in body.
     */
    @GetMapping("/enquiry-notes")
    public ResponseEntity<List<EnquiryNoteDTO>> getAllEnquiryNotes(EnquiryNoteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EnquiryNotes by criteria: {}", criteria);
        Page<EnquiryNoteDTO> page = enquiryNoteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enquiry-notes/count} : count all the enquiryNotes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/enquiry-notes/count")
    public ResponseEntity<Long> countEnquiryNotes(EnquiryNoteCriteria criteria) {
        log.debug("REST request to count EnquiryNotes by criteria: {}", criteria);
        return ResponseEntity.ok().body(enquiryNoteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /enquiry-notes/:id} : get the "id" enquiryNote.
     *
     * @param id the id of the enquiryNoteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enquiryNoteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enquiry-notes/{id}")
    public ResponseEntity<EnquiryNoteDTO> getEnquiryNote(@PathVariable Long id) {
        log.debug("REST request to get EnquiryNote : {}", id);
        Optional<EnquiryNoteDTO> enquiryNoteDTO = enquiryNoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enquiryNoteDTO);
    }

    /**
     * {@code DELETE  /enquiry-notes/:id} : delete the "id" enquiryNote.
     *
     * @param id the id of the enquiryNoteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enquiry-notes/{id}")
    public ResponseEntity<Void> deleteEnquiryNote(@PathVariable Long id) {
        log.debug("REST request to delete EnquiryNote : {}", id);
        enquiryNoteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
