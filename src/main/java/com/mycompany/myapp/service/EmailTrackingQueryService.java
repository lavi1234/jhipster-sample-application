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

import com.mycompany.myapp.domain.EmailTracking;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.EmailTrackingRepository;
import com.mycompany.myapp.service.dto.EmailTrackingCriteria;
import com.mycompany.myapp.service.dto.EmailTrackingDTO;
import com.mycompany.myapp.service.mapper.EmailTrackingMapper;

/**
 * Service for executing complex queries for {@link EmailTracking} entities in the database.
 * The main input is a {@link EmailTrackingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmailTrackingDTO} or a {@link Page} of {@link EmailTrackingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmailTrackingQueryService extends QueryService<EmailTracking> {

    private final Logger log = LoggerFactory.getLogger(EmailTrackingQueryService.class);

    private final EmailTrackingRepository emailTrackingRepository;

    private final EmailTrackingMapper emailTrackingMapper;

    public EmailTrackingQueryService(EmailTrackingRepository emailTrackingRepository, EmailTrackingMapper emailTrackingMapper) {
        this.emailTrackingRepository = emailTrackingRepository;
        this.emailTrackingMapper = emailTrackingMapper;
    }

    /**
     * Return a {@link List} of {@link EmailTrackingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmailTrackingDTO> findByCriteria(EmailTrackingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmailTracking> specification = createSpecification(criteria);
        return emailTrackingMapper.toDto(emailTrackingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmailTrackingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmailTrackingDTO> findByCriteria(EmailTrackingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmailTracking> specification = createSpecification(criteria);
        return emailTrackingRepository.findAll(specification, page)
            .map(emailTrackingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmailTrackingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmailTracking> specification = createSpecification(criteria);
        return emailTrackingRepository.count(specification);
    }

    /**
     * Function to convert {@link EmailTrackingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmailTracking> createSpecification(EmailTrackingCriteria criteria) {
        Specification<EmailTracking> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmailTracking_.id));
            }
            if (criteria.getToEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getToEmail(), EmailTracking_.toEmail));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), EmailTracking_.subject));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), EmailTracking_.message));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), EmailTracking_.type));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), EmailTracking_.createdAt));
            }
            if (criteria.getReceiverId() != null) {
                specification = specification.and(buildSpecification(criteria.getReceiverId(),
                    root -> root.join(EmailTracking_.receiver, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getCreatedById() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedById(),
                    root -> root.join(EmailTracking_.createdBy, JoinType.LEFT).get(UserProfile_.id)));
            }
        }
        return specification;
    }
}
