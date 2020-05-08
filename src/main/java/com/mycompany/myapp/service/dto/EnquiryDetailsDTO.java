package com.mycompany.myapp.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.EnquiryDetails} entity.
 */
public class EnquiryDetailsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer version;

    @NotNull
    private Double edition;

    @NotNull
    private Integer format;

    @NotNull
    @Size(max = 255)
    private String documents;

    @NotNull
    private LocalDate deliveryDate;

    @NotNull
    @Size(max = 255)
    private String remarks;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;


    private Long enquiryId;

    private Long printId;

    private Long finishingId;

    private Long handlingId;

    private Long packagingId;

    private Long createdById;

    private Long offerId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Double getEdition() {
        return edition;
    }

    public void setEdition(Double edition) {
        this.edition = edition;
    }

    public Integer getFormat() {
        return format;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public Long getPrintId() {
        return printId;
    }

    public void setPrintId(Long categoryId) {
        this.printId = categoryId;
    }

    public Long getFinishingId() {
        return finishingId;
    }

    public void setFinishingId(Long categoryId) {
        this.finishingId = categoryId;
    }

    public Long getHandlingId() {
        return handlingId;
    }

    public void setHandlingId(Long categoryId) {
        this.handlingId = categoryId;
    }

    public Long getPackagingId() {
        return packagingId;
    }

    public void setPackagingId(Long categoryId) {
        this.packagingId = categoryId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long userProfileId) {
        this.createdById = userProfileId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnquiryDetailsDTO enquiryDetailsDTO = (EnquiryDetailsDTO) o;
        if (enquiryDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enquiryDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnquiryDetailsDTO{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", edition=" + getEdition() +
            ", format=" + getFormat() +
            ", documents='" + getDocuments() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", enquiryId=" + getEnquiryId() +
            ", printId=" + getPrintId() +
            ", finishingId=" + getFinishingId() +
            ", handlingId=" + getHandlingId() +
            ", packagingId=" + getPackagingId() +
            ", createdById=" + getCreatedById() +
            ", offerId=" + getOfferId() +
            "}";
    }
}
