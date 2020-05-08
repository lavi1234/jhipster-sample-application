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

import com.mycompany.myapp.domain.UserSubscription;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.UserSubscriptionRepository;
import com.mycompany.myapp.service.dto.UserSubscriptionCriteria;
import com.mycompany.myapp.service.dto.UserSubscriptionDTO;
import com.mycompany.myapp.service.mapper.UserSubscriptionMapper;

/**
 * Service for executing complex queries for {@link UserSubscription} entities in the database.
 * The main input is a {@link UserSubscriptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserSubscriptionDTO} or a {@link Page} of {@link UserSubscriptionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserSubscriptionQueryService extends QueryService<UserSubscription> {

    private final Logger log = LoggerFactory.getLogger(UserSubscriptionQueryService.class);

    private final UserSubscriptionRepository userSubscriptionRepository;

    private final UserSubscriptionMapper userSubscriptionMapper;

    public UserSubscriptionQueryService(UserSubscriptionRepository userSubscriptionRepository, UserSubscriptionMapper userSubscriptionMapper) {
        this.userSubscriptionRepository = userSubscriptionRepository;
        this.userSubscriptionMapper = userSubscriptionMapper;
    }

    /**
     * Return a {@link List} of {@link UserSubscriptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserSubscriptionDTO> findByCriteria(UserSubscriptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserSubscription> specification = createSpecification(criteria);
        return userSubscriptionMapper.toDto(userSubscriptionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserSubscriptionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserSubscriptionDTO> findByCriteria(UserSubscriptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserSubscription> specification = createSpecification(criteria);
        return userSubscriptionRepository.findAll(specification, page)
            .map(userSubscriptionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserSubscriptionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserSubscription> specification = createSpecification(criteria);
        return userSubscriptionRepository.count(specification);
    }

    /**
     * Function to convert {@link UserSubscriptionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserSubscription> createSpecification(UserSubscriptionCriteria criteria) {
        Specification<UserSubscription> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserSubscription_.id));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), UserSubscription_.price));
            }
            if (criteria.getValidUpto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidUpto(), UserSubscription_.validUpto));
            }
            if (criteria.getPaymentGatewayToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentGatewayToken(), UserSubscription_.paymentGatewayToken));
            }
            if (criteria.getNextRenewal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextRenewal(), UserSubscription_.nextRenewal));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), UserSubscription_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), UserSubscription_.updatedAt));
            }
            if (criteria.getSubscriptionPlanId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubscriptionPlanId(),
                    root -> root.join(UserSubscription_.subscriptionPlan, JoinType.LEFT).get(SubscriptionPlan_.id)));
            }
            if (criteria.getUserProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserProfileId(),
                    root -> root.join(UserSubscription_.userProfile, JoinType.LEFT).get(UserProfile_.id)));
            }
        }
        return specification;
    }
}
