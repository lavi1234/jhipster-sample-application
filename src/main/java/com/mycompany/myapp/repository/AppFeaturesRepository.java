package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.AppFeatures;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AppFeatures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppFeaturesRepository extends JpaRepository<AppFeatures, Long>, JpaSpecificationExecutor<AppFeatures> {
}
