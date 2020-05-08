package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.UserCategoryMapping;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.repository.UserCategoryMappingRepository;
import com.mycompany.myapp.service.UserCategoryMappingService;
import com.mycompany.myapp.service.dto.UserCategoryMappingDTO;
import com.mycompany.myapp.service.mapper.UserCategoryMappingMapper;
import com.mycompany.myapp.service.dto.UserCategoryMappingCriteria;
import com.mycompany.myapp.service.UserCategoryMappingQueryService;

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
 * Integration tests for the {@link UserCategoryMappingResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserCategoryMappingResourceIT {

    @Autowired
    private UserCategoryMappingRepository userCategoryMappingRepository;

    @Mock
    private UserCategoryMappingRepository userCategoryMappingRepositoryMock;

    @Autowired
    private UserCategoryMappingMapper userCategoryMappingMapper;

    @Mock
    private UserCategoryMappingService userCategoryMappingServiceMock;

    @Autowired
    private UserCategoryMappingService userCategoryMappingService;

    @Autowired
    private UserCategoryMappingQueryService userCategoryMappingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserCategoryMappingMockMvc;

    private UserCategoryMapping userCategoryMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCategoryMapping createEntity(EntityManager em) {
        UserCategoryMapping userCategoryMapping = new UserCategoryMapping();
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        userCategoryMapping.getUserProfiles().add(userProfile);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        userCategoryMapping.getCategories().add(category);
        return userCategoryMapping;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCategoryMapping createUpdatedEntity(EntityManager em) {
        UserCategoryMapping userCategoryMapping = new UserCategoryMapping();
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        userCategoryMapping.getUserProfiles().add(userProfile);
        // Add required entity
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            category = CategoryResourceIT.createUpdatedEntity(em);
            em.persist(category);
            em.flush();
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        userCategoryMapping.getCategories().add(category);
        return userCategoryMapping;
    }

    @BeforeEach
    public void initTest() {
        userCategoryMapping = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserCategoryMapping() throws Exception {
        int databaseSizeBeforeCreate = userCategoryMappingRepository.findAll().size();

        // Create the UserCategoryMapping
        UserCategoryMappingDTO userCategoryMappingDTO = userCategoryMappingMapper.toDto(userCategoryMapping);
        restUserCategoryMappingMockMvc.perform(post("/api/user-category-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCategoryMappingDTO)))
            .andExpect(status().isCreated());

        // Validate the UserCategoryMapping in the database
        List<UserCategoryMapping> userCategoryMappingList = userCategoryMappingRepository.findAll();
        assertThat(userCategoryMappingList).hasSize(databaseSizeBeforeCreate + 1);
        UserCategoryMapping testUserCategoryMapping = userCategoryMappingList.get(userCategoryMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void createUserCategoryMappingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userCategoryMappingRepository.findAll().size();

        // Create the UserCategoryMapping with an existing ID
        userCategoryMapping.setId(1L);
        UserCategoryMappingDTO userCategoryMappingDTO = userCategoryMappingMapper.toDto(userCategoryMapping);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserCategoryMappingMockMvc.perform(post("/api/user-category-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCategoryMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserCategoryMapping in the database
        List<UserCategoryMapping> userCategoryMappingList = userCategoryMappingRepository.findAll();
        assertThat(userCategoryMappingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserCategoryMappings() throws Exception {
        // Initialize the database
        userCategoryMappingRepository.saveAndFlush(userCategoryMapping);

        // Get all the userCategoryMappingList
        restUserCategoryMappingMockMvc.perform(get("/api/user-category-mappings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userCategoryMapping.getId().intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUserCategoryMappingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(userCategoryMappingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserCategoryMappingMockMvc.perform(get("/api/user-category-mappings?eagerload=true"))
            .andExpect(status().isOk());

        verify(userCategoryMappingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUserCategoryMappingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(userCategoryMappingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUserCategoryMappingMockMvc.perform(get("/api/user-category-mappings?eagerload=true"))
            .andExpect(status().isOk());

        verify(userCategoryMappingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUserCategoryMapping() throws Exception {
        // Initialize the database
        userCategoryMappingRepository.saveAndFlush(userCategoryMapping);

        // Get the userCategoryMapping
        restUserCategoryMappingMockMvc.perform(get("/api/user-category-mappings/{id}", userCategoryMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userCategoryMapping.getId().intValue()));
    }


    @Test
    @Transactional
    public void getUserCategoryMappingsByIdFiltering() throws Exception {
        // Initialize the database
        userCategoryMappingRepository.saveAndFlush(userCategoryMapping);

        Long id = userCategoryMapping.getId();

        defaultUserCategoryMappingShouldBeFound("id.equals=" + id);
        defaultUserCategoryMappingShouldNotBeFound("id.notEquals=" + id);

        defaultUserCategoryMappingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserCategoryMappingShouldNotBeFound("id.greaterThan=" + id);

        defaultUserCategoryMappingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserCategoryMappingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserCategoryMappingsByUserProfileIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile userProfile = userCategoryMapping.getUserProfile();
        userCategoryMappingRepository.saveAndFlush(userCategoryMapping);
        Long userProfileId = userProfile.getId();

        // Get all the userCategoryMappingList where userProfile equals to userProfileId
        defaultUserCategoryMappingShouldBeFound("userProfileId.equals=" + userProfileId);

        // Get all the userCategoryMappingList where userProfile equals to userProfileId + 1
        defaultUserCategoryMappingShouldNotBeFound("userProfileId.equals=" + (userProfileId + 1));
    }


    @Test
    @Transactional
    public void getAllUserCategoryMappingsByCategoryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Category category = userCategoryMapping.getCategory();
        userCategoryMappingRepository.saveAndFlush(userCategoryMapping);
        Long categoryId = category.getId();

        // Get all the userCategoryMappingList where category equals to categoryId
        defaultUserCategoryMappingShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the userCategoryMappingList where category equals to categoryId + 1
        defaultUserCategoryMappingShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserCategoryMappingShouldBeFound(String filter) throws Exception {
        restUserCategoryMappingMockMvc.perform(get("/api/user-category-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userCategoryMapping.getId().intValue())));

        // Check, that the count call also returns 1
        restUserCategoryMappingMockMvc.perform(get("/api/user-category-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserCategoryMappingShouldNotBeFound(String filter) throws Exception {
        restUserCategoryMappingMockMvc.perform(get("/api/user-category-mappings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserCategoryMappingMockMvc.perform(get("/api/user-category-mappings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUserCategoryMapping() throws Exception {
        // Get the userCategoryMapping
        restUserCategoryMappingMockMvc.perform(get("/api/user-category-mappings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserCategoryMapping() throws Exception {
        // Initialize the database
        userCategoryMappingRepository.saveAndFlush(userCategoryMapping);

        int databaseSizeBeforeUpdate = userCategoryMappingRepository.findAll().size();

        // Update the userCategoryMapping
        UserCategoryMapping updatedUserCategoryMapping = userCategoryMappingRepository.findById(userCategoryMapping.getId()).get();
        // Disconnect from session so that the updates on updatedUserCategoryMapping are not directly saved in db
        em.detach(updatedUserCategoryMapping);
        UserCategoryMappingDTO userCategoryMappingDTO = userCategoryMappingMapper.toDto(updatedUserCategoryMapping);

        restUserCategoryMappingMockMvc.perform(put("/api/user-category-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCategoryMappingDTO)))
            .andExpect(status().isOk());

        // Validate the UserCategoryMapping in the database
        List<UserCategoryMapping> userCategoryMappingList = userCategoryMappingRepository.findAll();
        assertThat(userCategoryMappingList).hasSize(databaseSizeBeforeUpdate);
        UserCategoryMapping testUserCategoryMapping = userCategoryMappingList.get(userCategoryMappingList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingUserCategoryMapping() throws Exception {
        int databaseSizeBeforeUpdate = userCategoryMappingRepository.findAll().size();

        // Create the UserCategoryMapping
        UserCategoryMappingDTO userCategoryMappingDTO = userCategoryMappingMapper.toDto(userCategoryMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserCategoryMappingMockMvc.perform(put("/api/user-category-mappings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCategoryMappingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserCategoryMapping in the database
        List<UserCategoryMapping> userCategoryMappingList = userCategoryMappingRepository.findAll();
        assertThat(userCategoryMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserCategoryMapping() throws Exception {
        // Initialize the database
        userCategoryMappingRepository.saveAndFlush(userCategoryMapping);

        int databaseSizeBeforeDelete = userCategoryMappingRepository.findAll().size();

        // Delete the userCategoryMapping
        restUserCategoryMappingMockMvc.perform(delete("/api/user-category-mappings/{id}", userCategoryMapping.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserCategoryMapping> userCategoryMappingList = userCategoryMappingRepository.findAll();
        assertThat(userCategoryMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
