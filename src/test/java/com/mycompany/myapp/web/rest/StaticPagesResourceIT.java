package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.StaticPages;
import com.mycompany.myapp.repository.StaticPagesRepository;
import com.mycompany.myapp.service.StaticPagesService;
import com.mycompany.myapp.service.dto.StaticPagesDTO;
import com.mycompany.myapp.service.mapper.StaticPagesMapper;
import com.mycompany.myapp.service.dto.StaticPagesCriteria;
import com.mycompany.myapp.service.StaticPagesQueryService;

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
 * Integration tests for the {@link StaticPagesResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class StaticPagesResourceIT {

    private static final String DEFAULT_PAGE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_PAGE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_HEADING = "AAAAAAAAAA";
    private static final String UPDATED_HEADING = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_PUBLISH = false;
    private static final Boolean UPDATED_PUBLISH = true;

    @Autowired
    private StaticPagesRepository staticPagesRepository;

    @Autowired
    private StaticPagesMapper staticPagesMapper;

    @Autowired
    private StaticPagesService staticPagesService;

    @Autowired
    private StaticPagesQueryService staticPagesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStaticPagesMockMvc;

    private StaticPages staticPages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticPages createEntity(EntityManager em) {
        StaticPages staticPages = new StaticPages()
            .pageTitle(DEFAULT_PAGE_TITLE)
            .heading(DEFAULT_HEADING)
            .description(DEFAULT_DESCRIPTION)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .publish(DEFAULT_PUBLISH);
        return staticPages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StaticPages createUpdatedEntity(EntityManager em) {
        StaticPages staticPages = new StaticPages()
            .pageTitle(UPDATED_PAGE_TITLE)
            .heading(UPDATED_HEADING)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .publish(UPDATED_PUBLISH);
        return staticPages;
    }

    @BeforeEach
    public void initTest() {
        staticPages = createEntity(em);
    }

    @Test
    @Transactional
    public void createStaticPages() throws Exception {
        int databaseSizeBeforeCreate = staticPagesRepository.findAll().size();

        // Create the StaticPages
        StaticPagesDTO staticPagesDTO = staticPagesMapper.toDto(staticPages);
        restStaticPagesMockMvc.perform(post("/api/static-pages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticPagesDTO)))
            .andExpect(status().isCreated());

        // Validate the StaticPages in the database
        List<StaticPages> staticPagesList = staticPagesRepository.findAll();
        assertThat(staticPagesList).hasSize(databaseSizeBeforeCreate + 1);
        StaticPages testStaticPages = staticPagesList.get(staticPagesList.size() - 1);
        assertThat(testStaticPages.getPageTitle()).isEqualTo(DEFAULT_PAGE_TITLE);
        assertThat(testStaticPages.getHeading()).isEqualTo(DEFAULT_HEADING);
        assertThat(testStaticPages.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStaticPages.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testStaticPages.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testStaticPages.isPublish()).isEqualTo(DEFAULT_PUBLISH);
    }

    @Test
    @Transactional
    public void createStaticPagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = staticPagesRepository.findAll().size();

        // Create the StaticPages with an existing ID
        staticPages.setId(1L);
        StaticPagesDTO staticPagesDTO = staticPagesMapper.toDto(staticPages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStaticPagesMockMvc.perform(post("/api/static-pages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticPagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StaticPages in the database
        List<StaticPages> staticPagesList = staticPagesRepository.findAll();
        assertThat(staticPagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPageTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = staticPagesRepository.findAll().size();
        // set the field null
        staticPages.setPageTitle(null);

        // Create the StaticPages, which fails.
        StaticPagesDTO staticPagesDTO = staticPagesMapper.toDto(staticPages);

        restStaticPagesMockMvc.perform(post("/api/static-pages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticPagesDTO)))
            .andExpect(status().isBadRequest());

        List<StaticPages> staticPagesList = staticPagesRepository.findAll();
        assertThat(staticPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHeadingIsRequired() throws Exception {
        int databaseSizeBeforeTest = staticPagesRepository.findAll().size();
        // set the field null
        staticPages.setHeading(null);

        // Create the StaticPages, which fails.
        StaticPagesDTO staticPagesDTO = staticPagesMapper.toDto(staticPages);

        restStaticPagesMockMvc.perform(post("/api/static-pages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticPagesDTO)))
            .andExpect(status().isBadRequest());

        List<StaticPages> staticPagesList = staticPagesRepository.findAll();
        assertThat(staticPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = staticPagesRepository.findAll().size();
        // set the field null
        staticPages.setCreatedAt(null);

        // Create the StaticPages, which fails.
        StaticPagesDTO staticPagesDTO = staticPagesMapper.toDto(staticPages);

        restStaticPagesMockMvc.perform(post("/api/static-pages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticPagesDTO)))
            .andExpect(status().isBadRequest());

        List<StaticPages> staticPagesList = staticPagesRepository.findAll();
        assertThat(staticPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = staticPagesRepository.findAll().size();
        // set the field null
        staticPages.setUpdatedAt(null);

        // Create the StaticPages, which fails.
        StaticPagesDTO staticPagesDTO = staticPagesMapper.toDto(staticPages);

        restStaticPagesMockMvc.perform(post("/api/static-pages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticPagesDTO)))
            .andExpect(status().isBadRequest());

        List<StaticPages> staticPagesList = staticPagesRepository.findAll();
        assertThat(staticPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPublishIsRequired() throws Exception {
        int databaseSizeBeforeTest = staticPagesRepository.findAll().size();
        // set the field null
        staticPages.setPublish(null);

        // Create the StaticPages, which fails.
        StaticPagesDTO staticPagesDTO = staticPagesMapper.toDto(staticPages);

        restStaticPagesMockMvc.perform(post("/api/static-pages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticPagesDTO)))
            .andExpect(status().isBadRequest());

        List<StaticPages> staticPagesList = staticPagesRepository.findAll();
        assertThat(staticPagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStaticPages() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList
        restStaticPagesMockMvc.perform(get("/api/static-pages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticPages.getId().intValue())))
            .andExpect(jsonPath("$.[*].pageTitle").value(hasItem(DEFAULT_PAGE_TITLE)))
            .andExpect(jsonPath("$.[*].heading").value(hasItem(DEFAULT_HEADING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].publish").value(hasItem(DEFAULT_PUBLISH.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStaticPages() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get the staticPages
        restStaticPagesMockMvc.perform(get("/api/static-pages/{id}", staticPages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(staticPages.getId().intValue()))
            .andExpect(jsonPath("$.pageTitle").value(DEFAULT_PAGE_TITLE))
            .andExpect(jsonPath("$.heading").value(DEFAULT_HEADING))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.publish").value(DEFAULT_PUBLISH.booleanValue()));
    }


    @Test
    @Transactional
    public void getStaticPagesByIdFiltering() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        Long id = staticPages.getId();

        defaultStaticPagesShouldBeFound("id.equals=" + id);
        defaultStaticPagesShouldNotBeFound("id.notEquals=" + id);

        defaultStaticPagesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStaticPagesShouldNotBeFound("id.greaterThan=" + id);

        defaultStaticPagesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStaticPagesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStaticPagesByPageTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where pageTitle equals to DEFAULT_PAGE_TITLE
        defaultStaticPagesShouldBeFound("pageTitle.equals=" + DEFAULT_PAGE_TITLE);

        // Get all the staticPagesList where pageTitle equals to UPDATED_PAGE_TITLE
        defaultStaticPagesShouldNotBeFound("pageTitle.equals=" + UPDATED_PAGE_TITLE);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByPageTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where pageTitle not equals to DEFAULT_PAGE_TITLE
        defaultStaticPagesShouldNotBeFound("pageTitle.notEquals=" + DEFAULT_PAGE_TITLE);

        // Get all the staticPagesList where pageTitle not equals to UPDATED_PAGE_TITLE
        defaultStaticPagesShouldBeFound("pageTitle.notEquals=" + UPDATED_PAGE_TITLE);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByPageTitleIsInShouldWork() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where pageTitle in DEFAULT_PAGE_TITLE or UPDATED_PAGE_TITLE
        defaultStaticPagesShouldBeFound("pageTitle.in=" + DEFAULT_PAGE_TITLE + "," + UPDATED_PAGE_TITLE);

        // Get all the staticPagesList where pageTitle equals to UPDATED_PAGE_TITLE
        defaultStaticPagesShouldNotBeFound("pageTitle.in=" + UPDATED_PAGE_TITLE);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByPageTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where pageTitle is not null
        defaultStaticPagesShouldBeFound("pageTitle.specified=true");

        // Get all the staticPagesList where pageTitle is null
        defaultStaticPagesShouldNotBeFound("pageTitle.specified=false");
    }
                @Test
    @Transactional
    public void getAllStaticPagesByPageTitleContainsSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where pageTitle contains DEFAULT_PAGE_TITLE
        defaultStaticPagesShouldBeFound("pageTitle.contains=" + DEFAULT_PAGE_TITLE);

        // Get all the staticPagesList where pageTitle contains UPDATED_PAGE_TITLE
        defaultStaticPagesShouldNotBeFound("pageTitle.contains=" + UPDATED_PAGE_TITLE);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByPageTitleNotContainsSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where pageTitle does not contain DEFAULT_PAGE_TITLE
        defaultStaticPagesShouldNotBeFound("pageTitle.doesNotContain=" + DEFAULT_PAGE_TITLE);

        // Get all the staticPagesList where pageTitle does not contain UPDATED_PAGE_TITLE
        defaultStaticPagesShouldBeFound("pageTitle.doesNotContain=" + UPDATED_PAGE_TITLE);
    }


    @Test
    @Transactional
    public void getAllStaticPagesByHeadingIsEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where heading equals to DEFAULT_HEADING
        defaultStaticPagesShouldBeFound("heading.equals=" + DEFAULT_HEADING);

        // Get all the staticPagesList where heading equals to UPDATED_HEADING
        defaultStaticPagesShouldNotBeFound("heading.equals=" + UPDATED_HEADING);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByHeadingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where heading not equals to DEFAULT_HEADING
        defaultStaticPagesShouldNotBeFound("heading.notEquals=" + DEFAULT_HEADING);

        // Get all the staticPagesList where heading not equals to UPDATED_HEADING
        defaultStaticPagesShouldBeFound("heading.notEquals=" + UPDATED_HEADING);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByHeadingIsInShouldWork() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where heading in DEFAULT_HEADING or UPDATED_HEADING
        defaultStaticPagesShouldBeFound("heading.in=" + DEFAULT_HEADING + "," + UPDATED_HEADING);

        // Get all the staticPagesList where heading equals to UPDATED_HEADING
        defaultStaticPagesShouldNotBeFound("heading.in=" + UPDATED_HEADING);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByHeadingIsNullOrNotNull() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where heading is not null
        defaultStaticPagesShouldBeFound("heading.specified=true");

        // Get all the staticPagesList where heading is null
        defaultStaticPagesShouldNotBeFound("heading.specified=false");
    }
                @Test
    @Transactional
    public void getAllStaticPagesByHeadingContainsSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where heading contains DEFAULT_HEADING
        defaultStaticPagesShouldBeFound("heading.contains=" + DEFAULT_HEADING);

        // Get all the staticPagesList where heading contains UPDATED_HEADING
        defaultStaticPagesShouldNotBeFound("heading.contains=" + UPDATED_HEADING);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByHeadingNotContainsSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where heading does not contain DEFAULT_HEADING
        defaultStaticPagesShouldNotBeFound("heading.doesNotContain=" + DEFAULT_HEADING);

        // Get all the staticPagesList where heading does not contain UPDATED_HEADING
        defaultStaticPagesShouldBeFound("heading.doesNotContain=" + UPDATED_HEADING);
    }


    @Test
    @Transactional
    public void getAllStaticPagesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where description equals to DEFAULT_DESCRIPTION
        defaultStaticPagesShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the staticPagesList where description equals to UPDATED_DESCRIPTION
        defaultStaticPagesShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where description not equals to DEFAULT_DESCRIPTION
        defaultStaticPagesShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the staticPagesList where description not equals to UPDATED_DESCRIPTION
        defaultStaticPagesShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultStaticPagesShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the staticPagesList where description equals to UPDATED_DESCRIPTION
        defaultStaticPagesShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where description is not null
        defaultStaticPagesShouldBeFound("description.specified=true");

        // Get all the staticPagesList where description is null
        defaultStaticPagesShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllStaticPagesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where description contains DEFAULT_DESCRIPTION
        defaultStaticPagesShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the staticPagesList where description contains UPDATED_DESCRIPTION
        defaultStaticPagesShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where description does not contain DEFAULT_DESCRIPTION
        defaultStaticPagesShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the staticPagesList where description does not contain UPDATED_DESCRIPTION
        defaultStaticPagesShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllStaticPagesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where createdAt equals to DEFAULT_CREATED_AT
        defaultStaticPagesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the staticPagesList where createdAt equals to UPDATED_CREATED_AT
        defaultStaticPagesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where createdAt not equals to DEFAULT_CREATED_AT
        defaultStaticPagesShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the staticPagesList where createdAt not equals to UPDATED_CREATED_AT
        defaultStaticPagesShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultStaticPagesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the staticPagesList where createdAt equals to UPDATED_CREATED_AT
        defaultStaticPagesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where createdAt is not null
        defaultStaticPagesShouldBeFound("createdAt.specified=true");

        // Get all the staticPagesList where createdAt is null
        defaultStaticPagesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllStaticPagesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultStaticPagesShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the staticPagesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultStaticPagesShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultStaticPagesShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the staticPagesList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultStaticPagesShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultStaticPagesShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the staticPagesList where updatedAt equals to UPDATED_UPDATED_AT
        defaultStaticPagesShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where updatedAt is not null
        defaultStaticPagesShouldBeFound("updatedAt.specified=true");

        // Get all the staticPagesList where updatedAt is null
        defaultStaticPagesShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllStaticPagesByPublishIsEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where publish equals to DEFAULT_PUBLISH
        defaultStaticPagesShouldBeFound("publish.equals=" + DEFAULT_PUBLISH);

        // Get all the staticPagesList where publish equals to UPDATED_PUBLISH
        defaultStaticPagesShouldNotBeFound("publish.equals=" + UPDATED_PUBLISH);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByPublishIsNotEqualToSomething() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where publish not equals to DEFAULT_PUBLISH
        defaultStaticPagesShouldNotBeFound("publish.notEquals=" + DEFAULT_PUBLISH);

        // Get all the staticPagesList where publish not equals to UPDATED_PUBLISH
        defaultStaticPagesShouldBeFound("publish.notEquals=" + UPDATED_PUBLISH);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByPublishIsInShouldWork() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where publish in DEFAULT_PUBLISH or UPDATED_PUBLISH
        defaultStaticPagesShouldBeFound("publish.in=" + DEFAULT_PUBLISH + "," + UPDATED_PUBLISH);

        // Get all the staticPagesList where publish equals to UPDATED_PUBLISH
        defaultStaticPagesShouldNotBeFound("publish.in=" + UPDATED_PUBLISH);
    }

    @Test
    @Transactional
    public void getAllStaticPagesByPublishIsNullOrNotNull() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        // Get all the staticPagesList where publish is not null
        defaultStaticPagesShouldBeFound("publish.specified=true");

        // Get all the staticPagesList where publish is null
        defaultStaticPagesShouldNotBeFound("publish.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStaticPagesShouldBeFound(String filter) throws Exception {
        restStaticPagesMockMvc.perform(get("/api/static-pages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(staticPages.getId().intValue())))
            .andExpect(jsonPath("$.[*].pageTitle").value(hasItem(DEFAULT_PAGE_TITLE)))
            .andExpect(jsonPath("$.[*].heading").value(hasItem(DEFAULT_HEADING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].publish").value(hasItem(DEFAULT_PUBLISH.booleanValue())));

        // Check, that the count call also returns 1
        restStaticPagesMockMvc.perform(get("/api/static-pages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStaticPagesShouldNotBeFound(String filter) throws Exception {
        restStaticPagesMockMvc.perform(get("/api/static-pages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStaticPagesMockMvc.perform(get("/api/static-pages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStaticPages() throws Exception {
        // Get the staticPages
        restStaticPagesMockMvc.perform(get("/api/static-pages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStaticPages() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        int databaseSizeBeforeUpdate = staticPagesRepository.findAll().size();

        // Update the staticPages
        StaticPages updatedStaticPages = staticPagesRepository.findById(staticPages.getId()).get();
        // Disconnect from session so that the updates on updatedStaticPages are not directly saved in db
        em.detach(updatedStaticPages);
        updatedStaticPages
            .pageTitle(UPDATED_PAGE_TITLE)
            .heading(UPDATED_HEADING)
            .description(UPDATED_DESCRIPTION)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .publish(UPDATED_PUBLISH);
        StaticPagesDTO staticPagesDTO = staticPagesMapper.toDto(updatedStaticPages);

        restStaticPagesMockMvc.perform(put("/api/static-pages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticPagesDTO)))
            .andExpect(status().isOk());

        // Validate the StaticPages in the database
        List<StaticPages> staticPagesList = staticPagesRepository.findAll();
        assertThat(staticPagesList).hasSize(databaseSizeBeforeUpdate);
        StaticPages testStaticPages = staticPagesList.get(staticPagesList.size() - 1);
        assertThat(testStaticPages.getPageTitle()).isEqualTo(UPDATED_PAGE_TITLE);
        assertThat(testStaticPages.getHeading()).isEqualTo(UPDATED_HEADING);
        assertThat(testStaticPages.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStaticPages.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testStaticPages.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testStaticPages.isPublish()).isEqualTo(UPDATED_PUBLISH);
    }

    @Test
    @Transactional
    public void updateNonExistingStaticPages() throws Exception {
        int databaseSizeBeforeUpdate = staticPagesRepository.findAll().size();

        // Create the StaticPages
        StaticPagesDTO staticPagesDTO = staticPagesMapper.toDto(staticPages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStaticPagesMockMvc.perform(put("/api/static-pages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(staticPagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the StaticPages in the database
        List<StaticPages> staticPagesList = staticPagesRepository.findAll();
        assertThat(staticPagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStaticPages() throws Exception {
        // Initialize the database
        staticPagesRepository.saveAndFlush(staticPages);

        int databaseSizeBeforeDelete = staticPagesRepository.findAll().size();

        // Delete the staticPages
        restStaticPagesMockMvc.perform(delete("/api/static-pages/{id}", staticPages.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StaticPages> staticPagesList = staticPagesRepository.findAll();
        assertThat(staticPagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
