package com.mycompany.myapp.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Offer} entity.
 */
public class OfferDTO implements Serializable {
    
    private Long id;

    @NotNull
    private LocalDate validUpto;

    @NotNull
    @Size(max = 200)
    private String status;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;


    private Long supplierEnquiryId;

    private Long createdById;

    private Long updatedById;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(LocalDate validUpto) {
        this.validUpto = validUpto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Long getSupplierEnquiryId() {
        return supplierEnquiryId;
    }

    public void setSupplierEnquiryId(Long supplierEnquiryMappingId) {
        this.supplierEnquiryId = supplierEnquiryMappingId;
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

        OfferDTO offerDTO = (OfferDTO) o;
        if (offerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfferDTO{" +
            "id=" + getId() +
            ", validUpto='" + getValidUpto() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", supplierEnquiryId=" + getSupplierEnquiryId() +
            ", createdById=" + getCreatedById() +
            ", updatedById=" + getUpdatedById() +
            "}";
    }
}
