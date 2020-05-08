package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.TransactionHistory;
import com.mycompany.myapp.domain.SubscriptionPlan;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.TransactionHistoryRepository;
import com.mycompany.myapp.service.TransactionHistoryService;
import com.mycompany.myapp.service.dto.TransactionHistoryDTO;
import com.mycompany.myapp.service.mapper.TransactionHistoryMapper;
import com.mycompany.myapp.service.dto.TransactionHistoryCriteria;
import com.mycompany.myapp.service.TransactionHistoryQueryService;

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
 * Integration tests for the {@link TransactionHistoryResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TransactionHistoryResourceIT {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PAYMENT_GATEWAY_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_GATEWAY_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_GATEWAY_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_GATEWAY_RESPONSE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private TransactionHistoryMapper transactionHistoryMapper;

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private TransactionHistoryQueryService transactionHistoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTransactionHistoryMockMvc;

    private TransactionHistory transactionHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionHistory createEntity(EntityManager em) {
        TransactionHistory transactionHistory = new TransactionHistory()
            .price(DEFAULT_PRICE)
            .createdAt(DEFAULT_CREATED_AT)
            .paymentGatewayToken(DEFAULT_PAYMENT_GATEWAY_TOKEN)
            .paymentGatewayResponse(DEFAULT_PAYMENT_GATEWAY_RESPONSE)
            .status(DEFAULT_STATUS);
        // Add required entity
        SubscriptionPlan subscriptionPlan;
        if (TestUtil.findAll(em, SubscriptionPlan.class).isEmpty()) {
            subscriptionPlan = SubscriptionPlanResourceIT.createEntity(em);
            em.persist(subscriptionPlan);
            em.flush();
        } else {
            subscriptionPlan = TestUtil.findAll(em, SubscriptionPlan.class).get(0);
        }
        transactionHistory.setSubscriptionPlan(subscriptionPlan);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        transactionHistory.setUserProfile(userProfile);
        return transactionHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionHistory createUpdatedEntity(EntityManager em) {
        TransactionHistory transactionHistory = new TransactionHistory()
            .price(UPDATED_PRICE)
            .createdAt(UPDATED_CREATED_AT)
            .paymentGatewayToken(UPDATED_PAYMENT_GATEWAY_TOKEN)
            .paymentGatewayResponse(UPDATED_PAYMENT_GATEWAY_RESPONSE)
            .status(UPDATED_STATUS);
        // Add required entity
        SubscriptionPlan subscriptionPlan;
        if (TestUtil.findAll(em, SubscriptionPlan.class).isEmpty()) {
            subscriptionPlan = SubscriptionPlanResourceIT.createUpdatedEntity(em);
            em.persist(subscriptionPlan);
            em.flush();
        } else {
            subscriptionPlan = TestUtil.findAll(em, SubscriptionPlan.class).get(0);
        }
        transactionHistory.setSubscriptionPlan(subscriptionPlan);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        transactionHistory.setUserProfile(userProfile);
        return transactionHistory;
    }

    @BeforeEach
    public void initTest() {
        transactionHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionHistory() throws Exception {
        int databaseSizeBeforeCreate = transactionHistoryRepository.findAll().size();

        // Create the TransactionHistory
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);
        restTransactionHistoryMockMvc.perform(post("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionHistory testTransactionHistory = transactionHistoryList.get(transactionHistoryList.size() - 1);
        assertThat(testTransactionHistory.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTransactionHistory.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTransactionHistory.getPaymentGatewayToken()).isEqualTo(DEFAULT_PAYMENT_GATEWAY_TOKEN);
        assertThat(testTransactionHistory.getPaymentGatewayResponse()).isEqualTo(DEFAULT_PAYMENT_GATEWAY_RESPONSE);
        assertThat(testTransactionHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createTransactionHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionHistoryRepository.findAll().size();

        // Create the TransactionHistory with an existing ID
        transactionHistory.setId(1L);
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionHistoryMockMvc.perform(post("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoryRepository.findAll().size();
        // set the field null
        transactionHistory.setPrice(null);

        // Create the TransactionHistory, which fails.
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        restTransactionHistoryMockMvc.perform(post("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoryRepository.findAll().size();
        // set the field null
        transactionHistory.setCreatedAt(null);

        // Create the TransactionHistory, which fails.
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        restTransactionHistoryMockMvc.perform(post("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentGatewayTokenIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoryRepository.findAll().size();
        // set the field null
        transactionHistory.setPaymentGatewayToken(null);

        // Create the TransactionHistory, which fails.
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        restTransactionHistoryMockMvc.perform(post("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentGatewayResponseIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoryRepository.findAll().size();
        // set the field null
        transactionHistory.setPaymentGatewayResponse(null);

        // Create the TransactionHistory, which fails.
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        restTransactionHistoryMockMvc.perform(post("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionHistoryRepository.findAll().size();
        // set the field null
        transactionHistory.setStatus(null);

        // Create the TransactionHistory, which fails.
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        restTransactionHistoryMockMvc.perform(post("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionHistories() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList
        restTransactionHistoryMockMvc.perform(get("/api/transaction-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].paymentGatewayToken").value(hasItem(DEFAULT_PAYMENT_GATEWAY_TOKEN)))
            .andExpect(jsonPath("$.[*].paymentGatewayResponse").value(hasItem(DEFAULT_PAYMENT_GATEWAY_RESPONSE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getTransactionHistory() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get the transactionHistory
        restTransactionHistoryMockMvc.perform(get("/api/transaction-histories/{id}", transactionHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(transactionHistory.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.paymentGatewayToken").value(DEFAULT_PAYMENT_GATEWAY_TOKEN))
            .andExpect(jsonPath("$.paymentGatewayResponse").value(DEFAULT_PAYMENT_GATEWAY_RESPONSE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }


    @Test
    @Transactional
    public void getTransactionHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        Long id = transactionHistory.getId();

        defaultTransactionHistoryShouldBeFound("id.equals=" + id);
        defaultTransactionHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultTransactionHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTransactionHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultTransactionHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTransactionHistoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTransactionHistoriesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where price equals to DEFAULT_PRICE
        defaultTransactionHistoryShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the transactionHistoryList where price equals to UPDATED_PRICE
        defaultTransactionHistoryShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where price not equals to DEFAULT_PRICE
        defaultTransactionHistoryShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the transactionHistoryList where price not equals to UPDATED_PRICE
        defaultTransactionHistoryShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultTransactionHistoryShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the transactionHistoryList where price equals to UPDATED_PRICE
        defaultTransactionHistoryShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where price is not null
        defaultTransactionHistoryShouldBeFound("price.specified=true");

        // Get all the transactionHistoryList where price is null
        defaultTransactionHistoryShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where price is greater than or equal to DEFAULT_PRICE
        defaultTransactionHistoryShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the transactionHistoryList where price is greater than or equal to UPDATED_PRICE
        defaultTransactionHistoryShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where price is less than or equal to DEFAULT_PRICE
        defaultTransactionHistoryShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the transactionHistoryList where price is less than or equal to SMALLER_PRICE
        defaultTransactionHistoryShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where price is less than DEFAULT_PRICE
        defaultTransactionHistoryShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the transactionHistoryList where price is less than UPDATED_PRICE
        defaultTransactionHistoryShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where price is greater than DEFAULT_PRICE
        defaultTransactionHistoryShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the transactionHistoryList where price is greater than SMALLER_PRICE
        defaultTransactionHistoryShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllTransactionHistoriesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where createdAt equals to DEFAULT_CREATED_AT
        defaultTransactionHistoryShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the transactionHistoryList where createdAt equals to UPDATED_CREATED_AT
        defaultTransactionHistoryShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where createdAt not equals to DEFAULT_CREATED_AT
        defaultTransactionHistoryShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the transactionHistoryList where createdAt not equals to UPDATED_CREATED_AT
        defaultTransactionHistoryShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultTransactionHistoryShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the transactionHistoryList where createdAt equals to UPDATED_CREATED_AT
        defaultTransactionHistoryShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where createdAt is not null
        defaultTransactionHistoryShouldBeFound("createdAt.specified=true");

        // Get all the transactionHistoryList where createdAt is null
        defaultTransactionHistoryShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayToken equals to DEFAULT_PAYMENT_GATEWAY_TOKEN
        defaultTransactionHistoryShouldBeFound("paymentGatewayToken.equals=" + DEFAULT_PAYMENT_GATEWAY_TOKEN);

        // Get all the transactionHistoryList where paymentGatewayToken equals to UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayToken.equals=" + UPDATED_PAYMENT_GATEWAY_TOKEN);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayTokenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayToken not equals to DEFAULT_PAYMENT_GATEWAY_TOKEN
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayToken.notEquals=" + DEFAULT_PAYMENT_GATEWAY_TOKEN);

        // Get all the transactionHistoryList where paymentGatewayToken not equals to UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultTransactionHistoryShouldBeFound("paymentGatewayToken.notEquals=" + UPDATED_PAYMENT_GATEWAY_TOKEN);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayTokenIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayToken in DEFAULT_PAYMENT_GATEWAY_TOKEN or UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultTransactionHistoryShouldBeFound("paymentGatewayToken.in=" + DEFAULT_PAYMENT_GATEWAY_TOKEN + "," + UPDATED_PAYMENT_GATEWAY_TOKEN);

        // Get all the transactionHistoryList where paymentGatewayToken equals to UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayToken.in=" + UPDATED_PAYMENT_GATEWAY_TOKEN);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayToken is not null
        defaultTransactionHistoryShouldBeFound("paymentGatewayToken.specified=true");

        // Get all the transactionHistoryList where paymentGatewayToken is null
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayToken.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayTokenContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayToken contains DEFAULT_PAYMENT_GATEWAY_TOKEN
        defaultTransactionHistoryShouldBeFound("paymentGatewayToken.contains=" + DEFAULT_PAYMENT_GATEWAY_TOKEN);

        // Get all the transactionHistoryList where paymentGatewayToken contains UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayToken.contains=" + UPDATED_PAYMENT_GATEWAY_TOKEN);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayTokenNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayToken does not contain DEFAULT_PAYMENT_GATEWAY_TOKEN
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayToken.doesNotContain=" + DEFAULT_PAYMENT_GATEWAY_TOKEN);

        // Get all the transactionHistoryList where paymentGatewayToken does not contain UPDATED_PAYMENT_GATEWAY_TOKEN
        defaultTransactionHistoryShouldBeFound("paymentGatewayToken.doesNotContain=" + UPDATED_PAYMENT_GATEWAY_TOKEN);
    }


    @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayResponseIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayResponse equals to DEFAULT_PAYMENT_GATEWAY_RESPONSE
        defaultTransactionHistoryShouldBeFound("paymentGatewayResponse.equals=" + DEFAULT_PAYMENT_GATEWAY_RESPONSE);

        // Get all the transactionHistoryList where paymentGatewayResponse equals to UPDATED_PAYMENT_GATEWAY_RESPONSE
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayResponse.equals=" + UPDATED_PAYMENT_GATEWAY_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayResponseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayResponse not equals to DEFAULT_PAYMENT_GATEWAY_RESPONSE
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayResponse.notEquals=" + DEFAULT_PAYMENT_GATEWAY_RESPONSE);

        // Get all the transactionHistoryList where paymentGatewayResponse not equals to UPDATED_PAYMENT_GATEWAY_RESPONSE
        defaultTransactionHistoryShouldBeFound("paymentGatewayResponse.notEquals=" + UPDATED_PAYMENT_GATEWAY_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayResponseIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayResponse in DEFAULT_PAYMENT_GATEWAY_RESPONSE or UPDATED_PAYMENT_GATEWAY_RESPONSE
        defaultTransactionHistoryShouldBeFound("paymentGatewayResponse.in=" + DEFAULT_PAYMENT_GATEWAY_RESPONSE + "," + UPDATED_PAYMENT_GATEWAY_RESPONSE);

        // Get all the transactionHistoryList where paymentGatewayResponse equals to UPDATED_PAYMENT_GATEWAY_RESPONSE
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayResponse.in=" + UPDATED_PAYMENT_GATEWAY_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayResponseIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayResponse is not null
        defaultTransactionHistoryShouldBeFound("paymentGatewayResponse.specified=true");

        // Get all the transactionHistoryList where paymentGatewayResponse is null
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayResponse.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayResponseContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayResponse contains DEFAULT_PAYMENT_GATEWAY_RESPONSE
        defaultTransactionHistoryShouldBeFound("paymentGatewayResponse.contains=" + DEFAULT_PAYMENT_GATEWAY_RESPONSE);

        // Get all the transactionHistoryList where paymentGatewayResponse contains UPDATED_PAYMENT_GATEWAY_RESPONSE
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayResponse.contains=" + UPDATED_PAYMENT_GATEWAY_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByPaymentGatewayResponseNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where paymentGatewayResponse does not contain DEFAULT_PAYMENT_GATEWAY_RESPONSE
        defaultTransactionHistoryShouldNotBeFound("paymentGatewayResponse.doesNotContain=" + DEFAULT_PAYMENT_GATEWAY_RESPONSE);

        // Get all the transactionHistoryList where paymentGatewayResponse does not contain UPDATED_PAYMENT_GATEWAY_RESPONSE
        defaultTransactionHistoryShouldBeFound("paymentGatewayResponse.doesNotContain=" + UPDATED_PAYMENT_GATEWAY_RESPONSE);
    }


    @Test
    @Transactional
    public void getAllTransactionHistoriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where status equals to DEFAULT_STATUS
        defaultTransactionHistoryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the transactionHistoryList where status equals to UPDATED_STATUS
        defaultTransactionHistoryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where status not equals to DEFAULT_STATUS
        defaultTransactionHistoryShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the transactionHistoryList where status not equals to UPDATED_STATUS
        defaultTransactionHistoryShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTransactionHistoryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the transactionHistoryList where status equals to UPDATED_STATUS
        defaultTransactionHistoryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where status is not null
        defaultTransactionHistoryShouldBeFound("status.specified=true");

        // Get all the transactionHistoryList where status is null
        defaultTransactionHistoryShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllTransactionHistoriesByStatusContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where status contains DEFAULT_STATUS
        defaultTransactionHistoryShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the transactionHistoryList where status contains UPDATED_STATUS
        defaultTransactionHistoryShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTransactionHistoriesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        // Get all the transactionHistoryList where status does not contain DEFAULT_STATUS
        defaultTransactionHistoryShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the transactionHistoryList where status does not contain UPDATED_STATUS
        defaultTransactionHistoryShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllTransactionHistoriesBySubscriptionPlanIsEqualToSomething() throws Exception {
        // Get already existing entity
        SubscriptionPlan subscriptionPlan = transactionHistory.getSubscriptionPlan();
        transactionHistoryRepository.saveAndFlush(transactionHistory);
        Long subscriptionPlanId = subscriptionPlan.getId();

        // Get all the transactionHistoryList where subscriptionPlan equals to subscriptionPlanId
        defaultTransactionHistoryShouldBeFound("subscriptionPlanId.equals=" + subscriptionPlanId);

        // Get all the transactionHistoryList where subscriptionPlan equals to subscriptionPlanId + 1
        defaultTransactionHistoryShouldNotBeFound("subscriptionPlanId.equals=" + (subscriptionPlanId + 1));
    }


    @Test
    @Transactional
    public void getAllTransactionHistoriesByUserProfileIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile userProfile = transactionHistory.getUserProfile();
        transactionHistoryRepository.saveAndFlush(transactionHistory);
        Long userProfileId = userProfile.getId();

        // Get all the transactionHistoryList where userProfile equals to userProfileId
        defaultTransactionHistoryShouldBeFound("userProfileId.equals=" + userProfileId);

        // Get all the transactionHistoryList where userProfile equals to userProfileId + 1
        defaultTransactionHistoryShouldNotBeFound("userProfileId.equals=" + (userProfileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionHistoryShouldBeFound(String filter) throws Exception {
        restTransactionHistoryMockMvc.perform(get("/api/transaction-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].paymentGatewayToken").value(hasItem(DEFAULT_PAYMENT_GATEWAY_TOKEN)))
            .andExpect(jsonPath("$.[*].paymentGatewayResponse").value(hasItem(DEFAULT_PAYMENT_GATEWAY_RESPONSE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restTransactionHistoryMockMvc.perform(get("/api/transaction-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionHistoryShouldNotBeFound(String filter) throws Exception {
        restTransactionHistoryMockMvc.perform(get("/api/transaction-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionHistoryMockMvc.perform(get("/api/transaction-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTransactionHistory() throws Exception {
        // Get the transactionHistory
        restTransactionHistoryMockMvc.perform(get("/api/transaction-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionHistory() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();

        // Update the transactionHistory
        TransactionHistory updatedTransactionHistory = transactionHistoryRepository.findById(transactionHistory.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionHistory are not directly saved in db
        em.detach(updatedTransactionHistory);
        updatedTransactionHistory
            .price(UPDATED_PRICE)
            .createdAt(UPDATED_CREATED_AT)
            .paymentGatewayToken(UPDATED_PAYMENT_GATEWAY_TOKEN)
            .paymentGatewayResponse(UPDATED_PAYMENT_GATEWAY_RESPONSE)
            .status(UPDATED_STATUS);
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(updatedTransactionHistory);

        restTransactionHistoryMockMvc.perform(put("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
        TransactionHistory testTransactionHistory = transactionHistoryList.get(transactionHistoryList.size() - 1);
        assertThat(testTransactionHistory.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTransactionHistory.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTransactionHistory.getPaymentGatewayToken()).isEqualTo(UPDATED_PAYMENT_GATEWAY_TOKEN);
        assertThat(testTransactionHistory.getPaymentGatewayResponse()).isEqualTo(UPDATED_PAYMENT_GATEWAY_RESPONSE);
        assertThat(testTransactionHistory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionHistory() throws Exception {
        int databaseSizeBeforeUpdate = transactionHistoryRepository.findAll().size();

        // Create the TransactionHistory
        TransactionHistoryDTO transactionHistoryDTO = transactionHistoryMapper.toDto(transactionHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionHistoryMockMvc.perform(put("/api/transaction-histories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(transactionHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionHistory in the database
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionHistory() throws Exception {
        // Initialize the database
        transactionHistoryRepository.saveAndFlush(transactionHistory);

        int databaseSizeBeforeDelete = transactionHistoryRepository.findAll().size();

        // Delete the transactionHistory
        restTransactionHistoryMockMvc.perform(delete("/api/transaction-histories/{id}", transactionHistory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TransactionHistory> transactionHistoryList = transactionHistoryRepository.findAll();
        assertThat(transactionHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
