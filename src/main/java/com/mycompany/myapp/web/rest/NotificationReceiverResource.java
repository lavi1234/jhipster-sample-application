package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.NotificationReceiverService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.NotificationReceiverDTO;
import com.mycompany.myapp.service.dto.NotificationReceiverCriteria;
import com.mycompany.myapp.service.NotificationReceiverQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.NotificationReceiver}.
 */
@RestController
@RequestMapping("/api")
public class NotificationReceiverResource {

    private final Logger log = LoggerFactory.getLogger(NotificationReceiverResource.class);

    private static final String ENTITY_NAME = "notificationReceiver";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotificationReceiverService notificationReceiverService;

    private final NotificationReceiverQueryService notificationReceiverQueryService;

    public NotificationReceiverResource(NotificationReceiverService notificationReceiverService, NotificationReceiverQueryService notificationReceiverQueryService) {
        this.notificationReceiverService = notificationReceiverService;
        this.notificationReceiverQueryService = notificationReceiverQueryService;
    }

    /**
     * {@code POST  /notification-receivers} : Create a new notificationReceiver.
     *
     * @param notificationReceiverDTO the notificationReceiverDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notificationReceiverDTO, or with status {@code 400 (Bad Request)} if the notificationReceiver has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notification-receivers")
    public ResponseEntity<NotificationReceiverDTO> createNotificationReceiver(@Valid @RequestBody NotificationReceiverDTO notificationReceiverDTO) throws URISyntaxException {
        log.debug("REST request to save NotificationReceiver : {}", notificationReceiverDTO);
        if (notificationReceiverDTO.getId() != null) {
            throw new BadRequestAlertException("A new notificationReceiver cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotificationReceiverDTO result = notificationReceiverService.save(notificationReceiverDTO);
        return ResponseEntity.created(new URI("/api/notification-receivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notification-receivers} : Updates an existing notificationReceiver.
     *
     * @param notificationReceiverDTO the notificationReceiverDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notificationReceiverDTO,
     * or with status {@code 400 (Bad Request)} if the notificationReceiverDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notificationReceiverDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notification-receivers")
    public ResponseEntity<NotificationReceiverDTO> updateNotificationReceiver(@Valid @RequestBody NotificationReceiverDTO notificationReceiverDTO) throws URISyntaxException {
        log.debug("REST request to update NotificationReceiver : {}", notificationReceiverDTO);
        if (notificationReceiverDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NotificationReceiverDTO result = notificationReceiverService.save(notificationReceiverDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notificationReceiverDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /notification-receivers} : get all the notificationReceivers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notificationReceivers in body.
     */
    @GetMapping("/notification-receivers")
    public ResponseEntity<List<NotificationReceiverDTO>> getAllNotificationReceivers(NotificationReceiverCriteria criteria, Pageable pageable) {
        log.debug("REST request to get NotificationReceivers by criteria: {}", criteria);
        Page<NotificationReceiverDTO> page = notificationReceiverQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /notification-receivers/count} : count all the notificationReceivers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/notification-receivers/count")
    public ResponseEntity<Long> countNotificationReceivers(NotificationReceiverCriteria criteria) {
        log.debug("REST request to count NotificationReceivers by criteria: {}", criteria);
        return ResponseEntity.ok().body(notificationReceiverQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /notification-receivers/:id} : get the "id" notificationReceiver.
     *
     * @param id the id of the notificationReceiverDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notificationReceiverDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notification-receivers/{id}")
    public ResponseEntity<NotificationReceiverDTO> getNotificationReceiver(@PathVariable Long id) {
        log.debug("REST request to get NotificationReceiver : {}", id);
        Optional<NotificationReceiverDTO> notificationReceiverDTO = notificationReceiverService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notificationReceiverDTO);
    }

    /**
     * {@code DELETE  /notification-receivers/:id} : delete the "id" notificationReceiver.
     *
     * @param id the id of the notificationReceiverDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notification-receivers/{id}")
    public ResponseEntity<Void> deleteNotificationReceiver(@PathVariable Long id) {
        log.debug("REST request to delete NotificationReceiver : {}", id);
        notificationReceiverService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
