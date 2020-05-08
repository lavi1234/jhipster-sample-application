package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.EmailTracking;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.EmailTrackingRepository;
import com.mycompany.myapp.service.EmailTrackingService;
import com.mycompany.myapp.service.dto.EmailTrackingDTO;
import com.mycompany.myapp.service.mapper.EmailTrackingMapper;
import com.mycompany.myapp.service.dto.EmailTrackingCriteria;
import com.mycompany.myapp.service.EmailTrackingQueryService;

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
 * Integration tests for the {@link EmailTrackingResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EmailTrackingResourceIT {

    private static final String DEFAULT_TO_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_TO_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmailTrackingRepository emailTrackingRepository;

    @Autowired
    private EmailTrackingMapper emailTrackingMapper;

    @Autowired
    private EmailTrackingService emailTrackingService;

    @Autowired
    private EmailTrackingQueryService emailTrackingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmailTrackingMockMvc;

    private EmailTracking emailTracking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTracking createEntity(EntityManager em) {
        EmailTracking emailTracking = new EmailTracking()
            .toEmail(DEFAULT_TO_EMAIL)
            .subject(DEFAULT_SUBJECT)
            .message(DEFAULT_MESSAGE)
            .type(DEFAULT_TYPE)
            .createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        emailTracking.setReceiver(userProfile);
        // Add required entity
        emailTracking.setCreatedBy(userProfile);
        return emailTracking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailTracking createUpdatedEntity(EntityManager em) {
        EmailTracking emailTracking = new EmailTracking()
            .toEmail(UPDATED_TO_EMAIL)
            .subject(UPDATED_SUBJECT)
            .message(UPDATED_MESSAGE)
            .type(UPDATED_TYPE)
            .createdAt(UPDATED_CREATED_AT);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        emailTracking.setReceiver(userProfile);
        // Add required entity
        emailTracking.setCreatedBy(userProfile);
        return emailTracking;
    }

    @BeforeEach
    public void initTest() {
        emailTracking = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmailTracking() throws Exception {
        int databaseSizeBeforeCreate = emailTrackingRepository.findAll().size();

        // Create the EmailTracking
        EmailTrackingDTO emailTrackingDTO = emailTrackingMapper.toDto(emailTracking);
        restEmailTrackingMockMvc.perform(post("/api/email-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTrackingDTO)))
            .andExpect(status().isCreated());

        // Validate the EmailTracking in the database
        List<EmailTracking> emailTrackingList = emailTrackingRepository.findAll();
        assertThat(emailTrackingList).hasSize(databaseSizeBeforeCreate + 1);
        EmailTracking testEmailTracking = emailTrackingList.get(emailTrackingList.size() - 1);
        assertThat(testEmailTracking.getToEmail()).isEqualTo(DEFAULT_TO_EMAIL);
        assertThat(testEmailTracking.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testEmailTracking.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testEmailTracking.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEmailTracking.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createEmailTrackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emailTrackingRepository.findAll().size();

        // Create the EmailTracking with an existing ID
        emailTracking.setId(1L);
        EmailTrackingDTO emailTrackingDTO = emailTrackingMapper.toDto(emailTracking);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmailTrackingMockMvc.perform(post("/api/email-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTrackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailTracking in the database
        List<EmailTracking> emailTrackingList = emailTrackingRepository.findAll();
        assertThat(emailTrackingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkToEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTrackingRepository.findAll().size();
        // set the field null
        emailTracking.setToEmail(null);

        // Create the EmailTracking, which fails.
        EmailTrackingDTO emailTrackingDTO = emailTrackingMapper.toDto(emailTracking);

        restEmailTrackingMockMvc.perform(post("/api/email-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<EmailTracking> emailTrackingList = emailTrackingRepository.findAll();
        assertThat(emailTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTrackingRepository.findAll().size();
        // set the field null
        emailTracking.setType(null);

        // Create the EmailTracking, which fails.
        EmailTrackingDTO emailTrackingDTO = emailTrackingMapper.toDto(emailTracking);

        restEmailTrackingMockMvc.perform(post("/api/email-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<EmailTracking> emailTrackingList = emailTrackingRepository.findAll();
        assertThat(emailTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = emailTrackingRepository.findAll().size();
        // set the field null
        emailTracking.setCreatedAt(null);

        // Create the EmailTracking, which fails.
        EmailTrackingDTO emailTrackingDTO = emailTrackingMapper.toDto(emailTracking);

        restEmailTrackingMockMvc.perform(post("/api/email-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<EmailTracking> emailTrackingList = emailTrackingRepository.findAll();
        assertThat(emailTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmailTrackings() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList
        restEmailTrackingMockMvc.perform(get("/api/email-trackings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].toEmail").value(hasItem(DEFAULT_TO_EMAIL)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getEmailTracking() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get the emailTracking
        restEmailTrackingMockMvc.perform(get("/api/email-trackings/{id}", emailTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emailTracking.getId().intValue()))
            .andExpect(jsonPath("$.toEmail").value(DEFAULT_TO_EMAIL))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getEmailTrackingsByIdFiltering() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        Long id = emailTracking.getId();

        defaultEmailTrackingShouldBeFound("id.equals=" + id);
        defaultEmailTrackingShouldNotBeFound("id.notEquals=" + id);

        defaultEmailTrackingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmailTrackingShouldNotBeFound("id.greaterThan=" + id);

        defaultEmailTrackingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmailTrackingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmailTrackingsByToEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where toEmail equals to DEFAULT_TO_EMAIL
        defaultEmailTrackingShouldBeFound("toEmail.equals=" + DEFAULT_TO_EMAIL);

        // Get all the emailTrackingList where toEmail equals to UPDATED_TO_EMAIL
        defaultEmailTrackingShouldNotBeFound("toEmail.equals=" + UPDATED_TO_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByToEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where toEmail not equals to DEFAULT_TO_EMAIL
        defaultEmailTrackingShouldNotBeFound("toEmail.notEquals=" + DEFAULT_TO_EMAIL);

        // Get all the emailTrackingList where toEmail not equals to UPDATED_TO_EMAIL
        defaultEmailTrackingShouldBeFound("toEmail.notEquals=" + UPDATED_TO_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByToEmailIsInShouldWork() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where toEmail in DEFAULT_TO_EMAIL or UPDATED_TO_EMAIL
        defaultEmailTrackingShouldBeFound("toEmail.in=" + DEFAULT_TO_EMAIL + "," + UPDATED_TO_EMAIL);

        // Get all the emailTrackingList where toEmail equals to UPDATED_TO_EMAIL
        defaultEmailTrackingShouldNotBeFound("toEmail.in=" + UPDATED_TO_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByToEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where toEmail is not null
        defaultEmailTrackingShouldBeFound("toEmail.specified=true");

        // Get all the emailTrackingList where toEmail is null
        defaultEmailTrackingShouldNotBeFound("toEmail.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmailTrackingsByToEmailContainsSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where toEmail contains DEFAULT_TO_EMAIL
        defaultEmailTrackingShouldBeFound("toEmail.contains=" + DEFAULT_TO_EMAIL);

        // Get all the emailTrackingList where toEmail contains UPDATED_TO_EMAIL
        defaultEmailTrackingShouldNotBeFound("toEmail.contains=" + UPDATED_TO_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByToEmailNotContainsSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where toEmail does not contain DEFAULT_TO_EMAIL
        defaultEmailTrackingShouldNotBeFound("toEmail.doesNotContain=" + DEFAULT_TO_EMAIL);

        // Get all the emailTrackingList where toEmail does not contain UPDATED_TO_EMAIL
        defaultEmailTrackingShouldBeFound("toEmail.doesNotContain=" + UPDATED_TO_EMAIL);
    }


    @Test
    @Transactional
    public void getAllEmailTrackingsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where subject equals to DEFAULT_SUBJECT
        defaultEmailTrackingShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the emailTrackingList where subject equals to UPDATED_SUBJECT
        defaultEmailTrackingShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsBySubjectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where subject not equals to DEFAULT_SUBJECT
        defaultEmailTrackingShouldNotBeFound("subject.notEquals=" + DEFAULT_SUBJECT);

        // Get all the emailTrackingList where subject not equals to UPDATED_SUBJECT
        defaultEmailTrackingShouldBeFound("subject.notEquals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultEmailTrackingShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the emailTrackingList where subject equals to UPDATED_SUBJECT
        defaultEmailTrackingShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where subject is not null
        defaultEmailTrackingShouldBeFound("subject.specified=true");

        // Get all the emailTrackingList where subject is null
        defaultEmailTrackingShouldNotBeFound("subject.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmailTrackingsBySubjectContainsSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where subject contains DEFAULT_SUBJECT
        defaultEmailTrackingShouldBeFound("subject.contains=" + DEFAULT_SUBJECT);

        // Get all the emailTrackingList where subject contains UPDATED_SUBJECT
        defaultEmailTrackingShouldNotBeFound("subject.contains=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsBySubjectNotContainsSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where subject does not contain DEFAULT_SUBJECT
        defaultEmailTrackingShouldNotBeFound("subject.doesNotContain=" + DEFAULT_SUBJECT);

        // Get all the emailTrackingList where subject does not contain UPDATED_SUBJECT
        defaultEmailTrackingShouldBeFound("subject.doesNotContain=" + UPDATED_SUBJECT);
    }


    @Test
    @Transactional
    public void getAllEmailTrackingsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where message equals to DEFAULT_MESSAGE
        defaultEmailTrackingShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the emailTrackingList where message equals to UPDATED_MESSAGE
        defaultEmailTrackingShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where message not equals to DEFAULT_MESSAGE
        defaultEmailTrackingShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the emailTrackingList where message not equals to UPDATED_MESSAGE
        defaultEmailTrackingShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultEmailTrackingShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the emailTrackingList where message equals to UPDATED_MESSAGE
        defaultEmailTrackingShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where message is not null
        defaultEmailTrackingShouldBeFound("message.specified=true");

        // Get all the emailTrackingList where message is null
        defaultEmailTrackingShouldNotBeFound("message.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmailTrackingsByMessageContainsSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where message contains DEFAULT_MESSAGE
        defaultEmailTrackingShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the emailTrackingList where message contains UPDATED_MESSAGE
        defaultEmailTrackingShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where message does not contain DEFAULT_MESSAGE
        defaultEmailTrackingShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the emailTrackingList where message does not contain UPDATED_MESSAGE
        defaultEmailTrackingShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllEmailTrackingsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where type equals to DEFAULT_TYPE
        defaultEmailTrackingShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the emailTrackingList where type equals to UPDATED_TYPE
        defaultEmailTrackingShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where type not equals to DEFAULT_TYPE
        defaultEmailTrackingShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the emailTrackingList where type not equals to UPDATED_TYPE
        defaultEmailTrackingShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultEmailTrackingShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the emailTrackingList where type equals to UPDATED_TYPE
        defaultEmailTrackingShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where type is not null
        defaultEmailTrackingShouldBeFound("type.specified=true");

        // Get all the emailTrackingList where type is null
        defaultEmailTrackingShouldNotBeFound("type.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmailTrackingsByTypeContainsSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where type contains DEFAULT_TYPE
        defaultEmailTrackingShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the emailTrackingList where type contains UPDATED_TYPE
        defaultEmailTrackingShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where type does not contain DEFAULT_TYPE
        defaultEmailTrackingShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the emailTrackingList where type does not contain UPDATED_TYPE
        defaultEmailTrackingShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }


    @Test
    @Transactional
    public void getAllEmailTrackingsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where createdAt equals to DEFAULT_CREATED_AT
        defaultEmailTrackingShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the emailTrackingList where createdAt equals to UPDATED_CREATED_AT
        defaultEmailTrackingShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where createdAt not equals to DEFAULT_CREATED_AT
        defaultEmailTrackingShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the emailTrackingList where createdAt not equals to UPDATED_CREATED_AT
        defaultEmailTrackingShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultEmailTrackingShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the emailTrackingList where createdAt equals to UPDATED_CREATED_AT
        defaultEmailTrackingShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        // Get all the emailTrackingList where createdAt is not null
        defaultEmailTrackingShouldBeFound("createdAt.specified=true");

        // Get all the emailTrackingList where createdAt is null
        defaultEmailTrackingShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmailTrackingsByReceiverIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile receiver = emailTracking.getReceiver();
        emailTrackingRepository.saveAndFlush(emailTracking);
        Long receiverId = receiver.getId();

        // Get all the emailTrackingList where receiver equals to receiverId
        defaultEmailTrackingShouldBeFound("receiverId.equals=" + receiverId);

        // Get all the emailTrackingList where receiver equals to receiverId + 1
        defaultEmailTrackingShouldNotBeFound("receiverId.equals=" + (receiverId + 1));
    }


    @Test
    @Transactional
    public void getAllEmailTrackingsByCreatedByIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile createdBy = emailTracking.getCreatedBy();
        emailTrackingRepository.saveAndFlush(emailTracking);
        Long createdById = createdBy.getId();

        // Get all the emailTrackingList where createdBy equals to createdById
        defaultEmailTrackingShouldBeFound("createdById.equals=" + createdById);

        // Get all the emailTrackingList where createdBy equals to createdById + 1
        defaultEmailTrackingShouldNotBeFound("createdById.equals=" + (createdById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmailTrackingShouldBeFound(String filter) throws Exception {
        restEmailTrackingMockMvc.perform(get("/api/email-trackings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].toEmail").value(hasItem(DEFAULT_TO_EMAIL)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));

        // Check, that the count call also returns 1
        restEmailTrackingMockMvc.perform(get("/api/email-trackings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmailTrackingShouldNotBeFound(String filter) throws Exception {
        restEmailTrackingMockMvc.perform(get("/api/email-trackings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmailTrackingMockMvc.perform(get("/api/email-trackings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmailTracking() throws Exception {
        // Get the emailTracking
        restEmailTrackingMockMvc.perform(get("/api/email-trackings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmailTracking() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        int databaseSizeBeforeUpdate = emailTrackingRepository.findAll().size();

        // Update the emailTracking
        EmailTracking updatedEmailTracking = emailTrackingRepository.findById(emailTracking.getId()).get();
        // Disconnect from session so that the updates on updatedEmailTracking are not directly saved in db
        em.detach(updatedEmailTracking);
        updatedEmailTracking
            .toEmail(UPDATED_TO_EMAIL)
            .subject(UPDATED_SUBJECT)
            .message(UPDATED_MESSAGE)
            .type(UPDATED_TYPE)
            .createdAt(UPDATED_CREATED_AT);
        EmailTrackingDTO emailTrackingDTO = emailTrackingMapper.toDto(updatedEmailTracking);

        restEmailTrackingMockMvc.perform(put("/api/email-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTrackingDTO)))
            .andExpect(status().isOk());

        // Validate the EmailTracking in the database
        List<EmailTracking> emailTrackingList = emailTrackingRepository.findAll();
        assertThat(emailTrackingList).hasSize(databaseSizeBeforeUpdate);
        EmailTracking testEmailTracking = emailTrackingList.get(emailTrackingList.size() - 1);
        assertThat(testEmailTracking.getToEmail()).isEqualTo(UPDATED_TO_EMAIL);
        assertThat(testEmailTracking.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testEmailTracking.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testEmailTracking.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEmailTracking.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingEmailTracking() throws Exception {
        int databaseSizeBeforeUpdate = emailTrackingRepository.findAll().size();

        // Create the EmailTracking
        EmailTrackingDTO emailTrackingDTO = emailTrackingMapper.toDto(emailTracking);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmailTrackingMockMvc.perform(put("/api/email-trackings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emailTrackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmailTracking in the database
        List<EmailTracking> emailTrackingList = emailTrackingRepository.findAll();
        assertThat(emailTrackingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmailTracking() throws Exception {
        // Initialize the database
        emailTrackingRepository.saveAndFlush(emailTracking);

        int databaseSizeBeforeDelete = emailTrackingRepository.findAll().size();

        // Delete the emailTracking
        restEmailTrackingMockMvc.perform(delete("/api/email-trackings/{id}", emailTracking.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmailTracking> emailTrackingList = emailTrackingRepository.findAll();
        assertThat(emailTrackingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
