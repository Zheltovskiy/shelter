package kz.vz.shelter.service;

import kz.vz.shelter.domain.Photo;
import kz.vz.shelter.repository.PhotoRepository;
import kz.vz.shelter.service.dto.PhotoDTO;
import kz.vz.shelter.service.mapper.PhotoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Photo.
 */
@Service
public class PhotoService {

    private final Logger log = LoggerFactory.getLogger(PhotoService.class);

    private final PhotoRepository photoRepository;

    private final PhotoMapper photoMapper;

    public PhotoService(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    /**
     * Save a photo.
     *
     * @param photoDTO the entity to save
     * @return the persisted entity
     */
    public PhotoDTO save(PhotoDTO photoDTO) {
        log.debug("Request to save Photo : {}", photoDTO);
        Photo photo = photoMapper.toEntity(photoDTO);
        photo = photoRepository.save(photo);
        return photoMapper.toDto(photo);
    }

    /**
     *  Get all the photos.
     *
     *  @return the list of entities
     */
    public List<PhotoDTO> findAll() {
        log.debug("Request to get all Photos");
        return photoRepository.findAll().stream()
            .map(photoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one photo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public PhotoDTO findOne(String id) {
        log.debug("Request to get Photo : {}", id);
        Photo photo = photoRepository.findOne(id);
        return photoMapper.toDto(photo);
    }

    /**
     *  Delete the  photo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Photo : {}", id);
        photoRepository.delete(id);
    }
}
