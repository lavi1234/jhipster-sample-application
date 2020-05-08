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

import com.mycompany.myapp.domain.SubscriptionPlan;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.SubscriptionPlanRepository;
import com.mycompany.myapp.service.dto.SubscriptionPlanCriteria;
import com.mycompany.myapp.service.dto.SubscriptionPlanDTO;
import com.mycompany.myapp.service.mapper.SubscriptionPlanMapper;

/**
 * Service for executing complex queries for {@link SubscriptionPlan} entities in the database.
 * The main input is a {@link SubscriptionPlanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubscriptionPlanDTO} or a {@link Page} of {@link SubscriptionPlanDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubscriptionPlanQueryService extends QueryService<SubscriptionPlan> {

    private final Logger log = LoggerFactory.getLogger(SubscriptionPlanQueryService.class);

    private final SubscriptionPlanRepository subscriptionPlanRepository;

    private final SubscriptionPlanMapper subscriptionPlanMapper;

    public SubscriptionPlanQueryService(SubscriptionPlanRepository subscriptionPlanRepository, SubscriptionPlanMapper subscriptionPlanMapper) {
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.subscriptionPlanMapper = subscriptionPlanMapper;
    }

    /**
     * Return a {@link List} of {@link SubscriptionPlanDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubscriptionPlanDTO> findByCriteria(SubscriptionPlanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SubscriptionPlan> specification = createSpecification(criteria);
        return subscriptionPlanMapper.toDto(subscriptionPlanRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SubscriptionPlanDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubscriptionPlanDTO> findByCriteria(SubscriptionPlanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SubscriptionPlan> specification = createSpecification(criteria);
        return subscriptionPlanRepository.findAll(specification, page)
            .map(subscriptionPlanMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubscriptionPlanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SubscriptionPlan> specification = createSpecification(criteria);
        return subscriptionPlanRepository.count(specification);
    }

    /**
     * Function to convert {@link SubscriptionPlanCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SubscriptionPlan> createSpecification(SubscriptionPlanCriteria criteria) {
        Specification<SubscriptionPlan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SubscriptionPlan_.id));
            }
            if (criteria.getValidity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValidity(), SubscriptionPlan_.validity));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), SubscriptionPlan_.price));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedBy(), SubscriptionPlan_.createdBy));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), SubscriptionPlan_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), SubscriptionPlan_.updatedAt));
            }
            if (criteria.getNameId() != null) {
                specification = specification.and(buildSpecification(criteria.getNameId(),
                    root -> root.join(SubscriptionPlan_.name, JoinType.LEFT).get(Localization_.id)));
            }
            if (criteria.getDescriptionId() != null) {
                specification = specification.and(buildSpecification(criteria.getDescriptionId(),
                    root -> root.join(SubscriptionPlan_.description, JoinType.LEFT).get(Localization_.id)));
            }
            if (criteria.getSubsriptionPlanFeatureId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubsriptionPlanFeatureId(),
                    root -> root.join(SubscriptionPlan_.subsriptionPlanFeatures, JoinType.LEFT).get(SubsriptionPlanFeature_.id)));
            }
        }
        return specification;
    }
}
