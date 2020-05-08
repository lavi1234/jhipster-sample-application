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
 * A Offer.
 */
@Entity
@Table(name = "offer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "valid_upto", nullable = false)
    private LocalDate validUpto;

    @NotNull
    @Size(max = 200)
    @Column(name = "status", length = 200, nullable = false)
    private String status;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("offers")
    private SupplierEnquiryMapping supplierEnquiry;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("offers")
    private UserProfile createdBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("offers")
    private UserProfile updatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getValidUpto() {
        return validUpto;
    }

    public Offer validUpto(LocalDate validUpto) {
        this.validUpto = validUpto;
        return this;
    }

    public void setValidUpto(LocalDate validUpto) {
        this.validUpto = validUpto;
    }

    public String getStatus() {
        return status;
    }

    public Offer status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Offer createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Offer updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SupplierEnquiryMapping getSupplierEnquiry() {
        return supplierEnquiry;
    }

    public Offer supplierEnquiry(SupplierEnquiryMapping supplierEnquiryMapping) {
        this.supplierEnquiry = supplierEnquiryMapping;
        return this;
    }

    public void setSupplierEnquiry(SupplierEnquiryMapping supplierEnquiryMapping) {
        this.supplierEnquiry = supplierEnquiryMapping;
    }

    public UserProfile getCreatedBy() {
        return createdBy;
    }

    public Offer createdBy(UserProfile userProfile) {
        this.createdBy = userProfile;
        return this;
    }

    public void setCreatedBy(UserProfile userProfile) {
        this.createdBy = userProfile;
    }

    public UserProfile getUpdatedBy() {
        return updatedBy;
    }

    public Offer updatedBy(UserProfile userProfile) {
        this.updatedBy = userProfile;
        return this;
    }

    public void setUpdatedBy(UserProfile userProfile) {
        this.updatedBy = userProfile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Offer)) {
            return false;
        }
        return id != null && id.equals(((Offer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Offer{" +
            "id=" + getId() +
            ", validUpto='" + getValidUpto() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
