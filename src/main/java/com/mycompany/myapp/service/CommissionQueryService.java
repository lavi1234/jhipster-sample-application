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

import com.mycompany.myapp.domain.Commission;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.CommissionRepository;
import com.mycompany.myapp.service.dto.CommissionCriteria;
import com.mycompany.myapp.service.dto.CommissionDTO;
import com.mycompany.myapp.service.mapper.CommissionMapper;

/**
 * Service for executing complex queries for {@link Commission} entities in the database.
 * The main input is a {@link CommissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommissionDTO} or a {@link Page} of {@link CommissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommissionQueryService extends QueryService<Commission> {

    private final Logger log = LoggerFactory.getLogger(CommissionQueryService.class);

    private final CommissionRepository commissionRepository;

    private final CommissionMapper commissionMapper;

    public CommissionQueryService(CommissionRepository commissionRepository, CommissionMapper commissionMapper) {
        this.commissionRepository = commissionRepository;
        this.commissionMapper = commissionMapper;
    }

    /**
     * Return a {@link List} of {@link CommissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommissionDTO> findByCriteria(CommissionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Commission> specification = createSpecification(criteria);
        return commissionMapper.toDto(commissionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommissionDTO> findByCriteria(CommissionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Commission> specification = createSpecification(criteria);
        return commissionRepository.findAll(specification, page)
            .map(commissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommissionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Commission> specification = createSpecification(criteria);
        return commissionRepository.count(specification);
    }

    /**
     * Function to convert {@link CommissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Commission> createSpecification(CommissionCriteria criteria) {
        Specification<Commission> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Commission_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Commission_.amount));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Commission_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Commission_.updatedAt));
            }
            if (criteria.getPrincipalAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrincipalAmount(), Commission_.principalAmount));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Commission_.status));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), Commission_.remarks));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(Commission_.supplier, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(Commission_.order, JoinType.LEFT).get(Order_.id)));
            }
        }
        return specification;
    }
}
