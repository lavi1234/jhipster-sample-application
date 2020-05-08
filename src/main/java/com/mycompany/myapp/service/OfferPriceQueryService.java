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

import com.mycompany.myapp.domain.OfferPrice;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.OfferPriceRepository;
import com.mycompany.myapp.service.dto.OfferPriceCriteria;
import com.mycompany.myapp.service.dto.OfferPriceDTO;
import com.mycompany.myapp.service.mapper.OfferPriceMapper;

/**
 * Service for executing complex queries for {@link OfferPrice} entities in the database.
 * The main input is a {@link OfferPriceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OfferPriceDTO} or a {@link Page} of {@link OfferPriceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OfferPriceQueryService extends QueryService<OfferPrice> {

    private final Logger log = LoggerFactory.getLogger(OfferPriceQueryService.class);

    private final OfferPriceRepository offerPriceRepository;

    private final OfferPriceMapper offerPriceMapper;

    public OfferPriceQueryService(OfferPriceRepository offerPriceRepository, OfferPriceMapper offerPriceMapper) {
        this.offerPriceRepository = offerPriceRepository;
        this.offerPriceMapper = offerPriceMapper;
    }

    /**
     * Return a {@link List} of {@link OfferPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OfferPriceDTO> findByCriteria(OfferPriceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OfferPrice> specification = createSpecification(criteria);
        return offerPriceMapper.toDto(offerPriceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OfferPriceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OfferPriceDTO> findByCriteria(OfferPriceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OfferPrice> specification = createSpecification(criteria);
        return offerPriceRepository.findAll(specification, page)
            .map(offerPriceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OfferPriceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OfferPrice> specification = createSpecification(criteria);
        return offerPriceRepository.count(specification);
    }

    /**
     * Function to convert {@link OfferPriceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OfferPrice> createSpecification(OfferPriceCriteria criteria) {
        Specification<OfferPrice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OfferPrice_.id));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), OfferPrice_.price));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), OfferPrice_.createdAt));
            }
            if (criteria.getFinishingDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinishingDate(), OfferPrice_.finishingDate));
            }
            if (criteria.getOfferId() != null) {
                specification = specification.and(buildSpecification(criteria.getOfferId(),
                    root -> root.join(OfferPrice_.offer, JoinType.LEFT).get(Offer_.id)));
            }
            if (criteria.getEnquiryId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnquiryId(),
                    root -> root.join(OfferPrice_.enquiry, JoinType.LEFT).get(Enquiry_.id)));
            }
            if (criteria.getEnquiryDetailsId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnquiryDetailsId(),
                    root -> root.join(OfferPrice_.enquiryDetails, JoinType.LEFT).get(EnquiryDetails_.id)));
            }
        }
        return specification;
    }
}
