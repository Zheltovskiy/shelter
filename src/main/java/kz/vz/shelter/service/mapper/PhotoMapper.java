package kz.vz.shelter.service.mapper;

import kz.vz.shelter.domain.*;
import kz.vz.shelter.service.dto.PhotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Photo and its DTO PhotoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhotoMapper extends EntityMapper <PhotoDTO, Photo> {
    
    

}
