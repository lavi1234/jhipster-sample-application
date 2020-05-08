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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.CommissionReference} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CommissionReferenceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /commission-references?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CommissionReferenceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BigDecimalFilter percentage;

    private IntegerFilter holdDays;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    public CommissionReferenceCriteria() {
    }

    public CommissionReferenceCriteria(CommissionReferenceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.percentage = other.percentage == null ? null : other.percentage.copy();
        this.holdDays = other.holdDays == null ? null : other.holdDays.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
    }

    @Override
    public CommissionReferenceCriteria copy() {
        return new CommissionReferenceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BigDecimalFilter getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimalFilter percentage) {
        this.percentage = percentage;
    }

    public IntegerFilter getHoldDays() {
        return holdDays;
    }

    public void setHoldDays(IntegerFilter holdDays) {
        this.holdDays = holdDays;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CommissionReferenceCriteria that = (CommissionReferenceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(percentage, that.percentage) &&
            Objects.equals(holdDays, that.holdDays) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        percentage,
        holdDays,
        createdAt,
        updatedAt
        );
    }

    @Override
    public String toString() {
        return "CommissionReferenceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (percentage != null ? "percentage=" + percentage + ", " : "") +
                (holdDays != null ? "holdDays=" + holdDays + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
            "}";
    }

}
