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
 * A Commission.
 */
@Entity
@Table(name = "commission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Commission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @NotNull
    @Column(name = "principal_amount", nullable = false)
    private Double principalAmount;

    @NotNull
    @Size(max = 200)
    @Column(name = "status", length = 200, nullable = false)
    private String status;

    @Size(max = 255)
    @Column(name = "remarks", length = 255)
    private String remarks;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("commissions")
    private UserProfile supplier;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("commissions")
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public Commission amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Commission createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Commission updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Double getPrincipalAmount() {
        return principalAmount;
    }

    public Commission principalAmount(Double principalAmount) {
        this.principalAmount = principalAmount;
        return this;
    }

    public void setPrincipalAmount(Double principalAmount) {
        this.principalAmount = principalAmount;
    }

    public String getStatus() {
        return status;
    }

    public Commission status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public Commission remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public UserProfile getSupplier() {
        return supplier;
    }

    public Commission supplier(UserProfile userProfile) {
        this.supplier = userProfile;
        return this;
    }

    public void setSupplier(UserProfile userProfile) {
        this.supplier = userProfile;
    }

    public Order getOrder() {
        return order;
    }

    public Commission order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commission)) {
            return false;
        }
        return id != null && id.equals(((Commission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Commission{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", principalAmount=" + getPrincipalAmount() +
            ", status='" + getStatus() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
