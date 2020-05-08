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

import com.mycompany.myapp.domain.Localization;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.LocalizationRepository;
import com.mycompany.myapp.service.dto.LocalizationCriteria;
import com.mycompany.myapp.service.dto.LocalizationDTO;
import com.mycompany.myapp.service.mapper.LocalizationMapper;

/**
 * Service for executing complex queries for {@link Localization} entities in the database.
 * The main input is a {@link LocalizationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LocalizationDTO} or a {@link Page} of {@link LocalizationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LocalizationQueryService extends QueryService<Localization> {

    private final Logger log = LoggerFactory.getLogger(LocalizationQueryService.class);

    private final LocalizationRepository localizationRepository;

    private final LocalizationMapper localizationMapper;

    public LocalizationQueryService(LocalizationRepository localizationRepository, LocalizationMapper localizationMapper) {
        this.localizationRepository = localizationRepository;
        this.localizationMapper = localizationMapper;
    }

    /**
     * Return a {@link List} of {@link LocalizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LocalizationDTO> findByCriteria(LocalizationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Localization> specification = createSpecification(criteria);
        return localizationMapper.toDto(localizationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LocalizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LocalizationDTO> findByCriteria(LocalizationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Localization> specification = createSpecification(criteria);
        return localizationRepository.findAll(specification, page)
            .map(localizationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LocalizationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Localization> specification = createSpecification(criteria);
        return localizationRepository.count(specification);
    }

    /**
     * Function to convert {@link LocalizationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Localization> createSpecification(LocalizationCriteria criteria) {
        Specification<Localization> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Localization_.id));
            }
            if (criteria.getLabelEn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabelEn(), Localization_.labelEn));
            }
            if (criteria.getLabelDe() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLabelDe(), Localization_.labelDe));
            }
        }
        return specification;
    }
}
