package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.BonusReference;
import com.mycompany.myapp.repository.BonusReferenceRepository;
import com.mycompany.myapp.service.BonusReferenceService;
import com.mycompany.myapp.service.dto.BonusReferenceDTO;
import com.mycompany.myapp.service.mapper.BonusReferenceMapper;
import com.mycompany.myapp.service.dto.BonusReferenceCriteria;
import com.mycompany.myapp.service.BonusReferenceQueryService;

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
 * Integration tests for the {@link BonusReferenceResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BonusReferenceResourceIT {

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
    private BonusReferenceRepository bonusReferenceRepository;

    @Autowired
    private BonusReferenceMapper bonusReferenceMapper;

    @Autowired
    private BonusReferenceService bonusReferenceService;

    @Autowired
    private BonusReferenceQueryService bonusReferenceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBonusReferenceMockMvc;

    private BonusReference bonusReference;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BonusReference createEntity(EntityManager em) {
        BonusReference bonusReference = new BonusReference()
            .percentage(DEFAULT_PERCENTAGE)
            .holdDays(DEFAULT_HOLD_DAYS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return bonusReference;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BonusReference createUpdatedEntity(EntityManager em) {
        BonusReference bonusReference = new BonusReference()
            .percentage(UPDATED_PERCENTAGE)
            .holdDays(UPDATED_HOLD_DAYS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return bonusReference;
    }

    @BeforeEach
    public void initTest() {
        bonusReference = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonusReference() throws Exception {
        int databaseSizeBeforeCreate = bonusReferenceRepository.findAll().size();

        // Create the BonusReference
        BonusReferenceDTO bonusReferenceDTO = bonusReferenceMapper.toDto(bonusReference);
        restBonusReferenceMockMvc.perform(post("/api/bonus-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusReferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the BonusReference in the database
        List<BonusReference> bonusReferenceList = bonusReferenceRepository.findAll();
        assertThat(bonusReferenceList).hasSize(databaseSizeBeforeCreate + 1);
        BonusReference testBonusReference = bonusReferenceList.get(bonusReferenceList.size() - 1);
        assertThat(testBonusReference.getPercentage()).isEqualTo(DEFAULT_PERCENTAGE);
        assertThat(testBonusReference.getHoldDays()).isEqualTo(DEFAULT_HOLD_DAYS);
        assertThat(testBonusReference.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBonusReference.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createBonusReferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonusReferenceRepository.findAll().size();

        // Create the BonusReference with an existing ID
        bonusReference.setId(1L);
        BonusReferenceDTO bonusReferenceDTO = bonusReferenceMapper.toDto(bonusReference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonusReferenceMockMvc.perform(post("/api/bonus-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BonusReference in the database
        List<BonusReference> bonusReferenceList = bonusReferenceRepository.findAll();
        assertThat(bonusReferenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusReferenceRepository.findAll().size();
        // set the field null
        bonusReference.setPercentage(null);

        // Create the BonusReference, which fails.
        BonusReferenceDTO bonusReferenceDTO = bonusReferenceMapper.toDto(bonusReference);

        restBonusReferenceMockMvc.perform(post("/api/bonus-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<BonusReference> bonusReferenceList = bonusReferenceRepository.findAll();
        assertThat(bonusReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHoldDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusReferenceRepository.findAll().size();
        // set the field null
        bonusReference.setHoldDays(null);

        // Create the BonusReference, which fails.
        BonusReferenceDTO bonusReferenceDTO = bonusReferenceMapper.toDto(bonusReference);

        restBonusReferenceMockMvc.perform(post("/api/bonus-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<BonusReference> bonusReferenceList = bonusReferenceRepository.findAll();
        assertThat(bonusReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusReferenceRepository.findAll().size();
        // set the field null
        bonusReference.setCreatedAt(null);

        // Create the BonusReference, which fails.
        BonusReferenceDTO bonusReferenceDTO = bonusReferenceMapper.toDto(bonusReference);

        restBonusReferenceMockMvc.perform(post("/api/bonus-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<BonusReference> bonusReferenceList = bonusReferenceRepository.findAll();
        assertThat(bonusReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusReferenceRepository.findAll().size();
        // set the field null
        bonusReference.setUpdatedAt(null);

        // Create the BonusReference, which fails.
        BonusReferenceDTO bonusReferenceDTO = bonusReferenceMapper.toDto(bonusReference);

        restBonusReferenceMockMvc.perform(post("/api/bonus-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusReferenceDTO)))
            .andExpect(status().isBadRequest());

        List<BonusReference> bonusReferenceList = bonusReferenceRepository.findAll();
        assertThat(bonusReferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBonusReferences() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList
        restBonusReferenceMockMvc.perform(get("/api/bonus-references?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonusReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].holdDays").value(hasItem(DEFAULT_HOLD_DAYS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getBonusReference() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get the bonusReference
        restBonusReferenceMockMvc.perform(get("/api/bonus-references/{id}", bonusReference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bonusReference.getId().intValue()))
            .andExpect(jsonPath("$.percentage").value(DEFAULT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.holdDays").value(DEFAULT_HOLD_DAYS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getBonusReferencesByIdFiltering() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        Long id = bonusReference.getId();

        defaultBonusReferenceShouldBeFound("id.equals=" + id);
        defaultBonusReferenceShouldNotBeFound("id.notEquals=" + id);

        defaultBonusReferenceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBonusReferenceShouldNotBeFound("id.greaterThan=" + id);

        defaultBonusReferenceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBonusReferenceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBonusReferencesByPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where percentage equals to DEFAULT_PERCENTAGE
        defaultBonusReferenceShouldBeFound("percentage.equals=" + DEFAULT_PERCENTAGE);

        // Get all the bonusReferenceList where percentage equals to UPDATED_PERCENTAGE
        defaultBonusReferenceShouldNotBeFound("percentage.equals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where percentage not equals to DEFAULT_PERCENTAGE
        defaultBonusReferenceShouldNotBeFound("percentage.notEquals=" + DEFAULT_PERCENTAGE);

        // Get all the bonusReferenceList where percentage not equals to UPDATED_PERCENTAGE
        defaultBonusReferenceShouldBeFound("percentage.notEquals=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where percentage in DEFAULT_PERCENTAGE or UPDATED_PERCENTAGE
        defaultBonusReferenceShouldBeFound("percentage.in=" + DEFAULT_PERCENTAGE + "," + UPDATED_PERCENTAGE);

        // Get all the bonusReferenceList where percentage equals to UPDATED_PERCENTAGE
        defaultBonusReferenceShouldNotBeFound("percentage.in=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where percentage is not null
        defaultBonusReferenceShouldBeFound("percentage.specified=true");

        // Get all the bonusReferenceList where percentage is null
        defaultBonusReferenceShouldNotBeFound("percentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where percentage is greater than or equal to DEFAULT_PERCENTAGE
        defaultBonusReferenceShouldBeFound("percentage.greaterThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the bonusReferenceList where percentage is greater than or equal to UPDATED_PERCENTAGE
        defaultBonusReferenceShouldNotBeFound("percentage.greaterThanOrEqual=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where percentage is less than or equal to DEFAULT_PERCENTAGE
        defaultBonusReferenceShouldBeFound("percentage.lessThanOrEqual=" + DEFAULT_PERCENTAGE);

        // Get all the bonusReferenceList where percentage is less than or equal to SMALLER_PERCENTAGE
        defaultBonusReferenceShouldNotBeFound("percentage.lessThanOrEqual=" + SMALLER_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where percentage is less than DEFAULT_PERCENTAGE
        defaultBonusReferenceShouldNotBeFound("percentage.lessThan=" + DEFAULT_PERCENTAGE);

        // Get all the bonusReferenceList where percentage is less than UPDATED_PERCENTAGE
        defaultBonusReferenceShouldBeFound("percentage.lessThan=" + UPDATED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where percentage is greater than DEFAULT_PERCENTAGE
        defaultBonusReferenceShouldNotBeFound("percentage.greaterThan=" + DEFAULT_PERCENTAGE);

        // Get all the bonusReferenceList where percentage is greater than SMALLER_PERCENTAGE
        defaultBonusReferenceShouldBeFound("percentage.greaterThan=" + SMALLER_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllBonusReferencesByHoldDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where holdDays equals to DEFAULT_HOLD_DAYS
        defaultBonusReferenceShouldBeFound("holdDays.equals=" + DEFAULT_HOLD_DAYS);

        // Get all the bonusReferenceList where holdDays equals to UPDATED_HOLD_DAYS
        defaultBonusReferenceShouldNotBeFound("holdDays.equals=" + UPDATED_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByHoldDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where holdDays not equals to DEFAULT_HOLD_DAYS
        defaultBonusReferenceShouldNotBeFound("holdDays.notEquals=" + DEFAULT_HOLD_DAYS);

        // Get all the bonusReferenceList where holdDays not equals to UPDATED_HOLD_DAYS
        defaultBonusReferenceShouldBeFound("holdDays.notEquals=" + UPDATED_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByHoldDaysIsInShouldWork() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where holdDays in DEFAULT_HOLD_DAYS or UPDATED_HOLD_DAYS
        defaultBonusReferenceShouldBeFound("holdDays.in=" + DEFAULT_HOLD_DAYS + "," + UPDATED_HOLD_DAYS);

        // Get all the bonusReferenceList where holdDays equals to UPDATED_HOLD_DAYS
        defaultBonusReferenceShouldNotBeFound("holdDays.in=" + UPDATED_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByHoldDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where holdDays is not null
        defaultBonusReferenceShouldBeFound("holdDays.specified=true");

        // Get all the bonusReferenceList where holdDays is null
        defaultBonusReferenceShouldNotBeFound("holdDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByHoldDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where holdDays is greater than or equal to DEFAULT_HOLD_DAYS
        defaultBonusReferenceShouldBeFound("holdDays.greaterThanOrEqual=" + DEFAULT_HOLD_DAYS);

        // Get all the bonusReferenceList where holdDays is greater than or equal to UPDATED_HOLD_DAYS
        defaultBonusReferenceShouldNotBeFound("holdDays.greaterThanOrEqual=" + UPDATED_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByHoldDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where holdDays is less than or equal to DEFAULT_HOLD_DAYS
        defaultBonusReferenceShouldBeFound("holdDays.lessThanOrEqual=" + DEFAULT_HOLD_DAYS);

        // Get all the bonusReferenceList where holdDays is less than or equal to SMALLER_HOLD_DAYS
        defaultBonusReferenceShouldNotBeFound("holdDays.lessThanOrEqual=" + SMALLER_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByHoldDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where holdDays is less than DEFAULT_HOLD_DAYS
        defaultBonusReferenceShouldNotBeFound("holdDays.lessThan=" + DEFAULT_HOLD_DAYS);

        // Get all the bonusReferenceList where holdDays is less than UPDATED_HOLD_DAYS
        defaultBonusReferenceShouldBeFound("holdDays.lessThan=" + UPDATED_HOLD_DAYS);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByHoldDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where holdDays is greater than DEFAULT_HOLD_DAYS
        defaultBonusReferenceShouldNotBeFound("holdDays.greaterThan=" + DEFAULT_HOLD_DAYS);

        // Get all the bonusReferenceList where holdDays is greater than SMALLER_HOLD_DAYS
        defaultBonusReferenceShouldBeFound("holdDays.greaterThan=" + SMALLER_HOLD_DAYS);
    }


    @Test
    @Transactional
    public void getAllBonusReferencesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where createdAt equals to DEFAULT_CREATED_AT
        defaultBonusReferenceShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the bonusReferenceList where createdAt equals to UPDATED_CREATED_AT
        defaultBonusReferenceShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where createdAt not equals to DEFAULT_CREATED_AT
        defaultBonusReferenceShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the bonusReferenceList where createdAt not equals to UPDATED_CREATED_AT
        defaultBonusReferenceShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultBonusReferenceShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the bonusReferenceList where createdAt equals to UPDATED_CREATED_AT
        defaultBonusReferenceShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where createdAt is not null
        defaultBonusReferenceShouldBeFound("createdAt.specified=true");

        // Get all the bonusReferenceList where createdAt is null
        defaultBonusReferenceShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultBonusReferenceShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the bonusReferenceList where updatedAt equals to UPDATED_UPDATED_AT
        defaultBonusReferenceShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultBonusReferenceShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the bonusReferenceList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultBonusReferenceShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultBonusReferenceShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the bonusReferenceList where updatedAt equals to UPDATED_UPDATED_AT
        defaultBonusReferenceShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusReferencesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        // Get all the bonusReferenceList where updatedAt is not null
        defaultBonusReferenceShouldBeFound("updatedAt.specified=true");

        // Get all the bonusReferenceList where updatedAt is null
        defaultBonusReferenceShouldNotBeFound("updatedAt.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBonusReferenceShouldBeFound(String filter) throws Exception {
        restBonusReferenceMockMvc.perform(get("/api/bonus-references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonusReference.getId().intValue())))
            .andExpect(jsonPath("$.[*].percentage").value(hasItem(DEFAULT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].holdDays").value(hasItem(DEFAULT_HOLD_DAYS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restBonusReferenceMockMvc.perform(get("/api/bonus-references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBonusReferenceShouldNotBeFound(String filter) throws Exception {
        restBonusReferenceMockMvc.perform(get("/api/bonus-references?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBonusReferenceMockMvc.perform(get("/api/bonus-references/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBonusReference() throws Exception {
        // Get the bonusReference
        restBonusReferenceMockMvc.perform(get("/api/bonus-references/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonusReference() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        int databaseSizeBeforeUpdate = bonusReferenceRepository.findAll().size();

        // Update the bonusReference
        BonusReference updatedBonusReference = bonusReferenceRepository.findById(bonusReference.getId()).get();
        // Disconnect from session so that the updates on updatedBonusReference are not directly saved in db
        em.detach(updatedBonusReference);
        updatedBonusReference
            .percentage(UPDATED_PERCENTAGE)
            .holdDays(UPDATED_HOLD_DAYS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        BonusReferenceDTO bonusReferenceDTO = bonusReferenceMapper.toDto(updatedBonusReference);

        restBonusReferenceMockMvc.perform(put("/api/bonus-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusReferenceDTO)))
            .andExpect(status().isOk());

        // Validate the BonusReference in the database
        List<BonusReference> bonusReferenceList = bonusReferenceRepository.findAll();
        assertThat(bonusReferenceList).hasSize(databaseSizeBeforeUpdate);
        BonusReference testBonusReference = bonusReferenceList.get(bonusReferenceList.size() - 1);
        assertThat(testBonusReference.getPercentage()).isEqualTo(UPDATED_PERCENTAGE);
        assertThat(testBonusReference.getHoldDays()).isEqualTo(UPDATED_HOLD_DAYS);
        assertThat(testBonusReference.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBonusReference.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingBonusReference() throws Exception {
        int databaseSizeBeforeUpdate = bonusReferenceRepository.findAll().size();

        // Create the BonusReference
        BonusReferenceDTO bonusReferenceDTO = bonusReferenceMapper.toDto(bonusReference);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonusReferenceMockMvc.perform(put("/api/bonus-references")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusReferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BonusReference in the database
        List<BonusReference> bonusReferenceList = bonusReferenceRepository.findAll();
        assertThat(bonusReferenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBonusReference() throws Exception {
        // Initialize the database
        bonusReferenceRepository.saveAndFlush(bonusReference);

        int databaseSizeBeforeDelete = bonusReferenceRepository.findAll().size();

        // Delete the bonusReference
        restBonusReferenceMockMvc.perform(delete("/api/bonus-references/{id}", bonusReference.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BonusReference> bonusReferenceList = bonusReferenceRepository.findAll();
        assertThat(bonusReferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
