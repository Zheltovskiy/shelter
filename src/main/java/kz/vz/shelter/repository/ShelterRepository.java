package kz.vz.shelter.repository;

import kz.vz.shelter.domain.Shelter;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Shelter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShelterRepository extends MongoRepository<Shelter,String> {
    
}
