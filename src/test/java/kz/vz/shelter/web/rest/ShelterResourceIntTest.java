package kz.vz.shelter.web.rest;

import kz.vz.shelter.ShelterApp;

import kz.vz.shelter.domain.Shelter;
import kz.vz.shelter.repository.ShelterRepository;
import kz.vz.shelter.service.ShelterService;
import kz.vz.shelter.service.dto.ShelterDTO;
import kz.vz.shelter.service.mapper.ShelterMapper;
import kz.vz.shelter.web.rest.errors.ExceptionTranslator;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShelterResource REST controller.
 *
 * @see ShelterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShelterApp.class)
public class ShelterResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    @Autowired
    private ShelterRepository shelterRepository;

    @Autowired
    private ShelterMapper shelterMapper;

    @Autowired
    private ShelterService shelterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restShelterMockMvc;

    private Shelter shelter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ShelterResource shelterResource = new ShelterResource(shelterService);
        this.restShelterMockMvc = MockMvcBuilders.standaloneSetup(shelterResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shelter createEntity() {
        Shelter shelter = new Shelter()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return shelter;
    }

    @Before
    public void initTest() {
        shelterRepository.deleteAll();
        shelter = createEntity();
    }

    @Test
    public void createShelter() throws Exception {
        int databaseSizeBeforeCreate = shelterRepository.findAll().size();

        // Create the Shelter
        ShelterDTO shelterDTO = shelterMapper.toDto(shelter);
        restShelterMockMvc.perform(post("/api/shelters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shelterDTO)))
            .andExpect(status().isCreated());

        // Validate the Shelter in the database
        List<Shelter> shelterList = shelterRepository.findAll();
        assertThat(shelterList).hasSize(databaseSizeBeforeCreate + 1);
        Shelter testShelter = shelterList.get(shelterList.size() - 1);
        assertThat(testShelter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testShelter.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testShelter.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testShelter.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    public void createShelterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shelterRepository.findAll().size();

        // Create the Shelter with an existing ID
        shelter.setId("existing_id");
        ShelterDTO shelterDTO = shelterMapper.toDto(shelter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShelterMockMvc.perform(post("/api/shelters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shelterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Shelter> shelterList = shelterRepository.findAll();
        assertThat(shelterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllShelters() throws Exception {
        // Initialize the database
        shelterRepository.save(shelter);

        // Get all the shelterList
        restShelterMockMvc.perform(get("/api/shelters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shelter.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }

    @Test
    public void getShelter() throws Exception {
        // Initialize the database
        shelterRepository.save(shelter);

        // Get the shelter
        restShelterMockMvc.perform(get("/api/shelters/{id}", shelter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shelter.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }

    @Test
    public void getNonExistingShelter() throws Exception {
        // Get the shelter
        restShelterMockMvc.perform(get("/api/shelters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateShelter() throws Exception {
        // Initialize the database
        shelterRepository.save(shelter);
        int databaseSizeBeforeUpdate = shelterRepository.findAll().size();

        // Update the shelter
        Shelter updatedShelter = shelterRepository.findOne(shelter.getId());
        updatedShelter
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        ShelterDTO shelterDTO = shelterMapper.toDto(updatedShelter);

        restShelterMockMvc.perform(put("/api/shelters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shelterDTO)))
            .andExpect(status().isOk());

        // Validate the Shelter in the database
        List<Shelter> shelterList = shelterRepository.findAll();
        assertThat(shelterList).hasSize(databaseSizeBeforeUpdate);
        Shelter testShelter = shelterList.get(shelterList.size() - 1);
        assertThat(testShelter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testShelter.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testShelter.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testShelter.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    public void updateNonExistingShelter() throws Exception {
        int databaseSizeBeforeUpdate = shelterRepository.findAll().size();

        // Create the Shelter
        ShelterDTO shelterDTO = shelterMapper.toDto(shelter);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restShelterMockMvc.perform(put("/api/shelters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shelterDTO)))
            .andExpect(status().isCreated());

        // Validate the Shelter in the database
        List<Shelter> shelterList = shelterRepository.findAll();
        assertThat(shelterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteShelter() throws Exception {
        // Initialize the database
        shelterRepository.save(shelter);
        int databaseSizeBeforeDelete = shelterRepository.findAll().size();

        // Get the shelter
        restShelterMockMvc.perform(delete("/api/shelters/{id}", shelter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Shelter> shelterList = shelterRepository.findAll();
        assertThat(shelterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Shelter.class);
        Shelter shelter1 = new Shelter();
        shelter1.setId("id1");
        Shelter shelter2 = new Shelter();
        shelter2.setId(shelter1.getId());
        assertThat(shelter1).isEqualTo(shelter2);
        shelter2.setId("id2");
        assertThat(shelter1).isNotEqualTo(shelter2);
        shelter1.setId(null);
        assertThat(shelter1).isNotEqualTo(shelter2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShelterDTO.class);
        ShelterDTO shelterDTO1 = new ShelterDTO();
        shelterDTO1.setId("id1");
        ShelterDTO shelterDTO2 = new ShelterDTO();
        assertThat(shelterDTO1).isNotEqualTo(shelterDTO2);
        shelterDTO2.setId(shelterDTO1.getId());
        assertThat(shelterDTO1).isEqualTo(shelterDTO2);
        shelterDTO2.setId("id2");
        assertThat(shelterDTO1).isNotEqualTo(shelterDTO2);
        shelterDTO1.setId(null);
        assertThat(shelterDTO1).isNotEqualTo(shelterDTO2);
    }
}
