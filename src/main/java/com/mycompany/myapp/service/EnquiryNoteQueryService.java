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

import com.mycompany.myapp.domain.EnquiryNote;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.EnquiryNoteRepository;
import com.mycompany.myapp.service.dto.EnquiryNoteCriteria;
import com.mycompany.myapp.service.dto.EnquiryNoteDTO;
import com.mycompany.myapp.service.mapper.EnquiryNoteMapper;

/**
 * Service for executing complex queries for {@link EnquiryNote} entities in the database.
 * The main input is a {@link EnquiryNoteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EnquiryNoteDTO} or a {@link Page} of {@link EnquiryNoteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EnquiryNoteQueryService extends QueryService<EnquiryNote> {

    private final Logger log = LoggerFactory.getLogger(EnquiryNoteQueryService.class);

    private final EnquiryNoteRepository enquiryNoteRepository;

    private final EnquiryNoteMapper enquiryNoteMapper;

    public EnquiryNoteQueryService(EnquiryNoteRepository enquiryNoteRepository, EnquiryNoteMapper enquiryNoteMapper) {
        this.enquiryNoteRepository = enquiryNoteRepository;
        this.enquiryNoteMapper = enquiryNoteMapper;
    }

    /**
     * Return a {@link List} of {@link EnquiryNoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EnquiryNoteDTO> findByCriteria(EnquiryNoteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EnquiryNote> specification = createSpecification(criteria);
        return enquiryNoteMapper.toDto(enquiryNoteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EnquiryNoteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EnquiryNoteDTO> findByCriteria(EnquiryNoteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EnquiryNote> specification = createSpecification(criteria);
        return enquiryNoteRepository.findAll(specification, page)
            .map(enquiryNoteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EnquiryNoteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EnquiryNote> specification = createSpecification(criteria);
        return enquiryNoteRepository.count(specification);
    }

    /**
     * Function to convert {@link EnquiryNoteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EnquiryNote> createSpecification(EnquiryNoteCriteria criteria) {
        Specification<EnquiryNote> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EnquiryNote_.id));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), EnquiryNote_.note));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), EnquiryNote_.createdAt));
            }
            if (criteria.getEnquiryDetailsId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnquiryDetailsId(),
                    root -> root.join(EnquiryNote_.enquiryDetails, JoinType.LEFT).get(EnquiryDetails_.id)));
            }
        }
        return specification;
    }
}
