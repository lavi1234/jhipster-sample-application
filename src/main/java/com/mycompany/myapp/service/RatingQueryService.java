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

import com.mycompany.myapp.domain.Rating;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.RatingRepository;
import com.mycompany.myapp.service.dto.RatingCriteria;
import com.mycompany.myapp.service.dto.RatingDTO;
import com.mycompany.myapp.service.mapper.RatingMapper;

/**
 * Service for executing complex queries for {@link Rating} entities in the database.
 * The main input is a {@link RatingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RatingDTO} or a {@link Page} of {@link RatingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RatingQueryService extends QueryService<Rating> {

    private final Logger log = LoggerFactory.getLogger(RatingQueryService.class);

    private final RatingRepository ratingRepository;

    private final RatingMapper ratingMapper;

    public RatingQueryService(RatingRepository ratingRepository, RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.ratingMapper = ratingMapper;
    }

    /**
     * Return a {@link List} of {@link RatingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RatingDTO> findByCriteria(RatingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Rating> specification = createSpecification(criteria);
        return ratingMapper.toDto(ratingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RatingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RatingDTO> findByCriteria(RatingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Rating> specification = createSpecification(criteria);
        return ratingRepository.findAll(specification, page)
            .map(ratingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RatingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Rating> specification = createSpecification(criteria);
        return ratingRepository.count(specification);
    }

    /**
     * Function to convert {@link RatingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Rating> createSpecification(RatingCriteria criteria) {
        Specification<Rating> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Rating_.id));
            }
            if (criteria.getRating() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRating(), Rating_.rating));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Rating_.createdAt));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), Rating_.remarks));
            }
            if (criteria.getFromProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getFromProfileId(),
                    root -> root.join(Rating_.fromProfile, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getToProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getToProfileId(),
                    root -> root.join(Rating_.toProfile, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(Rating_.order, JoinType.LEFT).get(Order_.id)));
            }
        }
        return specification;
    }
}
