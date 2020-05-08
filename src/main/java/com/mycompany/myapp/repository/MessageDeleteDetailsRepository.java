package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.MessageDeleteDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MessageDeleteDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MessageDeleteDetailsRepository extends JpaRepository<MessageDeleteDetails, Long>, JpaSpecificationExecutor<MessageDeleteDetails> {
}
