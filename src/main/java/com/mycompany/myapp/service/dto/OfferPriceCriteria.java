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
 * Criteria class for the {@link com.mycompany.myapp.domain.OfferPrice} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.OfferPriceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /offer-prices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OfferPriceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter price;

    private InstantFilter createdAt;

    private LocalDateFilter finishingDate;

    private LongFilter offerId;

    private LongFilter enquiryId;

    private LongFilter enquiryDetailsId;

    public OfferPriceCriteria() {
    }

    public OfferPriceCriteria(OfferPriceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.finishingDate = other.finishingDate == null ? null : other.finishingDate.copy();
        this.offerId = other.offerId == null ? null : other.offerId.copy();
        this.enquiryId = other.enquiryId == null ? null : other.enquiryId.copy();
        this.enquiryDetailsId = other.enquiryDetailsId == null ? null : other.enquiryDetailsId.copy();
    }

    @Override
    public OfferPriceCriteria copy() {
        return new OfferPriceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateFilter getFinishingDate() {
        return finishingDate;
    }

    public void setFinishingDate(LocalDateFilter finishingDate) {
        this.finishingDate = finishingDate;
    }

    public LongFilter getOfferId() {
        return offerId;
    }

    public void setOfferId(LongFilter offerId) {
        this.offerId = offerId;
    }

    public LongFilter getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(LongFilter enquiryId) {
        this.enquiryId = enquiryId;
    }

    public LongFilter getEnquiryDetailsId() {
        return enquiryDetailsId;
    }

    public void setEnquiryDetailsId(LongFilter enquiryDetailsId) {
        this.enquiryDetailsId = enquiryDetailsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OfferPriceCriteria that = (OfferPriceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(price, that.price) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(finishingDate, that.finishingDate) &&
            Objects.equals(offerId, that.offerId) &&
            Objects.equals(enquiryId, that.enquiryId) &&
            Objects.equals(enquiryDetailsId, that.enquiryDetailsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        price,
        createdAt,
        finishingDate,
        offerId,
        enquiryId,
        enquiryDetailsId
        );
    }

    @Override
    public String toString() {
        return "OfferPriceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (finishingDate != null ? "finishingDate=" + finishingDate + ", " : "") +
                (offerId != null ? "offerId=" + offerId + ", " : "") +
                (enquiryId != null ? "enquiryId=" + enquiryId + ", " : "") +
                (enquiryDetailsId != null ? "enquiryDetailsId=" + enquiryDetailsId + ", " : "") +
            "}";
    }

}
