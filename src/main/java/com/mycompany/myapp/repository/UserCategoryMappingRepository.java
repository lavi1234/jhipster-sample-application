package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserCategoryMapping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserCategoryMapping entity.
 */
@Repository
public interface UserCategoryMappingRepository extends JpaRepository<UserCategoryMapping, Long>, JpaSpecificationExecutor<UserCategoryMapping> {

    @Query(value = "select distinct userCategoryMapping from UserCategoryMapping userCategoryMapping left join fetch userCategoryMapping.userProfiles left join fetch userCategoryMapping.categories",
        countQuery = "select count(distinct userCategoryMapping) from UserCategoryMapping userCategoryMapping")
    Page<UserCategoryMapping> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct userCategoryMapping from UserCategoryMapping userCategoryMapping left join fetch userCategoryMapping.userProfiles left join fetch userCategoryMapping.categories")
    List<UserCategoryMapping> findAllWithEagerRelationships();

    @Query("select userCategoryMapping from UserCategoryMapping userCategoryMapping left join fetch userCategoryMapping.userProfiles left join fetch userCategoryMapping.categories where userCategoryMapping.id =:id")
    Optional<UserCategoryMapping> findOneWithEagerRelationships(@Param("id") Long id);
}
