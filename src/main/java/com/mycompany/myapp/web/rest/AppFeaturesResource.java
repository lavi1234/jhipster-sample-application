package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.AppFeaturesService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.AppFeaturesDTO;
import com.mycompany.myapp.service.dto.AppFeaturesCriteria;
import com.mycompany.myapp.service.AppFeaturesQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.AppFeatures}.
 */
@RestController
@RequestMapping("/api")
public class AppFeaturesResource {

    private final Logger log = LoggerFactory.getLogger(AppFeaturesResource.class);

    private static final String ENTITY_NAME = "appFeatures";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AppFeaturesService appFeaturesService;

    private final AppFeaturesQueryService appFeaturesQueryService;

    public AppFeaturesResource(AppFeaturesService appFeaturesService, AppFeaturesQueryService appFeaturesQueryService) {
        this.appFeaturesService = appFeaturesService;
        this.appFeaturesQueryService = appFeaturesQueryService;
    }

    /**
     * {@code POST  /app-features} : Create a new appFeatures.
     *
     * @param appFeaturesDTO the appFeaturesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new appFeaturesDTO, or with status {@code 400 (Bad Request)} if the appFeatures has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/app-features")
    public ResponseEntity<AppFeaturesDTO> createAppFeatures(@Valid @RequestBody AppFeaturesDTO appFeaturesDTO) throws URISyntaxException {
        log.debug("REST request to save AppFeatures : {}", appFeaturesDTO);
        if (appFeaturesDTO.getId() != null) {
            throw new BadRequestAlertException("A new appFeatures cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AppFeaturesDTO result = appFeaturesService.save(appFeaturesDTO);
        return ResponseEntity.created(new URI("/api/app-features/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /app-features} : Updates an existing appFeatures.
     *
     * @param appFeaturesDTO the appFeaturesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated appFeaturesDTO,
     * or with status {@code 400 (Bad Request)} if the appFeaturesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the appFeaturesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/app-features")
    public ResponseEntity<AppFeaturesDTO> updateAppFeatures(@Valid @RequestBody AppFeaturesDTO appFeaturesDTO) throws URISyntaxException {
        log.debug("REST request to update AppFeatures : {}", appFeaturesDTO);
        if (appFeaturesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AppFeaturesDTO result = appFeaturesService.save(appFeaturesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, appFeaturesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /app-features} : get all the appFeatures.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of appFeatures in body.
     */
    @GetMapping("/app-features")
    public ResponseEntity<List<AppFeaturesDTO>> getAllAppFeatures(AppFeaturesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get AppFeatures by criteria: {}", criteria);
        Page<AppFeaturesDTO> page = appFeaturesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /app-features/count} : count all the appFeatures.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/app-features/count")
    public ResponseEntity<Long> countAppFeatures(AppFeaturesCriteria criteria) {
        log.debug("REST request to count AppFeatures by criteria: {}", criteria);
        return ResponseEntity.ok().body(appFeaturesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /app-features/:id} : get the "id" appFeatures.
     *
     * @param id the id of the appFeaturesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the appFeaturesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/app-features/{id}")
    public ResponseEntity<AppFeaturesDTO> getAppFeatures(@PathVariable Long id) {
        log.debug("REST request to get AppFeatures : {}", id);
        Optional<AppFeaturesDTO> appFeaturesDTO = appFeaturesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(appFeaturesDTO);
    }

    /**
     * {@code DELETE  /app-features/:id} : delete the "id" appFeatures.
     *
     * @param id the id of the appFeaturesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/app-features/{id}")
    public ResponseEntity<Void> deleteAppFeatures(@PathVariable Long id) {
        log.debug("REST request to delete AppFeatures : {}", id);
        appFeaturesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
