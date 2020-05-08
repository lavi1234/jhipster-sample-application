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

import com.mycompany.myapp.domain.UserProfile;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.UserProfileRepository;
import com.mycompany.myapp.service.dto.UserProfileCriteria;
import com.mycompany.myapp.service.dto.UserProfileDTO;
import com.mycompany.myapp.service.mapper.UserProfileMapper;

/**
 * Service for executing complex queries for {@link UserProfile} entities in the database.
 * The main input is a {@link UserProfileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserProfileDTO} or a {@link Page} of {@link UserProfileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserProfileQueryService extends QueryService<UserProfile> {

    private final Logger log = LoggerFactory.getLogger(UserProfileQueryService.class);

    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;

    public UserProfileQueryService(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    /**
     * Return a {@link List} of {@link UserProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserProfileDTO> findByCriteria(UserProfileCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserProfile> specification = createSpecification(criteria);
        return userProfileMapper.toDto(userProfileRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserProfileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserProfileDTO> findByCriteria(UserProfileCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserProfile> specification = createSpecification(criteria);
        return userProfileRepository.findAll(specification, page)
            .map(userProfileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserProfileCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserProfile> specification = createSpecification(criteria);
        return userProfileRepository.count(specification);
    }

    /**
     * Function to convert {@link UserProfileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserProfile> createSpecification(UserProfileCriteria criteria) {
        Specification<UserProfile> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserProfile_.id));
            }
            if (criteria.getSalutation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSalutation(), UserProfile_.salutation));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), UserProfile_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), UserProfile_.lastName));
            }
            if (criteria.getProfilePicture() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProfilePicture(), UserProfile_.profilePicture));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), UserProfile_.phoneNumber));
            }
            if (criteria.getUserType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserType(), UserProfile_.userType));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), UserProfile_.createdAt));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), UserProfile_.updatedAt));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(UserProfile_.user, JoinType.LEFT).get(User_.id)));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(UserProfile_.company, JoinType.LEFT).get(Company_.id)));
            }
            if (criteria.getUserCategoryMappingId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserCategoryMappingId(),
                    root -> root.join(UserProfile_.userCategoryMappings, JoinType.LEFT).get(UserCategoryMapping_.id)));
            }
        }
        return specification;
    }
}
