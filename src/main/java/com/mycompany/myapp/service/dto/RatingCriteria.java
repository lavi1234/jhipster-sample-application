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
 * Criteria class for the {@link com.mycompany.myapp.domain.Rating} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.RatingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ratings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RatingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter rating;

    private InstantFilter createdAt;

    private StringFilter remarks;

    private LongFilter fromProfileId;

    private LongFilter toProfileId;

    private LongFilter orderId;

    public RatingCriteria() {
    }

    public RatingCriteria(RatingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rating = other.rating == null ? null : other.rating.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.fromProfileId = other.fromProfileId == null ? null : other.fromProfileId.copy();
        this.toProfileId = other.toProfileId == null ? null : other.toProfileId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
    }

    @Override
    public RatingCriteria copy() {
        return new RatingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getRating() {
        return rating;
    }

    public void setRating(IntegerFilter rating) {
        this.rating = rating;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public LongFilter getFromProfileId() {
        return fromProfileId;
    }

    public void setFromProfileId(LongFilter fromProfileId) {
        this.fromProfileId = fromProfileId;
    }

    public LongFilter getToProfileId() {
        return toProfileId;
    }

    public void setToProfileId(LongFilter toProfileId) {
        this.toProfileId = toProfileId;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RatingCriteria that = (RatingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(fromProfileId, that.fromProfileId) &&
            Objects.equals(toProfileId, that.toProfileId) &&
            Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        rating,
        createdAt,
        remarks,
        fromProfileId,
        toProfileId,
        orderId
        );
    }

    @Override
    public String toString() {
        return "RatingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (rating != null ? "rating=" + rating + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (fromProfileId != null ? "fromProfileId=" + fromProfileId + ", " : "") +
                (toProfileId != null ? "toProfileId=" + toProfileId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
            "}";
    }

}
