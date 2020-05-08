package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.SupplierEnquiryMapping;
import com.mycompany.myapp.domain.Enquiry;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.SupplierEnquiryMappingRepository;
import com.mycompany.myapp.service.SupplierEnquiryMappingService;
import com.mycompany.myapp.service.dto.SupplierEnquiryMappingDTO;
import com.mycompany.myapp.service.mapper.SupplierEnquiryMappingMapper;
import com.mycompany.myapp.service.dto.SupplierEnquiryMappingCriteria;
import com.mycompany.myapp.service.SupplierEnquiryMappingQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SupplierEnquiryMappingResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SupplierEnquiryMappingResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SupplierEnquiryMappingRepository supplierEnquiryMappingRepository;

    @Autowired
    private SupplierEnquiryMappingMapper supplierEnquiryMappingMapper;

    @Autowired
    private SupplierEnquiryMappingService supplierEnquiryMappingService;

    @Autowired
    private SupplierEnquiryMappingQueryService supplierEnquiryMappingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplierEnquiryMappingMockMvc;

    private SupplierEnquiryMapping supplierEnquiryMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierEnquiryMapping createEntity(EntityManager em) {
        SupplierEnquiryMapping supplierEnquiryMapping = new SupplierEnquiryMapping()
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        supplierEnquiryMapping.setEnquiry(enquiry);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        supplierEnquiryMapping.setSupplier(userProfile);
        // Add required entity
        supplierEnquiryMapping.setCreatedBy(userProfile);
        return supplierEnquiryMapping;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierEnquiryMapping createUpdatedEntity(EntityManager em) {
        SupplierEnquiryMapping supplierEnquiryMapping = new SupplierEnquiryMapping()
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createUpdatedEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        supplierEnquiryMapping.setEnquiry(enquiry);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        supplierEnquiryMapping.setSupplier(userProfile);
        // Add required entity
        supplierEnquiryMapping.setCreatedBy(userProfile);
        return supplierEnquiryMapping;
    }

    @BeforeEach
    public void initTest() {
        supplierEnquiryMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplierEnquiryMapping() throws Exception {
        int databaseSizeBeforeCreate = supplierEnquiryMappingRepository.findAll().size();

        // Create the SupplierEnquiryMapping
        SupplierEnquiryMappingDTO supplierEnquiryMappingDTO = supplierEnquiryMappingMapper.toDto(supplierEnquiryMapping);
        restSupplierEnquiryMappingMockMvc.perform(post("/api/supplier-enquiry-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierEnquiryMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplierEnquiryMapping in the database
        List<SupplierEnquiryMapping> supplierEnquiryMappingList = supplierEnquiryMappingRepository.findAll();
        assertThat(supplierEnquiryMappingList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierEnquiryMapping testSupplierEnquiryMapping = supplierEnquiryMappingList.get(supplierEnquiryMappingList.size() - 1);
        assertThat(testSupplierEnquiryMapping.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSupplierEnquiryMapping.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createSupplierEnquiryMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierEnquiryMappingRepository.findAll().size();

        // Create the SupplierEnquiryMapping with an existing ID
        supplierEnquiryMapping.setId(1L);
        SupplierEnquiryMappingDTO supplierEnquiryMappingDTO = supplierEnquiryMappingMapper.toDto(supplierEnquiryMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierEnquiryMappingMockMvc.perform(post("/api/supplier-enquiry-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierEnquiryMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierEnquiryMapping in the database
        List<SupplierEnquiryMapping> supplierEnquiryMappingList = supplierEnquiryMappingRepository.findAll();
        assertThat(supplierEnquiryMappingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierEnquiryMappingRepository.findAll().size();
        // set the field null
        supplierEnquiryMapping.setCreatedAt(null);

        // Create the SupplierEnquiryMapping, which fails.
        SupplierEnquiryMappingDTO supplierEnquiryMappingDTO = supplierEnquiryMappingMapper.toDto(supplierEnquiryMapping);

        restSupplierEnquiryMappingMockMvc.perform(post("/api/supplier-enquiry-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierEnquiryMappingDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierEnquiryMapping> supplierEnquiryMappingList = supplierEnquiryMappingRepository.findAll();
        assertThat(supplierEnquiryMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierEnquiryMappingRepository.findAll().size();
        // set the field null
        supplierEnquiryMapping.setUpdatedAt(null);

        // Create the SupplierEnquiryMapping, which fails.
        SupplierEnquiryMappingDTO supplierEnquiryMappingDTO = supplierEnquiryMappingMapper.toDto(supplierEnquiryMapping);

        restSupplierEnquiryMappingMockMvc.perform(post("/api/supplier-enquiry-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierEnquiryMappingDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierEnquiryMapping> supplierEnquiryMappingList = supplierEnquiryMappingRepository.findAll();
        assertThat(supplierEnquiryMappingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplierEnquiryMappings() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        // Get all the supplierEnquiryMappingList
        restSupplierEnquiryMappingMockMvc.perform(get("/api/supplier-enquiry-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierEnquiryMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getSupplierEnquiryMapping() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        // Get the supplierEnquiryMapping
        restSupplierEnquiryMappingMockMvc.perform(get("/api/supplier-enquiry-mappings/{id}", supplierEnquiryMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplierEnquiryMapping.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getSupplierEnquiryMappingsByIdFiltering() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        Long id = supplierEnquiryMapping.getId();

        defaultSupplierEnquiryMappingShouldBeFound("id.equals=" + id);
        defaultSupplierEnquiryMappingShouldNotBeFound("id.notEquals=" + id);

        defaultSupplierEnquiryMappingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupplierEnquiryMappingShouldNotBeFound("id.greaterThan=" + id);

        defaultSupplierEnquiryMappingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupplierEnquiryMappingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSupplierEnquiryMappingsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        // Get all the supplierEnquiryMappingList where createdAt equals to DEFAULT_CREATED_AT
        defaultSupplierEnquiryMappingShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the supplierEnquiryMappingList where createdAt equals to UPDATED_CREATED_AT
        defaultSupplierEnquiryMappingShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllSupplierEnquiryMappingsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        // Get all the supplierEnquiryMappingList where createdAt not equals to DEFAULT_CREATED_AT
        defaultSupplierEnquiryMappingShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the supplierEnquiryMappingList where createdAt not equals to UPDATED_CREATED_AT
        defaultSupplierEnquiryMappingShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllSupplierEnquiryMappingsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        // Get all the supplierEnquiryMappingList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultSupplierEnquiryMappingShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the supplierEnquiryMappingList where createdAt equals to UPDATED_CREATED_AT
        defaultSupplierEnquiryMappingShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllSupplierEnquiryMappingsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        // Get all the supplierEnquiryMappingList where createdAt is not null
        defaultSupplierEnquiryMappingShouldBeFound("createdAt.specified=true");

        // Get all the supplierEnquiryMappingList where createdAt is null
        defaultSupplierEnquiryMappingShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierEnquiryMappingsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        // Get all the supplierEnquiryMappingList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultSupplierEnquiryMappingShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the supplierEnquiryMappingList where updatedAt equals to UPDATED_UPDATED_AT
        defaultSupplierEnquiryMappingShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllSupplierEnquiryMappingsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        // Get all the supplierEnquiryMappingList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultSupplierEnquiryMappingShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the supplierEnquiryMappingList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultSupplierEnquiryMappingShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllSupplierEnquiryMappingsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        // Get all the supplierEnquiryMappingList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultSupplierEnquiryMappingShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the supplierEnquiryMappingList where updatedAt equals to UPDATED_UPDATED_AT
        defaultSupplierEnquiryMappingShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllSupplierEnquiryMappingsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        // Get all the supplierEnquiryMappingList where updatedAt is not null
        defaultSupplierEnquiryMappingShouldBeFound("updatedAt.specified=true");

        // Get all the supplierEnquiryMappingList where updatedAt is null
        defaultSupplierEnquiryMappingShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierEnquiryMappingsByEnquiryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Enquiry enquiry = supplierEnquiryMapping.getEnquiry();
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);
        Long enquiryId = enquiry.getId();

        // Get all the supplierEnquiryMappingList where enquiry equals to enquiryId
        defaultSupplierEnquiryMappingShouldBeFound("enquiryId.equals=" + enquiryId);

        // Get all the supplierEnquiryMappingList where enquiry equals to enquiryId + 1
        defaultSupplierEnquiryMappingShouldNotBeFound("enquiryId.equals=" + (enquiryId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplierEnquiryMappingsBySupplierIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile supplier = supplierEnquiryMapping.getSupplier();
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);
        Long supplierId = supplier.getId();

        // Get all the supplierEnquiryMappingList where supplier equals to supplierId
        defaultSupplierEnquiryMappingShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the supplierEnquiryMappingList where supplier equals to supplierId + 1
        defaultSupplierEnquiryMappingShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplierEnquiryMappingsByCreatedByIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile createdBy = supplierEnquiryMapping.getCreatedBy();
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);
        Long createdById = createdBy.getId();

        // Get all the supplierEnquiryMappingList where createdBy equals to createdById
        defaultSupplierEnquiryMappingShouldBeFound("createdById.equals=" + createdById);

        // Get all the supplierEnquiryMappingList where createdBy equals to createdById + 1
        defaultSupplierEnquiryMappingShouldNotBeFound("createdById.equals=" + (createdById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierEnquiryMappingShouldBeFound(String filter) throws Exception {
        restSupplierEnquiryMappingMockMvc.perform(get("/api/supplier-enquiry-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierEnquiryMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restSupplierEnquiryMappingMockMvc.perform(get("/api/supplier-enquiry-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierEnquiryMappingShouldNotBeFound(String filter) throws Exception {
        restSupplierEnquiryMappingMockMvc.perform(get("/api/supplier-enquiry-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierEnquiryMappingMockMvc.perform(get("/api/supplier-enquiry-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplierEnquiryMapping() throws Exception {
        // Get the supplierEnquiryMapping
        restSupplierEnquiryMappingMockMvc.perform(get("/api/supplier-enquiry-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierEnquiryMapping() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        int databaseSizeBeforeUpdate = supplierEnquiryMappingRepository.findAll().size();

        // Update the supplierEnquiryMapping
        SupplierEnquiryMapping updatedSupplierEnquiryMapping = supplierEnquiryMappingRepository.findById(supplierEnquiryMapping.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierEnquiryMapping are not directly saved in db
        em.detach(updatedSupplierEnquiryMapping);
        updatedSupplierEnquiryMapping
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        SupplierEnquiryMappingDTO supplierEnquiryMappingDTO = supplierEnquiryMappingMapper.toDto(updatedSupplierEnquiryMapping);

        restSupplierEnquiryMappingMockMvc.perform(put("/api/supplier-enquiry-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierEnquiryMappingDTO)))
            .andExpect(status().isOk());

        // Validate the SupplierEnquiryMapping in the database
        List<SupplierEnquiryMapping> supplierEnquiryMappingList = supplierEnquiryMappingRepository.findAll();
        assertThat(supplierEnquiryMappingList).hasSize(databaseSizeBeforeUpdate);
        SupplierEnquiryMapping testSupplierEnquiryMapping = supplierEnquiryMappingList.get(supplierEnquiryMappingList.size() - 1);
        assertThat(testSupplierEnquiryMapping.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSupplierEnquiryMapping.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplierEnquiryMapping() throws Exception {
        int databaseSizeBeforeUpdate = supplierEnquiryMappingRepository.findAll().size();

        // Create the SupplierEnquiryMapping
        SupplierEnquiryMappingDTO supplierEnquiryMappingDTO = supplierEnquiryMappingMapper.toDto(supplierEnquiryMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierEnquiryMappingMockMvc.perform(put("/api/supplier-enquiry-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierEnquiryMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierEnquiryMapping in the database
        List<SupplierEnquiryMapping> supplierEnquiryMappingList = supplierEnquiryMappingRepository.findAll();
        assertThat(supplierEnquiryMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupplierEnquiryMapping() throws Exception {
        // Initialize the database
        supplierEnquiryMappingRepository.saveAndFlush(supplierEnquiryMapping);

        int databaseSizeBeforeDelete = supplierEnquiryMappingRepository.findAll().size();

        // Delete the supplierEnquiryMapping
        restSupplierEnquiryMappingMockMvc.perform(delete("/api/supplier-enquiry-mappings/{id}", supplierEnquiryMapping.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupplierEnquiryMapping> supplierEnquiryMappingList = supplierEnquiryMappingRepository.findAll();
        assertThat(supplierEnquiryMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
