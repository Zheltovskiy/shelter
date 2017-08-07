package kz.vz.shelter.web.rest;

import kz.vz.shelter.ShelterApp;

import kz.vz.shelter.domain.Pet;
import kz.vz.shelter.repository.PetRepository;
import kz.vz.shelter.service.PetService;
import kz.vz.shelter.service.dto.PetDTO;
import kz.vz.shelter.service.mapper.PetMapper;
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
import org.springframework.util.Base64Utils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import kz.vz.shelter.domain.enumeration.PetType;
/**
 * Test class for the PetResource REST controller.
 *
 * @see PetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShelterApp.class)
public class PetResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE_IN_MONTHS = 1;
    private static final Integer UPDATED_AGE_IN_MONTHS = 2;

    private static final PetType DEFAULT_TYPE = PetType.CAT;
    private static final PetType UPDATED_TYPE = PetType.DOG;

    private static final String DEFAULT_OTHER_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_TYPE_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_MAIN_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MAIN_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_MAIN_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MAIN_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private PetService petService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPetMockMvc;

    private Pet pet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PetResource petResource = new PetResource(petService);
        this.restPetMockMvc = MockMvcBuilders.standaloneSetup(petResource)
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
    public static Pet createEntity() {
        Pet pet = new Pet()
            .name(DEFAULT_NAME)
            .color(DEFAULT_COLOR)
            .ageInMonths(DEFAULT_AGE_IN_MONTHS)
            .type(DEFAULT_TYPE)
            .otherTypeName(DEFAULT_OTHER_TYPE_NAME)
            .mainPhoto(DEFAULT_MAIN_PHOTO)
            .mainPhotoContentType(DEFAULT_MAIN_PHOTO_CONTENT_TYPE);
        return pet;
    }

    @Before
    public void initTest() {
        petRepository.deleteAll();
        pet = createEntity();
    }

    @Test
    public void createPet() throws Exception {
        int databaseSizeBeforeCreate = petRepository.findAll().size();

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);
        restPetMockMvc.perform(post("/api/pets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isCreated());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate + 1);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPet.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testPet.getAgeInMonths()).isEqualTo(DEFAULT_AGE_IN_MONTHS);
        assertThat(testPet.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPet.getOtherTypeName()).isEqualTo(DEFAULT_OTHER_TYPE_NAME);
        assertThat(testPet.getMainPhoto()).isEqualTo(DEFAULT_MAIN_PHOTO);
        assertThat(testPet.getMainPhotoContentType()).isEqualTo(DEFAULT_MAIN_PHOTO_CONTENT_TYPE);
    }

    @Test
    public void createPetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = petRepository.findAll().size();

        // Create the Pet with an existing ID
        pet.setId("existing_id");
        PetDTO petDTO = petMapper.toDto(pet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetMockMvc.perform(post("/api/pets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllPets() throws Exception {
        // Initialize the database
        petRepository.save(pet);

        // Get all the petList
        restPetMockMvc.perform(get("/api/pets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())))
            .andExpect(jsonPath("$.[*].ageInMonths").value(hasItem(DEFAULT_AGE_IN_MONTHS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].otherTypeName").value(hasItem(DEFAULT_OTHER_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].mainPhotoContentType").value(hasItem(DEFAULT_MAIN_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].mainPhoto").value(hasItem(Base64Utils.encodeToString(DEFAULT_MAIN_PHOTO))));
    }

    @Test
    public void getPet() throws Exception {
        // Initialize the database
        petRepository.save(pet);

        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", pet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pet.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()))
            .andExpect(jsonPath("$.ageInMonths").value(DEFAULT_AGE_IN_MONTHS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.otherTypeName").value(DEFAULT_OTHER_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.mainPhotoContentType").value(DEFAULT_MAIN_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.mainPhoto").value(Base64Utils.encodeToString(DEFAULT_MAIN_PHOTO)));
    }

    @Test
    public void getNonExistingPet() throws Exception {
        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePet() throws Exception {
        // Initialize the database
        petRepository.save(pet);
        int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Update the pet
        Pet updatedPet = petRepository.findOne(pet.getId());
        updatedPet
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR)
            .ageInMonths(UPDATED_AGE_IN_MONTHS)
            .type(UPDATED_TYPE)
            .otherTypeName(UPDATED_OTHER_TYPE_NAME)
            .mainPhoto(UPDATED_MAIN_PHOTO)
            .mainPhotoContentType(UPDATED_MAIN_PHOTO_CONTENT_TYPE);
        PetDTO petDTO = petMapper.toDto(updatedPet);

        restPetMockMvc.perform(put("/api/pets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isOk());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
        Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPet.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testPet.getAgeInMonths()).isEqualTo(UPDATED_AGE_IN_MONTHS);
        assertThat(testPet.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPet.getOtherTypeName()).isEqualTo(UPDATED_OTHER_TYPE_NAME);
        assertThat(testPet.getMainPhoto()).isEqualTo(UPDATED_MAIN_PHOTO);
        assertThat(testPet.getMainPhotoContentType()).isEqualTo(UPDATED_MAIN_PHOTO_CONTENT_TYPE);
    }

    @Test
    public void updateNonExistingPet() throws Exception {
        int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Create the Pet
        PetDTO petDTO = petMapper.toDto(pet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPetMockMvc.perform(put("/api/pets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(petDTO)))
            .andExpect(status().isCreated());

        // Validate the Pet in the database
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deletePet() throws Exception {
        // Initialize the database
        petRepository.save(pet);
        int databaseSizeBeforeDelete = petRepository.findAll().size();

        // Get the pet
        restPetMockMvc.perform(delete("/api/pets/{id}", pet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pet.class);
        Pet pet1 = new Pet();
        pet1.setId("id1");
        Pet pet2 = new Pet();
        pet2.setId(pet1.getId());
        assertThat(pet1).isEqualTo(pet2);
        pet2.setId("id2");
        assertThat(pet1).isNotEqualTo(pet2);
        pet1.setId(null);
        assertThat(pet1).isNotEqualTo(pet2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PetDTO.class);
        PetDTO petDTO1 = new PetDTO();
        petDTO1.setId("id1");
        PetDTO petDTO2 = new PetDTO();
        assertThat(petDTO1).isNotEqualTo(petDTO2);
        petDTO2.setId(petDTO1.getId());
        assertThat(petDTO1).isEqualTo(petDTO2);
        petDTO2.setId("id2");
        assertThat(petDTO1).isNotEqualTo(petDTO2);
        petDTO1.setId(null);
        assertThat(petDTO1).isNotEqualTo(petDTO2);
    }
}
