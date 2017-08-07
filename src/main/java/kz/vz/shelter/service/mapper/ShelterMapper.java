package kz.vz.shelter.service.mapper;

import kz.vz.shelter.domain.*;
import kz.vz.shelter.service.dto.ShelterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Shelter and its DTO ShelterDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ShelterMapper extends EntityMapper <ShelterDTO, Shelter> {
    
    

}
