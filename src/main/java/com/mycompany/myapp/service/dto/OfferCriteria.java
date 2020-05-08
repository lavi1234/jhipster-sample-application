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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Offer} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.OfferResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /offers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OfferCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter validUpto;

    private StringFilter status;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter supplierEnquiryId;

    private LongFilter createdById;

    private LongFilter updatedById;

    public OfferCriteria() {
    }

    public OfferCriteria(OfferCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.validUpto = other.validUpto == null ? null : other.validUpto.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.supplierEnquiryId = other.supplierEnquiryId == null ? null : other.supplierEnquiryId.copy();
        this.createdById = other.createdById == null ? null : other.createdById.copy();
        this.updatedById = other.updatedById == null ? null : other.updatedById.copy();
    }

    @Override
    public OfferCriteria copy() {
        return new OfferCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(LocalDateFilter validUpto) {
        this.validUpto = validUpto;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
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

    public LongFilter getSupplierEnquiryId() {
        return supplierEnquiryId;
    }

    public void setSupplierEnquiryId(LongFilter supplierEnquiryId) {
        this.supplierEnquiryId = supplierEnquiryId;
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
        final OfferCriteria that = (OfferCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(validUpto, that.validUpto) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(supplierEnquiryId, that.supplierEnquiryId) &&
            Objects.equals(createdById, that.createdById) &&
            Objects.equals(updatedById, that.updatedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        validUpto,
        status,
        createdAt,
        updatedAt,
        supplierEnquiryId,
        createdById,
        updatedById
        );
    }

    @Override
    public String toString() {
        return "OfferCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (validUpto != null ? "validUpto=" + validUpto + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (supplierEnquiryId != null ? "supplierEnquiryId=" + supplierEnquiryId + ", " : "") +
                (createdById != null ? "createdById=" + createdById + ", " : "") +
                (updatedById != null ? "updatedById=" + updatedById + ", " : "") +
            "}";
    }

}
