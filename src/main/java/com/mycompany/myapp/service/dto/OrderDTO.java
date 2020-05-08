package com.mycompany.myapp.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Order} entity.
 */
public class OrderDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double price;

    @NotNull
    private LocalDate deliveryDate;

    @NotNull
    @Size(max = 200)
    private String status;

    @NotNull
    private LocalDate commissionDate;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    @NotNull
    @Size(max = 255)
    private String remarks;


    private Long offerId;

    private Long buyerId;

    private Long supplierId;

    private Long enquiryId;

    private Long enquiryDetailsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCommissionDate() {
        return commissionDate;
    }

    public void setCommissionDate(LocalDate commissionDate) {
        this.commissionDate = commissionDate;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long userProfileId) {
        this.buyerId = userProfileId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long userProfileId) {
        this.supplierId = userProfileId;
    }

    public Long getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Long enquiryId) {
        this.enquiryId = enquiryId;
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

        OrderDTO orderDTO = (OrderDTO) o;
        if (orderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", commissionDate='" + getCommissionDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", offerId=" + getOfferId() +
            ", buyerId=" + getBuyerId() +
            ", supplierId=" + getSupplierId() +
            ", enquiryId=" + getEnquiryId() +
            ", enquiryDetailsId=" + getEnquiryDetailsId() +
            "}";
    }
}
