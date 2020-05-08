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

import com.mycompany.myapp.domain.PaymentDetails;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.PaymentDetailsRepository;
import com.mycompany.myapp.service.dto.PaymentDetailsCriteria;
import com.mycompany.myapp.service.dto.PaymentDetailsDTO;
import com.mycompany.myapp.service.mapper.PaymentDetailsMapper;

/**
 * Service for executing complex queries for {@link PaymentDetails} entities in the database.
 * The main input is a {@link PaymentDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentDetailsDTO} or a {@link Page} of {@link PaymentDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentDetailsQueryService extends QueryService<PaymentDetails> {

    private final Logger log = LoggerFactory.getLogger(PaymentDetailsQueryService.class);

    private final PaymentDetailsRepository paymentDetailsRepository;

    private final PaymentDetailsMapper paymentDetailsMapper;

    public PaymentDetailsQueryService(PaymentDetailsRepository paymentDetailsRepository, PaymentDetailsMapper paymentDetailsMapper) {
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.paymentDetailsMapper = paymentDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link PaymentDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentDetailsDTO> findByCriteria(PaymentDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PaymentDetails> specification = createSpecification(criteria);
        return paymentDetailsMapper.toDto(paymentDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentDetailsDTO> findByCriteria(PaymentDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PaymentDetails> specification = createSpecification(criteria);
        return paymentDetailsRepository.findAll(specification, page)
            .map(paymentDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PaymentDetails> specification = createSpecification(criteria);
        return paymentDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PaymentDetails> createSpecification(PaymentDetailsCriteria criteria) {
        Specification<PaymentDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PaymentDetails_.id));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), PaymentDetails_.bankName));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), PaymentDetails_.accountNumber));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), PaymentDetails_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), PaymentDetails_.updatedAt));
            }
            if (criteria.getUserProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserProfileId(),
                    root -> root.join(PaymentDetails_.userProfile, JoinType.LEFT).get(UserProfile_.id)));
            }
        }
        return specification;
    }
}
