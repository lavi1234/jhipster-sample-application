package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.SubscriptionPlan} entity.
 */
public class SubscriptionPlanDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 100)
    private String validity;

    @NotNull
    private Double price;

    @NotNull
    private Integer createdBy;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;


    private Long nameId;

    private Long descriptionId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
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

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long localizationId) {
        this.nameId = localizationId;
    }

    public Long getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(Long localizationId) {
        this.descriptionId = localizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubscriptionPlanDTO subscriptionPlanDTO = (SubscriptionPlanDTO) o;
        if (subscriptionPlanDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subscriptionPlanDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubscriptionPlanDTO{" +
            "id=" + getId() +
            ", validity='" + getValidity() + "'" +
            ", price=" + getPrice() +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", nameId=" + getNameId() +
            ", descriptionId=" + getDescriptionId() +
            "}";
    }
}
