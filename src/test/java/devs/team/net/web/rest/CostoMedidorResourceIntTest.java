package devs.team.net.web.rest;

import devs.team.net.SystemAguaPotableApp;

import devs.team.net.domain.CostoMedidor;
import devs.team.net.repository.CostoMedidorRepository;
import devs.team.net.service.CostoMedidorService;
import devs.team.net.repository.search.CostoMedidorSearchRepository;
import devs.team.net.service.dto.CostoMedidorDTO;
import devs.team.net.service.mapper.CostoMedidorMapper;
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
 * Test class for the CostoMedidorResource REST controller.
 *
 * @see CostoMedidorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemAguaPotableApp.class)
public class CostoMedidorResourceIntTest {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Estado DEFAULT_ESTADO = Estado.ACTIVO;
    private static final Estado UPDATED_ESTADO = Estado.INACTIVO;

    @Autowired
    private CostoMedidorRepository costoMedidorRepository;

    @Autowired
    private CostoMedidorMapper costoMedidorMapper;

    @Autowired
    private CostoMedidorService costoMedidorService;

    @Autowired
    private CostoMedidorSearchRepository costoMedidorSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCostoMedidorMockMvc;

    private CostoMedidor costoMedidor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CostoMedidorResource costoMedidorResource = new CostoMedidorResource(costoMedidorService);
        this.restCostoMedidorMockMvc = MockMvcBuilders.standaloneSetup(costoMedidorResource)
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
    public static CostoMedidor createEntity(EntityManager em) {
        CostoMedidor costoMedidor = new CostoMedidor()
            .fecha(DEFAULT_FECHA)
            .estado(DEFAULT_ESTADO);
        return costoMedidor;
    }

    @Before
    public void initTest() {
        costoMedidorSearchRepository.deleteAll();
        costoMedidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createCostoMedidor() throws Exception {
        int databaseSizeBeforeCreate = costoMedidorRepository.findAll().size();

        // Create the CostoMedidor
        CostoMedidorDTO costoMedidorDTO = costoMedidorMapper.toDto(costoMedidor);
        restCostoMedidorMockMvc.perform(post("/api/costo-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoMedidorDTO)))
            .andExpect(status().isCreated());

        // Validate the CostoMedidor in the database
        List<CostoMedidor> costoMedidorList = costoMedidorRepository.findAll();
        assertThat(costoMedidorList).hasSize(databaseSizeBeforeCreate + 1);
        CostoMedidor testCostoMedidor = costoMedidorList.get(costoMedidorList.size() - 1);
        assertThat(testCostoMedidor.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCostoMedidor.getEstado()).isEqualTo(DEFAULT_ESTADO);

        // Validate the CostoMedidor in Elasticsearch
        CostoMedidor costoMedidorEs = costoMedidorSearchRepository.findOne(testCostoMedidor.getId());
        assertThat(costoMedidorEs).isEqualToIgnoringGivenFields(testCostoMedidor);
    }

    @Test
    @Transactional
    public void createCostoMedidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = costoMedidorRepository.findAll().size();

        // Create the CostoMedidor with an existing ID
        costoMedidor.setId(1L);
        CostoMedidorDTO costoMedidorDTO = costoMedidorMapper.toDto(costoMedidor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCostoMedidorMockMvc.perform(post("/api/costo-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoMedidorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CostoMedidor in the database
        List<CostoMedidor> costoMedidorList = costoMedidorRepository.findAll();
        assertThat(costoMedidorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = costoMedidorRepository.findAll().size();
        // set the field null
        costoMedidor.setFecha(null);

        // Create the CostoMedidor, which fails.
        CostoMedidorDTO costoMedidorDTO = costoMedidorMapper.toDto(costoMedidor);

        restCostoMedidorMockMvc.perform(post("/api/costo-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoMedidorDTO)))
            .andExpect(status().isBadRequest());

        List<CostoMedidor> costoMedidorList = costoMedidorRepository.findAll();
        assertThat(costoMedidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = costoMedidorRepository.findAll().size();
        // set the field null
        costoMedidor.setEstado(null);

        // Create the CostoMedidor, which fails.
        CostoMedidorDTO costoMedidorDTO = costoMedidorMapper.toDto(costoMedidor);

        restCostoMedidorMockMvc.perform(post("/api/costo-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoMedidorDTO)))
            .andExpect(status().isBadRequest());

        List<CostoMedidor> costoMedidorList = costoMedidorRepository.findAll();
        assertThat(costoMedidorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCostoMedidors() throws Exception {
        // Initialize the database
        costoMedidorRepository.saveAndFlush(costoMedidor);

        // Get all the costoMedidorList
        restCostoMedidorMockMvc.perform(get("/api/costo-medidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costoMedidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void getCostoMedidor() throws Exception {
        // Initialize the database
        costoMedidorRepository.saveAndFlush(costoMedidor);

        // Get the costoMedidor
        restCostoMedidorMockMvc.perform(get("/api/costo-medidors/{id}", costoMedidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(costoMedidor.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCostoMedidor() throws Exception {
        // Get the costoMedidor
        restCostoMedidorMockMvc.perform(get("/api/costo-medidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCostoMedidor() throws Exception {
        // Initialize the database
        costoMedidorRepository.saveAndFlush(costoMedidor);
        costoMedidorSearchRepository.save(costoMedidor);
        int databaseSizeBeforeUpdate = costoMedidorRepository.findAll().size();

        // Update the costoMedidor
        CostoMedidor updatedCostoMedidor = costoMedidorRepository.findOne(costoMedidor.getId());
        // Disconnect from session so that the updates on updatedCostoMedidor are not directly saved in db
        em.detach(updatedCostoMedidor);
        updatedCostoMedidor
            .fecha(UPDATED_FECHA)
            .estado(UPDATED_ESTADO);
        CostoMedidorDTO costoMedidorDTO = costoMedidorMapper.toDto(updatedCostoMedidor);

        restCostoMedidorMockMvc.perform(put("/api/costo-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoMedidorDTO)))
            .andExpect(status().isOk());

        // Validate the CostoMedidor in the database
        List<CostoMedidor> costoMedidorList = costoMedidorRepository.findAll();
        assertThat(costoMedidorList).hasSize(databaseSizeBeforeUpdate);
        CostoMedidor testCostoMedidor = costoMedidorList.get(costoMedidorList.size() - 1);
        assertThat(testCostoMedidor.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCostoMedidor.getEstado()).isEqualTo(UPDATED_ESTADO);

        // Validate the CostoMedidor in Elasticsearch
        CostoMedidor costoMedidorEs = costoMedidorSearchRepository.findOne(testCostoMedidor.getId());
        assertThat(costoMedidorEs).isEqualToIgnoringGivenFields(testCostoMedidor);
    }

    @Test
    @Transactional
    public void updateNonExistingCostoMedidor() throws Exception {
        int databaseSizeBeforeUpdate = costoMedidorRepository.findAll().size();

        // Create the CostoMedidor
        CostoMedidorDTO costoMedidorDTO = costoMedidorMapper.toDto(costoMedidor);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCostoMedidorMockMvc.perform(put("/api/costo-medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoMedidorDTO)))
            .andExpect(status().isCreated());

        // Validate the CostoMedidor in the database
        List<CostoMedidor> costoMedidorList = costoMedidorRepository.findAll();
        assertThat(costoMedidorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCostoMedidor() throws Exception {
        // Initialize the database
        costoMedidorRepository.saveAndFlush(costoMedidor);
        costoMedidorSearchRepository.save(costoMedidor);
        int databaseSizeBeforeDelete = costoMedidorRepository.findAll().size();

        // Get the costoMedidor
        restCostoMedidorMockMvc.perform(delete("/api/costo-medidors/{id}", costoMedidor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean costoMedidorExistsInEs = costoMedidorSearchRepository.exists(costoMedidor.getId());
        assertThat(costoMedidorExistsInEs).isFalse();

        // Validate the database is empty
        List<CostoMedidor> costoMedidorList = costoMedidorRepository.findAll();
        assertThat(costoMedidorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCostoMedidor() throws Exception {
        // Initialize the database
        costoMedidorRepository.saveAndFlush(costoMedidor);
        costoMedidorSearchRepository.save(costoMedidor);

        // Search the costoMedidor
        restCostoMedidorMockMvc.perform(get("/api/_search/costo-medidors?query=id:" + costoMedidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costoMedidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostoMedidor.class);
        CostoMedidor costoMedidor1 = new CostoMedidor();
        costoMedidor1.setId(1L);
        CostoMedidor costoMedidor2 = new CostoMedidor();
        costoMedidor2.setId(costoMedidor1.getId());
        assertThat(costoMedidor1).isEqualTo(costoMedidor2);
        costoMedidor2.setId(2L);
        assertThat(costoMedidor1).isNotEqualTo(costoMedidor2);
        costoMedidor1.setId(null);
        assertThat(costoMedidor1).isNotEqualTo(costoMedidor2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostoMedidorDTO.class);
        CostoMedidorDTO costoMedidorDTO1 = new CostoMedidorDTO();
        costoMedidorDTO1.setId(1L);
        CostoMedidorDTO costoMedidorDTO2 = new CostoMedidorDTO();
        assertThat(costoMedidorDTO1).isNotEqualTo(costoMedidorDTO2);
        costoMedidorDTO2.setId(costoMedidorDTO1.getId());
        assertThat(costoMedidorDTO1).isEqualTo(costoMedidorDTO2);
        costoMedidorDTO2.setId(2L);
        assertThat(costoMedidorDTO1).isNotEqualTo(costoMedidorDTO2);
        costoMedidorDTO1.setId(null);
        assertThat(costoMedidorDTO1).isNotEqualTo(costoMedidorDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(costoMedidorMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(costoMedidorMapper.fromId(null)).isNull();
    }
}
