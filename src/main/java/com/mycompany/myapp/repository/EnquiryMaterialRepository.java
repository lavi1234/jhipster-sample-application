package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EnquiryMaterial;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EnquiryMaterial entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnquiryMaterialRepository extends JpaRepository<EnquiryMaterial, Long>, JpaSpecificationExecutor<EnquiryMaterial> {
}
