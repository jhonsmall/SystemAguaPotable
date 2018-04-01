package devs.team.net.web.rest;

import devs.team.net.SystemAguaPotableApp;

import devs.team.net.domain.Medidor;
import devs.team.net.repository.MedidorRepository;
import devs.team.net.service.MedidorService;
import devs.team.net.repository.search.MedidorSearchRepository;
import devs.team.net.service.dto.MedidorDTO;
import devs.team.net.service.mapper.MedidorMapper;
import devs.team.net.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static devs.team.net.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MedidorResource REST controller.
 *
 * @see MedidorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemAguaPotableApp.class)
public class MedidorResourceIntTest {

    private static final Integer DEFAULT_NUMEROMEDIDOR = 1;
    private static final Integer UPDATED_NUMEROMEDIDOR = 2;

    private static final Instant DEFAULT_FECHAADQUIRIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHAADQUIRIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHAACTUAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHAACTUAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MedidorRepository medidorRepository;

    @Autowired
    private MedidorMapper medidorMapper;

    @Autowired
    private MedidorService medidorService;

    @Autowired
    private MedidorSearchRepository medidorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedidorMockMvc;

    private Medidor medidor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedidorResource medidorResource = new MedidorResource(medidorService);
        this.restMedidorMockMvc = MockMvcBuilders.standaloneSetup(medidorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medidor createEntity(EntityManager em) {
        Medidor medidor = new Medidor()
            .numeromedidor(DEFAULT_NUMEROMEDIDOR)
            .fechaadquirio(DEFAULT_FECHAADQUIRIO)
            .fechaactual(DEFAULT_FECHAACTUAL);
        return medidor;
    }

    @Before
    public void initTest() {
        medidorSearchRepository.deleteAll();
        medidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedidor() throws Exception {
        int databaseSizeBeforeCreate = medidorRepository.findAll().size();

        // Create the Medidor
        MedidorDTO medidorDTO = medidorMapper.toDto(medidor);
        restMedidorMockMvc.perform(post("/api/medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidorDTO)))
            .andExpect(status().isCreated());

        // Validate the Medidor in the database
        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeCreate + 1);
        Medidor testMedidor = medidorList.get(medidorList.size() - 1);
        assertThat(testMedidor.getNumeromedidor()).isEqualTo(DEFAULT_NUMEROMEDIDOR);
        assertThat(testMedidor.getFechaadquirio()).isEqualTo(DEFAULT_FECHAADQUIRIO);
        assertThat(testMedidor.getFechaactual()).isEqualTo(DEFAULT_FECHAACTUAL);

        // Validate the Medidor in Elasticsearch
        Medidor medidorEs = medidorSearchRepository.findOne(testMedidor.getId());
        assertThat(medidorEs).isEqualToIgnoringGivenFields(testMedidor);
    }

    @Test
    @Transactional
    public void createMedidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medidorRepository.findAll().size();

        // Create the Medidor with an existing ID
        medidor.setId(1L);
        MedidorDTO medidorDTO = medidorMapper.toDto(medidor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedidorMockMvc.perform(post("/api/medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medidor in the database
        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeromedidorIsRequired() throws Exception {
        int databaseSizeBeforeTest = medidorRepository.findAll().size();
        // set the field null
        medidor.setNumeromedidor(null);

        // Create the Medidor, which fails.
        MedidorDTO medidorDTO = medidorMapper.toDto(medidor);

        restMedidorMockMvc.perform(post("/api/medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidorDTO)))
            .andExpect(status().isBadRequest());

        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaadquirioIsRequired() throws Exception {
        int databaseSizeBeforeTest = medidorRepository.findAll().size();
        // set the field null
        medidor.setFechaadquirio(null);

        // Create the Medidor, which fails.
        MedidorDTO medidorDTO = medidorMapper.toDto(medidor);

        restMedidorMockMvc.perform(post("/api/medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidorDTO)))
            .andExpect(status().isBadRequest());

        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaactualIsRequired() throws Exception {
        int databaseSizeBeforeTest = medidorRepository.findAll().size();
        // set the field null
        medidor.setFechaactual(null);

        // Create the Medidor, which fails.
        MedidorDTO medidorDTO = medidorMapper.toDto(medidor);

        restMedidorMockMvc.perform(post("/api/medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidorDTO)))
            .andExpect(status().isBadRequest());

        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedidors() throws Exception {
        // Initialize the database
        medidorRepository.saveAndFlush(medidor);

        // Get all the medidorList
        restMedidorMockMvc.perform(get("/api/medidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeromedidor").value(hasItem(DEFAULT_NUMEROMEDIDOR)))
            .andExpect(jsonPath("$.[*].fechaadquirio").value(hasItem(DEFAULT_FECHAADQUIRIO.toString())))
            .andExpect(jsonPath("$.[*].fechaactual").value(hasItem(DEFAULT_FECHAACTUAL.toString())));
    }

    @Test
    @Transactional
    public void getMedidor() throws Exception {
        // Initialize the database
        medidorRepository.saveAndFlush(medidor);

        // Get the medidor
        restMedidorMockMvc.perform(get("/api/medidors/{id}", medidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medidor.getId().intValue()))
            .andExpect(jsonPath("$.numeromedidor").value(DEFAULT_NUMEROMEDIDOR))
            .andExpect(jsonPath("$.fechaadquirio").value(DEFAULT_FECHAADQUIRIO.toString()))
            .andExpect(jsonPath("$.fechaactual").value(DEFAULT_FECHAACTUAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedidor() throws Exception {
        // Get the medidor
        restMedidorMockMvc.perform(get("/api/medidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedidor() throws Exception {
        // Initialize the database
        medidorRepository.saveAndFlush(medidor);
        medidorSearchRepository.save(medidor);
        int databaseSizeBeforeUpdate = medidorRepository.findAll().size();

        // Update the medidor
        Medidor updatedMedidor = medidorRepository.findOne(medidor.getId());
        // Disconnect from session so that the updates on updatedMedidor are not directly saved in db
        em.detach(updatedMedidor);
        updatedMedidor
            .numeromedidor(UPDATED_NUMEROMEDIDOR)
            .fechaadquirio(UPDATED_FECHAADQUIRIO)
            .fechaactual(UPDATED_FECHAACTUAL);
        MedidorDTO medidorDTO = medidorMapper.toDto(updatedMedidor);

        restMedidorMockMvc.perform(put("/api/medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidorDTO)))
            .andExpect(status().isOk());

        // Validate the Medidor in the database
        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeUpdate);
        Medidor testMedidor = medidorList.get(medidorList.size() - 1);
        assertThat(testMedidor.getNumeromedidor()).isEqualTo(UPDATED_NUMEROMEDIDOR);
        assertThat(testMedidor.getFechaadquirio()).isEqualTo(UPDATED_FECHAADQUIRIO);
        assertThat(testMedidor.getFechaactual()).isEqualTo(UPDATED_FECHAACTUAL);

        // Validate the Medidor in Elasticsearch
        Medidor medidorEs = medidorSearchRepository.findOne(testMedidor.getId());
        assertThat(medidorEs).isEqualToIgnoringGivenFields(testMedidor);
    }

    @Test
    @Transactional
    public void updateNonExistingMedidor() throws Exception {
        int databaseSizeBeforeUpdate = medidorRepository.findAll().size();

        // Create the Medidor
        MedidorDTO medidorDTO = medidorMapper.toDto(medidor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedidorMockMvc.perform(put("/api/medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidorDTO)))
            .andExpect(status().isCreated());

        // Validate the Medidor in the database
        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedidor() throws Exception {
        // Initialize the database
        medidorRepository.saveAndFlush(medidor);
        medidorSearchRepository.save(medidor);
        int databaseSizeBeforeDelete = medidorRepository.findAll().size();

        // Get the medidor
        restMedidorMockMvc.perform(delete("/api/medidors/{id}", medidor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean medidorExistsInEs = medidorSearchRepository.exists(medidor.getId());
        assertThat(medidorExistsInEs).isFalse();

        // Validate the database is empty
        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchMedidor() throws Exception {
        // Initialize the database
        medidorRepository.saveAndFlush(medidor);
        medidorSearchRepository.save(medidor);

        // Search the medidor
        restMedidorMockMvc.perform(get("/api/_search/medidors?query=id:" + medidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeromedidor").value(hasItem(DEFAULT_NUMEROMEDIDOR)))
            .andExpect(jsonPath("$.[*].fechaadquirio").value(hasItem(DEFAULT_FECHAADQUIRIO.toString())))
            .andExpect(jsonPath("$.[*].fechaactual").value(hasItem(DEFAULT_FECHAACTUAL.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medidor.class);
        Medidor medidor1 = new Medidor();
        medidor1.setId(1L);
        Medidor medidor2 = new Medidor();
        medidor2.setId(medidor1.getId());
        assertThat(medidor1).isEqualTo(medidor2);
        medidor2.setId(2L);
        assertThat(medidor1).isNotEqualTo(medidor2);
        medidor1.setId(null);
        assertThat(medidor1).isNotEqualTo(medidor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedidorDTO.class);
        MedidorDTO medidorDTO1 = new MedidorDTO();
        medidorDTO1.setId(1L);
        MedidorDTO medidorDTO2 = new MedidorDTO();
        assertThat(medidorDTO1).isNotEqualTo(medidorDTO2);
        medidorDTO2.setId(medidorDTO1.getId());
        assertThat(medidorDTO1).isEqualTo(medidorDTO2);
        medidorDTO2.setId(2L);
        assertThat(medidorDTO1).isNotEqualTo(medidorDTO2);
        medidorDTO1.setId(null);
        assertThat(medidorDTO1).isNotEqualTo(medidorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(medidorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(medidorMapper.fromId(null)).isNull();
    }
}
