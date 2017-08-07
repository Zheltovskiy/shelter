package kz.vz.shelter.repository;

import kz.vz.shelter.domain.Pet;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Pet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PetRepository extends MongoRepository<Pet,String> {
    
}
