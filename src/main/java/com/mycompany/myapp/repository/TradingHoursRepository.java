package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TradingHours;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TradingHours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TradingHoursRepository extends JpaRepository<TradingHours, Long>, JpaSpecificationExecutor<TradingHours> {
}
