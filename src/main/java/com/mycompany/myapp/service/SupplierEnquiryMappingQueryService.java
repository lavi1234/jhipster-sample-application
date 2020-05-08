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

import com.mycompany.myapp.domain.SupplierEnquiryMapping;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.SupplierEnquiryMappingRepository;
import com.mycompany.myapp.service.dto.SupplierEnquiryMappingCriteria;
import com.mycompany.myapp.service.dto.SupplierEnquiryMappingDTO;
import com.mycompany.myapp.service.mapper.SupplierEnquiryMappingMapper;

/**
 * Service for executing complex queries for {@link SupplierEnquiryMapping} entities in the database.
 * The main input is a {@link SupplierEnquiryMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SupplierEnquiryMappingDTO} or a {@link Page} of {@link SupplierEnquiryMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierEnquiryMappingQueryService extends QueryService<SupplierEnquiryMapping> {

    private final Logger log = LoggerFactory.getLogger(SupplierEnquiryMappingQueryService.class);

    private final SupplierEnquiryMappingRepository supplierEnquiryMappingRepository;

    private final SupplierEnquiryMappingMapper supplierEnquiryMappingMapper;

    public SupplierEnquiryMappingQueryService(SupplierEnquiryMappingRepository supplierEnquiryMappingRepository, SupplierEnquiryMappingMapper supplierEnquiryMappingMapper) {
        this.supplierEnquiryMappingRepository = supplierEnquiryMappingRepository;
        this.supplierEnquiryMappingMapper = supplierEnquiryMappingMapper;
    }

    /**
     * Return a {@link List} of {@link SupplierEnquiryMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SupplierEnquiryMappingDTO> findByCriteria(SupplierEnquiryMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SupplierEnquiryMapping> specification = createSpecification(criteria);
        return supplierEnquiryMappingMapper.toDto(supplierEnquiryMappingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SupplierEnquiryMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierEnquiryMappingDTO> findByCriteria(SupplierEnquiryMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SupplierEnquiryMapping> specification = createSpecification(criteria);
        return supplierEnquiryMappingRepository.findAll(specification, page)
            .map(supplierEnquiryMappingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplierEnquiryMappingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SupplierEnquiryMapping> specification = createSpecification(criteria);
        return supplierEnquiryMappingRepository.count(specification);
    }

    /**
     * Function to convert {@link SupplierEnquiryMappingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SupplierEnquiryMapping> createSpecification(SupplierEnquiryMappingCriteria criteria) {
        Specification<SupplierEnquiryMapping> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SupplierEnquiryMapping_.id));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), SupplierEnquiryMapping_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), SupplierEnquiryMapping_.updatedAt));
            }
            if (criteria.getEnquiryId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnquiryId(),
                    root -> root.join(SupplierEnquiryMapping_.enquiry, JoinType.LEFT).get(Enquiry_.id)));
            }
            if (criteria.getSupplierId() != null) {
                specification = specification.and(buildSpecification(criteria.getSupplierId(),
                    root -> root.join(SupplierEnquiryMapping_.supplier, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getCreatedById() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedById(),
                    root -> root.join(SupplierEnquiryMapping_.createdBy, JoinType.LEFT).get(UserProfile_.id)));
            }
        }
        return specification;
    }
}
