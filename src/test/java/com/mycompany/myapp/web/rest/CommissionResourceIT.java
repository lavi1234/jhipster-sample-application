package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Commission;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.domain.Order;
import com.mycompany.myapp.repository.CommissionRepository;
import com.mycompany.myapp.service.CommissionService;
import com.mycompany.myapp.service.dto.CommissionDTO;
import com.mycompany.myapp.service.mapper.CommissionMapper;
import com.mycompany.myapp.service.dto.CommissionCriteria;
import com.mycompany.myapp.service.CommissionQueryService;

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
 * Integration tests for the {@link CommissionResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CommissionResourceIT {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;
    private static final Double SMALLER_AMOUNT = 1D - 1D;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PRINCIPAL_AMOUNT = 1D;
    private static final Double UPDATED_PRINCIPAL_AMOUNT = 2D;
    private static final Double SMALLER_PRINCIPAL_AMOUNT = 1D - 1D;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private CommissionRepository commissionRepository;

    @Autowired
    private CommissionMapper commissionMapper;

    @Autowired
    private CommissionService commissionService;

    @Autowired
    private CommissionQueryService commissionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommissionMockMvc;

    private Commission commission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commission createEntity(EntityManager em) {
        Commission commission = new Commission()
            .amount(DEFAULT_AMOUNT)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .principalAmount(DEFAULT_PRINCIPAL_AMOUNT)
            .status(DEFAULT_STATUS)
            .remarks(DEFAULT_REMARKS);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        commission.setSupplier(userProfile);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createEntity(em);
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        commission.setOrder(order);
        return commission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commission createUpdatedEntity(EntityManager em) {
        Commission commission = new Commission()
            .amount(UPDATED_AMOUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .principalAmount(UPDATED_PRINCIPAL_AMOUNT)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        commission.setSupplier(userProfile);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createUpdatedEntity(em);
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        commission.setOrder(order);
        return commission;
    }

    @BeforeEach
    public void initTest() {
        commission = createEntity(em);
    }

    @Test
    @Transactional
    public void createCommission() throws Exception {
        int databaseSizeBeforeCreate = commissionRepository.findAll().size();

        // Create the Commission
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);
        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isCreated());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeCreate + 1);
        Commission testCommission = commissionList.get(commissionList.size() - 1);
        assertThat(testCommission.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCommission.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCommission.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testCommission.getPrincipalAmount()).isEqualTo(DEFAULT_PRINCIPAL_AMOUNT);
        assertThat(testCommission.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCommission.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createCommissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commissionRepository.findAll().size();

        // Create the Commission with an existing ID
        commission.setId(1L);
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionRepository.findAll().size();
        // set the field null
        commission.setAmount(null);

        // Create the Commission, which fails.
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);

        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isBadRequest());

        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionRepository.findAll().size();
        // set the field null
        commission.setCreatedAt(null);

        // Create the Commission, which fails.
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);

        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isBadRequest());

        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionRepository.findAll().size();
        // set the field null
        commission.setUpdatedAt(null);

        // Create the Commission, which fails.
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);

        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isBadRequest());

        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrincipalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionRepository.findAll().size();
        // set the field null
        commission.setPrincipalAmount(null);

        // Create the Commission, which fails.
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);

        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isBadRequest());

        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = commissionRepository.findAll().size();
        // set the field null
        commission.setStatus(null);

        // Create the Commission, which fails.
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);

        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isBadRequest());

        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCommissions() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList
        restCommissionMockMvc.perform(get("/api/commissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commission.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].principalAmount").value(hasItem(DEFAULT_PRINCIPAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getCommission() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get the commission
        restCommissionMockMvc.perform(get("/api/commissions/{id}", commission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commission.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.principalAmount").value(DEFAULT_PRINCIPAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }


    @Test
    @Transactional
    public void getCommissionsByIdFiltering() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        Long id = commission.getId();

        defaultCommissionShouldBeFound("id.equals=" + id);
        defaultCommissionShouldNotBeFound("id.notEquals=" + id);

        defaultCommissionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCommissionShouldNotBeFound("id.greaterThan=" + id);

        defaultCommissionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCommissionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCommissionsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where amount equals to DEFAULT_AMOUNT
        defaultCommissionShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the commissionList where amount equals to UPDATED_AMOUNT
        defaultCommissionShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where amount not equals to DEFAULT_AMOUNT
        defaultCommissionShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the commissionList where amount not equals to UPDATED_AMOUNT
        defaultCommissionShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCommissionShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the commissionList where amount equals to UPDATED_AMOUNT
        defaultCommissionShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where amount is not null
        defaultCommissionShouldBeFound("amount.specified=true");

        // Get all the commissionList where amount is null
        defaultCommissionShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommissionsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultCommissionShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the commissionList where amount is greater than or equal to UPDATED_AMOUNT
        defaultCommissionShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where amount is less than or equal to DEFAULT_AMOUNT
        defaultCommissionShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the commissionList where amount is less than or equal to SMALLER_AMOUNT
        defaultCommissionShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where amount is less than DEFAULT_AMOUNT
        defaultCommissionShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the commissionList where amount is less than UPDATED_AMOUNT
        defaultCommissionShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where amount is greater than DEFAULT_AMOUNT
        defaultCommissionShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the commissionList where amount is greater than SMALLER_AMOUNT
        defaultCommissionShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCommissionsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where createdAt equals to DEFAULT_CREATED_AT
        defaultCommissionShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the commissionList where createdAt equals to UPDATED_CREATED_AT
        defaultCommissionShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where createdAt not equals to DEFAULT_CREATED_AT
        defaultCommissionShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the commissionList where createdAt not equals to UPDATED_CREATED_AT
        defaultCommissionShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultCommissionShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the commissionList where createdAt equals to UPDATED_CREATED_AT
        defaultCommissionShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where createdAt is not null
        defaultCommissionShouldBeFound("createdAt.specified=true");

        // Get all the commissionList where createdAt is null
        defaultCommissionShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommissionsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultCommissionShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the commissionList where updatedAt equals to UPDATED_UPDATED_AT
        defaultCommissionShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultCommissionShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the commissionList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultCommissionShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultCommissionShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the commissionList where updatedAt equals to UPDATED_UPDATED_AT
        defaultCommissionShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where updatedAt is not null
        defaultCommissionShouldBeFound("updatedAt.specified=true");

        // Get all the commissionList where updatedAt is null
        defaultCommissionShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommissionsByPrincipalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where principalAmount equals to DEFAULT_PRINCIPAL_AMOUNT
        defaultCommissionShouldBeFound("principalAmount.equals=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the commissionList where principalAmount equals to UPDATED_PRINCIPAL_AMOUNT
        defaultCommissionShouldNotBeFound("principalAmount.equals=" + UPDATED_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByPrincipalAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where principalAmount not equals to DEFAULT_PRINCIPAL_AMOUNT
        defaultCommissionShouldNotBeFound("principalAmount.notEquals=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the commissionList where principalAmount not equals to UPDATED_PRINCIPAL_AMOUNT
        defaultCommissionShouldBeFound("principalAmount.notEquals=" + UPDATED_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByPrincipalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where principalAmount in DEFAULT_PRINCIPAL_AMOUNT or UPDATED_PRINCIPAL_AMOUNT
        defaultCommissionShouldBeFound("principalAmount.in=" + DEFAULT_PRINCIPAL_AMOUNT + "," + UPDATED_PRINCIPAL_AMOUNT);

        // Get all the commissionList where principalAmount equals to UPDATED_PRINCIPAL_AMOUNT
        defaultCommissionShouldNotBeFound("principalAmount.in=" + UPDATED_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByPrincipalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where principalAmount is not null
        defaultCommissionShouldBeFound("principalAmount.specified=true");

        // Get all the commissionList where principalAmount is null
        defaultCommissionShouldNotBeFound("principalAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCommissionsByPrincipalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where principalAmount is greater than or equal to DEFAULT_PRINCIPAL_AMOUNT
        defaultCommissionShouldBeFound("principalAmount.greaterThanOrEqual=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the commissionList where principalAmount is greater than or equal to UPDATED_PRINCIPAL_AMOUNT
        defaultCommissionShouldNotBeFound("principalAmount.greaterThanOrEqual=" + UPDATED_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByPrincipalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where principalAmount is less than or equal to DEFAULT_PRINCIPAL_AMOUNT
        defaultCommissionShouldBeFound("principalAmount.lessThanOrEqual=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the commissionList where principalAmount is less than or equal to SMALLER_PRINCIPAL_AMOUNT
        defaultCommissionShouldNotBeFound("principalAmount.lessThanOrEqual=" + SMALLER_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByPrincipalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where principalAmount is less than DEFAULT_PRINCIPAL_AMOUNT
        defaultCommissionShouldNotBeFound("principalAmount.lessThan=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the commissionList where principalAmount is less than UPDATED_PRINCIPAL_AMOUNT
        defaultCommissionShouldBeFound("principalAmount.lessThan=" + UPDATED_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCommissionsByPrincipalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where principalAmount is greater than DEFAULT_PRINCIPAL_AMOUNT
        defaultCommissionShouldNotBeFound("principalAmount.greaterThan=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the commissionList where principalAmount is greater than SMALLER_PRINCIPAL_AMOUNT
        defaultCommissionShouldBeFound("principalAmount.greaterThan=" + SMALLER_PRINCIPAL_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCommissionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where status equals to DEFAULT_STATUS
        defaultCommissionShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the commissionList where status equals to UPDATED_STATUS
        defaultCommissionShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommissionsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where status not equals to DEFAULT_STATUS
        defaultCommissionShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the commissionList where status not equals to UPDATED_STATUS
        defaultCommissionShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommissionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultCommissionShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the commissionList where status equals to UPDATED_STATUS
        defaultCommissionShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommissionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where status is not null
        defaultCommissionShouldBeFound("status.specified=true");

        // Get all the commissionList where status is null
        defaultCommissionShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommissionsByStatusContainsSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where status contains DEFAULT_STATUS
        defaultCommissionShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the commissionList where status contains UPDATED_STATUS
        defaultCommissionShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllCommissionsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where status does not contain DEFAULT_STATUS
        defaultCommissionShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the commissionList where status does not contain UPDATED_STATUS
        defaultCommissionShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllCommissionsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where remarks equals to DEFAULT_REMARKS
        defaultCommissionShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the commissionList where remarks equals to UPDATED_REMARKS
        defaultCommissionShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllCommissionsByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where remarks not equals to DEFAULT_REMARKS
        defaultCommissionShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the commissionList where remarks not equals to UPDATED_REMARKS
        defaultCommissionShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllCommissionsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultCommissionShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the commissionList where remarks equals to UPDATED_REMARKS
        defaultCommissionShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllCommissionsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where remarks is not null
        defaultCommissionShouldBeFound("remarks.specified=true");

        // Get all the commissionList where remarks is null
        defaultCommissionShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllCommissionsByRemarksContainsSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where remarks contains DEFAULT_REMARKS
        defaultCommissionShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the commissionList where remarks contains UPDATED_REMARKS
        defaultCommissionShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllCommissionsByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        // Get all the commissionList where remarks does not contain DEFAULT_REMARKS
        defaultCommissionShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the commissionList where remarks does not contain UPDATED_REMARKS
        defaultCommissionShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllCommissionsBySupplierIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile supplier = commission.getSupplier();
        commissionRepository.saveAndFlush(commission);
        Long supplierId = supplier.getId();

        // Get all the commissionList where supplier equals to supplierId
        defaultCommissionShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the commissionList where supplier equals to supplierId + 1
        defaultCommissionShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllCommissionsByOrderIsEqualToSomething() throws Exception {
        // Get already existing entity
        Order order = commission.getOrder();
        commissionRepository.saveAndFlush(commission);
        Long orderId = order.getId();

        // Get all the commissionList where order equals to orderId
        defaultCommissionShouldBeFound("orderId.equals=" + orderId);

        // Get all the commissionList where order equals to orderId + 1
        defaultCommissionShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCommissionShouldBeFound(String filter) throws Exception {
        restCommissionMockMvc.perform(get("/api/commissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commission.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].principalAmount").value(hasItem(DEFAULT_PRINCIPAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restCommissionMockMvc.perform(get("/api/commissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCommissionShouldNotBeFound(String filter) throws Exception {
        restCommissionMockMvc.perform(get("/api/commissions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCommissionMockMvc.perform(get("/api/commissions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCommission() throws Exception {
        // Get the commission
        restCommissionMockMvc.perform(get("/api/commissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCommission() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();

        // Update the commission
        Commission updatedCommission = commissionRepository.findById(commission.getId()).get();
        // Disconnect from session so that the updates on updatedCommission are not directly saved in db
        em.detach(updatedCommission);
        updatedCommission
            .amount(UPDATED_AMOUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .principalAmount(UPDATED_PRINCIPAL_AMOUNT)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS);
        CommissionDTO commissionDTO = commissionMapper.toDto(updatedCommission);

        restCommissionMockMvc.perform(put("/api/commissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isOk());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
        Commission testCommission = commissionList.get(commissionList.size() - 1);
        assertThat(testCommission.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCommission.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCommission.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testCommission.getPrincipalAmount()).isEqualTo(UPDATED_PRINCIPAL_AMOUNT);
        assertThat(testCommission.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCommission.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingCommission() throws Exception {
        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();

        // Create the Commission
        CommissionDTO commissionDTO = commissionMapper.toDto(commission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionMockMvc.perform(put("/api/commissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(commissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCommission() throws Exception {
        // Initialize the database
        commissionRepository.saveAndFlush(commission);

        int databaseSizeBeforeDelete = commissionRepository.findAll().size();

        // Delete the commission
        restCommissionMockMvc.perform(delete("/api/commissions/{id}", commission.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
