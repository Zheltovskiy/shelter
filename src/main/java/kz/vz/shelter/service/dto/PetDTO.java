package kz.vz.shelter.service.dto;


import java.io.Serializable;
import java.util.Objects;
import kz.vz.shelter.domain.enumeration.PetType;

/**
 * A DTO for the Pet entity.
 */
public class PetDTO implements Serializable {

    private String id;

    private String name;

    private String color;

    private Integer ageInMonths;

    private PetType type;

    private String otherTypeName;

    private byte[] mainPhoto;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getAgeInMonths() {
        return ageInMonths;
    }

    public void setAgeInMonths(Integer ageInMonths) {
        this.ageInMonths = ageInMonths;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getOtherTypeName() {
        return otherTypeName;
    }

    public void setOtherTypeName(String otherTypeName) {
        this.otherTypeName = otherTypeName;
    }

    public byte[] getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(byte[] mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getMainPhotoContentType() {
        return mainPhotoContentType;
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

        PetDTO petDTO = (PetDTO) o;
        if(petDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), petDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PetDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            ", ageInMonths='" + getAgeInMonths() + "'" +
            ", type='" + getType() + "'" +
            ", otherTypeName='" + getOtherTypeName() + "'" +
            ", mainPhoto='" + getMainPhoto() + "'" +
            "}";
    }
}
