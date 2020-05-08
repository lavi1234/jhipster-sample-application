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
 * Criteria class for the {@link com.mycompany.myapp.domain.TransactionHistory} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.TransactionHistoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transaction-histories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TransactionHistoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter price;

    private InstantFilter createdAt;

    private StringFilter paymentGatewayToken;

    private StringFilter paymentGatewayResponse;

    private StringFilter status;

    private LongFilter subscriptionPlanId;

    private LongFilter userProfileId;

    public TransactionHistoryCriteria() {
    }

    public TransactionHistoryCriteria(TransactionHistoryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.paymentGatewayToken = other.paymentGatewayToken == null ? null : other.paymentGatewayToken.copy();
        this.paymentGatewayResponse = other.paymentGatewayResponse == null ? null : other.paymentGatewayResponse.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.subscriptionPlanId = other.subscriptionPlanId == null ? null : other.subscriptionPlanId.copy();
        this.userProfileId = other.userProfileId == null ? null : other.userProfileId.copy();
    }

    @Override
    public TransactionHistoryCriteria copy() {
        return new TransactionHistoryCriteria(this);
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

    public StringFilter getPaymentGatewayToken() {
        return paymentGatewayToken;
    }

    public void setPaymentGatewayToken(StringFilter paymentGatewayToken) {
        this.paymentGatewayToken = paymentGatewayToken;
    }

    public StringFilter getPaymentGatewayResponse() {
        return paymentGatewayResponse;
    }

    public void setPaymentGatewayResponse(StringFilter paymentGatewayResponse) {
        this.paymentGatewayResponse = paymentGatewayResponse;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
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
        final TransactionHistoryCriteria that = (TransactionHistoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(price, that.price) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(paymentGatewayToken, that.paymentGatewayToken) &&
            Objects.equals(paymentGatewayResponse, that.paymentGatewayResponse) &&
            Objects.equals(status, that.status) &&
            Objects.equals(subscriptionPlanId, that.subscriptionPlanId) &&
            Objects.equals(userProfileId, that.userProfileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        price,
        createdAt,
        paymentGatewayToken,
        paymentGatewayResponse,
        status,
        subscriptionPlanId,
        userProfileId
        );
    }

    @Override
    public String toString() {
        return "TransactionHistoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (paymentGatewayToken != null ? "paymentGatewayToken=" + paymentGatewayToken + ", " : "") +
                (paymentGatewayResponse != null ? "paymentGatewayResponse=" + paymentGatewayResponse + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (subscriptionPlanId != null ? "subscriptionPlanId=" + subscriptionPlanId + ", " : "") +
                (userProfileId != null ? "userProfileId=" + userProfileId + ", " : "") +
            "}";
    }

}
