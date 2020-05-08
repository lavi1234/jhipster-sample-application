package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.AppFeatures;
import com.mycompany.myapp.domain.Localization;
import com.mycompany.myapp.domain.SubsriptionPlanFeature;
import com.mycompany.myapp.repository.AppFeaturesRepository;
import com.mycompany.myapp.service.AppFeaturesService;
import com.mycompany.myapp.service.dto.AppFeaturesDTO;
import com.mycompany.myapp.service.mapper.AppFeaturesMapper;
import com.mycompany.myapp.service.dto.AppFeaturesCriteria;
import com.mycompany.myapp.service.AppFeaturesQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AppFeaturesResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AppFeaturesResourceIT {

    private static final Integer DEFAULT_MENU_SORT_NUMBER = 1;
    private static final Integer UPDATED_MENU_SORT_NUMBER = 2;
    private static final Integer SMALLER_MENU_SORT_NUMBER = 1 - 1;

    @Autowired
    private AppFeaturesRepository appFeaturesRepository;

    @Autowired
    private AppFeaturesMapper appFeaturesMapper;

    @Autowired
    private AppFeaturesService appFeaturesService;

    @Autowired
    private AppFeaturesQueryService appFeaturesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAppFeaturesMockMvc;

    private AppFeatures appFeatures;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppFeatures createEntity(EntityManager em) {
        AppFeatures appFeatures = new AppFeatures()
            .menuSortNumber(DEFAULT_MENU_SORT_NUMBER);
        // Add required entity
        Localization localization;
        if (TestUtil.findAll(em, Localization.class).isEmpty()) {
            localization = LocalizationResourceIT.createEntity(em);
            em.persist(localization);
            em.flush();
        } else {
            localization = TestUtil.findAll(em, Localization.class).get(0);
        }
        appFeatures.setName(localization);
        return appFeatures;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AppFeatures createUpdatedEntity(EntityManager em) {
        AppFeatures appFeatures = new AppFeatures()
            .menuSortNumber(UPDATED_MENU_SORT_NUMBER);
        // Add required entity
        Localization localization;
        if (TestUtil.findAll(em, Localization.class).isEmpty()) {
            localization = LocalizationResourceIT.createUpdatedEntity(em);
            em.persist(localization);
            em.flush();
        } else {
            localization = TestUtil.findAll(em, Localization.class).get(0);
        }
        appFeatures.setName(localization);
        return appFeatures;
    }

    @BeforeEach
    public void initTest() {
        appFeatures = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppFeatures() throws Exception {
        int databaseSizeBeforeCreate = appFeaturesRepository.findAll().size();

        // Create the AppFeatures
        AppFeaturesDTO appFeaturesDTO = appFeaturesMapper.toDto(appFeatures);
        restAppFeaturesMockMvc.perform(post("/api/app-features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appFeaturesDTO)))
            .andExpect(status().isCreated());

        // Validate the AppFeatures in the database
        List<AppFeatures> appFeaturesList = appFeaturesRepository.findAll();
        assertThat(appFeaturesList).hasSize(databaseSizeBeforeCreate + 1);
        AppFeatures testAppFeatures = appFeaturesList.get(appFeaturesList.size() - 1);
        assertThat(testAppFeatures.getMenuSortNumber()).isEqualTo(DEFAULT_MENU_SORT_NUMBER);
    }

    @Test
    @Transactional
    public void createAppFeaturesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appFeaturesRepository.findAll().size();

        // Create the AppFeatures with an existing ID
        appFeatures.setId(1L);
        AppFeaturesDTO appFeaturesDTO = appFeaturesMapper.toDto(appFeatures);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppFeaturesMockMvc.perform(post("/api/app-features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appFeaturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppFeatures in the database
        List<AppFeatures> appFeaturesList = appFeaturesRepository.findAll();
        assertThat(appFeaturesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMenuSortNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = appFeaturesRepository.findAll().size();
        // set the field null
        appFeatures.setMenuSortNumber(null);

        // Create the AppFeatures, which fails.
        AppFeaturesDTO appFeaturesDTO = appFeaturesMapper.toDto(appFeatures);

        restAppFeaturesMockMvc.perform(post("/api/app-features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appFeaturesDTO)))
            .andExpect(status().isBadRequest());

        List<AppFeatures> appFeaturesList = appFeaturesRepository.findAll();
        assertThat(appFeaturesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppFeatures() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get all the appFeaturesList
        restAppFeaturesMockMvc.perform(get("/api/app-features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appFeatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].menuSortNumber").value(hasItem(DEFAULT_MENU_SORT_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getAppFeatures() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get the appFeatures
        restAppFeaturesMockMvc.perform(get("/api/app-features/{id}", appFeatures.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(appFeatures.getId().intValue()))
            .andExpect(jsonPath("$.menuSortNumber").value(DEFAULT_MENU_SORT_NUMBER));
    }


    @Test
    @Transactional
    public void getAppFeaturesByIdFiltering() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        Long id = appFeatures.getId();

        defaultAppFeaturesShouldBeFound("id.equals=" + id);
        defaultAppFeaturesShouldNotBeFound("id.notEquals=" + id);

        defaultAppFeaturesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAppFeaturesShouldNotBeFound("id.greaterThan=" + id);

        defaultAppFeaturesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAppFeaturesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAppFeaturesByMenuSortNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get all the appFeaturesList where menuSortNumber equals to DEFAULT_MENU_SORT_NUMBER
        defaultAppFeaturesShouldBeFound("menuSortNumber.equals=" + DEFAULT_MENU_SORT_NUMBER);

        // Get all the appFeaturesList where menuSortNumber equals to UPDATED_MENU_SORT_NUMBER
        defaultAppFeaturesShouldNotBeFound("menuSortNumber.equals=" + UPDATED_MENU_SORT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAppFeaturesByMenuSortNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get all the appFeaturesList where menuSortNumber not equals to DEFAULT_MENU_SORT_NUMBER
        defaultAppFeaturesShouldNotBeFound("menuSortNumber.notEquals=" + DEFAULT_MENU_SORT_NUMBER);

        // Get all the appFeaturesList where menuSortNumber not equals to UPDATED_MENU_SORT_NUMBER
        defaultAppFeaturesShouldBeFound("menuSortNumber.notEquals=" + UPDATED_MENU_SORT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAppFeaturesByMenuSortNumberIsInShouldWork() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get all the appFeaturesList where menuSortNumber in DEFAULT_MENU_SORT_NUMBER or UPDATED_MENU_SORT_NUMBER
        defaultAppFeaturesShouldBeFound("menuSortNumber.in=" + DEFAULT_MENU_SORT_NUMBER + "," + UPDATED_MENU_SORT_NUMBER);

        // Get all the appFeaturesList where menuSortNumber equals to UPDATED_MENU_SORT_NUMBER
        defaultAppFeaturesShouldNotBeFound("menuSortNumber.in=" + UPDATED_MENU_SORT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAppFeaturesByMenuSortNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get all the appFeaturesList where menuSortNumber is not null
        defaultAppFeaturesShouldBeFound("menuSortNumber.specified=true");

        // Get all the appFeaturesList where menuSortNumber is null
        defaultAppFeaturesShouldNotBeFound("menuSortNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppFeaturesByMenuSortNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get all the appFeaturesList where menuSortNumber is greater than or equal to DEFAULT_MENU_SORT_NUMBER
        defaultAppFeaturesShouldBeFound("menuSortNumber.greaterThanOrEqual=" + DEFAULT_MENU_SORT_NUMBER);

        // Get all the appFeaturesList where menuSortNumber is greater than or equal to UPDATED_MENU_SORT_NUMBER
        defaultAppFeaturesShouldNotBeFound("menuSortNumber.greaterThanOrEqual=" + UPDATED_MENU_SORT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAppFeaturesByMenuSortNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get all the appFeaturesList where menuSortNumber is less than or equal to DEFAULT_MENU_SORT_NUMBER
        defaultAppFeaturesShouldBeFound("menuSortNumber.lessThanOrEqual=" + DEFAULT_MENU_SORT_NUMBER);

        // Get all the appFeaturesList where menuSortNumber is less than or equal to SMALLER_MENU_SORT_NUMBER
        defaultAppFeaturesShouldNotBeFound("menuSortNumber.lessThanOrEqual=" + SMALLER_MENU_SORT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAppFeaturesByMenuSortNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get all the appFeaturesList where menuSortNumber is less than DEFAULT_MENU_SORT_NUMBER
        defaultAppFeaturesShouldNotBeFound("menuSortNumber.lessThan=" + DEFAULT_MENU_SORT_NUMBER);

        // Get all the appFeaturesList where menuSortNumber is less than UPDATED_MENU_SORT_NUMBER
        defaultAppFeaturesShouldBeFound("menuSortNumber.lessThan=" + UPDATED_MENU_SORT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAppFeaturesByMenuSortNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        // Get all the appFeaturesList where menuSortNumber is greater than DEFAULT_MENU_SORT_NUMBER
        defaultAppFeaturesShouldNotBeFound("menuSortNumber.greaterThan=" + DEFAULT_MENU_SORT_NUMBER);

        // Get all the appFeaturesList where menuSortNumber is greater than SMALLER_MENU_SORT_NUMBER
        defaultAppFeaturesShouldBeFound("menuSortNumber.greaterThan=" + SMALLER_MENU_SORT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllAppFeaturesByNameIsEqualToSomething() throws Exception {
        // Get already existing entity
        Localization name = appFeatures.getName();
        appFeaturesRepository.saveAndFlush(appFeatures);
        Long nameId = name.getId();

        // Get all the appFeaturesList where name equals to nameId
        defaultAppFeaturesShouldBeFound("nameId.equals=" + nameId);

        // Get all the appFeaturesList where name equals to nameId + 1
        defaultAppFeaturesShouldNotBeFound("nameId.equals=" + (nameId + 1));
    }


    @Test
    @Transactional
    public void getAllAppFeaturesBySubsriptionPlanFeatureIsEqualToSomething() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);
        SubsriptionPlanFeature subsriptionPlanFeature = SubsriptionPlanFeatureResourceIT.createEntity(em);
        em.persist(subsriptionPlanFeature);
        em.flush();
        appFeatures.addSubsriptionPlanFeature(subsriptionPlanFeature);
        appFeaturesRepository.saveAndFlush(appFeatures);
        Long subsriptionPlanFeatureId = subsriptionPlanFeature.getId();

        // Get all the appFeaturesList where subsriptionPlanFeature equals to subsriptionPlanFeatureId
        defaultAppFeaturesShouldBeFound("subsriptionPlanFeatureId.equals=" + subsriptionPlanFeatureId);

        // Get all the appFeaturesList where subsriptionPlanFeature equals to subsriptionPlanFeatureId + 1
        defaultAppFeaturesShouldNotBeFound("subsriptionPlanFeatureId.equals=" + (subsriptionPlanFeatureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAppFeaturesShouldBeFound(String filter) throws Exception {
        restAppFeaturesMockMvc.perform(get("/api/app-features?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appFeatures.getId().intValue())))
            .andExpect(jsonPath("$.[*].menuSortNumber").value(hasItem(DEFAULT_MENU_SORT_NUMBER)));

        // Check, that the count call also returns 1
        restAppFeaturesMockMvc.perform(get("/api/app-features/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAppFeaturesShouldNotBeFound(String filter) throws Exception {
        restAppFeaturesMockMvc.perform(get("/api/app-features?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppFeaturesMockMvc.perform(get("/api/app-features/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAppFeatures() throws Exception {
        // Get the appFeatures
        restAppFeaturesMockMvc.perform(get("/api/app-features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppFeatures() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        int databaseSizeBeforeUpdate = appFeaturesRepository.findAll().size();

        // Update the appFeatures
        AppFeatures updatedAppFeatures = appFeaturesRepository.findById(appFeatures.getId()).get();
        // Disconnect from session so that the updates on updatedAppFeatures are not directly saved in db
        em.detach(updatedAppFeatures);
        updatedAppFeatures
            .menuSortNumber(UPDATED_MENU_SORT_NUMBER);
        AppFeaturesDTO appFeaturesDTO = appFeaturesMapper.toDto(updatedAppFeatures);

        restAppFeaturesMockMvc.perform(put("/api/app-features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appFeaturesDTO)))
            .andExpect(status().isOk());

        // Validate the AppFeatures in the database
        List<AppFeatures> appFeaturesList = appFeaturesRepository.findAll();
        assertThat(appFeaturesList).hasSize(databaseSizeBeforeUpdate);
        AppFeatures testAppFeatures = appFeaturesList.get(appFeaturesList.size() - 1);
        assertThat(testAppFeatures.getMenuSortNumber()).isEqualTo(UPDATED_MENU_SORT_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingAppFeatures() throws Exception {
        int databaseSizeBeforeUpdate = appFeaturesRepository.findAll().size();

        // Create the AppFeatures
        AppFeaturesDTO appFeaturesDTO = appFeaturesMapper.toDto(appFeatures);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppFeaturesMockMvc.perform(put("/api/app-features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(appFeaturesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AppFeatures in the database
        List<AppFeatures> appFeaturesList = appFeaturesRepository.findAll();
        assertThat(appFeaturesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppFeatures() throws Exception {
        // Initialize the database
        appFeaturesRepository.saveAndFlush(appFeatures);

        int databaseSizeBeforeDelete = appFeaturesRepository.findAll().size();

        // Delete the appFeatures
        restAppFeaturesMockMvc.perform(delete("/api/app-features/{id}", appFeatures.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AppFeatures> appFeaturesList = appFeaturesRepository.findAll();
        assertThat(appFeaturesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
