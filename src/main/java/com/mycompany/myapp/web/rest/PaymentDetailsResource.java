package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.PaymentDetailsService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.PaymentDetailsDTO;
import com.mycompany.myapp.service.dto.PaymentDetailsCriteria;
import com.mycompany.myapp.service.PaymentDetailsQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.PaymentDetails}.
 */
@RestController
@RequestMapping("/api")
public class PaymentDetailsResource {

    private final Logger log = LoggerFactory.getLogger(PaymentDetailsResource.class);

    private static final String ENTITY_NAME = "paymentDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentDetailsService paymentDetailsService;

    private final PaymentDetailsQueryService paymentDetailsQueryService;

    public PaymentDetailsResource(PaymentDetailsService paymentDetailsService, PaymentDetailsQueryService paymentDetailsQueryService) {
        this.paymentDetailsService = paymentDetailsService;
        this.paymentDetailsQueryService = paymentDetailsQueryService;
    }

    /**
     * {@code POST  /payment-details} : Create a new paymentDetails.
     *
     * @param paymentDetailsDTO the paymentDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentDetailsDTO, or with status {@code 400 (Bad Request)} if the paymentDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-details")
    public ResponseEntity<PaymentDetailsDTO> createPaymentDetails(@Valid @RequestBody PaymentDetailsDTO paymentDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentDetails : {}", paymentDetailsDTO);
        if (paymentDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentDetailsDTO result = paymentDetailsService.save(paymentDetailsDTO);
        return ResponseEntity.created(new URI("/api/payment-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-details} : Updates an existing paymentDetails.
     *
     * @param paymentDetailsDTO the paymentDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the paymentDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-details")
    public ResponseEntity<PaymentDetailsDTO> updatePaymentDetails(@Valid @RequestBody PaymentDetailsDTO paymentDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentDetails : {}", paymentDetailsDTO);
        if (paymentDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentDetailsDTO result = paymentDetailsService.save(paymentDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /payment-details} : get all the paymentDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentDetails in body.
     */
    @GetMapping("/payment-details")
    public ResponseEntity<List<PaymentDetailsDTO>> getAllPaymentDetails(PaymentDetailsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PaymentDetails by criteria: {}", criteria);
        Page<PaymentDetailsDTO> page = paymentDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-details/count} : count all the paymentDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/payment-details/count")
    public ResponseEntity<Long> countPaymentDetails(PaymentDetailsCriteria criteria) {
        log.debug("REST request to count PaymentDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(paymentDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /payment-details/:id} : get the "id" paymentDetails.
     *
     * @param id the id of the paymentDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-details/{id}")
    public ResponseEntity<PaymentDetailsDTO> getPaymentDetails(@PathVariable Long id) {
        log.debug("REST request to get PaymentDetails : {}", id);
        Optional<PaymentDetailsDTO> paymentDetailsDTO = paymentDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentDetailsDTO);
    }

    /**
     * {@code DELETE  /payment-details/:id} : delete the "id" paymentDetails.
     *
     * @param id the id of the paymentDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-details/{id}")
    public ResponseEntity<Void> deletePaymentDetails(@PathVariable Long id) {
        log.debug("REST request to delete PaymentDetails : {}", id);
        paymentDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
