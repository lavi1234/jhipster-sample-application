package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Localization;
import com.mycompany.myapp.repository.LocalizationRepository;
import com.mycompany.myapp.service.LocalizationService;
import com.mycompany.myapp.service.dto.LocalizationDTO;
import com.mycompany.myapp.service.mapper.LocalizationMapper;
import com.mycompany.myapp.service.dto.LocalizationCriteria;
import com.mycompany.myapp.service.LocalizationQueryService;

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
 * Integration tests for the {@link LocalizationResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class LocalizationResourceIT {

    private static final String DEFAULT_LABEL_EN = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_EN = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL_DE = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_DE = "BBBBBBBBBB";

    @Autowired
    private LocalizationRepository localizationRepository;

    @Autowired
    private LocalizationMapper localizationMapper;

    @Autowired
    private LocalizationService localizationService;

    @Autowired
    private LocalizationQueryService localizationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLocalizationMockMvc;

    private Localization localization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localization createEntity(EntityManager em) {
        Localization localization = new Localization()
            .labelEn(DEFAULT_LABEL_EN)
            .labelDe(DEFAULT_LABEL_DE);
        return localization;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Localization createUpdatedEntity(EntityManager em) {
        Localization localization = new Localization()
            .labelEn(UPDATED_LABEL_EN)
            .labelDe(UPDATED_LABEL_DE);
        return localization;
    }

    @BeforeEach
    public void initTest() {
        localization = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocalization() throws Exception {
        int databaseSizeBeforeCreate = localizationRepository.findAll().size();

        // Create the Localization
        LocalizationDTO localizationDTO = localizationMapper.toDto(localization);
        restLocalizationMockMvc.perform(post("/api/localizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localizationDTO)))
            .andExpect(status().isCreated());

        // Validate the Localization in the database
        List<Localization> localizationList = localizationRepository.findAll();
        assertThat(localizationList).hasSize(databaseSizeBeforeCreate + 1);
        Localization testLocalization = localizationList.get(localizationList.size() - 1);
        assertThat(testLocalization.getLabelEn()).isEqualTo(DEFAULT_LABEL_EN);
        assertThat(testLocalization.getLabelDe()).isEqualTo(DEFAULT_LABEL_DE);
    }

    @Test
    @Transactional
    public void createLocalizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localizationRepository.findAll().size();

        // Create the Localization with an existing ID
        localization.setId(1L);
        LocalizationDTO localizationDTO = localizationMapper.toDto(localization);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalizationMockMvc.perform(post("/api/localizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Localization in the database
        List<Localization> localizationList = localizationRepository.findAll();
        assertThat(localizationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLabelEnIsRequired() throws Exception {
        int databaseSizeBeforeTest = localizationRepository.findAll().size();
        // set the field null
        localization.setLabelEn(null);

        // Create the Localization, which fails.
        LocalizationDTO localizationDTO = localizationMapper.toDto(localization);

        restLocalizationMockMvc.perform(post("/api/localizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localizationDTO)))
            .andExpect(status().isBadRequest());

        List<Localization> localizationList = localizationRepository.findAll();
        assertThat(localizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLabelDeIsRequired() throws Exception {
        int databaseSizeBeforeTest = localizationRepository.findAll().size();
        // set the field null
        localization.setLabelDe(null);

        // Create the Localization, which fails.
        LocalizationDTO localizationDTO = localizationMapper.toDto(localization);

        restLocalizationMockMvc.perform(post("/api/localizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localizationDTO)))
            .andExpect(status().isBadRequest());

        List<Localization> localizationList = localizationRepository.findAll();
        assertThat(localizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocalizations() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList
        restLocalizationMockMvc.perform(get("/api/localizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localization.getId().intValue())))
            .andExpect(jsonPath("$.[*].labelEn").value(hasItem(DEFAULT_LABEL_EN)))
            .andExpect(jsonPath("$.[*].labelDe").value(hasItem(DEFAULT_LABEL_DE)));
    }
    
    @Test
    @Transactional
    public void getLocalization() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get the localization
        restLocalizationMockMvc.perform(get("/api/localizations/{id}", localization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(localization.getId().intValue()))
            .andExpect(jsonPath("$.labelEn").value(DEFAULT_LABEL_EN))
            .andExpect(jsonPath("$.labelDe").value(DEFAULT_LABEL_DE));
    }


    @Test
    @Transactional
    public void getLocalizationsByIdFiltering() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        Long id = localization.getId();

        defaultLocalizationShouldBeFound("id.equals=" + id);
        defaultLocalizationShouldNotBeFound("id.notEquals=" + id);

        defaultLocalizationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLocalizationShouldNotBeFound("id.greaterThan=" + id);

        defaultLocalizationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLocalizationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLocalizationsByLabelEnIsEqualToSomething() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelEn equals to DEFAULT_LABEL_EN
        defaultLocalizationShouldBeFound("labelEn.equals=" + DEFAULT_LABEL_EN);

        // Get all the localizationList where labelEn equals to UPDATED_LABEL_EN
        defaultLocalizationShouldNotBeFound("labelEn.equals=" + UPDATED_LABEL_EN);
    }

    @Test
    @Transactional
    public void getAllLocalizationsByLabelEnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelEn not equals to DEFAULT_LABEL_EN
        defaultLocalizationShouldNotBeFound("labelEn.notEquals=" + DEFAULT_LABEL_EN);

        // Get all the localizationList where labelEn not equals to UPDATED_LABEL_EN
        defaultLocalizationShouldBeFound("labelEn.notEquals=" + UPDATED_LABEL_EN);
    }

    @Test
    @Transactional
    public void getAllLocalizationsByLabelEnIsInShouldWork() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelEn in DEFAULT_LABEL_EN or UPDATED_LABEL_EN
        defaultLocalizationShouldBeFound("labelEn.in=" + DEFAULT_LABEL_EN + "," + UPDATED_LABEL_EN);

        // Get all the localizationList where labelEn equals to UPDATED_LABEL_EN
        defaultLocalizationShouldNotBeFound("labelEn.in=" + UPDATED_LABEL_EN);
    }

    @Test
    @Transactional
    public void getAllLocalizationsByLabelEnIsNullOrNotNull() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelEn is not null
        defaultLocalizationShouldBeFound("labelEn.specified=true");

        // Get all the localizationList where labelEn is null
        defaultLocalizationShouldNotBeFound("labelEn.specified=false");
    }
                @Test
    @Transactional
    public void getAllLocalizationsByLabelEnContainsSomething() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelEn contains DEFAULT_LABEL_EN
        defaultLocalizationShouldBeFound("labelEn.contains=" + DEFAULT_LABEL_EN);

        // Get all the localizationList where labelEn contains UPDATED_LABEL_EN
        defaultLocalizationShouldNotBeFound("labelEn.contains=" + UPDATED_LABEL_EN);
    }

    @Test
    @Transactional
    public void getAllLocalizationsByLabelEnNotContainsSomething() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelEn does not contain DEFAULT_LABEL_EN
        defaultLocalizationShouldNotBeFound("labelEn.doesNotContain=" + DEFAULT_LABEL_EN);

        // Get all the localizationList where labelEn does not contain UPDATED_LABEL_EN
        defaultLocalizationShouldBeFound("labelEn.doesNotContain=" + UPDATED_LABEL_EN);
    }


    @Test
    @Transactional
    public void getAllLocalizationsByLabelDeIsEqualToSomething() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelDe equals to DEFAULT_LABEL_DE
        defaultLocalizationShouldBeFound("labelDe.equals=" + DEFAULT_LABEL_DE);

        // Get all the localizationList where labelDe equals to UPDATED_LABEL_DE
        defaultLocalizationShouldNotBeFound("labelDe.equals=" + UPDATED_LABEL_DE);
    }

    @Test
    @Transactional
    public void getAllLocalizationsByLabelDeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelDe not equals to DEFAULT_LABEL_DE
        defaultLocalizationShouldNotBeFound("labelDe.notEquals=" + DEFAULT_LABEL_DE);

        // Get all the localizationList where labelDe not equals to UPDATED_LABEL_DE
        defaultLocalizationShouldBeFound("labelDe.notEquals=" + UPDATED_LABEL_DE);
    }

    @Test
    @Transactional
    public void getAllLocalizationsByLabelDeIsInShouldWork() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelDe in DEFAULT_LABEL_DE or UPDATED_LABEL_DE
        defaultLocalizationShouldBeFound("labelDe.in=" + DEFAULT_LABEL_DE + "," + UPDATED_LABEL_DE);

        // Get all the localizationList where labelDe equals to UPDATED_LABEL_DE
        defaultLocalizationShouldNotBeFound("labelDe.in=" + UPDATED_LABEL_DE);
    }

    @Test
    @Transactional
    public void getAllLocalizationsByLabelDeIsNullOrNotNull() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelDe is not null
        defaultLocalizationShouldBeFound("labelDe.specified=true");

        // Get all the localizationList where labelDe is null
        defaultLocalizationShouldNotBeFound("labelDe.specified=false");
    }
                @Test
    @Transactional
    public void getAllLocalizationsByLabelDeContainsSomething() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelDe contains DEFAULT_LABEL_DE
        defaultLocalizationShouldBeFound("labelDe.contains=" + DEFAULT_LABEL_DE);

        // Get all the localizationList where labelDe contains UPDATED_LABEL_DE
        defaultLocalizationShouldNotBeFound("labelDe.contains=" + UPDATED_LABEL_DE);
    }

    @Test
    @Transactional
    public void getAllLocalizationsByLabelDeNotContainsSomething() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        // Get all the localizationList where labelDe does not contain DEFAULT_LABEL_DE
        defaultLocalizationShouldNotBeFound("labelDe.doesNotContain=" + DEFAULT_LABEL_DE);

        // Get all the localizationList where labelDe does not contain UPDATED_LABEL_DE
        defaultLocalizationShouldBeFound("labelDe.doesNotContain=" + UPDATED_LABEL_DE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLocalizationShouldBeFound(String filter) throws Exception {
        restLocalizationMockMvc.perform(get("/api/localizations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(localization.getId().intValue())))
            .andExpect(jsonPath("$.[*].labelEn").value(hasItem(DEFAULT_LABEL_EN)))
            .andExpect(jsonPath("$.[*].labelDe").value(hasItem(DEFAULT_LABEL_DE)));

        // Check, that the count call also returns 1
        restLocalizationMockMvc.perform(get("/api/localizations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLocalizationShouldNotBeFound(String filter) throws Exception {
        restLocalizationMockMvc.perform(get("/api/localizations?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLocalizationMockMvc.perform(get("/api/localizations/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLocalization() throws Exception {
        // Get the localization
        restLocalizationMockMvc.perform(get("/api/localizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocalization() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        int databaseSizeBeforeUpdate = localizationRepository.findAll().size();

        // Update the localization
        Localization updatedLocalization = localizationRepository.findById(localization.getId()).get();
        // Disconnect from session so that the updates on updatedLocalization are not directly saved in db
        em.detach(updatedLocalization);
        updatedLocalization
            .labelEn(UPDATED_LABEL_EN)
            .labelDe(UPDATED_LABEL_DE);
        LocalizationDTO localizationDTO = localizationMapper.toDto(updatedLocalization);

        restLocalizationMockMvc.perform(put("/api/localizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localizationDTO)))
            .andExpect(status().isOk());

        // Validate the Localization in the database
        List<Localization> localizationList = localizationRepository.findAll();
        assertThat(localizationList).hasSize(databaseSizeBeforeUpdate);
        Localization testLocalization = localizationList.get(localizationList.size() - 1);
        assertThat(testLocalization.getLabelEn()).isEqualTo(UPDATED_LABEL_EN);
        assertThat(testLocalization.getLabelDe()).isEqualTo(UPDATED_LABEL_DE);
    }

    @Test
    @Transactional
    public void updateNonExistingLocalization() throws Exception {
        int databaseSizeBeforeUpdate = localizationRepository.findAll().size();

        // Create the Localization
        LocalizationDTO localizationDTO = localizationMapper.toDto(localization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalizationMockMvc.perform(put("/api/localizations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(localizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Localization in the database
        List<Localization> localizationList = localizationRepository.findAll();
        assertThat(localizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocalization() throws Exception {
        // Initialize the database
        localizationRepository.saveAndFlush(localization);

        int databaseSizeBeforeDelete = localizationRepository.findAll().size();

        // Delete the localization
        restLocalizationMockMvc.perform(delete("/api/localizations/{id}", localization.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Localization> localizationList = localizationRepository.findAll();
        assertThat(localizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
