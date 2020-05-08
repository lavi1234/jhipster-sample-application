package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.EnquiryDetailsService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.EnquiryDetailsDTO;
import com.mycompany.myapp.service.dto.EnquiryDetailsCriteria;
import com.mycompany.myapp.service.EnquiryDetailsQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.EnquiryDetails}.
 */
@RestController
@RequestMapping("/api")
public class EnquiryDetailsResource {

    private final Logger log = LoggerFactory.getLogger(EnquiryDetailsResource.class);

    private static final String ENTITY_NAME = "enquiryDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnquiryDetailsService enquiryDetailsService;

    private final EnquiryDetailsQueryService enquiryDetailsQueryService;

    public EnquiryDetailsResource(EnquiryDetailsService enquiryDetailsService, EnquiryDetailsQueryService enquiryDetailsQueryService) {
        this.enquiryDetailsService = enquiryDetailsService;
        this.enquiryDetailsQueryService = enquiryDetailsQueryService;
    }

    /**
     * {@code POST  /enquiry-details} : Create a new enquiryDetails.
     *
     * @param enquiryDetailsDTO the enquiryDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enquiryDetailsDTO, or with status {@code 400 (Bad Request)} if the enquiryDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enquiry-details")
    public ResponseEntity<EnquiryDetailsDTO> createEnquiryDetails(@Valid @RequestBody EnquiryDetailsDTO enquiryDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save EnquiryDetails : {}", enquiryDetailsDTO);
        if (enquiryDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new enquiryDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnquiryDetailsDTO result = enquiryDetailsService.save(enquiryDetailsDTO);
        return ResponseEntity.created(new URI("/api/enquiry-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enquiry-details} : Updates an existing enquiryDetails.
     *
     * @param enquiryDetailsDTO the enquiryDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enquiryDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the enquiryDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enquiryDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enquiry-details")
    public ResponseEntity<EnquiryDetailsDTO> updateEnquiryDetails(@Valid @RequestBody EnquiryDetailsDTO enquiryDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update EnquiryDetails : {}", enquiryDetailsDTO);
        if (enquiryDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnquiryDetailsDTO result = enquiryDetailsService.save(enquiryDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enquiryDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enquiry-details} : get all the enquiryDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enquiryDetails in body.
     */
    @GetMapping("/enquiry-details")
    public ResponseEntity<List<EnquiryDetailsDTO>> getAllEnquiryDetails(EnquiryDetailsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EnquiryDetails by criteria: {}", criteria);
        Page<EnquiryDetailsDTO> page = enquiryDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enquiry-details/count} : count all the enquiryDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/enquiry-details/count")
    public ResponseEntity<Long> countEnquiryDetails(EnquiryDetailsCriteria criteria) {
        log.debug("REST request to count EnquiryDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(enquiryDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /enquiry-details/:id} : get the "id" enquiryDetails.
     *
     * @param id the id of the enquiryDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enquiryDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enquiry-details/{id}")
    public ResponseEntity<EnquiryDetailsDTO> getEnquiryDetails(@PathVariable Long id) {
        log.debug("REST request to get EnquiryDetails : {}", id);
        Optional<EnquiryDetailsDTO> enquiryDetailsDTO = enquiryDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enquiryDetailsDTO);
    }

    /**
     * {@code DELETE  /enquiry-details/:id} : delete the "id" enquiryDetails.
     *
     * @param id the id of the enquiryDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enquiry-details/{id}")
    public ResponseEntity<Void> deleteEnquiryDetails(@PathVariable Long id) {
        log.debug("REST request to delete EnquiryDetails : {}", id);
        enquiryDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
