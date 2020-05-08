package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Order;
import com.mycompany.myapp.domain.Offer;
import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.domain.Enquiry;
import com.mycompany.myapp.domain.EnquiryDetails;
import com.mycompany.myapp.repository.OrderRepository;
import com.mycompany.myapp.service.OrderService;
import com.mycompany.myapp.service.dto.OrderDTO;
import com.mycompany.myapp.service.mapper.OrderMapper;
import com.mycompany.myapp.service.dto.OrderCriteria;
import com.mycompany.myapp.service.OrderQueryService;

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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class OrderResourceIT {

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;
    private static final Double SMALLER_PRICE = 1D - 1D;

    private static final LocalDate DEFAULT_DELIVERY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DELIVERY_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COMMISSION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMMISSION_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_COMMISSION_DATE = LocalDate.ofEpochDay(-1L);

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderQueryService orderQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderMockMvc;

    private Order order;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createEntity(EntityManager em) {
        Order order = new Order()
            .price(DEFAULT_PRICE)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .status(DEFAULT_STATUS)
            .commissionDate(DEFAULT_COMMISSION_DATE)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .remarks(DEFAULT_REMARKS);
        // Add required entity
        Offer offer;
        if (TestUtil.findAll(em, Offer.class).isEmpty()) {
            offer = OfferResourceIT.createEntity(em);
            em.persist(offer);
            em.flush();
        } else {
            offer = TestUtil.findAll(em, Offer.class).get(0);
        }
        order.setOffer(offer);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        order.setBuyer(userProfile);
        // Add required entity
        order.setSupplier(userProfile);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        order.setEnquiry(enquiry);
        // Add required entity
        EnquiryDetails enquiryDetails;
        if (TestUtil.findAll(em, EnquiryDetails.class).isEmpty()) {
            enquiryDetails = EnquiryDetailsResourceIT.createEntity(em);
            em.persist(enquiryDetails);
            em.flush();
        } else {
            enquiryDetails = TestUtil.findAll(em, EnquiryDetails.class).get(0);
        }
        order.setEnquiryDetails(enquiryDetails);
        return order;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createUpdatedEntity(EntityManager em) {
        Order order = new Order()
            .price(UPDATED_PRICE)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .status(UPDATED_STATUS)
            .commissionDate(UPDATED_COMMISSION_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .remarks(UPDATED_REMARKS);
        // Add required entity
        Offer offer;
        if (TestUtil.findAll(em, Offer.class).isEmpty()) {
            offer = OfferResourceIT.createUpdatedEntity(em);
            em.persist(offer);
            em.flush();
        } else {
            offer = TestUtil.findAll(em, Offer.class).get(0);
        }
        order.setOffer(offer);
        // Add required entity
        UserProfile userProfile;
        if (TestUtil.findAll(em, UserProfile.class).isEmpty()) {
            userProfile = UserProfileResourceIT.createUpdatedEntity(em);
            em.persist(userProfile);
            em.flush();
        } else {
            userProfile = TestUtil.findAll(em, UserProfile.class).get(0);
        }
        order.setBuyer(userProfile);
        // Add required entity
        order.setSupplier(userProfile);
        // Add required entity
        Enquiry enquiry;
        if (TestUtil.findAll(em, Enquiry.class).isEmpty()) {
            enquiry = EnquiryResourceIT.createUpdatedEntity(em);
            em.persist(enquiry);
            em.flush();
        } else {
            enquiry = TestUtil.findAll(em, Enquiry.class).get(0);
        }
        order.setEnquiry(enquiry);
        // Add required entity
        EnquiryDetails enquiryDetails;
        if (TestUtil.findAll(em, EnquiryDetails.class).isEmpty()) {
            enquiryDetails = EnquiryDetailsResourceIT.createUpdatedEntity(em);
            em.persist(enquiryDetails);
            em.flush();
        } else {
            enquiryDetails = TestUtil.findAll(em, EnquiryDetails.class).get(0);
        }
        order.setEnquiryDetails(enquiryDetails);
        return order;
    }

    @BeforeEach
    public void initTest() {
        order = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testOrder.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrder.getCommissionDate()).isEqualTo(DEFAULT_COMMISSION_DATE);
        assertThat(testOrder.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOrder.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testOrder.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order with an existing ID
        order.setId(1L);
        OrderDTO orderDTO = orderMapper.toDto(order);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setPrice(null);

        // Create the Order, which fails.
        OrderDTO orderDTO = orderMapper.toDto(order);

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDeliveryDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setDeliveryDate(null);

        // Create the Order, which fails.
        OrderDTO orderDTO = orderMapper.toDto(order);

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setStatus(null);

        // Create the Order, which fails.
        OrderDTO orderDTO = orderMapper.toDto(order);

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCommissionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setCommissionDate(null);

        // Create the Order, which fails.
        OrderDTO orderDTO = orderMapper.toDto(order);

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setCreatedAt(null);

        // Create the Order, which fails.
        OrderDTO orderDTO = orderMapper.toDto(order);

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setUpdatedAt(null);

        // Create the Order, which fails.
        OrderDTO orderDTO = orderMapper.toDto(order);

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRemarksIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setRemarks(null);

        // Create the Order, which fails.
        OrderDTO orderDTO = orderMapper.toDto(order);

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].commissionDate").value(hasItem(DEFAULT_COMMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.commissionDate").value(DEFAULT_COMMISSION_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }


    @Test
    @Transactional
    public void getOrdersByIdFiltering() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        Long id = order.getId();

        defaultOrderShouldBeFound("id.equals=" + id);
        defaultOrderShouldNotBeFound("id.notEquals=" + id);

        defaultOrderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrderShouldNotBeFound("id.greaterThan=" + id);

        defaultOrderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrderShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrdersByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where price equals to DEFAULT_PRICE
        defaultOrderShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the orderList where price equals to UPDATED_PRICE
        defaultOrderShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrdersByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where price not equals to DEFAULT_PRICE
        defaultOrderShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the orderList where price not equals to UPDATED_PRICE
        defaultOrderShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrdersByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultOrderShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the orderList where price equals to UPDATED_PRICE
        defaultOrderShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrdersByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where price is not null
        defaultOrderShouldBeFound("price.specified=true");

        // Get all the orderList where price is null
        defaultOrderShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where price is greater than or equal to DEFAULT_PRICE
        defaultOrderShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the orderList where price is greater than or equal to UPDATED_PRICE
        defaultOrderShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrdersByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where price is less than or equal to DEFAULT_PRICE
        defaultOrderShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the orderList where price is less than or equal to SMALLER_PRICE
        defaultOrderShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrdersByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where price is less than DEFAULT_PRICE
        defaultOrderShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the orderList where price is less than UPDATED_PRICE
        defaultOrderShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrdersByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where price is greater than DEFAULT_PRICE
        defaultOrderShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the orderList where price is greater than SMALLER_PRICE
        defaultOrderShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllOrdersByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where deliveryDate equals to DEFAULT_DELIVERY_DATE
        defaultOrderShouldBeFound("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE);

        // Get all the orderList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultOrderShouldNotBeFound("deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where deliveryDate not equals to DEFAULT_DELIVERY_DATE
        defaultOrderShouldNotBeFound("deliveryDate.notEquals=" + DEFAULT_DELIVERY_DATE);

        // Get all the orderList where deliveryDate not equals to UPDATED_DELIVERY_DATE
        defaultOrderShouldBeFound("deliveryDate.notEquals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where deliveryDate in DEFAULT_DELIVERY_DATE or UPDATED_DELIVERY_DATE
        defaultOrderShouldBeFound("deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE);

        // Get all the orderList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultOrderShouldNotBeFound("deliveryDate.in=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where deliveryDate is not null
        defaultOrderShouldBeFound("deliveryDate.specified=true");

        // Get all the orderList where deliveryDate is null
        defaultOrderShouldNotBeFound("deliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where deliveryDate is greater than or equal to DEFAULT_DELIVERY_DATE
        defaultOrderShouldBeFound("deliveryDate.greaterThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the orderList where deliveryDate is greater than or equal to UPDATED_DELIVERY_DATE
        defaultOrderShouldNotBeFound("deliveryDate.greaterThanOrEqual=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where deliveryDate is less than or equal to DEFAULT_DELIVERY_DATE
        defaultOrderShouldBeFound("deliveryDate.lessThanOrEqual=" + DEFAULT_DELIVERY_DATE);

        // Get all the orderList where deliveryDate is less than or equal to SMALLER_DELIVERY_DATE
        defaultOrderShouldNotBeFound("deliveryDate.lessThanOrEqual=" + SMALLER_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where deliveryDate is less than DEFAULT_DELIVERY_DATE
        defaultOrderShouldNotBeFound("deliveryDate.lessThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the orderList where deliveryDate is less than UPDATED_DELIVERY_DATE
        defaultOrderShouldBeFound("deliveryDate.lessThan=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where deliveryDate is greater than DEFAULT_DELIVERY_DATE
        defaultOrderShouldNotBeFound("deliveryDate.greaterThan=" + DEFAULT_DELIVERY_DATE);

        // Get all the orderList where deliveryDate is greater than SMALLER_DELIVERY_DATE
        defaultOrderShouldBeFound("deliveryDate.greaterThan=" + SMALLER_DELIVERY_DATE);
    }


    @Test
    @Transactional
    public void getAllOrdersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where status equals to DEFAULT_STATUS
        defaultOrderShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the orderList where status equals to UPDATED_STATUS
        defaultOrderShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where status not equals to DEFAULT_STATUS
        defaultOrderShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the orderList where status not equals to UPDATED_STATUS
        defaultOrderShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOrderShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the orderList where status equals to UPDATED_STATUS
        defaultOrderShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where status is not null
        defaultOrderShouldBeFound("status.specified=true");

        // Get all the orderList where status is null
        defaultOrderShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrdersByStatusContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where status contains DEFAULT_STATUS
        defaultOrderShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the orderList where status contains UPDATED_STATUS
        defaultOrderShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where status does not contain DEFAULT_STATUS
        defaultOrderShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the orderList where status does not contain UPDATED_STATUS
        defaultOrderShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllOrdersByCommissionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where commissionDate equals to DEFAULT_COMMISSION_DATE
        defaultOrderShouldBeFound("commissionDate.equals=" + DEFAULT_COMMISSION_DATE);

        // Get all the orderList where commissionDate equals to UPDATED_COMMISSION_DATE
        defaultOrderShouldNotBeFound("commissionDate.equals=" + UPDATED_COMMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByCommissionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where commissionDate not equals to DEFAULT_COMMISSION_DATE
        defaultOrderShouldNotBeFound("commissionDate.notEquals=" + DEFAULT_COMMISSION_DATE);

        // Get all the orderList where commissionDate not equals to UPDATED_COMMISSION_DATE
        defaultOrderShouldBeFound("commissionDate.notEquals=" + UPDATED_COMMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByCommissionDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where commissionDate in DEFAULT_COMMISSION_DATE or UPDATED_COMMISSION_DATE
        defaultOrderShouldBeFound("commissionDate.in=" + DEFAULT_COMMISSION_DATE + "," + UPDATED_COMMISSION_DATE);

        // Get all the orderList where commissionDate equals to UPDATED_COMMISSION_DATE
        defaultOrderShouldNotBeFound("commissionDate.in=" + UPDATED_COMMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByCommissionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where commissionDate is not null
        defaultOrderShouldBeFound("commissionDate.specified=true");

        // Get all the orderList where commissionDate is null
        defaultOrderShouldNotBeFound("commissionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByCommissionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where commissionDate is greater than or equal to DEFAULT_COMMISSION_DATE
        defaultOrderShouldBeFound("commissionDate.greaterThanOrEqual=" + DEFAULT_COMMISSION_DATE);

        // Get all the orderList where commissionDate is greater than or equal to UPDATED_COMMISSION_DATE
        defaultOrderShouldNotBeFound("commissionDate.greaterThanOrEqual=" + UPDATED_COMMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByCommissionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where commissionDate is less than or equal to DEFAULT_COMMISSION_DATE
        defaultOrderShouldBeFound("commissionDate.lessThanOrEqual=" + DEFAULT_COMMISSION_DATE);

        // Get all the orderList where commissionDate is less than or equal to SMALLER_COMMISSION_DATE
        defaultOrderShouldNotBeFound("commissionDate.lessThanOrEqual=" + SMALLER_COMMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByCommissionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where commissionDate is less than DEFAULT_COMMISSION_DATE
        defaultOrderShouldNotBeFound("commissionDate.lessThan=" + DEFAULT_COMMISSION_DATE);

        // Get all the orderList where commissionDate is less than UPDATED_COMMISSION_DATE
        defaultOrderShouldBeFound("commissionDate.lessThan=" + UPDATED_COMMISSION_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByCommissionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where commissionDate is greater than DEFAULT_COMMISSION_DATE
        defaultOrderShouldNotBeFound("commissionDate.greaterThan=" + DEFAULT_COMMISSION_DATE);

        // Get all the orderList where commissionDate is greater than SMALLER_COMMISSION_DATE
        defaultOrderShouldBeFound("commissionDate.greaterThan=" + SMALLER_COMMISSION_DATE);
    }


    @Test
    @Transactional
    public void getAllOrdersByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where createdAt equals to DEFAULT_CREATED_AT
        defaultOrderShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the orderList where createdAt equals to UPDATED_CREATED_AT
        defaultOrderShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllOrdersByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where createdAt not equals to DEFAULT_CREATED_AT
        defaultOrderShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the orderList where createdAt not equals to UPDATED_CREATED_AT
        defaultOrderShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllOrdersByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultOrderShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the orderList where createdAt equals to UPDATED_CREATED_AT
        defaultOrderShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllOrdersByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where createdAt is not null
        defaultOrderShouldBeFound("createdAt.specified=true");

        // Get all the orderList where createdAt is null
        defaultOrderShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultOrderShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the orderList where updatedAt equals to UPDATED_UPDATED_AT
        defaultOrderShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllOrdersByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultOrderShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the orderList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultOrderShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllOrdersByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultOrderShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the orderList where updatedAt equals to UPDATED_UPDATED_AT
        defaultOrderShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllOrdersByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where updatedAt is not null
        defaultOrderShouldBeFound("updatedAt.specified=true");

        // Get all the orderList where updatedAt is null
        defaultOrderShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remarks equals to DEFAULT_REMARKS
        defaultOrderShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the orderList where remarks equals to UPDATED_REMARKS
        defaultOrderShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllOrdersByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remarks not equals to DEFAULT_REMARKS
        defaultOrderShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the orderList where remarks not equals to UPDATED_REMARKS
        defaultOrderShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllOrdersByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultOrderShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the orderList where remarks equals to UPDATED_REMARKS
        defaultOrderShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllOrdersByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remarks is not null
        defaultOrderShouldBeFound("remarks.specified=true");

        // Get all the orderList where remarks is null
        defaultOrderShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrdersByRemarksContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remarks contains DEFAULT_REMARKS
        defaultOrderShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the orderList where remarks contains UPDATED_REMARKS
        defaultOrderShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllOrdersByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remarks does not contain DEFAULT_REMARKS
        defaultOrderShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the orderList where remarks does not contain UPDATED_REMARKS
        defaultOrderShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllOrdersByOfferIsEqualToSomething() throws Exception {
        // Get already existing entity
        Offer offer = order.getOffer();
        orderRepository.saveAndFlush(order);
        Long offerId = offer.getId();

        // Get all the orderList where offer equals to offerId
        defaultOrderShouldBeFound("offerId.equals=" + offerId);

        // Get all the orderList where offer equals to offerId + 1
        defaultOrderShouldNotBeFound("offerId.equals=" + (offerId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByBuyerIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile buyer = order.getBuyer();
        orderRepository.saveAndFlush(order);
        Long buyerId = buyer.getId();

        // Get all the orderList where buyer equals to buyerId
        defaultOrderShouldBeFound("buyerId.equals=" + buyerId);

        // Get all the orderList where buyer equals to buyerId + 1
        defaultOrderShouldNotBeFound("buyerId.equals=" + (buyerId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersBySupplierIsEqualToSomething() throws Exception {
        // Get already existing entity
        UserProfile supplier = order.getSupplier();
        orderRepository.saveAndFlush(order);
        Long supplierId = supplier.getId();

        // Get all the orderList where supplier equals to supplierId
        defaultOrderShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the orderList where supplier equals to supplierId + 1
        defaultOrderShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByEnquiryIsEqualToSomething() throws Exception {
        // Get already existing entity
        Enquiry enquiry = order.getEnquiry();
        orderRepository.saveAndFlush(order);
        Long enquiryId = enquiry.getId();

        // Get all the orderList where enquiry equals to enquiryId
        defaultOrderShouldBeFound("enquiryId.equals=" + enquiryId);

        // Get all the orderList where enquiry equals to enquiryId + 1
        defaultOrderShouldNotBeFound("enquiryId.equals=" + (enquiryId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByEnquiryDetailsIsEqualToSomething() throws Exception {
        // Get already existing entity
        EnquiryDetails enquiryDetails = order.getEnquiryDetails();
        orderRepository.saveAndFlush(order);
        Long enquiryDetailsId = enquiryDetails.getId();

        // Get all the orderList where enquiryDetails equals to enquiryDetailsId
        defaultOrderShouldBeFound("enquiryDetailsId.equals=" + enquiryDetailsId);

        // Get all the orderList where enquiryDetails equals to enquiryDetailsId + 1
        defaultOrderShouldNotBeFound("enquiryDetailsId.equals=" + (enquiryDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrderShouldBeFound(String filter) throws Exception {
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].commissionDate").value(hasItem(DEFAULT_COMMISSION_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restOrderMockMvc.perform(get("/api/orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrderShouldNotBeFound(String filter) throws Exception {
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrderMockMvc.perform(get("/api/orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).get();
        // Disconnect from session so that the updates on updatedOrder are not directly saved in db
        em.detach(updatedOrder);
        updatedOrder
            .price(UPDATED_PRICE)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .status(UPDATED_STATUS)
            .commissionDate(UPDATED_COMMISSION_DATE)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .remarks(UPDATED_REMARKS);
        OrderDTO orderDTO = orderMapper.toDto(updatedOrder);

        restOrderMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testOrder.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrder.getCommissionDate()).isEqualTo(UPDATED_COMMISSION_DATE);
        assertThat(testOrder.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOrder.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testOrder.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Delete the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
