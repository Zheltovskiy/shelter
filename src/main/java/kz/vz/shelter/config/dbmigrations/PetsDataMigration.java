package kz.vz.shelter.config.dbmigrations;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import kz.vz.shelter.domain.Pet;
import kz.vz.shelter.domain.enumeration.PetType;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

/**
 * Creates test pets data
 */
@ChangeLog(order = "002")
public class PetsDataMigration {

    @ChangeSet(order = "01", author = "v.zheltovskiy", id = "01-addTestCats")
    public void addTestCats(MongoTemplate mongoTemplate) {
        Pet timosha = new Pet();
        timosha.setName("Тимоша");
        timosha.setColor("RED");
        timosha.setAgeInMonths(36);
        timosha.setMainPhoto(getPetImage("timosha"));
        timosha.setMainPhotoContentType("image/jpg");
        timosha.setType(PetType.CAT);
        mongoTemplate.save(timosha);

        Pet ugolek = new Pet();
        ugolek.setName("Уголёк");
        ugolek.setColor("BLACK");
        ugolek.setAgeInMonths(2);
        ugolek.setMainPhoto(getPetImage("ugolek"));
        ugolek.setMainPhotoContentType("image/jpg");
        ugolek.setType(PetType.CAT);
        mongoTemplate.save(ugolek);
    }

    private byte[] getPetImage(String name) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("data/pets/" + name + ".jpg").getFile());
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(String.format("Cannot read file with test image: %s", name), e);
        }
    }

    @ChangeSet(order = "02", author = "v.zheltovskiy", id = "02-addTestDogs")
    public void addTestDogs(MongoTemplate mongoTemplate) {
        Pet rolda = new Pet();
        rolda.setName("Рольда");
        rolda.setColor("BROWN");
        rolda.setAgeInMonths(4);
        rolda.setMainPhoto(getPetImage("rolda"));
        rolda.setMainPhotoContentType("image/jpg");
        rolda.setType(PetType.DOG);
        mongoTemplate.save(rolda);

        Pet druzhok = new Pet();
        druzhok.setName("Уголёк");
        druzhok.setColor("BLACK");
        druzhok.setAgeInMonths(5);
        druzhok.setMainPhoto(getPetImage("druzhok"));
        druzhok.setMainPhotoContentType("image/jpg");
        druzhok.setType(PetType.DOG);
        mongoTemplate.save(druzhok);
    }

}
