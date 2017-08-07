package kz.vz.shelter.service;

import kz.vz.shelter.domain.Shelter;
import kz.vz.shelter.repository.ShelterRepository;
import kz.vz.shelter.service.dto.ShelterDTO;
import kz.vz.shelter.service.mapper.ShelterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Shelter.
 */
@Service
public class ShelterService {

    private final Logger log = LoggerFactory.getLogger(ShelterService.class);

    private final ShelterRepository shelterRepository;

    private final ShelterMapper shelterMapper;

    public ShelterService(ShelterRepository shelterRepository, ShelterMapper shelterMapper) {
        this.shelterRepository = shelterRepository;
        this.shelterMapper = shelterMapper;
    }

    /**
     * Save a shelter.
     *
     * @param shelterDTO the entity to save
     * @return the persisted entity
     */
    public ShelterDTO save(ShelterDTO shelterDTO) {
        log.debug("Request to save Shelter : {}", shelterDTO);
        Shelter shelter = shelterMapper.toEntity(shelterDTO);
        shelter = shelterRepository.save(shelter);
        return shelterMapper.toDto(shelter);
    }

    /**
     *  Get all the shelters.
     *
     *  @return the list of entities
     */
    public List<ShelterDTO> findAll() {
        log.debug("Request to get all Shelters");
        return shelterRepository.findAll().stream()
            .map(shelterMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one shelter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ShelterDTO findOne(String id) {
        log.debug("Request to get Shelter : {}", id);
        Shelter shelter = shelterRepository.findOne(id);
        return shelterMapper.toDto(shelter);
    }

    /**
     *  Delete the  shelter by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Shelter : {}", id);
        shelterRepository.delete(id);
    }
}
