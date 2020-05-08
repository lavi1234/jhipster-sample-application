package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EnquiryDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EnquiryDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnquiryDetailsRepository extends JpaRepository<EnquiryDetails, Long>, JpaSpecificationExecutor<EnquiryDetails> {
}
