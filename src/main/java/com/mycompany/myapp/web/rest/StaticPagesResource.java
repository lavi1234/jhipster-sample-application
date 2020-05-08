package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.StaticPagesService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.StaticPagesDTO;
import com.mycompany.myapp.service.dto.StaticPagesCriteria;
import com.mycompany.myapp.service.StaticPagesQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.StaticPages}.
 */
@RestController
@RequestMapping("/api")
public class StaticPagesResource {

    private final Logger log = LoggerFactory.getLogger(StaticPagesResource.class);

    private static final String ENTITY_NAME = "staticPages";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaticPagesService staticPagesService;

    private final StaticPagesQueryService staticPagesQueryService;

    public StaticPagesResource(StaticPagesService staticPagesService, StaticPagesQueryService staticPagesQueryService) {
        this.staticPagesService = staticPagesService;
        this.staticPagesQueryService = staticPagesQueryService;
    }

    /**
     * {@code POST  /static-pages} : Create a new staticPages.
     *
     * @param staticPagesDTO the staticPagesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new staticPagesDTO, or with status {@code 400 (Bad Request)} if the staticPages has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/static-pages")
    public ResponseEntity<StaticPagesDTO> createStaticPages(@Valid @RequestBody StaticPagesDTO staticPagesDTO) throws URISyntaxException {
        log.debug("REST request to save StaticPages : {}", staticPagesDTO);
        if (staticPagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new staticPages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaticPagesDTO result = staticPagesService.save(staticPagesDTO);
        return ResponseEntity.created(new URI("/api/static-pages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /static-pages} : Updates an existing staticPages.
     *
     * @param staticPagesDTO the staticPagesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated staticPagesDTO,
     * or with status {@code 400 (Bad Request)} if the staticPagesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the staticPagesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/static-pages")
    public ResponseEntity<StaticPagesDTO> updateStaticPages(@Valid @RequestBody StaticPagesDTO staticPagesDTO) throws URISyntaxException {
        log.debug("REST request to update StaticPages : {}", staticPagesDTO);
        if (staticPagesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaticPagesDTO result = staticPagesService.save(staticPagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, staticPagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /static-pages} : get all the staticPages.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staticPages in body.
     */
    @GetMapping("/static-pages")
    public ResponseEntity<List<StaticPagesDTO>> getAllStaticPages(StaticPagesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StaticPages by criteria: {}", criteria);
        Page<StaticPagesDTO> page = staticPagesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /static-pages/count} : count all the staticPages.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/static-pages/count")
    public ResponseEntity<Long> countStaticPages(StaticPagesCriteria criteria) {
        log.debug("REST request to count StaticPages by criteria: {}", criteria);
        return ResponseEntity.ok().body(staticPagesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /static-pages/:id} : get the "id" staticPages.
     *
     * @param id the id of the staticPagesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staticPagesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/static-pages/{id}")
    public ResponseEntity<StaticPagesDTO> getStaticPages(@PathVariable Long id) {
        log.debug("REST request to get StaticPages : {}", id);
        Optional<StaticPagesDTO> staticPagesDTO = staticPagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(staticPagesDTO);
    }

    /**
     * {@code DELETE  /static-pages/:id} : delete the "id" staticPages.
     *
     * @param id the id of the staticPagesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/static-pages/{id}")
    public ResponseEntity<Void> deleteStaticPages(@PathVariable Long id) {
        log.debug("REST request to delete StaticPages : {}", id);
        staticPagesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
