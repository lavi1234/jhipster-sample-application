package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CommissionReference;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CommissionReference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommissionReferenceRepository extends JpaRepository<CommissionReference, Long>, JpaSpecificationExecutor<CommissionReference> {
}
