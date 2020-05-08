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
 * Criteria class for the {@link com.mycompany.myapp.domain.Order} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.OrderResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /orders?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OrderCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter price;

    private LocalDateFilter deliveryDate;

    private StringFilter status;

    private LocalDateFilter commissionDate;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private StringFilter remarks;

    private LongFilter offerId;

    private LongFilter buyerId;

    private LongFilter supplierId;

    private LongFilter enquiryId;

    private LongFilter enquiryDetailsId;

    public OrderCriteria() {
    }

    public OrderCriteria(OrderCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.deliveryDate = other.deliveryDate == null ? null : other.deliveryDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.commissionDate = other.commissionDate == null ? null : other.commissionDate.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.remarks = other.remarks == null ? null : other.remarks.copy();
        this.offerId = other.offerId == null ? null : other.offerId.copy();
        this.buyerId = other.buyerId == null ? null : other.buyerId.copy();
        this.supplierId = other.supplierId == null ? null : other.supplierId.copy();
        this.enquiryId = other.enquiryId == null ? null : other.enquiryId.copy();
        this.enquiryDetailsId = other.enquiryDetailsId == null ? null : other.enquiryDetailsId.copy();
    }

    @Override
    public OrderCriteria copy() {
        return new OrderCriteria(this);
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

    public LocalDateFilter getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LocalDateFilter getCommissionDate() {
        return commissionDate;
    }

    public void setCommissionDate(LocalDateFilter commissionDate) {
        this.commissionDate = commissionDate;
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

    public StringFilter getRemarks() {
        return remarks;
    }

    public void setRemarks(StringFilter remarks) {
        this.remarks = remarks;
    }

    public LongFilter getOfferId() {
        return offerId;
    }

    public void setOfferId(LongFilter offerId) {
        this.offerId = offerId;
    }

    public LongFilter getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(LongFilter buyerId) {
        this.buyerId = buyerId;
    }

    public LongFilter getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(LongFilter supplierId) {
        this.supplierId = supplierId;
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
        final OrderCriteria that = (OrderCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(price, that.price) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(commissionDate, that.commissionDate) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(remarks, that.remarks) &&
            Objects.equals(offerId, that.offerId) &&
            Objects.equals(buyerId, that.buyerId) &&
            Objects.equals(supplierId, that.supplierId) &&
            Objects.equals(enquiryId, that.enquiryId) &&
            Objects.equals(enquiryDetailsId, that.enquiryDetailsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        price,
        deliveryDate,
        status,
        commissionDate,
        createdAt,
        updatedAt,
        remarks,
        offerId,
        buyerId,
        supplierId,
        enquiryId,
        enquiryDetailsId
        );
    }

    @Override
    public String toString() {
        return "OrderCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (deliveryDate != null ? "deliveryDate=" + deliveryDate + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (commissionDate != null ? "commissionDate=" + commissionDate + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (remarks != null ? "remarks=" + remarks + ", " : "") +
                (offerId != null ? "offerId=" + offerId + ", " : "") +
                (buyerId != null ? "buyerId=" + buyerId + ", " : "") +
                (supplierId != null ? "supplierId=" + supplierId + ", " : "") +
                (enquiryId != null ? "enquiryId=" + enquiryId + ", " : "") +
                (enquiryDetailsId != null ? "enquiryDetailsId=" + enquiryDetailsId + ", " : "") +
            "}";
    }

}
