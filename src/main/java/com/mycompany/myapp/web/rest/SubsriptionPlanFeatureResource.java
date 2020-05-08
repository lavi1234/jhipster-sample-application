package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.SubsriptionPlanFeatureService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.SubsriptionPlanFeatureDTO;
import com.mycompany.myapp.service.dto.SubsriptionPlanFeatureCriteria;
import com.mycompany.myapp.service.SubsriptionPlanFeatureQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.SubsriptionPlanFeature}.
 */
@RestController
@RequestMapping("/api")
public class SubsriptionPlanFeatureResource {

    private final Logger log = LoggerFactory.getLogger(SubsriptionPlanFeatureResource.class);

    private static final String ENTITY_NAME = "subsriptionPlanFeature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubsriptionPlanFeatureService subsriptionPlanFeatureService;

    private final SubsriptionPlanFeatureQueryService subsriptionPlanFeatureQueryService;

    public SubsriptionPlanFeatureResource(SubsriptionPlanFeatureService subsriptionPlanFeatureService, SubsriptionPlanFeatureQueryService subsriptionPlanFeatureQueryService) {
        this.subsriptionPlanFeatureService = subsriptionPlanFeatureService;
        this.subsriptionPlanFeatureQueryService = subsriptionPlanFeatureQueryService;
    }

    /**
     * {@code POST  /subsription-plan-features} : Create a new subsriptionPlanFeature.
     *
     * @param subsriptionPlanFeatureDTO the subsriptionPlanFeatureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subsriptionPlanFeatureDTO, or with status {@code 400 (Bad Request)} if the subsriptionPlanFeature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subsription-plan-features")
    public ResponseEntity<SubsriptionPlanFeatureDTO> createSubsriptionPlanFeature(@Valid @RequestBody SubsriptionPlanFeatureDTO subsriptionPlanFeatureDTO) throws URISyntaxException {
        log.debug("REST request to save SubsriptionPlanFeature : {}", subsriptionPlanFeatureDTO);
        if (subsriptionPlanFeatureDTO.getId() != null) {
            throw new BadRequestAlertException("A new subsriptionPlanFeature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubsriptionPlanFeatureDTO result = subsriptionPlanFeatureService.save(subsriptionPlanFeatureDTO);
        return ResponseEntity.created(new URI("/api/subsription-plan-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subsription-plan-features} : Updates an existing subsriptionPlanFeature.
     *
     * @param subsriptionPlanFeatureDTO the subsriptionPlanFeatureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subsriptionPlanFeatureDTO,
     * or with status {@code 400 (Bad Request)} if the subsriptionPlanFeatureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subsriptionPlanFeatureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subsription-plan-features")
    public ResponseEntity<SubsriptionPlanFeatureDTO> updateSubsriptionPlanFeature(@Valid @RequestBody SubsriptionPlanFeatureDTO subsriptionPlanFeatureDTO) throws URISyntaxException {
        log.debug("REST request to update SubsriptionPlanFeature : {}", subsriptionPlanFeatureDTO);
        if (subsriptionPlanFeatureDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SubsriptionPlanFeatureDTO result = subsriptionPlanFeatureService.save(subsriptionPlanFeatureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subsriptionPlanFeatureDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /subsription-plan-features} : get all the subsriptionPlanFeatures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subsriptionPlanFeatures in body.
     */
    @GetMapping("/subsription-plan-features")
    public ResponseEntity<List<SubsriptionPlanFeatureDTO>> getAllSubsriptionPlanFeatures(SubsriptionPlanFeatureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SubsriptionPlanFeatures by criteria: {}", criteria);
        Page<SubsriptionPlanFeatureDTO> page = subsriptionPlanFeatureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /subsription-plan-features/count} : count all the subsriptionPlanFeatures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/subsription-plan-features/count")
    public ResponseEntity<Long> countSubsriptionPlanFeatures(SubsriptionPlanFeatureCriteria criteria) {
        log.debug("REST request to count SubsriptionPlanFeatures by criteria: {}", criteria);
        return ResponseEntity.ok().body(subsriptionPlanFeatureQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /subsription-plan-features/:id} : get the "id" subsriptionPlanFeature.
     *
     * @param id the id of the subsriptionPlanFeatureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subsriptionPlanFeatureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subsription-plan-features/{id}")
    public ResponseEntity<SubsriptionPlanFeatureDTO> getSubsriptionPlanFeature(@PathVariable Long id) {
        log.debug("REST request to get SubsriptionPlanFeature : {}", id);
        Optional<SubsriptionPlanFeatureDTO> subsriptionPlanFeatureDTO = subsriptionPlanFeatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subsriptionPlanFeatureDTO);
    }

    /**
     * {@code DELETE  /subsription-plan-features/:id} : delete the "id" subsriptionPlanFeature.
     *
     * @param id the id of the subsriptionPlanFeatureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subsription-plan-features/{id}")
    public ResponseEntity<Void> deleteSubsriptionPlanFeature(@PathVariable Long id) {
        log.debug("REST request to delete SubsriptionPlanFeature : {}", id);
        subsriptionPlanFeatureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
