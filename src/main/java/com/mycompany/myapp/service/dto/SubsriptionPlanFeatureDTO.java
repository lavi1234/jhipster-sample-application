package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.SubsriptionPlanFeature} entity.
 */
public class SubsriptionPlanFeatureDTO implements Serializable {
    
    private Long id;

    private Set<SubscriptionPlanDTO> subscriptionPlans = new HashSet<>();
    private Set<AppFeaturesDTO> appFeatures = new HashSet<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<SubscriptionPlanDTO> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    public void setSubscriptionPlans(Set<SubscriptionPlanDTO> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

    public Set<AppFeaturesDTO> getAppFeatures() {
        return appFeatures;
    }

    public void setAppFeatures(Set<AppFeaturesDTO> appFeatures) {
        this.appFeatures = appFeatures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubsriptionPlanFeatureDTO subsriptionPlanFeatureDTO = (SubsriptionPlanFeatureDTO) o;
        if (subsriptionPlanFeatureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subsriptionPlanFeatureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubsriptionPlanFeatureDTO{" +
            "id=" + getId() +
            ", subscriptionPlans='" + getSubscriptionPlans() + "'" +
            ", appFeatures='" + getAppFeatures() + "'" +
            "}";
    }
}
