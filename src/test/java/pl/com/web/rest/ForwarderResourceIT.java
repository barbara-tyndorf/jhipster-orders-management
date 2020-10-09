package pl.com.web.rest;

import pl.com.OrdersManagementApp;
import pl.com.domain.Forwarder;
import pl.com.repository.ForwarderRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static pl.com.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pl.com.domain.enumeration.Branch;
/**
 * Integration tests for the {@link ForwarderResource} REST controller.
 */
@SpringBootTest(classes = OrdersManagementApp.class)
public class ForwarderResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_HIRE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HIRE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_SALARY = 1L;
    private static final Long UPDATED_SALARY = 2L;

    private static final Branch DEFAULT_BRANCH = Branch.SZC;
    private static final Branch UPDATED_BRANCH = Branch.WAW;

    @Autowired
    private ForwarderRepository forwarderRepository;

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

    private MockMvc restForwarderMockMvc;

    private Forwarder forwarder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ForwarderResource forwarderResource = new ForwarderResource(forwarderRepository);
        this.restForwarderMockMvc = MockMvcBuilders.standaloneSetup(forwarderResource)
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
    public static Forwarder createEntity(EntityManager em) {
        Forwarder forwarder = new Forwarder()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .hireDate(DEFAULT_HIRE_DATE)
            .salary(DEFAULT_SALARY)
            .branch(DEFAULT_BRANCH);
        return forwarder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Forwarder createUpdatedEntity(EntityManager em) {
        Forwarder forwarder = new Forwarder()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .branch(UPDATED_BRANCH);
        return forwarder;
    }

    @BeforeEach
    public void initTest() {
        forwarder = createEntity(em);
    }

    @Test
    @Transactional
    public void createForwarder() throws Exception {
        int databaseSizeBeforeCreate = forwarderRepository.findAll().size();

        // Create the Forwarder
        restForwarderMockMvc.perform(post("/api/forwarders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forwarder)))
            .andExpect(status().isCreated());

        // Validate the Forwarder in the database
        List<Forwarder> forwarderList = forwarderRepository.findAll();
        assertThat(forwarderList).hasSize(databaseSizeBeforeCreate + 1);
        Forwarder testForwarder = forwarderList.get(forwarderList.size() - 1);
        assertThat(testForwarder.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testForwarder.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testForwarder.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testForwarder.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testForwarder.getHireDate()).isEqualTo(DEFAULT_HIRE_DATE);
        assertThat(testForwarder.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testForwarder.getBranch()).isEqualTo(DEFAULT_BRANCH);
    }

    @Test
    @Transactional
    public void createForwarderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = forwarderRepository.findAll().size();

        // Create the Forwarder with an existing ID
        forwarder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restForwarderMockMvc.perform(post("/api/forwarders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forwarder)))
            .andExpect(status().isBadRequest());

        // Validate the Forwarder in the database
        List<Forwarder> forwarderList = forwarderRepository.findAll();
        assertThat(forwarderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllForwarders() throws Exception {
        // Initialize the database
        forwarderRepository.saveAndFlush(forwarder);

        // Get all the forwarderList
        restForwarderMockMvc.perform(get("/api/forwarders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(forwarder.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].hireDate").value(hasItem(DEFAULT_HIRE_DATE.toString())))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.intValue())))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH.toString())));
    }
    
    @Test
    @Transactional
    public void getForwarder() throws Exception {
        // Initialize the database
        forwarderRepository.saveAndFlush(forwarder);

        // Get the forwarder
        restForwarderMockMvc.perform(get("/api/forwarders/{id}", forwarder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(forwarder.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.hireDate").value(DEFAULT_HIRE_DATE.toString()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.intValue()))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingForwarder() throws Exception {
        // Get the forwarder
        restForwarderMockMvc.perform(get("/api/forwarders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForwarder() throws Exception {
        // Initialize the database
        forwarderRepository.saveAndFlush(forwarder);

        int databaseSizeBeforeUpdate = forwarderRepository.findAll().size();

        // Update the forwarder
        Forwarder updatedForwarder = forwarderRepository.findById(forwarder.getId()).get();
        // Disconnect from session so that the updates on updatedForwarder are not directly saved in db
        em.detach(updatedForwarder);
        updatedForwarder
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .hireDate(UPDATED_HIRE_DATE)
            .salary(UPDATED_SALARY)
            .branch(UPDATED_BRANCH);

        restForwarderMockMvc.perform(put("/api/forwarders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedForwarder)))
            .andExpect(status().isOk());

        // Validate the Forwarder in the database
        List<Forwarder> forwarderList = forwarderRepository.findAll();
        assertThat(forwarderList).hasSize(databaseSizeBeforeUpdate);
        Forwarder testForwarder = forwarderList.get(forwarderList.size() - 1);
        assertThat(testForwarder.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testForwarder.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testForwarder.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testForwarder.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testForwarder.getHireDate()).isEqualTo(UPDATED_HIRE_DATE);
        assertThat(testForwarder.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testForwarder.getBranch()).isEqualTo(UPDATED_BRANCH);
    }

    @Test
    @Transactional
    public void updateNonExistingForwarder() throws Exception {
        int databaseSizeBeforeUpdate = forwarderRepository.findAll().size();

        // Create the Forwarder

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restForwarderMockMvc.perform(put("/api/forwarders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(forwarder)))
            .andExpect(status().isBadRequest());

        // Validate the Forwarder in the database
        List<Forwarder> forwarderList = forwarderRepository.findAll();
        assertThat(forwarderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteForwarder() throws Exception {
        // Initialize the database
        forwarderRepository.saveAndFlush(forwarder);

        int databaseSizeBeforeDelete = forwarderRepository.findAll().size();

        // Delete the forwarder
        restForwarderMockMvc.perform(delete("/api/forwarders/{id}", forwarder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Forwarder> forwarderList = forwarderRepository.findAll();
        assertThat(forwarderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
