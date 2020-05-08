package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.UserSubscriptionService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.UserSubscriptionDTO;
import com.mycompany.myapp.service.dto.UserSubscriptionCriteria;
import com.mycompany.myapp.service.UserSubscriptionQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.UserSubscription}.
 */
@RestController
@RequestMapping("/api")
public class UserSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(UserSubscriptionResource.class);

    private static final String ENTITY_NAME = "userSubscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserSubscriptionService userSubscriptionService;

    private final UserSubscriptionQueryService userSubscriptionQueryService;

    public UserSubscriptionResource(UserSubscriptionService userSubscriptionService, UserSubscriptionQueryService userSubscriptionQueryService) {
        this.userSubscriptionService = userSubscriptionService;
        this.userSubscriptionQueryService = userSubscriptionQueryService;
    }

    /**
     * {@code POST  /user-subscriptions} : Create a new userSubscription.
     *
     * @param userSubscriptionDTO the userSubscriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userSubscriptionDTO, or with status {@code 400 (Bad Request)} if the userSubscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-subscriptions")
    public ResponseEntity<UserSubscriptionDTO> createUserSubscription(@Valid @RequestBody UserSubscriptionDTO userSubscriptionDTO) throws URISyntaxException {
        log.debug("REST request to save UserSubscription : {}", userSubscriptionDTO);
        if (userSubscriptionDTO.getId() != null) {
            throw new BadRequestAlertException("A new userSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserSubscriptionDTO result = userSubscriptionService.save(userSubscriptionDTO);
        return ResponseEntity.created(new URI("/api/user-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-subscriptions} : Updates an existing userSubscription.
     *
     * @param userSubscriptionDTO the userSubscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSubscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the userSubscriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userSubscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-subscriptions")
    public ResponseEntity<UserSubscriptionDTO> updateUserSubscription(@Valid @RequestBody UserSubscriptionDTO userSubscriptionDTO) throws URISyntaxException {
        log.debug("REST request to update UserSubscription : {}", userSubscriptionDTO);
        if (userSubscriptionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserSubscriptionDTO result = userSubscriptionService.save(userSubscriptionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userSubscriptionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-subscriptions} : get all the userSubscriptions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userSubscriptions in body.
     */
    @GetMapping("/user-subscriptions")
    public ResponseEntity<List<UserSubscriptionDTO>> getAllUserSubscriptions(UserSubscriptionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get UserSubscriptions by criteria: {}", criteria);
        Page<UserSubscriptionDTO> page = userSubscriptionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-subscriptions/count} : count all the userSubscriptions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/user-subscriptions/count")
    public ResponseEntity<Long> countUserSubscriptions(UserSubscriptionCriteria criteria) {
        log.debug("REST request to count UserSubscriptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(userSubscriptionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /user-subscriptions/:id} : get the "id" userSubscription.
     *
     * @param id the id of the userSubscriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userSubscriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-subscriptions/{id}")
    public ResponseEntity<UserSubscriptionDTO> getUserSubscription(@PathVariable Long id) {
        log.debug("REST request to get UserSubscription : {}", id);
        Optional<UserSubscriptionDTO> userSubscriptionDTO = userSubscriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userSubscriptionDTO);
    }

    /**
     * {@code DELETE  /user-subscriptions/:id} : delete the "id" userSubscription.
     *
     * @param id the id of the userSubscriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-subscriptions/{id}")
    public ResponseEntity<Void> deleteUserSubscription(@PathVariable Long id) {
        log.debug("REST request to delete UserSubscription : {}", id);
        userSubscriptionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
