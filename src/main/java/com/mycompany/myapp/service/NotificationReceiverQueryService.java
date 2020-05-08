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

import com.mycompany.myapp.domain.NotificationReceiver;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.NotificationReceiverRepository;
import com.mycompany.myapp.service.dto.NotificationReceiverCriteria;
import com.mycompany.myapp.service.dto.NotificationReceiverDTO;
import com.mycompany.myapp.service.mapper.NotificationReceiverMapper;

/**
 * Service for executing complex queries for {@link NotificationReceiver} entities in the database.
 * The main input is a {@link NotificationReceiverCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NotificationReceiverDTO} or a {@link Page} of {@link NotificationReceiverDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NotificationReceiverQueryService extends QueryService<NotificationReceiver> {

    private final Logger log = LoggerFactory.getLogger(NotificationReceiverQueryService.class);

    private final NotificationReceiverRepository notificationReceiverRepository;

    private final NotificationReceiverMapper notificationReceiverMapper;

    public NotificationReceiverQueryService(NotificationReceiverRepository notificationReceiverRepository, NotificationReceiverMapper notificationReceiverMapper) {
        this.notificationReceiverRepository = notificationReceiverRepository;
        this.notificationReceiverMapper = notificationReceiverMapper;
    }

    /**
     * Return a {@link List} of {@link NotificationReceiverDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NotificationReceiverDTO> findByCriteria(NotificationReceiverCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NotificationReceiver> specification = createSpecification(criteria);
        return notificationReceiverMapper.toDto(notificationReceiverRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NotificationReceiverDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationReceiverDTO> findByCriteria(NotificationReceiverCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NotificationReceiver> specification = createSpecification(criteria);
        return notificationReceiverRepository.findAll(specification, page)
            .map(notificationReceiverMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NotificationReceiverCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NotificationReceiver> specification = createSpecification(criteria);
        return notificationReceiverRepository.count(specification);
    }

    /**
     * Function to convert {@link NotificationReceiverCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NotificationReceiver> createSpecification(NotificationReceiverCriteria criteria) {
        Specification<NotificationReceiver> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NotificationReceiver_.id));
            }
            if (criteria.getReadStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getReadStatus(), NotificationReceiver_.readStatus));
            }
            if (criteria.getUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), NotificationReceiver_.updatedAt));
            }
            if (criteria.getNotificationId() != null) {
                specification = specification.and(buildSpecification(criteria.getNotificationId(),
                    root -> root.join(NotificationReceiver_.notification, JoinType.LEFT).get(Notification_.id)));
            }
            if (criteria.getUserProfileId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserProfileId(),
                    root -> root.join(NotificationReceiver_.userProfile, JoinType.LEFT).get(UserProfile_.id)));
            }
        }
        return specification;
    }
}
