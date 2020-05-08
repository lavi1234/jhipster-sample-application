package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Offer;
import com.mycompany.myapp.domain.SupplierEnquiryMapping;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.OfferRepository;
import com.mycompany.myapp.service.OfferService;
import com.mycompany.myapp.service.dto.OfferDTO;
import com.mycompany.myapp.service.mapper.OfferMapper;
import com.mycompany.myapp.service.dto.OfferCriteria;
import com.mycompany.myapp.service.OfferQueryService;

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
 * Integration tests for the {@link OfferResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class OfferResourceIT {

    private static final LocalDate DEFAULT_VALID_UPTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_UPTO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_VALID_UPTO = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferMapper offerMapper;

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferQueryService offerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferMockMvc;

    private Offer offer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createEntity(EntityManager em) {
        Offer offer = new Offer()
            .validUpto(DEFAULT_VALID_UPTO)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        SupplierEnquiryMapping supplierEnquiryMapping;
        if (TestUtil.findAll(em, SupplierEnquiryMapping.class).isEmpty()) {
            supplierEnquiryMapping = SupplierEnquiryMappingResourceIT.createEntity(em);
            em.persist(supplierEnquiryMapping);
            em.flush();
        } else {
            supplierEnquiryMapping = TestUtil.findAll(em, SupplierEnquiryMapping.class).get(0);
        }
        offer.setSupplierEnquiry(supplierEnquiryMapping);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        offer.setCreatedBy(userProfile);
        // Add required entity
        offer.setUpdatedBy(userProfile);
        return offer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createUpdatedEntity(EntityManager em) {
        Offer offer = new Offer()
            .validUpto(UPDATED_VALID_UPTO)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        SupplierEnquiryMapping supplierEnquiryMapping;
        if (TestUtil.findAll(em, SupplierEnquiryMapping.class).isEmpty()) {
            supplierEnquiryMapping = SupplierEnquiryMappingResourceIT.createUpdatedEntity(em);
            em.persist(supplierEnquiryMapping);
            em.flush();
        } else {
            supplierEnquiryMapping = TestUtil.findAll(em, SupplierEnquiryMapping.class).get(0);
        }
        offer.setSupplierEnquiry(supplierEnquiryMapping);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        offer.setCreatedBy(userProfile);
        // Add required entity
        offer.setUpdatedBy(userProfile);
        return offer;
    }

    @BeforeEach
    public void initTest() {
        offer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffer() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);
        restOfferMockMvc.perform(post("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getValidUpto()).isEqualTo(DEFAULT_VALID_UPTO);
        assertThat(testOffer.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOffer.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOffer.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // Create the Offer with an existing ID
        offer.setId(1L);
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferMockMvc.perform(post("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValidUptoIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setValidUpto(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc.perform(post("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setStatus(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc.perform(post("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setCreatedAt(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc.perform(post("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerRepository.findAll().size();
        // set the field null
        offer.setUpdatedAt(null);

        // Create the Offer, which fails.
        OfferDTO offerDTO = offerMapper.toDto(offer);

        restOfferMockMvc.perform(post("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOffers() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList
        restOfferMockMvc.perform(get("/api/offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].validUpto").value(hasItem(DEFAULT_VALID_UPTO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.validUpto").value(DEFAULT_VALID_UPTO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getOffersByIdFiltering() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        Long id = offer.getId();

        defaultOfferShouldBeFound("id.equals=" + id);
        defaultOfferShouldNotBeFound("id.notEquals=" + id);

        defaultOfferShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOfferShouldNotBeFound("id.greaterThan=" + id);

        defaultOfferShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOfferShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOffersByValidUptoIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where validUpto equals to DEFAULT_VALID_UPTO
        defaultOfferShouldBeFound("validUpto.equals=" + DEFAULT_VALID_UPTO);

        // Get all the offerList where validUpto equals to UPDATED_VALID_UPTO
        defaultOfferShouldNotBeFound("validUpto.equals=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllOffersByValidUptoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where validUpto not equals to DEFAULT_VALID_UPTO
        defaultOfferShouldNotBeFound("validUpto.notEquals=" + DEFAULT_VALID_UPTO);

        // Get all the offerList where validUpto not equals to UPDATED_VALID_UPTO
        defaultOfferShouldBeFound("validUpto.notEquals=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllOffersByValidUptoIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where validUpto in DEFAULT_VALID_UPTO or UPDATED_VALID_UPTO
        defaultOfferShouldBeFound("validUpto.in=" + DEFAULT_VALID_UPTO + "," + UPDATED_VALID_UPTO);

        // Get all the offerList where validUpto equals to UPDATED_VALID_UPTO
        defaultOfferShouldNotBeFound("validUpto.in=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllOffersByValidUptoIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where validUpto is not null
        defaultOfferShouldBeFound("validUpto.specified=true");

        // Get all the offerList where validUpto is null
        defaultOfferShouldNotBeFound("validUpto.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffersByValidUptoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where validUpto is greater than or equal to DEFAULT_VALID_UPTO
        defaultOfferShouldBeFound("validUpto.greaterThanOrEqual=" + DEFAULT_VALID_UPTO);

        // Get all the offerList where validUpto is greater than or equal to UPDATED_VALID_UPTO
        defaultOfferShouldNotBeFound("validUpto.greaterThanOrEqual=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllOffersByValidUptoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where validUpto is less than or equal to DEFAULT_VALID_UPTO
        defaultOfferShouldBeFound("validUpto.lessThanOrEqual=" + DEFAULT_VALID_UPTO);

        // Get all the offerList where validUpto is less than or equal to SMALLER_VALID_UPTO
        defaultOfferShouldNotBeFound("validUpto.lessThanOrEqual=" + SMALLER_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllOffersByValidUptoIsLessThanSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where validUpto is less than DEFAULT_VALID_UPTO
        defaultOfferShouldNotBeFound("validUpto.lessThan=" + DEFAULT_VALID_UPTO);

        // Get all the offerList where validUpto is less than UPDATED_VALID_UPTO
        defaultOfferShouldBeFound("validUpto.lessThan=" + UPDATED_VALID_UPTO);
    }

    @Test
    @Transactional
    public void getAllOffersByValidUptoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where validUpto is greater than DEFAULT_VALID_UPTO
        defaultOfferShouldNotBeFound("validUpto.greaterThan=" + DEFAULT_VALID_UPTO);

        // Get all the offerList where validUpto is greater than SMALLER_VALID_UPTO
        defaultOfferShouldBeFound("validUpto.greaterThan=" + SMALLER_VALID_UPTO);
    }


    @Test
    @Transactional
    public void getAllOffersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where status equals to DEFAULT_STATUS
        defaultOfferShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the offerList where status equals to UPDATED_STATUS
        defaultOfferShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOffersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where status not equals to DEFAULT_STATUS
        defaultOfferShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the offerList where status not equals to UPDATED_STATUS
        defaultOfferShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOffersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOfferShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the offerList where status equals to UPDATED_STATUS
        defaultOfferShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOffersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where status is not null
        defaultOfferShouldBeFound("status.specified=true");

        // Get all the offerList where status is null
        defaultOfferShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllOffersByStatusContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where status contains DEFAULT_STATUS
        defaultOfferShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the offerList where status contains UPDATED_STATUS
        defaultOfferShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOffersByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where status does not contain DEFAULT_STATUS
        defaultOfferShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the offerList where status does not contain UPDATED_STATUS
        defaultOfferShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllOffersByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where createdAt equals to DEFAULT_CREATED_AT
        defaultOfferShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the offerList where createdAt equals to UPDATED_CREATED_AT
        defaultOfferShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllOffersByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where createdAt not equals to DEFAULT_CREATED_AT
        defaultOfferShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the offerList where createdAt not equals to UPDATED_CREATED_AT
        defaultOfferShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllOffersByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultOfferShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the offerList where createdAt equals to UPDATED_CREATED_AT
        defaultOfferShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllOffersByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where createdAt is not null
        defaultOfferShouldBeFound("createdAt.specified=true");

        // Get all the offerList where createdAt is null
        defaultOfferShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffersByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultOfferShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the offerList where updatedAt equals to UPDATED_UPDATED_AT
        defaultOfferShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllOffersByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultOfferShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the offerList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultOfferShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllOffersByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultOfferShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the offerList where updatedAt equals to UPDATED_UPDATED_AT
        defaultOfferShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllOffersByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where updatedAt is not null
        defaultOfferShouldBeFound("updatedAt.specified=true");

        // Get all the offerList where updatedAt is null
        defaultOfferShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffersBySupplierEnquiryIsEqualToSomething() throws Exception {
        // Get already existing entity
        SupplierEnquiryMapping supplierEnquiry = offer.getSupplierEnquiry();
        offerRepository.saveAndFlush(offer);
        Long supplierEnquiryId = supplierEnquiry.getId();

        // Get all the offerList where supplierEnquiry equals to supplierEnquiryId
        defaultOfferShouldBeFound("supplierEnquiryId.equals=" + supplierEnquiryId);

        // Get all the offerList where supplierEnquiry equals to supplierEnquiryId + 1
        defaultOfferShouldNotBeFound("supplierEnquiryId.equals=" + (supplierEnquiryId + 1));
    }


    @Test
    @Transactional
    public void getAllOffersByCreatedByIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile createdBy = offer.getCreatedBy();
        offerRepository.saveAndFlush(offer);
        Long createdById = createdBy.getId();

        // Get all the offerList where createdBy equals to createdById
        defaultOfferShouldBeFound("createdById.equals=" + createdById);

        // Get all the offerList where createdBy equals to createdById + 1
        defaultOfferShouldNotBeFound("createdById.equals=" + (createdById + 1));
    }


    @Test
    @Transactional
    public void getAllOffersByUpdatedByIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile updatedBy = offer.getUpdatedBy();
        offerRepository.saveAndFlush(offer);
        Long updatedById = updatedBy.getId();

        // Get all the offerList where updatedBy equals to updatedById
        defaultOfferShouldBeFound("updatedById.equals=" + updatedById);

        // Get all the offerList where updatedBy equals to updatedById + 1
        defaultOfferShouldNotBeFound("updatedById.equals=" + (updatedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOfferShouldBeFound(String filter) throws Exception {
        restOfferMockMvc.perform(get("/api/offers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].validUpto").value(hasItem(DEFAULT_VALID_UPTO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restOfferMockMvc.perform(get("/api/offers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOfferShouldNotBeFound(String filter) throws Exception {
        restOfferMockMvc.perform(get("/api/offers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOfferMockMvc.perform(get("/api/offers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        Offer updatedOffer = offerRepository.findById(offer.getId()).get();
        // Disconnect from session so that the updates on updatedOffer are not directly saved in db
        em.detach(updatedOffer);
        updatedOffer
            .validUpto(UPDATED_VALID_UPTO)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        OfferDTO offerDTO = offerMapper.toDto(updatedOffer);

        restOfferMockMvc.perform(put("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getValidUpto()).isEqualTo(UPDATED_VALID_UPTO);
        assertThat(testOffer.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOffer.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOffer.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc.perform(put("/api/offers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Delete the offer
        restOfferMockMvc.perform(delete("/api/offers/{id}", offer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
