package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Localization;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Localization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalizationRepository extends JpaRepository<Localization, Long>, JpaSpecificationExecutor<Localization> {
}
