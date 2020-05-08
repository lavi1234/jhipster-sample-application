package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Favourites;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Favourites entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Long>, JpaSpecificationExecutor<Favourites> {
}
