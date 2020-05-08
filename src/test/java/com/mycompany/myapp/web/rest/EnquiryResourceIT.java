package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Enquiry;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.repository.EnquiryRepository;
import com.mycompany.myapp.service.EnquiryService;
import com.mycompany.myapp.service.dto.EnquiryDTO;
import com.mycompany.myapp.service.mapper.EnquiryMapper;
import com.mycompany.myapp.service.dto.EnquiryCriteria;
import com.mycompany.myapp.service.EnquiryQueryService;

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
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EnquiryResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EnquiryResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_TERMS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_TERMS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_OFFER_TAXT_UNTIL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OFFER_TAXT_UNTIL = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_OFFER_TAXT_UNTIL = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private EnquiryMapper enquiryMapper;

    @Autowired
    private EnquiryService enquiryService;

    @Autowired
    private EnquiryQueryService enquiryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnquiryMockMvc;

    private Enquiry enquiry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enquiry createEntity(EntityManager em) {
        Enquiry enquiry = new Enquiry()
            .description(DEFAULT_DESCRIPTION)
            .deliveryTerms(DEFAULT_DELIVERY_TERMS)
            .offerTaxtUntil(DEFAULT_OFFER_TAXT_UNTIL)
            .status(DEFAULT_STATUS);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        enquiry.setProduct(category);
        // Add required entity
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        enquiry.setDeliveryAddress(address);
        return enquiry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enquiry createUpdatedEntity(EntityManager em) {
        Enquiry enquiry = new Enquiry()
            .description(UPDATED_DESCRIPTION)
            .deliveryTerms(UPDATED_DELIVERY_TERMS)
            .offerTaxtUntil(UPDATED_OFFER_TAXT_UNTIL)
            .status(UPDATED_STATUS);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        enquiry.setProduct(category);
        // Add required entity
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            address = AddressResourceIT.createUpdatedEntity(em);
            em.persist(address);
            em.flush();
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        enquiry.setDeliveryAddress(address);
        return enquiry;
    }

    @BeforeEach
    public void initTest() {
        enquiry = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnquiry() throws Exception {
        int databaseSizeBeforeCreate = enquiryRepository.findAll().size();

        // Create the Enquiry
        EnquiryDTO enquiryDTO = enquiryMapper.toDto(enquiry);
        restEnquiryMockMvc.perform(post("/api/enquiries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDTO)))
            .andExpect(status().isCreated());

        // Validate the Enquiry in the database
        List<Enquiry> enquiryList = enquiryRepository.findAll();
        assertThat(enquiryList).hasSize(databaseSizeBeforeCreate + 1);
        Enquiry testEnquiry = enquiryList.get(enquiryList.size() - 1);
        assertThat(testEnquiry.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEnquiry.getDeliveryTerms()).isEqualTo(DEFAULT_DELIVERY_TERMS);
        assertThat(testEnquiry.getOfferTaxtUntil()).isEqualTo(DEFAULT_OFFER_TAXT_UNTIL);
        assertThat(testEnquiry.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createEnquiryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enquiryRepository.findAll().size();

        // Create the Enquiry with an existing ID
        enquiry.setId(1L);
        EnquiryDTO enquiryDTO = enquiryMapper.toDto(enquiry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnquiryMockMvc.perform(post("/api/enquiries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enquiry in the database
        List<Enquiry> enquiryList = enquiryRepository.findAll();
        assertThat(enquiryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryRepository.findAll().size();
        // set the field null
        enquiry.setDescription(null);

        // Create the Enquiry, which fails.
        EnquiryDTO enquiryDTO = enquiryMapper.toDto(enquiry);

        restEnquiryMockMvc.perform(post("/api/enquiries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDTO)))
            .andExpect(status().isBadRequest());

        List<Enquiry> enquiryList = enquiryRepository.findAll();
        assertThat(enquiryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryRepository.findAll().size();
        // set the field null
        enquiry.setStatus(null);

        // Create the Enquiry, which fails.
        EnquiryDTO enquiryDTO = enquiryMapper.toDto(enquiry);

        restEnquiryMockMvc.perform(post("/api/enquiries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDTO)))
            .andExpect(status().isBadRequest());

        List<Enquiry> enquiryList = enquiryRepository.findAll();
        assertThat(enquiryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnquiries() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList
        restEnquiryMockMvc.perform(get("/api/enquiries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquiry.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].deliveryTerms").value(hasItem(DEFAULT_DELIVERY_TERMS)))
            .andExpect(jsonPath("$.[*].offerTaxtUntil").value(hasItem(DEFAULT_OFFER_TAXT_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getEnquiry() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get the enquiry
        restEnquiryMockMvc.perform(get("/api/enquiries/{id}", enquiry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enquiry.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.deliveryTerms").value(DEFAULT_DELIVERY_TERMS))
            .andExpect(jsonPath("$.offerTaxtUntil").value(DEFAULT_OFFER_TAXT_UNTIL.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }


    @Test
    @Transactional
    public void getEnquiriesByIdFiltering() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        Long id = enquiry.getId();

        defaultEnquiryShouldBeFound("id.equals=" + id);
        defaultEnquiryShouldNotBeFound("id.notEquals=" + id);

        defaultEnquiryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnquiryShouldNotBeFound("id.greaterThan=" + id);

        defaultEnquiryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnquiryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEnquiriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where description equals to DEFAULT_DESCRIPTION
        defaultEnquiryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the enquiryList where description equals to UPDATED_DESCRIPTION
        defaultEnquiryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where description not equals to DEFAULT_DESCRIPTION
        defaultEnquiryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the enquiryList where description not equals to UPDATED_DESCRIPTION
        defaultEnquiryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEnquiryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the enquiryList where description equals to UPDATED_DESCRIPTION
        defaultEnquiryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where description is not null
        defaultEnquiryShouldBeFound("description.specified=true");

        // Get all the enquiryList where description is null
        defaultEnquiryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnquiriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where description contains DEFAULT_DESCRIPTION
        defaultEnquiryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the enquiryList where description contains UPDATED_DESCRIPTION
        defaultEnquiryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where description does not contain DEFAULT_DESCRIPTION
        defaultEnquiryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the enquiryList where description does not contain UPDATED_DESCRIPTION
        defaultEnquiryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllEnquiriesByDeliveryTermsIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where deliveryTerms equals to DEFAULT_DELIVERY_TERMS
        defaultEnquiryShouldBeFound("deliveryTerms.equals=" + DEFAULT_DELIVERY_TERMS);

        // Get all the enquiryList where deliveryTerms equals to UPDATED_DELIVERY_TERMS
        defaultEnquiryShouldNotBeFound("deliveryTerms.equals=" + UPDATED_DELIVERY_TERMS);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByDeliveryTermsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where deliveryTerms not equals to DEFAULT_DELIVERY_TERMS
        defaultEnquiryShouldNotBeFound("deliveryTerms.notEquals=" + DEFAULT_DELIVERY_TERMS);

        // Get all the enquiryList where deliveryTerms not equals to UPDATED_DELIVERY_TERMS
        defaultEnquiryShouldBeFound("deliveryTerms.notEquals=" + UPDATED_DELIVERY_TERMS);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByDeliveryTermsIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where deliveryTerms in DEFAULT_DELIVERY_TERMS or UPDATED_DELIVERY_TERMS
        defaultEnquiryShouldBeFound("deliveryTerms.in=" + DEFAULT_DELIVERY_TERMS + "," + UPDATED_DELIVERY_TERMS);

        // Get all the enquiryList where deliveryTerms equals to UPDATED_DELIVERY_TERMS
        defaultEnquiryShouldNotBeFound("deliveryTerms.in=" + UPDATED_DELIVERY_TERMS);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByDeliveryTermsIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where deliveryTerms is not null
        defaultEnquiryShouldBeFound("deliveryTerms.specified=true");

        // Get all the enquiryList where deliveryTerms is null
        defaultEnquiryShouldNotBeFound("deliveryTerms.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnquiriesByDeliveryTermsContainsSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where deliveryTerms contains DEFAULT_DELIVERY_TERMS
        defaultEnquiryShouldBeFound("deliveryTerms.contains=" + DEFAULT_DELIVERY_TERMS);

        // Get all the enquiryList where deliveryTerms contains UPDATED_DELIVERY_TERMS
        defaultEnquiryShouldNotBeFound("deliveryTerms.contains=" + UPDATED_DELIVERY_TERMS);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByDeliveryTermsNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where deliveryTerms does not contain DEFAULT_DELIVERY_TERMS
        defaultEnquiryShouldNotBeFound("deliveryTerms.doesNotContain=" + DEFAULT_DELIVERY_TERMS);

        // Get all the enquiryList where deliveryTerms does not contain UPDATED_DELIVERY_TERMS
        defaultEnquiryShouldBeFound("deliveryTerms.doesNotContain=" + UPDATED_DELIVERY_TERMS);
    }


    @Test
    @Transactional
    public void getAllEnquiriesByOfferTaxtUntilIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where offerTaxtUntil equals to DEFAULT_OFFER_TAXT_UNTIL
        defaultEnquiryShouldBeFound("offerTaxtUntil.equals=" + DEFAULT_OFFER_TAXT_UNTIL);

        // Get all the enquiryList where offerTaxtUntil equals to UPDATED_OFFER_TAXT_UNTIL
        defaultEnquiryShouldNotBeFound("offerTaxtUntil.equals=" + UPDATED_OFFER_TAXT_UNTIL);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByOfferTaxtUntilIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where offerTaxtUntil not equals to DEFAULT_OFFER_TAXT_UNTIL
        defaultEnquiryShouldNotBeFound("offerTaxtUntil.notEquals=" + DEFAULT_OFFER_TAXT_UNTIL);

        // Get all the enquiryList where offerTaxtUntil not equals to UPDATED_OFFER_TAXT_UNTIL
        defaultEnquiryShouldBeFound("offerTaxtUntil.notEquals=" + UPDATED_OFFER_TAXT_UNTIL);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByOfferTaxtUntilIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where offerTaxtUntil in DEFAULT_OFFER_TAXT_UNTIL or UPDATED_OFFER_TAXT_UNTIL
        defaultEnquiryShouldBeFound("offerTaxtUntil.in=" + DEFAULT_OFFER_TAXT_UNTIL + "," + UPDATED_OFFER_TAXT_UNTIL);

        // Get all the enquiryList where offerTaxtUntil equals to UPDATED_OFFER_TAXT_UNTIL
        defaultEnquiryShouldNotBeFound("offerTaxtUntil.in=" + UPDATED_OFFER_TAXT_UNTIL);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByOfferTaxtUntilIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where offerTaxtUntil is not null
        defaultEnquiryShouldBeFound("offerTaxtUntil.specified=true");

        // Get all the enquiryList where offerTaxtUntil is null
        defaultEnquiryShouldNotBeFound("offerTaxtUntil.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnquiriesByOfferTaxtUntilIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where offerTaxtUntil is greater than or equal to DEFAULT_OFFER_TAXT_UNTIL
        defaultEnquiryShouldBeFound("offerTaxtUntil.greaterThanOrEqual=" + DEFAULT_OFFER_TAXT_UNTIL);

        // Get all the enquiryList where offerTaxtUntil is greater than or equal to UPDATED_OFFER_TAXT_UNTIL
        defaultEnquiryShouldNotBeFound("offerTaxtUntil.greaterThanOrEqual=" + UPDATED_OFFER_TAXT_UNTIL);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByOfferTaxtUntilIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where offerTaxtUntil is less than or equal to DEFAULT_OFFER_TAXT_UNTIL
        defaultEnquiryShouldBeFound("offerTaxtUntil.lessThanOrEqual=" + DEFAULT_OFFER_TAXT_UNTIL);

        // Get all the enquiryList where offerTaxtUntil is less than or equal to SMALLER_OFFER_TAXT_UNTIL
        defaultEnquiryShouldNotBeFound("offerTaxtUntil.lessThanOrEqual=" + SMALLER_OFFER_TAXT_UNTIL);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByOfferTaxtUntilIsLessThanSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where offerTaxtUntil is less than DEFAULT_OFFER_TAXT_UNTIL
        defaultEnquiryShouldNotBeFound("offerTaxtUntil.lessThan=" + DEFAULT_OFFER_TAXT_UNTIL);

        // Get all the enquiryList where offerTaxtUntil is less than UPDATED_OFFER_TAXT_UNTIL
        defaultEnquiryShouldBeFound("offerTaxtUntil.lessThan=" + UPDATED_OFFER_TAXT_UNTIL);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByOfferTaxtUntilIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where offerTaxtUntil is greater than DEFAULT_OFFER_TAXT_UNTIL
        defaultEnquiryShouldNotBeFound("offerTaxtUntil.greaterThan=" + DEFAULT_OFFER_TAXT_UNTIL);

        // Get all the enquiryList where offerTaxtUntil is greater than SMALLER_OFFER_TAXT_UNTIL
        defaultEnquiryShouldBeFound("offerTaxtUntil.greaterThan=" + SMALLER_OFFER_TAXT_UNTIL);
    }


    @Test
    @Transactional
    public void getAllEnquiriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where status equals to DEFAULT_STATUS
        defaultEnquiryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the enquiryList where status equals to UPDATED_STATUS
        defaultEnquiryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where status not equals to DEFAULT_STATUS
        defaultEnquiryShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the enquiryList where status not equals to UPDATED_STATUS
        defaultEnquiryShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultEnquiryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the enquiryList where status equals to UPDATED_STATUS
        defaultEnquiryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where status is not null
        defaultEnquiryShouldBeFound("status.specified=true");

        // Get all the enquiryList where status is null
        defaultEnquiryShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnquiriesByStatusContainsSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where status contains DEFAULT_STATUS
        defaultEnquiryShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the enquiryList where status contains UPDATED_STATUS
        defaultEnquiryShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllEnquiriesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        // Get all the enquiryList where status does not contain DEFAULT_STATUS
        defaultEnquiryShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the enquiryList where status does not contain UPDATED_STATUS
        defaultEnquiryShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllEnquiriesByProductIsEqualToSomething() throws Exception {
        // Get already existing entity
        Category product = enquiry.getProduct();
        enquiryRepository.saveAndFlush(enquiry);
        Long productId = product.getId();

        // Get all the enquiryList where product equals to productId
        defaultEnquiryShouldBeFound("productId.equals=" + productId);

        // Get all the enquiryList where product equals to productId + 1
        defaultEnquiryShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllEnquiriesByDeliveryAddressIsEqualToSomething() throws Exception {
        // Get already existing entity
        Address deliveryAddress = enquiry.getDeliveryAddress();
        enquiryRepository.saveAndFlush(enquiry);
        Long deliveryAddressId = deliveryAddress.getId();

        // Get all the enquiryList where deliveryAddress equals to deliveryAddressId
        defaultEnquiryShouldBeFound("deliveryAddressId.equals=" + deliveryAddressId);

        // Get all the enquiryList where deliveryAddress equals to deliveryAddressId + 1
        defaultEnquiryShouldNotBeFound("deliveryAddressId.equals=" + (deliveryAddressId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnquiryShouldBeFound(String filter) throws Exception {
        restEnquiryMockMvc.perform(get("/api/enquiries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquiry.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].deliveryTerms").value(hasItem(DEFAULT_DELIVERY_TERMS)))
            .andExpect(jsonPath("$.[*].offerTaxtUntil").value(hasItem(DEFAULT_OFFER_TAXT_UNTIL.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restEnquiryMockMvc.perform(get("/api/enquiries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnquiryShouldNotBeFound(String filter) throws Exception {
        restEnquiryMockMvc.perform(get("/api/enquiries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnquiryMockMvc.perform(get("/api/enquiries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEnquiry() throws Exception {
        // Get the enquiry
        restEnquiryMockMvc.perform(get("/api/enquiries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnquiry() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        int databaseSizeBeforeUpdate = enquiryRepository.findAll().size();

        // Update the enquiry
        Enquiry updatedEnquiry = enquiryRepository.findById(enquiry.getId()).get();
        // Disconnect from session so that the updates on updatedEnquiry are not directly saved in db
        em.detach(updatedEnquiry);
        updatedEnquiry
            .description(UPDATED_DESCRIPTION)
            .deliveryTerms(UPDATED_DELIVERY_TERMS)
            .offerTaxtUntil(UPDATED_OFFER_TAXT_UNTIL)
            .status(UPDATED_STATUS);
        EnquiryDTO enquiryDTO = enquiryMapper.toDto(updatedEnquiry);

        restEnquiryMockMvc.perform(put("/api/enquiries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDTO)))
            .andExpect(status().isOk());

        // Validate the Enquiry in the database
        List<Enquiry> enquiryList = enquiryRepository.findAll();
        assertThat(enquiryList).hasSize(databaseSizeBeforeUpdate);
        Enquiry testEnquiry = enquiryList.get(enquiryList.size() - 1);
        assertThat(testEnquiry.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEnquiry.getDeliveryTerms()).isEqualTo(UPDATED_DELIVERY_TERMS);
        assertThat(testEnquiry.getOfferTaxtUntil()).isEqualTo(UPDATED_OFFER_TAXT_UNTIL);
        assertThat(testEnquiry.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingEnquiry() throws Exception {
        int databaseSizeBeforeUpdate = enquiryRepository.findAll().size();

        // Create the Enquiry
        EnquiryDTO enquiryDTO = enquiryMapper.toDto(enquiry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnquiryMockMvc.perform(put("/api/enquiries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Enquiry in the database
        List<Enquiry> enquiryList = enquiryRepository.findAll();
        assertThat(enquiryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnquiry() throws Exception {
        // Initialize the database
        enquiryRepository.saveAndFlush(enquiry);

        int databaseSizeBeforeDelete = enquiryRepository.findAll().size();

        // Delete the enquiry
        restEnquiryMockMvc.perform(delete("/api/enquiries/{id}", enquiry.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Enquiry> enquiryList = enquiryRepository.findAll();
        assertThat(enquiryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
