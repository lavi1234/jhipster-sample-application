package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.repository.AddressRepository;
import com.mycompany.myapp.service.AddressService;
import com.mycompany.myapp.service.dto.AddressDTO;
import com.mycompany.myapp.service.mapper.AddressMapper;
import com.mycompany.myapp.service.dto.AddressCriteria;
import com.mycompany.myapp.service.AddressQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AddressResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AddressResourceIT {

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_GEO_LATITUDE = 1D;
    private static final Double UPDATED_GEO_LATITUDE = 2D;
    private static final Double SMALLER_GEO_LATITUDE = 1D - 1D;

    private static final Double DEFAULT_GEO_LONGITUDE = 1D;
    private static final Double UPDATED_GEO_LONGITUDE = 2D;
    private static final Double SMALLER_GEO_LONGITUDE = 1D - 1D;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressQueryService addressQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .street(DEFAULT_STREET)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .postalCode(DEFAULT_POSTAL_CODE)
            .geoLatitude(DEFAULT_GEO_LATITUDE)
            .geoLongitude(DEFAULT_GEO_LONGITUDE);
        return address;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity(EntityManager em) {
        Address address = new Address()
            .street(UPDATED_STREET)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .postalCode(UPDATED_POSTAL_CODE)
            .geoLatitude(UPDATED_GEO_LATITUDE)
            .geoLongitude(UPDATED_GEO_LONGITUDE);
        return address;
    }

    @BeforeEach
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    public void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testAddress.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testAddress.getGeoLatitude()).isEqualTo(DEFAULT_GEO_LATITUDE);
        assertThat(testAddress.getGeoLongitude()).isEqualTo(DEFAULT_GEO_LONGITUDE);
    }

    @Test
    @Transactional
    public void createAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // Create the Address with an existing ID
        address.setId(1L);
        AddressDTO addressDTO = addressMapper.toDto(address);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGeoLatitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().size();
        // set the field null
        address.setGeoLatitude(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeoLongitudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = addressRepository.findAll().size();
        // set the field null
        address.setGeoLongitude(null);

        // Create the Address, which fails.
        AddressDTO addressDTO = addressMapper.toDto(address);

        restAddressMockMvc.perform(post("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].geoLatitude").value(hasItem(DEFAULT_GEO_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].geoLongitude").value(hasItem(DEFAULT_GEO_LONGITUDE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.geoLatitude").value(DEFAULT_GEO_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.geoLongitude").value(DEFAULT_GEO_LONGITUDE.doubleValue()));
    }


    @Test
    @Transactional
    public void getAddressesByIdFiltering() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        Long id = address.getId();

        defaultAddressShouldBeFound("id.equals=" + id);
        defaultAddressShouldNotBeFound("id.notEquals=" + id);

        defaultAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAddressesByStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street equals to DEFAULT_STREET
        defaultAddressShouldBeFound("street.equals=" + DEFAULT_STREET);

        // Get all the addressList where street equals to UPDATED_STREET
        defaultAddressShouldNotBeFound("street.equals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByStreetIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street not equals to DEFAULT_STREET
        defaultAddressShouldNotBeFound("street.notEquals=" + DEFAULT_STREET);

        // Get all the addressList where street not equals to UPDATED_STREET
        defaultAddressShouldBeFound("street.notEquals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByStreetIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street in DEFAULT_STREET or UPDATED_STREET
        defaultAddressShouldBeFound("street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

        // Get all the addressList where street equals to UPDATED_STREET
        defaultAddressShouldNotBeFound("street.in=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street is not null
        defaultAddressShouldBeFound("street.specified=true");

        // Get all the addressList where street is null
        defaultAddressShouldNotBeFound("street.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByStreetContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street contains DEFAULT_STREET
        defaultAddressShouldBeFound("street.contains=" + DEFAULT_STREET);

        // Get all the addressList where street contains UPDATED_STREET
        defaultAddressShouldNotBeFound("street.contains=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllAddressesByStreetNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where street does not contain DEFAULT_STREET
        defaultAddressShouldNotBeFound("street.doesNotContain=" + DEFAULT_STREET);

        // Get all the addressList where street does not contain UPDATED_STREET
        defaultAddressShouldBeFound("street.doesNotContain=" + UPDATED_STREET);
    }


    @Test
    @Transactional
    public void getAllAddressesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where city equals to DEFAULT_CITY
        defaultAddressShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the addressList where city equals to UPDATED_CITY
        defaultAddressShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where city not equals to DEFAULT_CITY
        defaultAddressShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the addressList where city not equals to UPDATED_CITY
        defaultAddressShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where city in DEFAULT_CITY or UPDATED_CITY
        defaultAddressShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the addressList where city equals to UPDATED_CITY
        defaultAddressShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where city is not null
        defaultAddressShouldBeFound("city.specified=true");

        // Get all the addressList where city is null
        defaultAddressShouldNotBeFound("city.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByCityContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where city contains DEFAULT_CITY
        defaultAddressShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the addressList where city contains UPDATED_CITY
        defaultAddressShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where city does not contain DEFAULT_CITY
        defaultAddressShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the addressList where city does not contain UPDATED_CITY
        defaultAddressShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }


    @Test
    @Transactional
    public void getAllAddressesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state equals to DEFAULT_STATE
        defaultAddressShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the addressList where state equals to UPDATED_STATE
        defaultAddressShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllAddressesByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state not equals to DEFAULT_STATE
        defaultAddressShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the addressList where state not equals to UPDATED_STATE
        defaultAddressShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllAddressesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state in DEFAULT_STATE or UPDATED_STATE
        defaultAddressShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the addressList where state equals to UPDATED_STATE
        defaultAddressShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllAddressesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state is not null
        defaultAddressShouldBeFound("state.specified=true");

        // Get all the addressList where state is null
        defaultAddressShouldNotBeFound("state.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByStateContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state contains DEFAULT_STATE
        defaultAddressShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the addressList where state contains UPDATED_STATE
        defaultAddressShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    public void getAllAddressesByStateNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where state does not contain DEFAULT_STATE
        defaultAddressShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the addressList where state does not contain UPDATED_STATE
        defaultAddressShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }


    @Test
    @Transactional
    public void getAllAddressesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where country equals to DEFAULT_COUNTRY
        defaultAddressShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the addressList where country equals to UPDATED_COUNTRY
        defaultAddressShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where country not equals to DEFAULT_COUNTRY
        defaultAddressShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the addressList where country not equals to UPDATED_COUNTRY
        defaultAddressShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultAddressShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the addressList where country equals to UPDATED_COUNTRY
        defaultAddressShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where country is not null
        defaultAddressShouldBeFound("country.specified=true");

        // Get all the addressList where country is null
        defaultAddressShouldNotBeFound("country.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByCountryContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where country contains DEFAULT_COUNTRY
        defaultAddressShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the addressList where country contains UPDATED_COUNTRY
        defaultAddressShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void getAllAddressesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where country does not contain DEFAULT_COUNTRY
        defaultAddressShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the addressList where country does not contain UPDATED_COUNTRY
        defaultAddressShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }


    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postalCode equals to DEFAULT_POSTAL_CODE
        defaultAddressShouldBeFound("postalCode.equals=" + DEFAULT_POSTAL_CODE);

        // Get all the addressList where postalCode equals to UPDATED_POSTAL_CODE
        defaultAddressShouldNotBeFound("postalCode.equals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postalCode not equals to DEFAULT_POSTAL_CODE
        defaultAddressShouldNotBeFound("postalCode.notEquals=" + DEFAULT_POSTAL_CODE);

        // Get all the addressList where postalCode not equals to UPDATED_POSTAL_CODE
        defaultAddressShouldBeFound("postalCode.notEquals=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postalCode in DEFAULT_POSTAL_CODE or UPDATED_POSTAL_CODE
        defaultAddressShouldBeFound("postalCode.in=" + DEFAULT_POSTAL_CODE + "," + UPDATED_POSTAL_CODE);

        // Get all the addressList where postalCode equals to UPDATED_POSTAL_CODE
        defaultAddressShouldNotBeFound("postalCode.in=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postalCode is not null
        defaultAddressShouldBeFound("postalCode.specified=true");

        // Get all the addressList where postalCode is null
        defaultAddressShouldNotBeFound("postalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllAddressesByPostalCodeContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postalCode contains DEFAULT_POSTAL_CODE
        defaultAddressShouldBeFound("postalCode.contains=" + DEFAULT_POSTAL_CODE);

        // Get all the addressList where postalCode contains UPDATED_POSTAL_CODE
        defaultAddressShouldNotBeFound("postalCode.contains=" + UPDATED_POSTAL_CODE);
    }

    @Test
    @Transactional
    public void getAllAddressesByPostalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where postalCode does not contain DEFAULT_POSTAL_CODE
        defaultAddressShouldNotBeFound("postalCode.doesNotContain=" + DEFAULT_POSTAL_CODE);

        // Get all the addressList where postalCode does not contain UPDATED_POSTAL_CODE
        defaultAddressShouldBeFound("postalCode.doesNotContain=" + UPDATED_POSTAL_CODE);
    }


    @Test
    @Transactional
    public void getAllAddressesByGeoLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLatitude equals to DEFAULT_GEO_LATITUDE
        defaultAddressShouldBeFound("geoLatitude.equals=" + DEFAULT_GEO_LATITUDE);

        // Get all the addressList where geoLatitude equals to UPDATED_GEO_LATITUDE
        defaultAddressShouldNotBeFound("geoLatitude.equals=" + UPDATED_GEO_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLatitude not equals to DEFAULT_GEO_LATITUDE
        defaultAddressShouldNotBeFound("geoLatitude.notEquals=" + DEFAULT_GEO_LATITUDE);

        // Get all the addressList where geoLatitude not equals to UPDATED_GEO_LATITUDE
        defaultAddressShouldBeFound("geoLatitude.notEquals=" + UPDATED_GEO_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLatitude in DEFAULT_GEO_LATITUDE or UPDATED_GEO_LATITUDE
        defaultAddressShouldBeFound("geoLatitude.in=" + DEFAULT_GEO_LATITUDE + "," + UPDATED_GEO_LATITUDE);

        // Get all the addressList where geoLatitude equals to UPDATED_GEO_LATITUDE
        defaultAddressShouldNotBeFound("geoLatitude.in=" + UPDATED_GEO_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLatitude is not null
        defaultAddressShouldBeFound("geoLatitude.specified=true");

        // Get all the addressList where geoLatitude is null
        defaultAddressShouldNotBeFound("geoLatitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLatitude is greater than or equal to DEFAULT_GEO_LATITUDE
        defaultAddressShouldBeFound("geoLatitude.greaterThanOrEqual=" + DEFAULT_GEO_LATITUDE);

        // Get all the addressList where geoLatitude is greater than or equal to UPDATED_GEO_LATITUDE
        defaultAddressShouldNotBeFound("geoLatitude.greaterThanOrEqual=" + UPDATED_GEO_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLatitude is less than or equal to DEFAULT_GEO_LATITUDE
        defaultAddressShouldBeFound("geoLatitude.lessThanOrEqual=" + DEFAULT_GEO_LATITUDE);

        // Get all the addressList where geoLatitude is less than or equal to SMALLER_GEO_LATITUDE
        defaultAddressShouldNotBeFound("geoLatitude.lessThanOrEqual=" + SMALLER_GEO_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLatitude is less than DEFAULT_GEO_LATITUDE
        defaultAddressShouldNotBeFound("geoLatitude.lessThan=" + DEFAULT_GEO_LATITUDE);

        // Get all the addressList where geoLatitude is less than UPDATED_GEO_LATITUDE
        defaultAddressShouldBeFound("geoLatitude.lessThan=" + UPDATED_GEO_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLatitude is greater than DEFAULT_GEO_LATITUDE
        defaultAddressShouldNotBeFound("geoLatitude.greaterThan=" + DEFAULT_GEO_LATITUDE);

        // Get all the addressList where geoLatitude is greater than SMALLER_GEO_LATITUDE
        defaultAddressShouldBeFound("geoLatitude.greaterThan=" + SMALLER_GEO_LATITUDE);
    }


    @Test
    @Transactional
    public void getAllAddressesByGeoLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLongitude equals to DEFAULT_GEO_LONGITUDE
        defaultAddressShouldBeFound("geoLongitude.equals=" + DEFAULT_GEO_LONGITUDE);

        // Get all the addressList where geoLongitude equals to UPDATED_GEO_LONGITUDE
        defaultAddressShouldNotBeFound("geoLongitude.equals=" + UPDATED_GEO_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLongitude not equals to DEFAULT_GEO_LONGITUDE
        defaultAddressShouldNotBeFound("geoLongitude.notEquals=" + DEFAULT_GEO_LONGITUDE);

        // Get all the addressList where geoLongitude not equals to UPDATED_GEO_LONGITUDE
        defaultAddressShouldBeFound("geoLongitude.notEquals=" + UPDATED_GEO_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLongitude in DEFAULT_GEO_LONGITUDE or UPDATED_GEO_LONGITUDE
        defaultAddressShouldBeFound("geoLongitude.in=" + DEFAULT_GEO_LONGITUDE + "," + UPDATED_GEO_LONGITUDE);

        // Get all the addressList where geoLongitude equals to UPDATED_GEO_LONGITUDE
        defaultAddressShouldNotBeFound("geoLongitude.in=" + UPDATED_GEO_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLongitude is not null
        defaultAddressShouldBeFound("geoLongitude.specified=true");

        // Get all the addressList where geoLongitude is null
        defaultAddressShouldNotBeFound("geoLongitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLongitude is greater than or equal to DEFAULT_GEO_LONGITUDE
        defaultAddressShouldBeFound("geoLongitude.greaterThanOrEqual=" + DEFAULT_GEO_LONGITUDE);

        // Get all the addressList where geoLongitude is greater than or equal to UPDATED_GEO_LONGITUDE
        defaultAddressShouldNotBeFound("geoLongitude.greaterThanOrEqual=" + UPDATED_GEO_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLongitude is less than or equal to DEFAULT_GEO_LONGITUDE
        defaultAddressShouldBeFound("geoLongitude.lessThanOrEqual=" + DEFAULT_GEO_LONGITUDE);

        // Get all the addressList where geoLongitude is less than or equal to SMALLER_GEO_LONGITUDE
        defaultAddressShouldNotBeFound("geoLongitude.lessThanOrEqual=" + SMALLER_GEO_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLongitude is less than DEFAULT_GEO_LONGITUDE
        defaultAddressShouldNotBeFound("geoLongitude.lessThan=" + DEFAULT_GEO_LONGITUDE);

        // Get all the addressList where geoLongitude is less than UPDATED_GEO_LONGITUDE
        defaultAddressShouldBeFound("geoLongitude.lessThan=" + UPDATED_GEO_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllAddressesByGeoLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where geoLongitude is greater than DEFAULT_GEO_LONGITUDE
        defaultAddressShouldNotBeFound("geoLongitude.greaterThan=" + DEFAULT_GEO_LONGITUDE);

        // Get all the addressList where geoLongitude is greater than SMALLER_GEO_LONGITUDE
        defaultAddressShouldBeFound("geoLongitude.greaterThan=" + SMALLER_GEO_LONGITUDE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressShouldBeFound(String filter) throws Exception {
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].geoLatitude").value(hasItem(DEFAULT_GEO_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].geoLongitude").value(hasItem(DEFAULT_GEO_LONGITUDE.doubleValue())));

        // Check, that the count call also returns 1
        restAddressMockMvc.perform(get("/api/addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressShouldNotBeFound(String filter) throws Exception {
        restAddressMockMvc.perform(get("/api/addresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressMockMvc.perform(get("/api/addresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get("/api/addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .street(UPDATED_STREET)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .postalCode(UPDATED_POSTAL_CODE)
            .geoLatitude(UPDATED_GEO_LATITUDE)
            .geoLongitude(UPDATED_GEO_LONGITUDE);
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testAddress.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testAddress.getGeoLatitude()).isEqualTo(UPDATED_GEO_LATITUDE);
        assertThat(testAddress.getGeoLongitude()).isEqualTo(UPDATED_GEO_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc.perform(put("/api/addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Delete the address
        restAddressMockMvc.perform(delete("/api/addresses/{id}", address.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
