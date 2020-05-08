package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.LocalDate;

/**
 * A Enquiry.
 */
@Entity
@Table(name = "enquiry")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Enquiry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Size(max = 255)
    @Column(name = "delivery_terms", length = 255)
    private String deliveryTerms;

    @Column(name = "offer_taxt_until")
    private LocalDate offerTaxtUntil;

    @NotNull
    @Size(max = 200)
    @Column(name = "status", length = 200, nullable = false)
    private String status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("enquiries")
    private Category product;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("enquiries")
    private Address deliveryAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Enquiry description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryTerms() {
        return deliveryTerms;
    }

    public Enquiry deliveryTerms(String deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
        return this;
    }

    public void setDeliveryTerms(String deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public LocalDate getOfferTaxtUntil() {
        return offerTaxtUntil;
    }

    public Enquiry offerTaxtUntil(LocalDate offerTaxtUntil) {
        this.offerTaxtUntil = offerTaxtUntil;
        return this;
    }

    public void setOfferTaxtUntil(LocalDate offerTaxtUntil) {
        this.offerTaxtUntil = offerTaxtUntil;
    }

    public String getStatus() {
        return status;
    }

    public Enquiry status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category getProduct() {
        return product;
    }

    public Enquiry product(Category category) {
        this.product = category;
        return this;
    }

    public void setProduct(Category category) {
        this.product = category;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public Enquiry deliveryAddress(Address address) {
        this.deliveryAddress = address;
        return this;
    }

    public void setDeliveryAddress(Address address) {
        this.deliveryAddress = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enquiry)) {
            return false;
        }
        return id != null && id.equals(((Enquiry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Enquiry{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", deliveryTerms='" + getDeliveryTerms() + "'" +
            ", offerTaxtUntil='" + getOfferTaxtUntil() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
