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
 * Criteria class for the {@link com.mycompany.myapp.domain.SupplierEnquiryMapping} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.SupplierEnquiryMappingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /supplier-enquiry-mappings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SupplierEnquiryMappingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter enquiryId;

    private LongFilter supplierId;

    private LongFilter createdById;

    public SupplierEnquiryMappingCriteria() {
    }

    public SupplierEnquiryMappingCriteria(SupplierEnquiryMappingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.enquiryId = other.enquiryId == null ? null : other.enquiryId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.createdById = other.createdById == null ? null : other.createdById.copy();
    }

    @Override
    public SupplierEnquiryMappingCriteria copy() {
        return new SupplierEnquiryMappingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public LongFilter getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(LongFilter enquiryId) {
        this.enquiryId = enquiryId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
    }

    public LongFilter getCreatedById() {
        return createdById;
    }

    public void setCreatedById(LongFilter createdById) {
        this.createdById = createdById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SupplierEnquiryMappingCriteria that = (SupplierEnquiryMappingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(enquiryId, that.enquiryId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(createdById, that.createdById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        createdAt,
        updatedAt,
        enquiryId,
        supplierId,
        createdById
        );
    }

    @Override
    public String toString() {
        return "SupplierEnquiryMappingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (enquiryId != null ? "enquiryId=" + enquiryId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (createdById != null ? "createdById=" + createdById + ", " : "") +
            "}";
    }

}
