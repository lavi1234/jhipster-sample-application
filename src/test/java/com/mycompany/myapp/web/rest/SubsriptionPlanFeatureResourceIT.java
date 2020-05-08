package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.SubsriptionPlanFeature;
import com.mycompany.myapp.domain.SubscriptionPlan;
import com.mycompany.myapp.domain.AppFeatures;
import com.mycompany.myapp.repository.SubsriptionPlanFeatureRepository;
import com.mycompany.myapp.service.SubsriptionPlanFeatureService;
import com.mycompany.myapp.service.dto.SubsriptionPlanFeatureDTO;
import com.mycompany.myapp.service.mapper.SubsriptionPlanFeatureMapper;
import com.mycompany.myapp.service.dto.SubsriptionPlanFeatureCriteria;
import com.mycompany.myapp.service.SubsriptionPlanFeatureQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SubsriptionPlanFeatureResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class SubsriptionPlanFeatureResourceIT {

    @Autowired
    private SubsriptionPlanFeatureRepository subsriptionPlanFeatureRepository;

    @Mock
    private SubsriptionPlanFeatureRepository subsriptionPlanFeatureRepositoryMock;

    @Autowired
    private SubsriptionPlanFeatureMapper subsriptionPlanFeatureMapper;

    @Mock
    private SubsriptionPlanFeatureService subsriptionPlanFeatureServiceMock;

    @Autowired
    private SubsriptionPlanFeatureService subsriptionPlanFeatureService;

    @Autowired
    private SubsriptionPlanFeatureQueryService subsriptionPlanFeatureQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubsriptionPlanFeatureMockMvc;

    private SubsriptionPlanFeature subsriptionPlanFeature;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubsriptionPlanFeature createEntity(EntityManager em) {
        SubsriptionPlanFeature subsriptionPlanFeature = new SubsriptionPlanFeature();
        // Add required entity
        SubscriptionPlan subscriptionPlan;
        if (TestUtil.findAll(em, SubscriptionPlan.class).isEmpty()) {
            subscriptionPlan = SubscriptionPlanResourceIT.createEntity(em);
            em.persist(subscriptionPlan);
            em.flush();
        } else {
            subscriptionPlan = TestUtil.findAll(em, SubscriptionPlan.class).get(0);
        }
        subsriptionPlanFeature.getSubscriptionPlans().add(subscriptionPlan);
        // Add required entity
        AppFeatures appFeatures;
        if (TestUtil.findAll(em, AppFeatures.class).isEmpty()) {
            appFeatures = AppFeaturesResourceIT.createEntity(em);
            em.persist(appFeatures);
            em.flush();
        } else {
            appFeatures = TestUtil.findAll(em, AppFeatures.class).get(0);
        }
        subsriptionPlanFeature.getAppFeatures().add(appFeatures);
        return subsriptionPlanFeature;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubsriptionPlanFeature createUpdatedEntity(EntityManager em) {
        SubsriptionPlanFeature subsriptionPlanFeature = new SubsriptionPlanFeature();
        // Add required entity
        SubscriptionPlan subscriptionPlan;
        if (TestUtil.findAll(em, SubscriptionPlan.class).isEmpty()) {
            subscriptionPlan = SubscriptionPlanResourceIT.createUpdatedEntity(em);
            em.persist(subscriptionPlan);
            em.flush();
        } else {
            subscriptionPlan = TestUtil.findAll(em, SubscriptionPlan.class).get(0);
        }
        subsriptionPlanFeature.getSubscriptionPlans().add(subscriptionPlan);
        // Add required entity
        AppFeatures appFeatures;
        if (TestUtil.findAll(em, AppFeatures.class).isEmpty()) {
            appFeatures = AppFeaturesResourceIT.createUpdatedEntity(em);
            em.persist(appFeatures);
            em.flush();
        } else {
            appFeatures = TestUtil.findAll(em, AppFeatures.class).get(0);
        }
        subsriptionPlanFeature.getAppFeatures().add(appFeatures);
        return subsriptionPlanFeature;
    }

    @BeforeEach
    public void initTest() {
        subsriptionPlanFeature = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubsriptionPlanFeature() throws Exception {
        int databaseSizeBeforeCreate = subsriptionPlanFeatureRepository.findAll().size();

        // Create the SubsriptionPlanFeature
        SubsriptionPlanFeatureDTO subsriptionPlanFeatureDTO = subsriptionPlanFeatureMapper.toDto(subsriptionPlanFeature);
        restSubsriptionPlanFeatureMockMvc.perform(post("/api/subsription-plan-features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subsriptionPlanFeatureDTO)))
            .andExpect(status().isCreated());

        // Validate the SubsriptionPlanFeature in the database
        List<SubsriptionPlanFeature> subsriptionPlanFeatureList = subsriptionPlanFeatureRepository.findAll();
        assertThat(subsriptionPlanFeatureList).hasSize(databaseSizeBeforeCreate + 1);
        SubsriptionPlanFeature testSubsriptionPlanFeature = subsriptionPlanFeatureList.get(subsriptionPlanFeatureList.size() - 1);
    }

    @Test
    @Transactional
    public void createSubsriptionPlanFeatureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subsriptionPlanFeatureRepository.findAll().size();

        // Create the SubsriptionPlanFeature with an existing ID
        subsriptionPlanFeature.setId(1L);
        SubsriptionPlanFeatureDTO subsriptionPlanFeatureDTO = subsriptionPlanFeatureMapper.toDto(subsriptionPlanFeature);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubsriptionPlanFeatureMockMvc.perform(post("/api/subsription-plan-features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subsriptionPlanFeatureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubsriptionPlanFeature in the database
        List<SubsriptionPlanFeature> subsriptionPlanFeatureList = subsriptionPlanFeatureRepository.findAll();
        assertThat(subsriptionPlanFeatureList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSubsriptionPlanFeatures() throws Exception {
        // Initialize the database
        subsriptionPlanFeatureRepository.saveAndFlush(subsriptionPlanFeature);

        // Get all the subsriptionPlanFeatureList
        restSubsriptionPlanFeatureMockMvc.perform(get("/api/subsription-plan-features?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subsriptionPlanFeature.getId().intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllSubsriptionPlanFeaturesWithEagerRelationshipsIsEnabled() throws Exception {
        when(subsriptionPlanFeatureServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubsriptionPlanFeatureMockMvc.perform(get("/api/subsription-plan-features?eagerload=true"))
            .andExpect(status().isOk());

        verify(subsriptionPlanFeatureServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllSubsriptionPlanFeaturesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(subsriptionPlanFeatureServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSubsriptionPlanFeatureMockMvc.perform(get("/api/subsription-plan-features?eagerload=true"))
            .andExpect(status().isOk());

        verify(subsriptionPlanFeatureServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getSubsriptionPlanFeature() throws Exception {
        // Initialize the database
        subsriptionPlanFeatureRepository.saveAndFlush(subsriptionPlanFeature);

        // Get the subsriptionPlanFeature
        restSubsriptionPlanFeatureMockMvc.perform(get("/api/subsription-plan-features/{id}", subsriptionPlanFeature.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subsriptionPlanFeature.getId().intValue()));
    }


    @Test
    @Transactional
    public void getSubsriptionPlanFeaturesByIdFiltering() throws Exception {
        // Initialize the database
        subsriptionPlanFeatureRepository.saveAndFlush(subsriptionPlanFeature);

        Long id = subsriptionPlanFeature.getId();

        defaultSubsriptionPlanFeatureShouldBeFound("id.equals=" + id);
        defaultSubsriptionPlanFeatureShouldNotBeFound("id.notEquals=" + id);

        defaultSubsriptionPlanFeatureShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubsriptionPlanFeatureShouldNotBeFound("id.greaterThan=" + id);

        defaultSubsriptionPlanFeatureShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubsriptionPlanFeatureShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSubsriptionPlanFeaturesBySubscriptionPlanIsEqualToSomething() throws Exception {
        // Get already existing entity
        SubscriptionPlan subscriptionPlan = subsriptionPlanFeature.getSubscriptionPlan();
        subsriptionPlanFeatureRepository.saveAndFlush(subsriptionPlanFeature);
        Long subscriptionPlanId = subscriptionPlan.getId();

        // Get all the subsriptionPlanFeatureList where subscriptionPlan equals to subscriptionPlanId
        defaultSubsriptionPlanFeatureShouldBeFound("subscriptionPlanId.equals=" + subscriptionPlanId);

        // Get all the subsriptionPlanFeatureList where subscriptionPlan equals to subscriptionPlanId + 1
        defaultSubsriptionPlanFeatureShouldNotBeFound("subscriptionPlanId.equals=" + (subscriptionPlanId + 1));
    }


    @Test
    @Transactional
    public void getAllSubsriptionPlanFeaturesByAppFeatureIsEqualToSomething() throws Exception {
        // Get already existing entity
        AppFeatures appFeature = subsriptionPlanFeature.getAppFeature();
        subsriptionPlanFeatureRepository.saveAndFlush(subsriptionPlanFeature);
        Long appFeatureId = appFeature.getId();

        // Get all the subsriptionPlanFeatureList where appFeature equals to appFeatureId
        defaultSubsriptionPlanFeatureShouldBeFound("appFeatureId.equals=" + appFeatureId);

        // Get all the subsriptionPlanFeatureList where appFeature equals to appFeatureId + 1
        defaultSubsriptionPlanFeatureShouldNotBeFound("appFeatureId.equals=" + (appFeatureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubsriptionPlanFeatureShouldBeFound(String filter) throws Exception {
        restSubsriptionPlanFeatureMockMvc.perform(get("/api/subsription-plan-features?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subsriptionPlanFeature.getId().intValue())));

        // Check, that the count call also returns 1
        restSubsriptionPlanFeatureMockMvc.perform(get("/api/subsription-plan-features/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubsriptionPlanFeatureShouldNotBeFound(String filter) throws Exception {
        restSubsriptionPlanFeatureMockMvc.perform(get("/api/subsription-plan-features?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubsriptionPlanFeatureMockMvc.perform(get("/api/subsription-plan-features/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSubsriptionPlanFeature() throws Exception {
        // Get the subsriptionPlanFeature
        restSubsriptionPlanFeatureMockMvc.perform(get("/api/subsription-plan-features/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubsriptionPlanFeature() throws Exception {
        // Initialize the database
        subsriptionPlanFeatureRepository.saveAndFlush(subsriptionPlanFeature);

        int databaseSizeBeforeUpdate = subsriptionPlanFeatureRepository.findAll().size();

        // Update the subsriptionPlanFeature
        SubsriptionPlanFeature updatedSubsriptionPlanFeature = subsriptionPlanFeatureRepository.findById(subsriptionPlanFeature.getId()).get();
        // Disconnect from session so that the updates on updatedSubsriptionPlanFeature are not directly saved in db
        em.detach(updatedSubsriptionPlanFeature);
        SubsriptionPlanFeatureDTO subsriptionPlanFeatureDTO = subsriptionPlanFeatureMapper.toDto(updatedSubsriptionPlanFeature);

        restSubsriptionPlanFeatureMockMvc.perform(put("/api/subsription-plan-features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subsriptionPlanFeatureDTO)))
            .andExpect(status().isOk());

        // Validate the SubsriptionPlanFeature in the database
        List<SubsriptionPlanFeature> subsriptionPlanFeatureList = subsriptionPlanFeatureRepository.findAll();
        assertThat(subsriptionPlanFeatureList).hasSize(databaseSizeBeforeUpdate);
        SubsriptionPlanFeature testSubsriptionPlanFeature = subsriptionPlanFeatureList.get(subsriptionPlanFeatureList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSubsriptionPlanFeature() throws Exception {
        int databaseSizeBeforeUpdate = subsriptionPlanFeatureRepository.findAll().size();

        // Create the SubsriptionPlanFeature
        SubsriptionPlanFeatureDTO subsriptionPlanFeatureDTO = subsriptionPlanFeatureMapper.toDto(subsriptionPlanFeature);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubsriptionPlanFeatureMockMvc.perform(put("/api/subsription-plan-features")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subsriptionPlanFeatureDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SubsriptionPlanFeature in the database
        List<SubsriptionPlanFeature> subsriptionPlanFeatureList = subsriptionPlanFeatureRepository.findAll();
        assertThat(subsriptionPlanFeatureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubsriptionPlanFeature() throws Exception {
        // Initialize the database
        subsriptionPlanFeatureRepository.saveAndFlush(subsriptionPlanFeature);

        int databaseSizeBeforeDelete = subsriptionPlanFeatureRepository.findAll().size();

        // Delete the subsriptionPlanFeature
        restSubsriptionPlanFeatureMockMvc.perform(delete("/api/subsription-plan-features/{id}", subsriptionPlanFeature.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubsriptionPlanFeature> subsriptionPlanFeatureList = subsriptionPlanFeatureRepository.findAll();
        assertThat(subsriptionPlanFeatureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
