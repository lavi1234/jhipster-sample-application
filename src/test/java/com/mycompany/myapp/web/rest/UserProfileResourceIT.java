package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.domain.UserCategoryMapping;
import com.mycompany.myapp.repository.UserProfileRepository;
import com.mycompany.myapp.service.UserProfileService;
import com.mycompany.myapp.service.dto.UserProfileDTO;
import com.mycompany.myapp.service.mapper.UserProfileMapper;
import com.mycompany.myapp.service.dto.UserProfileCriteria;
import com.mycompany.myapp.service.UserProfileQueryService;

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
 * Integration tests for the {@link UserProfileResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class UserProfileResourceIT {

    private static final String DEFAULT_SALUTATION = "AAAAAAAAAA";
    private static final String UPDATED_SALUTATION = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PICTURE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_USER_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserProfileQueryService userProfileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserProfileMockMvc;

    private UserProfile userProfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .salutation(DEFAULT_SALUTATION)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .profilePicture(DEFAULT_PROFILE_PICTURE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .userType(DEFAULT_USER_TYPE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userProfile.setUser(user);
        return userProfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserProfile createUpdatedEntity(EntityManager em) {
        UserProfile userProfile = new UserProfile()
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .userType(UPDATED_USER_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userProfile.setUser(user);
        return userProfile;
    }

    @BeforeEach
    public void initTest() {
        userProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserProfile() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate + 1);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getSalutation()).isEqualTo(DEFAULT_SALUTATION);
        assertThat(testUserProfile.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testUserProfile.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testUserProfile.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);
        assertThat(testUserProfile.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testUserProfile.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testUserProfile.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testUserProfile.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createUserProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userProfileRepository.findAll().size();

        // Create the UserProfile with an existing ID
        userProfile.setId(1L);
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSalutationIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setSalutation(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setFirstName(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setLastName(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setPhoneNumber(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUserTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setUserType(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setCreatedAt(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userProfileRepository.findAll().size();
        // set the field null
        userProfile.setUpdatedAt(null);

        // Create the UserProfile, which fails.
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        restUserProfileMockMvc.perform(post("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserProfiles() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].salutation").value(hasItem(DEFAULT_SALUTATION)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(DEFAULT_PROFILE_PICTURE)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", userProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userProfile.getId().intValue()))
            .andExpect(jsonPath("$.salutation").value(DEFAULT_SALUTATION))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.profilePicture").value(DEFAULT_PROFILE_PICTURE))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getUserProfilesByIdFiltering() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        Long id = userProfile.getId();

        defaultUserProfileShouldBeFound("id.equals=" + id);
        defaultUserProfileShouldNotBeFound("id.notEquals=" + id);

        defaultUserProfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserProfileShouldNotBeFound("id.greaterThan=" + id);

        defaultUserProfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserProfileShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUserProfilesBySalutationIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where salutation equals to DEFAULT_SALUTATION
        defaultUserProfileShouldBeFound("salutation.equals=" + DEFAULT_SALUTATION);

        // Get all the userProfileList where salutation equals to UPDATED_SALUTATION
        defaultUserProfileShouldNotBeFound("salutation.equals=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllUserProfilesBySalutationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where salutation not equals to DEFAULT_SALUTATION
        defaultUserProfileShouldNotBeFound("salutation.notEquals=" + DEFAULT_SALUTATION);

        // Get all the userProfileList where salutation not equals to UPDATED_SALUTATION
        defaultUserProfileShouldBeFound("salutation.notEquals=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllUserProfilesBySalutationIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where salutation in DEFAULT_SALUTATION or UPDATED_SALUTATION
        defaultUserProfileShouldBeFound("salutation.in=" + DEFAULT_SALUTATION + "," + UPDATED_SALUTATION);

        // Get all the userProfileList where salutation equals to UPDATED_SALUTATION
        defaultUserProfileShouldNotBeFound("salutation.in=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllUserProfilesBySalutationIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where salutation is not null
        defaultUserProfileShouldBeFound("salutation.specified=true");

        // Get all the userProfileList where salutation is null
        defaultUserProfileShouldNotBeFound("salutation.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesBySalutationContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where salutation contains DEFAULT_SALUTATION
        defaultUserProfileShouldBeFound("salutation.contains=" + DEFAULT_SALUTATION);

        // Get all the userProfileList where salutation contains UPDATED_SALUTATION
        defaultUserProfileShouldNotBeFound("salutation.contains=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllUserProfilesBySalutationNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where salutation does not contain DEFAULT_SALUTATION
        defaultUserProfileShouldNotBeFound("salutation.doesNotContain=" + DEFAULT_SALUTATION);

        // Get all the userProfileList where salutation does not contain UPDATED_SALUTATION
        defaultUserProfileShouldBeFound("salutation.doesNotContain=" + UPDATED_SALUTATION);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName equals to DEFAULT_FIRST_NAME
        defaultUserProfileShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the userProfileList where firstName equals to UPDATED_FIRST_NAME
        defaultUserProfileShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName not equals to DEFAULT_FIRST_NAME
        defaultUserProfileShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the userProfileList where firstName not equals to UPDATED_FIRST_NAME
        defaultUserProfileShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultUserProfileShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the userProfileList where firstName equals to UPDATED_FIRST_NAME
        defaultUserProfileShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName is not null
        defaultUserProfileShouldBeFound("firstName.specified=true");

        // Get all the userProfileList where firstName is null
        defaultUserProfileShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName contains DEFAULT_FIRST_NAME
        defaultUserProfileShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the userProfileList where firstName contains UPDATED_FIRST_NAME
        defaultUserProfileShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where firstName does not contain DEFAULT_FIRST_NAME
        defaultUserProfileShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the userProfileList where firstName does not contain UPDATED_FIRST_NAME
        defaultUserProfileShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName equals to DEFAULT_LAST_NAME
        defaultUserProfileShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the userProfileList where lastName equals to UPDATED_LAST_NAME
        defaultUserProfileShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName not equals to DEFAULT_LAST_NAME
        defaultUserProfileShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the userProfileList where lastName not equals to UPDATED_LAST_NAME
        defaultUserProfileShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultUserProfileShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the userProfileList where lastName equals to UPDATED_LAST_NAME
        defaultUserProfileShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName is not null
        defaultUserProfileShouldBeFound("lastName.specified=true");

        // Get all the userProfileList where lastName is null
        defaultUserProfileShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName contains DEFAULT_LAST_NAME
        defaultUserProfileShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the userProfileList where lastName contains UPDATED_LAST_NAME
        defaultUserProfileShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where lastName does not contain DEFAULT_LAST_NAME
        defaultUserProfileShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the userProfileList where lastName does not contain UPDATED_LAST_NAME
        defaultUserProfileShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByProfilePictureIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where profilePicture equals to DEFAULT_PROFILE_PICTURE
        defaultUserProfileShouldBeFound("profilePicture.equals=" + DEFAULT_PROFILE_PICTURE);

        // Get all the userProfileList where profilePicture equals to UPDATED_PROFILE_PICTURE
        defaultUserProfileShouldNotBeFound("profilePicture.equals=" + UPDATED_PROFILE_PICTURE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByProfilePictureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where profilePicture not equals to DEFAULT_PROFILE_PICTURE
        defaultUserProfileShouldNotBeFound("profilePicture.notEquals=" + DEFAULT_PROFILE_PICTURE);

        // Get all the userProfileList where profilePicture not equals to UPDATED_PROFILE_PICTURE
        defaultUserProfileShouldBeFound("profilePicture.notEquals=" + UPDATED_PROFILE_PICTURE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByProfilePictureIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where profilePicture in DEFAULT_PROFILE_PICTURE or UPDATED_PROFILE_PICTURE
        defaultUserProfileShouldBeFound("profilePicture.in=" + DEFAULT_PROFILE_PICTURE + "," + UPDATED_PROFILE_PICTURE);

        // Get all the userProfileList where profilePicture equals to UPDATED_PROFILE_PICTURE
        defaultUserProfileShouldNotBeFound("profilePicture.in=" + UPDATED_PROFILE_PICTURE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByProfilePictureIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where profilePicture is not null
        defaultUserProfileShouldBeFound("profilePicture.specified=true");

        // Get all the userProfileList where profilePicture is null
        defaultUserProfileShouldNotBeFound("profilePicture.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByProfilePictureContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where profilePicture contains DEFAULT_PROFILE_PICTURE
        defaultUserProfileShouldBeFound("profilePicture.contains=" + DEFAULT_PROFILE_PICTURE);

        // Get all the userProfileList where profilePicture contains UPDATED_PROFILE_PICTURE
        defaultUserProfileShouldNotBeFound("profilePicture.contains=" + UPDATED_PROFILE_PICTURE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByProfilePictureNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where profilePicture does not contain DEFAULT_PROFILE_PICTURE
        defaultUserProfileShouldNotBeFound("profilePicture.doesNotContain=" + DEFAULT_PROFILE_PICTURE);

        // Get all the userProfileList where profilePicture does not contain UPDATED_PROFILE_PICTURE
        defaultUserProfileShouldBeFound("profilePicture.doesNotContain=" + UPDATED_PROFILE_PICTURE);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultUserProfileShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the userProfileList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultUserProfileShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultUserProfileShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the userProfileList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultUserProfileShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultUserProfileShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the userProfileList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultUserProfileShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where phoneNumber is not null
        defaultUserProfileShouldBeFound("phoneNumber.specified=true");

        // Get all the userProfileList where phoneNumber is null
        defaultUserProfileShouldNotBeFound("phoneNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultUserProfileShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the userProfileList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultUserProfileShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultUserProfileShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the userProfileList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultUserProfileShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByUserTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where userType equals to DEFAULT_USER_TYPE
        defaultUserProfileShouldBeFound("userType.equals=" + DEFAULT_USER_TYPE);

        // Get all the userProfileList where userType equals to UPDATED_USER_TYPE
        defaultUserProfileShouldNotBeFound("userType.equals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUserTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where userType not equals to DEFAULT_USER_TYPE
        defaultUserProfileShouldNotBeFound("userType.notEquals=" + DEFAULT_USER_TYPE);

        // Get all the userProfileList where userType not equals to UPDATED_USER_TYPE
        defaultUserProfileShouldBeFound("userType.notEquals=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUserTypeIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where userType in DEFAULT_USER_TYPE or UPDATED_USER_TYPE
        defaultUserProfileShouldBeFound("userType.in=" + DEFAULT_USER_TYPE + "," + UPDATED_USER_TYPE);

        // Get all the userProfileList where userType equals to UPDATED_USER_TYPE
        defaultUserProfileShouldNotBeFound("userType.in=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUserTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where userType is not null
        defaultUserProfileShouldBeFound("userType.specified=true");

        // Get all the userProfileList where userType is null
        defaultUserProfileShouldNotBeFound("userType.specified=false");
    }
                @Test
    @Transactional
    public void getAllUserProfilesByUserTypeContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where userType contains DEFAULT_USER_TYPE
        defaultUserProfileShouldBeFound("userType.contains=" + DEFAULT_USER_TYPE);

        // Get all the userProfileList where userType contains UPDATED_USER_TYPE
        defaultUserProfileShouldNotBeFound("userType.contains=" + UPDATED_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUserTypeNotContainsSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where userType does not contain DEFAULT_USER_TYPE
        defaultUserProfileShouldNotBeFound("userType.doesNotContain=" + DEFAULT_USER_TYPE);

        // Get all the userProfileList where userType does not contain UPDATED_USER_TYPE
        defaultUserProfileShouldBeFound("userType.doesNotContain=" + UPDATED_USER_TYPE);
    }


    @Test
    @Transactional
    public void getAllUserProfilesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where createdAt equals to DEFAULT_CREATED_AT
        defaultUserProfileShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the userProfileList where createdAt equals to UPDATED_CREATED_AT
        defaultUserProfileShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where createdAt not equals to DEFAULT_CREATED_AT
        defaultUserProfileShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the userProfileList where createdAt not equals to UPDATED_CREATED_AT
        defaultUserProfileShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultUserProfileShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the userProfileList where createdAt equals to UPDATED_CREATED_AT
        defaultUserProfileShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where createdAt is not null
        defaultUserProfileShouldBeFound("createdAt.specified=true");

        // Get all the userProfileList where createdAt is null
        defaultUserProfileShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultUserProfileShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the userProfileList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserProfileShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultUserProfileShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the userProfileList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultUserProfileShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultUserProfileShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the userProfileList where updatedAt equals to UPDATED_UPDATED_AT
        defaultUserProfileShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        // Get all the userProfileList where updatedAt is not null
        defaultUserProfileShouldBeFound("updatedAt.specified=true");

        // Get all the userProfileList where updatedAt is null
        defaultUserProfileShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserProfilesByUserIsEqualToSomething() throws Exception {
        // Get already existing entity
        User user = userProfile.getUser();
        userProfileRepository.saveAndFlush(userProfile);
        Long userId = user.getId();

        // Get all the userProfileList where user equals to userId
        defaultUserProfileShouldBeFound("userId.equals=" + userId);

        // Get all the userProfileList where user equals to userId + 1
        defaultUserProfileShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllUserProfilesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);
        Company company = CompanyResourceIT.createEntity(em);
        em.persist(company);
        em.flush();
        userProfile.setCompany(company);
        userProfileRepository.saveAndFlush(userProfile);
        Long companyId = company.getId();

        // Get all the userProfileList where company equals to companyId
        defaultUserProfileShouldBeFound("companyId.equals=" + companyId);

        // Get all the userProfileList where company equals to companyId + 1
        defaultUserProfileShouldNotBeFound("companyId.equals=" + (companyId + 1));
    }


    @Test
    @Transactional
    public void getAllUserProfilesByUserCategoryMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);
        UserCategoryMapping userCategoryMapping = UserCategoryMappingResourceIT.createEntity(em);
        em.persist(userCategoryMapping);
        em.flush();
        userProfile.addUserCategoryMapping(userCategoryMapping);
        userProfileRepository.saveAndFlush(userProfile);
        Long userCategoryMappingId = userCategoryMapping.getId();

        // Get all the userProfileList where userCategoryMapping equals to userCategoryMappingId
        defaultUserProfileShouldBeFound("userCategoryMappingId.equals=" + userCategoryMappingId);

        // Get all the userProfileList where userCategoryMapping equals to userCategoryMappingId + 1
        defaultUserProfileShouldNotBeFound("userCategoryMappingId.equals=" + (userCategoryMappingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserProfileShouldBeFound(String filter) throws Exception {
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].salutation").value(hasItem(DEFAULT_SALUTATION)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(DEFAULT_PROFILE_PICTURE)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restUserProfileMockMvc.perform(get("/api/user-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserProfileShouldNotBeFound(String filter) throws Exception {
        restUserProfileMockMvc.perform(get("/api/user-profiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserProfileMockMvc.perform(get("/api/user-profiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUserProfile() throws Exception {
        // Get the userProfile
        restUserProfileMockMvc.perform(get("/api/user-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Update the userProfile
        UserProfile updatedUserProfile = userProfileRepository.findById(userProfile.getId()).get();
        // Disconnect from session so that the updates on updatedUserProfile are not directly saved in db
        em.detach(updatedUserProfile);
        updatedUserProfile
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .userType(UPDATED_USER_TYPE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(updatedUserProfile);

        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isOk());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
        UserProfile testUserProfile = userProfileList.get(userProfileList.size() - 1);
        assertThat(testUserProfile.getSalutation()).isEqualTo(UPDATED_SALUTATION);
        assertThat(testUserProfile.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testUserProfile.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testUserProfile.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
        assertThat(testUserProfile.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserProfile.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testUserProfile.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testUserProfile.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingUserProfile() throws Exception {
        int databaseSizeBeforeUpdate = userProfileRepository.findAll().size();

        // Create the UserProfile
        UserProfileDTO userProfileDTO = userProfileMapper.toDto(userProfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserProfileMockMvc.perform(put("/api/user-profiles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserProfile in the database
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserProfile() throws Exception {
        // Initialize the database
        userProfileRepository.saveAndFlush(userProfile);

        int databaseSizeBeforeDelete = userProfileRepository.findAll().size();

        // Delete the userProfile
        restUserProfileMockMvc.perform(delete("/api/user-profiles/{id}", userProfile.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserProfile> userProfileList = userProfileRepository.findAll();
        assertThat(userProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
