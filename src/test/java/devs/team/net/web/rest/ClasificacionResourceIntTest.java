package devs.team.net.web.rest;

import devs.team.net.SystemAguaPotableApp;

import devs.team.net.domain.Clasificacion;
import devs.team.net.repository.ClasificacionRepository;
import devs.team.net.service.ClasificacionService;
import devs.team.net.repository.search.ClasificacionSearchRepository;
import devs.team.net.service.dto.ClasificacionDTO;
import devs.team.net.service.mapper.ClasificacionMapper;
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
import java.util.List;

import static devs.team.net.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import devs.team.net.domain.enumeration.Estado;
/**
 * Test class for the ClasificacionResource REST controller.
 *
 * @see ClasificacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SystemAguaPotableApp.class)
public class ClasificacionResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.ACTIVO;
    private static final Estado UPDATED_ESTADO = Estado.INACTIVO;

    @Autowired
    private ClasificacionRepository clasificacionRepository;

    @Autowired
    private ClasificacionMapper clasificacionMapper;

    @Autowired
    private ClasificacionService clasificacionService;

    @Autowired
    private ClasificacionSearchRepository clasificacionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClasificacionMockMvc;

    private Clasificacion clasificacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClasificacionResource clasificacionResource = new ClasificacionResource(clasificacionService);
        this.restClasificacionMockMvc = MockMvcBuilders.standaloneSetup(clasificacionResource)
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
    public static Clasificacion createEntity(EntityManager em) {
        Clasificacion clasificacion = new Clasificacion()
            .nombre(DEFAULT_NOMBRE)
            .estado(DEFAULT_ESTADO);
        return clasificacion;
    }

    @Before
    public void initTest() {
        clasificacionSearchRepository.deleteAll();
        clasificacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasificacion() throws Exception {
        int databaseSizeBeforeCreate = clasificacionRepository.findAll().size();

        // Create the Clasificacion
        ClasificacionDTO clasificacionDTO = clasificacionMapper.toDto(clasificacion);
        restClasificacionMockMvc.perform(post("/api/clasificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clasificacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Clasificacion in the database
        List<Clasificacion> clasificacionList = clasificacionRepository.findAll();
        assertThat(clasificacionList).hasSize(databaseSizeBeforeCreate + 1);
        Clasificacion testClasificacion = clasificacionList.get(clasificacionList.size() - 1);
        assertThat(testClasificacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testClasificacion.getEstado()).isEqualTo(DEFAULT_ESTADO);

        // Validate the Clasificacion in Elasticsearch
        Clasificacion clasificacionEs = clasificacionSearchRepository.findOne(testClasificacion.getId());
        assertThat(clasificacionEs).isEqualToIgnoringGivenFields(testClasificacion);
    }

    @Test
    @Transactional
    public void createClasificacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clasificacionRepository.findAll().size();

        // Create the Clasificacion with an existing ID
        clasificacion.setId(1L);
        ClasificacionDTO clasificacionDTO = clasificacionMapper.toDto(clasificacion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasificacionMockMvc.perform(post("/api/clasificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clasificacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clasificacion in the database
        List<Clasificacion> clasificacionList = clasificacionRepository.findAll();
        assertThat(clasificacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clasificacionRepository.findAll().size();
        // set the field null
        clasificacion.setNombre(null);

        // Create the Clasificacion, which fails.
        ClasificacionDTO clasificacionDTO = clasificacionMapper.toDto(clasificacion);

        restClasificacionMockMvc.perform(post("/api/clasificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clasificacionDTO)))
            .andExpect(status().isBadRequest());

        List<Clasificacion> clasificacionList = clasificacionRepository.findAll();
        assertThat(clasificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clasificacionRepository.findAll().size();
        // set the field null
        clasificacion.setEstado(null);

        // Create the Clasificacion, which fails.
        ClasificacionDTO clasificacionDTO = clasificacionMapper.toDto(clasificacion);

        restClasificacionMockMvc.perform(post("/api/clasificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clasificacionDTO)))
            .andExpect(status().isBadRequest());

        List<Clasificacion> clasificacionList = clasificacionRepository.findAll();
        assertThat(clasificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClasificacions() throws Exception {
        // Initialize the database
        clasificacionRepository.saveAndFlush(clasificacion);

        // Get all the clasificacionList
        restClasificacionMockMvc.perform(get("/api/clasificacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clasificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void getClasificacion() throws Exception {
        // Initialize the database
        clasificacionRepository.saveAndFlush(clasificacion);

        // Get the clasificacion
        restClasificacionMockMvc.perform(get("/api/clasificacions/{id}", clasificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clasificacion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClasificacion() throws Exception {
        // Get the clasificacion
        restClasificacionMockMvc.perform(get("/api/clasificacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasificacion() throws Exception {
        // Initialize the database
        clasificacionRepository.saveAndFlush(clasificacion);
        clasificacionSearchRepository.save(clasificacion);
        int databaseSizeBeforeUpdate = clasificacionRepository.findAll().size();

        // Update the clasificacion
        Clasificacion updatedClasificacion = clasificacionRepository.findOne(clasificacion.getId());
        // Disconnect from session so that the updates on updatedClasificacion are not directly saved in db
        em.detach(updatedClasificacion);
        updatedClasificacion
            .nombre(UPDATED_NOMBRE)
            .estado(UPDATED_ESTADO);
        ClasificacionDTO clasificacionDTO = clasificacionMapper.toDto(updatedClasificacion);

        restClasificacionMockMvc.perform(put("/api/clasificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clasificacionDTO)))
            .andExpect(status().isOk());

        // Validate the Clasificacion in the database
        List<Clasificacion> clasificacionList = clasificacionRepository.findAll();
        assertThat(clasificacionList).hasSize(databaseSizeBeforeUpdate);
        Clasificacion testClasificacion = clasificacionList.get(clasificacionList.size() - 1);
        assertThat(testClasificacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testClasificacion.getEstado()).isEqualTo(UPDATED_ESTADO);

        // Validate the Clasificacion in Elasticsearch
        Clasificacion clasificacionEs = clasificacionSearchRepository.findOne(testClasificacion.getId());
        assertThat(clasificacionEs).isEqualToIgnoringGivenFields(testClasificacion);
    }

    @Test
    @Transactional
    public void updateNonExistingClasificacion() throws Exception {
        int databaseSizeBeforeUpdate = clasificacionRepository.findAll().size();

        // Create the Clasificacion
        ClasificacionDTO clasificacionDTO = clasificacionMapper.toDto(clasificacion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClasificacionMockMvc.perform(put("/api/clasificacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clasificacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Clasificacion in the database
        List<Clasificacion> clasificacionList = clasificacionRepository.findAll();
        assertThat(clasificacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClasificacion() throws Exception {
        // Initialize the database
        clasificacionRepository.saveAndFlush(clasificacion);
        clasificacionSearchRepository.save(clasificacion);
        int databaseSizeBeforeDelete = clasificacionRepository.findAll().size();

        // Get the clasificacion
        restClasificacionMockMvc.perform(delete("/api/clasificacions/{id}", clasificacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean clasificacionExistsInEs = clasificacionSearchRepository.exists(clasificacion.getId());
        assertThat(clasificacionExistsInEs).isFalse();

        // Validate the database is empty
        List<Clasificacion> clasificacionList = clasificacionRepository.findAll();
        assertThat(clasificacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchClasificacion() throws Exception {
        // Initialize the database
        clasificacionRepository.saveAndFlush(clasificacion);
        clasificacionSearchRepository.save(clasificacion);

        // Search the clasificacion
        restClasificacionMockMvc.perform(get("/api/_search/clasificacions?query=id:" + clasificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clasificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clasificacion.class);
        Clasificacion clasificacion1 = new Clasificacion();
        clasificacion1.setId(1L);
        Clasificacion clasificacion2 = new Clasificacion();
        clasificacion2.setId(clasificacion1.getId());
        assertThat(clasificacion1).isEqualTo(clasificacion2);
        clasificacion2.setId(2L);
        assertThat(clasificacion1).isNotEqualTo(clasificacion2);
        clasificacion1.setId(null);
        assertThat(clasificacion1).isNotEqualTo(clasificacion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasificacionDTO.class);
        ClasificacionDTO clasificacionDTO1 = new ClasificacionDTO();
        clasificacionDTO1.setId(1L);
        ClasificacionDTO clasificacionDTO2 = new ClasificacionDTO();
        assertThat(clasificacionDTO1).isNotEqualTo(clasificacionDTO2);
        clasificacionDTO2.setId(clasificacionDTO1.getId());
        assertThat(clasificacionDTO1).isEqualTo(clasificacionDTO2);
        clasificacionDTO2.setId(2L);
        assertThat(clasificacionDTO1).isNotEqualTo(clasificacionDTO2);
        clasificacionDTO1.setId(null);
        assertThat(clasificacionDTO1).isNotEqualTo(clasificacionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clasificacionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clasificacionMapper.fromId(null)).isNull();
    }
}
