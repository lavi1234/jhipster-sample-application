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
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Size(max = 200)
    @Column(name = "email", length = 200)
    private String email;

    @Size(max = 255)
    @Column(name = "terms_conditions", length = 255)
    private String termsConditions;

    @Size(max = 255)
    @Column(name = "about_us", length = 255)
    private String aboutUs;

    /**
     * Url of uploaded catalogue document
     */
    @Size(max = 255)
    @Column(name = "catalogue", length = 255)
    private String catalogue;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne
    @JsonIgnoreProperties("companies")
    private Address address;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("companies")
    private UserProfile createdBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("companies")
    private UserProfile updatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Company name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Company email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTermsConditions() {
        return termsConditions;
    }

    public Company termsConditions(String termsConditions) {
        this.termsConditions = termsConditions;
        return this;
    }

    public void setTermsConditions(String termsConditions) {
        this.termsConditions = termsConditions;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public Company aboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
        return this;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getCatalogue() {
        return catalogue;
    }

    public Company catalogue(String catalogue) {
        this.catalogue = catalogue;
        return this;
    }

    public void setCatalogue(String catalogue) {
        this.catalogue = catalogue;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Company createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Company updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Address getAddress() {
        return address;
    }

    public Company address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserProfile getCreatedBy() {
        return createdBy;
    }

    public Company createdBy(UserProfile userProfile) {
        this.createdBy = userProfile;
        return this;
    }

    public void setCreatedBy(UserProfile userProfile) {
        this.createdBy = userProfile;
    }

    public UserProfile getUpdatedBy() {
        return updatedBy;
    }

    public Company updatedBy(UserProfile userProfile) {
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
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", termsConditions='" + getTermsConditions() + "'" +
            ", aboutUs='" + getAboutUs() + "'" +
            ", catalogue='" + getCatalogue() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
