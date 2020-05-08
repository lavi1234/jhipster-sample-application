package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BonusReference;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BonusReference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BonusReferenceRepository extends JpaRepository<BonusReference, Long>, JpaSpecificationExecutor<BonusReference> {
}
