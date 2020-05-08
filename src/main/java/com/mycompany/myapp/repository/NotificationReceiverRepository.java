package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.NotificationReceiver;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NotificationReceiver entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificationReceiverRepository extends JpaRepository<NotificationReceiver, Long>, JpaSpecificationExecutor<NotificationReceiver> {
}
