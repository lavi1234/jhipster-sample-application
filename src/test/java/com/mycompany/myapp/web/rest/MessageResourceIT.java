package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Message;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.domain.Enquiry;
import com.mycompany.myapp.domain.Order;
import com.mycompany.myapp.domain.Offer;
import com.mycompany.myapp.domain.Message;
import com.mycompany.myapp.repository.MessageRepository;
import com.mycompany.myapp.service.MessageService;
import com.mycompany.myapp.service.dto.MessageDTO;
import com.mycompany.myapp.service.mapper.MessageMapper;
import com.mycompany.myapp.service.dto.MessageCriteria;
import com.mycompany.myapp.service.MessageQueryService;

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
 * Integration tests for the {@link MessageResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MessageResourceIT {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_API_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_API_RESPONSE = "BBBBBBBBBB";

    private static final String DEFAULT_DISCUSSION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_DISCUSSION_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_READ_STATUS = false;
    private static final Boolean UPDATED_READ_STATUS = true;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageQueryService messageQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMessageMockMvc;

    private Message message;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Message createEntity(EntityManager em) {
        Message message = new Message()
            .subject(DEFAULT_SUBJECT)
            .message(DEFAULT_MESSAGE)
            .createdAt(DEFAULT_CREATED_AT)
            .apiResponse(DEFAULT_API_RESPONSE)
            .discussionType(DEFAULT_DISCUSSION_TYPE)
            .readStatus(DEFAULT_READ_STATUS);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        message.setFrom(userProfile);
        // Add required entity
        message.setTo(userProfile);
        // Add required entity
        message.setCreatedBy(userProfile);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        message.setEnquiry(enquiry);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createEntity(em);
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        message.setOrder(order);
        // Add required entity
        Offer offer;
        if (TestUtil.findAll(em, Offer.class).isEmpty()) {
            offer = OfferResourceIT.createEntity(em);
            em.persist(offer);
            em.flush();
        } else {
            offer = TestUtil.findAll(em, Offer.class).get(0);
        }
        message.setOffer(offer);
        return message;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Message createUpdatedEntity(EntityManager em) {
        Message message = new Message()
            .subject(UPDATED_SUBJECT)
            .message(UPDATED_MESSAGE)
            .createdAt(UPDATED_CREATED_AT)
            .apiResponse(UPDATED_API_RESPONSE)
            .discussionType(UPDATED_DISCUSSION_TYPE)
            .readStatus(UPDATED_READ_STATUS);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        message.setFrom(userProfile);
        // Add required entity
        message.setTo(userProfile);
        // Add required entity
        message.setCreatedBy(userProfile);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createUpdatedEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        message.setEnquiry(enquiry);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createUpdatedEntity(em);
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        message.setOrder(order);
        // Add required entity
        Offer offer;
        if (TestUtil.findAll(em, Offer.class).isEmpty()) {
            offer = OfferResourceIT.createUpdatedEntity(em);
            em.persist(offer);
            em.flush();
        } else {
            offer = TestUtil.findAll(em, Offer.class).get(0);
        }
        message.setOffer(offer);
        return message;
    }

    @BeforeEach
    public void initTest() {
        message = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessage() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate + 1);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testMessage.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testMessage.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testMessage.getApiResponse()).isEqualTo(DEFAULT_API_RESPONSE);
        assertThat(testMessage.getDiscussionType()).isEqualTo(DEFAULT_DISCUSSION_TYPE);
        assertThat(testMessage.isReadStatus()).isEqualTo(DEFAULT_READ_STATUS);
    }

    @Test
    @Transactional
    public void createMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // Create the Message with an existing ID
        message.setId(1L);
        MessageDTO messageDTO = messageMapper.toDto(message);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageMockMvc.perform(post("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSubjectIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setSubject(null);

        // Create the Message, which fails.
        MessageDTO messageDTO = messageMapper.toDto(message);

        restMessageMockMvc.perform(post("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setCreatedAt(null);

        // Create the Message, which fails.
        MessageDTO messageDTO = messageMapper.toDto(message);

        restMessageMockMvc.perform(post("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscussionTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageRepository.findAll().size();
        // set the field null
        message.setDiscussionType(null);

        // Create the Message, which fails.
        MessageDTO messageDTO = messageMapper.toDto(message);

        restMessageMockMvc.perform(post("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessages() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList
        restMessageMockMvc.perform(get("/api/messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].apiResponse").value(hasItem(DEFAULT_API_RESPONSE)))
            .andExpect(jsonPath("$.[*].discussionType").value(hasItem(DEFAULT_DISCUSSION_TYPE)))
            .andExpect(jsonPath("$.[*].readStatus").value(hasItem(DEFAULT_READ_STATUS.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(message.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.apiResponse").value(DEFAULT_API_RESPONSE))
            .andExpect(jsonPath("$.discussionType").value(DEFAULT_DISCUSSION_TYPE))
            .andExpect(jsonPath("$.readStatus").value(DEFAULT_READ_STATUS.booleanValue()));
    }


    @Test
    @Transactional
    public void getMessagesByIdFiltering() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        Long id = message.getId();

        defaultMessageShouldBeFound("id.equals=" + id);
        defaultMessageShouldNotBeFound("id.notEquals=" + id);

        defaultMessageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMessageShouldNotBeFound("id.greaterThan=" + id);

        defaultMessageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMessageShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMessagesBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where subject equals to DEFAULT_SUBJECT
        defaultMessageShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the messageList where subject equals to UPDATED_SUBJECT
        defaultMessageShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllMessagesBySubjectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where subject not equals to DEFAULT_SUBJECT
        defaultMessageShouldNotBeFound("subject.notEquals=" + DEFAULT_SUBJECT);

        // Get all the messageList where subject not equals to UPDATED_SUBJECT
        defaultMessageShouldBeFound("subject.notEquals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllMessagesBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultMessageShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the messageList where subject equals to UPDATED_SUBJECT
        defaultMessageShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllMessagesBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where subject is not null
        defaultMessageShouldBeFound("subject.specified=true");

        // Get all the messageList where subject is null
        defaultMessageShouldNotBeFound("subject.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessagesBySubjectContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where subject contains DEFAULT_SUBJECT
        defaultMessageShouldBeFound("subject.contains=" + DEFAULT_SUBJECT);

        // Get all the messageList where subject contains UPDATED_SUBJECT
        defaultMessageShouldNotBeFound("subject.contains=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllMessagesBySubjectNotContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where subject does not contain DEFAULT_SUBJECT
        defaultMessageShouldNotBeFound("subject.doesNotContain=" + DEFAULT_SUBJECT);

        // Get all the messageList where subject does not contain UPDATED_SUBJECT
        defaultMessageShouldBeFound("subject.doesNotContain=" + UPDATED_SUBJECT);
    }


    @Test
    @Transactional
    public void getAllMessagesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message equals to DEFAULT_MESSAGE
        defaultMessageShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the messageList where message equals to UPDATED_MESSAGE
        defaultMessageShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message not equals to DEFAULT_MESSAGE
        defaultMessageShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the messageList where message not equals to UPDATED_MESSAGE
        defaultMessageShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultMessageShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the messageList where message equals to UPDATED_MESSAGE
        defaultMessageShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message is not null
        defaultMessageShouldBeFound("message.specified=true");

        // Get all the messageList where message is null
        defaultMessageShouldNotBeFound("message.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessagesByMessageContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message contains DEFAULT_MESSAGE
        defaultMessageShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the messageList where message contains UPDATED_MESSAGE
        defaultMessageShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllMessagesByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where message does not contain DEFAULT_MESSAGE
        defaultMessageShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the messageList where message does not contain UPDATED_MESSAGE
        defaultMessageShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllMessagesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where createdAt equals to DEFAULT_CREATED_AT
        defaultMessageShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the messageList where createdAt equals to UPDATED_CREATED_AT
        defaultMessageShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllMessagesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where createdAt not equals to DEFAULT_CREATED_AT
        defaultMessageShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the messageList where createdAt not equals to UPDATED_CREATED_AT
        defaultMessageShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllMessagesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultMessageShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the messageList where createdAt equals to UPDATED_CREATED_AT
        defaultMessageShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllMessagesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where createdAt is not null
        defaultMessageShouldBeFound("createdAt.specified=true");

        // Get all the messageList where createdAt is null
        defaultMessageShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessagesByApiResponseIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where apiResponse equals to DEFAULT_API_RESPONSE
        defaultMessageShouldBeFound("apiResponse.equals=" + DEFAULT_API_RESPONSE);

        // Get all the messageList where apiResponse equals to UPDATED_API_RESPONSE
        defaultMessageShouldNotBeFound("apiResponse.equals=" + UPDATED_API_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllMessagesByApiResponseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where apiResponse not equals to DEFAULT_API_RESPONSE
        defaultMessageShouldNotBeFound("apiResponse.notEquals=" + DEFAULT_API_RESPONSE);

        // Get all the messageList where apiResponse not equals to UPDATED_API_RESPONSE
        defaultMessageShouldBeFound("apiResponse.notEquals=" + UPDATED_API_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllMessagesByApiResponseIsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where apiResponse in DEFAULT_API_RESPONSE or UPDATED_API_RESPONSE
        defaultMessageShouldBeFound("apiResponse.in=" + DEFAULT_API_RESPONSE + "," + UPDATED_API_RESPONSE);

        // Get all the messageList where apiResponse equals to UPDATED_API_RESPONSE
        defaultMessageShouldNotBeFound("apiResponse.in=" + UPDATED_API_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllMessagesByApiResponseIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where apiResponse is not null
        defaultMessageShouldBeFound("apiResponse.specified=true");

        // Get all the messageList where apiResponse is null
        defaultMessageShouldNotBeFound("apiResponse.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessagesByApiResponseContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where apiResponse contains DEFAULT_API_RESPONSE
        defaultMessageShouldBeFound("apiResponse.contains=" + DEFAULT_API_RESPONSE);

        // Get all the messageList where apiResponse contains UPDATED_API_RESPONSE
        defaultMessageShouldNotBeFound("apiResponse.contains=" + UPDATED_API_RESPONSE);
    }

    @Test
    @Transactional
    public void getAllMessagesByApiResponseNotContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where apiResponse does not contain DEFAULT_API_RESPONSE
        defaultMessageShouldNotBeFound("apiResponse.doesNotContain=" + DEFAULT_API_RESPONSE);

        // Get all the messageList where apiResponse does not contain UPDATED_API_RESPONSE
        defaultMessageShouldBeFound("apiResponse.doesNotContain=" + UPDATED_API_RESPONSE);
    }


    @Test
    @Transactional
    public void getAllMessagesByDiscussionTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where discussionType equals to DEFAULT_DISCUSSION_TYPE
        defaultMessageShouldBeFound("discussionType.equals=" + DEFAULT_DISCUSSION_TYPE);

        // Get all the messageList where discussionType equals to UPDATED_DISCUSSION_TYPE
        defaultMessageShouldNotBeFound("discussionType.equals=" + UPDATED_DISCUSSION_TYPE);
    }

    @Test
    @Transactional
    public void getAllMessagesByDiscussionTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where discussionType not equals to DEFAULT_DISCUSSION_TYPE
        defaultMessageShouldNotBeFound("discussionType.notEquals=" + DEFAULT_DISCUSSION_TYPE);

        // Get all the messageList where discussionType not equals to UPDATED_DISCUSSION_TYPE
        defaultMessageShouldBeFound("discussionType.notEquals=" + UPDATED_DISCUSSION_TYPE);
    }

    @Test
    @Transactional
    public void getAllMessagesByDiscussionTypeIsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where discussionType in DEFAULT_DISCUSSION_TYPE or UPDATED_DISCUSSION_TYPE
        defaultMessageShouldBeFound("discussionType.in=" + DEFAULT_DISCUSSION_TYPE + "," + UPDATED_DISCUSSION_TYPE);

        // Get all the messageList where discussionType equals to UPDATED_DISCUSSION_TYPE
        defaultMessageShouldNotBeFound("discussionType.in=" + UPDATED_DISCUSSION_TYPE);
    }

    @Test
    @Transactional
    public void getAllMessagesByDiscussionTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where discussionType is not null
        defaultMessageShouldBeFound("discussionType.specified=true");

        // Get all the messageList where discussionType is null
        defaultMessageShouldNotBeFound("discussionType.specified=false");
    }
                @Test
    @Transactional
    public void getAllMessagesByDiscussionTypeContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where discussionType contains DEFAULT_DISCUSSION_TYPE
        defaultMessageShouldBeFound("discussionType.contains=" + DEFAULT_DISCUSSION_TYPE);

        // Get all the messageList where discussionType contains UPDATED_DISCUSSION_TYPE
        defaultMessageShouldNotBeFound("discussionType.contains=" + UPDATED_DISCUSSION_TYPE);
    }

    @Test
    @Transactional
    public void getAllMessagesByDiscussionTypeNotContainsSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where discussionType does not contain DEFAULT_DISCUSSION_TYPE
        defaultMessageShouldNotBeFound("discussionType.doesNotContain=" + DEFAULT_DISCUSSION_TYPE);

        // Get all the messageList where discussionType does not contain UPDATED_DISCUSSION_TYPE
        defaultMessageShouldBeFound("discussionType.doesNotContain=" + UPDATED_DISCUSSION_TYPE);
    }


    @Test
    @Transactional
    public void getAllMessagesByReadStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where readStatus equals to DEFAULT_READ_STATUS
        defaultMessageShouldBeFound("readStatus.equals=" + DEFAULT_READ_STATUS);

        // Get all the messageList where readStatus equals to UPDATED_READ_STATUS
        defaultMessageShouldNotBeFound("readStatus.equals=" + UPDATED_READ_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessagesByReadStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where readStatus not equals to DEFAULT_READ_STATUS
        defaultMessageShouldNotBeFound("readStatus.notEquals=" + DEFAULT_READ_STATUS);

        // Get all the messageList where readStatus not equals to UPDATED_READ_STATUS
        defaultMessageShouldBeFound("readStatus.notEquals=" + UPDATED_READ_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessagesByReadStatusIsInShouldWork() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where readStatus in DEFAULT_READ_STATUS or UPDATED_READ_STATUS
        defaultMessageShouldBeFound("readStatus.in=" + DEFAULT_READ_STATUS + "," + UPDATED_READ_STATUS);

        // Get all the messageList where readStatus equals to UPDATED_READ_STATUS
        defaultMessageShouldNotBeFound("readStatus.in=" + UPDATED_READ_STATUS);
    }

    @Test
    @Transactional
    public void getAllMessagesByReadStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        // Get all the messageList where readStatus is not null
        defaultMessageShouldBeFound("readStatus.specified=true");

        // Get all the messageList where readStatus is null
        defaultMessageShouldNotBeFound("readStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllMessagesByFromIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile from = message.getFrom();
        messageRepository.saveAndFlush(message);
        Long fromId = from.getId();

        // Get all the messageList where from equals to fromId
        defaultMessageShouldBeFound("fromId.equals=" + fromId);

        // Get all the messageList where from equals to fromId + 1
        defaultMessageShouldNotBeFound("fromId.equals=" + (fromId + 1));
    }


    @Test
    @Transactional
    public void getAllMessagesByToIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile to = message.getTo();
        messageRepository.saveAndFlush(message);
        Long toId = to.getId();

        // Get all the messageList where to equals to toId
        defaultMessageShouldBeFound("toId.equals=" + toId);

        // Get all the messageList where to equals to toId + 1
        defaultMessageShouldNotBeFound("toId.equals=" + (toId + 1));
    }


    @Test
    @Transactional
    public void getAllMessagesByCreatedByIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile createdBy = message.getCreatedBy();
        messageRepository.saveAndFlush(message);
        Long createdById = createdBy.getId();

        // Get all the messageList where createdBy equals to createdById
        defaultMessageShouldBeFound("createdById.equals=" + createdById);

        // Get all the messageList where createdBy equals to createdById + 1
        defaultMessageShouldNotBeFound("createdById.equals=" + (createdById + 1));
    }


    @Test
    @Transactional
    public void getAllMessagesByEnquiryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Enquiry enquiry = message.getEnquiry();
        messageRepository.saveAndFlush(message);
        Long enquiryId = enquiry.getId();

        // Get all the messageList where enquiry equals to enquiryId
        defaultMessageShouldBeFound("enquiryId.equals=" + enquiryId);

        // Get all the messageList where enquiry equals to enquiryId + 1
        defaultMessageShouldNotBeFound("enquiryId.equals=" + (enquiryId + 1));
    }


    @Test
    @Transactional
    public void getAllMessagesByOrderIsEqualToSomething() throws Exception {
        // Get already existing entity
        Order order = message.getOrder();
        messageRepository.saveAndFlush(message);
        Long orderId = order.getId();

        // Get all the messageList where order equals to orderId
        defaultMessageShouldBeFound("orderId.equals=" + orderId);

        // Get all the messageList where order equals to orderId + 1
        defaultMessageShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }


    @Test
    @Transactional
    public void getAllMessagesByOfferIsEqualToSomething() throws Exception {
        // Get already existing entity
        Offer offer = message.getOffer();
        messageRepository.saveAndFlush(message);
        Long offerId = offer.getId();

        // Get all the messageList where offer equals to offerId
        defaultMessageShouldBeFound("offerId.equals=" + offerId);

        // Get all the messageList where offer equals to offerId + 1
        defaultMessageShouldNotBeFound("offerId.equals=" + (offerId + 1));
    }


    @Test
    @Transactional
    public void getAllMessagesByReplyMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);
        Message replyMessage = MessageResourceIT.createEntity(em);
        em.persist(replyMessage);
        em.flush();
        message.setReplyMessage(replyMessage);
        messageRepository.saveAndFlush(message);
        Long replyMessageId = replyMessage.getId();

        // Get all the messageList where replyMessage equals to replyMessageId
        defaultMessageShouldBeFound("replyMessageId.equals=" + replyMessageId);

        // Get all the messageList where replyMessage equals to replyMessageId + 1
        defaultMessageShouldNotBeFound("replyMessageId.equals=" + (replyMessageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMessageShouldBeFound(String filter) throws Exception {
        restMessageMockMvc.perform(get("/api/messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].apiResponse").value(hasItem(DEFAULT_API_RESPONSE)))
            .andExpect(jsonPath("$.[*].discussionType").value(hasItem(DEFAULT_DISCUSSION_TYPE)))
            .andExpect(jsonPath("$.[*].readStatus").value(hasItem(DEFAULT_READ_STATUS.booleanValue())));

        // Check, that the count call also returns 1
        restMessageMockMvc.perform(get("/api/messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMessageShouldNotBeFound(String filter) throws Exception {
        restMessageMockMvc.perform(get("/api/messages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMessageMockMvc.perform(get("/api/messages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMessage() throws Exception {
        // Get the message
        restMessageMockMvc.perform(get("/api/messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message
        Message updatedMessage = messageRepository.findById(message.getId()).get();
        // Disconnect from session so that the updates on updatedMessage are not directly saved in db
        em.detach(updatedMessage);
        updatedMessage
            .subject(UPDATED_SUBJECT)
            .message(UPDATED_MESSAGE)
            .createdAt(UPDATED_CREATED_AT)
            .apiResponse(UPDATED_API_RESPONSE)
            .discussionType(UPDATED_DISCUSSION_TYPE)
            .readStatus(UPDATED_READ_STATUS);
        MessageDTO messageDTO = messageMapper.toDto(updatedMessage);

        restMessageMockMvc.perform(put("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testMessage.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testMessage.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testMessage.getApiResponse()).isEqualTo(UPDATED_API_RESPONSE);
        assertThat(testMessage.getDiscussionType()).isEqualTo(UPDATED_DISCUSSION_TYPE);
        assertThat(testMessage.isReadStatus()).isEqualTo(UPDATED_READ_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageMockMvc.perform(put("/api/messages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMessage() throws Exception {
        // Initialize the database
        messageRepository.saveAndFlush(message);

        int databaseSizeBeforeDelete = messageRepository.findAll().size();

        // Delete the message
        restMessageMockMvc.perform(delete("/api/messages/{id}", message.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
