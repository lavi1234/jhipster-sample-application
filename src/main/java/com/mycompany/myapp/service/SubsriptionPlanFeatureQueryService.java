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

import com.mycompany.myapp.domain.SubsriptionPlanFeature;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.SubsriptionPlanFeatureRepository;
import com.mycompany.myapp.service.dto.SubsriptionPlanFeatureCriteria;
import com.mycompany.myapp.service.dto.SubsriptionPlanFeatureDTO;
import com.mycompany.myapp.service.mapper.SubsriptionPlanFeatureMapper;

/**
 * Service for executing complex queries for {@link SubsriptionPlanFeature} entities in the database.
 * The main input is a {@link SubsriptionPlanFeatureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SubsriptionPlanFeatureDTO} or a {@link Page} of {@link SubsriptionPlanFeatureDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubsriptionPlanFeatureQueryService extends QueryService<SubsriptionPlanFeature> {

    private final Logger log = LoggerFactory.getLogger(SubsriptionPlanFeatureQueryService.class);

    private final SubsriptionPlanFeatureRepository subsriptionPlanFeatureRepository;

    private final SubsriptionPlanFeatureMapper subsriptionPlanFeatureMapper;

    public SubsriptionPlanFeatureQueryService(SubsriptionPlanFeatureRepository subsriptionPlanFeatureRepository, SubsriptionPlanFeatureMapper subsriptionPlanFeatureMapper) {
        this.subsriptionPlanFeatureRepository = subsriptionPlanFeatureRepository;
        this.subsriptionPlanFeatureMapper = subsriptionPlanFeatureMapper;
    }

    /**
     * Return a {@link List} of {@link SubsriptionPlanFeatureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SubsriptionPlanFeatureDTO> findByCriteria(SubsriptionPlanFeatureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SubsriptionPlanFeature> specification = createSpecification(criteria);
        return subsriptionPlanFeatureMapper.toDto(subsriptionPlanFeatureRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SubsriptionPlanFeatureDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubsriptionPlanFeatureDTO> findByCriteria(SubsriptionPlanFeatureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SubsriptionPlanFeature> specification = createSpecification(criteria);
        return subsriptionPlanFeatureRepository.findAll(specification, page)
            .map(subsriptionPlanFeatureMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubsriptionPlanFeatureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SubsriptionPlanFeature> specification = createSpecification(criteria);
        return subsriptionPlanFeatureRepository.count(specification);
    }

    /**
     * Function to convert {@link SubsriptionPlanFeatureCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SubsriptionPlanFeature> createSpecification(SubsriptionPlanFeatureCriteria criteria) {
        Specification<SubsriptionPlanFeature> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SubsriptionPlanFeature_.id));
            }
            if (criteria.getSubscriptionPlanId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubscriptionPlanId(),
                    root -> root.join(SubsriptionPlanFeature_.subscriptionPlans, JoinType.LEFT).get(SubscriptionPlan_.id)));
            }
            if (criteria.getAppFeatureId() != null) {
                specification = specification.and(buildSpecification(criteria.getAppFeatureId(),
                    root -> root.join(SubsriptionPlanFeature_.appFeatures, JoinType.LEFT).get(AppFeatures_.id)));
            }
        }
        return specification;
    }
}
