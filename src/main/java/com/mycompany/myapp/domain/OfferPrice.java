package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A OfferPrice.
 */
@Entity
@Table(name = "offer_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OfferPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "finishing_date")
    private LocalDate finishingDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("offerPrices")
    private Offer offer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("offerPrices")
    private Enquiry enquiry;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("offerPrices")
    private EnquiryDetails enquiryDetails;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public OfferPrice price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public OfferPrice createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getFinishingDate() {
        return finishingDate;
    }

    public OfferPrice finishingDate(LocalDate finishingDate) {
        this.finishingDate = finishingDate;
        return this;
    }

    public void setFinishingDate(LocalDate finishingDate) {
        this.finishingDate = finishingDate;
    }

    public Offer getOffer() {
        return offer;
    }

    public OfferPrice offer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public OfferPrice enquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
        return this;
    }

    public void setEnquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
    }

    public EnquiryDetails getEnquiryDetails() {
        return enquiryDetails;
    }

    public OfferPrice enquiryDetails(EnquiryDetails enquiryDetails) {
        this.enquiryDetails = enquiryDetails;
        return this;
    }

    public void setEnquiryDetails(EnquiryDetails enquiryDetails) {
        this.enquiryDetails = enquiryDetails;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OfferPrice)) {
            return false;
        }
        return id != null && id.equals(((OfferPrice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OfferPrice{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", finishingDate='" + getFinishingDate() + "'" +
            "}";
    }
}
