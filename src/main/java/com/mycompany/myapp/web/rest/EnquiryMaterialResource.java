package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.EnquiryMaterialService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.service.dto.EnquiryMaterialDTO;
import com.mycompany.myapp.service.dto.EnquiryMaterialCriteria;
import com.mycompany.myapp.service.EnquiryMaterialQueryService;

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
 * REST controller for managing {@link com.mycompany.myapp.domain.EnquiryMaterial}.
 */
@RestController
@RequestMapping("/api")
public class EnquiryMaterialResource {

    private final Logger log = LoggerFactory.getLogger(EnquiryMaterialResource.class);

    private static final String ENTITY_NAME = "enquiryMaterial";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnquiryMaterialService enquiryMaterialService;

    private final EnquiryMaterialQueryService enquiryMaterialQueryService;

    public EnquiryMaterialResource(EnquiryMaterialService enquiryMaterialService, EnquiryMaterialQueryService enquiryMaterialQueryService) {
        this.enquiryMaterialService = enquiryMaterialService;
        this.enquiryMaterialQueryService = enquiryMaterialQueryService;
    }

    /**
     * {@code POST  /enquiry-materials} : Create a new enquiryMaterial.
     *
     * @param enquiryMaterialDTO the enquiryMaterialDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enquiryMaterialDTO, or with status {@code 400 (Bad Request)} if the enquiryMaterial has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enquiry-materials")
    public ResponseEntity<EnquiryMaterialDTO> createEnquiryMaterial(@Valid @RequestBody EnquiryMaterialDTO enquiryMaterialDTO) throws URISyntaxException {
        log.debug("REST request to save EnquiryMaterial : {}", enquiryMaterialDTO);
        if (enquiryMaterialDTO.getId() != null) {
            throw new BadRequestAlertException("A new enquiryMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnquiryMaterialDTO result = enquiryMaterialService.save(enquiryMaterialDTO);
        return ResponseEntity.created(new URI("/api/enquiry-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enquiry-materials} : Updates an existing enquiryMaterial.
     *
     * @param enquiryMaterialDTO the enquiryMaterialDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enquiryMaterialDTO,
     * or with status {@code 400 (Bad Request)} if the enquiryMaterialDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enquiryMaterialDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enquiry-materials")
    public ResponseEntity<EnquiryMaterialDTO> updateEnquiryMaterial(@Valid @RequestBody EnquiryMaterialDTO enquiryMaterialDTO) throws URISyntaxException {
        log.debug("REST request to update EnquiryMaterial : {}", enquiryMaterialDTO);
        if (enquiryMaterialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnquiryMaterialDTO result = enquiryMaterialService.save(enquiryMaterialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enquiryMaterialDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enquiry-materials} : get all the enquiryMaterials.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enquiryMaterials in body.
     */
    @GetMapping("/enquiry-materials")
    public ResponseEntity<List<EnquiryMaterialDTO>> getAllEnquiryMaterials(EnquiryMaterialCriteria criteria, Pageable pageable) {
        log.debug("REST request to get EnquiryMaterials by criteria: {}", criteria);
        Page<EnquiryMaterialDTO> page = enquiryMaterialQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enquiry-materials/count} : count all the enquiryMaterials.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/enquiry-materials/count")
    public ResponseEntity<Long> countEnquiryMaterials(EnquiryMaterialCriteria criteria) {
        log.debug("REST request to count EnquiryMaterials by criteria: {}", criteria);
        return ResponseEntity.ok().body(enquiryMaterialQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /enquiry-materials/:id} : get the "id" enquiryMaterial.
     *
     * @param id the id of the enquiryMaterialDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enquiryMaterialDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enquiry-materials/{id}")
    public ResponseEntity<EnquiryMaterialDTO> getEnquiryMaterial(@PathVariable Long id) {
        log.debug("REST request to get EnquiryMaterial : {}", id);
        Optional<EnquiryMaterialDTO> enquiryMaterialDTO = enquiryMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enquiryMaterialDTO);
    }

    /**
     * {@code DELETE  /enquiry-materials/:id} : delete the "id" enquiryMaterial.
     *
     * @param id the id of the enquiryMaterialDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enquiry-materials/{id}")
    public ResponseEntity<Void> deleteEnquiryMaterial(@PathVariable Long id) {
        log.debug("REST request to delete EnquiryMaterial : {}", id);
        enquiryMaterialService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
