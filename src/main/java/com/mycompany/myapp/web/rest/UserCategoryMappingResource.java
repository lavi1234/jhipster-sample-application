package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.UserCategoryMappingService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.UserCategoryMappingDTO;
import com.mycompany.myapp.service.dto.UserCategoryMappingCriteria;
import com.mycompany.myapp.service.UserCategoryMappingQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.UserCategoryMapping}.
 */
@RestController
@RequestMapping("/api")
public class UserCategoryMappingResource {

    private final Logger log = LoggerFactory.getLogger(UserCategoryMappingResource.class);

    private static final String ENTITY_NAME = "userCategoryMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserCategoryMappingService userCategoryMappingService;

    private final UserCategoryMappingQueryService userCategoryMappingQueryService;

    public UserCategoryMappingResource(UserCategoryMappingService userCategoryMappingService, UserCategoryMappingQueryService userCategoryMappingQueryService) {
        this.userCategoryMappingService = userCategoryMappingService;
        this.userCategoryMappingQueryService = userCategoryMappingQueryService;
    }

    /**
     * {@code POST  /user-category-mappings} : Create a new userCategoryMapping.
     *
     * @param userCategoryMappingDTO the userCategoryMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userCategoryMappingDTO, or with status {@code 400 (Bad Request)} if the userCategoryMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-category-mappings")
    public ResponseEntity<UserCategoryMappingDTO> createUserCategoryMapping(@Valid @RequestBody UserCategoryMappingDTO userCategoryMappingDTO) throws URISyntaxException {
        log.debug("REST request to save UserCategoryMapping : {}", userCategoryMappingDTO);
        if (userCategoryMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new userCategoryMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserCategoryMappingDTO result = userCategoryMappingService.save(userCategoryMappingDTO);
        return ResponseEntity.created(new URI("/api/user-category-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-category-mappings} : Updates an existing userCategoryMapping.
     *
     * @param userCategoryMappingDTO the userCategoryMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userCategoryMappingDTO,
     * or with status {@code 400 (Bad Request)} if the userCategoryMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userCategoryMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-category-mappings")
    public ResponseEntity<UserCategoryMappingDTO> updateUserCategoryMapping(@Valid @RequestBody UserCategoryMappingDTO userCategoryMappingDTO) throws URISyntaxException {
        log.debug("REST request to update UserCategoryMapping : {}", userCategoryMappingDTO);
        if (userCategoryMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserCategoryMappingDTO result = userCategoryMappingService.save(userCategoryMappingDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userCategoryMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-category-mappings} : get all the userCategoryMappings.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userCategoryMappings in body.
     */
    @GetMapping("/user-category-mappings")
    public ResponseEntity<List<UserCategoryMappingDTO>> getAllUserCategoryMappings(UserCategoryMappingCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserCategoryMappings by criteria: {}", criteria);
        Page<UserCategoryMappingDTO> page = userCategoryMappingQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-category-mappings/count} : count all the userCategoryMappings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-category-mappings/count")
    public ResponseEntity<Long> countUserCategoryMappings(UserCategoryMappingCriteria criteria) {
        log.debug("REST request to count UserCategoryMappings by criteria: {}", criteria);
        return ResponseEntity.ok().body(userCategoryMappingQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-category-mappings/:id} : get the "id" userCategoryMapping.
     *
     * @param id the id of the userCategoryMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userCategoryMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-category-mappings/{id}")
    public ResponseEntity<UserCategoryMappingDTO> getUserCategoryMapping(@PathVariable Long id) {
        log.debug("REST request to get UserCategoryMapping : {}", id);
        Optional<UserCategoryMappingDTO> userCategoryMappingDTO = userCategoryMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userCategoryMappingDTO);
    }

    /**
     * {@code DELETE  /user-category-mappings/:id} : delete the "id" userCategoryMapping.
     *
     * @param id the id of the userCategoryMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-category-mappings/{id}")
    public ResponseEntity<Void> deleteUserCategoryMapping(@PathVariable Long id) {
        log.debug("REST request to delete UserCategoryMapping : {}", id);
        userCategoryMappingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
