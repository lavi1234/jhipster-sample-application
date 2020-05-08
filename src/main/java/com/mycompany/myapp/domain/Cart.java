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
 * A Cart.
 */
@Entity
@Table(name = "cart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("carts")
    private Enquiry enquiry;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("carts")
    private UserProfile supplier;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("carts")
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

    public Cart createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public Cart enquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
        return this;
    }

    public void setEnquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
    }

    public UserProfile getSupplier() {
        return supplier;
    }

    public Cart supplier(UserProfile userProfile) {
        this.supplier = userProfile;
        return this;
    }

    public void setSupplier(UserProfile userProfile) {
        this.supplier = userProfile;
    }

    public UserProfile getCreatedBy() {
        return createdBy;
    }

    public Cart createdBy(UserProfile userProfile) {
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
        if (!(o instanceof Cart)) {
            return false;
        }
        return id != null && id.equals(((Cart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cart{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            "}";
    }
}
