package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.EnquiryNote;
import com.mycompany.myapp.domain.EnquiryDetails;
import com.mycompany.myapp.repository.EnquiryNoteRepository;
import com.mycompany.myapp.service.EnquiryNoteService;
import com.mycompany.myapp.service.dto.EnquiryNoteDTO;
import com.mycompany.myapp.service.mapper.EnquiryNoteMapper;
import com.mycompany.myapp.service.dto.EnquiryNoteCriteria;
import com.mycompany.myapp.service.EnquiryNoteQueryService;

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
 * Integration tests for the {@link EnquiryNoteResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EnquiryNoteResourceIT {

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EnquiryNoteRepository enquiryNoteRepository;

    @Autowired
    private EnquiryNoteMapper enquiryNoteMapper;

    @Autowired
    private EnquiryNoteService enquiryNoteService;

    @Autowired
    private EnquiryNoteQueryService enquiryNoteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnquiryNoteMockMvc;

    private EnquiryNote enquiryNote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnquiryNote createEntity(EntityManager em) {
        EnquiryNote enquiryNote = new EnquiryNote()
            .note(DEFAULT_NOTE)
            .createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        EnquiryDetails enquiryDetails;
        if (TestUtil.findAll(em, EnquiryDetails.class).isEmpty()) {
            enquiryDetails = EnquiryDetailsResourceIT.createEntity(em);
            em.persist(enquiryDetails);
            em.flush();
        } else {
            enquiryDetails = TestUtil.findAll(em, EnquiryDetails.class).get(0);
        }
        enquiryNote.setEnquiryDetails(enquiryDetails);
        return enquiryNote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnquiryNote createUpdatedEntity(EntityManager em) {
        EnquiryNote enquiryNote = new EnquiryNote()
            .note(UPDATED_NOTE)
            .createdAt(UPDATED_CREATED_AT);
        // Add required entity
        EnquiryDetails enquiryDetails;
        if (TestUtil.findAll(em, EnquiryDetails.class).isEmpty()) {
            enquiryDetails = EnquiryDetailsResourceIT.createUpdatedEntity(em);
            em.persist(enquiryDetails);
            em.flush();
        } else {
            enquiryDetails = TestUtil.findAll(em, EnquiryDetails.class).get(0);
        }
        enquiryNote.setEnquiryDetails(enquiryDetails);
        return enquiryNote;
    }

    @BeforeEach
    public void initTest() {
        enquiryNote = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnquiryNote() throws Exception {
        int databaseSizeBeforeCreate = enquiryNoteRepository.findAll().size();

        // Create the EnquiryNote
        EnquiryNoteDTO enquiryNoteDTO = enquiryNoteMapper.toDto(enquiryNote);
        restEnquiryNoteMockMvc.perform(post("/api/enquiry-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryNoteDTO)))
            .andExpect(status().isCreated());

        // Validate the EnquiryNote in the database
        List<EnquiryNote> enquiryNoteList = enquiryNoteRepository.findAll();
        assertThat(enquiryNoteList).hasSize(databaseSizeBeforeCreate + 1);
        EnquiryNote testEnquiryNote = enquiryNoteList.get(enquiryNoteList.size() - 1);
        assertThat(testEnquiryNote.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testEnquiryNote.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createEnquiryNoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enquiryNoteRepository.findAll().size();

        // Create the EnquiryNote with an existing ID
        enquiryNote.setId(1L);
        EnquiryNoteDTO enquiryNoteDTO = enquiryNoteMapper.toDto(enquiryNote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnquiryNoteMockMvc.perform(post("/api/enquiry-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryNoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnquiryNote in the database
        List<EnquiryNote> enquiryNoteList = enquiryNoteRepository.findAll();
        assertThat(enquiryNoteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNoteIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryNoteRepository.findAll().size();
        // set the field null
        enquiryNote.setNote(null);

        // Create the EnquiryNote, which fails.
        EnquiryNoteDTO enquiryNoteDTO = enquiryNoteMapper.toDto(enquiryNote);

        restEnquiryNoteMockMvc.perform(post("/api/enquiry-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryNoteDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryNote> enquiryNoteList = enquiryNoteRepository.findAll();
        assertThat(enquiryNoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryNoteRepository.findAll().size();
        // set the field null
        enquiryNote.setCreatedAt(null);

        // Create the EnquiryNote, which fails.
        EnquiryNoteDTO enquiryNoteDTO = enquiryNoteMapper.toDto(enquiryNote);

        restEnquiryNoteMockMvc.perform(post("/api/enquiry-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryNoteDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryNote> enquiryNoteList = enquiryNoteRepository.findAll();
        assertThat(enquiryNoteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnquiryNotes() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get all the enquiryNoteList
        restEnquiryNoteMockMvc.perform(get("/api/enquiry-notes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquiryNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getEnquiryNote() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get the enquiryNote
        restEnquiryNoteMockMvc.perform(get("/api/enquiry-notes/{id}", enquiryNote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enquiryNote.getId().intValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getEnquiryNotesByIdFiltering() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        Long id = enquiryNote.getId();

        defaultEnquiryNoteShouldBeFound("id.equals=" + id);
        defaultEnquiryNoteShouldNotBeFound("id.notEquals=" + id);

        defaultEnquiryNoteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnquiryNoteShouldNotBeFound("id.greaterThan=" + id);

        defaultEnquiryNoteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnquiryNoteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEnquiryNotesByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get all the enquiryNoteList where note equals to DEFAULT_NOTE
        defaultEnquiryNoteShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the enquiryNoteList where note equals to UPDATED_NOTE
        defaultEnquiryNoteShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEnquiryNotesByNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get all the enquiryNoteList where note not equals to DEFAULT_NOTE
        defaultEnquiryNoteShouldNotBeFound("note.notEquals=" + DEFAULT_NOTE);

        // Get all the enquiryNoteList where note not equals to UPDATED_NOTE
        defaultEnquiryNoteShouldBeFound("note.notEquals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEnquiryNotesByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get all the enquiryNoteList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultEnquiryNoteShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the enquiryNoteList where note equals to UPDATED_NOTE
        defaultEnquiryNoteShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEnquiryNotesByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get all the enquiryNoteList where note is not null
        defaultEnquiryNoteShouldBeFound("note.specified=true");

        // Get all the enquiryNoteList where note is null
        defaultEnquiryNoteShouldNotBeFound("note.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnquiryNotesByNoteContainsSomething() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get all the enquiryNoteList where note contains DEFAULT_NOTE
        defaultEnquiryNoteShouldBeFound("note.contains=" + DEFAULT_NOTE);

        // Get all the enquiryNoteList where note contains UPDATED_NOTE
        defaultEnquiryNoteShouldNotBeFound("note.contains=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllEnquiryNotesByNoteNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get all the enquiryNoteList where note does not contain DEFAULT_NOTE
        defaultEnquiryNoteShouldNotBeFound("note.doesNotContain=" + DEFAULT_NOTE);

        // Get all the enquiryNoteList where note does not contain UPDATED_NOTE
        defaultEnquiryNoteShouldBeFound("note.doesNotContain=" + UPDATED_NOTE);
    }


    @Test
    @Transactional
    public void getAllEnquiryNotesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get all the enquiryNoteList where createdAt equals to DEFAULT_CREATED_AT
        defaultEnquiryNoteShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the enquiryNoteList where createdAt equals to UPDATED_CREATED_AT
        defaultEnquiryNoteShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllEnquiryNotesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get all the enquiryNoteList where createdAt not equals to DEFAULT_CREATED_AT
        defaultEnquiryNoteShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the enquiryNoteList where createdAt not equals to UPDATED_CREATED_AT
        defaultEnquiryNoteShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllEnquiryNotesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get all the enquiryNoteList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultEnquiryNoteShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the enquiryNoteList where createdAt equals to UPDATED_CREATED_AT
        defaultEnquiryNoteShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllEnquiryNotesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        // Get all the enquiryNoteList where createdAt is not null
        defaultEnquiryNoteShouldBeFound("createdAt.specified=true");

        // Get all the enquiryNoteList where createdAt is null
        defaultEnquiryNoteShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnquiryNotesByEnquiryDetailsIsEqualToSomething() throws Exception {
        // Get already existing entity
        EnquiryDetails enquiryDetails = enquiryNote.getEnquiryDetails();
        enquiryNoteRepository.saveAndFlush(enquiryNote);
        Long enquiryDetailsId = enquiryDetails.getId();

        // Get all the enquiryNoteList where enquiryDetails equals to enquiryDetailsId
        defaultEnquiryNoteShouldBeFound("enquiryDetailsId.equals=" + enquiryDetailsId);

        // Get all the enquiryNoteList where enquiryDetails equals to enquiryDetailsId + 1
        defaultEnquiryNoteShouldNotBeFound("enquiryDetailsId.equals=" + (enquiryDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnquiryNoteShouldBeFound(String filter) throws Exception {
        restEnquiryNoteMockMvc.perform(get("/api/enquiry-notes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquiryNote.getId().intValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));

        // Check, that the count call also returns 1
        restEnquiryNoteMockMvc.perform(get("/api/enquiry-notes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnquiryNoteShouldNotBeFound(String filter) throws Exception {
        restEnquiryNoteMockMvc.perform(get("/api/enquiry-notes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnquiryNoteMockMvc.perform(get("/api/enquiry-notes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEnquiryNote() throws Exception {
        // Get the enquiryNote
        restEnquiryNoteMockMvc.perform(get("/api/enquiry-notes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnquiryNote() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        int databaseSizeBeforeUpdate = enquiryNoteRepository.findAll().size();

        // Update the enquiryNote
        EnquiryNote updatedEnquiryNote = enquiryNoteRepository.findById(enquiryNote.getId()).get();
        // Disconnect from session so that the updates on updatedEnquiryNote are not directly saved in db
        em.detach(updatedEnquiryNote);
        updatedEnquiryNote
            .note(UPDATED_NOTE)
            .createdAt(UPDATED_CREATED_AT);
        EnquiryNoteDTO enquiryNoteDTO = enquiryNoteMapper.toDto(updatedEnquiryNote);

        restEnquiryNoteMockMvc.perform(put("/api/enquiry-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryNoteDTO)))
            .andExpect(status().isOk());

        // Validate the EnquiryNote in the database
        List<EnquiryNote> enquiryNoteList = enquiryNoteRepository.findAll();
        assertThat(enquiryNoteList).hasSize(databaseSizeBeforeUpdate);
        EnquiryNote testEnquiryNote = enquiryNoteList.get(enquiryNoteList.size() - 1);
        assertThat(testEnquiryNote.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testEnquiryNote.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingEnquiryNote() throws Exception {
        int databaseSizeBeforeUpdate = enquiryNoteRepository.findAll().size();

        // Create the EnquiryNote
        EnquiryNoteDTO enquiryNoteDTO = enquiryNoteMapper.toDto(enquiryNote);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnquiryNoteMockMvc.perform(put("/api/enquiry-notes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryNoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnquiryNote in the database
        List<EnquiryNote> enquiryNoteList = enquiryNoteRepository.findAll();
        assertThat(enquiryNoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnquiryNote() throws Exception {
        // Initialize the database
        enquiryNoteRepository.saveAndFlush(enquiryNote);

        int databaseSizeBeforeDelete = enquiryNoteRepository.findAll().size();

        // Delete the enquiryNote
        restEnquiryNoteMockMvc.perform(delete("/api/enquiry-notes/{id}", enquiryNote.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnquiryNote> enquiryNoteList = enquiryNoteRepository.findAll();
        assertThat(enquiryNoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
