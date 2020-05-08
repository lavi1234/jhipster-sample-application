package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.CommissionReference} entity.
 */
public class CommissionReferenceDTO implements Serializable {
    
    private Long id;

    @NotNull
    private BigDecimal percentage;

    @NotNull
    private Integer holdDays;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Instant updatedAt;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Integer getHoldDays() {
        return holdDays;
    }

    public void setHoldDays(Integer holdDays) {
        this.holdDays = holdDays;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommissionReferenceDTO commissionReferenceDTO = (CommissionReferenceDTO) o;
        if (commissionReferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commissionReferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommissionReferenceDTO{" +
            "id=" + getId() +
            ", percentage=" + getPercentage() +
            ", holdDays=" + getHoldDays() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
