package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A SupplierEnquiryMapping.
 */
@Entity
@Table(name = "supplier_enquiry_mapping")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SupplierEnquiryMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplierEnquiryMappings")
    private Enquiry enquiry;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplierEnquiryMappings")
    private UserProfile supplier;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("supplierEnquiryMappings")
    private UserProfile createdBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public SupplierEnquiryMapping createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public SupplierEnquiryMapping updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public SupplierEnquiryMapping enquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
        return this;
    }

    public void setEnquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
    }

    public UserProfile getSupplier() {
        return supplier;
    }

    public SupplierEnquiryMapping supplier(UserProfile userProfile) {
        this.supplier = userProfile;
        return this;
    }

    public void setSupplier(UserProfile userProfile) {
        this.supplier = userProfile;
    }

    public UserProfile getCreatedBy() {
        return createdBy;
    }

    public SupplierEnquiryMapping createdBy(UserProfile userProfile) {
        this.createdBy = userProfile;
        return this;
    }

    public void setCreatedBy(UserProfile userProfile) {
        this.createdBy = userProfile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupplierEnquiryMapping)) {
            return false;
        }
        return id != null && id.equals(((SupplierEnquiryMapping) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SupplierEnquiryMapping{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
