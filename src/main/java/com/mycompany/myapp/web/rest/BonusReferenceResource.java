package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.BonusReferenceService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.BonusReferenceDTO;
import com.mycompany.myapp.service.dto.BonusReferenceCriteria;
import com.mycompany.myapp.service.BonusReferenceQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.BonusReference}.
 */
@RestController
@RequestMapping("/api")
public class BonusReferenceResource {

    private final Logger log = LoggerFactory.getLogger(BonusReferenceResource.class);

    private static final String ENTITY_NAME = "bonusReference";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BonusReferenceService bonusReferenceService;

    private final BonusReferenceQueryService bonusReferenceQueryService;

    public BonusReferenceResource(BonusReferenceService bonusReferenceService, BonusReferenceQueryService bonusReferenceQueryService) {
        this.bonusReferenceService = bonusReferenceService;
        this.bonusReferenceQueryService = bonusReferenceQueryService;
    }

    /**
     * {@code POST  /bonus-references} : Create a new bonusReference.
     *
     * @param bonusReferenceDTO the bonusReferenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bonusReferenceDTO, or with status {@code 400 (Bad Request)} if the bonusReference has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bonus-references")
    public ResponseEntity<BonusReferenceDTO> createBonusReference(@Valid @RequestBody BonusReferenceDTO bonusReferenceDTO) throws URISyntaxException {
        log.debug("REST request to save BonusReference : {}", bonusReferenceDTO);
        if (bonusReferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new bonusReference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BonusReferenceDTO result = bonusReferenceService.save(bonusReferenceDTO);
        return ResponseEntity.created(new URI("/api/bonus-references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bonus-references} : Updates an existing bonusReference.
     *
     * @param bonusReferenceDTO the bonusReferenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bonusReferenceDTO,
     * or with status {@code 400 (Bad Request)} if the bonusReferenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bonusReferenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bonus-references")
    public ResponseEntity<BonusReferenceDTO> updateBonusReference(@Valid @RequestBody BonusReferenceDTO bonusReferenceDTO) throws URISyntaxException {
        log.debug("REST request to update BonusReference : {}", bonusReferenceDTO);
        if (bonusReferenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BonusReferenceDTO result = bonusReferenceService.save(bonusReferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bonusReferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bonus-references} : get all the bonusReferences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bonusReferences in body.
     */
    @GetMapping("/bonus-references")
    public ResponseEntity<List<BonusReferenceDTO>> getAllBonusReferences(BonusReferenceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BonusReferences by criteria: {}", criteria);
        Page<BonusReferenceDTO> page = bonusReferenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bonus-references/count} : count all the bonusReferences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/bonus-references/count")
    public ResponseEntity<Long> countBonusReferences(BonusReferenceCriteria criteria) {
        log.debug("REST request to count BonusReferences by criteria: {}", criteria);
        return ResponseEntity.ok().body(bonusReferenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /bonus-references/:id} : get the "id" bonusReference.
     *
     * @param id the id of the bonusReferenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bonusReferenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bonus-references/{id}")
    public ResponseEntity<BonusReferenceDTO> getBonusReference(@PathVariable Long id) {
        log.debug("REST request to get BonusReference : {}", id);
        Optional<BonusReferenceDTO> bonusReferenceDTO = bonusReferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bonusReferenceDTO);
    }

    /**
     * {@code DELETE  /bonus-references/:id} : delete the "id" bonusReference.
     *
     * @param id the id of the bonusReferenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bonus-references/{id}")
    public ResponseEntity<Void> deleteBonusReference(@PathVariable Long id) {
        log.debug("REST request to delete BonusReference : {}", id);
        bonusReferenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
