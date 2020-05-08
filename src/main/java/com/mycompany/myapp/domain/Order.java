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
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @NotNull
    @Size(max = 200)
    @Column(name = "status", length = 200, nullable = false)
    private String status;

    @NotNull
    @Column(name = "commission_date", nullable = false)
    private LocalDate commissionDate;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @NotNull
    @Size(max = 255)
    @Column(name = "remarks", length = 255, nullable = false)
    private String remarks;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("orders")
    private Offer offer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("orders")
    private UserProfile buyer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("orders")
    private UserProfile supplier;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("orders")
    private Enquiry enquiry;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("orders")
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

    public Order price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public Order deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public Order status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCommissionDate() {
        return commissionDate;
    }

    public Order commissionDate(LocalDate commissionDate) {
        this.commissionDate = commissionDate;
        return this;
    }

    public void setCommissionDate(LocalDate commissionDate) {
        this.commissionDate = commissionDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Order createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Order updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public Order remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Offer getOffer() {
        return offer;
    }

    public Order offer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public UserProfile getBuyer() {
        return buyer;
    }

    public Order buyer(UserProfile userProfile) {
        this.buyer = userProfile;
        return this;
    }

    public void setBuyer(UserProfile userProfile) {
        this.buyer = userProfile;
    }

    public UserProfile getSupplier() {
        return supplier;
    }

    public Order supplier(UserProfile userProfile) {
        this.supplier = userProfile;
        return this;
    }

    public void setSupplier(UserProfile userProfile) {
        this.supplier = userProfile;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public Order enquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
        return this;
    }

    public void setEnquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
    }

    public EnquiryDetails getEnquiryDetails() {
        return enquiryDetails;
    }

    public Order enquiryDetails(EnquiryDetails enquiryDetails) {
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
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", commissionDate='" + getCommissionDate() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
