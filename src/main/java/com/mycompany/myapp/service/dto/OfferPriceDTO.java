package com.mycompany.myapp.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.OfferPrice} entity.
 */
public class OfferPriceDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double price;

    @NotNull
    private Instant createdAt;

    private LocalDate finishingDate;


    private Long offerId;

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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getFinishingDate() {
        return finishingDate;
    }

    public void setFinishingDate(LocalDate finishingDate) {
        this.finishingDate = finishingDate;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
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

        OfferPriceDTO offerPriceDTO = (OfferPriceDTO) o;
        if (offerPriceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), offerPriceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OfferPriceDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", finishingDate='" + getFinishingDate() + "'" +
            ", offerId=" + getOfferId() +
            ", enquiryId=" + getEnquiryId() +
            ", enquiryDetailsId=" + getEnquiryDetailsId() +
            "}";
    }
}
