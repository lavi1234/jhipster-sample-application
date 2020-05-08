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

import com.mycompany.myapp.domain.Enquiry;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.EnquiryRepository;
import com.mycompany.myapp.service.dto.EnquiryCriteria;
import com.mycompany.myapp.service.dto.EnquiryDTO;
import com.mycompany.myapp.service.mapper.EnquiryMapper;

/**
 * Service for executing complex queries for {@link Enquiry} entities in the database.
 * The main input is a {@link EnquiryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnquiryDTO} or a {@link Page} of {@link EnquiryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnquiryQueryService extends QueryService<Enquiry> {

    private final Logger log = LoggerFactory.getLogger(EnquiryQueryService.class);

    private final EnquiryRepository enquiryRepository;

    private final EnquiryMapper enquiryMapper;

    public EnquiryQueryService(EnquiryRepository enquiryRepository, EnquiryMapper enquiryMapper) {
        this.enquiryRepository = enquiryRepository;
        this.enquiryMapper = enquiryMapper;
    }

    /**
     * Return a {@link List} of {@link EnquiryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnquiryDTO> findByCriteria(EnquiryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Enquiry> specification = createSpecification(criteria);
        return enquiryMapper.toDto(enquiryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnquiryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnquiryDTO> findByCriteria(EnquiryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Enquiry> specification = createSpecification(criteria);
        return enquiryRepository.findAll(specification, page)
            .map(enquiryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnquiryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Enquiry> specification = createSpecification(criteria);
        return enquiryRepository.count(specification);
    }

    /**
     * Function to convert {@link EnquiryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Enquiry> createSpecification(EnquiryCriteria criteria) {
        Specification<Enquiry> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Enquiry_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Enquiry_.description));
            }
            if (criteria.getDeliveryTerms() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDeliveryTerms(), Enquiry_.deliveryTerms));
            }
            if (criteria.getOfferTaxtUntil() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOfferTaxtUntil(), Enquiry_.offerTaxtUntil));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Enquiry_.status));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(Enquiry_.product, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getDeliveryAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeliveryAddressId(),
                    root -> root.join(Enquiry_.deliveryAddress, JoinType.LEFT).get(Address_.id)));
            }
        }
        return specification;
    }
}
