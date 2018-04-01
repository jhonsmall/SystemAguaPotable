package devs.team.net.web.rest;

import devs.team.net.SystemAguaPotableApp;

import devs.team.net.domain.Costo;
import devs.team.net.repository.CostoRepository;
import devs.team.net.service.CostoService;
import devs.team.net.repository.search.CostoSearchRepository;
import devs.team.net.service.dto.CostoDTO;
import devs.team.net.service.mapper.CostoMapper;
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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static devs.team.net.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CostoResource REST controller.
 *
 * @see CostoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemAguaPotableApp.class)
public class CostoResourceIntTest {

    private static final BigDecimal DEFAULT_CUOTA = new BigDecimal(1);
    private static final BigDecimal UPDATED_CUOTA = new BigDecimal(2);

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CostoRepository costoRepository;

    @Autowired
    private CostoMapper costoMapper;

    @Autowired
    private CostoService costoService;

    @Autowired
    private CostoSearchRepository costoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCostoMockMvc;

    private Costo costo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CostoResource costoResource = new CostoResource(costoService);
        this.restCostoMockMvc = MockMvcBuilders.standaloneSetup(costoResource)
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
    public static Costo createEntity(EntityManager em) {
        Costo costo = new Costo()
            .cuota(DEFAULT_CUOTA)
            .fecha(DEFAULT_FECHA);
        return costo;
    }

    @Before
    public void initTest() {
        costoSearchRepository.deleteAll();
        costo = createEntity(em);
    }

    @Test
    @Transactional
    public void createCosto() throws Exception {
        int databaseSizeBeforeCreate = costoRepository.findAll().size();

        // Create the Costo
        CostoDTO costoDTO = costoMapper.toDto(costo);
        restCostoMockMvc.perform(post("/api/costos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoDTO)))
            .andExpect(status().isCreated());

        // Validate the Costo in the database
        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeCreate + 1);
        Costo testCosto = costoList.get(costoList.size() - 1);
        assertThat(testCosto.getCuota()).isEqualTo(DEFAULT_CUOTA);
        assertThat(testCosto.getFecha()).isEqualTo(DEFAULT_FECHA);

        // Validate the Costo in Elasticsearch
        Costo costoEs = costoSearchRepository.findOne(testCosto.getId());
        assertThat(costoEs).isEqualToIgnoringGivenFields(testCosto);
    }

    @Test
    @Transactional
    public void createCostoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = costoRepository.findAll().size();

        // Create the Costo with an existing ID
        costo.setId(1L);
        CostoDTO costoDTO = costoMapper.toDto(costo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCostoMockMvc.perform(post("/api/costos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Costo in the database
        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCuotaIsRequired() throws Exception {
        int databaseSizeBeforeTest = costoRepository.findAll().size();
        // set the field null
        costo.setCuota(null);

        // Create the Costo, which fails.
        CostoDTO costoDTO = costoMapper.toDto(costo);

        restCostoMockMvc.perform(post("/api/costos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoDTO)))
            .andExpect(status().isBadRequest());

        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = costoRepository.findAll().size();
        // set the field null
        costo.setFecha(null);

        // Create the Costo, which fails.
        CostoDTO costoDTO = costoMapper.toDto(costo);

        restCostoMockMvc.perform(post("/api/costos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoDTO)))
            .andExpect(status().isBadRequest());

        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCostos() throws Exception {
        // Initialize the database
        costoRepository.saveAndFlush(costo);

        // Get all the costoList
        restCostoMockMvc.perform(get("/api/costos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cuota").value(hasItem(DEFAULT_CUOTA.intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void getCosto() throws Exception {
        // Initialize the database
        costoRepository.saveAndFlush(costo);

        // Get the costo
        restCostoMockMvc.perform(get("/api/costos/{id}", costo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(costo.getId().intValue()))
            .andExpect(jsonPath("$.cuota").value(DEFAULT_CUOTA.intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCosto() throws Exception {
        // Get the costo
        restCostoMockMvc.perform(get("/api/costos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCosto() throws Exception {
        // Initialize the database
        costoRepository.saveAndFlush(costo);
        costoSearchRepository.save(costo);
        int databaseSizeBeforeUpdate = costoRepository.findAll().size();

        // Update the costo
        Costo updatedCosto = costoRepository.findOne(costo.getId());
        // Disconnect from session so that the updates on updatedCosto are not directly saved in db
        em.detach(updatedCosto);
        updatedCosto
            .cuota(UPDATED_CUOTA)
            .fecha(UPDATED_FECHA);
        CostoDTO costoDTO = costoMapper.toDto(updatedCosto);

        restCostoMockMvc.perform(put("/api/costos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoDTO)))
            .andExpect(status().isOk());

        // Validate the Costo in the database
        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeUpdate);
        Costo testCosto = costoList.get(costoList.size() - 1);
        assertThat(testCosto.getCuota()).isEqualTo(UPDATED_CUOTA);
        assertThat(testCosto.getFecha()).isEqualTo(UPDATED_FECHA);

        // Validate the Costo in Elasticsearch
        Costo costoEs = costoSearchRepository.findOne(testCosto.getId());
        assertThat(costoEs).isEqualToIgnoringGivenFields(testCosto);
    }

    @Test
    @Transactional
    public void updateNonExistingCosto() throws Exception {
        int databaseSizeBeforeUpdate = costoRepository.findAll().size();

        // Create the Costo
        CostoDTO costoDTO = costoMapper.toDto(costo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCostoMockMvc.perform(put("/api/costos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(costoDTO)))
            .andExpect(status().isCreated());

        // Validate the Costo in the database
        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCosto() throws Exception {
        // Initialize the database
        costoRepository.saveAndFlush(costo);
        costoSearchRepository.save(costo);
        int databaseSizeBeforeDelete = costoRepository.findAll().size();

        // Get the costo
        restCostoMockMvc.perform(delete("/api/costos/{id}", costo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean costoExistsInEs = costoSearchRepository.exists(costo.getId());
        assertThat(costoExistsInEs).isFalse();

        // Validate the database is empty
        List<Costo> costoList = costoRepository.findAll();
        assertThat(costoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCosto() throws Exception {
        // Initialize the database
        costoRepository.saveAndFlush(costo);
        costoSearchRepository.save(costo);

        // Search the costo
        restCostoMockMvc.perform(get("/api/_search/costos?query=id:" + costo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cuota").value(hasItem(DEFAULT_CUOTA.intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Costo.class);
        Costo costo1 = new Costo();
        costo1.setId(1L);
        Costo costo2 = new Costo();
        costo2.setId(costo1.getId());
        assertThat(costo1).isEqualTo(costo2);
        costo2.setId(2L);
        assertThat(costo1).isNotEqualTo(costo2);
        costo1.setId(null);
        assertThat(costo1).isNotEqualTo(costo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostoDTO.class);
        CostoDTO costoDTO1 = new CostoDTO();
        costoDTO1.setId(1L);
        CostoDTO costoDTO2 = new CostoDTO();
        assertThat(costoDTO1).isNotEqualTo(costoDTO2);
        costoDTO2.setId(costoDTO1.getId());
        assertThat(costoDTO1).isEqualTo(costoDTO2);
        costoDTO2.setId(2L);
        assertThat(costoDTO1).isNotEqualTo(costoDTO2);
        costoDTO1.setId(null);
        assertThat(costoDTO1).isNotEqualTo(costoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(costoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(costoMapper.fromId(null)).isNull();
    }
}
