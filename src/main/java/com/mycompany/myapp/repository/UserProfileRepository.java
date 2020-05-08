package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.UserProfile;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {

    @Query("select userProfile from UserProfile userProfile where userProfile.user.login = ?#{principal.username}")
    List<UserProfile> findByUserIsCurrentUser();
}
