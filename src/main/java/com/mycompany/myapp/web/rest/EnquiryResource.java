package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.EnquiryService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.EnquiryDTO;
import com.mycompany.myapp.service.dto.EnquiryCriteria;
import com.mycompany.myapp.service.EnquiryQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Enquiry}.
 */
@RestController
@RequestMapping("/api")
public class EnquiryResource {

    private final Logger log = LoggerFactory.getLogger(EnquiryResource.class);

    private static final String ENTITY_NAME = "enquiry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnquiryService enquiryService;

    private final EnquiryQueryService enquiryQueryService;

    public EnquiryResource(EnquiryService enquiryService, EnquiryQueryService enquiryQueryService) {
        this.enquiryService = enquiryService;
        this.enquiryQueryService = enquiryQueryService;
    }

    /**
     * {@code POST  /enquiries} : Create a new enquiry.
     *
     * @param enquiryDTO the enquiryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enquiryDTO, or with status {@code 400 (Bad Request)} if the enquiry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enquiries")
    public ResponseEntity<EnquiryDTO> createEnquiry(@Valid @RequestBody EnquiryDTO enquiryDTO) throws URISyntaxException {
        log.debug("REST request to save Enquiry : {}", enquiryDTO);
        if (enquiryDTO.getId() != null) {
            throw new BadRequestAlertException("A new enquiry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnquiryDTO result = enquiryService.save(enquiryDTO);
        return ResponseEntity.created(new URI("/api/enquiries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enquiries} : Updates an existing enquiry.
     *
     * @param enquiryDTO the enquiryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enquiryDTO,
     * or with status {@code 400 (Bad Request)} if the enquiryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enquiryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enquiries")
    public ResponseEntity<EnquiryDTO> updateEnquiry(@Valid @RequestBody EnquiryDTO enquiryDTO) throws URISyntaxException {
        log.debug("REST request to update Enquiry : {}", enquiryDTO);
        if (enquiryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnquiryDTO result = enquiryService.save(enquiryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enquiryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enquiries} : get all the enquiries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enquiries in body.
     */
    @GetMapping("/enquiries")
    public ResponseEntity<List<EnquiryDTO>> getAllEnquiries(EnquiryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Enquiries by criteria: {}", criteria);
        Page<EnquiryDTO> page = enquiryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enquiries/count} : count all the enquiries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/enquiries/count")
    public ResponseEntity<Long> countEnquiries(EnquiryCriteria criteria) {
        log.debug("REST request to count Enquiries by criteria: {}", criteria);
        return ResponseEntity.ok().body(enquiryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /enquiries/:id} : get the "id" enquiry.
     *
     * @param id the id of the enquiryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enquiryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enquiries/{id}")
    public ResponseEntity<EnquiryDTO> getEnquiry(@PathVariable Long id) {
        log.debug("REST request to get Enquiry : {}", id);
        Optional<EnquiryDTO> enquiryDTO = enquiryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enquiryDTO);
    }

    /**
     * {@code DELETE  /enquiries/:id} : delete the "id" enquiry.
     *
     * @param id the id of the enquiryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enquiries/{id}")
    public ResponseEntity<Void> deleteEnquiry(@PathVariable Long id) {
        log.debug("REST request to delete Enquiry : {}", id);
        enquiryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
