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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Enquiry} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EnquiryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /enquiries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EnquiryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private StringFilter deliveryTerms;

    private LocalDateFilter offerTaxtUntil;

    private StringFilter status;

    private LongFilter productId;

    private LongFilter deliveryAddressId;

    public EnquiryCriteria() {
    }

    public EnquiryCriteria(EnquiryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.deliveryTerms = other.deliveryTerms == null ? null : other.deliveryTerms.copy();
        this.offerTaxtUntil = other.offerTaxtUntil == null ? null : other.offerTaxtUntil.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.productId = other.productId == null ? null : other.productId.copy();
        this.deliveryAddressId = other.deliveryAddressId == null ? null : other.deliveryAddressId.copy();
    }

    @Override
    public EnquiryCriteria copy() {
        return new EnquiryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getDeliveryTerms() {
        return deliveryTerms;
    }

    public void setDeliveryTerms(StringFilter deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
    }

    public LocalDateFilter getOfferTaxtUntil() {
        return offerTaxtUntil;
    }

    public void setOfferTaxtUntil(LocalDateFilter offerTaxtUntil) {
        this.offerTaxtUntil = offerTaxtUntil;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getProductId() {
        return productId;
    }

    public void setProductId(LongFilter productId) {
        this.productId = productId;
    }

    public LongFilter getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(LongFilter deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EnquiryCriteria that = (EnquiryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(deliveryTerms, that.deliveryTerms) &&
            Objects.equals(offerTaxtUntil, that.offerTaxtUntil) &&
            Objects.equals(status, that.status) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(deliveryAddressId, that.deliveryAddressId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        deliveryTerms,
        offerTaxtUntil,
        status,
        productId,
        deliveryAddressId
        );
    }

    @Override
    public String toString() {
        return "EnquiryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (deliveryTerms != null ? "deliveryTerms=" + deliveryTerms + ", " : "") +
                (offerTaxtUntil != null ? "offerTaxtUntil=" + offerTaxtUntil + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (productId != null ? "productId=" + productId + ", " : "") +
                (deliveryAddressId != null ? "deliveryAddressId=" + deliveryAddressId + ", " : "") +
            "}";
    }

}
