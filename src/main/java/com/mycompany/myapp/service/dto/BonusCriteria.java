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
 * Criteria class for the {@link com.mycompany.myapp.domain.Bonus} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.BonusResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bonuses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BonusCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter amount;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private DoubleFilter principalAmount;

    private StringFilter status;

    private StringFilter remarks;

    private LongFilter buyerId;

    private LongFilter orderId;

    public BonusCriteria() {
    }

    public BonusCriteria(BonusCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.principalAmount = other.principalAmount == null ? null : other.principalAmount.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.buyerId = other.buyerId == null ? null : other.buyerId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
    }

    @Override
    public BonusCriteria copy() {
        return new BonusCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
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

    public DoubleFilter getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(DoubleFilter principalAmount) {
        this.principalAmount = principalAmount;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public LongFilter getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(LongFilter buyerId) {
        this.buyerId = buyerId;
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
        final BonusCriteria that = (BonusCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(principalAmount, that.principalAmount) &&
            Objects.equals(status, that.status) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(buyerId, that.buyerId) &&
            Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amount,
        createdAt,
        updatedAt,
        principalAmount,
        status,
        remarks,
        buyerId,
        orderId
        );
    }

    @Override
    public String toString() {
        return "BonusCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (principalAmount != null ? "principalAmount=" + principalAmount + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (buyerId != null ? "buyerId=" + buyerId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
            "}";
    }

}
