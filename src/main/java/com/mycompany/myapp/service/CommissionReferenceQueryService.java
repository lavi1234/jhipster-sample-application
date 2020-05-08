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

import com.mycompany.myapp.domain.CommissionReference;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.CommissionReferenceRepository;
import com.mycompany.myapp.service.dto.CommissionReferenceCriteria;
import com.mycompany.myapp.service.dto.CommissionReferenceDTO;
import com.mycompany.myapp.service.mapper.CommissionReferenceMapper;

/**
 * Service for executing complex queries for {@link CommissionReference} entities in the database.
 * The main input is a {@link CommissionReferenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CommissionReferenceDTO} or a {@link Page} of {@link CommissionReferenceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CommissionReferenceQueryService extends QueryService<CommissionReference> {

    private final Logger log = LoggerFactory.getLogger(CommissionReferenceQueryService.class);

    private final CommissionReferenceRepository commissionReferenceRepository;

    private final CommissionReferenceMapper commissionReferenceMapper;

    public CommissionReferenceQueryService(CommissionReferenceRepository commissionReferenceRepository, CommissionReferenceMapper commissionReferenceMapper) {
        this.commissionReferenceRepository = commissionReferenceRepository;
        this.commissionReferenceMapper = commissionReferenceMapper;
    }

    /**
     * Return a {@link List} of {@link CommissionReferenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CommissionReferenceDTO> findByCriteria(CommissionReferenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CommissionReference> specification = createSpecification(criteria);
        return commissionReferenceMapper.toDto(commissionReferenceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CommissionReferenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CommissionReferenceDTO> findByCriteria(CommissionReferenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CommissionReference> specification = createSpecification(criteria);
        return commissionReferenceRepository.findAll(specification, page)
            .map(commissionReferenceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CommissionReferenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CommissionReference> specification = createSpecification(criteria);
        return commissionReferenceRepository.count(specification);
    }

    /**
     * Function to convert {@link CommissionReferenceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CommissionReference> createSpecification(CommissionReferenceCriteria criteria) {
        Specification<CommissionReference> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CommissionReference_.id));
            }
            if (criteria.getPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentage(), CommissionReference_.percentage));
            }
            if (criteria.getHoldDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoldDays(), CommissionReference_.holdDays));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), CommissionReference_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), CommissionReference_.updatedAt));
            }
        }
        return specification;
    }
}
