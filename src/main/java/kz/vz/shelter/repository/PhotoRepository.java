package kz.vz.shelter.repository;

import kz.vz.shelter.domain.Photo;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Photo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhotoRepository extends MongoRepository<Photo,String> {
    
}
