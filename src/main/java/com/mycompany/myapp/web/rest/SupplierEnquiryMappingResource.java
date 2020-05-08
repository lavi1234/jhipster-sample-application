package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.SupplierEnquiryMappingService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.SupplierEnquiryMappingDTO;
import com.mycompany.myapp.service.dto.SupplierEnquiryMappingCriteria;
import com.mycompany.myapp.service.SupplierEnquiryMappingQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.SupplierEnquiryMapping}.
 */
@RestController
@RequestMapping("/api")
public class SupplierEnquiryMappingResource {

    private final Logger log = LoggerFactory.getLogger(SupplierEnquiryMappingResource.class);

    private static final String ENTITY_NAME = "supplierEnquiryMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplierEnquiryMappingService supplierEnquiryMappingService;

    private final SupplierEnquiryMappingQueryService supplierEnquiryMappingQueryService;

    public SupplierEnquiryMappingResource(SupplierEnquiryMappingService supplierEnquiryMappingService, SupplierEnquiryMappingQueryService supplierEnquiryMappingQueryService) {
        this.supplierEnquiryMappingService = supplierEnquiryMappingService;
        this.supplierEnquiryMappingQueryService = supplierEnquiryMappingQueryService;
    }

    /**
     * {@code POST  /supplier-enquiry-mappings} : Create a new supplierEnquiryMapping.
     *
     * @param supplierEnquiryMappingDTO the supplierEnquiryMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierEnquiryMappingDTO, or with status {@code 400 (Bad Request)} if the supplierEnquiryMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supplier-enquiry-mappings")
    public ResponseEntity<SupplierEnquiryMappingDTO> createSupplierEnquiryMapping(@Valid @RequestBody SupplierEnquiryMappingDTO supplierEnquiryMappingDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierEnquiryMapping : {}", supplierEnquiryMappingDTO);
        if (supplierEnquiryMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierEnquiryMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierEnquiryMappingDTO result = supplierEnquiryMappingService.save(supplierEnquiryMappingDTO);
        return ResponseEntity.created(new URI("/api/supplier-enquiry-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supplier-enquiry-mappings} : Updates an existing supplierEnquiryMapping.
     *
     * @param supplierEnquiryMappingDTO the supplierEnquiryMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierEnquiryMappingDTO,
     * or with status {@code 400 (Bad Request)} if the supplierEnquiryMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplierEnquiryMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supplier-enquiry-mappings")
    public ResponseEntity<SupplierEnquiryMappingDTO> updateSupplierEnquiryMapping(@Valid @RequestBody SupplierEnquiryMappingDTO supplierEnquiryMappingDTO) throws URISyntaxException {
        log.debug("REST request to update SupplierEnquiryMapping : {}", supplierEnquiryMappingDTO);
        if (supplierEnquiryMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupplierEnquiryMappingDTO result = supplierEnquiryMappingService.save(supplierEnquiryMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierEnquiryMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supplier-enquiry-mappings} : get all the supplierEnquiryMappings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierEnquiryMappings in body.
     */
    @GetMapping("/supplier-enquiry-mappings")
    public ResponseEntity<List<SupplierEnquiryMappingDTO>> getAllSupplierEnquiryMappings(SupplierEnquiryMappingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SupplierEnquiryMappings by criteria: {}", criteria);
        Page<SupplierEnquiryMappingDTO> page = supplierEnquiryMappingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supplier-enquiry-mappings/count} : count all the supplierEnquiryMappings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/supplier-enquiry-mappings/count")
    public ResponseEntity<Long> countSupplierEnquiryMappings(SupplierEnquiryMappingCriteria criteria) {
        log.debug("REST request to count SupplierEnquiryMappings by criteria: {}", criteria);
        return ResponseEntity.ok().body(supplierEnquiryMappingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /supplier-enquiry-mappings/:id} : get the "id" supplierEnquiryMapping.
     *
     * @param id the id of the supplierEnquiryMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierEnquiryMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supplier-enquiry-mappings/{id}")
    public ResponseEntity<SupplierEnquiryMappingDTO> getSupplierEnquiryMapping(@PathVariable Long id) {
        log.debug("REST request to get SupplierEnquiryMapping : {}", id);
        Optional<SupplierEnquiryMappingDTO> supplierEnquiryMappingDTO = supplierEnquiryMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplierEnquiryMappingDTO);
    }

    /**
     * {@code DELETE  /supplier-enquiry-mappings/:id} : delete the "id" supplierEnquiryMapping.
     *
     * @param id the id of the supplierEnquiryMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supplier-enquiry-mappings/{id}")
    public ResponseEntity<Void> deleteSupplierEnquiryMapping(@PathVariable Long id) {
        log.debug("REST request to delete SupplierEnquiryMapping : {}", id);
        supplierEnquiryMappingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
