package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.FavouritesService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.FavouritesDTO;
import com.mycompany.myapp.service.dto.FavouritesCriteria;
import com.mycompany.myapp.service.FavouritesQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.Favourites}.
 */
@RestController
@RequestMapping("/api")
public class FavouritesResource {

    private final Logger log = LoggerFactory.getLogger(FavouritesResource.class);

    private static final String ENTITY_NAME = "favourites";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FavouritesService favouritesService;

    private final FavouritesQueryService favouritesQueryService;

    public FavouritesResource(FavouritesService favouritesService, FavouritesQueryService favouritesQueryService) {
        this.favouritesService = favouritesService;
        this.favouritesQueryService = favouritesQueryService;
    }

    /**
     * {@code POST  /favourites} : Create a new favourites.
     *
     * @param favouritesDTO the favouritesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new favouritesDTO, or with status {@code 400 (Bad Request)} if the favourites has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/favourites")
    public ResponseEntity<FavouritesDTO> createFavourites(@Valid @RequestBody FavouritesDTO favouritesDTO) throws URISyntaxException {
        log.debug("REST request to save Favourites : {}", favouritesDTO);
        if (favouritesDTO.getId() != null) {
            throw new BadRequestAlertException("A new favourites cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FavouritesDTO result = favouritesService.save(favouritesDTO);
        return ResponseEntity.created(new URI("/api/favourites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /favourites} : Updates an existing favourites.
     *
     * @param favouritesDTO the favouritesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated favouritesDTO,
     * or with status {@code 400 (Bad Request)} if the favouritesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the favouritesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/favourites")
    public ResponseEntity<FavouritesDTO> updateFavourites(@Valid @RequestBody FavouritesDTO favouritesDTO) throws URISyntaxException {
        log.debug("REST request to update Favourites : {}", favouritesDTO);
        if (favouritesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FavouritesDTO result = favouritesService.save(favouritesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, favouritesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /favourites} : get all the favourites.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of favourites in body.
     */
    @GetMapping("/favourites")
    public ResponseEntity<List<FavouritesDTO>> getAllFavourites(FavouritesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Favourites by criteria: {}", criteria);
        Page<FavouritesDTO> page = favouritesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /favourites/count} : count all the favourites.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/favourites/count")
    public ResponseEntity<Long> countFavourites(FavouritesCriteria criteria) {
        log.debug("REST request to count Favourites by criteria: {}", criteria);
        return ResponseEntity.ok().body(favouritesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /favourites/:id} : get the "id" favourites.
     *
     * @param id the id of the favouritesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the favouritesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/favourites/{id}")
    public ResponseEntity<FavouritesDTO> getFavourites(@PathVariable Long id) {
        log.debug("REST request to get Favourites : {}", id);
        Optional<FavouritesDTO> favouritesDTO = favouritesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(favouritesDTO);
    }

    /**
     * {@code DELETE  /favourites/:id} : delete the "id" favourites.
     *
     * @param id the id of the favouritesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/favourites/{id}")
    public ResponseEntity<Void> deleteFavourites(@PathVariable Long id) {
        log.debug("REST request to delete Favourites : {}", id);
        favouritesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
