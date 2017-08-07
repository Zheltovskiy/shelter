package kz.vz.shelter.service;

import kz.vz.shelter.domain.Pet;
import kz.vz.shelter.repository.PetRepository;
import kz.vz.shelter.service.dto.PetDTO;
import kz.vz.shelter.service.mapper.PetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Pet.
 */
@Service
public class PetService {

    private final Logger log = LoggerFactory.getLogger(PetService.class);

    private final PetRepository petRepository;

    private final PetMapper petMapper;

    public PetService(PetRepository petRepository, PetMapper petMapper) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
    }

    /**
     * Save a pet.
     *
     * @param petDTO the entity to save
     * @return the persisted entity
     */
    public PetDTO save(PetDTO petDTO) {
        log.debug("Request to save Pet : {}", petDTO);
        Pet pet = petMapper.toEntity(petDTO);
        pet = petRepository.save(pet);
        return petMapper.toDto(pet);
    }

    /**
     *  Get all the pets.
     *
     *  @return the list of entities
     */
    public List<PetDTO> findAll() {
        log.debug("Request to get all Pets");
        return petRepository.findAll().stream()
            .map(petMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one pet by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public PetDTO findOne(String id) {
        log.debug("Request to get Pet : {}", id);
        Pet pet = petRepository.findOne(id);
        return petMapper.toDto(pet);
    }

    /**
     *  Delete the  pet by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Pet : {}", id);
        petRepository.delete(id);
    }
}
