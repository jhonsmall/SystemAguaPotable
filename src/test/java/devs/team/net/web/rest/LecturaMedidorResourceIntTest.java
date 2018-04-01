package devs.team.net.web.rest;

import devs.team.net.SystemAguaPotableApp;

import devs.team.net.domain.LecturaMedidor;
import devs.team.net.repository.LecturaMedidorRepository;
import devs.team.net.service.LecturaMedidorService;
import devs.team.net.repository.search.LecturaMedidorSearchRepository;
import devs.team.net.service.dto.LecturaMedidorDTO;
import devs.team.net.service.mapper.LecturaMedidorMapper;
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

import devs.team.net.domain.enumeration.Estado;
/**
 * Test class for the LecturaMedidorResource REST controller.
 *
 * @see LecturaMedidorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemAguaPotableApp.class)
public class LecturaMedidorResourceIntTest {

    private static final Integer DEFAULT_LECTURAINICIAL = 1;
    private static final Integer UPDATED_LECTURAINICIAL = 2;

    private static final Integer DEFAULT_LECTURAFINAL = 1;
    private static final Integer UPDATED_LECTURAFINAL = 2;

    private static final Estado DEFAULT_ESTADO = Estado.ACTIVO;
    private static final Estado UPDATED_ESTADO = Estado.INACTIVO;

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ANIO = 1;
    private static final Integer UPDATED_ANIO = 2;

    private static final Integer DEFAULT_MES = 1;
    private static final Integer UPDATED_MES = 2;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private LecturaMedidorRepository lecturaMedidorRepository;

    @Autowired
    private LecturaMedidorMapper lecturaMedidorMapper;

    @Autowired
    private LecturaMedidorService lecturaMedidorService;

    @Autowired
    private LecturaMedidorSearchRepository lecturaMedidorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLecturaMedidorMockMvc;

    private LecturaMedidor lecturaMedidor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LecturaMedidorResource lecturaMedidorResource = new LecturaMedidorResource(lecturaMedidorService);
        this.restLecturaMedidorMockMvc = MockMvcBuilders.standaloneSetup(lecturaMedidorResource)
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
    public static LecturaMedidor createEntity(EntityManager em) {
        LecturaMedidor lecturaMedidor = new LecturaMedidor()
            .lecturainicial(DEFAULT_LECTURAINICIAL)
            .lecturafinal(DEFAULT_LECTURAFINAL)
            .estado(DEFAULT_ESTADO)
            .fecha(DEFAULT_FECHA)
            .anio(DEFAULT_ANIO)
            .mes(DEFAULT_MES)
            .descripcion(DEFAULT_DESCRIPCION);
        return lecturaMedidor;
    }

    @Before
    public void initTest() {
        lecturaMedidorSearchRepository.deleteAll();
        lecturaMedidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createLecturaMedidor() throws Exception {
        int databaseSizeBeforeCreate = lecturaMedidorRepository.findAll().size();

        // Create the LecturaMedidor
        LecturaMedidorDTO lecturaMedidorDTO = lecturaMedidorMapper.toDto(lecturaMedidor);
        restLecturaMedidorMockMvc.perform(post("/api/lectura-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturaMedidorDTO)))
            .andExpect(status().isCreated());

        // Validate the LecturaMedidor in the database
        List<LecturaMedidor> lecturaMedidorList = lecturaMedidorRepository.findAll();
        assertThat(lecturaMedidorList).hasSize(databaseSizeBeforeCreate + 1);
        LecturaMedidor testLecturaMedidor = lecturaMedidorList.get(lecturaMedidorList.size() - 1);
        assertThat(testLecturaMedidor.getLecturainicial()).isEqualTo(DEFAULT_LECTURAINICIAL);
        assertThat(testLecturaMedidor.getLecturafinal()).isEqualTo(DEFAULT_LECTURAFINAL);
        assertThat(testLecturaMedidor.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testLecturaMedidor.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testLecturaMedidor.getAnio()).isEqualTo(DEFAULT_ANIO);
        assertThat(testLecturaMedidor.getMes()).isEqualTo(DEFAULT_MES);
        assertThat(testLecturaMedidor.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);

        // Validate the LecturaMedidor in Elasticsearch
        LecturaMedidor lecturaMedidorEs = lecturaMedidorSearchRepository.findOne(testLecturaMedidor.getId());
        assertThat(lecturaMedidorEs).isEqualToIgnoringGivenFields(testLecturaMedidor);
    }

    @Test
    @Transactional
    public void createLecturaMedidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lecturaMedidorRepository.findAll().size();

        // Create the LecturaMedidor with an existing ID
        lecturaMedidor.setId(1L);
        LecturaMedidorDTO lecturaMedidorDTO = lecturaMedidorMapper.toDto(lecturaMedidor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLecturaMedidorMockMvc.perform(post("/api/lectura-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturaMedidorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LecturaMedidor in the database
        List<LecturaMedidor> lecturaMedidorList = lecturaMedidorRepository.findAll();
        assertThat(lecturaMedidorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkLecturainicialIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturaMedidorRepository.findAll().size();
        // set the field null
        lecturaMedidor.setLecturainicial(null);

        // Create the LecturaMedidor, which fails.
        LecturaMedidorDTO lecturaMedidorDTO = lecturaMedidorMapper.toDto(lecturaMedidor);

        restLecturaMedidorMockMvc.perform(post("/api/lectura-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturaMedidorDTO)))
            .andExpect(status().isBadRequest());

        List<LecturaMedidor> lecturaMedidorList = lecturaMedidorRepository.findAll();
        assertThat(lecturaMedidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLecturafinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturaMedidorRepository.findAll().size();
        // set the field null
        lecturaMedidor.setLecturafinal(null);

        // Create the LecturaMedidor, which fails.
        LecturaMedidorDTO lecturaMedidorDTO = lecturaMedidorMapper.toDto(lecturaMedidor);

        restLecturaMedidorMockMvc.perform(post("/api/lectura-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturaMedidorDTO)))
            .andExpect(status().isBadRequest());

        List<LecturaMedidor> lecturaMedidorList = lecturaMedidorRepository.findAll();
        assertThat(lecturaMedidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturaMedidorRepository.findAll().size();
        // set the field null
        lecturaMedidor.setEstado(null);

        // Create the LecturaMedidor, which fails.
        LecturaMedidorDTO lecturaMedidorDTO = lecturaMedidorMapper.toDto(lecturaMedidor);

        restLecturaMedidorMockMvc.perform(post("/api/lectura-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturaMedidorDTO)))
            .andExpect(status().isBadRequest());

        List<LecturaMedidor> lecturaMedidorList = lecturaMedidorRepository.findAll();
        assertThat(lecturaMedidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturaMedidorRepository.findAll().size();
        // set the field null
        lecturaMedidor.setFecha(null);

        // Create the LecturaMedidor, which fails.
        LecturaMedidorDTO lecturaMedidorDTO = lecturaMedidorMapper.toDto(lecturaMedidor);

        restLecturaMedidorMockMvc.perform(post("/api/lectura-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturaMedidorDTO)))
            .andExpect(status().isBadRequest());

        List<LecturaMedidor> lecturaMedidorList = lecturaMedidorRepository.findAll();
        assertThat(lecturaMedidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnioIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturaMedidorRepository.findAll().size();
        // set the field null
        lecturaMedidor.setAnio(null);

        // Create the LecturaMedidor, which fails.
        LecturaMedidorDTO lecturaMedidorDTO = lecturaMedidorMapper.toDto(lecturaMedidor);

        restLecturaMedidorMockMvc.perform(post("/api/lectura-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturaMedidorDTO)))
            .andExpect(status().isBadRequest());

        List<LecturaMedidor> lecturaMedidorList = lecturaMedidorRepository.findAll();
        assertThat(lecturaMedidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMesIsRequired() throws Exception {
        int databaseSizeBeforeTest = lecturaMedidorRepository.findAll().size();
        // set the field null
        lecturaMedidor.setMes(null);

        // Create the LecturaMedidor, which fails.
        LecturaMedidorDTO lecturaMedidorDTO = lecturaMedidorMapper.toDto(lecturaMedidor);

        restLecturaMedidorMockMvc.perform(post("/api/lectura-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturaMedidorDTO)))
            .andExpect(status().isBadRequest());

        List<LecturaMedidor> lecturaMedidorList = lecturaMedidorRepository.findAll();
        assertThat(lecturaMedidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLecturaMedidors() throws Exception {
        // Initialize the database
        lecturaMedidorRepository.saveAndFlush(lecturaMedidor);

        // Get all the lecturaMedidorList
        restLecturaMedidorMockMvc.perform(get("/api/lectura-medidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lecturaMedidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].lecturainicial").value(hasItem(DEFAULT_LECTURAINICIAL)))
            .andExpect(jsonPath("$.[*].lecturafinal").value(hasItem(DEFAULT_LECTURAFINAL)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO)))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getLecturaMedidor() throws Exception {
        // Initialize the database
        lecturaMedidorRepository.saveAndFlush(lecturaMedidor);

        // Get the lecturaMedidor
        restLecturaMedidorMockMvc.perform(get("/api/lectura-medidors/{id}", lecturaMedidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lecturaMedidor.getId().intValue()))
            .andExpect(jsonPath("$.lecturainicial").value(DEFAULT_LECTURAINICIAL))
            .andExpect(jsonPath("$.lecturafinal").value(DEFAULT_LECTURAFINAL))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.anio").value(DEFAULT_ANIO))
            .andExpect(jsonPath("$.mes").value(DEFAULT_MES))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLecturaMedidor() throws Exception {
        // Get the lecturaMedidor
        restLecturaMedidorMockMvc.perform(get("/api/lectura-medidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLecturaMedidor() throws Exception {
        // Initialize the database
        lecturaMedidorRepository.saveAndFlush(lecturaMedidor);
        lecturaMedidorSearchRepository.save(lecturaMedidor);
        int databaseSizeBeforeUpdate = lecturaMedidorRepository.findAll().size();

        // Update the lecturaMedidor
        LecturaMedidor updatedLecturaMedidor = lecturaMedidorRepository.findOne(lecturaMedidor.getId());
        // Disconnect from session so that the updates on updatedLecturaMedidor are not directly saved in db
        em.detach(updatedLecturaMedidor);
        updatedLecturaMedidor
            .lecturainicial(UPDATED_LECTURAINICIAL)
            .lecturafinal(UPDATED_LECTURAFINAL)
            .estado(UPDATED_ESTADO)
            .fecha(UPDATED_FECHA)
            .anio(UPDATED_ANIO)
            .mes(UPDATED_MES)
            .descripcion(UPDATED_DESCRIPCION);
        LecturaMedidorDTO lecturaMedidorDTO = lecturaMedidorMapper.toDto(updatedLecturaMedidor);

        restLecturaMedidorMockMvc.perform(put("/api/lectura-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturaMedidorDTO)))
            .andExpect(status().isOk());

        // Validate the LecturaMedidor in the database
        List<LecturaMedidor> lecturaMedidorList = lecturaMedidorRepository.findAll();
        assertThat(lecturaMedidorList).hasSize(databaseSizeBeforeUpdate);
        LecturaMedidor testLecturaMedidor = lecturaMedidorList.get(lecturaMedidorList.size() - 1);
        assertThat(testLecturaMedidor.getLecturainicial()).isEqualTo(UPDATED_LECTURAINICIAL);
        assertThat(testLecturaMedidor.getLecturafinal()).isEqualTo(UPDATED_LECTURAFINAL);
        assertThat(testLecturaMedidor.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testLecturaMedidor.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testLecturaMedidor.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testLecturaMedidor.getMes()).isEqualTo(UPDATED_MES);
        assertThat(testLecturaMedidor.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);

        // Validate the LecturaMedidor in Elasticsearch
        LecturaMedidor lecturaMedidorEs = lecturaMedidorSearchRepository.findOne(testLecturaMedidor.getId());
        assertThat(lecturaMedidorEs).isEqualToIgnoringGivenFields(testLecturaMedidor);
    }

    @Test
    @Transactional
    public void updateNonExistingLecturaMedidor() throws Exception {
        int databaseSizeBeforeUpdate = lecturaMedidorRepository.findAll().size();

        // Create the LecturaMedidor
        LecturaMedidorDTO lecturaMedidorDTO = lecturaMedidorMapper.toDto(lecturaMedidor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLecturaMedidorMockMvc.perform(put("/api/lectura-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lecturaMedidorDTO)))
            .andExpect(status().isCreated());

        // Validate the LecturaMedidor in the database
        List<LecturaMedidor> lecturaMedidorList = lecturaMedidorRepository.findAll();
        assertThat(lecturaMedidorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLecturaMedidor() throws Exception {
        // Initialize the database
        lecturaMedidorRepository.saveAndFlush(lecturaMedidor);
        lecturaMedidorSearchRepository.save(lecturaMedidor);
        int databaseSizeBeforeDelete = lecturaMedidorRepository.findAll().size();

        // Get the lecturaMedidor
        restLecturaMedidorMockMvc.perform(delete("/api/lectura-medidors/{id}", lecturaMedidor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean lecturaMedidorExistsInEs = lecturaMedidorSearchRepository.exists(lecturaMedidor.getId());
        assertThat(lecturaMedidorExistsInEs).isFalse();

        // Validate the database is empty
        List<LecturaMedidor> lecturaMedidorList = lecturaMedidorRepository.findAll();
        assertThat(lecturaMedidorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLecturaMedidor() throws Exception {
        // Initialize the database
        lecturaMedidorRepository.saveAndFlush(lecturaMedidor);
        lecturaMedidorSearchRepository.save(lecturaMedidor);

        // Search the lecturaMedidor
        restLecturaMedidorMockMvc.perform(get("/api/_search/lectura-medidors?query=id:" + lecturaMedidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lecturaMedidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].lecturainicial").value(hasItem(DEFAULT_LECTURAINICIAL)))
            .andExpect(jsonPath("$.[*].lecturafinal").value(hasItem(DEFAULT_LECTURAFINAL)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO)))
            .andExpect(jsonPath("$.[*].mes").value(hasItem(DEFAULT_MES)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LecturaMedidor.class);
        LecturaMedidor lecturaMedidor1 = new LecturaMedidor();
        lecturaMedidor1.setId(1L);
        LecturaMedidor lecturaMedidor2 = new LecturaMedidor();
        lecturaMedidor2.setId(lecturaMedidor1.getId());
        assertThat(lecturaMedidor1).isEqualTo(lecturaMedidor2);
        lecturaMedidor2.setId(2L);
        assertThat(lecturaMedidor1).isNotEqualTo(lecturaMedidor2);
        lecturaMedidor1.setId(null);
        assertThat(lecturaMedidor1).isNotEqualTo(lecturaMedidor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LecturaMedidorDTO.class);
        LecturaMedidorDTO lecturaMedidorDTO1 = new LecturaMedidorDTO();
        lecturaMedidorDTO1.setId(1L);
        LecturaMedidorDTO lecturaMedidorDTO2 = new LecturaMedidorDTO();
        assertThat(lecturaMedidorDTO1).isNotEqualTo(lecturaMedidorDTO2);
        lecturaMedidorDTO2.setId(lecturaMedidorDTO1.getId());
        assertThat(lecturaMedidorDTO1).isEqualTo(lecturaMedidorDTO2);
        lecturaMedidorDTO2.setId(2L);
        assertThat(lecturaMedidorDTO1).isNotEqualTo(lecturaMedidorDTO2);
        lecturaMedidorDTO1.setId(null);
        assertThat(lecturaMedidorDTO1).isNotEqualTo(lecturaMedidorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lecturaMedidorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lecturaMedidorMapper.fromId(null)).isNull();
    }
}
