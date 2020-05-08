package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Bonus;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.domain.Order;
import com.mycompany.myapp.repository.BonusRepository;
import com.mycompany.myapp.service.BonusService;
import com.mycompany.myapp.service.dto.BonusDTO;
import com.mycompany.myapp.service.mapper.BonusMapper;
import com.mycompany.myapp.service.dto.BonusCriteria;
import com.mycompany.myapp.service.BonusQueryService;

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
 * Integration tests for the {@link BonusResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BonusResourceIT {

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
    private BonusRepository bonusRepository;

    @Autowired
    private BonusMapper bonusMapper;

    @Autowired
    private BonusService bonusService;

    @Autowired
    private BonusQueryService bonusQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBonusMockMvc;

    private Bonus bonus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonus createEntity(EntityManager em) {
        Bonus bonus = new Bonus()
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
        bonus.setBuyer(userProfile);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createEntity(em);
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        bonus.setOrder(order);
        return bonus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bonus createUpdatedEntity(EntityManager em) {
        Bonus bonus = new Bonus()
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
        bonus.setBuyer(userProfile);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createUpdatedEntity(em);
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        bonus.setOrder(order);
        return bonus;
    }

    @BeforeEach
    public void initTest() {
        bonus = createEntity(em);
    }

    @Test
    @Transactional
    public void createBonus() throws Exception {
        int databaseSizeBeforeCreate = bonusRepository.findAll().size();

        // Create the Bonus
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);
        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isCreated());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeCreate + 1);
        Bonus testBonus = bonusList.get(bonusList.size() - 1);
        assertThat(testBonus.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBonus.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBonus.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testBonus.getPrincipalAmount()).isEqualTo(DEFAULT_PRINCIPAL_AMOUNT);
        assertThat(testBonus.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBonus.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createBonusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bonusRepository.findAll().size();

        // Create the Bonus with an existing ID
        bonus.setId(1L);
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusRepository.findAll().size();
        // set the field null
        bonus.setAmount(null);

        // Create the Bonus, which fails.
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusRepository.findAll().size();
        // set the field null
        bonus.setCreatedAt(null);

        // Create the Bonus, which fails.
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusRepository.findAll().size();
        // set the field null
        bonus.setUpdatedAt(null);

        // Create the Bonus, which fails.
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrincipalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusRepository.findAll().size();
        // set the field null
        bonus.setPrincipalAmount(null);

        // Create the Bonus, which fails.
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusRepository.findAll().size();
        // set the field null
        bonus.setStatus(null);

        // Create the Bonus, which fails.
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRemarksIsRequired() throws Exception {
        int databaseSizeBeforeTest = bonusRepository.findAll().size();
        // set the field null
        bonus.setRemarks(null);

        // Create the Bonus, which fails.
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        restBonusMockMvc.perform(post("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBonuses() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList
        restBonusMockMvc.perform(get("/api/bonuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonus.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].principalAmount").value(hasItem(DEFAULT_PRINCIPAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getBonus() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get the bonus
        restBonusMockMvc.perform(get("/api/bonuses/{id}", bonus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bonus.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.principalAmount").value(DEFAULT_PRINCIPAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }


    @Test
    @Transactional
    public void getBonusesByIdFiltering() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        Long id = bonus.getId();

        defaultBonusShouldBeFound("id.equals=" + id);
        defaultBonusShouldNotBeFound("id.notEquals=" + id);

        defaultBonusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBonusShouldNotBeFound("id.greaterThan=" + id);

        defaultBonusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBonusShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBonusesByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where amount equals to DEFAULT_AMOUNT
        defaultBonusShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the bonusList where amount equals to UPDATED_AMOUNT
        defaultBonusShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where amount not equals to DEFAULT_AMOUNT
        defaultBonusShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the bonusList where amount not equals to UPDATED_AMOUNT
        defaultBonusShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultBonusShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the bonusList where amount equals to UPDATED_AMOUNT
        defaultBonusShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where amount is not null
        defaultBonusShouldBeFound("amount.specified=true");

        // Get all the bonusList where amount is null
        defaultBonusShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllBonusesByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultBonusShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the bonusList where amount is greater than or equal to UPDATED_AMOUNT
        defaultBonusShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where amount is less than or equal to DEFAULT_AMOUNT
        defaultBonusShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the bonusList where amount is less than or equal to SMALLER_AMOUNT
        defaultBonusShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where amount is less than DEFAULT_AMOUNT
        defaultBonusShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the bonusList where amount is less than UPDATED_AMOUNT
        defaultBonusShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where amount is greater than DEFAULT_AMOUNT
        defaultBonusShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the bonusList where amount is greater than SMALLER_AMOUNT
        defaultBonusShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllBonusesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where createdAt equals to DEFAULT_CREATED_AT
        defaultBonusShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the bonusList where createdAt equals to UPDATED_CREATED_AT
        defaultBonusShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where createdAt not equals to DEFAULT_CREATED_AT
        defaultBonusShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the bonusList where createdAt not equals to UPDATED_CREATED_AT
        defaultBonusShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultBonusShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the bonusList where createdAt equals to UPDATED_CREATED_AT
        defaultBonusShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where createdAt is not null
        defaultBonusShouldBeFound("createdAt.specified=true");

        // Get all the bonusList where createdAt is null
        defaultBonusShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllBonusesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultBonusShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the bonusList where updatedAt equals to UPDATED_UPDATED_AT
        defaultBonusShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusesByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultBonusShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the bonusList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultBonusShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultBonusShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the bonusList where updatedAt equals to UPDATED_UPDATED_AT
        defaultBonusShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllBonusesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where updatedAt is not null
        defaultBonusShouldBeFound("updatedAt.specified=true");

        // Get all the bonusList where updatedAt is null
        defaultBonusShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllBonusesByPrincipalAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where principalAmount equals to DEFAULT_PRINCIPAL_AMOUNT
        defaultBonusShouldBeFound("principalAmount.equals=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the bonusList where principalAmount equals to UPDATED_PRINCIPAL_AMOUNT
        defaultBonusShouldNotBeFound("principalAmount.equals=" + UPDATED_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByPrincipalAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where principalAmount not equals to DEFAULT_PRINCIPAL_AMOUNT
        defaultBonusShouldNotBeFound("principalAmount.notEquals=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the bonusList where principalAmount not equals to UPDATED_PRINCIPAL_AMOUNT
        defaultBonusShouldBeFound("principalAmount.notEquals=" + UPDATED_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByPrincipalAmountIsInShouldWork() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where principalAmount in DEFAULT_PRINCIPAL_AMOUNT or UPDATED_PRINCIPAL_AMOUNT
        defaultBonusShouldBeFound("principalAmount.in=" + DEFAULT_PRINCIPAL_AMOUNT + "," + UPDATED_PRINCIPAL_AMOUNT);

        // Get all the bonusList where principalAmount equals to UPDATED_PRINCIPAL_AMOUNT
        defaultBonusShouldNotBeFound("principalAmount.in=" + UPDATED_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByPrincipalAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where principalAmount is not null
        defaultBonusShouldBeFound("principalAmount.specified=true");

        // Get all the bonusList where principalAmount is null
        defaultBonusShouldNotBeFound("principalAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllBonusesByPrincipalAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where principalAmount is greater than or equal to DEFAULT_PRINCIPAL_AMOUNT
        defaultBonusShouldBeFound("principalAmount.greaterThanOrEqual=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the bonusList where principalAmount is greater than or equal to UPDATED_PRINCIPAL_AMOUNT
        defaultBonusShouldNotBeFound("principalAmount.greaterThanOrEqual=" + UPDATED_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByPrincipalAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where principalAmount is less than or equal to DEFAULT_PRINCIPAL_AMOUNT
        defaultBonusShouldBeFound("principalAmount.lessThanOrEqual=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the bonusList where principalAmount is less than or equal to SMALLER_PRINCIPAL_AMOUNT
        defaultBonusShouldNotBeFound("principalAmount.lessThanOrEqual=" + SMALLER_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByPrincipalAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where principalAmount is less than DEFAULT_PRINCIPAL_AMOUNT
        defaultBonusShouldNotBeFound("principalAmount.lessThan=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the bonusList where principalAmount is less than UPDATED_PRINCIPAL_AMOUNT
        defaultBonusShouldBeFound("principalAmount.lessThan=" + UPDATED_PRINCIPAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllBonusesByPrincipalAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where principalAmount is greater than DEFAULT_PRINCIPAL_AMOUNT
        defaultBonusShouldNotBeFound("principalAmount.greaterThan=" + DEFAULT_PRINCIPAL_AMOUNT);

        // Get all the bonusList where principalAmount is greater than SMALLER_PRINCIPAL_AMOUNT
        defaultBonusShouldBeFound("principalAmount.greaterThan=" + SMALLER_PRINCIPAL_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllBonusesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where status equals to DEFAULT_STATUS
        defaultBonusShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the bonusList where status equals to UPDATED_STATUS
        defaultBonusShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBonusesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where status not equals to DEFAULT_STATUS
        defaultBonusShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the bonusList where status not equals to UPDATED_STATUS
        defaultBonusShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBonusesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultBonusShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the bonusList where status equals to UPDATED_STATUS
        defaultBonusShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBonusesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where status is not null
        defaultBonusShouldBeFound("status.specified=true");

        // Get all the bonusList where status is null
        defaultBonusShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllBonusesByStatusContainsSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where status contains DEFAULT_STATUS
        defaultBonusShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the bonusList where status contains UPDATED_STATUS
        defaultBonusShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllBonusesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where status does not contain DEFAULT_STATUS
        defaultBonusShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the bonusList where status does not contain UPDATED_STATUS
        defaultBonusShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllBonusesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where remarks equals to DEFAULT_REMARKS
        defaultBonusShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the bonusList where remarks equals to UPDATED_REMARKS
        defaultBonusShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllBonusesByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where remarks not equals to DEFAULT_REMARKS
        defaultBonusShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the bonusList where remarks not equals to UPDATED_REMARKS
        defaultBonusShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllBonusesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultBonusShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the bonusList where remarks equals to UPDATED_REMARKS
        defaultBonusShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllBonusesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where remarks is not null
        defaultBonusShouldBeFound("remarks.specified=true");

        // Get all the bonusList where remarks is null
        defaultBonusShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllBonusesByRemarksContainsSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where remarks contains DEFAULT_REMARKS
        defaultBonusShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the bonusList where remarks contains UPDATED_REMARKS
        defaultBonusShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllBonusesByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        // Get all the bonusList where remarks does not contain DEFAULT_REMARKS
        defaultBonusShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the bonusList where remarks does not contain UPDATED_REMARKS
        defaultBonusShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllBonusesByBuyerIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile buyer = bonus.getBuyer();
        bonusRepository.saveAndFlush(bonus);
        Long buyerId = buyer.getId();

        // Get all the bonusList where buyer equals to buyerId
        defaultBonusShouldBeFound("buyerId.equals=" + buyerId);

        // Get all the bonusList where buyer equals to buyerId + 1
        defaultBonusShouldNotBeFound("buyerId.equals=" + (buyerId + 1));
    }


    @Test
    @Transactional
    public void getAllBonusesByOrderIsEqualToSomething() throws Exception {
        // Get already existing entity
        Order order = bonus.getOrder();
        bonusRepository.saveAndFlush(bonus);
        Long orderId = order.getId();

        // Get all the bonusList where order equals to orderId
        defaultBonusShouldBeFound("orderId.equals=" + orderId);

        // Get all the bonusList where order equals to orderId + 1
        defaultBonusShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBonusShouldBeFound(String filter) throws Exception {
        restBonusMockMvc.perform(get("/api/bonuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bonus.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].principalAmount").value(hasItem(DEFAULT_PRINCIPAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restBonusMockMvc.perform(get("/api/bonuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBonusShouldNotBeFound(String filter) throws Exception {
        restBonusMockMvc.perform(get("/api/bonuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBonusMockMvc.perform(get("/api/bonuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBonus() throws Exception {
        // Get the bonus
        restBonusMockMvc.perform(get("/api/bonuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBonus() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        int databaseSizeBeforeUpdate = bonusRepository.findAll().size();

        // Update the bonus
        Bonus updatedBonus = bonusRepository.findById(bonus.getId()).get();
        // Disconnect from session so that the updates on updatedBonus are not directly saved in db
        em.detach(updatedBonus);
        updatedBonus
            .amount(UPDATED_AMOUNT)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .principalAmount(UPDATED_PRINCIPAL_AMOUNT)
            .status(UPDATED_STATUS)
            .remarks(UPDATED_REMARKS);
        BonusDTO bonusDTO = bonusMapper.toDto(updatedBonus);

        restBonusMockMvc.perform(put("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isOk());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeUpdate);
        Bonus testBonus = bonusList.get(bonusList.size() - 1);
        assertThat(testBonus.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBonus.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBonus.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testBonus.getPrincipalAmount()).isEqualTo(UPDATED_PRINCIPAL_AMOUNT);
        assertThat(testBonus.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBonus.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingBonus() throws Exception {
        int databaseSizeBeforeUpdate = bonusRepository.findAll().size();

        // Create the Bonus
        BonusDTO bonusDTO = bonusMapper.toDto(bonus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBonusMockMvc.perform(put("/api/bonuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(bonusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bonus in the database
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBonus() throws Exception {
        // Initialize the database
        bonusRepository.saveAndFlush(bonus);

        int databaseSizeBeforeDelete = bonusRepository.findAll().size();

        // Delete the bonus
        restBonusMockMvc.perform(delete("/api/bonuses/{id}", bonus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bonus> bonusList = bonusRepository.findAll();
        assertThat(bonusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
