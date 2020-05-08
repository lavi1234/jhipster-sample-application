package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.StaticPages;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StaticPages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StaticPagesRepository extends JpaRepository<StaticPages, Long>, JpaSpecificationExecutor<StaticPages> {
}
