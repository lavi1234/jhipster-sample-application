package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Company} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter email;

    private StringFilter termsConditions;

    private StringFilter aboutUs;

    private StringFilter catalogue;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter addressId;

    private LongFilter createdById;

    private LongFilter updatedById;

    public CompanyCriteria() {
    }

    public CompanyCriteria(CompanyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.termsConditions = other.termsConditions == null ? null : other.termsConditions.copy();
        this.aboutUs = other.aboutUs == null ? null : other.aboutUs.copy();
        this.catalogue = other.catalogue == null ? null : other.catalogue.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.createdById = other.createdById == null ? null : other.createdById.copy();
        this.updatedById = other.updatedById == null ? null : other.updatedById.copy();
    }

    @Override
    public CompanyCriteria copy() {
        return new CompanyCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getTermsConditions() {
        return termsConditions;
    }

    public void setTermsConditions(StringFilter termsConditions) {
        this.termsConditions = termsConditions;
    }

    public StringFilter getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(StringFilter aboutUs) {
        this.aboutUs = aboutUs;
    }

    public StringFilter getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(StringFilter catalogue) {
        this.catalogue = catalogue;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
    }

    public LongFilter getCreatedById() {
        return createdById;
    }

    public void setCreatedById(LongFilter createdById) {
        this.createdById = createdById;
    }

    public LongFilter getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(LongFilter updatedById) {
        this.updatedById = updatedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CompanyCriteria that = (CompanyCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(email, that.email) &&
            Objects.equals(termsConditions, that.termsConditions) &&
            Objects.equals(aboutUs, that.aboutUs) &&
            Objects.equals(catalogue, that.catalogue) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(createdById, that.createdById) &&
            Objects.equals(updatedById, that.updatedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        email,
        termsConditions,
        aboutUs,
        catalogue,
        createdAt,
        updatedAt,
        addressId,
        createdById,
        updatedById
        );
    }

    @Override
    public String toString() {
        return "CompanyCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (termsConditions != null ? "termsConditions=" + termsConditions + ", " : "") +
                (aboutUs != null ? "aboutUs=" + aboutUs + ", " : "") +
                (catalogue != null ? "catalogue=" + catalogue + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (addressId != null ? "addressId=" + addressId + ", " : "") +
                (createdById != null ? "createdById=" + createdById + ", " : "") +
                (updatedById != null ? "updatedById=" + updatedById + ", " : "") +
            "}";
    }

}
