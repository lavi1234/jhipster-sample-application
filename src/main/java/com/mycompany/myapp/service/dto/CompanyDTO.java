package com.mycompany.myapp.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Company} entity.
 */
public class CompanyDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    @Size(max = 200)
    private String email;

    @Size(max = 255)
    private String termsConditions;

    @Size(max = 255)
    private String aboutUs;

    /**
     * Url of uploaded catalogue document
     */
    @Size(max = 255)
    @ApiModelProperty(value = "Url of uploaded catalogue document")
    private String catalogue;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;


    private Long addressId;

    private Long createdById;

    private Long updatedById;
    
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTermsConditions() {
        return termsConditions;
    }

    public void setTermsConditions(String termsConditions) {
        this.termsConditions = termsConditions;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(String catalogue) {
        this.catalogue = catalogue;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long userProfileId) {
        this.createdById = userProfileId;
    }

    public Long getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(Long userProfileId) {
        this.updatedById = userProfileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (companyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", termsConditions='" + getTermsConditions() + "'" +
            ", aboutUs='" + getAboutUs() + "'" +
            ", catalogue='" + getCatalogue() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", addressId=" + getAddressId() +
            ", createdById=" + getCreatedById() +
            ", updatedById=" + getUpdatedById() +
            "}";
    }
}
