package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.CompanyRepository;
import com.mycompany.myapp.service.CompanyService;
import com.mycompany.myapp.service.dto.CompanyDTO;
import com.mycompany.myapp.service.mapper.CompanyMapper;
import com.mycompany.myapp.service.dto.CompanyCriteria;
import com.mycompany.myapp.service.CompanyQueryService;

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
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CompanyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TERMS_CONDITIONS = "AAAAAAAAAA";
    private static final String UPDATED_TERMS_CONDITIONS = "BBBBBBBBBB";

    private static final String DEFAULT_ABOUT_US = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT_US = "BBBBBBBBBB";

    private static final String DEFAULT_CATALOGUE = "AAAAAAAAAA";
    private static final String UPDATED_CATALOGUE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyQueryService companyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .termsConditions(DEFAULT_TERMS_CONDITIONS)
            .aboutUs(DEFAULT_ABOUT_US)
            .catalogue(DEFAULT_CATALOGUE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        company.setCreatedBy(userProfile);
        // Add required entity
        company.setUpdatedBy(userProfile);
        return company;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .termsConditions(UPDATED_TERMS_CONDITIONS)
            .aboutUs(UPDATED_ABOUT_US)
            .catalogue(UPDATED_CATALOGUE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        company.setCreatedBy(userProfile);
        // Add required entity
        company.setUpdatedBy(userProfile);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompany.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCompany.getTermsConditions()).isEqualTo(DEFAULT_TERMS_CONDITIONS);
        assertThat(testCompany.getAboutUs()).isEqualTo(DEFAULT_ABOUT_US);
        assertThat(testCompany.getCatalogue()).isEqualTo(DEFAULT_CATALOGUE);
        assertThat(testCompany.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testCompany.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setName(null);

        // Create the Company, which fails.
        CompanyDTO companyDTO = companyMapper.toDto(company);

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setCreatedAt(null);

        // Create the Company, which fails.
        CompanyDTO companyDTO = companyMapper.toDto(company);

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyRepository.findAll().size();
        // set the field null
        company.setUpdatedAt(null);

        // Create the Company, which fails.
        CompanyDTO companyDTO = companyMapper.toDto(company);

        restCompanyMockMvc.perform(post("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].termsConditions").value(hasItem(DEFAULT_TERMS_CONDITIONS)))
            .andExpect(jsonPath("$.[*].aboutUs").value(hasItem(DEFAULT_ABOUT_US)))
            .andExpect(jsonPath("$.[*].catalogue").value(hasItem(DEFAULT_CATALOGUE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.termsConditions").value(DEFAULT_TERMS_CONDITIONS))
            .andExpect(jsonPath("$.aboutUs").value(DEFAULT_ABOUT_US))
            .andExpect(jsonPath("$.catalogue").value(DEFAULT_CATALOGUE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        Long id = company.getId();

        defaultCompanyShouldBeFound("id.equals=" + id);
        defaultCompanyShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCompaniesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name equals to DEFAULT_NAME
        defaultCompanyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name not equals to DEFAULT_NAME
        defaultCompanyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the companyList where name not equals to UPDATED_NAME
        defaultCompanyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompanyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name is not null
        defaultCompanyShouldBeFound("name.specified=true");

        // Get all the companyList where name is null
        defaultCompanyShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByNameContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name contains DEFAULT_NAME
        defaultCompanyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the companyList where name contains UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name does not contain DEFAULT_NAME
        defaultCompanyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the companyList where name does not contain UPDATED_NAME
        defaultCompanyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCompaniesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email equals to DEFAULT_EMAIL
        defaultCompanyShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email not equals to DEFAULT_EMAIL
        defaultCompanyShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the companyList where email not equals to UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the companyList where email equals to UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email is not null
        defaultCompanyShouldBeFound("email.specified=true");

        // Get all the companyList where email is null
        defaultCompanyShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByEmailContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email contains DEFAULT_EMAIL
        defaultCompanyShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the companyList where email contains UPDATED_EMAIL
        defaultCompanyShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllCompaniesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where email does not contain DEFAULT_EMAIL
        defaultCompanyShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the companyList where email does not contain UPDATED_EMAIL
        defaultCompanyShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllCompaniesByTermsConditionsIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where termsConditions equals to DEFAULT_TERMS_CONDITIONS
        defaultCompanyShouldBeFound("termsConditions.equals=" + DEFAULT_TERMS_CONDITIONS);

        // Get all the companyList where termsConditions equals to UPDATED_TERMS_CONDITIONS
        defaultCompanyShouldNotBeFound("termsConditions.equals=" + UPDATED_TERMS_CONDITIONS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTermsConditionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where termsConditions not equals to DEFAULT_TERMS_CONDITIONS
        defaultCompanyShouldNotBeFound("termsConditions.notEquals=" + DEFAULT_TERMS_CONDITIONS);

        // Get all the companyList where termsConditions not equals to UPDATED_TERMS_CONDITIONS
        defaultCompanyShouldBeFound("termsConditions.notEquals=" + UPDATED_TERMS_CONDITIONS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTermsConditionsIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where termsConditions in DEFAULT_TERMS_CONDITIONS or UPDATED_TERMS_CONDITIONS
        defaultCompanyShouldBeFound("termsConditions.in=" + DEFAULT_TERMS_CONDITIONS + "," + UPDATED_TERMS_CONDITIONS);

        // Get all the companyList where termsConditions equals to UPDATED_TERMS_CONDITIONS
        defaultCompanyShouldNotBeFound("termsConditions.in=" + UPDATED_TERMS_CONDITIONS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTermsConditionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where termsConditions is not null
        defaultCompanyShouldBeFound("termsConditions.specified=true");

        // Get all the companyList where termsConditions is null
        defaultCompanyShouldNotBeFound("termsConditions.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByTermsConditionsContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where termsConditions contains DEFAULT_TERMS_CONDITIONS
        defaultCompanyShouldBeFound("termsConditions.contains=" + DEFAULT_TERMS_CONDITIONS);

        // Get all the companyList where termsConditions contains UPDATED_TERMS_CONDITIONS
        defaultCompanyShouldNotBeFound("termsConditions.contains=" + UPDATED_TERMS_CONDITIONS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTermsConditionsNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where termsConditions does not contain DEFAULT_TERMS_CONDITIONS
        defaultCompanyShouldNotBeFound("termsConditions.doesNotContain=" + DEFAULT_TERMS_CONDITIONS);

        // Get all the companyList where termsConditions does not contain UPDATED_TERMS_CONDITIONS
        defaultCompanyShouldBeFound("termsConditions.doesNotContain=" + UPDATED_TERMS_CONDITIONS);
    }


    @Test
    @Transactional
    public void getAllCompaniesByAboutUsIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where aboutUs equals to DEFAULT_ABOUT_US
        defaultCompanyShouldBeFound("aboutUs.equals=" + DEFAULT_ABOUT_US);

        // Get all the companyList where aboutUs equals to UPDATED_ABOUT_US
        defaultCompanyShouldNotBeFound("aboutUs.equals=" + UPDATED_ABOUT_US);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAboutUsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where aboutUs not equals to DEFAULT_ABOUT_US
        defaultCompanyShouldNotBeFound("aboutUs.notEquals=" + DEFAULT_ABOUT_US);

        // Get all the companyList where aboutUs not equals to UPDATED_ABOUT_US
        defaultCompanyShouldBeFound("aboutUs.notEquals=" + UPDATED_ABOUT_US);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAboutUsIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where aboutUs in DEFAULT_ABOUT_US or UPDATED_ABOUT_US
        defaultCompanyShouldBeFound("aboutUs.in=" + DEFAULT_ABOUT_US + "," + UPDATED_ABOUT_US);

        // Get all the companyList where aboutUs equals to UPDATED_ABOUT_US
        defaultCompanyShouldNotBeFound("aboutUs.in=" + UPDATED_ABOUT_US);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAboutUsIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where aboutUs is not null
        defaultCompanyShouldBeFound("aboutUs.specified=true");

        // Get all the companyList where aboutUs is null
        defaultCompanyShouldNotBeFound("aboutUs.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByAboutUsContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where aboutUs contains DEFAULT_ABOUT_US
        defaultCompanyShouldBeFound("aboutUs.contains=" + DEFAULT_ABOUT_US);

        // Get all the companyList where aboutUs contains UPDATED_ABOUT_US
        defaultCompanyShouldNotBeFound("aboutUs.contains=" + UPDATED_ABOUT_US);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAboutUsNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where aboutUs does not contain DEFAULT_ABOUT_US
        defaultCompanyShouldNotBeFound("aboutUs.doesNotContain=" + DEFAULT_ABOUT_US);

        // Get all the companyList where aboutUs does not contain UPDATED_ABOUT_US
        defaultCompanyShouldBeFound("aboutUs.doesNotContain=" + UPDATED_ABOUT_US);
    }


    @Test
    @Transactional
    public void getAllCompaniesByCatalogueIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where catalogue equals to DEFAULT_CATALOGUE
        defaultCompanyShouldBeFound("catalogue.equals=" + DEFAULT_CATALOGUE);

        // Get all the companyList where catalogue equals to UPDATED_CATALOGUE
        defaultCompanyShouldNotBeFound("catalogue.equals=" + UPDATED_CATALOGUE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCatalogueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where catalogue not equals to DEFAULT_CATALOGUE
        defaultCompanyShouldNotBeFound("catalogue.notEquals=" + DEFAULT_CATALOGUE);

        // Get all the companyList where catalogue not equals to UPDATED_CATALOGUE
        defaultCompanyShouldBeFound("catalogue.notEquals=" + UPDATED_CATALOGUE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCatalogueIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where catalogue in DEFAULT_CATALOGUE or UPDATED_CATALOGUE
        defaultCompanyShouldBeFound("catalogue.in=" + DEFAULT_CATALOGUE + "," + UPDATED_CATALOGUE);

        // Get all the companyList where catalogue equals to UPDATED_CATALOGUE
        defaultCompanyShouldNotBeFound("catalogue.in=" + UPDATED_CATALOGUE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCatalogueIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where catalogue is not null
        defaultCompanyShouldBeFound("catalogue.specified=true");

        // Get all the companyList where catalogue is null
        defaultCompanyShouldNotBeFound("catalogue.specified=false");
    }
                @Test
    @Transactional
    public void getAllCompaniesByCatalogueContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where catalogue contains DEFAULT_CATALOGUE
        defaultCompanyShouldBeFound("catalogue.contains=" + DEFAULT_CATALOGUE);

        // Get all the companyList where catalogue contains UPDATED_CATALOGUE
        defaultCompanyShouldNotBeFound("catalogue.contains=" + UPDATED_CATALOGUE);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCatalogueNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where catalogue does not contain DEFAULT_CATALOGUE
        defaultCompanyShouldNotBeFound("catalogue.doesNotContain=" + DEFAULT_CATALOGUE);

        // Get all the companyList where catalogue does not contain UPDATED_CATALOGUE
        defaultCompanyShouldBeFound("catalogue.doesNotContain=" + UPDATED_CATALOGUE);
    }


    @Test
    @Transactional
    public void getAllCompaniesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where createdAt equals to DEFAULT_CREATED_AT
        defaultCompanyShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the companyList where createdAt equals to UPDATED_CREATED_AT
        defaultCompanyShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where createdAt not equals to DEFAULT_CREATED_AT
        defaultCompanyShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the companyList where createdAt not equals to UPDATED_CREATED_AT
        defaultCompanyShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultCompanyShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the companyList where createdAt equals to UPDATED_CREATED_AT
        defaultCompanyShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where createdAt is not null
        defaultCompanyShouldBeFound("createdAt.specified=true");

        // Get all the companyList where createdAt is null
        defaultCompanyShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultCompanyShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the companyList where updatedAt equals to UPDATED_UPDATED_AT
        defaultCompanyShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultCompanyShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the companyList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultCompanyShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultCompanyShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the companyList where updatedAt equals to UPDATED_UPDATED_AT
        defaultCompanyShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where updatedAt is not null
        defaultCompanyShouldBeFound("updatedAt.specified=true");

        // Get all the companyList where updatedAt is null
        defaultCompanyShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);
        Address address = AddressResourceIT.createEntity(em);
        em.persist(address);
        em.flush();
        company.setAddress(address);
        companyRepository.saveAndFlush(company);
        Long addressId = address.getId();

        // Get all the companyList where address equals to addressId
        defaultCompanyShouldBeFound("addressId.equals=" + addressId);

        // Get all the companyList where address equals to addressId + 1
        defaultCompanyShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }


    @Test
    @Transactional
    public void getAllCompaniesByCreatedByIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile createdBy = company.getCreatedBy();
        companyRepository.saveAndFlush(company);
        Long createdById = createdBy.getId();

        // Get all the companyList where createdBy equals to createdById
        defaultCompanyShouldBeFound("createdById.equals=" + createdById);

        // Get all the companyList where createdBy equals to createdById + 1
        defaultCompanyShouldNotBeFound("createdById.equals=" + (createdById + 1));
    }


    @Test
    @Transactional
    public void getAllCompaniesByUpdatedByIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile updatedBy = company.getUpdatedBy();
        companyRepository.saveAndFlush(company);
        Long updatedById = updatedBy.getId();

        // Get all the companyList where updatedBy equals to updatedById
        defaultCompanyShouldBeFound("updatedById.equals=" + updatedById);

        // Get all the companyList where updatedBy equals to updatedById + 1
        defaultCompanyShouldNotBeFound("updatedById.equals=" + (updatedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyShouldBeFound(String filter) throws Exception {
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].termsConditions").value(hasItem(DEFAULT_TERMS_CONDITIONS)))
            .andExpect(jsonPath("$.[*].aboutUs").value(hasItem(DEFAULT_ABOUT_US)))
            .andExpect(jsonPath("$.[*].catalogue").value(hasItem(DEFAULT_CATALOGUE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restCompanyMockMvc.perform(get("/api/companies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyShouldNotBeFound(String filter) throws Exception {
        restCompanyMockMvc.perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyMockMvc.perform(get("/api/companies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .termsConditions(UPDATED_TERMS_CONDITIONS)
            .aboutUs(UPDATED_ABOUT_US)
            .catalogue(UPDATED_CATALOGUE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        CompanyDTO companyDTO = companyMapper.toDto(updatedCompany);

        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCompany.getTermsConditions()).isEqualTo(UPDATED_TERMS_CONDITIONS);
        assertThat(testCompany.getAboutUs()).isEqualTo(UPDATED_ABOUT_US);
        assertThat(testCompany.getCatalogue()).isEqualTo(UPDATED_CATALOGUE);
        assertThat(testCompany.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testCompany.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc.perform(put("/api/companies")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc.perform(delete("/api/companies/{id}", company.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
