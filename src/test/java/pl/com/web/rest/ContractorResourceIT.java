package pl.com.web.rest;

import pl.com.OrdersManagementApp;
import pl.com.domain.Contractor;
import pl.com.repository.ContractorRepository;
import pl.com.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static pl.com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContractorResource} REST controller.
 */
@SpringBootTest(classes = OrdersManagementApp.class)
public class ContractorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VAT_ID = "AAAAAAAAAA";
    private static final String UPDATED_VAT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PERSON = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private ContractorRepository contractorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restContractorMockMvc;

    private Contractor contractor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractorResource contractorResource = new ContractorResource(contractorRepository);
        this.restContractorMockMvc = MockMvcBuilders.standaloneSetup(contractorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contractor createEntity(EntityManager em) {
        Contractor contractor = new Contractor()
            .name(DEFAULT_NAME)
            .vatId(DEFAULT_VAT_ID)
            .contactPerson(DEFAULT_CONTACT_PERSON)
            .phoneNumber(DEFAULT_PHONE_NUMBER);
        return contractor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contractor createUpdatedEntity(EntityManager em) {
        Contractor contractor = new Contractor()
            .name(UPDATED_NAME)
            .vatId(UPDATED_VAT_ID)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .phoneNumber(UPDATED_PHONE_NUMBER);
        return contractor;
    }

    @BeforeEach
    public void initTest() {
        contractor = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractor() throws Exception {
        int databaseSizeBeforeCreate = contractorRepository.findAll().size();

        // Create the Contractor
        restContractorMockMvc.perform(post("/api/contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractor)))
            .andExpect(status().isCreated());

        // Validate the Contractor in the database
        List<Contractor> contractorList = contractorRepository.findAll();
        assertThat(contractorList).hasSize(databaseSizeBeforeCreate + 1);
        Contractor testContractor = contractorList.get(contractorList.size() - 1);
        assertThat(testContractor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContractor.getVatId()).isEqualTo(DEFAULT_VAT_ID);
        assertThat(testContractor.getContactPerson()).isEqualTo(DEFAULT_CONTACT_PERSON);
        assertThat(testContractor.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void createContractorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractorRepository.findAll().size();

        // Create the Contractor with an existing ID
        contractor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractorMockMvc.perform(post("/api/contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractor)))
            .andExpect(status().isBadRequest());

        // Validate the Contractor in the database
        List<Contractor> contractorList = contractorRepository.findAll();
        assertThat(contractorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContractors() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get all the contractorList
        restContractorMockMvc.perform(get("/api/contractors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vatId").value(hasItem(DEFAULT_VAT_ID)))
            .andExpect(jsonPath("$.[*].contactPerson").value(hasItem(DEFAULT_CONTACT_PERSON)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getContractor() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        // Get the contractor
        restContractorMockMvc.perform(get("/api/contractors/{id}", contractor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.vatId").value(DEFAULT_VAT_ID))
            .andExpect(jsonPath("$.contactPerson").value(DEFAULT_CONTACT_PERSON))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingContractor() throws Exception {
        // Get the contractor
        restContractorMockMvc.perform(get("/api/contractors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractor() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        int databaseSizeBeforeUpdate = contractorRepository.findAll().size();

        // Update the contractor
        Contractor updatedContractor = contractorRepository.findById(contractor.getId()).get();
        // Disconnect from session so that the updates on updatedContractor are not directly saved in db
        em.detach(updatedContractor);
        updatedContractor
            .name(UPDATED_NAME)
            .vatId(UPDATED_VAT_ID)
            .contactPerson(UPDATED_CONTACT_PERSON)
            .phoneNumber(UPDATED_PHONE_NUMBER);

        restContractorMockMvc.perform(put("/api/contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContractor)))
            .andExpect(status().isOk());

        // Validate the Contractor in the database
        List<Contractor> contractorList = contractorRepository.findAll();
        assertThat(contractorList).hasSize(databaseSizeBeforeUpdate);
        Contractor testContractor = contractorList.get(contractorList.size() - 1);
        assertThat(testContractor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContractor.getVatId()).isEqualTo(UPDATED_VAT_ID);
        assertThat(testContractor.getContactPerson()).isEqualTo(UPDATED_CONTACT_PERSON);
        assertThat(testContractor.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingContractor() throws Exception {
        int databaseSizeBeforeUpdate = contractorRepository.findAll().size();

        // Create the Contractor

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractorMockMvc.perform(put("/api/contractors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractor)))
            .andExpect(status().isBadRequest());

        // Validate the Contractor in the database
        List<Contractor> contractorList = contractorRepository.findAll();
        assertThat(contractorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContractor() throws Exception {
        // Initialize the database
        contractorRepository.saveAndFlush(contractor);

        int databaseSizeBeforeDelete = contractorRepository.findAll().size();

        // Delete the contractor
        restContractorMockMvc.perform(delete("/api/contractors/{id}", contractor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contractor> contractorList = contractorRepository.findAll();
        assertThat(contractorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
