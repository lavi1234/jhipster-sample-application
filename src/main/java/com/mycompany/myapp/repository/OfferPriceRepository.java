package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OfferPrice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OfferPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferPriceRepository extends JpaRepository<OfferPrice, Long>, JpaSpecificationExecutor<OfferPrice> {
}
