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
 * Criteria class for the {@link com.mycompany.myapp.domain.UserSubscription} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.UserSubscriptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-subscriptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserSubscriptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter price;

    private LocalDateFilter validUpto;

    private StringFilter paymentGatewayToken;

    private LocalDateFilter nextRenewal;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter subscriptionPlanId;

    private LongFilter userProfileId;

    public UserSubscriptionCriteria() {
    }

    public UserSubscriptionCriteria(UserSubscriptionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.validUpto = other.validUpto == null ? null : other.validUpto.copy();
        this.paymentGatewayToken = other.paymentGatewayToken == null ? null : other.paymentGatewayToken.copy();
        this.nextRenewal = other.nextRenewal == null ? null : other.nextRenewal.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.subscriptionPlanId = other.subscriptionPlanId == null ? null : other.subscriptionPlanId.copy();
        this.userProfileId = other.userProfileId == null ? null : other.userProfileId.copy();
    }

    @Override
    public UserSubscriptionCriteria copy() {
        return new UserSubscriptionCriteria(this);
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

    public LocalDateFilter getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(LocalDateFilter validUpto) {
        this.validUpto = validUpto;
    }

    public StringFilter getPaymentGatewayToken() {
        return paymentGatewayToken;
    }

    public void setPaymentGatewayToken(StringFilter paymentGatewayToken) {
        this.paymentGatewayToken = paymentGatewayToken;
    }

    public LocalDateFilter getNextRenewal() {
        return nextRenewal;
    }

    public void setNextRenewal(LocalDateFilter nextRenewal) {
        this.nextRenewal = nextRenewal;
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

    public LongFilter getSubscriptionPlanId() {
        return subscriptionPlanId;
    }

    public void setSubscriptionPlanId(LongFilter subscriptionPlanId) {
        this.subscriptionPlanId = subscriptionPlanId;
    }

    public LongFilter getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(LongFilter userProfileId) {
        this.userProfileId = userProfileId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserSubscriptionCriteria that = (UserSubscriptionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(price, that.price) &&
            Objects.equals(validUpto, that.validUpto) &&
            Objects.equals(paymentGatewayToken, that.paymentGatewayToken) &&
            Objects.equals(nextRenewal, that.nextRenewal) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(subscriptionPlanId, that.subscriptionPlanId) &&
            Objects.equals(userProfileId, that.userProfileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        price,
        validUpto,
        paymentGatewayToken,
        nextRenewal,
        createdAt,
        updatedAt,
        subscriptionPlanId,
        userProfileId
        );
    }

    @Override
    public String toString() {
        return "UserSubscriptionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (validUpto != null ? "validUpto=" + validUpto + ", " : "") +
                (paymentGatewayToken != null ? "paymentGatewayToken=" + paymentGatewayToken + ", " : "") +
                (nextRenewal != null ? "nextRenewal=" + nextRenewal + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (subscriptionPlanId != null ? "subscriptionPlanId=" + subscriptionPlanId + ", " : "") +
                (userProfileId != null ? "userProfileId=" + userProfileId + ", " : "") +
            "}";
    }

}
