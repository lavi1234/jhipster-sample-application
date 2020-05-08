package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A SubsriptionPlanFeature.
 */
@Entity
@Table(name = "subsription_plan_feature")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubsriptionPlanFeature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "subsription_plan_feature_subscription_plan",
               joinColumns = @JoinColumn(name = "subsription_plan_feature_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "subscription_plan_id", referencedColumnName = "id"))
    private Set<SubscriptionPlan> subscriptionPlans = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "subsription_plan_feature_app_feature",
               joinColumns = @JoinColumn(name = "subsription_plan_feature_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "app_feature_id", referencedColumnName = "id"))
    private Set<AppFeatures> appFeatures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<SubscriptionPlan> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    public SubsriptionPlanFeature subscriptionPlans(Set<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
        return this;
    }

    public SubsriptionPlanFeature addSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlans.add(subscriptionPlan);
        subscriptionPlan.getSubsriptionPlanFeatures().add(this);
        return this;
    }

    public SubsriptionPlanFeature removeSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlans.remove(subscriptionPlan);
        subscriptionPlan.getSubsriptionPlanFeatures().remove(this);
        return this;
    }

    public void setSubscriptionPlans(Set<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

    public Set<AppFeatures> getAppFeatures() {
        return appFeatures;
    }

    public SubsriptionPlanFeature appFeatures(Set<AppFeatures> appFeatures) {
        this.appFeatures = appFeatures;
        return this;
    }

    public SubsriptionPlanFeature addAppFeature(AppFeatures appFeatures) {
        this.appFeatures.add(appFeatures);
        appFeatures.getSubsriptionPlanFeatures().add(this);
        return this;
    }

    public SubsriptionPlanFeature removeAppFeature(AppFeatures appFeatures) {
        this.appFeatures.remove(appFeatures);
        appFeatures.getSubsriptionPlanFeatures().remove(this);
        return this;
    }

    public void setAppFeatures(Set<AppFeatures> appFeatures) {
        this.appFeatures = appFeatures;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubsriptionPlanFeature)) {
            return false;
        }
        return id != null && id.equals(((SubsriptionPlanFeature) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubsriptionPlanFeature{" +
            "id=" + getId() +
            "}";
    }
}
