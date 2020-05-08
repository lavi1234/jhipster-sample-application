package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.EnquiryMaterial} entity.
 */
public class EnquiryMaterialDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    @NotNull
    @Size(max = 200)
    private String dimension;

    @NotNull
    private Integer materialId;

    @NotNull
    @Size(max = 200)
    private String color;

    @Size(max = 255)
    private String comments;


    private Long enquiryDetailsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public Integer getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Long getEnquiryDetailsId() {
        return enquiryDetailsId;
    }

    public void setEnquiryDetailsId(Long enquiryDetailsId) {
        this.enquiryDetailsId = enquiryDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnquiryMaterialDTO enquiryMaterialDTO = (EnquiryMaterialDTO) o;
        if (enquiryMaterialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enquiryMaterialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnquiryMaterialDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dimension='" + getDimension() + "'" +
            ", materialId=" + getMaterialId() +
            ", color='" + getColor() + "'" +
            ", comments='" + getComments() + "'" +
            ", enquiryDetailsId=" + getEnquiryDetailsId() +
            "}";
    }
}
