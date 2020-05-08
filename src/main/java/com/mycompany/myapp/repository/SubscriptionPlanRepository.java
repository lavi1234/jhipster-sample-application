package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SubscriptionPlan;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SubscriptionPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long>, JpaSpecificationExecutor<SubscriptionPlan> {
}
