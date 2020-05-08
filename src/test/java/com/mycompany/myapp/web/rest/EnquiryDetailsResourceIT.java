package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.EnquiryDetails;
import com.mycompany.myapp.domain.Enquiry;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.domain.Offer;
import com.mycompany.myapp.repository.EnquiryDetailsRepository;
import com.mycompany.myapp.service.EnquiryDetailsService;
import com.mycompany.myapp.service.dto.EnquiryDetailsDTO;
import com.mycompany.myapp.service.mapper.EnquiryDetailsMapper;
import com.mycompany.myapp.service.dto.EnquiryDetailsCriteria;
import com.mycompany.myapp.service.EnquiryDetailsQueryService;

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
 * Integration tests for the {@link EnquiryDetailsResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EnquiryDetailsResourceIT {

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final Double DEFAULT_EDITION = 1D;
    private static final Double UPDATED_EDITION = 2D;
    private static final Double SMALLER_EDITION = 1D - 1D;

    private static final Integer DEFAULT_FORMAT = 1;
    private static final Integer UPDATED_FORMAT = 2;
    private static final Integer SMALLER_FORMAT = 1 - 1;

    private static final String DEFAULT_DOCUMENTS = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DELIVERY_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EnquiryDetailsRepository enquiryDetailsRepository;

    @Autowired
    private EnquiryDetailsMapper enquiryDetailsMapper;

    @Autowired
    private EnquiryDetailsService enquiryDetailsService;

    @Autowired
    private EnquiryDetailsQueryService enquiryDetailsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnquiryDetailsMockMvc;

    private EnquiryDetails enquiryDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnquiryDetails createEntity(EntityManager em) {
        EnquiryDetails enquiryDetails = new EnquiryDetails()
            .version(DEFAULT_VERSION)
            .edition(DEFAULT_EDITION)
            .format(DEFAULT_FORMAT)
            .documents(DEFAULT_DOCUMENTS)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .remarks(DEFAULT_REMARKS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        enquiryDetails.setEnquiry(enquiry);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        enquiryDetails.setPrint(category);
        // Add required entity
        enquiryDetails.setFinishing(category);
        // Add required entity
        enquiryDetails.setHandling(category);
        // Add required entity
        enquiryDetails.setPackaging(category);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        enquiryDetails.setCreatedBy(userProfile);
        return enquiryDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnquiryDetails createUpdatedEntity(EntityManager em) {
        EnquiryDetails enquiryDetails = new EnquiryDetails()
            .version(UPDATED_VERSION)
            .edition(UPDATED_EDITION)
            .format(UPDATED_FORMAT)
            .documents(UPDATED_DOCUMENTS)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .remarks(UPDATED_REMARKS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createUpdatedEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        enquiryDetails.setEnquiry(enquiry);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        enquiryDetails.setPrint(category);
        // Add required entity
        enquiryDetails.setFinishing(category);
        // Add required entity
        enquiryDetails.setHandling(category);
        // Add required entity
        enquiryDetails.setPackaging(category);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        enquiryDetails.setCreatedBy(userProfile);
        return enquiryDetails;
    }

    @BeforeEach
    public void initTest() {
        enquiryDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnquiryDetails() throws Exception {
        int databaseSizeBeforeCreate = enquiryDetailsRepository.findAll().size();

        // Create the EnquiryDetails
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);
        restEnquiryDetailsMockMvc.perform(post("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        EnquiryDetails testEnquiryDetails = enquiryDetailsList.get(enquiryDetailsList.size() - 1);
        assertThat(testEnquiryDetails.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testEnquiryDetails.getEdition()).isEqualTo(DEFAULT_EDITION);
        assertThat(testEnquiryDetails.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testEnquiryDetails.getDocuments()).isEqualTo(DEFAULT_DOCUMENTS);
        assertThat(testEnquiryDetails.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testEnquiryDetails.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testEnquiryDetails.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testEnquiryDetails.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createEnquiryDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enquiryDetailsRepository.findAll().size();

        // Create the EnquiryDetails with an existing ID
        enquiryDetails.setId(1L);
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnquiryDetailsMockMvc.perform(post("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryDetailsRepository.findAll().size();
        // set the field null
        enquiryDetails.setVersion(null);

        // Create the EnquiryDetails, which fails.
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        restEnquiryDetailsMockMvc.perform(post("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEditionIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryDetailsRepository.findAll().size();
        // set the field null
        enquiryDetails.setEdition(null);

        // Create the EnquiryDetails, which fails.
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        restEnquiryDetailsMockMvc.perform(post("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormatIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryDetailsRepository.findAll().size();
        // set the field null
        enquiryDetails.setFormat(null);

        // Create the EnquiryDetails, which fails.
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        restEnquiryDetailsMockMvc.perform(post("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentsIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryDetailsRepository.findAll().size();
        // set the field null
        enquiryDetails.setDocuments(null);

        // Create the EnquiryDetails, which fails.
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        restEnquiryDetailsMockMvc.perform(post("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryDetailsRepository.findAll().size();
        // set the field null
        enquiryDetails.setDeliveryDate(null);

        // Create the EnquiryDetails, which fails.
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        restEnquiryDetailsMockMvc.perform(post("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRemarksIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryDetailsRepository.findAll().size();
        // set the field null
        enquiryDetails.setRemarks(null);

        // Create the EnquiryDetails, which fails.
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        restEnquiryDetailsMockMvc.perform(post("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryDetailsRepository.findAll().size();
        // set the field null
        enquiryDetails.setCreatedAt(null);

        // Create the EnquiryDetails, which fails.
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        restEnquiryDetailsMockMvc.perform(post("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryDetailsRepository.findAll().size();
        // set the field null
        enquiryDetails.setUpdatedAt(null);

        // Create the EnquiryDetails, which fails.
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        restEnquiryDetailsMockMvc.perform(post("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetails() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList
        restEnquiryDetailsMockMvc.perform(get("/api/enquiry-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquiryDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].edition").value(hasItem(DEFAULT_EDITION.doubleValue())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT)))
            .andExpect(jsonPath("$.[*].documents").value(hasItem(DEFAULT_DOCUMENTS)))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getEnquiryDetails() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get the enquiryDetails
        restEnquiryDetailsMockMvc.perform(get("/api/enquiry-details/{id}", enquiryDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enquiryDetails.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.edition").value(DEFAULT_EDITION.doubleValue()))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT))
            .andExpect(jsonPath("$.documents").value(DEFAULT_DOCUMENTS))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getEnquiryDetailsByIdFiltering() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        Long id = enquiryDetails.getId();

        defaultEnquiryDetailsShouldBeFound("id.equals=" + id);
        defaultEnquiryDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultEnquiryDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnquiryDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultEnquiryDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnquiryDetailsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where version equals to DEFAULT_VERSION
        defaultEnquiryDetailsShouldBeFound("version.equals=" + DEFAULT_VERSION);

        // Get all the enquiryDetailsList where version equals to UPDATED_VERSION
        defaultEnquiryDetailsShouldNotBeFound("version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByVersionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where version not equals to DEFAULT_VERSION
        defaultEnquiryDetailsShouldNotBeFound("version.notEquals=" + DEFAULT_VERSION);

        // Get all the enquiryDetailsList where version not equals to UPDATED_VERSION
        defaultEnquiryDetailsShouldBeFound("version.notEquals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where version in DEFAULT_VERSION or UPDATED_VERSION
        defaultEnquiryDetailsShouldBeFound("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION);

        // Get all the enquiryDetailsList where version equals to UPDATED_VERSION
        defaultEnquiryDetailsShouldNotBeFound("version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where version is not null
        defaultEnquiryDetailsShouldBeFound("version.specified=true");

        // Get all the enquiryDetailsList where version is null
        defaultEnquiryDetailsShouldNotBeFound("version.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where version is greater than or equal to DEFAULT_VERSION
        defaultEnquiryDetailsShouldBeFound("version.greaterThanOrEqual=" + DEFAULT_VERSION);

        // Get all the enquiryDetailsList where version is greater than or equal to UPDATED_VERSION
        defaultEnquiryDetailsShouldNotBeFound("version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where version is less than or equal to DEFAULT_VERSION
        defaultEnquiryDetailsShouldBeFound("version.lessThanOrEqual=" + DEFAULT_VERSION);

        // Get all the enquiryDetailsList where version is less than or equal to SMALLER_VERSION
        defaultEnquiryDetailsShouldNotBeFound("version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where version is less than DEFAULT_VERSION
        defaultEnquiryDetailsShouldNotBeFound("version.lessThan=" + DEFAULT_VERSION);

        // Get all the enquiryDetailsList where version is less than UPDATED_VERSION
        defaultEnquiryDetailsShouldBeFound("version.lessThan=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where version is greater than DEFAULT_VERSION
        defaultEnquiryDetailsShouldNotBeFound("version.greaterThan=" + DEFAULT_VERSION);

        // Get all the enquiryDetailsList where version is greater than SMALLER_VERSION
        defaultEnquiryDetailsShouldBeFound("version.greaterThan=" + SMALLER_VERSION);
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByEditionIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where edition equals to DEFAULT_EDITION
        defaultEnquiryDetailsShouldBeFound("edition.equals=" + DEFAULT_EDITION);

        // Get all the enquiryDetailsList where edition equals to UPDATED_EDITION
        defaultEnquiryDetailsShouldNotBeFound("edition.equals=" + UPDATED_EDITION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByEditionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where edition not equals to DEFAULT_EDITION
        defaultEnquiryDetailsShouldNotBeFound("edition.notEquals=" + DEFAULT_EDITION);

        // Get all the enquiryDetailsList where edition not equals to UPDATED_EDITION
        defaultEnquiryDetailsShouldBeFound("edition.notEquals=" + UPDATED_EDITION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByEditionIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where edition in DEFAULT_EDITION or UPDATED_EDITION
        defaultEnquiryDetailsShouldBeFound("edition.in=" + DEFAULT_EDITION + "," + UPDATED_EDITION);

        // Get all the enquiryDetailsList where edition equals to UPDATED_EDITION
        defaultEnquiryDetailsShouldNotBeFound("edition.in=" + UPDATED_EDITION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByEditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where edition is not null
        defaultEnquiryDetailsShouldBeFound("edition.specified=true");

        // Get all the enquiryDetailsList where edition is null
        defaultEnquiryDetailsShouldNotBeFound("edition.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByEditionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where edition is greater than or equal to DEFAULT_EDITION
        defaultEnquiryDetailsShouldBeFound("edition.greaterThanOrEqual=" + DEFAULT_EDITION);

        // Get all the enquiryDetailsList where edition is greater than or equal to UPDATED_EDITION
        defaultEnquiryDetailsShouldNotBeFound("edition.greaterThanOrEqual=" + UPDATED_EDITION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByEditionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where edition is less than or equal to DEFAULT_EDITION
        defaultEnquiryDetailsShouldBeFound("edition.lessThanOrEqual=" + DEFAULT_EDITION);

        // Get all the enquiryDetailsList where edition is less than or equal to SMALLER_EDITION
        defaultEnquiryDetailsShouldNotBeFound("edition.lessThanOrEqual=" + SMALLER_EDITION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByEditionIsLessThanSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where edition is less than DEFAULT_EDITION
        defaultEnquiryDetailsShouldNotBeFound("edition.lessThan=" + DEFAULT_EDITION);

        // Get all the enquiryDetailsList where edition is less than UPDATED_EDITION
        defaultEnquiryDetailsShouldBeFound("edition.lessThan=" + UPDATED_EDITION);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByEditionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where edition is greater than DEFAULT_EDITION
        defaultEnquiryDetailsShouldNotBeFound("edition.greaterThan=" + DEFAULT_EDITION);

        // Get all the enquiryDetailsList where edition is greater than SMALLER_EDITION
        defaultEnquiryDetailsShouldBeFound("edition.greaterThan=" + SMALLER_EDITION);
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByFormatIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where format equals to DEFAULT_FORMAT
        defaultEnquiryDetailsShouldBeFound("format.equals=" + DEFAULT_FORMAT);

        // Get all the enquiryDetailsList where format equals to UPDATED_FORMAT
        defaultEnquiryDetailsShouldNotBeFound("format.equals=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByFormatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where format not equals to DEFAULT_FORMAT
        defaultEnquiryDetailsShouldNotBeFound("format.notEquals=" + DEFAULT_FORMAT);

        // Get all the enquiryDetailsList where format not equals to UPDATED_FORMAT
        defaultEnquiryDetailsShouldBeFound("format.notEquals=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByFormatIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where format in DEFAULT_FORMAT or UPDATED_FORMAT
        defaultEnquiryDetailsShouldBeFound("format.in=" + DEFAULT_FORMAT + "," + UPDATED_FORMAT);

        // Get all the enquiryDetailsList where format equals to UPDATED_FORMAT
        defaultEnquiryDetailsShouldNotBeFound("format.in=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByFormatIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where format is not null
        defaultEnquiryDetailsShouldBeFound("format.specified=true");

        // Get all the enquiryDetailsList where format is null
        defaultEnquiryDetailsShouldNotBeFound("format.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByFormatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where format is greater than or equal to DEFAULT_FORMAT
        defaultEnquiryDetailsShouldBeFound("format.greaterThanOrEqual=" + DEFAULT_FORMAT);

        // Get all the enquiryDetailsList where format is greater than or equal to UPDATED_FORMAT
        defaultEnquiryDetailsShouldNotBeFound("format.greaterThanOrEqual=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByFormatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where format is less than or equal to DEFAULT_FORMAT
        defaultEnquiryDetailsShouldBeFound("format.lessThanOrEqual=" + DEFAULT_FORMAT);

        // Get all the enquiryDetailsList where format is less than or equal to SMALLER_FORMAT
        defaultEnquiryDetailsShouldNotBeFound("format.lessThanOrEqual=" + SMALLER_FORMAT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByFormatIsLessThanSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where format is less than DEFAULT_FORMAT
        defaultEnquiryDetailsShouldNotBeFound("format.lessThan=" + DEFAULT_FORMAT);

        // Get all the enquiryDetailsList where format is less than UPDATED_FORMAT
        defaultEnquiryDetailsShouldBeFound("format.lessThan=" + UPDATED_FORMAT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByFormatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where format is greater than DEFAULT_FORMAT
        defaultEnquiryDetailsShouldNotBeFound("format.greaterThan=" + DEFAULT_FORMAT);

        // Get all the enquiryDetailsList where format is greater than SMALLER_FORMAT
        defaultEnquiryDetailsShouldBeFound("format.greaterThan=" + SMALLER_FORMAT);
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByDocumentsIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where documents equals to DEFAULT_DOCUMENTS
        defaultEnquiryDetailsShouldBeFound("documents.equals=" + DEFAULT_DOCUMENTS);

        // Get all the enquiryDetailsList where documents equals to UPDATED_DOCUMENTS
        defaultEnquiryDetailsShouldNotBeFound("documents.equals=" + UPDATED_DOCUMENTS);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByDocumentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where documents not equals to DEFAULT_DOCUMENTS
        defaultEnquiryDetailsShouldNotBeFound("documents.notEquals=" + DEFAULT_DOCUMENTS);

        // Get all the enquiryDetailsList where documents not equals to UPDATED_DOCUMENTS
        defaultEnquiryDetailsShouldBeFound("documents.notEquals=" + UPDATED_DOCUMENTS);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByDocumentsIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where documents in DEFAULT_DOCUMENTS or UPDATED_DOCUMENTS
        defaultEnquiryDetailsShouldBeFound("documents.in=" + DEFAULT_DOCUMENTS + "," + UPDATED_DOCUMENTS);

        // Get all the enquiryDetailsList where documents equals to UPDATED_DOCUMENTS
        defaultEnquiryDetailsShouldNotBeFound("documents.in=" + UPDATED_DOCUMENTS);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByDocumentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where documents is not null
        defaultEnquiryDetailsShouldBeFound("documents.specified=true");

        // Get all the enquiryDetailsList where documents is null
        defaultEnquiryDetailsShouldNotBeFound("documents.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnquiryDetailsByDocumentsContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where documents contains DEFAULT_DOCUMENTS
        defaultEnquiryDetailsShouldBeFound("documents.contains=" + DEFAULT_DOCUMENTS);

        // Get all the enquiryDetailsList where documents contains UPDATED_DOCUMENTS
        defaultEnquiryDetailsShouldNotBeFound("documents.contains=" + UPDATED_DOCUMENTS);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByDocumentsNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where documents does not contain DEFAULT_DOCUMENTS
        defaultEnquiryDetailsShouldNotBeFound("documents.doesNotContain=" + DEFAULT_DOCUMENTS);

        // Get all the enquiryDetailsList where documents does not contain UPDATED_DOCUMENTS
        defaultEnquiryDetailsShouldBeFound("documents.doesNotContain=" + UPDATED_DOCUMENTS);
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where deliveryDate equals to DEFAULT_DELIVERY_DATE
        defaultEnquiryDetailsShouldBeFound("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE);

        // Get all the enquiryDetailsList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultEnquiryDetailsShouldNotBeFound("deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where deliveryDate not equals to DEFAULT_DELIVERY_DATE
        defaultEnquiryDetailsShouldNotBeFound("deliveryDate.notEquals=" + DEFAULT_DELIVERY_DATE);

        // Get all the enquiryDetailsList where deliveryDate not equals to UPDATED_DELIVERY_DATE
        defaultEnquiryDetailsShouldBeFound("deliveryDate.notEquals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where deliveryDate in DEFAULT_DELIVERY_DATE or UPDATED_DELIVERY_DATE
        defaultEnquiryDetailsShouldBeFound("deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE);

        // Get all the enquiryDetailsList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultEnquiryDetailsShouldNotBeFound("deliveryDate.in=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where deliveryDate is not null
        defaultEnquiryDetailsShouldBeFound("deliveryDate.specified=true");

        // Get all the enquiryDetailsList where deliveryDate is null
        defaultEnquiryDetailsShouldNotBeFound("deliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where deliveryDate is greater than or equal to DEFAULT_DELIVERY_DATE
        defaultEnquiryDetailsShouldBeFound("deliveryDate.greaterThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the enquiryDetailsList where deliveryDate is greater than or equal to UPDATED_DELIVERY_DATE
        defaultEnquiryDetailsShouldNotBeFound("deliveryDate.greaterThanOrEqual=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where deliveryDate is less than or equal to DEFAULT_DELIVERY_DATE
        defaultEnquiryDetailsShouldBeFound("deliveryDate.lessThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the enquiryDetailsList where deliveryDate is less than or equal to SMALLER_DELIVERY_DATE
        defaultEnquiryDetailsShouldNotBeFound("deliveryDate.lessThanOrEqual=" + SMALLER_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where deliveryDate is less than DEFAULT_DELIVERY_DATE
        defaultEnquiryDetailsShouldNotBeFound("deliveryDate.lessThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the enquiryDetailsList where deliveryDate is less than UPDATED_DELIVERY_DATE
        defaultEnquiryDetailsShouldBeFound("deliveryDate.lessThan=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where deliveryDate is greater than DEFAULT_DELIVERY_DATE
        defaultEnquiryDetailsShouldNotBeFound("deliveryDate.greaterThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the enquiryDetailsList where deliveryDate is greater than SMALLER_DELIVERY_DATE
        defaultEnquiryDetailsShouldBeFound("deliveryDate.greaterThan=" + SMALLER_DELIVERY_DATE);
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where remarks equals to DEFAULT_REMARKS
        defaultEnquiryDetailsShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the enquiryDetailsList where remarks equals to UPDATED_REMARKS
        defaultEnquiryDetailsShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where remarks not equals to DEFAULT_REMARKS
        defaultEnquiryDetailsShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the enquiryDetailsList where remarks not equals to UPDATED_REMARKS
        defaultEnquiryDetailsShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultEnquiryDetailsShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the enquiryDetailsList where remarks equals to UPDATED_REMARKS
        defaultEnquiryDetailsShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where remarks is not null
        defaultEnquiryDetailsShouldBeFound("remarks.specified=true");

        // Get all the enquiryDetailsList where remarks is null
        defaultEnquiryDetailsShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnquiryDetailsByRemarksContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where remarks contains DEFAULT_REMARKS
        defaultEnquiryDetailsShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the enquiryDetailsList where remarks contains UPDATED_REMARKS
        defaultEnquiryDetailsShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where remarks does not contain DEFAULT_REMARKS
        defaultEnquiryDetailsShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the enquiryDetailsList where remarks does not contain UPDATED_REMARKS
        defaultEnquiryDetailsShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where createdAt equals to DEFAULT_CREATED_AT
        defaultEnquiryDetailsShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the enquiryDetailsList where createdAt equals to UPDATED_CREATED_AT
        defaultEnquiryDetailsShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where createdAt not equals to DEFAULT_CREATED_AT
        defaultEnquiryDetailsShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the enquiryDetailsList where createdAt not equals to UPDATED_CREATED_AT
        defaultEnquiryDetailsShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultEnquiryDetailsShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the enquiryDetailsList where createdAt equals to UPDATED_CREATED_AT
        defaultEnquiryDetailsShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where createdAt is not null
        defaultEnquiryDetailsShouldBeFound("createdAt.specified=true");

        // Get all the enquiryDetailsList where createdAt is null
        defaultEnquiryDetailsShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultEnquiryDetailsShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the enquiryDetailsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultEnquiryDetailsShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultEnquiryDetailsShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the enquiryDetailsList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultEnquiryDetailsShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultEnquiryDetailsShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the enquiryDetailsList where updatedAt equals to UPDATED_UPDATED_AT
        defaultEnquiryDetailsShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        // Get all the enquiryDetailsList where updatedAt is not null
        defaultEnquiryDetailsShouldBeFound("updatedAt.specified=true");

        // Get all the enquiryDetailsList where updatedAt is null
        defaultEnquiryDetailsShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnquiryDetailsByEnquiryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Enquiry enquiry = enquiryDetails.getEnquiry();
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long enquiryId = enquiry.getId();

        // Get all the enquiryDetailsList where enquiry equals to enquiryId
        defaultEnquiryDetailsShouldBeFound("enquiryId.equals=" + enquiryId);

        // Get all the enquiryDetailsList where enquiry equals to enquiryId + 1
        defaultEnquiryDetailsShouldNotBeFound("enquiryId.equals=" + (enquiryId + 1));
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByPrintIsEqualToSomething() throws Exception {
        // Get already existing entity
        Category print = enquiryDetails.getPrint();
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long printId = print.getId();

        // Get all the enquiryDetailsList where print equals to printId
        defaultEnquiryDetailsShouldBeFound("printId.equals=" + printId);

        // Get all the enquiryDetailsList where print equals to printId + 1
        defaultEnquiryDetailsShouldNotBeFound("printId.equals=" + (printId + 1));
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByFinishingIsEqualToSomething() throws Exception {
        // Get already existing entity
        Category finishing = enquiryDetails.getFinishing();
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long finishingId = finishing.getId();

        // Get all the enquiryDetailsList where finishing equals to finishingId
        defaultEnquiryDetailsShouldBeFound("finishingId.equals=" + finishingId);

        // Get all the enquiryDetailsList where finishing equals to finishingId + 1
        defaultEnquiryDetailsShouldNotBeFound("finishingId.equals=" + (finishingId + 1));
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByHandlingIsEqualToSomething() throws Exception {
        // Get already existing entity
        Category handling = enquiryDetails.getHandling();
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long handlingId = handling.getId();

        // Get all the enquiryDetailsList where handling equals to handlingId
        defaultEnquiryDetailsShouldBeFound("handlingId.equals=" + handlingId);

        // Get all the enquiryDetailsList where handling equals to handlingId + 1
        defaultEnquiryDetailsShouldNotBeFound("handlingId.equals=" + (handlingId + 1));
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByPackagingIsEqualToSomething() throws Exception {
        // Get already existing entity
        Category packaging = enquiryDetails.getPackaging();
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long packagingId = packaging.getId();

        // Get all the enquiryDetailsList where packaging equals to packagingId
        defaultEnquiryDetailsShouldBeFound("packagingId.equals=" + packagingId);

        // Get all the enquiryDetailsList where packaging equals to packagingId + 1
        defaultEnquiryDetailsShouldNotBeFound("packagingId.equals=" + (packagingId + 1));
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile createdBy = enquiryDetails.getCreatedBy();
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long createdById = createdBy.getId();

        // Get all the enquiryDetailsList where createdBy equals to createdById
        defaultEnquiryDetailsShouldBeFound("createdById.equals=" + createdById);

        // Get all the enquiryDetailsList where createdBy equals to createdById + 1
        defaultEnquiryDetailsShouldNotBeFound("createdById.equals=" + (createdById + 1));
    }


    @Test
    @Transactional
    public void getAllEnquiryDetailsByOfferIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Offer offer = OfferResourceIT.createEntity(em);
        em.persist(offer);
        em.flush();
        enquiryDetails.setOffer(offer);
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);
        Long offerId = offer.getId();

        // Get all the enquiryDetailsList where offer equals to offerId
        defaultEnquiryDetailsShouldBeFound("offerId.equals=" + offerId);

        // Get all the enquiryDetailsList where offer equals to offerId + 1
        defaultEnquiryDetailsShouldNotBeFound("offerId.equals=" + (offerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnquiryDetailsShouldBeFound(String filter) throws Exception {
        restEnquiryDetailsMockMvc.perform(get("/api/enquiry-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquiryDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].edition").value(hasItem(DEFAULT_EDITION.doubleValue())))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT)))
            .andExpect(jsonPath("$.[*].documents").value(hasItem(DEFAULT_DOCUMENTS)))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restEnquiryDetailsMockMvc.perform(get("/api/enquiry-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnquiryDetailsShouldNotBeFound(String filter) throws Exception {
        restEnquiryDetailsMockMvc.perform(get("/api/enquiry-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnquiryDetailsMockMvc.perform(get("/api/enquiry-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEnquiryDetails() throws Exception {
        // Get the enquiryDetails
        restEnquiryDetailsMockMvc.perform(get("/api/enquiry-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnquiryDetails() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        int databaseSizeBeforeUpdate = enquiryDetailsRepository.findAll().size();

        // Update the enquiryDetails
        EnquiryDetails updatedEnquiryDetails = enquiryDetailsRepository.findById(enquiryDetails.getId()).get();
        // Disconnect from session so that the updates on updatedEnquiryDetails are not directly saved in db
        em.detach(updatedEnquiryDetails);
        updatedEnquiryDetails
            .version(UPDATED_VERSION)
            .edition(UPDATED_EDITION)
            .format(UPDATED_FORMAT)
            .documents(UPDATED_DOCUMENTS)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .remarks(UPDATED_REMARKS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(updatedEnquiryDetails);

        restEnquiryDetailsMockMvc.perform(put("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeUpdate);
        EnquiryDetails testEnquiryDetails = enquiryDetailsList.get(enquiryDetailsList.size() - 1);
        assertThat(testEnquiryDetails.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testEnquiryDetails.getEdition()).isEqualTo(UPDATED_EDITION);
        assertThat(testEnquiryDetails.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testEnquiryDetails.getDocuments()).isEqualTo(UPDATED_DOCUMENTS);
        assertThat(testEnquiryDetails.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testEnquiryDetails.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testEnquiryDetails.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testEnquiryDetails.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingEnquiryDetails() throws Exception {
        int databaseSizeBeforeUpdate = enquiryDetailsRepository.findAll().size();

        // Create the EnquiryDetails
        EnquiryDetailsDTO enquiryDetailsDTO = enquiryDetailsMapper.toDto(enquiryDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnquiryDetailsMockMvc.perform(put("/api/enquiry-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnquiryDetails in the database
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnquiryDetails() throws Exception {
        // Initialize the database
        enquiryDetailsRepository.saveAndFlush(enquiryDetails);

        int databaseSizeBeforeDelete = enquiryDetailsRepository.findAll().size();

        // Delete the enquiryDetails
        restEnquiryDetailsMockMvc.perform(delete("/api/enquiry-details/{id}", enquiryDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnquiryDetails> enquiryDetailsList = enquiryDetailsRepository.findAll();
        assertThat(enquiryDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
