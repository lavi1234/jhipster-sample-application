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

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.SubsriptionPlanFeature} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.SubsriptionPlanFeatureResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /subsription-plan-features?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SubsriptionPlanFeatureCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter subscriptionPlanId;

    private LongFilter appFeatureId;

    public SubsriptionPlanFeatureCriteria() {
    }

    public SubsriptionPlanFeatureCriteria(SubsriptionPlanFeatureCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.subscriptionPlanId = other.subscriptionPlanId == null ? null : other.subscriptionPlanId.copy();
        this.appFeatureId = other.appFeatureId == null ? null : other.appFeatureId.copy();
    }

    @Override
    public SubsriptionPlanFeatureCriteria copy() {
        return new SubsriptionPlanFeatureCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getSubscriptionPlanId() {
        return subscriptionPlanId;
    }

    public void setSubscriptionPlanId(LongFilter subscriptionPlanId) {
        this.subscriptionPlanId = subscriptionPlanId;
    }

    public LongFilter getAppFeatureId() {
        return appFeatureId;
    }

    public void setAppFeatureId(LongFilter appFeatureId) {
        this.appFeatureId = appFeatureId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SubsriptionPlanFeatureCriteria that = (SubsriptionPlanFeatureCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(subscriptionPlanId, that.subscriptionPlanId) &&
            Objects.equals(appFeatureId, that.appFeatureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        subscriptionPlanId,
        appFeatureId
        );
    }

    @Override
    public String toString() {
        return "SubsriptionPlanFeatureCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (subscriptionPlanId != null ? "subscriptionPlanId=" + subscriptionPlanId + ", " : "") +
                (appFeatureId != null ? "appFeatureId=" + appFeatureId + ", " : "") +
            "}";
    }

}
