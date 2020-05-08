package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A UserSubscription.
 */
@Entity
@Table(name = "user_subscription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "valid_upto", nullable = false)
    private LocalDate validUpto;

    @NotNull
    @Size(max = 255)
    @Column(name = "payment_gateway_token", length = 255, nullable = false)
    private String paymentGatewayToken;

    @NotNull
    @Column(name = "next_renewal", nullable = false)
    private LocalDate nextRenewal;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("userSubscriptions")
    private SubscriptionPlan subscriptionPlan;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("userSubscriptions")
    private UserProfile userProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public UserSubscription price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getValidUpto() {
        return validUpto;
    }

    public UserSubscription validUpto(LocalDate validUpto) {
        this.validUpto = validUpto;
        return this;
    }

    public void setValidUpto(LocalDate validUpto) {
        this.validUpto = validUpto;
    }

    public String getPaymentGatewayToken() {
        return paymentGatewayToken;
    }

    public UserSubscription paymentGatewayToken(String paymentGatewayToken) {
        this.paymentGatewayToken = paymentGatewayToken;
        return this;
    }

    public void setPaymentGatewayToken(String paymentGatewayToken) {
        this.paymentGatewayToken = paymentGatewayToken;
    }

    public LocalDate getNextRenewal() {
        return nextRenewal;
    }

    public UserSubscription nextRenewal(LocalDate nextRenewal) {
        this.nextRenewal = nextRenewal;
        return this;
    }

    public void setNextRenewal(LocalDate nextRenewal) {
        this.nextRenewal = nextRenewal;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserSubscription createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public UserSubscription updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public UserSubscription subscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
        return this;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public UserSubscription userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSubscription)) {
            return false;
        }
        return id != null && id.equals(((UserSubscription) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserSubscription{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", validUpto='" + getValidUpto() + "'" +
            ", paymentGatewayToken='" + getPaymentGatewayToken() + "'" +
            ", nextRenewal='" + getNextRenewal() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
