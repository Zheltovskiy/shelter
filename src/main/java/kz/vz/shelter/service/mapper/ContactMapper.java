package kz.vz.shelter.service.mapper;

import kz.vz.shelter.domain.*;
import kz.vz.shelter.service.dto.ContactDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contact and its DTO ContactDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactMapper extends EntityMapper <ContactDTO, Contact> {
    
    

}
