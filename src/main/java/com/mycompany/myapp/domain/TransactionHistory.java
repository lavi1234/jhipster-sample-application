package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A TransactionHistory.
 */
@Entity
@Table(name = "transaction_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Size(max = 255)
    @Column(name = "payment_gateway_token", length = 255, nullable = false)
    private String paymentGatewayToken;

    @NotNull
    @Size(max = 255)
    @Column(name = "payment_gateway_response", length = 255, nullable = false)
    private String paymentGatewayResponse;

    @NotNull
    @Size(max = 200)
    @Column(name = "status", length = 200, nullable = false)
    private String status;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("transactionHistories")
    private SubscriptionPlan subscriptionPlan;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("transactionHistories")
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

    public TransactionHistory price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public TransactionHistory createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentGatewayToken() {
        return paymentGatewayToken;
    }

    public TransactionHistory paymentGatewayToken(String paymentGatewayToken) {
        this.paymentGatewayToken = paymentGatewayToken;
        return this;
    }

    public void setPaymentGatewayToken(String paymentGatewayToken) {
        this.paymentGatewayToken = paymentGatewayToken;
    }

    public String getPaymentGatewayResponse() {
        return paymentGatewayResponse;
    }

    public TransactionHistory paymentGatewayResponse(String paymentGatewayResponse) {
        this.paymentGatewayResponse = paymentGatewayResponse;
        return this;
    }

    public void setPaymentGatewayResponse(String paymentGatewayResponse) {
        this.paymentGatewayResponse = paymentGatewayResponse;
    }

    public String getStatus() {
        return status;
    }

    public TransactionHistory status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public TransactionHistory subscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
        return this;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public TransactionHistory userProfile(UserProfile userProfile) {
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
        if (!(o instanceof TransactionHistory)) {
            return false;
        }
        return id != null && id.equals(((TransactionHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", paymentGatewayToken='" + getPaymentGatewayToken() + "'" +
            ", paymentGatewayResponse='" + getPaymentGatewayResponse() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
