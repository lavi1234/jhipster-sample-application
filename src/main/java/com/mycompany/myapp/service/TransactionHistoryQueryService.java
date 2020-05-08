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

import com.mycompany.myapp.domain.TransactionHistory;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.TransactionHistoryRepository;
import com.mycompany.myapp.service.dto.TransactionHistoryCriteria;
import com.mycompany.myapp.service.dto.TransactionHistoryDTO;
import com.mycompany.myapp.service.mapper.TransactionHistoryMapper;

/**
 * Service for executing complex queries for {@link TransactionHistory} entities in the database.
 * The main input is a {@link TransactionHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransactionHistoryDTO} or a {@link Page} of {@link TransactionHistoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionHistoryQueryService extends QueryService<TransactionHistory> {

    private final Logger log = LoggerFactory.getLogger(TransactionHistoryQueryService.class);

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final TransactionHistoryMapper transactionHistoryMapper;

    public TransactionHistoryQueryService(TransactionHistoryRepository transactionHistoryRepository, TransactionHistoryMapper transactionHistoryMapper) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transactionHistoryMapper = transactionHistoryMapper;
    }

    /**
     * Return a {@link List} of {@link TransactionHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionHistoryDTO> findByCriteria(TransactionHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransactionHistory> specification = createSpecification(criteria);
        return transactionHistoryMapper.toDto(transactionHistoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransactionHistoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionHistoryDTO> findByCriteria(TransactionHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransactionHistory> specification = createSpecification(criteria);
        return transactionHistoryRepository.findAll(specification, page)
            .map(transactionHistoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransactionHistory> specification = createSpecification(criteria);
        return transactionHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link TransactionHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TransactionHistory> createSpecification(TransactionHistoryCriteria criteria) {
        Specification<TransactionHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TransactionHistory_.id));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), TransactionHistory_.price));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), TransactionHistory_.createdAt));
            }
            if (criteria.getPaymentGatewayToken() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentGatewayToken(), TransactionHistory_.paymentGatewayToken));
            }
            if (criteria.getPaymentGatewayResponse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaymentGatewayResponse(), TransactionHistory_.paymentGatewayResponse));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), TransactionHistory_.status));
            }
            if (criteria.getSubscriptionPlanId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubscriptionPlanId(),
                    root -> root.join(TransactionHistory_.subscriptionPlan, JoinType.LEFT).get(SubscriptionPlan_.id)));
            }
            if (criteria.getUserProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserProfileId(),
                    root -> root.join(TransactionHistory_.userProfile, JoinType.LEFT).get(UserProfile_.id)));
            }
        }
        return specification;
    }
}
