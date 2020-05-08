package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.EnquiryMaterial;
import com.mycompany.myapp.domain.EnquiryDetails;
import com.mycompany.myapp.repository.EnquiryMaterialRepository;
import com.mycompany.myapp.service.EnquiryMaterialService;
import com.mycompany.myapp.service.dto.EnquiryMaterialDTO;
import com.mycompany.myapp.service.mapper.EnquiryMaterialMapper;
import com.mycompany.myapp.service.dto.EnquiryMaterialCriteria;
import com.mycompany.myapp.service.EnquiryMaterialQueryService;

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
 * Integration tests for the {@link EnquiryMaterialResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class EnquiryMaterialResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DIMENSION = "AAAAAAAAAA";
    private static final String UPDATED_DIMENSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MATERIAL_ID = 1;
    private static final Integer UPDATED_MATERIAL_ID = 2;
    private static final Integer SMALLER_MATERIAL_ID = 1 - 1;

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private EnquiryMaterialRepository enquiryMaterialRepository;

    @Autowired
    private EnquiryMaterialMapper enquiryMaterialMapper;

    @Autowired
    private EnquiryMaterialService enquiryMaterialService;

    @Autowired
    private EnquiryMaterialQueryService enquiryMaterialQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnquiryMaterialMockMvc;

    private EnquiryMaterial enquiryMaterial;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnquiryMaterial createEntity(EntityManager em) {
        EnquiryMaterial enquiryMaterial = new EnquiryMaterial()
            .name(DEFAULT_NAME)
            .dimension(DEFAULT_DIMENSION)
            .materialId(DEFAULT_MATERIAL_ID)
            .color(DEFAULT_COLOR)
            .comments(DEFAULT_COMMENTS);
        // Add required entity
        EnquiryDetails enquiryDetails;
        if (TestUtil.findAll(em, EnquiryDetails.class).isEmpty()) {
            enquiryDetails = EnquiryDetailsResourceIT.createEntity(em);
            em.persist(enquiryDetails);
            em.flush();
        } else {
            enquiryDetails = TestUtil.findAll(em, EnquiryDetails.class).get(0);
        }
        enquiryMaterial.setEnquiryDetails(enquiryDetails);
        return enquiryMaterial;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EnquiryMaterial createUpdatedEntity(EntityManager em) {
        EnquiryMaterial enquiryMaterial = new EnquiryMaterial()
            .name(UPDATED_NAME)
            .dimension(UPDATED_DIMENSION)
            .materialId(UPDATED_MATERIAL_ID)
            .color(UPDATED_COLOR)
            .comments(UPDATED_COMMENTS);
        // Add required entity
        EnquiryDetails enquiryDetails;
        if (TestUtil.findAll(em, EnquiryDetails.class).isEmpty()) {
            enquiryDetails = EnquiryDetailsResourceIT.createUpdatedEntity(em);
            em.persist(enquiryDetails);
            em.flush();
        } else {
            enquiryDetails = TestUtil.findAll(em, EnquiryDetails.class).get(0);
        }
        enquiryMaterial.setEnquiryDetails(enquiryDetails);
        return enquiryMaterial;
    }

    @BeforeEach
    public void initTest() {
        enquiryMaterial = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnquiryMaterial() throws Exception {
        int databaseSizeBeforeCreate = enquiryMaterialRepository.findAll().size();

        // Create the EnquiryMaterial
        EnquiryMaterialDTO enquiryMaterialDTO = enquiryMaterialMapper.toDto(enquiryMaterial);
        restEnquiryMaterialMockMvc.perform(post("/api/enquiry-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryMaterialDTO)))
            .andExpect(status().isCreated());

        // Validate the EnquiryMaterial in the database
        List<EnquiryMaterial> enquiryMaterialList = enquiryMaterialRepository.findAll();
        assertThat(enquiryMaterialList).hasSize(databaseSizeBeforeCreate + 1);
        EnquiryMaterial testEnquiryMaterial = enquiryMaterialList.get(enquiryMaterialList.size() - 1);
        assertThat(testEnquiryMaterial.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEnquiryMaterial.getDimension()).isEqualTo(DEFAULT_DIMENSION);
        assertThat(testEnquiryMaterial.getMaterialId()).isEqualTo(DEFAULT_MATERIAL_ID);
        assertThat(testEnquiryMaterial.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testEnquiryMaterial.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createEnquiryMaterialWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = enquiryMaterialRepository.findAll().size();

        // Create the EnquiryMaterial with an existing ID
        enquiryMaterial.setId(1L);
        EnquiryMaterialDTO enquiryMaterialDTO = enquiryMaterialMapper.toDto(enquiryMaterial);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnquiryMaterialMockMvc.perform(post("/api/enquiry-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryMaterialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnquiryMaterial in the database
        List<EnquiryMaterial> enquiryMaterialList = enquiryMaterialRepository.findAll();
        assertThat(enquiryMaterialList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryMaterialRepository.findAll().size();
        // set the field null
        enquiryMaterial.setName(null);

        // Create the EnquiryMaterial, which fails.
        EnquiryMaterialDTO enquiryMaterialDTO = enquiryMaterialMapper.toDto(enquiryMaterial);

        restEnquiryMaterialMockMvc.perform(post("/api/enquiry-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryMaterial> enquiryMaterialList = enquiryMaterialRepository.findAll();
        assertThat(enquiryMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDimensionIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryMaterialRepository.findAll().size();
        // set the field null
        enquiryMaterial.setDimension(null);

        // Create the EnquiryMaterial, which fails.
        EnquiryMaterialDTO enquiryMaterialDTO = enquiryMaterialMapper.toDto(enquiryMaterial);

        restEnquiryMaterialMockMvc.perform(post("/api/enquiry-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryMaterial> enquiryMaterialList = enquiryMaterialRepository.findAll();
        assertThat(enquiryMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMaterialIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryMaterialRepository.findAll().size();
        // set the field null
        enquiryMaterial.setMaterialId(null);

        // Create the EnquiryMaterial, which fails.
        EnquiryMaterialDTO enquiryMaterialDTO = enquiryMaterialMapper.toDto(enquiryMaterial);

        restEnquiryMaterialMockMvc.perform(post("/api/enquiry-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryMaterial> enquiryMaterialList = enquiryMaterialRepository.findAll();
        assertThat(enquiryMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkColorIsRequired() throws Exception {
        int databaseSizeBeforeTest = enquiryMaterialRepository.findAll().size();
        // set the field null
        enquiryMaterial.setColor(null);

        // Create the EnquiryMaterial, which fails.
        EnquiryMaterialDTO enquiryMaterialDTO = enquiryMaterialMapper.toDto(enquiryMaterial);

        restEnquiryMaterialMockMvc.perform(post("/api/enquiry-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryMaterialDTO)))
            .andExpect(status().isBadRequest());

        List<EnquiryMaterial> enquiryMaterialList = enquiryMaterialRepository.findAll();
        assertThat(enquiryMaterialList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterials() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList
        restEnquiryMaterialMockMvc.perform(get("/api/enquiry-materials?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquiryMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dimension").value(hasItem(DEFAULT_DIMENSION)))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getEnquiryMaterial() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get the enquiryMaterial
        restEnquiryMaterialMockMvc.perform(get("/api/enquiry-materials/{id}", enquiryMaterial.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(enquiryMaterial.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dimension").value(DEFAULT_DIMENSION))
            .andExpect(jsonPath("$.materialId").value(DEFAULT_MATERIAL_ID))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }


    @Test
    @Transactional
    public void getEnquiryMaterialsByIdFiltering() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        Long id = enquiryMaterial.getId();

        defaultEnquiryMaterialShouldBeFound("id.equals=" + id);
        defaultEnquiryMaterialShouldNotBeFound("id.notEquals=" + id);

        defaultEnquiryMaterialShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEnquiryMaterialShouldNotBeFound("id.greaterThan=" + id);

        defaultEnquiryMaterialShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEnquiryMaterialShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEnquiryMaterialsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where name equals to DEFAULT_NAME
        defaultEnquiryMaterialShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the enquiryMaterialList where name equals to UPDATED_NAME
        defaultEnquiryMaterialShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where name not equals to DEFAULT_NAME
        defaultEnquiryMaterialShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the enquiryMaterialList where name not equals to UPDATED_NAME
        defaultEnquiryMaterialShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where name in DEFAULT_NAME or UPDATED_NAME
        defaultEnquiryMaterialShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the enquiryMaterialList where name equals to UPDATED_NAME
        defaultEnquiryMaterialShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where name is not null
        defaultEnquiryMaterialShouldBeFound("name.specified=true");

        // Get all the enquiryMaterialList where name is null
        defaultEnquiryMaterialShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnquiryMaterialsByNameContainsSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where name contains DEFAULT_NAME
        defaultEnquiryMaterialShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the enquiryMaterialList where name contains UPDATED_NAME
        defaultEnquiryMaterialShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where name does not contain DEFAULT_NAME
        defaultEnquiryMaterialShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the enquiryMaterialList where name does not contain UPDATED_NAME
        defaultEnquiryMaterialShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllEnquiryMaterialsByDimensionIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where dimension equals to DEFAULT_DIMENSION
        defaultEnquiryMaterialShouldBeFound("dimension.equals=" + DEFAULT_DIMENSION);

        // Get all the enquiryMaterialList where dimension equals to UPDATED_DIMENSION
        defaultEnquiryMaterialShouldNotBeFound("dimension.equals=" + UPDATED_DIMENSION);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByDimensionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where dimension not equals to DEFAULT_DIMENSION
        defaultEnquiryMaterialShouldNotBeFound("dimension.notEquals=" + DEFAULT_DIMENSION);

        // Get all the enquiryMaterialList where dimension not equals to UPDATED_DIMENSION
        defaultEnquiryMaterialShouldBeFound("dimension.notEquals=" + UPDATED_DIMENSION);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByDimensionIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where dimension in DEFAULT_DIMENSION or UPDATED_DIMENSION
        defaultEnquiryMaterialShouldBeFound("dimension.in=" + DEFAULT_DIMENSION + "," + UPDATED_DIMENSION);

        // Get all the enquiryMaterialList where dimension equals to UPDATED_DIMENSION
        defaultEnquiryMaterialShouldNotBeFound("dimension.in=" + UPDATED_DIMENSION);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByDimensionIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where dimension is not null
        defaultEnquiryMaterialShouldBeFound("dimension.specified=true");

        // Get all the enquiryMaterialList where dimension is null
        defaultEnquiryMaterialShouldNotBeFound("dimension.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnquiryMaterialsByDimensionContainsSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where dimension contains DEFAULT_DIMENSION
        defaultEnquiryMaterialShouldBeFound("dimension.contains=" + DEFAULT_DIMENSION);

        // Get all the enquiryMaterialList where dimension contains UPDATED_DIMENSION
        defaultEnquiryMaterialShouldNotBeFound("dimension.contains=" + UPDATED_DIMENSION);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByDimensionNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where dimension does not contain DEFAULT_DIMENSION
        defaultEnquiryMaterialShouldNotBeFound("dimension.doesNotContain=" + DEFAULT_DIMENSION);

        // Get all the enquiryMaterialList where dimension does not contain UPDATED_DIMENSION
        defaultEnquiryMaterialShouldBeFound("dimension.doesNotContain=" + UPDATED_DIMENSION);
    }


    @Test
    @Transactional
    public void getAllEnquiryMaterialsByMaterialIdIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where materialId equals to DEFAULT_MATERIAL_ID
        defaultEnquiryMaterialShouldBeFound("materialId.equals=" + DEFAULT_MATERIAL_ID);

        // Get all the enquiryMaterialList where materialId equals to UPDATED_MATERIAL_ID
        defaultEnquiryMaterialShouldNotBeFound("materialId.equals=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByMaterialIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where materialId not equals to DEFAULT_MATERIAL_ID
        defaultEnquiryMaterialShouldNotBeFound("materialId.notEquals=" + DEFAULT_MATERIAL_ID);

        // Get all the enquiryMaterialList where materialId not equals to UPDATED_MATERIAL_ID
        defaultEnquiryMaterialShouldBeFound("materialId.notEquals=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByMaterialIdIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where materialId in DEFAULT_MATERIAL_ID or UPDATED_MATERIAL_ID
        defaultEnquiryMaterialShouldBeFound("materialId.in=" + DEFAULT_MATERIAL_ID + "," + UPDATED_MATERIAL_ID);

        // Get all the enquiryMaterialList where materialId equals to UPDATED_MATERIAL_ID
        defaultEnquiryMaterialShouldNotBeFound("materialId.in=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByMaterialIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where materialId is not null
        defaultEnquiryMaterialShouldBeFound("materialId.specified=true");

        // Get all the enquiryMaterialList where materialId is null
        defaultEnquiryMaterialShouldNotBeFound("materialId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByMaterialIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where materialId is greater than or equal to DEFAULT_MATERIAL_ID
        defaultEnquiryMaterialShouldBeFound("materialId.greaterThanOrEqual=" + DEFAULT_MATERIAL_ID);

        // Get all the enquiryMaterialList where materialId is greater than or equal to UPDATED_MATERIAL_ID
        defaultEnquiryMaterialShouldNotBeFound("materialId.greaterThanOrEqual=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByMaterialIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where materialId is less than or equal to DEFAULT_MATERIAL_ID
        defaultEnquiryMaterialShouldBeFound("materialId.lessThanOrEqual=" + DEFAULT_MATERIAL_ID);

        // Get all the enquiryMaterialList where materialId is less than or equal to SMALLER_MATERIAL_ID
        defaultEnquiryMaterialShouldNotBeFound("materialId.lessThanOrEqual=" + SMALLER_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByMaterialIdIsLessThanSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where materialId is less than DEFAULT_MATERIAL_ID
        defaultEnquiryMaterialShouldNotBeFound("materialId.lessThan=" + DEFAULT_MATERIAL_ID);

        // Get all the enquiryMaterialList where materialId is less than UPDATED_MATERIAL_ID
        defaultEnquiryMaterialShouldBeFound("materialId.lessThan=" + UPDATED_MATERIAL_ID);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByMaterialIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where materialId is greater than DEFAULT_MATERIAL_ID
        defaultEnquiryMaterialShouldNotBeFound("materialId.greaterThan=" + DEFAULT_MATERIAL_ID);

        // Get all the enquiryMaterialList where materialId is greater than SMALLER_MATERIAL_ID
        defaultEnquiryMaterialShouldBeFound("materialId.greaterThan=" + SMALLER_MATERIAL_ID);
    }


    @Test
    @Transactional
    public void getAllEnquiryMaterialsByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where color equals to DEFAULT_COLOR
        defaultEnquiryMaterialShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the enquiryMaterialList where color equals to UPDATED_COLOR
        defaultEnquiryMaterialShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where color not equals to DEFAULT_COLOR
        defaultEnquiryMaterialShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the enquiryMaterialList where color not equals to UPDATED_COLOR
        defaultEnquiryMaterialShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByColorIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultEnquiryMaterialShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the enquiryMaterialList where color equals to UPDATED_COLOR
        defaultEnquiryMaterialShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where color is not null
        defaultEnquiryMaterialShouldBeFound("color.specified=true");

        // Get all the enquiryMaterialList where color is null
        defaultEnquiryMaterialShouldNotBeFound("color.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnquiryMaterialsByColorContainsSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where color contains DEFAULT_COLOR
        defaultEnquiryMaterialShouldBeFound("color.contains=" + DEFAULT_COLOR);

        // Get all the enquiryMaterialList where color contains UPDATED_COLOR
        defaultEnquiryMaterialShouldNotBeFound("color.contains=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByColorNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where color does not contain DEFAULT_COLOR
        defaultEnquiryMaterialShouldNotBeFound("color.doesNotContain=" + DEFAULT_COLOR);

        // Get all the enquiryMaterialList where color does not contain UPDATED_COLOR
        defaultEnquiryMaterialShouldBeFound("color.doesNotContain=" + UPDATED_COLOR);
    }


    @Test
    @Transactional
    public void getAllEnquiryMaterialsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where comments equals to DEFAULT_COMMENTS
        defaultEnquiryMaterialShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the enquiryMaterialList where comments equals to UPDATED_COMMENTS
        defaultEnquiryMaterialShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where comments not equals to DEFAULT_COMMENTS
        defaultEnquiryMaterialShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the enquiryMaterialList where comments not equals to UPDATED_COMMENTS
        defaultEnquiryMaterialShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultEnquiryMaterialShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the enquiryMaterialList where comments equals to UPDATED_COMMENTS
        defaultEnquiryMaterialShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where comments is not null
        defaultEnquiryMaterialShouldBeFound("comments.specified=true");

        // Get all the enquiryMaterialList where comments is null
        defaultEnquiryMaterialShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllEnquiryMaterialsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where comments contains DEFAULT_COMMENTS
        defaultEnquiryMaterialShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the enquiryMaterialList where comments contains UPDATED_COMMENTS
        defaultEnquiryMaterialShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllEnquiryMaterialsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        // Get all the enquiryMaterialList where comments does not contain DEFAULT_COMMENTS
        defaultEnquiryMaterialShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the enquiryMaterialList where comments does not contain UPDATED_COMMENTS
        defaultEnquiryMaterialShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllEnquiryMaterialsByEnquiryDetailsIsEqualToSomething() throws Exception {
        // Get already existing entity
        EnquiryDetails enquiryDetails = enquiryMaterial.getEnquiryDetails();
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);
        Long enquiryDetailsId = enquiryDetails.getId();

        // Get all the enquiryMaterialList where enquiryDetails equals to enquiryDetailsId
        defaultEnquiryMaterialShouldBeFound("enquiryDetailsId.equals=" + enquiryDetailsId);

        // Get all the enquiryMaterialList where enquiryDetails equals to enquiryDetailsId + 1
        defaultEnquiryMaterialShouldNotBeFound("enquiryDetailsId.equals=" + (enquiryDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEnquiryMaterialShouldBeFound(String filter) throws Exception {
        restEnquiryMaterialMockMvc.perform(get("/api/enquiry-materials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(enquiryMaterial.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dimension").value(hasItem(DEFAULT_DIMENSION)))
            .andExpect(jsonPath("$.[*].materialId").value(hasItem(DEFAULT_MATERIAL_ID)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));

        // Check, that the count call also returns 1
        restEnquiryMaterialMockMvc.perform(get("/api/enquiry-materials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEnquiryMaterialShouldNotBeFound(String filter) throws Exception {
        restEnquiryMaterialMockMvc.perform(get("/api/enquiry-materials?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEnquiryMaterialMockMvc.perform(get("/api/enquiry-materials/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEnquiryMaterial() throws Exception {
        // Get the enquiryMaterial
        restEnquiryMaterialMockMvc.perform(get("/api/enquiry-materials/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnquiryMaterial() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        int databaseSizeBeforeUpdate = enquiryMaterialRepository.findAll().size();

        // Update the enquiryMaterial
        EnquiryMaterial updatedEnquiryMaterial = enquiryMaterialRepository.findById(enquiryMaterial.getId()).get();
        // Disconnect from session so that the updates on updatedEnquiryMaterial are not directly saved in db
        em.detach(updatedEnquiryMaterial);
        updatedEnquiryMaterial
            .name(UPDATED_NAME)
            .dimension(UPDATED_DIMENSION)
            .materialId(UPDATED_MATERIAL_ID)
            .color(UPDATED_COLOR)
            .comments(UPDATED_COMMENTS);
        EnquiryMaterialDTO enquiryMaterialDTO = enquiryMaterialMapper.toDto(updatedEnquiryMaterial);

        restEnquiryMaterialMockMvc.perform(put("/api/enquiry-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryMaterialDTO)))
            .andExpect(status().isOk());

        // Validate the EnquiryMaterial in the database
        List<EnquiryMaterial> enquiryMaterialList = enquiryMaterialRepository.findAll();
        assertThat(enquiryMaterialList).hasSize(databaseSizeBeforeUpdate);
        EnquiryMaterial testEnquiryMaterial = enquiryMaterialList.get(enquiryMaterialList.size() - 1);
        assertThat(testEnquiryMaterial.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEnquiryMaterial.getDimension()).isEqualTo(UPDATED_DIMENSION);
        assertThat(testEnquiryMaterial.getMaterialId()).isEqualTo(UPDATED_MATERIAL_ID);
        assertThat(testEnquiryMaterial.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testEnquiryMaterial.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingEnquiryMaterial() throws Exception {
        int databaseSizeBeforeUpdate = enquiryMaterialRepository.findAll().size();

        // Create the EnquiryMaterial
        EnquiryMaterialDTO enquiryMaterialDTO = enquiryMaterialMapper.toDto(enquiryMaterial);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnquiryMaterialMockMvc.perform(put("/api/enquiry-materials")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(enquiryMaterialDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EnquiryMaterial in the database
        List<EnquiryMaterial> enquiryMaterialList = enquiryMaterialRepository.findAll();
        assertThat(enquiryMaterialList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEnquiryMaterial() throws Exception {
        // Initialize the database
        enquiryMaterialRepository.saveAndFlush(enquiryMaterial);

        int databaseSizeBeforeDelete = enquiryMaterialRepository.findAll().size();

        // Delete the enquiryMaterial
        restEnquiryMaterialMockMvc.perform(delete("/api/enquiry-materials/{id}", enquiryMaterial.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EnquiryMaterial> enquiryMaterialList = enquiryMaterialRepository.findAll();
        assertThat(enquiryMaterialList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
