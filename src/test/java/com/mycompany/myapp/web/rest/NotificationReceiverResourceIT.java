package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.NotificationReceiver;
import com.mycompany.myapp.domain.Notification;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.NotificationReceiverRepository;
import com.mycompany.myapp.service.NotificationReceiverService;
import com.mycompany.myapp.service.dto.NotificationReceiverDTO;
import com.mycompany.myapp.service.mapper.NotificationReceiverMapper;
import com.mycompany.myapp.service.dto.NotificationReceiverCriteria;
import com.mycompany.myapp.service.NotificationReceiverQueryService;

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
 * Integration tests for the {@link NotificationReceiverResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class NotificationReceiverResourceIT {

    private static final Boolean DEFAULT_READ_STATUS = false;
    private static final Boolean UPDATED_READ_STATUS = true;

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NotificationReceiverRepository notificationReceiverRepository;

    @Autowired
    private NotificationReceiverMapper notificationReceiverMapper;

    @Autowired
    private NotificationReceiverService notificationReceiverService;

    @Autowired
    private NotificationReceiverQueryService notificationReceiverQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNotificationReceiverMockMvc;

    private NotificationReceiver notificationReceiver;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationReceiver createEntity(EntityManager em) {
        NotificationReceiver notificationReceiver = new NotificationReceiver()
            .readStatus(DEFAULT_READ_STATUS)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        Notification notification;
        if (TestUtil.findAll(em, Notification.class).isEmpty()) {
            notification = NotificationResourceIT.createEntity(em);
            em.persist(notification);
            em.flush();
        } else {
            notification = TestUtil.findAll(em, Notification.class).get(0);
        }
        notificationReceiver.setNotification(notification);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        notificationReceiver.setUserProfile(userProfile);
        return notificationReceiver;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NotificationReceiver createUpdatedEntity(EntityManager em) {
        NotificationReceiver notificationReceiver = new NotificationReceiver()
            .readStatus(UPDATED_READ_STATUS)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        Notification notification;
        if (TestUtil.findAll(em, Notification.class).isEmpty()) {
            notification = NotificationResourceIT.createUpdatedEntity(em);
            em.persist(notification);
            em.flush();
        } else {
            notification = TestUtil.findAll(em, Notification.class).get(0);
        }
        notificationReceiver.setNotification(notification);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        notificationReceiver.setUserProfile(userProfile);
        return notificationReceiver;
    }

    @BeforeEach
    public void initTest() {
        notificationReceiver = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotificationReceiver() throws Exception {
        int databaseSizeBeforeCreate = notificationReceiverRepository.findAll().size();

        // Create the NotificationReceiver
        NotificationReceiverDTO notificationReceiverDTO = notificationReceiverMapper.toDto(notificationReceiver);
        restNotificationReceiverMockMvc.perform(post("/api/notification-receivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationReceiverDTO)))
            .andExpect(status().isCreated());

        // Validate the NotificationReceiver in the database
        List<NotificationReceiver> notificationReceiverList = notificationReceiverRepository.findAll();
        assertThat(notificationReceiverList).hasSize(databaseSizeBeforeCreate + 1);
        NotificationReceiver testNotificationReceiver = notificationReceiverList.get(notificationReceiverList.size() - 1);
        assertThat(testNotificationReceiver.isReadStatus()).isEqualTo(DEFAULT_READ_STATUS);
        assertThat(testNotificationReceiver.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createNotificationReceiverWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notificationReceiverRepository.findAll().size();

        // Create the NotificationReceiver with an existing ID
        notificationReceiver.setId(1L);
        NotificationReceiverDTO notificationReceiverDTO = notificationReceiverMapper.toDto(notificationReceiver);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotificationReceiverMockMvc.perform(post("/api/notification-receivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationReceiverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NotificationReceiver in the database
        List<NotificationReceiver> notificationReceiverList = notificationReceiverRepository.findAll();
        assertThat(notificationReceiverList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReadStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationReceiverRepository.findAll().size();
        // set the field null
        notificationReceiver.setReadStatus(null);

        // Create the NotificationReceiver, which fails.
        NotificationReceiverDTO notificationReceiverDTO = notificationReceiverMapper.toDto(notificationReceiver);

        restNotificationReceiverMockMvc.perform(post("/api/notification-receivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationReceiverDTO)))
            .andExpect(status().isBadRequest());

        List<NotificationReceiver> notificationReceiverList = notificationReceiverRepository.findAll();
        assertThat(notificationReceiverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = notificationReceiverRepository.findAll().size();
        // set the field null
        notificationReceiver.setUpdatedAt(null);

        // Create the NotificationReceiver, which fails.
        NotificationReceiverDTO notificationReceiverDTO = notificationReceiverMapper.toDto(notificationReceiver);

        restNotificationReceiverMockMvc.perform(post("/api/notification-receivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationReceiverDTO)))
            .andExpect(status().isBadRequest());

        List<NotificationReceiver> notificationReceiverList = notificationReceiverRepository.findAll();
        assertThat(notificationReceiverList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNotificationReceivers() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        // Get all the notificationReceiverList
        restNotificationReceiverMockMvc.perform(get("/api/notification-receivers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationReceiver.getId().intValue())))
            .andExpect(jsonPath("$.[*].readStatus").value(hasItem(DEFAULT_READ_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getNotificationReceiver() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        // Get the notificationReceiver
        restNotificationReceiverMockMvc.perform(get("/api/notification-receivers/{id}", notificationReceiver.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notificationReceiver.getId().intValue()))
            .andExpect(jsonPath("$.readStatus").value(DEFAULT_READ_STATUS.booleanValue()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getNotificationReceiversByIdFiltering() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        Long id = notificationReceiver.getId();

        defaultNotificationReceiverShouldBeFound("id.equals=" + id);
        defaultNotificationReceiverShouldNotBeFound("id.notEquals=" + id);

        defaultNotificationReceiverShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNotificationReceiverShouldNotBeFound("id.greaterThan=" + id);

        defaultNotificationReceiverShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNotificationReceiverShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNotificationReceiversByReadStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        // Get all the notificationReceiverList where readStatus equals to DEFAULT_READ_STATUS
        defaultNotificationReceiverShouldBeFound("readStatus.equals=" + DEFAULT_READ_STATUS);

        // Get all the notificationReceiverList where readStatus equals to UPDATED_READ_STATUS
        defaultNotificationReceiverShouldNotBeFound("readStatus.equals=" + UPDATED_READ_STATUS);
    }

    @Test
    @Transactional
    public void getAllNotificationReceiversByReadStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        // Get all the notificationReceiverList where readStatus not equals to DEFAULT_READ_STATUS
        defaultNotificationReceiverShouldNotBeFound("readStatus.notEquals=" + DEFAULT_READ_STATUS);

        // Get all the notificationReceiverList where readStatus not equals to UPDATED_READ_STATUS
        defaultNotificationReceiverShouldBeFound("readStatus.notEquals=" + UPDATED_READ_STATUS);
    }

    @Test
    @Transactional
    public void getAllNotificationReceiversByReadStatusIsInShouldWork() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        // Get all the notificationReceiverList where readStatus in DEFAULT_READ_STATUS or UPDATED_READ_STATUS
        defaultNotificationReceiverShouldBeFound("readStatus.in=" + DEFAULT_READ_STATUS + "," + UPDATED_READ_STATUS);

        // Get all the notificationReceiverList where readStatus equals to UPDATED_READ_STATUS
        defaultNotificationReceiverShouldNotBeFound("readStatus.in=" + UPDATED_READ_STATUS);
    }

    @Test
    @Transactional
    public void getAllNotificationReceiversByReadStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        // Get all the notificationReceiverList where readStatus is not null
        defaultNotificationReceiverShouldBeFound("readStatus.specified=true");

        // Get all the notificationReceiverList where readStatus is null
        defaultNotificationReceiverShouldNotBeFound("readStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificationReceiversByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        // Get all the notificationReceiverList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultNotificationReceiverShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the notificationReceiverList where updatedAt equals to UPDATED_UPDATED_AT
        defaultNotificationReceiverShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllNotificationReceiversByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        // Get all the notificationReceiverList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultNotificationReceiverShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the notificationReceiverList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultNotificationReceiverShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllNotificationReceiversByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        // Get all the notificationReceiverList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultNotificationReceiverShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the notificationReceiverList where updatedAt equals to UPDATED_UPDATED_AT
        defaultNotificationReceiverShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllNotificationReceiversByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        // Get all the notificationReceiverList where updatedAt is not null
        defaultNotificationReceiverShouldBeFound("updatedAt.specified=true");

        // Get all the notificationReceiverList where updatedAt is null
        defaultNotificationReceiverShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllNotificationReceiversByNotificationIsEqualToSomething() throws Exception {
        // Get already existing entity
        Notification notification = notificationReceiver.getNotification();
        notificationReceiverRepository.saveAndFlush(notificationReceiver);
        Long notificationId = notification.getId();

        // Get all the notificationReceiverList where notification equals to notificationId
        defaultNotificationReceiverShouldBeFound("notificationId.equals=" + notificationId);

        // Get all the notificationReceiverList where notification equals to notificationId + 1
        defaultNotificationReceiverShouldNotBeFound("notificationId.equals=" + (notificationId + 1));
    }


    @Test
    @Transactional
    public void getAllNotificationReceiversByUserProfileIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile userProfile = notificationReceiver.getUserProfile();
        notificationReceiverRepository.saveAndFlush(notificationReceiver);
        Long userProfileId = userProfile.getId();

        // Get all the notificationReceiverList where userProfile equals to userProfileId
        defaultNotificationReceiverShouldBeFound("userProfileId.equals=" + userProfileId);

        // Get all the notificationReceiverList where userProfile equals to userProfileId + 1
        defaultNotificationReceiverShouldNotBeFound("userProfileId.equals=" + (userProfileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNotificationReceiverShouldBeFound(String filter) throws Exception {
        restNotificationReceiverMockMvc.perform(get("/api/notification-receivers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notificationReceiver.getId().intValue())))
            .andExpect(jsonPath("$.[*].readStatus").value(hasItem(DEFAULT_READ_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restNotificationReceiverMockMvc.perform(get("/api/notification-receivers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNotificationReceiverShouldNotBeFound(String filter) throws Exception {
        restNotificationReceiverMockMvc.perform(get("/api/notification-receivers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNotificationReceiverMockMvc.perform(get("/api/notification-receivers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNotificationReceiver() throws Exception {
        // Get the notificationReceiver
        restNotificationReceiverMockMvc.perform(get("/api/notification-receivers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotificationReceiver() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        int databaseSizeBeforeUpdate = notificationReceiverRepository.findAll().size();

        // Update the notificationReceiver
        NotificationReceiver updatedNotificationReceiver = notificationReceiverRepository.findById(notificationReceiver.getId()).get();
        // Disconnect from session so that the updates on updatedNotificationReceiver are not directly saved in db
        em.detach(updatedNotificationReceiver);
        updatedNotificationReceiver
            .readStatus(UPDATED_READ_STATUS)
            .updatedAt(UPDATED_UPDATED_AT);
        NotificationReceiverDTO notificationReceiverDTO = notificationReceiverMapper.toDto(updatedNotificationReceiver);

        restNotificationReceiverMockMvc.perform(put("/api/notification-receivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationReceiverDTO)))
            .andExpect(status().isOk());

        // Validate the NotificationReceiver in the database
        List<NotificationReceiver> notificationReceiverList = notificationReceiverRepository.findAll();
        assertThat(notificationReceiverList).hasSize(databaseSizeBeforeUpdate);
        NotificationReceiver testNotificationReceiver = notificationReceiverList.get(notificationReceiverList.size() - 1);
        assertThat(testNotificationReceiver.isReadStatus()).isEqualTo(UPDATED_READ_STATUS);
        assertThat(testNotificationReceiver.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingNotificationReceiver() throws Exception {
        int databaseSizeBeforeUpdate = notificationReceiverRepository.findAll().size();

        // Create the NotificationReceiver
        NotificationReceiverDTO notificationReceiverDTO = notificationReceiverMapper.toDto(notificationReceiver);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNotificationReceiverMockMvc.perform(put("/api/notification-receivers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(notificationReceiverDTO)))
            .andExpect(status().isBadRequest());

        // Validate the NotificationReceiver in the database
        List<NotificationReceiver> notificationReceiverList = notificationReceiverRepository.findAll();
        assertThat(notificationReceiverList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotificationReceiver() throws Exception {
        // Initialize the database
        notificationReceiverRepository.saveAndFlush(notificationReceiver);

        int databaseSizeBeforeDelete = notificationReceiverRepository.findAll().size();

        // Delete the notificationReceiver
        restNotificationReceiverMockMvc.perform(delete("/api/notification-receivers/{id}", notificationReceiver.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NotificationReceiver> notificationReceiverList = notificationReceiverRepository.findAll();
        assertThat(notificationReceiverList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
