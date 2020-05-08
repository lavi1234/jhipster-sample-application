package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A SubscriptionPlan.
 */
@Entity
@Table(name = "subscription_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubscriptionPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "validity", length = 100, nullable = false)
    private String validity;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("subscriptionPlans")
    private Localization name;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("subscriptionPlans")
    private Localization description;

    @ManyToMany(mappedBy = "subscriptionPlans")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<SubsriptionPlanFeature> subsriptionPlanFeatures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValidity() {
        return validity;
    }

    public SubscriptionPlan validity(String validity) {
        this.validity = validity;
        return this;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public Double getPrice() {
        return price;
    }

    public SubscriptionPlan price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public SubscriptionPlan createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public SubscriptionPlan createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public SubscriptionPlan updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Localization getName() {
        return name;
    }

    public SubscriptionPlan name(Localization localization) {
        this.name = localization;
        return this;
    }

    public void setName(Localization localization) {
        this.name = localization;
    }

    public Localization getDescription() {
        return description;
    }

    public SubscriptionPlan description(Localization localization) {
        this.description = localization;
        return this;
    }

    public void setDescription(Localization localization) {
        this.description = localization;
    }

    public Set<SubsriptionPlanFeature> getSubsriptionPlanFeatures() {
        return subsriptionPlanFeatures;
    }

    public SubscriptionPlan subsriptionPlanFeatures(Set<SubsriptionPlanFeature> subsriptionPlanFeatures) {
        this.subsriptionPlanFeatures = subsriptionPlanFeatures;
        return this;
    }

    public SubscriptionPlan addSubsriptionPlanFeature(SubsriptionPlanFeature subsriptionPlanFeature) {
        this.subsriptionPlanFeatures.add(subsriptionPlanFeature);
        subsriptionPlanFeature.getSubscriptionPlans().add(this);
        return this;
    }

    public SubscriptionPlan removeSubsriptionPlanFeature(SubsriptionPlanFeature subsriptionPlanFeature) {
        this.subsriptionPlanFeatures.remove(subsriptionPlanFeature);
        subsriptionPlanFeature.getSubscriptionPlans().remove(this);
        return this;
    }

    public void setSubsriptionPlanFeatures(Set<SubsriptionPlanFeature> subsriptionPlanFeatures) {
        this.subsriptionPlanFeatures = subsriptionPlanFeatures;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubscriptionPlan)) {
            return false;
        }
        return id != null && id.equals(((SubscriptionPlan) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubscriptionPlan{" +
            "id=" + getId() +
            ", validity='" + getValidity() + "'" +
            ", price=" + getPrice() +
            ", createdBy=" + getCreatedBy() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
