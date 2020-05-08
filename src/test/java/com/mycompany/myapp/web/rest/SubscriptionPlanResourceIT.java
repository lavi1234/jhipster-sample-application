package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.SubscriptionPlan;
import com.mycompany.myapp.domain.Localization;
import com.mycompany.myapp.domain.SubsriptionPlanFeature;
import com.mycompany.myapp.repository.SubscriptionPlanRepository;
import com.mycompany.myapp.service.SubscriptionPlanService;
import com.mycompany.myapp.service.dto.SubscriptionPlanDTO;
import com.mycompany.myapp.service.mapper.SubscriptionPlanMapper;
import com.mycompany.myapp.service.dto.SubscriptionPlanCriteria;
import com.mycompany.myapp.service.SubscriptionPlanQueryService;

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
 * Integration tests for the {@link SubscriptionPlanResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SubscriptionPlanResourceIT {

    private static final String DEFAULT_VALIDITY = "AAAAAAAAAA";
    private static final String UPDATED_VALIDITY = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;
    private static final Integer SMALLER_CREATED_BY = 1 - 1;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Autowired
    private SubscriptionPlanMapper subscriptionPlanMapper;

    @Autowired
    private SubscriptionPlanService subscriptionPlanService;

    @Autowired
    private SubscriptionPlanQueryService subscriptionPlanQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubscriptionPlanMockMvc;

    private SubscriptionPlan subscriptionPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionPlan createEntity(EntityManager em) {
        SubscriptionPlan subscriptionPlan = new SubscriptionPlan()
            .validity(DEFAULT_VALIDITY)
            .price(DEFAULT_PRICE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        Localization localization;
        if (TestUtil.findAll(em, Localization.class).isEmpty()) {
            localization = LocalizationResourceIT.createEntity(em);
            em.persist(localization);
            em.flush();
        } else {
            localization = TestUtil.findAll(em, Localization.class).get(0);
        }
        subscriptionPlan.setName(localization);
        // Add required entity
        subscriptionPlan.setDescription(localization);
        return subscriptionPlan;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubscriptionPlan createUpdatedEntity(EntityManager em) {
        SubscriptionPlan subscriptionPlan = new SubscriptionPlan()
            .validity(UPDATED_VALIDITY)
            .price(UPDATED_PRICE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        Localization localization;
        if (TestUtil.findAll(em, Localization.class).isEmpty()) {
            localization = LocalizationResourceIT.createUpdatedEntity(em);
            em.persist(localization);
            em.flush();
        } else {
            localization = TestUtil.findAll(em, Localization.class).get(0);
        }
        subscriptionPlan.setName(localization);
        // Add required entity
        subscriptionPlan.setDescription(localization);
        return subscriptionPlan;
    }

    @BeforeEach
    public void initTest() {
        subscriptionPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubscriptionPlan() throws Exception {
        int databaseSizeBeforeCreate = subscriptionPlanRepository.findAll().size();

        // Create the SubscriptionPlan
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(subscriptionPlan);
        restSubscriptionPlanMockMvc.perform(post("/api/subscription-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isCreated());

        // Validate the SubscriptionPlan in the database
        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeCreate + 1);
        SubscriptionPlan testSubscriptionPlan = subscriptionPlanList.get(subscriptionPlanList.size() - 1);
        assertThat(testSubscriptionPlan.getValidity()).isEqualTo(DEFAULT_VALIDITY);
        assertThat(testSubscriptionPlan.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testSubscriptionPlan.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testSubscriptionPlan.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testSubscriptionPlan.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createSubscriptionPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscriptionPlanRepository.findAll().size();

        // Create the SubscriptionPlan with an existing ID
        subscriptionPlan.setId(1L);
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(subscriptionPlan);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionPlanMockMvc.perform(post("/api/subscription-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionPlan in the database
        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValidityIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionPlanRepository.findAll().size();
        // set the field null
        subscriptionPlan.setValidity(null);

        // Create the SubscriptionPlan, which fails.
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(subscriptionPlan);

        restSubscriptionPlanMockMvc.perform(post("/api/subscription-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isBadRequest());

        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionPlanRepository.findAll().size();
        // set the field null
        subscriptionPlan.setPrice(null);

        // Create the SubscriptionPlan, which fails.
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(subscriptionPlan);

        restSubscriptionPlanMockMvc.perform(post("/api/subscription-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isBadRequest());

        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionPlanRepository.findAll().size();
        // set the field null
        subscriptionPlan.setCreatedBy(null);

        // Create the SubscriptionPlan, which fails.
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(subscriptionPlan);

        restSubscriptionPlanMockMvc.perform(post("/api/subscription-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isBadRequest());

        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionPlanRepository.findAll().size();
        // set the field null
        subscriptionPlan.setCreatedAt(null);

        // Create the SubscriptionPlan, which fails.
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(subscriptionPlan);

        restSubscriptionPlanMockMvc.perform(post("/api/subscription-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isBadRequest());

        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionPlanRepository.findAll().size();
        // set the field null
        subscriptionPlan.setUpdatedAt(null);

        // Create the SubscriptionPlan, which fails.
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(subscriptionPlan);

        restSubscriptionPlanMockMvc.perform(post("/api/subscription-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isBadRequest());

        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlans() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList
        restSubscriptionPlanMockMvc.perform(get("/api/subscription-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptionPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].validity").value(hasItem(DEFAULT_VALIDITY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getSubscriptionPlan() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get the subscriptionPlan
        restSubscriptionPlanMockMvc.perform(get("/api/subscription-plans/{id}", subscriptionPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subscriptionPlan.getId().intValue()))
            .andExpect(jsonPath("$.validity").value(DEFAULT_VALIDITY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getSubscriptionPlansByIdFiltering() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        Long id = subscriptionPlan.getId();

        defaultSubscriptionPlanShouldBeFound("id.equals=" + id);
        defaultSubscriptionPlanShouldNotBeFound("id.notEquals=" + id);

        defaultSubscriptionPlanShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubscriptionPlanShouldNotBeFound("id.greaterThan=" + id);

        defaultSubscriptionPlanShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubscriptionPlanShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSubscriptionPlansByValidityIsEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where validity equals to DEFAULT_VALIDITY
        defaultSubscriptionPlanShouldBeFound("validity.equals=" + DEFAULT_VALIDITY);

        // Get all the subscriptionPlanList where validity equals to UPDATED_VALIDITY
        defaultSubscriptionPlanShouldNotBeFound("validity.equals=" + UPDATED_VALIDITY);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByValidityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where validity not equals to DEFAULT_VALIDITY
        defaultSubscriptionPlanShouldNotBeFound("validity.notEquals=" + DEFAULT_VALIDITY);

        // Get all the subscriptionPlanList where validity not equals to UPDATED_VALIDITY
        defaultSubscriptionPlanShouldBeFound("validity.notEquals=" + UPDATED_VALIDITY);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByValidityIsInShouldWork() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where validity in DEFAULT_VALIDITY or UPDATED_VALIDITY
        defaultSubscriptionPlanShouldBeFound("validity.in=" + DEFAULT_VALIDITY + "," + UPDATED_VALIDITY);

        // Get all the subscriptionPlanList where validity equals to UPDATED_VALIDITY
        defaultSubscriptionPlanShouldNotBeFound("validity.in=" + UPDATED_VALIDITY);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByValidityIsNullOrNotNull() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where validity is not null
        defaultSubscriptionPlanShouldBeFound("validity.specified=true");

        // Get all the subscriptionPlanList where validity is null
        defaultSubscriptionPlanShouldNotBeFound("validity.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubscriptionPlansByValidityContainsSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where validity contains DEFAULT_VALIDITY
        defaultSubscriptionPlanShouldBeFound("validity.contains=" + DEFAULT_VALIDITY);

        // Get all the subscriptionPlanList where validity contains UPDATED_VALIDITY
        defaultSubscriptionPlanShouldNotBeFound("validity.contains=" + UPDATED_VALIDITY);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByValidityNotContainsSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where validity does not contain DEFAULT_VALIDITY
        defaultSubscriptionPlanShouldNotBeFound("validity.doesNotContain=" + DEFAULT_VALIDITY);

        // Get all the subscriptionPlanList where validity does not contain UPDATED_VALIDITY
        defaultSubscriptionPlanShouldBeFound("validity.doesNotContain=" + UPDATED_VALIDITY);
    }


    @Test
    @Transactional
    public void getAllSubscriptionPlansByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where price equals to DEFAULT_PRICE
        defaultSubscriptionPlanShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the subscriptionPlanList where price equals to UPDATED_PRICE
        defaultSubscriptionPlanShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where price not equals to DEFAULT_PRICE
        defaultSubscriptionPlanShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the subscriptionPlanList where price not equals to UPDATED_PRICE
        defaultSubscriptionPlanShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultSubscriptionPlanShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the subscriptionPlanList where price equals to UPDATED_PRICE
        defaultSubscriptionPlanShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where price is not null
        defaultSubscriptionPlanShouldBeFound("price.specified=true");

        // Get all the subscriptionPlanList where price is null
        defaultSubscriptionPlanShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where price is greater than or equal to DEFAULT_PRICE
        defaultSubscriptionPlanShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the subscriptionPlanList where price is greater than or equal to UPDATED_PRICE
        defaultSubscriptionPlanShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where price is less than or equal to DEFAULT_PRICE
        defaultSubscriptionPlanShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the subscriptionPlanList where price is less than or equal to SMALLER_PRICE
        defaultSubscriptionPlanShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where price is less than DEFAULT_PRICE
        defaultSubscriptionPlanShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the subscriptionPlanList where price is less than UPDATED_PRICE
        defaultSubscriptionPlanShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where price is greater than DEFAULT_PRICE
        defaultSubscriptionPlanShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the subscriptionPlanList where price is greater than SMALLER_PRICE
        defaultSubscriptionPlanShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdBy equals to DEFAULT_CREATED_BY
        defaultSubscriptionPlanShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the subscriptionPlanList where createdBy equals to UPDATED_CREATED_BY
        defaultSubscriptionPlanShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdBy not equals to DEFAULT_CREATED_BY
        defaultSubscriptionPlanShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the subscriptionPlanList where createdBy not equals to UPDATED_CREATED_BY
        defaultSubscriptionPlanShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultSubscriptionPlanShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the subscriptionPlanList where createdBy equals to UPDATED_CREATED_BY
        defaultSubscriptionPlanShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdBy is not null
        defaultSubscriptionPlanShouldBeFound("createdBy.specified=true");

        // Get all the subscriptionPlanList where createdBy is null
        defaultSubscriptionPlanShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdBy is greater than or equal to DEFAULT_CREATED_BY
        defaultSubscriptionPlanShouldBeFound("createdBy.greaterThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the subscriptionPlanList where createdBy is greater than or equal to UPDATED_CREATED_BY
        defaultSubscriptionPlanShouldNotBeFound("createdBy.greaterThanOrEqual=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedByIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdBy is less than or equal to DEFAULT_CREATED_BY
        defaultSubscriptionPlanShouldBeFound("createdBy.lessThanOrEqual=" + DEFAULT_CREATED_BY);

        // Get all the subscriptionPlanList where createdBy is less than or equal to SMALLER_CREATED_BY
        defaultSubscriptionPlanShouldNotBeFound("createdBy.lessThanOrEqual=" + SMALLER_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedByIsLessThanSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdBy is less than DEFAULT_CREATED_BY
        defaultSubscriptionPlanShouldNotBeFound("createdBy.lessThan=" + DEFAULT_CREATED_BY);

        // Get all the subscriptionPlanList where createdBy is less than UPDATED_CREATED_BY
        defaultSubscriptionPlanShouldBeFound("createdBy.lessThan=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedByIsGreaterThanSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdBy is greater than DEFAULT_CREATED_BY
        defaultSubscriptionPlanShouldNotBeFound("createdBy.greaterThan=" + DEFAULT_CREATED_BY);

        // Get all the subscriptionPlanList where createdBy is greater than SMALLER_CREATED_BY
        defaultSubscriptionPlanShouldBeFound("createdBy.greaterThan=" + SMALLER_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdAt equals to DEFAULT_CREATED_AT
        defaultSubscriptionPlanShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the subscriptionPlanList where createdAt equals to UPDATED_CREATED_AT
        defaultSubscriptionPlanShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdAt not equals to DEFAULT_CREATED_AT
        defaultSubscriptionPlanShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the subscriptionPlanList where createdAt not equals to UPDATED_CREATED_AT
        defaultSubscriptionPlanShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultSubscriptionPlanShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the subscriptionPlanList where createdAt equals to UPDATED_CREATED_AT
        defaultSubscriptionPlanShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where createdAt is not null
        defaultSubscriptionPlanShouldBeFound("createdAt.specified=true");

        // Get all the subscriptionPlanList where createdAt is null
        defaultSubscriptionPlanShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultSubscriptionPlanShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the subscriptionPlanList where updatedAt equals to UPDATED_UPDATED_AT
        defaultSubscriptionPlanShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultSubscriptionPlanShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the subscriptionPlanList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultSubscriptionPlanShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultSubscriptionPlanShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the subscriptionPlanList where updatedAt equals to UPDATED_UPDATED_AT
        defaultSubscriptionPlanShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        // Get all the subscriptionPlanList where updatedAt is not null
        defaultSubscriptionPlanShouldBeFound("updatedAt.specified=true");

        // Get all the subscriptionPlanList where updatedAt is null
        defaultSubscriptionPlanShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubscriptionPlansByNameIsEqualToSomething() throws Exception {
        // Get already existing entity
        Localization name = subscriptionPlan.getName();
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);
        Long nameId = name.getId();

        // Get all the subscriptionPlanList where name equals to nameId
        defaultSubscriptionPlanShouldBeFound("nameId.equals=" + nameId);

        // Get all the subscriptionPlanList where name equals to nameId + 1
        defaultSubscriptionPlanShouldNotBeFound("nameId.equals=" + (nameId + 1));
    }


    @Test
    @Transactional
    public void getAllSubscriptionPlansByDescriptionIsEqualToSomething() throws Exception {
        // Get already existing entity
        Localization description = subscriptionPlan.getDescription();
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);
        Long descriptionId = description.getId();

        // Get all the subscriptionPlanList where description equals to descriptionId
        defaultSubscriptionPlanShouldBeFound("descriptionId.equals=" + descriptionId);

        // Get all the subscriptionPlanList where description equals to descriptionId + 1
        defaultSubscriptionPlanShouldNotBeFound("descriptionId.equals=" + (descriptionId + 1));
    }


    @Test
    @Transactional
    public void getAllSubscriptionPlansBySubsriptionPlanFeatureIsEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);
        SubsriptionPlanFeature subsriptionPlanFeature = SubsriptionPlanFeatureResourceIT.createEntity(em);
        em.persist(subsriptionPlanFeature);
        em.flush();
        subscriptionPlan.addSubsriptionPlanFeature(subsriptionPlanFeature);
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);
        Long subsriptionPlanFeatureId = subsriptionPlanFeature.getId();

        // Get all the subscriptionPlanList where subsriptionPlanFeature equals to subsriptionPlanFeatureId
        defaultSubscriptionPlanShouldBeFound("subsriptionPlanFeatureId.equals=" + subsriptionPlanFeatureId);

        // Get all the subscriptionPlanList where subsriptionPlanFeature equals to subsriptionPlanFeatureId + 1
        defaultSubscriptionPlanShouldNotBeFound("subsriptionPlanFeatureId.equals=" + (subsriptionPlanFeatureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubscriptionPlanShouldBeFound(String filter) throws Exception {
        restSubscriptionPlanMockMvc.perform(get("/api/subscription-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptionPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].validity").value(hasItem(DEFAULT_VALIDITY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restSubscriptionPlanMockMvc.perform(get("/api/subscription-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubscriptionPlanShouldNotBeFound(String filter) throws Exception {
        restSubscriptionPlanMockMvc.perform(get("/api/subscription-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubscriptionPlanMockMvc.perform(get("/api/subscription-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSubscriptionPlan() throws Exception {
        // Get the subscriptionPlan
        restSubscriptionPlanMockMvc.perform(get("/api/subscription-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriptionPlan() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        int databaseSizeBeforeUpdate = subscriptionPlanRepository.findAll().size();

        // Update the subscriptionPlan
        SubscriptionPlan updatedSubscriptionPlan = subscriptionPlanRepository.findById(subscriptionPlan.getId()).get();
        // Disconnect from session so that the updates on updatedSubscriptionPlan are not directly saved in db
        em.detach(updatedSubscriptionPlan);
        updatedSubscriptionPlan
            .validity(UPDATED_VALIDITY)
            .price(UPDATED_PRICE)
            .createdBy(UPDATED_CREATED_BY)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(updatedSubscriptionPlan);

        restSubscriptionPlanMockMvc.perform(put("/api/subscription-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isOk());

        // Validate the SubscriptionPlan in the database
        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
        SubscriptionPlan testSubscriptionPlan = subscriptionPlanList.get(subscriptionPlanList.size() - 1);
        assertThat(testSubscriptionPlan.getValidity()).isEqualTo(UPDATED_VALIDITY);
        assertThat(testSubscriptionPlan.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testSubscriptionPlan.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testSubscriptionPlan.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testSubscriptionPlan.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingSubscriptionPlan() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionPlanRepository.findAll().size();

        // Create the SubscriptionPlan
        SubscriptionPlanDTO subscriptionPlanDTO = subscriptionPlanMapper.toDto(subscriptionPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscriptionPlanMockMvc.perform(put("/api/subscription-plans")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubscriptionPlan in the database
        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubscriptionPlan() throws Exception {
        // Initialize the database
        subscriptionPlanRepository.saveAndFlush(subscriptionPlan);

        int databaseSizeBeforeDelete = subscriptionPlanRepository.findAll().size();

        // Delete the subscriptionPlan
        restSubscriptionPlanMockMvc.perform(delete("/api/subscription-plans/{id}", subscriptionPlan.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubscriptionPlan> subscriptionPlanList = subscriptionPlanRepository.findAll();
        assertThat(subscriptionPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
