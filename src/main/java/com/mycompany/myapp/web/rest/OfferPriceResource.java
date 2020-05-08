package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.OfferPriceService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.OfferPriceDTO;
import com.mycompany.myapp.service.dto.OfferPriceCriteria;
import com.mycompany.myapp.service.OfferPriceQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.OfferPrice}.
 */
@RestController
@RequestMapping("/api")
public class OfferPriceResource {

    private final Logger log = LoggerFactory.getLogger(OfferPriceResource.class);

    private static final String ENTITY_NAME = "offerPrice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfferPriceService offerPriceService;

    private final OfferPriceQueryService offerPriceQueryService;

    public OfferPriceResource(OfferPriceService offerPriceService, OfferPriceQueryService offerPriceQueryService) {
        this.offerPriceService = offerPriceService;
        this.offerPriceQueryService = offerPriceQueryService;
    }

    /**
     * {@code POST  /offer-prices} : Create a new offerPrice.
     *
     * @param offerPriceDTO the offerPriceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offerPriceDTO, or with status {@code 400 (Bad Request)} if the offerPrice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offer-prices")
    public ResponseEntity<OfferPriceDTO> createOfferPrice(@Valid @RequestBody OfferPriceDTO offerPriceDTO) throws URISyntaxException {
        log.debug("REST request to save OfferPrice : {}", offerPriceDTO);
        if (offerPriceDTO.getId() != null) {
            throw new BadRequestAlertException("A new offerPrice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfferPriceDTO result = offerPriceService.save(offerPriceDTO);
        return ResponseEntity.created(new URI("/api/offer-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offer-prices} : Updates an existing offerPrice.
     *
     * @param offerPriceDTO the offerPriceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerPriceDTO,
     * or with status {@code 400 (Bad Request)} if the offerPriceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offerPriceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offer-prices")
    public ResponseEntity<OfferPriceDTO> updateOfferPrice(@Valid @RequestBody OfferPriceDTO offerPriceDTO) throws URISyntaxException {
        log.debug("REST request to update OfferPrice : {}", offerPriceDTO);
        if (offerPriceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfferPriceDTO result = offerPriceService.save(offerPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offerPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /offer-prices} : get all the offerPrices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offerPrices in body.
     */
    @GetMapping("/offer-prices")
    public ResponseEntity<List<OfferPriceDTO>> getAllOfferPrices(OfferPriceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OfferPrices by criteria: {}", criteria);
        Page<OfferPriceDTO> page = offerPriceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /offer-prices/count} : count all the offerPrices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/offer-prices/count")
    public ResponseEntity<Long> countOfferPrices(OfferPriceCriteria criteria) {
        log.debug("REST request to count OfferPrices by criteria: {}", criteria);
        return ResponseEntity.ok().body(offerPriceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /offer-prices/:id} : get the "id" offerPrice.
     *
     * @param id the id of the offerPriceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerPriceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offer-prices/{id}")
    public ResponseEntity<OfferPriceDTO> getOfferPrice(@PathVariable Long id) {
        log.debug("REST request to get OfferPrice : {}", id);
        Optional<OfferPriceDTO> offerPriceDTO = offerPriceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offerPriceDTO);
    }

    /**
     * {@code DELETE  /offer-prices/:id} : delete the "id" offerPrice.
     *
     * @param id the id of the offerPriceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offer-prices/{id}")
    public ResponseEntity<Void> deleteOfferPrice(@PathVariable Long id) {
        log.debug("REST request to delete OfferPrice : {}", id);
        offerPriceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
