package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.CommissionReference;
import com.mycompany.myapp.repository.CommissionReferenceRepository;
import com.mycompany.myapp.service.CommissionReferenceService;
import com.mycompany.myapp.service.dto.CommissionReferenceDTO;
import com.mycompany.myapp.service.mapper.CommissionReferenceMapper;
import com.mycompany.myapp.service.dto.CommissionReferenceCriteria;
import com.mycompany.myapp.service.CommissionReferenceQueryService;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CommissionReferenceResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CommissionReferenceResourceIT {

    private static final BigDecimal DEFAULT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PERCENTAGE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_HOLD_DAYS = 1;
    private static final Integer UPDATED_HOLD_DAYS = 2;
    private static final Integer SMALLER_HOLD_DAYS = 1 - 1;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CommissionReferenceRepository commissionReferenceRepository;

    @Autowired
    private CommissionReferenceMapper commissionReferenceMapper;

    @Autowired
    private CommissionReferenceService commissionReferenceService;

    @Autowired
    private CommissionReferenceQueryService commissionReferenceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommissionReferenceMockMvc;

    private CommissionReference commissionReference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommissionReference createEntity(EntityManager em) {
        CommissionReference commissionReference = new CommissionReference()
            .percentage(DEFAULT_PERCENTAGE)
            .holdDays(DEFAULT_HOLD_DAYS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return commissionReference;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommissionReference createUpdatedEntity(EntityManager em) {
        CommissionReference commissionReference = new CommissionReference()
            .percentage(UPDATED_PERCENTAGE)
            .holdDays(UPDATED_HOLD_DAYS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return commissionReference;
    }

    @BeforeEach
    public void initTest() {
        commissionReference = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommissionReference() throws Exception {
        int databaseSizeBeforeCreate = commissionReferenceRepository.findAll().size();

        // Create the CommissionReference
        CommissionReferenceDTO commissionReferenceDTO = commissionReferenceMapper.toDto(commissionReference);
        restCommissionReferenceMockMvc.perform(post("/api/commission-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionReferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the CommissionReference in the database
        List<CommissionReference> commissionReferenceList = commissionReferenceRepository.findAll();
        assertThat(commissionReferenceList).hasSize(databaseSizeBeforeCreate + 1);
        CommissionReference testCommissionReference = commissionReferenceList.get(commissionReferenceList.size() - 1);
        assertThat(testCommissionReference.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testCommissionReference.getHoldDays()).isEqualTo(DEFAULT_HOLD_DAYS);
        assertThat(testCommissionReference.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCommissionReference.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createCommissionReferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commissionReferenceRepository.findAll().size();

        // Create the CommissionReference with an existing ID
        commissionReference.setId(1L);
        CommissionReferenceDTO commissionReferenceDTO = commissionReferenceMapper.toDto(commissionReference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommissionReferenceMockMvc.perform(post("/api/commission-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommissionReference in the database
        List<CommissionReference> commissionReferenceList = commissionReferenceRepository.findAll();
        assertThat(commissionReferenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionReferenceRepository.findAll().size();
        // set the field null
        commissionReference.setPercentage(null);

        // Create the CommissionReference, which fails.
        CommissionReferenceDTO commissionReferenceDTO = commissionReferenceMapper.toDto(commissionReference);

        restCommissionReferenceMockMvc.perform(post("/api/commission-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<CommissionReference> commissionReferenceList = commissionReferenceRepository.findAll();
        assertThat(commissionReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoldDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionReferenceRepository.findAll().size();
        // set the field null
        commissionReference.setHoldDays(null);

        // Create the CommissionReference, which fails.
        CommissionReferenceDTO commissionReferenceDTO = commissionReferenceMapper.toDto(commissionReference);

        restCommissionReferenceMockMvc.perform(post("/api/commission-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<CommissionReference> commissionReferenceList = commissionReferenceRepository.findAll();
        assertThat(commissionReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionReferenceRepository.findAll().size();
        // set the field null
        commissionReference.setCreatedAt(null);

        // Create the CommissionReference, which fails.
        CommissionReferenceDTO commissionReferenceDTO = commissionReferenceMapper.toDto(commissionReference);

        restCommissionReferenceMockMvc.perform(post("/api/commission-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<CommissionReference> commissionReferenceList = commissionReferenceRepository.findAll();
        assertThat(commissionReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionReferenceRepository.findAll().size();
        // set the field null
        commissionReference.setUpdatedAt(null);

        // Create the CommissionReference, which fails.
        CommissionReferenceDTO commissionReferenceDTO = commissionReferenceMapper.toDto(commissionReference);

        restCommissionReferenceMockMvc.perform(post("/api/commission-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<CommissionReference> commissionReferenceList = commissionReferenceRepository.findAll();
        assertThat(commissionReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommissionReferences() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList
        restCommissionReferenceMockMvc.perform(get("/api/commission-references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commissionReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].holdDays").value(hasItem(DEFAULT_HOLD_DAYS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getCommissionReference() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get the commissionReference
        restCommissionReferenceMockMvc.perform(get("/api/commission-references/{id}", commissionReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commissionReference.getId().intValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.holdDays").value(DEFAULT_HOLD_DAYS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getCommissionReferencesByIdFiltering() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        Long id = commissionReference.getId();

        defaultCommissionReferenceShouldBeFound("id.equals=" + id);
        defaultCommissionReferenceShouldNotBeFound("id.notEquals=" + id);

        defaultCommissionReferenceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommissionReferenceShouldNotBeFound("id.greaterThan=" + id);

        defaultCommissionReferenceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommissionReferenceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommissionReferencesByPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where percentage equals to DEFAULT_PERCENTAGE
        defaultCommissionReferenceShouldBeFound("percentage.equals=" + DEFAULT_PERCENTAGE);

        // Get all the commissionReferenceList where percentage equals to UPDATED_PERCENTAGE
        defaultCommissionReferenceShouldNotBeFound("percentage.equals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where percentage not equals to DEFAULT_PERCENTAGE
        defaultCommissionReferenceShouldNotBeFound("percentage.notEquals=" + DEFAULT_PERCENTAGE);

        // Get all the commissionReferenceList where percentage not equals to UPDATED_PERCENTAGE
        defaultCommissionReferenceShouldBeFound("percentage.notEquals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where percentage in DEFAULT_PERCENTAGE or UPDATED_PERCENTAGE
        defaultCommissionReferenceShouldBeFound("percentage.in=" + DEFAULT_PERCENTAGE + "," + UPDATED_PERCENTAGE);

        // Get all the commissionReferenceList where percentage equals to UPDATED_PERCENTAGE
        defaultCommissionReferenceShouldNotBeFound("percentage.in=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where percentage is not null
        defaultCommissionReferenceShouldBeFound("percentage.specified=true");

        // Get all the commissionReferenceList where percentage is null
        defaultCommissionReferenceShouldNotBeFound("percentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where percentage is greater than or equal to DEFAULT_PERCENTAGE
        defaultCommissionReferenceShouldBeFound("percentage.greaterThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the commissionReferenceList where percentage is greater than or equal to UPDATED_PERCENTAGE
        defaultCommissionReferenceShouldNotBeFound("percentage.greaterThanOrEqual=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where percentage is less than or equal to DEFAULT_PERCENTAGE
        defaultCommissionReferenceShouldBeFound("percentage.lessThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the commissionReferenceList where percentage is less than or equal to SMALLER_PERCENTAGE
        defaultCommissionReferenceShouldNotBeFound("percentage.lessThanOrEqual=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where percentage is less than DEFAULT_PERCENTAGE
        defaultCommissionReferenceShouldNotBeFound("percentage.lessThan=" + DEFAULT_PERCENTAGE);

        // Get all the commissionReferenceList where percentage is less than UPDATED_PERCENTAGE
        defaultCommissionReferenceShouldBeFound("percentage.lessThan=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where percentage is greater than DEFAULT_PERCENTAGE
        defaultCommissionReferenceShouldNotBeFound("percentage.greaterThan=" + DEFAULT_PERCENTAGE);

        // Get all the commissionReferenceList where percentage is greater than SMALLER_PERCENTAGE
        defaultCommissionReferenceShouldBeFound("percentage.greaterThan=" + SMALLER_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllCommissionReferencesByHoldDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where holdDays equals to DEFAULT_HOLD_DAYS
        defaultCommissionReferenceShouldBeFound("holdDays.equals=" + DEFAULT_HOLD_DAYS);

        // Get all the commissionReferenceList where holdDays equals to UPDATED_HOLD_DAYS
        defaultCommissionReferenceShouldNotBeFound("holdDays.equals=" + UPDATED_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByHoldDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where holdDays not equals to DEFAULT_HOLD_DAYS
        defaultCommissionReferenceShouldNotBeFound("holdDays.notEquals=" + DEFAULT_HOLD_DAYS);

        // Get all the commissionReferenceList where holdDays not equals to UPDATED_HOLD_DAYS
        defaultCommissionReferenceShouldBeFound("holdDays.notEquals=" + UPDATED_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByHoldDaysIsInShouldWork() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where holdDays in DEFAULT_HOLD_DAYS or UPDATED_HOLD_DAYS
        defaultCommissionReferenceShouldBeFound("holdDays.in=" + DEFAULT_HOLD_DAYS + "," + UPDATED_HOLD_DAYS);

        // Get all the commissionReferenceList where holdDays equals to UPDATED_HOLD_DAYS
        defaultCommissionReferenceShouldNotBeFound("holdDays.in=" + UPDATED_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByHoldDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where holdDays is not null
        defaultCommissionReferenceShouldBeFound("holdDays.specified=true");

        // Get all the commissionReferenceList where holdDays is null
        defaultCommissionReferenceShouldNotBeFound("holdDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByHoldDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where holdDays is greater than or equal to DEFAULT_HOLD_DAYS
        defaultCommissionReferenceShouldBeFound("holdDays.greaterThanOrEqual=" + DEFAULT_HOLD_DAYS);

        // Get all the commissionReferenceList where holdDays is greater than or equal to UPDATED_HOLD_DAYS
        defaultCommissionReferenceShouldNotBeFound("holdDays.greaterThanOrEqual=" + UPDATED_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByHoldDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where holdDays is less than or equal to DEFAULT_HOLD_DAYS
        defaultCommissionReferenceShouldBeFound("holdDays.lessThanOrEqual=" + DEFAULT_HOLD_DAYS);

        // Get all the commissionReferenceList where holdDays is less than or equal to SMALLER_HOLD_DAYS
        defaultCommissionReferenceShouldNotBeFound("holdDays.lessThanOrEqual=" + SMALLER_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByHoldDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where holdDays is less than DEFAULT_HOLD_DAYS
        defaultCommissionReferenceShouldNotBeFound("holdDays.lessThan=" + DEFAULT_HOLD_DAYS);

        // Get all the commissionReferenceList where holdDays is less than UPDATED_HOLD_DAYS
        defaultCommissionReferenceShouldBeFound("holdDays.lessThan=" + UPDATED_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByHoldDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where holdDays is greater than DEFAULT_HOLD_DAYS
        defaultCommissionReferenceShouldNotBeFound("holdDays.greaterThan=" + DEFAULT_HOLD_DAYS);

        // Get all the commissionReferenceList where holdDays is greater than SMALLER_HOLD_DAYS
        defaultCommissionReferenceShouldBeFound("holdDays.greaterThan=" + SMALLER_HOLD_DAYS);
    }


    @Test
    @Transactional
    public void getAllCommissionReferencesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where createdAt equals to DEFAULT_CREATED_AT
        defaultCommissionReferenceShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the commissionReferenceList where createdAt equals to UPDATED_CREATED_AT
        defaultCommissionReferenceShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where createdAt not equals to DEFAULT_CREATED_AT
        defaultCommissionReferenceShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the commissionReferenceList where createdAt not equals to UPDATED_CREATED_AT
        defaultCommissionReferenceShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultCommissionReferenceShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the commissionReferenceList where createdAt equals to UPDATED_CREATED_AT
        defaultCommissionReferenceShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where createdAt is not null
        defaultCommissionReferenceShouldBeFound("createdAt.specified=true");

        // Get all the commissionReferenceList where createdAt is null
        defaultCommissionReferenceShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultCommissionReferenceShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the commissionReferenceList where updatedAt equals to UPDATED_UPDATED_AT
        defaultCommissionReferenceShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultCommissionReferenceShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the commissionReferenceList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultCommissionReferenceShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultCommissionReferenceShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the commissionReferenceList where updatedAt equals to UPDATED_UPDATED_AT
        defaultCommissionReferenceShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionReferencesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        // Get all the commissionReferenceList where updatedAt is not null
        defaultCommissionReferenceShouldBeFound("updatedAt.specified=true");

        // Get all the commissionReferenceList where updatedAt is null
        defaultCommissionReferenceShouldNotBeFound("updatedAt.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommissionReferenceShouldBeFound(String filter) throws Exception {
        restCommissionReferenceMockMvc.perform(get("/api/commission-references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commissionReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].holdDays").value(hasItem(DEFAULT_HOLD_DAYS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restCommissionReferenceMockMvc.perform(get("/api/commission-references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommissionReferenceShouldNotBeFound(String filter) throws Exception {
        restCommissionReferenceMockMvc.perform(get("/api/commission-references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommissionReferenceMockMvc.perform(get("/api/commission-references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommissionReference() throws Exception {
        // Get the commissionReference
        restCommissionReferenceMockMvc.perform(get("/api/commission-references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommissionReference() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        int databaseSizeBeforeUpdate = commissionReferenceRepository.findAll().size();

        // Update the commissionReference
        CommissionReference updatedCommissionReference = commissionReferenceRepository.findById(commissionReference.getId()).get();
        // Disconnect from session so that the updates on updatedCommissionReference are not directly saved in db
        em.detach(updatedCommissionReference);
        updatedCommissionReference
            .percentage(UPDATED_PERCENTAGE)
            .holdDays(UPDATED_HOLD_DAYS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        CommissionReferenceDTO commissionReferenceDTO = commissionReferenceMapper.toDto(updatedCommissionReference);

        restCommissionReferenceMockMvc.perform(put("/api/commission-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionReferenceDTO)))
            .andExpect(status().isOk());

        // Validate the CommissionReference in the database
        List<CommissionReference> commissionReferenceList = commissionReferenceRepository.findAll();
        assertThat(commissionReferenceList).hasSize(databaseSizeBeforeUpdate);
        CommissionReference testCommissionReference = commissionReferenceList.get(commissionReferenceList.size() - 1);
        assertThat(testCommissionReference.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testCommissionReference.getHoldDays()).isEqualTo(UPDATED_HOLD_DAYS);
        assertThat(testCommissionReference.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCommissionReference.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingCommissionReference() throws Exception {
        int databaseSizeBeforeUpdate = commissionReferenceRepository.findAll().size();

        // Create the CommissionReference
        CommissionReferenceDTO commissionReferenceDTO = commissionReferenceMapper.toDto(commissionReference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionReferenceMockMvc.perform(put("/api/commission-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CommissionReference in the database
        List<CommissionReference> commissionReferenceList = commissionReferenceRepository.findAll();
        assertThat(commissionReferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommissionReference() throws Exception {
        // Initialize the database
        commissionReferenceRepository.saveAndFlush(commissionReference);

        int databaseSizeBeforeDelete = commissionReferenceRepository.findAll().size();

        // Delete the commissionReference
        restCommissionReferenceMockMvc.perform(delete("/api/commission-references/{id}", commissionReference.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommissionReference> commissionReferenceList = commissionReferenceRepository.findAll();
        assertThat(commissionReferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
