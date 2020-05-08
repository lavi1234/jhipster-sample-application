package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EmailTracking;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the EmailTracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmailTrackingRepository extends JpaRepository<EmailTracking, Long>, JpaSpecificationExecutor<EmailTracking> {
}
