package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A BonusReference.
 */
@Entity
@Table(name = "bonus_reference")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BonusReference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "percentage", precision = 21, scale = 2, nullable = false)
    private BigDecimal percentage;

    @NotNull
    @Column(name = "hold_days", nullable = false)
    private Integer holdDays;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public BonusReference percentage(BigDecimal percentage) {
        this.percentage = percentage;
        return this;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Integer getHoldDays() {
        return holdDays;
    }

    public BonusReference holdDays(Integer holdDays) {
        this.holdDays = holdDays;
        return this;
    }

    public void setHoldDays(Integer holdDays) {
        this.holdDays = holdDays;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public BonusReference createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public BonusReference updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonusReference)) {
            return false;
        }
        return id != null && id.equals(((BonusReference) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BonusReference{" +
            "id=" + getId() +
            ", percentage=" + getPercentage() +
            ", holdDays=" + getHoldDays() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
