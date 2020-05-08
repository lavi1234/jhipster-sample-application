package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CommissionReferenceService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.CommissionReferenceDTO;
import com.mycompany.myapp.service.dto.CommissionReferenceCriteria;
import com.mycompany.myapp.service.CommissionReferenceQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.CommissionReference}.
 */
@RestController
@RequestMapping("/api")
public class CommissionReferenceResource {

    private final Logger log = LoggerFactory.getLogger(CommissionReferenceResource.class);

    private static final String ENTITY_NAME = "commissionReference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommissionReferenceService commissionReferenceService;

    private final CommissionReferenceQueryService commissionReferenceQueryService;

    public CommissionReferenceResource(CommissionReferenceService commissionReferenceService, CommissionReferenceQueryService commissionReferenceQueryService) {
        this.commissionReferenceService = commissionReferenceService;
        this.commissionReferenceQueryService = commissionReferenceQueryService;
    }

    /**
     * {@code POST  /commission-references} : Create a new commissionReference.
     *
     * @param commissionReferenceDTO the commissionReferenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commissionReferenceDTO, or with status {@code 400 (Bad Request)} if the commissionReference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commission-references")
    public ResponseEntity<CommissionReferenceDTO> createCommissionReference(@Valid @RequestBody CommissionReferenceDTO commissionReferenceDTO) throws URISyntaxException {
        log.debug("REST request to save CommissionReference : {}", commissionReferenceDTO);
        if (commissionReferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new commissionReference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommissionReferenceDTO result = commissionReferenceService.save(commissionReferenceDTO);
        return ResponseEntity.created(new URI("/api/commission-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commission-references} : Updates an existing commissionReference.
     *
     * @param commissionReferenceDTO the commissionReferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commissionReferenceDTO,
     * or with status {@code 400 (Bad Request)} if the commissionReferenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commissionReferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commission-references")
    public ResponseEntity<CommissionReferenceDTO> updateCommissionReference(@Valid @RequestBody CommissionReferenceDTO commissionReferenceDTO) throws URISyntaxException {
        log.debug("REST request to update CommissionReference : {}", commissionReferenceDTO);
        if (commissionReferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommissionReferenceDTO result = commissionReferenceService.save(commissionReferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commissionReferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commission-references} : get all the commissionReferences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commissionReferences in body.
     */
    @GetMapping("/commission-references")
    public ResponseEntity<List<CommissionReferenceDTO>> getAllCommissionReferences(CommissionReferenceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CommissionReferences by criteria: {}", criteria);
        Page<CommissionReferenceDTO> page = commissionReferenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commission-references/count} : count all the commissionReferences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/commission-references/count")
    public ResponseEntity<Long> countCommissionReferences(CommissionReferenceCriteria criteria) {
        log.debug("REST request to count CommissionReferences by criteria: {}", criteria);
        return ResponseEntity.ok().body(commissionReferenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /commission-references/:id} : get the "id" commissionReference.
     *
     * @param id the id of the commissionReferenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commissionReferenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commission-references/{id}")
    public ResponseEntity<CommissionReferenceDTO> getCommissionReference(@PathVariable Long id) {
        log.debug("REST request to get CommissionReference : {}", id);
        Optional<CommissionReferenceDTO> commissionReferenceDTO = commissionReferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commissionReferenceDTO);
    }

    /**
     * {@code DELETE  /commission-references/:id} : delete the "id" commissionReference.
     *
     * @param id the id of the commissionReferenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commission-references/{id}")
    public ResponseEntity<Void> deleteCommissionReference(@PathVariable Long id) {
        log.debug("REST request to delete CommissionReference : {}", id);
        commissionReferenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
