package kz.vz.shelter.service.mapper;

import kz.vz.shelter.domain.*;
import kz.vz.shelter.service.dto.PetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pet and its DTO PetDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PetMapper extends EntityMapper <PetDTO, Pet> {
    
    

}
