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
 * Criteria class for the {@link com.mycompany.myapp.domain.SubscriptionPlan} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.SubscriptionPlanResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /subscription-plans?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SubscriptionPlanCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter validity;

    private DoubleFilter price;

    private IntegerFilter createdBy;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter nameId;

    private LongFilter descriptionId;

    private LongFilter subsriptionPlanFeatureId;

    public SubscriptionPlanCriteria() {
    }

    public SubscriptionPlanCriteria(SubscriptionPlanCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.validity = other.validity == null ? null : other.validity.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.nameId = other.nameId == null ? null : other.nameId.copy();
        this.descriptionId = other.descriptionId == null ? null : other.descriptionId.copy();
        this.subsriptionPlanFeatureId = other.subsriptionPlanFeatureId == null ? null : other.subsriptionPlanFeatureId.copy();
    }

    @Override
    public SubscriptionPlanCriteria copy() {
        return new SubscriptionPlanCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getValidity() {
        return validity;
    }

    public void setValidity(StringFilter validity) {
        this.validity = validity;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public IntegerFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(IntegerFilter createdBy) {
        this.createdBy = createdBy;
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

    public LongFilter getNameId() {
        return nameId;
    }

    public void setNameId(LongFilter nameId) {
        this.nameId = nameId;
    }

    public LongFilter getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(LongFilter descriptionId) {
        this.descriptionId = descriptionId;
    }

    public LongFilter getSubsriptionPlanFeatureId() {
        return subsriptionPlanFeatureId;
    }

    public void setSubsriptionPlanFeatureId(LongFilter subsriptionPlanFeatureId) {
        this.subsriptionPlanFeatureId = subsriptionPlanFeatureId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SubscriptionPlanCriteria that = (SubscriptionPlanCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(validity, that.validity) &&
            Objects.equals(price, that.price) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(nameId, that.nameId) &&
            Objects.equals(descriptionId, that.descriptionId) &&
            Objects.equals(subsriptionPlanFeatureId, that.subsriptionPlanFeatureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        validity,
        price,
        createdBy,
        createdAt,
        updatedAt,
        nameId,
        descriptionId,
        subsriptionPlanFeatureId
        );
    }

    @Override
    public String toString() {
        return "SubscriptionPlanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (validity != null ? "validity=" + validity + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (nameId != null ? "nameId=" + nameId + ", " : "") +
                (descriptionId != null ? "descriptionId=" + descriptionId + ", " : "") +
                (subsriptionPlanFeatureId != null ? "subsriptionPlanFeatureId=" + subsriptionPlanFeatureId + ", " : "") +
            "}";
    }

}
