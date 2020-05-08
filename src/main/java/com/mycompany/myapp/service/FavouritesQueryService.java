package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.Favourites;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.FavouritesRepository;
import com.mycompany.myapp.service.dto.FavouritesCriteria;
import com.mycompany.myapp.service.dto.FavouritesDTO;
import com.mycompany.myapp.service.mapper.FavouritesMapper;

/**
 * Service for executing complex queries for {@link Favourites} entities in the database.
 * The main input is a {@link FavouritesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FavouritesDTO} or a {@link Page} of {@link FavouritesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FavouritesQueryService extends QueryService<Favourites> {

    private final Logger log = LoggerFactory.getLogger(FavouritesQueryService.class);

    private final FavouritesRepository favouritesRepository;

    private final FavouritesMapper favouritesMapper;

    public FavouritesQueryService(FavouritesRepository favouritesRepository, FavouritesMapper favouritesMapper) {
        this.favouritesRepository = favouritesRepository;
        this.favouritesMapper = favouritesMapper;
    }

    /**
     * Return a {@link List} of {@link FavouritesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FavouritesDTO> findByCriteria(FavouritesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Favourites> specification = createSpecification(criteria);
        return favouritesMapper.toDto(favouritesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FavouritesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FavouritesDTO> findByCriteria(FavouritesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Favourites> specification = createSpecification(criteria);
        return favouritesRepository.findAll(specification, page)
            .map(favouritesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FavouritesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Favourites> specification = createSpecification(criteria);
        return favouritesRepository.count(specification);
    }

    /**
     * Function to convert {@link FavouritesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Favourites> createSpecification(FavouritesCriteria criteria) {
        Specification<Favourites> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Favourites_.id));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Favourites_.createdAt));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), Favourites_.remarks));
            }
            if (criteria.getFromProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getFromProfileId(),
                    root -> root.join(Favourites_.fromProfile, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getToProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getToProfileId(),
                    root -> root.join(Favourites_.toProfile, JoinType.LEFT).get(UserProfile_.id)));
            }
        }
        return specification;
    }
}
