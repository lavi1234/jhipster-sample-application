package com.mycompany.myapp.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Enquiry} entity.
 */
public class EnquiryDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String deliveryTerms;

    private LocalDate offerTaxtUntil;

    @NotNull
    @Size(max = 200)
    private String status;


    private Long productId;

    private Long deliveryAddressId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryTerms() {
        return deliveryTerms;
    }

    public void setDeliveryTerms(String deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public LocalDate getOfferTaxtUntil() {
        return offerTaxtUntil;
    }

    public void setOfferTaxtUntil(LocalDate offerTaxtUntil) {
        this.offerTaxtUntil = offerTaxtUntil;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long categoryId) {
        this.productId = categoryId;
    }

    public Long getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(Long addressId) {
        this.deliveryAddressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnquiryDTO enquiryDTO = (EnquiryDTO) o;
        if (enquiryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enquiryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnquiryDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", deliveryTerms='" + getDeliveryTerms() + "'" +
            ", offerTaxtUntil='" + getOfferTaxtUntil() + "'" +
            ", status='" + getStatus() + "'" +
            ", productId=" + getProductId() +
            ", deliveryAddressId=" + getDeliveryAddressId() +
            "}";
    }
}
