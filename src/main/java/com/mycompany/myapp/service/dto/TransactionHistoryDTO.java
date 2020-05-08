package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.TransactionHistory} entity.
 */
public class TransactionHistoryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double price;

    @NotNull
    private Instant createdAt;

    @NotNull
    @Size(max = 255)
    private String paymentGatewayToken;

    @NotNull
    @Size(max = 255)
    private String paymentGatewayResponse;

    @NotNull
    @Size(max = 200)
    private String status;


    private Long subscriptionPlanId;

    private Long userProfileId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getPaymentGatewayToken() {
        return paymentGatewayToken;
    }

    public void setPaymentGatewayToken(String paymentGatewayToken) {
        this.paymentGatewayToken = paymentGatewayToken;
    }

    public String getPaymentGatewayResponse() {
        return paymentGatewayResponse;
    }

    public void setPaymentGatewayResponse(String paymentGatewayResponse) {
        this.paymentGatewayResponse = paymentGatewayResponse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getSubscriptionPlanId() {
        return subscriptionPlanId;
    }

    public void setSubscriptionPlanId(Long subscriptionPlanId) {
        this.subscriptionPlanId = subscriptionPlanId;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
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

        TransactionHistoryDTO transactionHistoryDTO = (TransactionHistoryDTO) o;
        if (transactionHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionHistoryDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", paymentGatewayToken='" + getPaymentGatewayToken() + "'" +
            ", paymentGatewayResponse='" + getPaymentGatewayResponse() + "'" +
            ", status='" + getStatus() + "'" +
            ", subscriptionPlanId=" + getSubscriptionPlanId() +
            ", userProfileId=" + getUserProfileId() +
            "}";
    }
}
