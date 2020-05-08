package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Favourites;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.FavouritesRepository;
import com.mycompany.myapp.service.FavouritesService;
import com.mycompany.myapp.service.dto.FavouritesDTO;
import com.mycompany.myapp.service.mapper.FavouritesMapper;
import com.mycompany.myapp.service.dto.FavouritesCriteria;
import com.mycompany.myapp.service.FavouritesQueryService;

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
 * Integration tests for the {@link FavouritesResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class FavouritesResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private FavouritesRepository favouritesRepository;

    @Autowired
    private FavouritesMapper favouritesMapper;

    @Autowired
    private FavouritesService favouritesService;

    @Autowired
    private FavouritesQueryService favouritesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFavouritesMockMvc;

    private Favourites favourites;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Favourites createEntity(EntityManager em) {
        Favourites favourites = new Favourites()
            .createdAt(DEFAULT_CREATED_AT)
            .remarks(DEFAULT_REMARKS);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        favourites.setFromProfile(userProfile);
        // Add required entity
        favourites.setToProfile(userProfile);
        return favourites;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Favourites createUpdatedEntity(EntityManager em) {
        Favourites favourites = new Favourites()
            .createdAt(UPDATED_CREATED_AT)
            .remarks(UPDATED_REMARKS);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        favourites.setFromProfile(userProfile);
        // Add required entity
        favourites.setToProfile(userProfile);
        return favourites;
    }

    @BeforeEach
    public void initTest() {
        favourites = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavourites() throws Exception {
        int databaseSizeBeforeCreate = favouritesRepository.findAll().size();

        // Create the Favourites
        FavouritesDTO favouritesDTO = favouritesMapper.toDto(favourites);
        restFavouritesMockMvc.perform(post("/api/favourites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(favouritesDTO)))
            .andExpect(status().isCreated());

        // Validate the Favourites in the database
        List<Favourites> favouritesList = favouritesRepository.findAll();
        assertThat(favouritesList).hasSize(databaseSizeBeforeCreate + 1);
        Favourites testFavourites = favouritesList.get(favouritesList.size() - 1);
        assertThat(testFavourites.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testFavourites.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createFavouritesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = favouritesRepository.findAll().size();

        // Create the Favourites with an existing ID
        favourites.setId(1L);
        FavouritesDTO favouritesDTO = favouritesMapper.toDto(favourites);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavouritesMockMvc.perform(post("/api/favourites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(favouritesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Favourites in the database
        List<Favourites> favouritesList = favouritesRepository.findAll();
        assertThat(favouritesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = favouritesRepository.findAll().size();
        // set the field null
        favourites.setCreatedAt(null);

        // Create the Favourites, which fails.
        FavouritesDTO favouritesDTO = favouritesMapper.toDto(favourites);

        restFavouritesMockMvc.perform(post("/api/favourites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(favouritesDTO)))
            .andExpect(status().isBadRequest());

        List<Favourites> favouritesList = favouritesRepository.findAll();
        assertThat(favouritesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFavourites() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get all the favouritesList
        restFavouritesMockMvc.perform(get("/api/favourites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favourites.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getFavourites() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get the favourites
        restFavouritesMockMvc.perform(get("/api/favourites/{id}", favourites.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(favourites.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }


    @Test
    @Transactional
    public void getFavouritesByIdFiltering() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        Long id = favourites.getId();

        defaultFavouritesShouldBeFound("id.equals=" + id);
        defaultFavouritesShouldNotBeFound("id.notEquals=" + id);

        defaultFavouritesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFavouritesShouldNotBeFound("id.greaterThan=" + id);

        defaultFavouritesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFavouritesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFavouritesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get all the favouritesList where createdAt equals to DEFAULT_CREATED_AT
        defaultFavouritesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the favouritesList where createdAt equals to UPDATED_CREATED_AT
        defaultFavouritesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllFavouritesByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get all the favouritesList where createdAt not equals to DEFAULT_CREATED_AT
        defaultFavouritesShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the favouritesList where createdAt not equals to UPDATED_CREATED_AT
        defaultFavouritesShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllFavouritesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get all the favouritesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultFavouritesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the favouritesList where createdAt equals to UPDATED_CREATED_AT
        defaultFavouritesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllFavouritesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get all the favouritesList where createdAt is not null
        defaultFavouritesShouldBeFound("createdAt.specified=true");

        // Get all the favouritesList where createdAt is null
        defaultFavouritesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllFavouritesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get all the favouritesList where remarks equals to DEFAULT_REMARKS
        defaultFavouritesShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the favouritesList where remarks equals to UPDATED_REMARKS
        defaultFavouritesShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllFavouritesByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get all the favouritesList where remarks not equals to DEFAULT_REMARKS
        defaultFavouritesShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the favouritesList where remarks not equals to UPDATED_REMARKS
        defaultFavouritesShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllFavouritesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get all the favouritesList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultFavouritesShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the favouritesList where remarks equals to UPDATED_REMARKS
        defaultFavouritesShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllFavouritesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get all the favouritesList where remarks is not null
        defaultFavouritesShouldBeFound("remarks.specified=true");

        // Get all the favouritesList where remarks is null
        defaultFavouritesShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllFavouritesByRemarksContainsSomething() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get all the favouritesList where remarks contains DEFAULT_REMARKS
        defaultFavouritesShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the favouritesList where remarks contains UPDATED_REMARKS
        defaultFavouritesShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllFavouritesByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        // Get all the favouritesList where remarks does not contain DEFAULT_REMARKS
        defaultFavouritesShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the favouritesList where remarks does not contain UPDATED_REMARKS
        defaultFavouritesShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllFavouritesByFromProfileIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile fromProfile = favourites.getFromProfile();
        favouritesRepository.saveAndFlush(favourites);
        Long fromProfileId = fromProfile.getId();

        // Get all the favouritesList where fromProfile equals to fromProfileId
        defaultFavouritesShouldBeFound("fromProfileId.equals=" + fromProfileId);

        // Get all the favouritesList where fromProfile equals to fromProfileId + 1
        defaultFavouritesShouldNotBeFound("fromProfileId.equals=" + (fromProfileId + 1));
    }


    @Test
    @Transactional
    public void getAllFavouritesByToProfileIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile toProfile = favourites.getToProfile();
        favouritesRepository.saveAndFlush(favourites);
        Long toProfileId = toProfile.getId();

        // Get all the favouritesList where toProfile equals to toProfileId
        defaultFavouritesShouldBeFound("toProfileId.equals=" + toProfileId);

        // Get all the favouritesList where toProfile equals to toProfileId + 1
        defaultFavouritesShouldNotBeFound("toProfileId.equals=" + (toProfileId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFavouritesShouldBeFound(String filter) throws Exception {
        restFavouritesMockMvc.perform(get("/api/favourites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favourites.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restFavouritesMockMvc.perform(get("/api/favourites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFavouritesShouldNotBeFound(String filter) throws Exception {
        restFavouritesMockMvc.perform(get("/api/favourites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFavouritesMockMvc.perform(get("/api/favourites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFavourites() throws Exception {
        // Get the favourites
        restFavouritesMockMvc.perform(get("/api/favourites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavourites() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        int databaseSizeBeforeUpdate = favouritesRepository.findAll().size();

        // Update the favourites
        Favourites updatedFavourites = favouritesRepository.findById(favourites.getId()).get();
        // Disconnect from session so that the updates on updatedFavourites are not directly saved in db
        em.detach(updatedFavourites);
        updatedFavourites
            .createdAt(UPDATED_CREATED_AT)
            .remarks(UPDATED_REMARKS);
        FavouritesDTO favouritesDTO = favouritesMapper.toDto(updatedFavourites);

        restFavouritesMockMvc.perform(put("/api/favourites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(favouritesDTO)))
            .andExpect(status().isOk());

        // Validate the Favourites in the database
        List<Favourites> favouritesList = favouritesRepository.findAll();
        assertThat(favouritesList).hasSize(databaseSizeBeforeUpdate);
        Favourites testFavourites = favouritesList.get(favouritesList.size() - 1);
        assertThat(testFavourites.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testFavourites.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingFavourites() throws Exception {
        int databaseSizeBeforeUpdate = favouritesRepository.findAll().size();

        // Create the Favourites
        FavouritesDTO favouritesDTO = favouritesMapper.toDto(favourites);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavouritesMockMvc.perform(put("/api/favourites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(favouritesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Favourites in the database
        List<Favourites> favouritesList = favouritesRepository.findAll();
        assertThat(favouritesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFavourites() throws Exception {
        // Initialize the database
        favouritesRepository.saveAndFlush(favourites);

        int databaseSizeBeforeDelete = favouritesRepository.findAll().size();

        // Delete the favourites
        restFavouritesMockMvc.perform(delete("/api/favourites/{id}", favourites.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Favourites> favouritesList = favouritesRepository.findAll();
        assertThat(favouritesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
