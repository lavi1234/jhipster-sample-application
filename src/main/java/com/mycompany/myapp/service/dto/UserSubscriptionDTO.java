package com.mycompany.myapp.service.dto;

import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.UserSubscription} entity.
 */
public class UserSubscriptionDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Double price;

    @NotNull
    private LocalDate validUpto;

    @NotNull
    @Size(max = 255)
    private String paymentGatewayToken;

    @NotNull
    private LocalDate nextRenewal;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;


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

    public LocalDate getValidUpto() {
        return validUpto;
    }

    public void setValidUpto(LocalDate validUpto) {
        this.validUpto = validUpto;
    }

    public String getPaymentGatewayToken() {
        return paymentGatewayToken;
    }

    public void setPaymentGatewayToken(String paymentGatewayToken) {
        this.paymentGatewayToken = paymentGatewayToken;
    }

    public LocalDate getNextRenewal() {
        return nextRenewal;
    }

    public void setNextRenewal(LocalDate nextRenewal) {
        this.nextRenewal = nextRenewal;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
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

        UserSubscriptionDTO userSubscriptionDTO = (UserSubscriptionDTO) o;
        if (userSubscriptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userSubscriptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserSubscriptionDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", validUpto='" + getValidUpto() + "'" +
            ", paymentGatewayToken='" + getPaymentGatewayToken() + "'" +
            ", nextRenewal='" + getNextRenewal() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", subscriptionPlanId=" + getSubscriptionPlanId() +
            ", userProfileId=" + getUserProfileId() +
            "}";
    }
}
