package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.TradingHours;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.repository.TradingHoursRepository;
import com.mycompany.myapp.service.TradingHoursService;
import com.mycompany.myapp.service.dto.TradingHoursDTO;
import com.mycompany.myapp.service.mapper.TradingHoursMapper;
import com.mycompany.myapp.service.dto.TradingHoursCriteria;
import com.mycompany.myapp.service.TradingHoursQueryService;

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
 * Integration tests for the {@link TradingHoursResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TradingHoursResourceIT {

    private static final String DEFAULT_DAY = "AAAAAAAAAA";
    private static final String UPDATED_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_START_TIME = "14:03:19";
    private static final String UPDATED_START_TIME = "20:37:28";

    private static final String DEFAULT_END_TIME = "10:21:44";
    private static final String UPDATED_END_TIME = "17:23:19";

    @Autowired
    private TradingHoursRepository tradingHoursRepository;

    @Autowired
    private TradingHoursMapper tradingHoursMapper;

    @Autowired
    private TradingHoursService tradingHoursService;

    @Autowired
    private TradingHoursQueryService tradingHoursQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTradingHoursMockMvc;

    private TradingHours tradingHours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradingHours createEntity(EntityManager em) {
        TradingHours tradingHours = new TradingHours()
            .day(DEFAULT_DAY)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        tradingHours.setCompany(company);
        return tradingHours;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradingHours createUpdatedEntity(EntityManager em) {
        TradingHours tradingHours = new TradingHours()
            .day(UPDATED_DAY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        // Add required entity
        Company company;
        if (TestUtil.findAll(em, Company.class).isEmpty()) {
            company = CompanyResourceIT.createUpdatedEntity(em);
            em.persist(company);
            em.flush();
        } else {
            company = TestUtil.findAll(em, Company.class).get(0);
        }
        tradingHours.setCompany(company);
        return tradingHours;
    }

    @BeforeEach
    public void initTest() {
        tradingHours = createEntity(em);
    }

    @Test
    @Transactional
    public void createTradingHours() throws Exception {
        int databaseSizeBeforeCreate = tradingHoursRepository.findAll().size();

        // Create the TradingHours
        TradingHoursDTO tradingHoursDTO = tradingHoursMapper.toDto(tradingHours);
        restTradingHoursMockMvc.perform(post("/api/trading-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradingHoursDTO)))
            .andExpect(status().isCreated());

        // Validate the TradingHours in the database
        List<TradingHours> tradingHoursList = tradingHoursRepository.findAll();
        assertThat(tradingHoursList).hasSize(databaseSizeBeforeCreate + 1);
        TradingHours testTradingHours = tradingHoursList.get(tradingHoursList.size() - 1);
        assertThat(testTradingHours.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testTradingHours.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTradingHours.getEndTime()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    @Transactional
    public void createTradingHoursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tradingHoursRepository.findAll().size();

        // Create the TradingHours with an existing ID
        tradingHours.setId(1L);
        TradingHoursDTO tradingHoursDTO = tradingHoursMapper.toDto(tradingHours);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTradingHoursMockMvc.perform(post("/api/trading-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradingHoursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TradingHours in the database
        List<TradingHours> tradingHoursList = tradingHoursRepository.findAll();
        assertThat(tradingHoursList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingHoursRepository.findAll().size();
        // set the field null
        tradingHours.setDay(null);

        // Create the TradingHours, which fails.
        TradingHoursDTO tradingHoursDTO = tradingHoursMapper.toDto(tradingHours);

        restTradingHoursMockMvc.perform(post("/api/trading-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradingHoursDTO)))
            .andExpect(status().isBadRequest());

        List<TradingHours> tradingHoursList = tradingHoursRepository.findAll();
        assertThat(tradingHoursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingHoursRepository.findAll().size();
        // set the field null
        tradingHours.setStartTime(null);

        // Create the TradingHours, which fails.
        TradingHoursDTO tradingHoursDTO = tradingHoursMapper.toDto(tradingHours);

        restTradingHoursMockMvc.perform(post("/api/trading-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradingHoursDTO)))
            .andExpect(status().isBadRequest());

        List<TradingHours> tradingHoursList = tradingHoursRepository.findAll();
        assertThat(tradingHoursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tradingHoursRepository.findAll().size();
        // set the field null
        tradingHours.setEndTime(null);

        // Create the TradingHours, which fails.
        TradingHoursDTO tradingHoursDTO = tradingHoursMapper.toDto(tradingHours);

        restTradingHoursMockMvc.perform(post("/api/trading-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradingHoursDTO)))
            .andExpect(status().isBadRequest());

        List<TradingHours> tradingHoursList = tradingHoursRepository.findAll();
        assertThat(tradingHoursList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTradingHours() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList
        restTradingHoursMockMvc.perform(get("/api/trading-hours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tradingHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)));
    }
    
    @Test
    @Transactional
    public void getTradingHours() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get the tradingHours
        restTradingHoursMockMvc.perform(get("/api/trading-hours/{id}", tradingHours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tradingHours.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME));
    }


    @Test
    @Transactional
    public void getTradingHoursByIdFiltering() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        Long id = tradingHours.getId();

        defaultTradingHoursShouldBeFound("id.equals=" + id);
        defaultTradingHoursShouldNotBeFound("id.notEquals=" + id);

        defaultTradingHoursShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTradingHoursShouldNotBeFound("id.greaterThan=" + id);

        defaultTradingHoursShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTradingHoursShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTradingHoursByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where day equals to DEFAULT_DAY
        defaultTradingHoursShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the tradingHoursList where day equals to UPDATED_DAY
        defaultTradingHoursShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where day not equals to DEFAULT_DAY
        defaultTradingHoursShouldNotBeFound("day.notEquals=" + DEFAULT_DAY);

        // Get all the tradingHoursList where day not equals to UPDATED_DAY
        defaultTradingHoursShouldBeFound("day.notEquals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByDayIsInShouldWork() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where day in DEFAULT_DAY or UPDATED_DAY
        defaultTradingHoursShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the tradingHoursList where day equals to UPDATED_DAY
        defaultTradingHoursShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where day is not null
        defaultTradingHoursShouldBeFound("day.specified=true");

        // Get all the tradingHoursList where day is null
        defaultTradingHoursShouldNotBeFound("day.specified=false");
    }
                @Test
    @Transactional
    public void getAllTradingHoursByDayContainsSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where day contains DEFAULT_DAY
        defaultTradingHoursShouldBeFound("day.contains=" + DEFAULT_DAY);

        // Get all the tradingHoursList where day contains UPDATED_DAY
        defaultTradingHoursShouldNotBeFound("day.contains=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByDayNotContainsSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where day does not contain DEFAULT_DAY
        defaultTradingHoursShouldNotBeFound("day.doesNotContain=" + DEFAULT_DAY);

        // Get all the tradingHoursList where day does not contain UPDATED_DAY
        defaultTradingHoursShouldBeFound("day.doesNotContain=" + UPDATED_DAY);
    }


    @Test
    @Transactional
    public void getAllTradingHoursByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where startTime equals to DEFAULT_START_TIME
        defaultTradingHoursShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the tradingHoursList where startTime equals to UPDATED_START_TIME
        defaultTradingHoursShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByStartTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where startTime not equals to DEFAULT_START_TIME
        defaultTradingHoursShouldNotBeFound("startTime.notEquals=" + DEFAULT_START_TIME);

        // Get all the tradingHoursList where startTime not equals to UPDATED_START_TIME
        defaultTradingHoursShouldBeFound("startTime.notEquals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultTradingHoursShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the tradingHoursList where startTime equals to UPDATED_START_TIME
        defaultTradingHoursShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where startTime is not null
        defaultTradingHoursShouldBeFound("startTime.specified=true");

        // Get all the tradingHoursList where startTime is null
        defaultTradingHoursShouldNotBeFound("startTime.specified=false");
    }
                @Test
    @Transactional
    public void getAllTradingHoursByStartTimeContainsSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where startTime contains DEFAULT_START_TIME
        defaultTradingHoursShouldBeFound("startTime.contains=" + DEFAULT_START_TIME);

        // Get all the tradingHoursList where startTime contains UPDATED_START_TIME
        defaultTradingHoursShouldNotBeFound("startTime.contains=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByStartTimeNotContainsSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where startTime does not contain DEFAULT_START_TIME
        defaultTradingHoursShouldNotBeFound("startTime.doesNotContain=" + DEFAULT_START_TIME);

        // Get all the tradingHoursList where startTime does not contain UPDATED_START_TIME
        defaultTradingHoursShouldBeFound("startTime.doesNotContain=" + UPDATED_START_TIME);
    }


    @Test
    @Transactional
    public void getAllTradingHoursByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where endTime equals to DEFAULT_END_TIME
        defaultTradingHoursShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the tradingHoursList where endTime equals to UPDATED_END_TIME
        defaultTradingHoursShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByEndTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where endTime not equals to DEFAULT_END_TIME
        defaultTradingHoursShouldNotBeFound("endTime.notEquals=" + DEFAULT_END_TIME);

        // Get all the tradingHoursList where endTime not equals to UPDATED_END_TIME
        defaultTradingHoursShouldBeFound("endTime.notEquals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultTradingHoursShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the tradingHoursList where endTime equals to UPDATED_END_TIME
        defaultTradingHoursShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where endTime is not null
        defaultTradingHoursShouldBeFound("endTime.specified=true");

        // Get all the tradingHoursList where endTime is null
        defaultTradingHoursShouldNotBeFound("endTime.specified=false");
    }
                @Test
    @Transactional
    public void getAllTradingHoursByEndTimeContainsSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where endTime contains DEFAULT_END_TIME
        defaultTradingHoursShouldBeFound("endTime.contains=" + DEFAULT_END_TIME);

        // Get all the tradingHoursList where endTime contains UPDATED_END_TIME
        defaultTradingHoursShouldNotBeFound("endTime.contains=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllTradingHoursByEndTimeNotContainsSomething() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        // Get all the tradingHoursList where endTime does not contain DEFAULT_END_TIME
        defaultTradingHoursShouldNotBeFound("endTime.doesNotContain=" + DEFAULT_END_TIME);

        // Get all the tradingHoursList where endTime does not contain UPDATED_END_TIME
        defaultTradingHoursShouldBeFound("endTime.doesNotContain=" + UPDATED_END_TIME);
    }


    @Test
    @Transactional
    public void getAllTradingHoursByCompanyIsEqualToSomething() throws Exception {
        // Get already existing entity
        Company company = tradingHours.getCompany();
        tradingHoursRepository.saveAndFlush(tradingHours);
        Long companyId = company.getId();

        // Get all the tradingHoursList where company equals to companyId
        defaultTradingHoursShouldBeFound("companyId.equals=" + companyId);

        // Get all the tradingHoursList where company equals to companyId + 1
        defaultTradingHoursShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTradingHoursShouldBeFound(String filter) throws Exception {
        restTradingHoursMockMvc.perform(get("/api/trading-hours?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tradingHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME)));

        // Check, that the count call also returns 1
        restTradingHoursMockMvc.perform(get("/api/trading-hours/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTradingHoursShouldNotBeFound(String filter) throws Exception {
        restTradingHoursMockMvc.perform(get("/api/trading-hours?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTradingHoursMockMvc.perform(get("/api/trading-hours/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTradingHours() throws Exception {
        // Get the tradingHours
        restTradingHoursMockMvc.perform(get("/api/trading-hours/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTradingHours() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        int databaseSizeBeforeUpdate = tradingHoursRepository.findAll().size();

        // Update the tradingHours
        TradingHours updatedTradingHours = tradingHoursRepository.findById(tradingHours.getId()).get();
        // Disconnect from session so that the updates on updatedTradingHours are not directly saved in db
        em.detach(updatedTradingHours);
        updatedTradingHours
            .day(UPDATED_DAY)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME);
        TradingHoursDTO tradingHoursDTO = tradingHoursMapper.toDto(updatedTradingHours);

        restTradingHoursMockMvc.perform(put("/api/trading-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradingHoursDTO)))
            .andExpect(status().isOk());

        // Validate the TradingHours in the database
        List<TradingHours> tradingHoursList = tradingHoursRepository.findAll();
        assertThat(tradingHoursList).hasSize(databaseSizeBeforeUpdate);
        TradingHours testTradingHours = tradingHoursList.get(tradingHoursList.size() - 1);
        assertThat(testTradingHours.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testTradingHours.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTradingHours.getEndTime()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTradingHours() throws Exception {
        int databaseSizeBeforeUpdate = tradingHoursRepository.findAll().size();

        // Create the TradingHours
        TradingHoursDTO tradingHoursDTO = tradingHoursMapper.toDto(tradingHours);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradingHoursMockMvc.perform(put("/api/trading-hours")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tradingHoursDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TradingHours in the database
        List<TradingHours> tradingHoursList = tradingHoursRepository.findAll();
        assertThat(tradingHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTradingHours() throws Exception {
        // Initialize the database
        tradingHoursRepository.saveAndFlush(tradingHours);

        int databaseSizeBeforeDelete = tradingHoursRepository.findAll().size();

        // Delete the tradingHours
        restTradingHoursMockMvc.perform(delete("/api/trading-hours/{id}", tradingHours.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TradingHours> tradingHoursList = tradingHoursRepository.findAll();
        assertThat(tradingHoursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
