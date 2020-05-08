package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.TradingHoursService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.TradingHoursDTO;
import com.mycompany.myapp.service.dto.TradingHoursCriteria;
import com.mycompany.myapp.service.TradingHoursQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.TradingHours}.
 */
@RestController
@RequestMapping("/api")
public class TradingHoursResource {

    private final Logger log = LoggerFactory.getLogger(TradingHoursResource.class);

    private static final String ENTITY_NAME = "tradingHours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TradingHoursService tradingHoursService;

    private final TradingHoursQueryService tradingHoursQueryService;

    public TradingHoursResource(TradingHoursService tradingHoursService, TradingHoursQueryService tradingHoursQueryService) {
        this.tradingHoursService = tradingHoursService;
        this.tradingHoursQueryService = tradingHoursQueryService;
    }

    /**
     * {@code POST  /trading-hours} : Create a new tradingHours.
     *
     * @param tradingHoursDTO the tradingHoursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tradingHoursDTO, or with status {@code 400 (Bad Request)} if the tradingHours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trading-hours")
    public ResponseEntity<TradingHoursDTO> createTradingHours(@Valid @RequestBody TradingHoursDTO tradingHoursDTO) throws URISyntaxException {
        log.debug("REST request to save TradingHours : {}", tradingHoursDTO);
        if (tradingHoursDTO.getId() != null) {
            throw new BadRequestAlertException("A new tradingHours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TradingHoursDTO result = tradingHoursService.save(tradingHoursDTO);
        return ResponseEntity.created(new URI("/api/trading-hours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trading-hours} : Updates an existing tradingHours.
     *
     * @param tradingHoursDTO the tradingHoursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tradingHoursDTO,
     * or with status {@code 400 (Bad Request)} if the tradingHoursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tradingHoursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trading-hours")
    public ResponseEntity<TradingHoursDTO> updateTradingHours(@Valid @RequestBody TradingHoursDTO tradingHoursDTO) throws URISyntaxException {
        log.debug("REST request to update TradingHours : {}", tradingHoursDTO);
        if (tradingHoursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TradingHoursDTO result = tradingHoursService.save(tradingHoursDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tradingHoursDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /trading-hours} : get all the tradingHours.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tradingHours in body.
     */
    @GetMapping("/trading-hours")
    public ResponseEntity<List<TradingHoursDTO>> getAllTradingHours(TradingHoursCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TradingHours by criteria: {}", criteria);
        Page<TradingHoursDTO> page = tradingHoursQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trading-hours/count} : count all the tradingHours.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/trading-hours/count")
    public ResponseEntity<Long> countTradingHours(TradingHoursCriteria criteria) {
        log.debug("REST request to count TradingHours by criteria: {}", criteria);
        return ResponseEntity.ok().body(tradingHoursQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /trading-hours/:id} : get the "id" tradingHours.
     *
     * @param id the id of the tradingHoursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tradingHoursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trading-hours/{id}")
    public ResponseEntity<TradingHoursDTO> getTradingHours(@PathVariable Long id) {
        log.debug("REST request to get TradingHours : {}", id);
        Optional<TradingHoursDTO> tradingHoursDTO = tradingHoursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tradingHoursDTO);
    }

    /**
     * {@code DELETE  /trading-hours/:id} : delete the "id" tradingHours.
     *
     * @param id the id of the tradingHoursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trading-hours/{id}")
    public ResponseEntity<Void> deleteTradingHours(@PathVariable Long id) {
        log.debug("REST request to delete TradingHours : {}", id);
        tradingHoursService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
