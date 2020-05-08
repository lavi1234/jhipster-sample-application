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

import com.mycompany.myapp.domain.UserCategoryMapping;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.UserCategoryMappingRepository;
import com.mycompany.myapp.service.dto.UserCategoryMappingCriteria;
import com.mycompany.myapp.service.dto.UserCategoryMappingDTO;
import com.mycompany.myapp.service.mapper.UserCategoryMappingMapper;

/**
 * Service for executing complex queries for {@link UserCategoryMapping} entities in the database.
 * The main input is a {@link UserCategoryMappingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserCategoryMappingDTO} or a {@link Page} of {@link UserCategoryMappingDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserCategoryMappingQueryService extends QueryService<UserCategoryMapping> {

    private final Logger log = LoggerFactory.getLogger(UserCategoryMappingQueryService.class);

    private final UserCategoryMappingRepository userCategoryMappingRepository;

    private final UserCategoryMappingMapper userCategoryMappingMapper;

    public UserCategoryMappingQueryService(UserCategoryMappingRepository userCategoryMappingRepository, UserCategoryMappingMapper userCategoryMappingMapper) {
        this.userCategoryMappingRepository = userCategoryMappingRepository;
        this.userCategoryMappingMapper = userCategoryMappingMapper;
    }

    /**
     * Return a {@link List} of {@link UserCategoryMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserCategoryMappingDTO> findByCriteria(UserCategoryMappingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserCategoryMapping> specification = createSpecification(criteria);
        return userCategoryMappingMapper.toDto(userCategoryMappingRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserCategoryMappingDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserCategoryMappingDTO> findByCriteria(UserCategoryMappingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserCategoryMapping> specification = createSpecification(criteria);
        return userCategoryMappingRepository.findAll(specification, page)
            .map(userCategoryMappingMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserCategoryMappingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserCategoryMapping> specification = createSpecification(criteria);
        return userCategoryMappingRepository.count(specification);
    }

    /**
     * Function to convert {@link UserCategoryMappingCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserCategoryMapping> createSpecification(UserCategoryMappingCriteria criteria) {
        Specification<UserCategoryMapping> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserCategoryMapping_.id));
            }
            if (criteria.getUserProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserProfileId(),
                    root -> root.join(UserCategoryMapping_.userProfiles, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(),
                    root -> root.join(UserCategoryMapping_.categories, JoinType.LEFT).get(Category_.id)));
            }
        }
        return specification;
    }
}
