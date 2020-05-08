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

import com.mycompany.myapp.domain.EnquiryDetails;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.EnquiryDetailsRepository;
import com.mycompany.myapp.service.dto.EnquiryDetailsCriteria;
import com.mycompany.myapp.service.dto.EnquiryDetailsDTO;
import com.mycompany.myapp.service.mapper.EnquiryDetailsMapper;

/**
 * Service for executing complex queries for {@link EnquiryDetails} entities in the database.
 * The main input is a {@link EnquiryDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnquiryDetailsDTO} or a {@link Page} of {@link EnquiryDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnquiryDetailsQueryService extends QueryService<EnquiryDetails> {

    private final Logger log = LoggerFactory.getLogger(EnquiryDetailsQueryService.class);

    private final EnquiryDetailsRepository enquiryDetailsRepository;

    private final EnquiryDetailsMapper enquiryDetailsMapper;

    public EnquiryDetailsQueryService(EnquiryDetailsRepository enquiryDetailsRepository, EnquiryDetailsMapper enquiryDetailsMapper) {
        this.enquiryDetailsRepository = enquiryDetailsRepository;
        this.enquiryDetailsMapper = enquiryDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link EnquiryDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnquiryDetailsDTO> findByCriteria(EnquiryDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EnquiryDetails> specification = createSpecification(criteria);
        return enquiryDetailsMapper.toDto(enquiryDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnquiryDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnquiryDetailsDTO> findByCriteria(EnquiryDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EnquiryDetails> specification = createSpecification(criteria);
        return enquiryDetailsRepository.findAll(specification, page)
            .map(enquiryDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnquiryDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EnquiryDetails> specification = createSpecification(criteria);
        return enquiryDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link EnquiryDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EnquiryDetails> createSpecification(EnquiryDetailsCriteria criteria) {
        Specification<EnquiryDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EnquiryDetails_.id));
            }
            if (criteria.getVersion() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getVersion(), EnquiryDetails_.version));
            }
            if (criteria.getEdition() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEdition(), EnquiryDetails_.edition));
            }
            if (criteria.getFormat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFormat(), EnquiryDetails_.format));
            }
            if (criteria.getDocuments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocuments(), EnquiryDetails_.documents));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), EnquiryDetails_.deliveryDate));
            }
            if (criteria.getRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemarks(), EnquiryDetails_.remarks));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), EnquiryDetails_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), EnquiryDetails_.updatedAt));
            }
            if (criteria.getEnquiryId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnquiryId(),
                    root -> root.join(EnquiryDetails_.enquiry, JoinType.LEFT).get(Enquiry_.id)));
            }
            if (criteria.getPrintId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrintId(),
                    root -> root.join(EnquiryDetails_.print, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getFinishingId() != null) {
                specification = specification.and(buildSpecification(criteria.getFinishingId(),
                    root -> root.join(EnquiryDetails_.finishing, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getHandlingId() != null) {
                specification = specification.and(buildSpecification(criteria.getHandlingId(),
                    root -> root.join(EnquiryDetails_.handling, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getPackagingId() != null) {
                specification = specification.and(buildSpecification(criteria.getPackagingId(),
                    root -> root.join(EnquiryDetails_.packaging, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getCreatedById() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedById(),
                    root -> root.join(EnquiryDetails_.createdBy, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getOfferId() != null) {
                specification = specification.and(buildSpecification(criteria.getOfferId(),
                    root -> root.join(EnquiryDetails_.offer, JoinType.LEFT).get(Offer_.id)));
            }
        }
        return specification;
    }
}
