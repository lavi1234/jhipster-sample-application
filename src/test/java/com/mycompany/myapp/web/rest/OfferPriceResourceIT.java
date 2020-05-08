package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.OfferPrice;
import com.mycompany.myapp.domain.Offer;
import com.mycompany.myapp.domain.Enquiry;
import com.mycompany.myapp.domain.EnquiryDetails;
import com.mycompany.myapp.repository.OfferPriceRepository;
import com.mycompany.myapp.service.OfferPriceService;
import com.mycompany.myapp.service.dto.OfferPriceDTO;
import com.mycompany.myapp.service.mapper.OfferPriceMapper;
import com.mycompany.myapp.service.dto.OfferPriceCriteria;
import com.mycompany.myapp.service.OfferPriceQueryService;

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
 * Integration tests for the {@link OfferPriceResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class OfferPriceResourceIT {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_FINISHING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FINISHING_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FINISHING_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private OfferPriceRepository offerPriceRepository;

    @Autowired
    private OfferPriceMapper offerPriceMapper;

    @Autowired
    private OfferPriceService offerPriceService;

    @Autowired
    private OfferPriceQueryService offerPriceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferPriceMockMvc;

    private OfferPrice offerPrice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferPrice createEntity(EntityManager em) {
        OfferPrice offerPrice = new OfferPrice()
            .price(DEFAULT_PRICE)
            .createdAt(DEFAULT_CREATED_AT)
            .finishingDate(DEFAULT_FINISHING_DATE);
        // Add required entity
        Offer offer;
        if (TestUtil.findAll(em, Offer.class).isEmpty()) {
            offer = OfferResourceIT.createEntity(em);
            em.persist(offer);
            em.flush();
        } else {
            offer = TestUtil.findAll(em, Offer.class).get(0);
        }
        offerPrice.setOffer(offer);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        offerPrice.setEnquiry(enquiry);
        // Add required entity
        EnquiryDetails enquiryDetails;
        if (TestUtil.findAll(em, EnquiryDetails.class).isEmpty()) {
            enquiryDetails = EnquiryDetailsResourceIT.createEntity(em);
            em.persist(enquiryDetails);
            em.flush();
        } else {
            enquiryDetails = TestUtil.findAll(em, EnquiryDetails.class).get(0);
        }
        offerPrice.setEnquiryDetails(enquiryDetails);
        return offerPrice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferPrice createUpdatedEntity(EntityManager em) {
        OfferPrice offerPrice = new OfferPrice()
            .price(UPDATED_PRICE)
            .createdAt(UPDATED_CREATED_AT)
            .finishingDate(UPDATED_FINISHING_DATE);
        // Add required entity
        Offer offer;
        if (TestUtil.findAll(em, Offer.class).isEmpty()) {
            offer = OfferResourceIT.createUpdatedEntity(em);
            em.persist(offer);
            em.flush();
        } else {
            offer = TestUtil.findAll(em, Offer.class).get(0);
        }
        offerPrice.setOffer(offer);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createUpdatedEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        offerPrice.setEnquiry(enquiry);
        // Add required entity
        EnquiryDetails enquiryDetails;
        if (TestUtil.findAll(em, EnquiryDetails.class).isEmpty()) {
            enquiryDetails = EnquiryDetailsResourceIT.createUpdatedEntity(em);
            em.persist(enquiryDetails);
            em.flush();
        } else {
            enquiryDetails = TestUtil.findAll(em, EnquiryDetails.class).get(0);
        }
        offerPrice.setEnquiryDetails(enquiryDetails);
        return offerPrice;
    }

    @BeforeEach
    public void initTest() {
        offerPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfferPrice() throws Exception {
        int databaseSizeBeforeCreate = offerPriceRepository.findAll().size();

        // Create the OfferPrice
        OfferPriceDTO offerPriceDTO = offerPriceMapper.toDto(offerPrice);
        restOfferPriceMockMvc.perform(post("/api/offer-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the OfferPrice in the database
        List<OfferPrice> offerPriceList = offerPriceRepository.findAll();
        assertThat(offerPriceList).hasSize(databaseSizeBeforeCreate + 1);
        OfferPrice testOfferPrice = offerPriceList.get(offerPriceList.size() - 1);
        assertThat(testOfferPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testOfferPrice.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOfferPrice.getFinishingDate()).isEqualTo(DEFAULT_FINISHING_DATE);
    }

    @Test
    @Transactional
    public void createOfferPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerPriceRepository.findAll().size();

        // Create the OfferPrice with an existing ID
        offerPrice.setId(1L);
        OfferPriceDTO offerPriceDTO = offerPriceMapper.toDto(offerPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferPriceMockMvc.perform(post("/api/offer-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OfferPrice in the database
        List<OfferPrice> offerPriceList = offerPriceRepository.findAll();
        assertThat(offerPriceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerPriceRepository.findAll().size();
        // set the field null
        offerPrice.setPrice(null);

        // Create the OfferPrice, which fails.
        OfferPriceDTO offerPriceDTO = offerPriceMapper.toDto(offerPrice);

        restOfferPriceMockMvc.perform(post("/api/offer-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerPriceDTO)))
            .andExpect(status().isBadRequest());

        List<OfferPrice> offerPriceList = offerPriceRepository.findAll();
        assertThat(offerPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = offerPriceRepository.findAll().size();
        // set the field null
        offerPrice.setCreatedAt(null);

        // Create the OfferPrice, which fails.
        OfferPriceDTO offerPriceDTO = offerPriceMapper.toDto(offerPrice);

        restOfferPriceMockMvc.perform(post("/api/offer-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerPriceDTO)))
            .andExpect(status().isBadRequest());

        List<OfferPrice> offerPriceList = offerPriceRepository.findAll();
        assertThat(offerPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOfferPrices() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList
        restOfferPriceMockMvc.perform(get("/api/offer-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].finishingDate").value(hasItem(DEFAULT_FINISHING_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getOfferPrice() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get the offerPrice
        restOfferPriceMockMvc.perform(get("/api/offer-prices/{id}", offerPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offerPrice.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.finishingDate").value(DEFAULT_FINISHING_DATE.toString()));
    }


    @Test
    @Transactional
    public void getOfferPricesByIdFiltering() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        Long id = offerPrice.getId();

        defaultOfferPriceShouldBeFound("id.equals=" + id);
        defaultOfferPriceShouldNotBeFound("id.notEquals=" + id);

        defaultOfferPriceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOfferPriceShouldNotBeFound("id.greaterThan=" + id);

        defaultOfferPriceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOfferPriceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOfferPricesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where price equals to DEFAULT_PRICE
        defaultOfferPriceShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the offerPriceList where price equals to UPDATED_PRICE
        defaultOfferPriceShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where price not equals to DEFAULT_PRICE
        defaultOfferPriceShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the offerPriceList where price not equals to UPDATED_PRICE
        defaultOfferPriceShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultOfferPriceShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the offerPriceList where price equals to UPDATED_PRICE
        defaultOfferPriceShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where price is not null
        defaultOfferPriceShouldBeFound("price.specified=true");

        // Get all the offerPriceList where price is null
        defaultOfferPriceShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfferPricesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where price is greater than or equal to DEFAULT_PRICE
        defaultOfferPriceShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the offerPriceList where price is greater than or equal to UPDATED_PRICE
        defaultOfferPriceShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where price is less than or equal to DEFAULT_PRICE
        defaultOfferPriceShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the offerPriceList where price is less than or equal to SMALLER_PRICE
        defaultOfferPriceShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where price is less than DEFAULT_PRICE
        defaultOfferPriceShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the offerPriceList where price is less than UPDATED_PRICE
        defaultOfferPriceShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where price is greater than DEFAULT_PRICE
        defaultOfferPriceShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the offerPriceList where price is greater than SMALLER_PRICE
        defaultOfferPriceShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllOfferPricesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where createdAt equals to DEFAULT_CREATED_AT
        defaultOfferPriceShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the offerPriceList where createdAt equals to UPDATED_CREATED_AT
        defaultOfferPriceShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where createdAt not equals to DEFAULT_CREATED_AT
        defaultOfferPriceShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the offerPriceList where createdAt not equals to UPDATED_CREATED_AT
        defaultOfferPriceShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultOfferPriceShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the offerPriceList where createdAt equals to UPDATED_CREATED_AT
        defaultOfferPriceShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where createdAt is not null
        defaultOfferPriceShouldBeFound("createdAt.specified=true");

        // Get all the offerPriceList where createdAt is null
        defaultOfferPriceShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfferPricesByFinishingDateIsEqualToSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where finishingDate equals to DEFAULT_FINISHING_DATE
        defaultOfferPriceShouldBeFound("finishingDate.equals=" + DEFAULT_FINISHING_DATE);

        // Get all the offerPriceList where finishingDate equals to UPDATED_FINISHING_DATE
        defaultOfferPriceShouldNotBeFound("finishingDate.equals=" + UPDATED_FINISHING_DATE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByFinishingDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where finishingDate not equals to DEFAULT_FINISHING_DATE
        defaultOfferPriceShouldNotBeFound("finishingDate.notEquals=" + DEFAULT_FINISHING_DATE);

        // Get all the offerPriceList where finishingDate not equals to UPDATED_FINISHING_DATE
        defaultOfferPriceShouldBeFound("finishingDate.notEquals=" + UPDATED_FINISHING_DATE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByFinishingDateIsInShouldWork() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where finishingDate in DEFAULT_FINISHING_DATE or UPDATED_FINISHING_DATE
        defaultOfferPriceShouldBeFound("finishingDate.in=" + DEFAULT_FINISHING_DATE + "," + UPDATED_FINISHING_DATE);

        // Get all the offerPriceList where finishingDate equals to UPDATED_FINISHING_DATE
        defaultOfferPriceShouldNotBeFound("finishingDate.in=" + UPDATED_FINISHING_DATE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByFinishingDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where finishingDate is not null
        defaultOfferPriceShouldBeFound("finishingDate.specified=true");

        // Get all the offerPriceList where finishingDate is null
        defaultOfferPriceShouldNotBeFound("finishingDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfferPricesByFinishingDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where finishingDate is greater than or equal to DEFAULT_FINISHING_DATE
        defaultOfferPriceShouldBeFound("finishingDate.greaterThanOrEqual=" + DEFAULT_FINISHING_DATE);

        // Get all the offerPriceList where finishingDate is greater than or equal to UPDATED_FINISHING_DATE
        defaultOfferPriceShouldNotBeFound("finishingDate.greaterThanOrEqual=" + UPDATED_FINISHING_DATE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByFinishingDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where finishingDate is less than or equal to DEFAULT_FINISHING_DATE
        defaultOfferPriceShouldBeFound("finishingDate.lessThanOrEqual=" + DEFAULT_FINISHING_DATE);

        // Get all the offerPriceList where finishingDate is less than or equal to SMALLER_FINISHING_DATE
        defaultOfferPriceShouldNotBeFound("finishingDate.lessThanOrEqual=" + SMALLER_FINISHING_DATE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByFinishingDateIsLessThanSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where finishingDate is less than DEFAULT_FINISHING_DATE
        defaultOfferPriceShouldNotBeFound("finishingDate.lessThan=" + DEFAULT_FINISHING_DATE);

        // Get all the offerPriceList where finishingDate is less than UPDATED_FINISHING_DATE
        defaultOfferPriceShouldBeFound("finishingDate.lessThan=" + UPDATED_FINISHING_DATE);
    }

    @Test
    @Transactional
    public void getAllOfferPricesByFinishingDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        // Get all the offerPriceList where finishingDate is greater than DEFAULT_FINISHING_DATE
        defaultOfferPriceShouldNotBeFound("finishingDate.greaterThan=" + DEFAULT_FINISHING_DATE);

        // Get all the offerPriceList where finishingDate is greater than SMALLER_FINISHING_DATE
        defaultOfferPriceShouldBeFound("finishingDate.greaterThan=" + SMALLER_FINISHING_DATE);
    }


    @Test
    @Transactional
    public void getAllOfferPricesByOfferIsEqualToSomething() throws Exception {
        // Get already existing entity
        Offer offer = offerPrice.getOffer();
        offerPriceRepository.saveAndFlush(offerPrice);
        Long offerId = offer.getId();

        // Get all the offerPriceList where offer equals to offerId
        defaultOfferPriceShouldBeFound("offerId.equals=" + offerId);

        // Get all the offerPriceList where offer equals to offerId + 1
        defaultOfferPriceShouldNotBeFound("offerId.equals=" + (offerId + 1));
    }


    @Test
    @Transactional
    public void getAllOfferPricesByEnquiryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Enquiry enquiry = offerPrice.getEnquiry();
        offerPriceRepository.saveAndFlush(offerPrice);
        Long enquiryId = enquiry.getId();

        // Get all the offerPriceList where enquiry equals to enquiryId
        defaultOfferPriceShouldBeFound("enquiryId.equals=" + enquiryId);

        // Get all the offerPriceList where enquiry equals to enquiryId + 1
        defaultOfferPriceShouldNotBeFound("enquiryId.equals=" + (enquiryId + 1));
    }


    @Test
    @Transactional
    public void getAllOfferPricesByEnquiryDetailsIsEqualToSomething() throws Exception {
        // Get already existing entity
        EnquiryDetails enquiryDetails = offerPrice.getEnquiryDetails();
        offerPriceRepository.saveAndFlush(offerPrice);
        Long enquiryDetailsId = enquiryDetails.getId();

        // Get all the offerPriceList where enquiryDetails equals to enquiryDetailsId
        defaultOfferPriceShouldBeFound("enquiryDetailsId.equals=" + enquiryDetailsId);

        // Get all the offerPriceList where enquiryDetails equals to enquiryDetailsId + 1
        defaultOfferPriceShouldNotBeFound("enquiryDetailsId.equals=" + (enquiryDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOfferPriceShouldBeFound(String filter) throws Exception {
        restOfferPriceMockMvc.perform(get("/api/offer-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].finishingDate").value(hasItem(DEFAULT_FINISHING_DATE.toString())));

        // Check, that the count call also returns 1
        restOfferPriceMockMvc.perform(get("/api/offer-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOfferPriceShouldNotBeFound(String filter) throws Exception {
        restOfferPriceMockMvc.perform(get("/api/offer-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOfferPriceMockMvc.perform(get("/api/offer-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOfferPrice() throws Exception {
        // Get the offerPrice
        restOfferPriceMockMvc.perform(get("/api/offer-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfferPrice() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        int databaseSizeBeforeUpdate = offerPriceRepository.findAll().size();

        // Update the offerPrice
        OfferPrice updatedOfferPrice = offerPriceRepository.findById(offerPrice.getId()).get();
        // Disconnect from session so that the updates on updatedOfferPrice are not directly saved in db
        em.detach(updatedOfferPrice);
        updatedOfferPrice
            .price(UPDATED_PRICE)
            .createdAt(UPDATED_CREATED_AT)
            .finishingDate(UPDATED_FINISHING_DATE);
        OfferPriceDTO offerPriceDTO = offerPriceMapper.toDto(updatedOfferPrice);

        restOfferPriceMockMvc.perform(put("/api/offer-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerPriceDTO)))
            .andExpect(status().isOk());

        // Validate the OfferPrice in the database
        List<OfferPrice> offerPriceList = offerPriceRepository.findAll();
        assertThat(offerPriceList).hasSize(databaseSizeBeforeUpdate);
        OfferPrice testOfferPrice = offerPriceList.get(offerPriceList.size() - 1);
        assertThat(testOfferPrice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOfferPrice.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOfferPrice.getFinishingDate()).isEqualTo(UPDATED_FINISHING_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOfferPrice() throws Exception {
        int databaseSizeBeforeUpdate = offerPriceRepository.findAll().size();

        // Create the OfferPrice
        OfferPriceDTO offerPriceDTO = offerPriceMapper.toDto(offerPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferPriceMockMvc.perform(put("/api/offer-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OfferPrice in the database
        List<OfferPrice> offerPriceList = offerPriceRepository.findAll();
        assertThat(offerPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfferPrice() throws Exception {
        // Initialize the database
        offerPriceRepository.saveAndFlush(offerPrice);

        int databaseSizeBeforeDelete = offerPriceRepository.findAll().size();

        // Delete the offerPrice
        restOfferPriceMockMvc.perform(delete("/api/offer-prices/{id}", offerPrice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfferPrice> offerPriceList = offerPriceRepository.findAll();
        assertThat(offerPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
