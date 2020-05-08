package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Rating;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.domain.Order;
import com.mycompany.myapp.repository.RatingRepository;
import com.mycompany.myapp.service.RatingService;
import com.mycompany.myapp.service.dto.RatingDTO;
import com.mycompany.myapp.service.mapper.RatingMapper;
import com.mycompany.myapp.service.dto.RatingCriteria;
import com.mycompany.myapp.service.RatingQueryService;

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
 * Integration tests for the {@link RatingResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class RatingResourceIT {

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;
    private static final Integer SMALLER_RATING = 1 - 1;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RatingMapper ratingMapper;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingQueryService ratingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRatingMockMvc;

    private Rating rating;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rating createEntity(EntityManager em) {
        Rating rating = new Rating()
            .rating(DEFAULT_RATING)
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
        rating.setFromProfile(userProfile);
        // Add required entity
        rating.setToProfile(userProfile);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createEntity(em);
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        rating.setOrder(order);
        return rating;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rating createUpdatedEntity(EntityManager em) {
        Rating rating = new Rating()
            .rating(UPDATED_RATING)
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
        rating.setFromProfile(userProfile);
        // Add required entity
        rating.setToProfile(userProfile);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createUpdatedEntity(em);
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        rating.setOrder(order);
        return rating;
    }

    @BeforeEach
    public void initTest() {
        rating = createEntity(em);
    }

    @Test
    @Transactional
    public void createRating() throws Exception {
        int databaseSizeBeforeCreate = ratingRepository.findAll().size();

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);
        restRatingMockMvc.perform(post("/api/ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ratingDTO)))
            .andExpect(status().isCreated());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeCreate + 1);
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testRating.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testRating.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createRatingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ratingRepository.findAll().size();

        // Create the Rating with an existing ID
        rating.setId(1L);
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRatingMockMvc.perform(post("/api/ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ratingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingRepository.findAll().size();
        // set the field null
        rating.setRating(null);

        // Create the Rating, which fails.
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        restRatingMockMvc.perform(post("/api/ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ratingDTO)))
            .andExpect(status().isBadRequest());

        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = ratingRepository.findAll().size();
        // set the field null
        rating.setCreatedAt(null);

        // Create the Rating, which fails.
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        restRatingMockMvc.perform(post("/api/ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ratingDTO)))
            .andExpect(status().isBadRequest());

        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRatings() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList
        restRatingMockMvc.perform(get("/api/ratings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rating.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getRating() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get the rating
        restRatingMockMvc.perform(get("/api/ratings/{id}", rating.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rating.getId().intValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }


    @Test
    @Transactional
    public void getRatingsByIdFiltering() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        Long id = rating.getId();

        defaultRatingShouldBeFound("id.equals=" + id);
        defaultRatingShouldNotBeFound("id.notEquals=" + id);

        defaultRatingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRatingShouldNotBeFound("id.greaterThan=" + id);

        defaultRatingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRatingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRatingsByRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where rating equals to DEFAULT_RATING
        defaultRatingShouldBeFound("rating.equals=" + DEFAULT_RATING);

        // Get all the ratingList where rating equals to UPDATED_RATING
        defaultRatingShouldNotBeFound("rating.equals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllRatingsByRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where rating not equals to DEFAULT_RATING
        defaultRatingShouldNotBeFound("rating.notEquals=" + DEFAULT_RATING);

        // Get all the ratingList where rating not equals to UPDATED_RATING
        defaultRatingShouldBeFound("rating.notEquals=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllRatingsByRatingIsInShouldWork() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where rating in DEFAULT_RATING or UPDATED_RATING
        defaultRatingShouldBeFound("rating.in=" + DEFAULT_RATING + "," + UPDATED_RATING);

        // Get all the ratingList where rating equals to UPDATED_RATING
        defaultRatingShouldNotBeFound("rating.in=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllRatingsByRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where rating is not null
        defaultRatingShouldBeFound("rating.specified=true");

        // Get all the ratingList where rating is null
        defaultRatingShouldNotBeFound("rating.specified=false");
    }

    @Test
    @Transactional
    public void getAllRatingsByRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where rating is greater than or equal to DEFAULT_RATING
        defaultRatingShouldBeFound("rating.greaterThanOrEqual=" + DEFAULT_RATING);

        // Get all the ratingList where rating is greater than or equal to UPDATED_RATING
        defaultRatingShouldNotBeFound("rating.greaterThanOrEqual=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllRatingsByRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where rating is less than or equal to DEFAULT_RATING
        defaultRatingShouldBeFound("rating.lessThanOrEqual=" + DEFAULT_RATING);

        // Get all the ratingList where rating is less than or equal to SMALLER_RATING
        defaultRatingShouldNotBeFound("rating.lessThanOrEqual=" + SMALLER_RATING);
    }

    @Test
    @Transactional
    public void getAllRatingsByRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where rating is less than DEFAULT_RATING
        defaultRatingShouldNotBeFound("rating.lessThan=" + DEFAULT_RATING);

        // Get all the ratingList where rating is less than UPDATED_RATING
        defaultRatingShouldBeFound("rating.lessThan=" + UPDATED_RATING);
    }

    @Test
    @Transactional
    public void getAllRatingsByRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where rating is greater than DEFAULT_RATING
        defaultRatingShouldNotBeFound("rating.greaterThan=" + DEFAULT_RATING);

        // Get all the ratingList where rating is greater than SMALLER_RATING
        defaultRatingShouldBeFound("rating.greaterThan=" + SMALLER_RATING);
    }


    @Test
    @Transactional
    public void getAllRatingsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where createdAt equals to DEFAULT_CREATED_AT
        defaultRatingShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the ratingList where createdAt equals to UPDATED_CREATED_AT
        defaultRatingShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllRatingsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where createdAt not equals to DEFAULT_CREATED_AT
        defaultRatingShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the ratingList where createdAt not equals to UPDATED_CREATED_AT
        defaultRatingShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllRatingsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultRatingShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the ratingList where createdAt equals to UPDATED_CREATED_AT
        defaultRatingShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllRatingsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where createdAt is not null
        defaultRatingShouldBeFound("createdAt.specified=true");

        // Get all the ratingList where createdAt is null
        defaultRatingShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllRatingsByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where remarks equals to DEFAULT_REMARKS
        defaultRatingShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the ratingList where remarks equals to UPDATED_REMARKS
        defaultRatingShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRatingsByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where remarks not equals to DEFAULT_REMARKS
        defaultRatingShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the ratingList where remarks not equals to UPDATED_REMARKS
        defaultRatingShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRatingsByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultRatingShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the ratingList where remarks equals to UPDATED_REMARKS
        defaultRatingShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRatingsByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where remarks is not null
        defaultRatingShouldBeFound("remarks.specified=true");

        // Get all the ratingList where remarks is null
        defaultRatingShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllRatingsByRemarksContainsSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where remarks contains DEFAULT_REMARKS
        defaultRatingShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the ratingList where remarks contains UPDATED_REMARKS
        defaultRatingShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllRatingsByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        // Get all the ratingList where remarks does not contain DEFAULT_REMARKS
        defaultRatingShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the ratingList where remarks does not contain UPDATED_REMARKS
        defaultRatingShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllRatingsByFromProfileIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile fromProfile = rating.getFromProfile();
        ratingRepository.saveAndFlush(rating);
        Long fromProfileId = fromProfile.getId();

        // Get all the ratingList where fromProfile equals to fromProfileId
        defaultRatingShouldBeFound("fromProfileId.equals=" + fromProfileId);

        // Get all the ratingList where fromProfile equals to fromProfileId + 1
        defaultRatingShouldNotBeFound("fromProfileId.equals=" + (fromProfileId + 1));
    }


    @Test
    @Transactional
    public void getAllRatingsByToProfileIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile toProfile = rating.getToProfile();
        ratingRepository.saveAndFlush(rating);
        Long toProfileId = toProfile.getId();

        // Get all the ratingList where toProfile equals to toProfileId
        defaultRatingShouldBeFound("toProfileId.equals=" + toProfileId);

        // Get all the ratingList where toProfile equals to toProfileId + 1
        defaultRatingShouldNotBeFound("toProfileId.equals=" + (toProfileId + 1));
    }


    @Test
    @Transactional
    public void getAllRatingsByOrderIsEqualToSomething() throws Exception {
        // Get already existing entity
        Order order = rating.getOrder();
        ratingRepository.saveAndFlush(rating);
        Long orderId = order.getId();

        // Get all the ratingList where order equals to orderId
        defaultRatingShouldBeFound("orderId.equals=" + orderId);

        // Get all the ratingList where order equals to orderId + 1
        defaultRatingShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRatingShouldBeFound(String filter) throws Exception {
        restRatingMockMvc.perform(get("/api/ratings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rating.getId().intValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restRatingMockMvc.perform(get("/api/ratings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRatingShouldNotBeFound(String filter) throws Exception {
        restRatingMockMvc.perform(get("/api/ratings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRatingMockMvc.perform(get("/api/ratings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRating() throws Exception {
        // Get the rating
        restRatingMockMvc.perform(get("/api/ratings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRating() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();

        // Update the rating
        Rating updatedRating = ratingRepository.findById(rating.getId()).get();
        // Disconnect from session so that the updates on updatedRating are not directly saved in db
        em.detach(updatedRating);
        updatedRating
            .rating(UPDATED_RATING)
            .createdAt(UPDATED_CREATED_AT)
            .remarks(UPDATED_REMARKS);
        RatingDTO ratingDTO = ratingMapper.toDto(updatedRating);

        restRatingMockMvc.perform(put("/api/ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ratingDTO)))
            .andExpect(status().isOk());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
        Rating testRating = ratingList.get(ratingList.size() - 1);
        assertThat(testRating.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testRating.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testRating.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingRating() throws Exception {
        int databaseSizeBeforeUpdate = ratingRepository.findAll().size();

        // Create the Rating
        RatingDTO ratingDTO = ratingMapper.toDto(rating);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRatingMockMvc.perform(put("/api/ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ratingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rating in the database
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRating() throws Exception {
        // Initialize the database
        ratingRepository.saveAndFlush(rating);

        int databaseSizeBeforeDelete = ratingRepository.findAll().size();

        // Delete the rating
        restRatingMockMvc.perform(delete("/api/ratings/{id}", rating.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rating> ratingList = ratingRepository.findAll();
        assertThat(ratingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
