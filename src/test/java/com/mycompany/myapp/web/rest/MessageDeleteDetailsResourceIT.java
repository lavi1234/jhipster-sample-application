package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.MessageDeleteDetails;
import com.mycompany.myapp.domain.Message;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.MessageDeleteDetailsRepository;
import com.mycompany.myapp.service.MessageDeleteDetailsService;
import com.mycompany.myapp.service.dto.MessageDeleteDetailsDTO;
import com.mycompany.myapp.service.mapper.MessageDeleteDetailsMapper;
import com.mycompany.myapp.service.dto.MessageDeleteDetailsCriteria;
import com.mycompany.myapp.service.MessageDeleteDetailsQueryService;

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
 * Integration tests for the {@link MessageDeleteDetailsResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MessageDeleteDetailsResourceIT {

    private static final Instant DEFAULT_DELETED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MessageDeleteDetailsRepository messageDeleteDetailsRepository;

    @Autowired
    private MessageDeleteDetailsMapper messageDeleteDetailsMapper;

    @Autowired
    private MessageDeleteDetailsService messageDeleteDetailsService;

    @Autowired
    private MessageDeleteDetailsQueryService messageDeleteDetailsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageDeleteDetailsMockMvc;

    private MessageDeleteDetails messageDeleteDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageDeleteDetails createEntity(EntityManager em) {
        MessageDeleteDetails messageDeleteDetails = new MessageDeleteDetails()
            .deletedAt(DEFAULT_DELETED_AT);
        // Add required entity
        Message message;
        if (TestUtil.findAll(em, Message.class).isEmpty()) {
            message = MessageResourceIT.createEntity(em);
            em.persist(message);
            em.flush();
        } else {
            message = TestUtil.findAll(em, Message.class).get(0);
        }
        messageDeleteDetails.setMessage(message);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        messageDeleteDetails.setDeletedBy(userProfile);
        return messageDeleteDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageDeleteDetails createUpdatedEntity(EntityManager em) {
        MessageDeleteDetails messageDeleteDetails = new MessageDeleteDetails()
            .deletedAt(UPDATED_DELETED_AT);
        // Add required entity
        Message message;
        if (TestUtil.findAll(em, Message.class).isEmpty()) {
            message = MessageResourceIT.createUpdatedEntity(em);
            em.persist(message);
            em.flush();
        } else {
            message = TestUtil.findAll(em, Message.class).get(0);
        }
        messageDeleteDetails.setMessage(message);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        messageDeleteDetails.setDeletedBy(userProfile);
        return messageDeleteDetails;
    }

    @BeforeEach
    public void initTest() {
        messageDeleteDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageDeleteDetails() throws Exception {
        int databaseSizeBeforeCreate = messageDeleteDetailsRepository.findAll().size();

        // Create the MessageDeleteDetails
        MessageDeleteDetailsDTO messageDeleteDetailsDTO = messageDeleteDetailsMapper.toDto(messageDeleteDetails);
        restMessageDeleteDetailsMockMvc.perform(post("/api/message-delete-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDeleteDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the MessageDeleteDetails in the database
        List<MessageDeleteDetails> messageDeleteDetailsList = messageDeleteDetailsRepository.findAll();
        assertThat(messageDeleteDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        MessageDeleteDetails testMessageDeleteDetails = messageDeleteDetailsList.get(messageDeleteDetailsList.size() - 1);
        assertThat(testMessageDeleteDetails.getDeletedAt()).isEqualTo(DEFAULT_DELETED_AT);
    }

    @Test
    @Transactional
    public void createMessageDeleteDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageDeleteDetailsRepository.findAll().size();

        // Create the MessageDeleteDetails with an existing ID
        messageDeleteDetails.setId(1L);
        MessageDeleteDetailsDTO messageDeleteDetailsDTO = messageDeleteDetailsMapper.toDto(messageDeleteDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageDeleteDetailsMockMvc.perform(post("/api/message-delete-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDeleteDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MessageDeleteDetails in the database
        List<MessageDeleteDetails> messageDeleteDetailsList = messageDeleteDetailsRepository.findAll();
        assertThat(messageDeleteDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDeletedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageDeleteDetailsRepository.findAll().size();
        // set the field null
        messageDeleteDetails.setDeletedAt(null);

        // Create the MessageDeleteDetails, which fails.
        MessageDeleteDetailsDTO messageDeleteDetailsDTO = messageDeleteDetailsMapper.toDto(messageDeleteDetails);

        restMessageDeleteDetailsMockMvc.perform(post("/api/message-delete-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDeleteDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<MessageDeleteDetails> messageDeleteDetailsList = messageDeleteDetailsRepository.findAll();
        assertThat(messageDeleteDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessageDeleteDetails() throws Exception {
        // Initialize the database
        messageDeleteDetailsRepository.saveAndFlush(messageDeleteDetails);

        // Get all the messageDeleteDetailsList
        restMessageDeleteDetailsMockMvc.perform(get("/api/message-delete-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageDeleteDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getMessageDeleteDetails() throws Exception {
        // Initialize the database
        messageDeleteDetailsRepository.saveAndFlush(messageDeleteDetails);

        // Get the messageDeleteDetails
        restMessageDeleteDetailsMockMvc.perform(get("/api/message-delete-details/{id}", messageDeleteDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(messageDeleteDetails.getId().intValue()))
            .andExpect(jsonPath("$.deletedAt").value(DEFAULT_DELETED_AT.toString()));
    }


    @Test
    @Transactional
    public void getMessageDeleteDetailsByIdFiltering() throws Exception {
        // Initialize the database
        messageDeleteDetailsRepository.saveAndFlush(messageDeleteDetails);

        Long id = messageDeleteDetails.getId();

        defaultMessageDeleteDetailsShouldBeFound("id.equals=" + id);
        defaultMessageDeleteDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultMessageDeleteDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMessageDeleteDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultMessageDeleteDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMessageDeleteDetailsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMessageDeleteDetailsByDeletedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        messageDeleteDetailsRepository.saveAndFlush(messageDeleteDetails);

        // Get all the messageDeleteDetailsList where deletedAt equals to DEFAULT_DELETED_AT
        defaultMessageDeleteDetailsShouldBeFound("deletedAt.equals=" + DEFAULT_DELETED_AT);

        // Get all the messageDeleteDetailsList where deletedAt equals to UPDATED_DELETED_AT
        defaultMessageDeleteDetailsShouldNotBeFound("deletedAt.equals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void getAllMessageDeleteDetailsByDeletedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageDeleteDetailsRepository.saveAndFlush(messageDeleteDetails);

        // Get all the messageDeleteDetailsList where deletedAt not equals to DEFAULT_DELETED_AT
        defaultMessageDeleteDetailsShouldNotBeFound("deletedAt.notEquals=" + DEFAULT_DELETED_AT);

        // Get all the messageDeleteDetailsList where deletedAt not equals to UPDATED_DELETED_AT
        defaultMessageDeleteDetailsShouldBeFound("deletedAt.notEquals=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void getAllMessageDeleteDetailsByDeletedAtIsInShouldWork() throws Exception {
        // Initialize the database
        messageDeleteDetailsRepository.saveAndFlush(messageDeleteDetails);

        // Get all the messageDeleteDetailsList where deletedAt in DEFAULT_DELETED_AT or UPDATED_DELETED_AT
        defaultMessageDeleteDetailsShouldBeFound("deletedAt.in=" + DEFAULT_DELETED_AT + "," + UPDATED_DELETED_AT);

        // Get all the messageDeleteDetailsList where deletedAt equals to UPDATED_DELETED_AT
        defaultMessageDeleteDetailsShouldNotBeFound("deletedAt.in=" + UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void getAllMessageDeleteDetailsByDeletedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageDeleteDetailsRepository.saveAndFlush(messageDeleteDetails);

        // Get all the messageDeleteDetailsList where deletedAt is not null
        defaultMessageDeleteDetailsShouldBeFound("deletedAt.specified=true");

        // Get all the messageDeleteDetailsList where deletedAt is null
        defaultMessageDeleteDetailsShouldNotBeFound("deletedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessageDeleteDetailsByMessageIsEqualToSomething() throws Exception {
        // Get already existing entity
        Message message = messageDeleteDetails.getMessage();
        messageDeleteDetailsRepository.saveAndFlush(messageDeleteDetails);
        Long messageId = message.getId();

        // Get all the messageDeleteDetailsList where message equals to messageId
        defaultMessageDeleteDetailsShouldBeFound("messageId.equals=" + messageId);

        // Get all the messageDeleteDetailsList where message equals to messageId + 1
        defaultMessageDeleteDetailsShouldNotBeFound("messageId.equals=" + (messageId + 1));
    }


    @Test
    @Transactional
    public void getAllMessageDeleteDetailsByDeletedByIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile deletedBy = messageDeleteDetails.getDeletedBy();
        messageDeleteDetailsRepository.saveAndFlush(messageDeleteDetails);
        Long deletedById = deletedBy.getId();

        // Get all the messageDeleteDetailsList where deletedBy equals to deletedById
        defaultMessageDeleteDetailsShouldBeFound("deletedById.equals=" + deletedById);

        // Get all the messageDeleteDetailsList where deletedBy equals to deletedById + 1
        defaultMessageDeleteDetailsShouldNotBeFound("deletedById.equals=" + (deletedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMessageDeleteDetailsShouldBeFound(String filter) throws Exception {
        restMessageDeleteDetailsMockMvc.perform(get("/api/message-delete-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageDeleteDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].deletedAt").value(hasItem(DEFAULT_DELETED_AT.toString())));

        // Check, that the count call also returns 1
        restMessageDeleteDetailsMockMvc.perform(get("/api/message-delete-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMessageDeleteDetailsShouldNotBeFound(String filter) throws Exception {
        restMessageDeleteDetailsMockMvc.perform(get("/api/message-delete-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMessageDeleteDetailsMockMvc.perform(get("/api/message-delete-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMessageDeleteDetails() throws Exception {
        // Get the messageDeleteDetails
        restMessageDeleteDetailsMockMvc.perform(get("/api/message-delete-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageDeleteDetails() throws Exception {
        // Initialize the database
        messageDeleteDetailsRepository.saveAndFlush(messageDeleteDetails);

        int databaseSizeBeforeUpdate = messageDeleteDetailsRepository.findAll().size();

        // Update the messageDeleteDetails
        MessageDeleteDetails updatedMessageDeleteDetails = messageDeleteDetailsRepository.findById(messageDeleteDetails.getId()).get();
        // Disconnect from session so that the updates on updatedMessageDeleteDetails are not directly saved in db
        em.detach(updatedMessageDeleteDetails);
        updatedMessageDeleteDetails
            .deletedAt(UPDATED_DELETED_AT);
        MessageDeleteDetailsDTO messageDeleteDetailsDTO = messageDeleteDetailsMapper.toDto(updatedMessageDeleteDetails);

        restMessageDeleteDetailsMockMvc.perform(put("/api/message-delete-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDeleteDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the MessageDeleteDetails in the database
        List<MessageDeleteDetails> messageDeleteDetailsList = messageDeleteDetailsRepository.findAll();
        assertThat(messageDeleteDetailsList).hasSize(databaseSizeBeforeUpdate);
        MessageDeleteDetails testMessageDeleteDetails = messageDeleteDetailsList.get(messageDeleteDetailsList.size() - 1);
        assertThat(testMessageDeleteDetails.getDeletedAt()).isEqualTo(UPDATED_DELETED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageDeleteDetails() throws Exception {
        int databaseSizeBeforeUpdate = messageDeleteDetailsRepository.findAll().size();

        // Create the MessageDeleteDetails
        MessageDeleteDetailsDTO messageDeleteDetailsDTO = messageDeleteDetailsMapper.toDto(messageDeleteDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageDeleteDetailsMockMvc.perform(put("/api/message-delete-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDeleteDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MessageDeleteDetails in the database
        List<MessageDeleteDetails> messageDeleteDetailsList = messageDeleteDetailsRepository.findAll();
        assertThat(messageDeleteDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMessageDeleteDetails() throws Exception {
        // Initialize the database
        messageDeleteDetailsRepository.saveAndFlush(messageDeleteDetails);

        int databaseSizeBeforeDelete = messageDeleteDetailsRepository.findAll().size();

        // Delete the messageDeleteDetails
        restMessageDeleteDetailsMockMvc.perform(delete("/api/message-delete-details/{id}", messageDeleteDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MessageDeleteDetails> messageDeleteDetailsList = messageDeleteDetailsRepository.findAll();
        assertThat(messageDeleteDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
