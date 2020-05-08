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
 * A EnquiryDetails.
 */
@Entity
@Table(name = "enquiry_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EnquiryDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

    @NotNull
    @Column(name = "edition", nullable = false)
    private Double edition;

    @NotNull
    @Column(name = "format", nullable = false)
    private Integer format;

    @NotNull
    @Size(max = 255)
    @Column(name = "documents", length = 255, nullable = false)
    private String documents;

    @NotNull
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;

    @NotNull
    @Size(max = 255)
    @Column(name = "remarks", length = 255, nullable = false)
    private String remarks;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("enquiryDetails")
    private Enquiry enquiry;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("enquiryDetails")
    private Category print;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("enquiryDetails")
    private Category finishing;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("enquiryDetails")
    private Category handling;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("enquiryDetails")
    private Category packaging;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("enquiryDetails")
    private UserProfile createdBy;

    @ManyToOne
    @JsonIgnoreProperties("enquiryDetails")
    private Offer offer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public EnquiryDetails version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Double getEdition() {
        return edition;
    }

    public EnquiryDetails edition(Double edition) {
        this.edition = edition;
        return this;
    }

    public void setEdition(Double edition) {
        this.edition = edition;
    }

    public Integer getFormat() {
        return format;
    }

    public EnquiryDetails format(Integer format) {
        this.format = format;
        return this;
    }

    public void setFormat(Integer format) {
        this.format = format;
    }

    public String getDocuments() {
        return documents;
    }

    public EnquiryDetails documents(String documents) {
        this.documents = documents;
        return this;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public EnquiryDetails deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public EnquiryDetails remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public EnquiryDetails createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public EnquiryDetails updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public EnquiryDetails enquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
        return this;
    }

    public void setEnquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
    }

    public Category getPrint() {
        return print;
    }

    public EnquiryDetails print(Category category) {
        this.print = category;
        return this;
    }

    public void setPrint(Category category) {
        this.print = category;
    }

    public Category getFinishing() {
        return finishing;
    }

    public EnquiryDetails finishing(Category category) {
        this.finishing = category;
        return this;
    }

    public void setFinishing(Category category) {
        this.finishing = category;
    }

    public Category getHandling() {
        return handling;
    }

    public EnquiryDetails handling(Category category) {
        this.handling = category;
        return this;
    }

    public void setHandling(Category category) {
        this.handling = category;
    }

    public Category getPackaging() {
        return packaging;
    }

    public EnquiryDetails packaging(Category category) {
        this.packaging = category;
        return this;
    }

    public void setPackaging(Category category) {
        this.packaging = category;
    }

    public UserProfile getCreatedBy() {
        return createdBy;
    }

    public EnquiryDetails createdBy(UserProfile userProfile) {
        this.createdBy = userProfile;
        return this;
    }

    public void setCreatedBy(UserProfile userProfile) {
        this.createdBy = userProfile;
    }

    public Offer getOffer() {
        return offer;
    }

    public EnquiryDetails offer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnquiryDetails)) {
            return false;
        }
        return id != null && id.equals(((EnquiryDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EnquiryDetails{" +
            "id=" + getId() +
            ", version=" + getVersion() +
            ", edition=" + getEdition() +
            ", format=" + getFormat() +
            ", documents='" + getDocuments() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
