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

import com.mycompany.myapp.domain.AppFeatures;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.AppFeaturesRepository;
import com.mycompany.myapp.service.dto.AppFeaturesCriteria;
import com.mycompany.myapp.service.dto.AppFeaturesDTO;
import com.mycompany.myapp.service.mapper.AppFeaturesMapper;

/**
 * Service for executing complex queries for {@link AppFeatures} entities in the database.
 * The main input is a {@link AppFeaturesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AppFeaturesDTO} or a {@link Page} of {@link AppFeaturesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppFeaturesQueryService extends QueryService<AppFeatures> {

    private final Logger log = LoggerFactory.getLogger(AppFeaturesQueryService.class);

    private final AppFeaturesRepository appFeaturesRepository;

    private final AppFeaturesMapper appFeaturesMapper;

    public AppFeaturesQueryService(AppFeaturesRepository appFeaturesRepository, AppFeaturesMapper appFeaturesMapper) {
        this.appFeaturesRepository = appFeaturesRepository;
        this.appFeaturesMapper = appFeaturesMapper;
    }

    /**
     * Return a {@link List} of {@link AppFeaturesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AppFeaturesDTO> findByCriteria(AppFeaturesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AppFeatures> specification = createSpecification(criteria);
        return appFeaturesMapper.toDto(appFeaturesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AppFeaturesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AppFeaturesDTO> findByCriteria(AppFeaturesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AppFeatures> specification = createSpecification(criteria);
        return appFeaturesRepository.findAll(specification, page)
            .map(appFeaturesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppFeaturesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AppFeatures> specification = createSpecification(criteria);
        return appFeaturesRepository.count(specification);
    }

    /**
     * Function to convert {@link AppFeaturesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AppFeatures> createSpecification(AppFeaturesCriteria criteria) {
        Specification<AppFeatures> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), AppFeatures_.id));
            }
            if (criteria.getMenuSortNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMenuSortNumber(), AppFeatures_.menuSortNumber));
            }
            if (criteria.getNameId() != null) {
                specification = specification.and(buildSpecification(criteria.getNameId(),
                    root -> root.join(AppFeatures_.name, JoinType.LEFT).get(Localization_.id)));
            }
            if (criteria.getSubsriptionPlanFeatureId() != null) {
                specification = specification.and(buildSpecification(criteria.getSubsriptionPlanFeatureId(),
                    root -> root.join(AppFeatures_.subsriptionPlanFeatures, JoinType.LEFT).get(SubsriptionPlanFeature_.id)));
            }
        }
        return specification;
    }
}
