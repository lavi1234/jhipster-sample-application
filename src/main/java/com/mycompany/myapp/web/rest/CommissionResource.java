package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CommissionService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.CommissionDTO;
import com.mycompany.myapp.service.dto.CommissionCriteria;
import com.mycompany.myapp.service.CommissionQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Commission}.
 */
@RestController
@RequestMapping("/api")
public class CommissionResource {

    private final Logger log = LoggerFactory.getLogger(CommissionResource.class);

    private static final String ENTITY_NAME = "commission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommissionService commissionService;

    private final CommissionQueryService commissionQueryService;

    public CommissionResource(CommissionService commissionService, CommissionQueryService commissionQueryService) {
        this.commissionService = commissionService;
        this.commissionQueryService = commissionQueryService;
    }

    /**
     * {@code POST  /commissions} : Create a new commission.
     *
     * @param commissionDTO the commissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commissionDTO, or with status {@code 400 (Bad Request)} if the commission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commissions")
    public ResponseEntity<CommissionDTO> createCommission(@Valid @RequestBody CommissionDTO commissionDTO) throws URISyntaxException {
        log.debug("REST request to save Commission : {}", commissionDTO);
        if (commissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new commission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommissionDTO result = commissionService.save(commissionDTO);
        return ResponseEntity.created(new URI("/api/commissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commissions} : Updates an existing commission.
     *
     * @param commissionDTO the commissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commissionDTO,
     * or with status {@code 400 (Bad Request)} if the commissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commissions")
    public ResponseEntity<CommissionDTO> updateCommission(@Valid @RequestBody CommissionDTO commissionDTO) throws URISyntaxException {
        log.debug("REST request to update Commission : {}", commissionDTO);
        if (commissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommissionDTO result = commissionService.save(commissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commissions} : get all the commissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commissions in body.
     */
    @GetMapping("/commissions")
    public ResponseEntity<List<CommissionDTO>> getAllCommissions(CommissionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Commissions by criteria: {}", criteria);
        Page<CommissionDTO> page = commissionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /commissions/count} : count all the commissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/commissions/count")
    public ResponseEntity<Long> countCommissions(CommissionCriteria criteria) {
        log.debug("REST request to count Commissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(commissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /commissions/:id} : get the "id" commission.
     *
     * @param id the id of the commissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commissions/{id}")
    public ResponseEntity<CommissionDTO> getCommission(@PathVariable Long id) {
        log.debug("REST request to get Commission : {}", id);
        Optional<CommissionDTO> commissionDTO = commissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commissionDTO);
    }

    /**
     * {@code DELETE  /commissions/:id} : delete the "id" commission.
     *
     * @param id the id of the commissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commissions/{id}")
    public ResponseEntity<Void> deleteCommission(@PathVariable Long id) {
        log.debug("REST request to delete Commission : {}", id);
        commissionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
