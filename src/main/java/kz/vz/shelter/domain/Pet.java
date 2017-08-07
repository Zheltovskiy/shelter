package kz.vz.shelter.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.Objects;

import kz.vz.shelter.domain.enumeration.PetType;

/**
 * A Pet.
 */
@Document(collection = "pet")
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("color")
    private String color;

    @Field("age_in_months")
    private Integer ageInMonths;

    @Field("type")
    private PetType type;

    @Field("other_type_name")
    private String otherTypeName;

    @Field("main_photo")
    private byte[] mainPhoto;

    @Field("main_photo_content_type")
    private String mainPhotoContentType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Pet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public Pet color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getAgeInMonths() {
        return ageInMonths;
    }

    public Pet ageInMonths(Integer ageInMonths) {
        this.ageInMonths = ageInMonths;
        return this;
    }

    public void setAgeInMonths(Integer ageInMonths) {
        this.ageInMonths = ageInMonths;
    }

    public PetType getType() {
        return type;
    }

    public Pet type(PetType type) {
        this.type = type;
        return this;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getOtherTypeName() {
        return otherTypeName;
    }

    public Pet otherTypeName(String otherTypeName) {
        this.otherTypeName = otherTypeName;
        return this;
    }

    public void setOtherTypeName(String otherTypeName) {
        this.otherTypeName = otherTypeName;
    }

    public byte[] getMainPhoto() {
        return mainPhoto;
    }

    public Pet mainPhoto(byte[] mainPhoto) {
        this.mainPhoto = mainPhoto;
        return this;
    }

    public void setMainPhoto(byte[] mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getMainPhotoContentType() {
        return mainPhotoContentType;
    }

    public Pet mainPhotoContentType(String mainPhotoContentType) {
        this.mainPhotoContentType = mainPhotoContentType;
        return this;
    }

    public void setMainPhotoContentType(String mainPhotoContentType) {
        this.mainPhotoContentType = mainPhotoContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pet pet = (Pet) o;
        if (pet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            ", ageInMonths='" + getAgeInMonths() + "'" +
            ", type='" + getType() + "'" +
            ", otherTypeName='" + getOtherTypeName() + "'" +
            ", mainPhoto='" + getMainPhoto() + "'" +
            ", mainPhotoContentType='" + mainPhotoContentType + "'" +
            "}";
    }
}
