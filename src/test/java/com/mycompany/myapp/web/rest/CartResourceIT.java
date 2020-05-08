package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.domain.Enquiry;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.repository.CartRepository;
import com.mycompany.myapp.service.CartService;
import com.mycompany.myapp.service.dto.CartDTO;
import com.mycompany.myapp.service.mapper.CartMapper;
import com.mycompany.myapp.service.dto.CartCriteria;
import com.mycompany.myapp.service.CartQueryService;

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
 * Integration tests for the {@link CartResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class CartResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartQueryService cartQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCartMockMvc;

    private Cart cart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cart createEntity(EntityManager em) {
        Cart cart = new Cart()
            .createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        cart.setEnquiry(enquiry);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        cart.setSupplier(userProfile);
        // Add required entity
        cart.setCreatedBy(userProfile);
        return cart;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cart createUpdatedEntity(EntityManager em) {
        Cart cart = new Cart()
            .createdAt(UPDATED_CREATED_AT);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createUpdatedEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        cart.setEnquiry(enquiry);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        cart.setSupplier(userProfile);
        // Add required entity
        cart.setCreatedBy(userProfile);
        return cart;
    }

    @BeforeEach
    public void initTest() {
        cart = createEntity(em);
    }

    @Test
    @Transactional
    public void createCart() throws Exception {
        int databaseSizeBeforeCreate = cartRepository.findAll().size();

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);
        restCartMockMvc.perform(post("/api/carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isCreated());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate + 1);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createCartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartRepository.findAll().size();

        // Create the Cart with an existing ID
        cart.setId(1L);
        CartDTO cartDTO = cartMapper.toDto(cart);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartMockMvc.perform(post("/api/carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartRepository.findAll().size();
        // set the field null
        cart.setCreatedAt(null);

        // Create the Cart, which fails.
        CartDTO cartDTO = cartMapper.toDto(cart);

        restCartMockMvc.perform(post("/api/carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isBadRequest());

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCarts() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList
        restCartMockMvc.perform(get("/api/carts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cart.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get the cart
        restCartMockMvc.perform(get("/api/carts/{id}", cart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cart.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getCartsByIdFiltering() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        Long id = cart.getId();

        defaultCartShouldBeFound("id.equals=" + id);
        defaultCartShouldNotBeFound("id.notEquals=" + id);

        defaultCartShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCartShouldNotBeFound("id.greaterThan=" + id);

        defaultCartShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCartShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCartsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where createdAt equals to DEFAULT_CREATED_AT
        defaultCartShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the cartList where createdAt equals to UPDATED_CREATED_AT
        defaultCartShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCartsByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where createdAt not equals to DEFAULT_CREATED_AT
        defaultCartShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the cartList where createdAt not equals to UPDATED_CREATED_AT
        defaultCartShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCartsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultCartShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the cartList where createdAt equals to UPDATED_CREATED_AT
        defaultCartShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllCartsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList where createdAt is not null
        defaultCartShouldBeFound("createdAt.specified=true");

        // Get all the cartList where createdAt is null
        defaultCartShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllCartsByEnquiryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Enquiry enquiry = cart.getEnquiry();
        cartRepository.saveAndFlush(cart);
        Long enquiryId = enquiry.getId();

        // Get all the cartList where enquiry equals to enquiryId
        defaultCartShouldBeFound("enquiryId.equals=" + enquiryId);

        // Get all the cartList where enquiry equals to enquiryId + 1
        defaultCartShouldNotBeFound("enquiryId.equals=" + (enquiryId + 1));
    }


    @Test
    @Transactional
    public void getAllCartsBySupplierIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile supplier = cart.getSupplier();
        cartRepository.saveAndFlush(cart);
        Long supplierId = supplier.getId();

        // Get all the cartList where supplier equals to supplierId
        defaultCartShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the cartList where supplier equals to supplierId + 1
        defaultCartShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllCartsByCreatedByIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile createdBy = cart.getCreatedBy();
        cartRepository.saveAndFlush(cart);
        Long createdById = createdBy.getId();

        // Get all the cartList where createdBy equals to createdById
        defaultCartShouldBeFound("createdById.equals=" + createdById);

        // Get all the cartList where createdBy equals to createdById + 1
        defaultCartShouldNotBeFound("createdById.equals=" + (createdById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCartShouldBeFound(String filter) throws Exception {
        restCartMockMvc.perform(get("/api/carts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cart.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));

        // Check, that the count call also returns 1
        restCartMockMvc.perform(get("/api/carts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCartShouldNotBeFound(String filter) throws Exception {
        restCartMockMvc.perform(get("/api/carts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCartMockMvc.perform(get("/api/carts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCart() throws Exception {
        // Get the cart
        restCartMockMvc.perform(get("/api/carts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart
        Cart updatedCart = cartRepository.findById(cart.getId()).get();
        // Disconnect from session so that the updates on updatedCart are not directly saved in db
        em.detach(updatedCart);
        updatedCart
            .createdAt(UPDATED_CREATED_AT);
        CartDTO cartDTO = cartMapper.toDto(updatedCart);

        restCartMockMvc.perform(put("/api/carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isOk());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartMockMvc.perform(put("/api/carts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeDelete = cartRepository.findAll().size();

        // Delete the cart
        restCartMockMvc.perform(delete("/api/carts/{id}", cart.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
