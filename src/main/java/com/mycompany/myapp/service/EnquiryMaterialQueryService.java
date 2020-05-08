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

import com.mycompany.myapp.domain.EnquiryMaterial;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.EnquiryMaterialRepository;
import com.mycompany.myapp.service.dto.EnquiryMaterialCriteria;
import com.mycompany.myapp.service.dto.EnquiryMaterialDTO;
import com.mycompany.myapp.service.mapper.EnquiryMaterialMapper;

/**
 * Service for executing complex queries for {@link EnquiryMaterial} entities in the database.
 * The main input is a {@link EnquiryMaterialCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnquiryMaterialDTO} or a {@link Page} of {@link EnquiryMaterialDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnquiryMaterialQueryService extends QueryService<EnquiryMaterial> {

    private final Logger log = LoggerFactory.getLogger(EnquiryMaterialQueryService.class);

    private final EnquiryMaterialRepository enquiryMaterialRepository;

    private final EnquiryMaterialMapper enquiryMaterialMapper;

    public EnquiryMaterialQueryService(EnquiryMaterialRepository enquiryMaterialRepository, EnquiryMaterialMapper enquiryMaterialMapper) {
        this.enquiryMaterialRepository = enquiryMaterialRepository;
        this.enquiryMaterialMapper = enquiryMaterialMapper;
    }

    /**
     * Return a {@link List} of {@link EnquiryMaterialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnquiryMaterialDTO> findByCriteria(EnquiryMaterialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EnquiryMaterial> specification = createSpecification(criteria);
        return enquiryMaterialMapper.toDto(enquiryMaterialRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnquiryMaterialDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnquiryMaterialDTO> findByCriteria(EnquiryMaterialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EnquiryMaterial> specification = createSpecification(criteria);
        return enquiryMaterialRepository.findAll(specification, page)
            .map(enquiryMaterialMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnquiryMaterialCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EnquiryMaterial> specification = createSpecification(criteria);
        return enquiryMaterialRepository.count(specification);
    }

    /**
     * Function to convert {@link EnquiryMaterialCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EnquiryMaterial> createSpecification(EnquiryMaterialCriteria criteria) {
        Specification<EnquiryMaterial> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EnquiryMaterial_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EnquiryMaterial_.name));
            }
            if (criteria.getDimension() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDimension(), EnquiryMaterial_.dimension));
            }
            if (criteria.getMaterialId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaterialId(), EnquiryMaterial_.materialId));
            }
            if (criteria.getColor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getColor(), EnquiryMaterial_.color));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), EnquiryMaterial_.comments));
            }
            if (criteria.getEnquiryDetailsId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnquiryDetailsId(),
                    root -> root.join(EnquiryMaterial_.enquiryDetails, JoinType.LEFT).get(EnquiryDetails_.id)));
            }
        }
        return specification;
    }
}
