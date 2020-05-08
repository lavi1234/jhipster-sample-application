package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SubsriptionPlanFeature;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the SubsriptionPlanFeature entity.
 */
@Repository
public interface SubsriptionPlanFeatureRepository extends JpaRepository<SubsriptionPlanFeature, Long>, JpaSpecificationExecutor<SubsriptionPlanFeature> {

    @Query(value = "select distinct subsriptionPlanFeature from SubsriptionPlanFeature subsriptionPlanFeature left join fetch subsriptionPlanFeature.subscriptionPlans left join fetch subsriptionPlanFeature.appFeatures",
        countQuery = "select count(distinct subsriptionPlanFeature) from SubsriptionPlanFeature subsriptionPlanFeature")
    Page<SubsriptionPlanFeature> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct subsriptionPlanFeature from SubsriptionPlanFeature subsriptionPlanFeature left join fetch subsriptionPlanFeature.subscriptionPlans left join fetch subsriptionPlanFeature.appFeatures")
    List<SubsriptionPlanFeature> findAllWithEagerRelationships();

    @Query("select subsriptionPlanFeature from SubsriptionPlanFeature subsriptionPlanFeature left join fetch subsriptionPlanFeature.subscriptionPlans left join fetch subsriptionPlanFeature.appFeatures where subsriptionPlanFeature.id =:id")
    Optional<SubsriptionPlanFeature> findOneWithEagerRelationships(@Param("id") Long id);
}
