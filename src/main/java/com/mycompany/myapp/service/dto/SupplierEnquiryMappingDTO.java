package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.SupplierEnquiryMapping} entity.
 */
public class SupplierEnquiryMappingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;


    private Long enquiryId;

    private Long supplierId;

    private Long createdById;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Long enquiryId) {
        this.enquiryId = enquiryId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long userProfileId) {
        this.supplierId = userProfileId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long userProfileId) {
        this.createdById = userProfileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SupplierEnquiryMappingDTO supplierEnquiryMappingDTO = (SupplierEnquiryMappingDTO) o;
        if (supplierEnquiryMappingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), supplierEnquiryMappingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SupplierEnquiryMappingDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", enquiryId=" + getEnquiryId() +
            ", supplierId=" + getSupplierId() +
            ", createdById=" + getCreatedById() +
            "}";
    }
}
