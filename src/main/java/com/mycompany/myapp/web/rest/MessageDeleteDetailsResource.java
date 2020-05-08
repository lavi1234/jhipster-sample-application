package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.MessageDeleteDetailsService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.MessageDeleteDetailsDTO;
import com.mycompany.myapp.service.dto.MessageDeleteDetailsCriteria;
import com.mycompany.myapp.service.MessageDeleteDetailsQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.MessageDeleteDetails}.
 */
@RestController
@RequestMapping("/api")
public class MessageDeleteDetailsResource {

    private final Logger log = LoggerFactory.getLogger(MessageDeleteDetailsResource.class);

    private static final String ENTITY_NAME = "messageDeleteDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageDeleteDetailsService messageDeleteDetailsService;

    private final MessageDeleteDetailsQueryService messageDeleteDetailsQueryService;

    public MessageDeleteDetailsResource(MessageDeleteDetailsService messageDeleteDetailsService, MessageDeleteDetailsQueryService messageDeleteDetailsQueryService) {
        this.messageDeleteDetailsService = messageDeleteDetailsService;
        this.messageDeleteDetailsQueryService = messageDeleteDetailsQueryService;
    }

    /**
     * {@code POST  /message-delete-details} : Create a new messageDeleteDetails.
     *
     * @param messageDeleteDetailsDTO the messageDeleteDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageDeleteDetailsDTO, or with status {@code 400 (Bad Request)} if the messageDeleteDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-delete-details")
    public ResponseEntity<MessageDeleteDetailsDTO> createMessageDeleteDetails(@Valid @RequestBody MessageDeleteDetailsDTO messageDeleteDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save MessageDeleteDetails : {}", messageDeleteDetailsDTO);
        if (messageDeleteDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new messageDeleteDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageDeleteDetailsDTO result = messageDeleteDetailsService.save(messageDeleteDetailsDTO);
        return ResponseEntity.created(new URI("/api/message-delete-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-delete-details} : Updates an existing messageDeleteDetails.
     *
     * @param messageDeleteDetailsDTO the messageDeleteDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageDeleteDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the messageDeleteDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageDeleteDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-delete-details")
    public ResponseEntity<MessageDeleteDetailsDTO> updateMessageDeleteDetails(@Valid @RequestBody MessageDeleteDetailsDTO messageDeleteDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update MessageDeleteDetails : {}", messageDeleteDetailsDTO);
        if (messageDeleteDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MessageDeleteDetailsDTO result = messageDeleteDetailsService.save(messageDeleteDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageDeleteDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /message-delete-details} : get all the messageDeleteDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageDeleteDetails in body.
     */
    @GetMapping("/message-delete-details")
    public ResponseEntity<List<MessageDeleteDetailsDTO>> getAllMessageDeleteDetails(MessageDeleteDetailsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get MessageDeleteDetails by criteria: {}", criteria);
        Page<MessageDeleteDetailsDTO> page = messageDeleteDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /message-delete-details/count} : count all the messageDeleteDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/message-delete-details/count")
    public ResponseEntity<Long> countMessageDeleteDetails(MessageDeleteDetailsCriteria criteria) {
        log.debug("REST request to count MessageDeleteDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(messageDeleteDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /message-delete-details/:id} : get the "id" messageDeleteDetails.
     *
     * @param id the id of the messageDeleteDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageDeleteDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-delete-details/{id}")
    public ResponseEntity<MessageDeleteDetailsDTO> getMessageDeleteDetails(@PathVariable Long id) {
        log.debug("REST request to get MessageDeleteDetails : {}", id);
        Optional<MessageDeleteDetailsDTO> messageDeleteDetailsDTO = messageDeleteDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageDeleteDetailsDTO);
    }

    /**
     * {@code DELETE  /message-delete-details/:id} : delete the "id" messageDeleteDetails.
     *
     * @param id the id of the messageDeleteDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-delete-details/{id}")
    public ResponseEntity<Void> deleteMessageDeleteDetails(@PathVariable Long id) {
        log.debug("REST request to delete MessageDeleteDetails : {}", id);
        messageDeleteDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
