package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PaymentDetails;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.PaymentDetailsRepository;
import com.mycompany.myapp.service.PaymentDetailsService;
import com.mycompany.myapp.service.dto.PaymentDetailsDTO;
import com.mycompany.myapp.service.mapper.PaymentDetailsMapper;
import com.mycompany.myapp.service.dto.PaymentDetailsCriteria;
import com.mycompany.myapp.service.PaymentDetailsQueryService;

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
 * Integration tests for the {@link PaymentDetailsResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PaymentDetailsResourceIT {

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private PaymentDetailsMapper paymentDetailsMapper;

    @Autowired
    private PaymentDetailsService paymentDetailsService;

    @Autowired
    private PaymentDetailsQueryService paymentDetailsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentDetailsMockMvc;

    private PaymentDetails paymentDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentDetails createEntity(EntityManager em) {
        PaymentDetails paymentDetails = new PaymentDetails()
            .bankName(DEFAULT_BANK_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return paymentDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentDetails createUpdatedEntity(EntityManager em) {
        PaymentDetails paymentDetails = new PaymentDetails()
            .bankName(UPDATED_BANK_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return paymentDetails;
    }

    @BeforeEach
    public void initTest() {
        paymentDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentDetails() throws Exception {
        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);
        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testPaymentDetails.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testPaymentDetails.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPaymentDetails.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createPaymentDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails with an existing ID
        paymentDetails.setId(1L);
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkBankNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setBankName(null);

        // Create the PaymentDetails, which fails.
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setAccountNumber(null);

        // Create the PaymentDetails, which fails.
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setCreatedAt(null);

        // Create the PaymentDetails, which fails.
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentDetailsRepository.findAll().size();
        // set the field null
        paymentDetails.setUpdatedAt(null);

        // Create the PaymentDetails, which fails.
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList
        restPaymentDetailsMockMvc.perform(get("/api/payment-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/{id}", paymentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentDetails.getId().intValue()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getPaymentDetailsByIdFiltering() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        Long id = paymentDetails.getId();

        defaultPaymentDetailsShouldBeFound("id.equals=" + id);
        defaultPaymentDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentDetailsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaymentDetailsByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where bankName equals to DEFAULT_BANK_NAME
        defaultPaymentDetailsShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the paymentDetailsList where bankName equals to UPDATED_BANK_NAME
        defaultPaymentDetailsShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByBankNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where bankName not equals to DEFAULT_BANK_NAME
        defaultPaymentDetailsShouldNotBeFound("bankName.notEquals=" + DEFAULT_BANK_NAME);

        // Get all the paymentDetailsList where bankName not equals to UPDATED_BANK_NAME
        defaultPaymentDetailsShouldBeFound("bankName.notEquals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultPaymentDetailsShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the paymentDetailsList where bankName equals to UPDATED_BANK_NAME
        defaultPaymentDetailsShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where bankName is not null
        defaultPaymentDetailsShouldBeFound("bankName.specified=true");

        // Get all the paymentDetailsList where bankName is null
        defaultPaymentDetailsShouldNotBeFound("bankName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentDetailsByBankNameContainsSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where bankName contains DEFAULT_BANK_NAME
        defaultPaymentDetailsShouldBeFound("bankName.contains=" + DEFAULT_BANK_NAME);

        // Get all the paymentDetailsList where bankName contains UPDATED_BANK_NAME
        defaultPaymentDetailsShouldNotBeFound("bankName.contains=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByBankNameNotContainsSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where bankName does not contain DEFAULT_BANK_NAME
        defaultPaymentDetailsShouldNotBeFound("bankName.doesNotContain=" + DEFAULT_BANK_NAME);

        // Get all the paymentDetailsList where bankName does not contain UPDATED_BANK_NAME
        defaultPaymentDetailsShouldBeFound("bankName.doesNotContain=" + UPDATED_BANK_NAME);
    }


    @Test
    @Transactional
    public void getAllPaymentDetailsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultPaymentDetailsShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the paymentDetailsList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultPaymentDetailsShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where accountNumber not equals to DEFAULT_ACCOUNT_NUMBER
        defaultPaymentDetailsShouldNotBeFound("accountNumber.notEquals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the paymentDetailsList where accountNumber not equals to UPDATED_ACCOUNT_NUMBER
        defaultPaymentDetailsShouldBeFound("accountNumber.notEquals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultPaymentDetailsShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the paymentDetailsList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultPaymentDetailsShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where accountNumber is not null
        defaultPaymentDetailsShouldBeFound("accountNumber.specified=true");

        // Get all the paymentDetailsList where accountNumber is null
        defaultPaymentDetailsShouldNotBeFound("accountNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentDetailsByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultPaymentDetailsShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the paymentDetailsList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultPaymentDetailsShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultPaymentDetailsShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the paymentDetailsList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultPaymentDetailsShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPaymentDetailsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where createdAt equals to DEFAULT_CREATED_AT
        defaultPaymentDetailsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the paymentDetailsList where createdAt equals to UPDATED_CREATED_AT
        defaultPaymentDetailsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where createdAt not equals to DEFAULT_CREATED_AT
        defaultPaymentDetailsShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the paymentDetailsList where createdAt not equals to UPDATED_CREATED_AT
        defaultPaymentDetailsShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultPaymentDetailsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the paymentDetailsList where createdAt equals to UPDATED_CREATED_AT
        defaultPaymentDetailsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where createdAt is not null
        defaultPaymentDetailsShouldBeFound("createdAt.specified=true");

        // Get all the paymentDetailsList where createdAt is null
        defaultPaymentDetailsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultPaymentDetailsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the paymentDetailsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPaymentDetailsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultPaymentDetailsShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the paymentDetailsList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultPaymentDetailsShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultPaymentDetailsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the paymentDetailsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPaymentDetailsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList where updatedAt is not null
        defaultPaymentDetailsShouldBeFound("updatedAt.specified=true");

        // Get all the paymentDetailsList where updatedAt is null
        defaultPaymentDetailsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentDetailsByUserProfileIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);
        UserProfile userProfile = UserProfileResourceIT.createEntity(em);
        em.persist(userProfile);
        em.flush();
        paymentDetails.setUserProfile(userProfile);
        paymentDetailsRepository.saveAndFlush(paymentDetails);
        Long userProfileId = userProfile.getId();

        // Get all the paymentDetailsList where userProfile equals to userProfileId
        defaultPaymentDetailsShouldBeFound("userProfileId.equals=" + userProfileId);

        // Get all the paymentDetailsList where userProfile equals to userProfileId + 1
        defaultPaymentDetailsShouldNotBeFound("userProfileId.equals=" + (userProfileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentDetailsShouldBeFound(String filter) throws Exception {
        restPaymentDetailsMockMvc.perform(get("/api/payment-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentDetailsShouldNotBeFound(String filter) throws Exception {
        restPaymentDetailsMockMvc.perform(get("/api/payment-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPaymentDetails() throws Exception {
        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Update the paymentDetails
        PaymentDetails updatedPaymentDetails = paymentDetailsRepository.findById(paymentDetails.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentDetails are not directly saved in db
        em.detach(updatedPaymentDetails);
        updatedPaymentDetails
            .bankName(UPDATED_BANK_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(updatedPaymentDetails);

        restPaymentDetailsMockMvc.perform(put("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testPaymentDetails.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testPaymentDetails.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPaymentDetails.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentDetailsMockMvc.perform(put("/api/payment-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        int databaseSizeBeforeDelete = paymentDetailsRepository.findAll().size();

        // Delete the paymentDetails
        restPaymentDetailsMockMvc.perform(delete("/api/payment-details/{id}", paymentDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
