package devs.team.net.web.rest;

import devs.team.net.SystemAguaPotableApp;

import devs.team.net.domain.EscalasDelMedidor;
import devs.team.net.repository.EscalasDelMedidorRepository;
import devs.team.net.service.EscalasDelMedidorService;
import devs.team.net.repository.search.EscalasDelMedidorSearchRepository;
import devs.team.net.service.dto.EscalasDelMedidorDTO;
import devs.team.net.service.mapper.EscalasDelMedidorMapper;
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
 * Test class for the EscalasDelMedidorResource REST controller.
 *
 * @see EscalasDelMedidorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemAguaPotableApp.class)
public class EscalasDelMedidorResourceIntTest {

    private static final Integer DEFAULT_INICIO = 1;
    private static final Integer UPDATED_INICIO = 2;

    private static final Integer DEFAULT_FIN = 1;
    private static final Integer UPDATED_FIN = 2;

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EscalasDelMedidorRepository escalasDelMedidorRepository;

    @Autowired
    private EscalasDelMedidorMapper escalasDelMedidorMapper;

    @Autowired
    private EscalasDelMedidorService escalasDelMedidorService;

    @Autowired
    private EscalasDelMedidorSearchRepository escalasDelMedidorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEscalasDelMedidorMockMvc;

    private EscalasDelMedidor escalasDelMedidor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EscalasDelMedidorResource escalasDelMedidorResource = new EscalasDelMedidorResource(escalasDelMedidorService);
        this.restEscalasDelMedidorMockMvc = MockMvcBuilders.standaloneSetup(escalasDelMedidorResource)
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
    public static EscalasDelMedidor createEntity(EntityManager em) {
        EscalasDelMedidor escalasDelMedidor = new EscalasDelMedidor()
            .inicio(DEFAULT_INICIO)
            .fin(DEFAULT_FIN)
            .fecha(DEFAULT_FECHA);
        return escalasDelMedidor;
    }

    @Before
    public void initTest() {
        escalasDelMedidorSearchRepository.deleteAll();
        escalasDelMedidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createEscalasDelMedidor() throws Exception {
        int databaseSizeBeforeCreate = escalasDelMedidorRepository.findAll().size();

        // Create the EscalasDelMedidor
        EscalasDelMedidorDTO escalasDelMedidorDTO = escalasDelMedidorMapper.toDto(escalasDelMedidor);
        restEscalasDelMedidorMockMvc.perform(post("/api/escalas-del-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escalasDelMedidorDTO)))
            .andExpect(status().isCreated());

        // Validate the EscalasDelMedidor in the database
        List<EscalasDelMedidor> escalasDelMedidorList = escalasDelMedidorRepository.findAll();
        assertThat(escalasDelMedidorList).hasSize(databaseSizeBeforeCreate + 1);
        EscalasDelMedidor testEscalasDelMedidor = escalasDelMedidorList.get(escalasDelMedidorList.size() - 1);
        assertThat(testEscalasDelMedidor.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testEscalasDelMedidor.getFin()).isEqualTo(DEFAULT_FIN);
        assertThat(testEscalasDelMedidor.getFecha()).isEqualTo(DEFAULT_FECHA);

        // Validate the EscalasDelMedidor in Elasticsearch
        EscalasDelMedidor escalasDelMedidorEs = escalasDelMedidorSearchRepository.findOne(testEscalasDelMedidor.getId());
        assertThat(escalasDelMedidorEs).isEqualToIgnoringGivenFields(testEscalasDelMedidor);
    }

    @Test
    @Transactional
    public void createEscalasDelMedidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = escalasDelMedidorRepository.findAll().size();

        // Create the EscalasDelMedidor with an existing ID
        escalasDelMedidor.setId(1L);
        EscalasDelMedidorDTO escalasDelMedidorDTO = escalasDelMedidorMapper.toDto(escalasDelMedidor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEscalasDelMedidorMockMvc.perform(post("/api/escalas-del-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escalasDelMedidorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EscalasDelMedidor in the database
        List<EscalasDelMedidor> escalasDelMedidorList = escalasDelMedidorRepository.findAll();
        assertThat(escalasDelMedidorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = escalasDelMedidorRepository.findAll().size();
        // set the field null
        escalasDelMedidor.setInicio(null);

        // Create the EscalasDelMedidor, which fails.
        EscalasDelMedidorDTO escalasDelMedidorDTO = escalasDelMedidorMapper.toDto(escalasDelMedidor);

        restEscalasDelMedidorMockMvc.perform(post("/api/escalas-del-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escalasDelMedidorDTO)))
            .andExpect(status().isBadRequest());

        List<EscalasDelMedidor> escalasDelMedidorList = escalasDelMedidorRepository.findAll();
        assertThat(escalasDelMedidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = escalasDelMedidorRepository.findAll().size();
        // set the field null
        escalasDelMedidor.setFin(null);

        // Create the EscalasDelMedidor, which fails.
        EscalasDelMedidorDTO escalasDelMedidorDTO = escalasDelMedidorMapper.toDto(escalasDelMedidor);

        restEscalasDelMedidorMockMvc.perform(post("/api/escalas-del-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escalasDelMedidorDTO)))
            .andExpect(status().isBadRequest());

        List<EscalasDelMedidor> escalasDelMedidorList = escalasDelMedidorRepository.findAll();
        assertThat(escalasDelMedidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = escalasDelMedidorRepository.findAll().size();
        // set the field null
        escalasDelMedidor.setFecha(null);

        // Create the EscalasDelMedidor, which fails.
        EscalasDelMedidorDTO escalasDelMedidorDTO = escalasDelMedidorMapper.toDto(escalasDelMedidor);

        restEscalasDelMedidorMockMvc.perform(post("/api/escalas-del-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escalasDelMedidorDTO)))
            .andExpect(status().isBadRequest());

        List<EscalasDelMedidor> escalasDelMedidorList = escalasDelMedidorRepository.findAll();
        assertThat(escalasDelMedidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEscalasDelMedidors() throws Exception {
        // Initialize the database
        escalasDelMedidorRepository.saveAndFlush(escalasDelMedidor);

        // Get all the escalasDelMedidorList
        restEscalasDelMedidorMockMvc.perform(get("/api/escalas-del-medidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escalasDelMedidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO)))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void getEscalasDelMedidor() throws Exception {
        // Initialize the database
        escalasDelMedidorRepository.saveAndFlush(escalasDelMedidor);

        // Get the escalasDelMedidor
        restEscalasDelMedidorMockMvc.perform(get("/api/escalas-del-medidors/{id}", escalasDelMedidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(escalasDelMedidor.getId().intValue()))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEscalasDelMedidor() throws Exception {
        // Get the escalasDelMedidor
        restEscalasDelMedidorMockMvc.perform(get("/api/escalas-del-medidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEscalasDelMedidor() throws Exception {
        // Initialize the database
        escalasDelMedidorRepository.saveAndFlush(escalasDelMedidor);
        escalasDelMedidorSearchRepository.save(escalasDelMedidor);
        int databaseSizeBeforeUpdate = escalasDelMedidorRepository.findAll().size();

        // Update the escalasDelMedidor
        EscalasDelMedidor updatedEscalasDelMedidor = escalasDelMedidorRepository.findOne(escalasDelMedidor.getId());
        // Disconnect from session so that the updates on updatedEscalasDelMedidor are not directly saved in db
        em.detach(updatedEscalasDelMedidor);
        updatedEscalasDelMedidor
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN)
            .fecha(UPDATED_FECHA);
        EscalasDelMedidorDTO escalasDelMedidorDTO = escalasDelMedidorMapper.toDto(updatedEscalasDelMedidor);

        restEscalasDelMedidorMockMvc.perform(put("/api/escalas-del-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escalasDelMedidorDTO)))
            .andExpect(status().isOk());

        // Validate the EscalasDelMedidor in the database
        List<EscalasDelMedidor> escalasDelMedidorList = escalasDelMedidorRepository.findAll();
        assertThat(escalasDelMedidorList).hasSize(databaseSizeBeforeUpdate);
        EscalasDelMedidor testEscalasDelMedidor = escalasDelMedidorList.get(escalasDelMedidorList.size() - 1);
        assertThat(testEscalasDelMedidor.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testEscalasDelMedidor.getFin()).isEqualTo(UPDATED_FIN);
        assertThat(testEscalasDelMedidor.getFecha()).isEqualTo(UPDATED_FECHA);

        // Validate the EscalasDelMedidor in Elasticsearch
        EscalasDelMedidor escalasDelMedidorEs = escalasDelMedidorSearchRepository.findOne(testEscalasDelMedidor.getId());
        assertThat(escalasDelMedidorEs).isEqualToIgnoringGivenFields(testEscalasDelMedidor);
    }

    @Test
    @Transactional
    public void updateNonExistingEscalasDelMedidor() throws Exception {
        int databaseSizeBeforeUpdate = escalasDelMedidorRepository.findAll().size();

        // Create the EscalasDelMedidor
        EscalasDelMedidorDTO escalasDelMedidorDTO = escalasDelMedidorMapper.toDto(escalasDelMedidor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEscalasDelMedidorMockMvc.perform(put("/api/escalas-del-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(escalasDelMedidorDTO)))
            .andExpect(status().isCreated());

        // Validate the EscalasDelMedidor in the database
        List<EscalasDelMedidor> escalasDelMedidorList = escalasDelMedidorRepository.findAll();
        assertThat(escalasDelMedidorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEscalasDelMedidor() throws Exception {
        // Initialize the database
        escalasDelMedidorRepository.saveAndFlush(escalasDelMedidor);
        escalasDelMedidorSearchRepository.save(escalasDelMedidor);
        int databaseSizeBeforeDelete = escalasDelMedidorRepository.findAll().size();

        // Get the escalasDelMedidor
        restEscalasDelMedidorMockMvc.perform(delete("/api/escalas-del-medidors/{id}", escalasDelMedidor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean escalasDelMedidorExistsInEs = escalasDelMedidorSearchRepository.exists(escalasDelMedidor.getId());
        assertThat(escalasDelMedidorExistsInEs).isFalse();

        // Validate the database is empty
        List<EscalasDelMedidor> escalasDelMedidorList = escalasDelMedidorRepository.findAll();
        assertThat(escalasDelMedidorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEscalasDelMedidor() throws Exception {
        // Initialize the database
        escalasDelMedidorRepository.saveAndFlush(escalasDelMedidor);
        escalasDelMedidorSearchRepository.save(escalasDelMedidor);

        // Search the escalasDelMedidor
        restEscalasDelMedidorMockMvc.perform(get("/api/_search/escalas-del-medidors?query=id:" + escalasDelMedidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escalasDelMedidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO)))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EscalasDelMedidor.class);
        EscalasDelMedidor escalasDelMedidor1 = new EscalasDelMedidor();
        escalasDelMedidor1.setId(1L);
        EscalasDelMedidor escalasDelMedidor2 = new EscalasDelMedidor();
        escalasDelMedidor2.setId(escalasDelMedidor1.getId());
        assertThat(escalasDelMedidor1).isEqualTo(escalasDelMedidor2);
        escalasDelMedidor2.setId(2L);
        assertThat(escalasDelMedidor1).isNotEqualTo(escalasDelMedidor2);
        escalasDelMedidor1.setId(null);
        assertThat(escalasDelMedidor1).isNotEqualTo(escalasDelMedidor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EscalasDelMedidorDTO.class);
        EscalasDelMedidorDTO escalasDelMedidorDTO1 = new EscalasDelMedidorDTO();
        escalasDelMedidorDTO1.setId(1L);
        EscalasDelMedidorDTO escalasDelMedidorDTO2 = new EscalasDelMedidorDTO();
        assertThat(escalasDelMedidorDTO1).isNotEqualTo(escalasDelMedidorDTO2);
        escalasDelMedidorDTO2.setId(escalasDelMedidorDTO1.getId());
        assertThat(escalasDelMedidorDTO1).isEqualTo(escalasDelMedidorDTO2);
        escalasDelMedidorDTO2.setId(2L);
        assertThat(escalasDelMedidorDTO1).isNotEqualTo(escalasDelMedidorDTO2);
        escalasDelMedidorDTO1.setId(null);
        assertThat(escalasDelMedidorDTO1).isNotEqualTo(escalasDelMedidorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(escalasDelMedidorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(escalasDelMedidorMapper.fromId(null)).isNull();
    }
}
