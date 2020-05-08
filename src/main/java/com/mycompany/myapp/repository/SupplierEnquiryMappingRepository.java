package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SupplierEnquiryMapping;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SupplierEnquiryMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplierEnquiryMappingRepository extends JpaRepository<SupplierEnquiryMapping, Long>, JpaSpecificationExecutor<SupplierEnquiryMapping> {
}
