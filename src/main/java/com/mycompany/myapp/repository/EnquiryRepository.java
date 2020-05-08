package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Enquiry;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Enquiry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Long>, JpaSpecificationExecutor<Enquiry> {
}
