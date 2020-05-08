package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.UserSubscription;
import com.mycompany.myapp.domain.SubscriptionPlan;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.UserSubscriptionRepository;
import com.mycompany.myapp.service.UserSubscriptionService;
import com.mycompany.myapp.service.dto.UserSubscriptionDTO;
import com.mycompany.myapp.service.mapper.UserSubscriptionMapper;
import com.mycompany.myapp.service.dto.UserSubscriptionCriteria;
import com.mycompany.myapp.service.UserSubscriptionQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserSubscriptionResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class UserSubscriptionResourceIT {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    private static final LocalDate DEFAULT_VALID_UPTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_UPTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_UPTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PAYMENT_GATEWAY_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_GATEWAY_TOKEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_NEXT_RENEWAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NEXT_RENEWAL = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_NEXT_RENEWAL = LocalDate.ofEpochDay(-1L);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    @Autowired
    private UserSubscriptionMapper userSubscriptionMapper;

    @Autowired
    private UserSubscriptionService userSubscriptionService;

    @Autowired
    private UserSubscriptionQueryService userSubscriptionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserSubscriptionMockMvc;

    private UserSubscription userSubscription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSubscription createEntity(EntityManager em) {
        UserSubscription userSubscription = new UserSubscription()
            .price(DEFAULT_PRICE)
            .validUpto(DEFAULT_VALID_UPTO)
            .paymentGatewayToken(DEFAULT_PAYMENT_GATEWAY_TOKEN)
            .nextRenewal(DEFAULT_NEXT_RENEWAL)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        SubscriptionPlan subscriptionPlan;
        if (TestUtil.findAll(em, SubscriptionPlan.class).isEmpty()) {
            subscriptionPlan = SubscriptionPlanResourceIT.createEntity(em);
            em.persist(subscriptionPlan);
            em.flush();
        } else {
            subscriptionPlan = TestUtil.findAll(em, SubscriptionPlan.class).get(0);
        }
        userSubscription.setSubscriptionPlan(subscriptionPlan);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        userSubscription.setUserProfile(userProfile);
        return userSubscription;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSubscription createUpdatedEntity(EntityManager em) {
        UserSubscription userSubscription = new UserSubscription()
            .price(UPDATED_PRICE)
            .validUpto(UPDATED_VALID_UPTO)
            .paymentGatewayToken(UPDATED_PAYMENT_GATEWAY_TOKEN)
            .nextRenewal(UPDATED_NEXT_RENEWAL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        SubscriptionPlan subscriptionPlan;
        if (TestUtil.findAll(em, SubscriptionPlan.class).isEmpty()) {
            subscriptionPlan = SubscriptionPlanResourceIT.createUpdatedEntity(em);
            em.persist(subscriptionPlan);
            em.flush();
        } else {
            subscriptionPlan = TestUtil.findAll(em, SubscriptionPlan.class).get(0);
        }
        userSubscription.setSubscriptionPlan(subscriptionPlan);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        userSubscription.setUserProfile(userProfile);
        return userSubscription;
    }

    @BeforeEach
    public void initTest() {
        userSubscription = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserSubscription() throws Exception {
        int databaseSizeBeforeCreate = userSubscriptionRepository.findAll().size();

        // Create the UserSubscription
        UserSubscriptionDTO userSubscriptionDTO = userSubscriptionMapper.toDto(userSubscription);
        restUserSubscriptionMockMvc.perform(post("/api/user-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSubscriptionDTO)))
            .andExpect(status().isCreated());

        // Validate the UserSubscription in the database
        List<UserSubscription> userSubscriptionList = userSubscriptionRepository.findAll();
        assertThat(userSubscriptionList).hasSize(databaseSizeBeforeCreate + 1);
        UserSubscription testUserSubscription = userSubscriptionList.get(userSubscriptionList.size() - 1);
        assertThat(testUserSubscription.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testUserSubscription.getValidUpto()).isEqualTo(DEFAULT_VALID_UPTO);
        assertThat(testUserSubscription.getPaymentGatewayToken()).isEqualTo(DEFAULT_PAYMENT_GATEWAY_TOKEN);
        assertThat(testUserSubscription.getNextRenewal()).isEqualTo(DEFAULT_NEXT_RENEWAL);
        assertThat(testUserSubscription.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserSubscription.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createUserSubscriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userSubscriptionRepository.findAll().size();

        // Create the UserSubscription with an existing ID
        userSubscription.setId(1L);
        UserSubscriptionDTO userSubscriptionDTO = userSubscriptionMapper.toDto(userSubscription);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSubscriptionMockMvc.perform(post("/api/user-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSubscription in the database
        List<UserSubscription> userSubscriptionList = userSubscriptionRepository.findAll();
        assertThat(userSubscriptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSubscriptionRepository.findAll().size();
        // set the field null
        userSubscription.setPrice(null);

        // Create the UserSubscription, which fails.
        UserSubscriptionDTO userSubscriptionDTO = userSubscriptionMapper.toDto(userSubscription);

        restUserSubscriptionMockMvc.perform(post("/api/user-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<UserSubscription> userSubscriptionList = userSubscriptionRepository.findAll();
        assertThat(userSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidUptoIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSubscriptionRepository.findAll().size();
        // set the field null
        userSubscription.setValidUpto(null);

        // Create the UserSubscription, which fails.
        UserSubscriptionDTO userSubscriptionDTO = userSubscriptionMapper.toDto(userSubscription);

        restUserSubscriptionMockMvc.perform(post("/api/user-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<UserSubscription> userSubscriptionList = userSubscriptionRepository.findAll();
        assertThat(userSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentGatewayTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSubscriptionRepository.findAll().size();
        // set the field null
        userSubscription.setPaymentGatewayToken(null);

        // Create the UserSubscription, which fails.
        UserSubscriptionDTO userSubscriptionDTO = userSubscriptionMapper.toDto(userSubscription);

        restUserSubscriptionMockMvc.perform(post("/api/user-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<UserSubscription> userSubscriptionList = userSubscriptionRepository.findAll();
        assertThat(userSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNextRenewalIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSubscriptionRepository.findAll().size();
        // set the field null
        userSubscription.setNextRenewal(null);

        // Create the UserSubscription, which fails.
        UserSubscriptionDTO userSubscriptionDTO = userSubscriptionMapper.toDto(userSubscription);

        restUserSubscriptionMockMvc.perform(post("/api/user-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<UserSubscription> userSubscriptionList = userSubscriptionRepository.findAll();
        assertThat(userSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSubscriptionRepository.findAll().size();
        // set the field null
        userSubscription.setCreatedAt(null);

        // Create the UserSubscription, which fails.
        UserSubscriptionDTO userSubscriptionDTO = userSubscriptionMapper.toDto(userSubscription);

        restUserSubscriptionMockMvc.perform(post("/api/user-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<UserSubscription> userSubscriptionList = userSubscriptionRepository.findAll();
        assertThat(userSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userSubscriptionRepository.findAll().size();
        // set the field null
        userSubscription.setUpdatedAt(null);

        // Create the UserSubscription, which fails.
        UserSubscriptionDTO userSubscriptionDTO = userSubscriptionMapper.toDto(userSubscription);

        restUserSubscriptionMockMvc.perform(post("/api/user-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        List<UserSubscription> userSubscriptionList = userSubscriptionRepository.findAll();
        assertThat(userSubscriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptions() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList
        restUserSubscriptionMockMvc.perform(get("/api/user-subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].validUpto").value(hasItem(DEFAULT_VALID_UPTO.toString())))
            .andExpect(jsonPath("$.[*].paymentGatewayToken").value(hasItem(DEFAULT_PAYMENT_GATEWAY_TOKEN)))
            .andExpect(jsonPath("$.[*].nextRenewal").value(hasItem(DEFAULT_NEXT_RENEWAL.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getUserSubscription() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get the userSubscription
        restUserSubscriptionMockMvc.perform(get("/api/user-subscriptions/{id}", userSubscription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userSubscription.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.validUpto").value(DEFAULT_VALID_UPTO.toString()))
            .andExpect(jsonPath("$.paymentGatewayToken").value(DEFAULT_PAYMENT_GATEWAY_TOKEN))
            .andExpect(jsonPath("$.nextRenewal").value(DEFAULT_NEXT_RENEWAL.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getUserSubscriptionsByIdFiltering() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        Long id = userSubscription.getId();

        defaultUserSubscriptionShouldBeFound("id.equals=" + id);
        defaultUserSubscriptionShouldNotBeFound("id.notEquals=" + id);

        defaultUserSubscriptionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserSubscriptionShouldNotBeFound("id.greaterThan=" + id);

        defaultUserSubscriptionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserSubscriptionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserSubscriptionsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where price equals to DEFAULT_PRICE
        defaultUserSubscriptionShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the userSubscriptionList where price equals to UPDATED_PRICE
        defaultUserSubscriptionShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where price not equals to DEFAULT_PRICE
        defaultUserSubscriptionShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the userSubscriptionList where price not equals to UPDATED_PRICE
        defaultUserSubscriptionShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultUserSubscriptionShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the userSubscriptionList where price equals to UPDATED_PRICE
        defaultUserSubscriptionShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where price is not null
        defaultUserSubscriptionShouldBeFound("price.specified=true");

        // Get all the userSubscriptionList where price is null
        defaultUserSubscriptionShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where price is greater than or equal to DEFAULT_PRICE
        defaultUserSubscriptionShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the userSubscriptionList where price is greater than or equal to UPDATED_PRICE
        defaultUserSubscriptionShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where price is less than or equal to DEFAULT_PRICE
        defaultUserSubscriptionShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the userSubscriptionList where price is less than or equal to SMALLER_PRICE
        defaultUserSubscriptionShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where price is less than DEFAULT_PRICE
        defaultUserSubscriptionShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the userSubscriptionList where price is less than UPDATED_PRICE
        defaultUserSubscriptionShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where price is greater than DEFAULT_PRICE
        defaultUserSubscriptionShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the userSubscriptionList where price is greater than SMALLER_PRICE
        defaultUserSubscriptionShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllUserSubscriptionsByValidUptoIsEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where validUpto equals to DEFAULT_VALID_UPTO
        defaultUserSubscriptionShouldBeFound("validUpto.equals=" + DEFAULT_VALID_UPTO);

        // Get all the userSubscriptionList where validUpto equals to UPDATED_VALID_UPTO
        defaultUserSubscriptionShouldNotBeFound("validUpto.equals=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByValidUptoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where validUpto not equals to DEFAULT_VALID_UPTO
        defaultUserSubscriptionShouldNotBeFound("validUpto.notEquals=" + DEFAULT_VALID_UPTO);

        // Get all the userSubscriptionList where validUpto not equals to UPDATED_VALID_UPTO
        defaultUserSubscriptionShouldBeFound("validUpto.notEquals=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByValidUptoIsInShouldWork() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where validUpto in DEFAULT_VALID_UPTO or UPDATED_VALID_UPTO
        defaultUserSubscriptionShouldBeFound("validUpto.in=" + DEFAULT_VALID_UPTO + "," + UPDATED_VALID_UPTO);

        // Get all the userSubscriptionList where validUpto equals to UPDATED_VALID_UPTO
        defaultUserSubscriptionShouldNotBeFound("validUpto.in=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByValidUptoIsNullOrNotNull() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where validUpto is not null
        defaultUserSubscriptionShouldBeFound("validUpto.specified=true");

        // Get all the userSubscriptionList where validUpto is null
        defaultUserSubscriptionShouldNotBeFound("validUpto.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByValidUptoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where validUpto is greater than or equal to DEFAULT_VALID_UPTO
        defaultUserSubscriptionShouldBeFound("validUpto.greaterThanOrEqual=" + DEFAULT_VALID_UPTO);

        // Get all the userSubscriptionList where validUpto is greater than or equal to UPDATED_VALID_UPTO
        defaultUserSubscriptionShouldNotBeFound("validUpto.greaterThanOrEqual=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByValidUptoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where validUpto is less than or equal to DEFAULT_VALID_UPTO
        defaultUserSubscriptionShouldBeFound("validUpto.lessThanOrEqual=" + DEFAULT_VALID_UPTO);

        // Get all the userSubscriptionList where validUpto is less than or equal to SMALLER_VALID_UPTO
        defaultUserSubscriptionShouldNotBeFound("validUpto.lessThanOrEqual=" + SMALLER_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByValidUptoIsLessThanSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where validUpto is less than DEFAULT_VALID_UPTO
        defaultUserSubscriptionShouldNotBeFound("validUpto.lessThan=" + DEFAULT_VALID_UPTO);

        // Get all the userSubscriptionList where validUpto is less than UPDATED_VALID_UPTO
        defaultUserSubscriptionShouldBeFound("validUpto.lessThan=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByValidUptoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where validUpto is greater than DEFAULT_VALID_UPTO
        defaultUserSubscriptionShouldNotBeFound("validUpto.greaterThan=" + DEFAULT_VALID_UPTO);

        // Get all the userSubscriptionList where validUpto is greater than SMALLER_VALID_UPTO
        defaultUserSubscriptionShouldBeFound("validUpto.greaterThan=" + SMALLER_VALID_UPTO);
    }


    @Test
    @Transactional
    public void getAllUserSubscriptionsByPaymentGatewayTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where paymentGatewayToken equals to DEFAULT_PAYMENT_GATEWAY_TOKEN
        defaultUserSubscriptionShouldBeFound("paymentGatewayToken.equals=" + DEFAULT_PAYMENT_GATEWAY_TOKEN);

        // Get all the userSubscriptionList where paymentGatewayToken equals to UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultUserSubscriptionShouldNotBeFound("paymentGatewayToken.equals=" + UPDATED_PAYMENT_GATEWAY_TOKEN);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByPaymentGatewayTokenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where paymentGatewayToken not equals to DEFAULT_PAYMENT_GATEWAY_TOKEN
        defaultUserSubscriptionShouldNotBeFound("paymentGatewayToken.notEquals=" + DEFAULT_PAYMENT_GATEWAY_TOKEN);

        // Get all the userSubscriptionList where paymentGatewayToken not equals to UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultUserSubscriptionShouldBeFound("paymentGatewayToken.notEquals=" + UPDATED_PAYMENT_GATEWAY_TOKEN);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByPaymentGatewayTokenIsInShouldWork() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where paymentGatewayToken in DEFAULT_PAYMENT_GATEWAY_TOKEN or UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultUserSubscriptionShouldBeFound("paymentGatewayToken.in=" + DEFAULT_PAYMENT_GATEWAY_TOKEN + "," + UPDATED_PAYMENT_GATEWAY_TOKEN);

        // Get all the userSubscriptionList where paymentGatewayToken equals to UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultUserSubscriptionShouldNotBeFound("paymentGatewayToken.in=" + UPDATED_PAYMENT_GATEWAY_TOKEN);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByPaymentGatewayTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where paymentGatewayToken is not null
        defaultUserSubscriptionShouldBeFound("paymentGatewayToken.specified=true");

        // Get all the userSubscriptionList where paymentGatewayToken is null
        defaultUserSubscriptionShouldNotBeFound("paymentGatewayToken.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserSubscriptionsByPaymentGatewayTokenContainsSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where paymentGatewayToken contains DEFAULT_PAYMENT_GATEWAY_TOKEN
        defaultUserSubscriptionShouldBeFound("paymentGatewayToken.contains=" + DEFAULT_PAYMENT_GATEWAY_TOKEN);

        // Get all the userSubscriptionList where paymentGatewayToken contains UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultUserSubscriptionShouldNotBeFound("paymentGatewayToken.contains=" + UPDATED_PAYMENT_GATEWAY_TOKEN);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByPaymentGatewayTokenNotContainsSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where paymentGatewayToken does not contain DEFAULT_PAYMENT_GATEWAY_TOKEN
        defaultUserSubscriptionShouldNotBeFound("paymentGatewayToken.doesNotContain=" + DEFAULT_PAYMENT_GATEWAY_TOKEN);

        // Get all the userSubscriptionList where paymentGatewayToken does not contain UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultUserSubscriptionShouldBeFound("paymentGatewayToken.doesNotContain=" + UPDATED_PAYMENT_GATEWAY_TOKEN);
    }


    @Test
    @Transactional
    public void getAllUserSubscriptionsByNextRenewalIsEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where nextRenewal equals to DEFAULT_NEXT_RENEWAL
        defaultUserSubscriptionShouldBeFound("nextRenewal.equals=" + DEFAULT_NEXT_RENEWAL);

        // Get all the userSubscriptionList where nextRenewal equals to UPDATED_NEXT_RENEWAL
        defaultUserSubscriptionShouldNotBeFound("nextRenewal.equals=" + UPDATED_NEXT_RENEWAL);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByNextRenewalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where nextRenewal not equals to DEFAULT_NEXT_RENEWAL
        defaultUserSubscriptionShouldNotBeFound("nextRenewal.notEquals=" + DEFAULT_NEXT_RENEWAL);

        // Get all the userSubscriptionList where nextRenewal not equals to UPDATED_NEXT_RENEWAL
        defaultUserSubscriptionShouldBeFound("nextRenewal.notEquals=" + UPDATED_NEXT_RENEWAL);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByNextRenewalIsInShouldWork() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where nextRenewal in DEFAULT_NEXT_RENEWAL or UPDATED_NEXT_RENEWAL
        defaultUserSubscriptionShouldBeFound("nextRenewal.in=" + DEFAULT_NEXT_RENEWAL + "," + UPDATED_NEXT_RENEWAL);

        // Get all the userSubscriptionList where nextRenewal equals to UPDATED_NEXT_RENEWAL
        defaultUserSubscriptionShouldNotBeFound("nextRenewal.in=" + UPDATED_NEXT_RENEWAL);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByNextRenewalIsNullOrNotNull() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where nextRenewal is not null
        defaultUserSubscriptionShouldBeFound("nextRenewal.specified=true");

        // Get all the userSubscriptionList where nextRenewal is null
        defaultUserSubscriptionShouldNotBeFound("nextRenewal.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByNextRenewalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where nextRenewal is greater than or equal to DEFAULT_NEXT_RENEWAL
        defaultUserSubscriptionShouldBeFound("nextRenewal.greaterThanOrEqual=" + DEFAULT_NEXT_RENEWAL);

        // Get all the userSubscriptionList where nextRenewal is greater than or equal to UPDATED_NEXT_RENEWAL
        defaultUserSubscriptionShouldNotBeFound("nextRenewal.greaterThanOrEqual=" + UPDATED_NEXT_RENEWAL);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByNextRenewalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where nextRenewal is less than or equal to DEFAULT_NEXT_RENEWAL
        defaultUserSubscriptionShouldBeFound("nextRenewal.lessThanOrEqual=" + DEFAULT_NEXT_RENEWAL);

        // Get all the userSubscriptionList where nextRenewal is less than or equal to SMALLER_NEXT_RENEWAL
        defaultUserSubscriptionShouldNotBeFound("nextRenewal.lessThanOrEqual=" + SMALLER_NEXT_RENEWAL);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByNextRenewalIsLessThanSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where nextRenewal is less than DEFAULT_NEXT_RENEWAL
        defaultUserSubscriptionShouldNotBeFound("nextRenewal.lessThan=" + DEFAULT_NEXT_RENEWAL);

        // Get all the userSubscriptionList where nextRenewal is less than UPDATED_NEXT_RENEWAL
        defaultUserSubscriptionShouldBeFound("nextRenewal.lessThan=" + UPDATED_NEXT_RENEWAL);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByNextRenewalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where nextRenewal is greater than DEFAULT_NEXT_RENEWAL
        defaultUserSubscriptionShouldNotBeFound("nextRenewal.greaterThan=" + DEFAULT_NEXT_RENEWAL);

        // Get all the userSubscriptionList where nextRenewal is greater than SMALLER_NEXT_RENEWAL
        defaultUserSubscriptionShouldBeFound("nextRenewal.greaterThan=" + SMALLER_NEXT_RENEWAL);
    }


    @Test
    @Transactional
    public void getAllUserSubscriptionsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where createdAt equals to DEFAULT_CREATED_AT
        defaultUserSubscriptionShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the userSubscriptionList where createdAt equals to UPDATED_CREATED_AT
        defaultUserSubscriptionShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where createdAt not equals to DEFAULT_CREATED_AT
        defaultUserSubscriptionShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the userSubscriptionList where createdAt not equals to UPDATED_CREATED_AT
        defaultUserSubscriptionShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultUserSubscriptionShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the userSubscriptionList where createdAt equals to UPDATED_CREATED_AT
        defaultUserSubscriptionShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where createdAt is not null
        defaultUserSubscriptionShouldBeFound("createdAt.specified=true");

        // Get all the userSubscriptionList where createdAt is null
        defaultUserSubscriptionShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultUserSubscriptionShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the userSubscriptionList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserSubscriptionShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultUserSubscriptionShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the userSubscriptionList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultUserSubscriptionShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultUserSubscriptionShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the userSubscriptionList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserSubscriptionShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        // Get all the userSubscriptionList where updatedAt is not null
        defaultUserSubscriptionShouldBeFound("updatedAt.specified=true");

        // Get all the userSubscriptionList where updatedAt is null
        defaultUserSubscriptionShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserSubscriptionsBySubscriptionPlanIsEqualToSomething() throws Exception {
        // Get already existing entity
        SubscriptionPlan subscriptionPlan = userSubscription.getSubscriptionPlan();
        userSubscriptionRepository.saveAndFlush(userSubscription);
        Long subscriptionPlanId = subscriptionPlan.getId();

        // Get all the userSubscriptionList where subscriptionPlan equals to subscriptionPlanId
        defaultUserSubscriptionShouldBeFound("subscriptionPlanId.equals=" + subscriptionPlanId);

        // Get all the userSubscriptionList where subscriptionPlan equals to subscriptionPlanId + 1
        defaultUserSubscriptionShouldNotBeFound("subscriptionPlanId.equals=" + (subscriptionPlanId + 1));
    }


    @Test
    @Transactional
    public void getAllUserSubscriptionsByUserProfileIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile userProfile = userSubscription.getUserProfile();
        userSubscriptionRepository.saveAndFlush(userSubscription);
        Long userProfileId = userProfile.getId();

        // Get all the userSubscriptionList where userProfile equals to userProfileId
        defaultUserSubscriptionShouldBeFound("userProfileId.equals=" + userProfileId);

        // Get all the userSubscriptionList where userProfile equals to userProfileId + 1
        defaultUserSubscriptionShouldNotBeFound("userProfileId.equals=" + (userProfileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserSubscriptionShouldBeFound(String filter) throws Exception {
        restUserSubscriptionMockMvc.perform(get("/api/user-subscriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSubscription.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].validUpto").value(hasItem(DEFAULT_VALID_UPTO.toString())))
            .andExpect(jsonPath("$.[*].paymentGatewayToken").value(hasItem(DEFAULT_PAYMENT_GATEWAY_TOKEN)))
            .andExpect(jsonPath("$.[*].nextRenewal").value(hasItem(DEFAULT_NEXT_RENEWAL.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restUserSubscriptionMockMvc.perform(get("/api/user-subscriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserSubscriptionShouldNotBeFound(String filter) throws Exception {
        restUserSubscriptionMockMvc.perform(get("/api/user-subscriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserSubscriptionMockMvc.perform(get("/api/user-subscriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUserSubscription() throws Exception {
        // Get the userSubscription
        restUserSubscriptionMockMvc.perform(get("/api/user-subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserSubscription() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        int databaseSizeBeforeUpdate = userSubscriptionRepository.findAll().size();

        // Update the userSubscription
        UserSubscription updatedUserSubscription = userSubscriptionRepository.findById(userSubscription.getId()).get();
        // Disconnect from session so that the updates on updatedUserSubscription are not directly saved in db
        em.detach(updatedUserSubscription);
        updatedUserSubscription
            .price(UPDATED_PRICE)
            .validUpto(UPDATED_VALID_UPTO)
            .paymentGatewayToken(UPDATED_PAYMENT_GATEWAY_TOKEN)
            .nextRenewal(UPDATED_NEXT_RENEWAL)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        UserSubscriptionDTO userSubscriptionDTO = userSubscriptionMapper.toDto(updatedUserSubscription);

        restUserSubscriptionMockMvc.perform(put("/api/user-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSubscriptionDTO)))
            .andExpect(status().isOk());

        // Validate the UserSubscription in the database
        List<UserSubscription> userSubscriptionList = userSubscriptionRepository.findAll();
        assertThat(userSubscriptionList).hasSize(databaseSizeBeforeUpdate);
        UserSubscription testUserSubscription = userSubscriptionList.get(userSubscriptionList.size() - 1);
        assertThat(testUserSubscription.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testUserSubscription.getValidUpto()).isEqualTo(UPDATED_VALID_UPTO);
        assertThat(testUserSubscription.getPaymentGatewayToken()).isEqualTo(UPDATED_PAYMENT_GATEWAY_TOKEN);
        assertThat(testUserSubscription.getNextRenewal()).isEqualTo(UPDATED_NEXT_RENEWAL);
        assertThat(testUserSubscription.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserSubscription.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserSubscription() throws Exception {
        int databaseSizeBeforeUpdate = userSubscriptionRepository.findAll().size();

        // Create the UserSubscription
        UserSubscriptionDTO userSubscriptionDTO = userSubscriptionMapper.toDto(userSubscription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSubscriptionMockMvc.perform(put("/api/user-subscriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSubscriptionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSubscription in the database
        List<UserSubscription> userSubscriptionList = userSubscriptionRepository.findAll();
        assertThat(userSubscriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserSubscription() throws Exception {
        // Initialize the database
        userSubscriptionRepository.saveAndFlush(userSubscription);

        int databaseSizeBeforeDelete = userSubscriptionRepository.findAll().size();

        // Delete the userSubscription
        restUserSubscriptionMockMvc.perform(delete("/api/user-subscriptions/{id}", userSubscription.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSubscription> userSubscriptionList = userSubscriptionRepository.findAll();
        assertThat(userSubscriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
