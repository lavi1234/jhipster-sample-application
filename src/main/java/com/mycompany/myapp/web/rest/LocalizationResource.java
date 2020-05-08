package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.LocalizationService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.LocalizationDTO;
import com.mycompany.myapp.service.dto.LocalizationCriteria;
import com.mycompany.myapp.service.LocalizationQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Localization}.
 */
@RestController
@RequestMapping("/api")
public class LocalizationResource {

    private final Logger log = LoggerFactory.getLogger(LocalizationResource.class);

    private static final String ENTITY_NAME = "localization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalizationService localizationService;

    private final LocalizationQueryService localizationQueryService;

    public LocalizationResource(LocalizationService localizationService, LocalizationQueryService localizationQueryService) {
        this.localizationService = localizationService;
        this.localizationQueryService = localizationQueryService;
    }

    /**
     * {@code POST  /localizations} : Create a new localization.
     *
     * @param localizationDTO the localizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localizationDTO, or with status {@code 400 (Bad Request)} if the localization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/localizations")
    public ResponseEntity<LocalizationDTO> createLocalization(@Valid @RequestBody LocalizationDTO localizationDTO) throws URISyntaxException {
        log.debug("REST request to save Localization : {}", localizationDTO);
        if (localizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new localization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalizationDTO result = localizationService.save(localizationDTO);
        return ResponseEntity.created(new URI("/api/localizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /localizations} : Updates an existing localization.
     *
     * @param localizationDTO the localizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localizationDTO,
     * or with status {@code 400 (Bad Request)} if the localizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/localizations")
    public ResponseEntity<LocalizationDTO> updateLocalization(@Valid @RequestBody LocalizationDTO localizationDTO) throws URISyntaxException {
        log.debug("REST request to update Localization : {}", localizationDTO);
        if (localizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocalizationDTO result = localizationService.save(localizationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /localizations} : get all the localizations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localizations in body.
     */
    @GetMapping("/localizations")
    public ResponseEntity<List<LocalizationDTO>> getAllLocalizations(LocalizationCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Localizations by criteria: {}", criteria);
        Page<LocalizationDTO> page = localizationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /localizations/count} : count all the localizations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/localizations/count")
    public ResponseEntity<Long> countLocalizations(LocalizationCriteria criteria) {
        log.debug("REST request to count Localizations by criteria: {}", criteria);
        return ResponseEntity.ok().body(localizationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /localizations/:id} : get the "id" localization.
     *
     * @param id the id of the localizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/localizations/{id}")
    public ResponseEntity<LocalizationDTO> getLocalization(@PathVariable Long id) {
        log.debug("REST request to get Localization : {}", id);
        Optional<LocalizationDTO> localizationDTO = localizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(localizationDTO);
    }

    /**
     * {@code DELETE  /localizations/:id} : delete the "id" localization.
     *
     * @param id the id of the localizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/localizations/{id}")
    public ResponseEntity<Void> deleteLocalization(@PathVariable Long id) {
        log.debug("REST request to delete Localization : {}", id);
        localizationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
