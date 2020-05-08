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
 * Criteria class for the {@link com.mycompany.myapp.domain.EnquiryDetails} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EnquiryDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enquiry-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnquiryDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter version;

    private DoubleFilter edition;

    private IntegerFilter format;

    private StringFilter documents;

    private LocalDateFilter deliveryDate;

    private StringFilter remarks;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter enquiryId;

    private LongFilter printId;

    private LongFilter finishingId;

    private LongFilter handlingId;

    private LongFilter packagingId;

    private LongFilter createdById;

    private LongFilter offerId;

    public EnquiryDetailsCriteria() {
    }

    public EnquiryDetailsCriteria(EnquiryDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.version = other.version == null ? null : other.version.copy();
        this.edition = other.edition == null ? null : other.edition.copy();
        this.format = other.format == null ? null : other.format.copy();
        this.documents = other.documents == null ? null : other.documents.copy();
        this.deliveryDate = other.deliveryDate == null ? null : other.deliveryDate.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.enquiryId = other.enquiryId == null ? null : other.enquiryId.copy();
        this.printId = other.printId == null ? null : other.printId.copy();
        this.finishingId = other.finishingId == null ? null : other.finishingId.copy();
        this.handlingId = other.handlingId == null ? null : other.handlingId.copy();
        this.packagingId = other.packagingId == null ? null : other.packagingId.copy();
        this.createdById = other.createdById == null ? null : other.createdById.copy();
        this.offerId = other.offerId == null ? null : other.offerId.copy();
    }

    @Override
    public EnquiryDetailsCriteria copy() {
        return new EnquiryDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getVersion() {
        return version;
    }

    public void setVersion(IntegerFilter version) {
        this.version = version;
    }

    public DoubleFilter getEdition() {
        return edition;
    }

    public void setEdition(DoubleFilter edition) {
        this.edition = edition;
    }

    public IntegerFilter getFormat() {
        return format;
    }

    public void setFormat(IntegerFilter format) {
        this.format = format;
    }

    public StringFilter getDocuments() {
        return documents;
    }

    public void setDocuments(StringFilter documents) {
        this.documents = documents;
    }

    public LocalDateFilter getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
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

    public LongFilter getPrintId() {
        return printId;
    }

    public void setPrintId(LongFilter printId) {
        this.printId = printId;
    }

    public LongFilter getFinishingId() {
        return finishingId;
    }

    public void setFinishingId(LongFilter finishingId) {
        this.finishingId = finishingId;
    }

    public LongFilter getHandlingId() {
        return handlingId;
    }

    public void setHandlingId(LongFilter handlingId) {
        this.handlingId = handlingId;
    }

    public LongFilter getPackagingId() {
        return packagingId;
    }

    public void setPackagingId(LongFilter packagingId) {
        this.packagingId = packagingId;
    }

    public LongFilter getCreatedById() {
        return createdById;
    }

    public void setCreatedById(LongFilter createdById) {
        this.createdById = createdById;
    }

    public LongFilter getOfferId() {
        return offerId;
    }

    public void setOfferId(LongFilter offerId) {
        this.offerId = offerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnquiryDetailsCriteria that = (EnquiryDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(version, that.version) &&
            Objects.equals(edition, that.edition) &&
            Objects.equals(format, that.format) &&
            Objects.equals(documents, that.documents) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(enquiryId, that.enquiryId) &&
            Objects.equals(printId, that.printId) &&
            Objects.equals(finishingId, that.finishingId) &&
            Objects.equals(handlingId, that.handlingId) &&
            Objects.equals(packagingId, that.packagingId) &&
            Objects.equals(createdById, that.createdById) &&
            Objects.equals(offerId, that.offerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        version,
        edition,
        format,
        documents,
        deliveryDate,
        remarks,
        createdAt,
        updatedAt,
        enquiryId,
        printId,
        finishingId,
        handlingId,
        packagingId,
        createdById,
        offerId
        );
    }

    @Override
    public String toString() {
        return "EnquiryDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (version != null ? "version=" + version + ", " : "") +
                (edition != null ? "edition=" + edition + ", " : "") +
                (format != null ? "format=" + format + ", " : "") +
                (documents != null ? "documents=" + documents + ", " : "") +
                (deliveryDate != null ? "deliveryDate=" + deliveryDate + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (enquiryId != null ? "enquiryId=" + enquiryId + ", " : "") +
                (printId != null ? "printId=" + printId + ", " : "") +
                (finishingId != null ? "finishingId=" + finishingId + ", " : "") +
                (handlingId != null ? "handlingId=" + handlingId + ", " : "") +
                (packagingId != null ? "packagingId=" + packagingId + ", " : "") +
                (createdById != null ? "createdById=" + createdById + ", " : "") +
                (offerId != null ? "offerId=" + offerId + ", " : "") +
            "}";
    }

}
