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

import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.CompanyRepository;
import com.mycompany.myapp.service.dto.CompanyCriteria;
import com.mycompany.myapp.service.dto.CompanyDTO;
import com.mycompany.myapp.service.mapper.CompanyMapper;

/**
 * Service for executing complex queries for {@link Company} entities in the database.
 * The main input is a {@link CompanyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CompanyDTO} or a {@link Page} of {@link CompanyDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CompanyQueryService extends QueryService<Company> {

    private final Logger log = LoggerFactory.getLogger(CompanyQueryService.class);

    private final CompanyRepository companyRepository;

    private final CompanyMapper companyMapper;

    public CompanyQueryService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }

    /**
     * Return a {@link List} of {@link CompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CompanyDTO> findByCriteria(CompanyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyMapper.toDto(companyRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CompanyDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CompanyDTO> findByCriteria(CompanyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.findAll(specification, page)
            .map(companyMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CompanyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Company> specification = createSpecification(criteria);
        return companyRepository.count(specification);
    }

    /**
     * Function to convert {@link CompanyCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Company> createSpecification(CompanyCriteria criteria) {
        Specification<Company> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Company_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Company_.name));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Company_.email));
            }
            if (criteria.getTermsConditions() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTermsConditions(), Company_.termsConditions));
            }
            if (criteria.getAboutUs() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAboutUs(), Company_.aboutUs));
            }
            if (criteria.getCatalogue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCatalogue(), Company_.catalogue));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Company_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), Company_.updatedAt));
            }
            if (criteria.getAddressId() != null) {
                specification = specification.and(buildSpecification(criteria.getAddressId(),
                    root -> root.join(Company_.address, JoinType.LEFT).get(Address_.id)));
            }
            if (criteria.getCreatedById() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedById(),
                    root -> root.join(Company_.createdBy, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getUpdatedById() != null) {
                specification = specification.and(buildSpecification(criteria.getUpdatedById(),
                    root -> root.join(Company_.updatedBy, JoinType.LEFT).get(UserProfile_.id)));
            }
        }
        return specification;
    }
}
