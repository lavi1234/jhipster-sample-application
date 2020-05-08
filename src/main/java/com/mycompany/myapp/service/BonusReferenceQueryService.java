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

import com.mycompany.myapp.domain.BonusReference;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.BonusReferenceRepository;
import com.mycompany.myapp.service.dto.BonusReferenceCriteria;
import com.mycompany.myapp.service.dto.BonusReferenceDTO;
import com.mycompany.myapp.service.mapper.BonusReferenceMapper;

/**
 * Service for executing complex queries for {@link BonusReference} entities in the database.
 * The main input is a {@link BonusReferenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BonusReferenceDTO} or a {@link Page} of {@link BonusReferenceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BonusReferenceQueryService extends QueryService<BonusReference> {

    private final Logger log = LoggerFactory.getLogger(BonusReferenceQueryService.class);

    private final BonusReferenceRepository bonusReferenceRepository;

    private final BonusReferenceMapper bonusReferenceMapper;

    public BonusReferenceQueryService(BonusReferenceRepository bonusReferenceRepository, BonusReferenceMapper bonusReferenceMapper) {
        this.bonusReferenceRepository = bonusReferenceRepository;
        this.bonusReferenceMapper = bonusReferenceMapper;
    }

    /**
     * Return a {@link List} of {@link BonusReferenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BonusReferenceDTO> findByCriteria(BonusReferenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BonusReference> specification = createSpecification(criteria);
        return bonusReferenceMapper.toDto(bonusReferenceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BonusReferenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BonusReferenceDTO> findByCriteria(BonusReferenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BonusReference> specification = createSpecification(criteria);
        return bonusReferenceRepository.findAll(specification, page)
            .map(bonusReferenceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BonusReferenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BonusReference> specification = createSpecification(criteria);
        return bonusReferenceRepository.count(specification);
    }

    /**
     * Function to convert {@link BonusReferenceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BonusReference> createSpecification(BonusReferenceCriteria criteria) {
        Specification<BonusReference> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BonusReference_.id));
            }
            if (criteria.getPercentage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPercentage(), BonusReference_.percentage));
            }
            if (criteria.getHoldDays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHoldDays(), BonusReference_.holdDays));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), BonusReference_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), BonusReference_.updatedAt));
            }
        }
        return specification;
    }
}
