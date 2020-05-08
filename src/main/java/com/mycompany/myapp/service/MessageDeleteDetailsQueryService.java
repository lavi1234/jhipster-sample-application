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

import com.mycompany.myapp.domain.MessageDeleteDetails;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.MessageDeleteDetailsRepository;
import com.mycompany.myapp.service.dto.MessageDeleteDetailsCriteria;
import com.mycompany.myapp.service.dto.MessageDeleteDetailsDTO;
import com.mycompany.myapp.service.mapper.MessageDeleteDetailsMapper;

/**
 * Service for executing complex queries for {@link MessageDeleteDetails} entities in the database.
 * The main input is a {@link MessageDeleteDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MessageDeleteDetailsDTO} or a {@link Page} of {@link MessageDeleteDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MessageDeleteDetailsQueryService extends QueryService<MessageDeleteDetails> {

    private final Logger log = LoggerFactory.getLogger(MessageDeleteDetailsQueryService.class);

    private final MessageDeleteDetailsRepository messageDeleteDetailsRepository;

    private final MessageDeleteDetailsMapper messageDeleteDetailsMapper;

    public MessageDeleteDetailsQueryService(MessageDeleteDetailsRepository messageDeleteDetailsRepository, MessageDeleteDetailsMapper messageDeleteDetailsMapper) {
        this.messageDeleteDetailsRepository = messageDeleteDetailsRepository;
        this.messageDeleteDetailsMapper = messageDeleteDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link MessageDeleteDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MessageDeleteDetailsDTO> findByCriteria(MessageDeleteDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MessageDeleteDetails> specification = createSpecification(criteria);
        return messageDeleteDetailsMapper.toDto(messageDeleteDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MessageDeleteDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageDeleteDetailsDTO> findByCriteria(MessageDeleteDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MessageDeleteDetails> specification = createSpecification(criteria);
        return messageDeleteDetailsRepository.findAll(specification, page)
            .map(messageDeleteDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MessageDeleteDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MessageDeleteDetails> specification = createSpecification(criteria);
        return messageDeleteDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link MessageDeleteDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MessageDeleteDetails> createSpecification(MessageDeleteDetailsCriteria criteria) {
        Specification<MessageDeleteDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MessageDeleteDetails_.id));
            }
            if (criteria.getDeletedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeletedAt(), MessageDeleteDetails_.deletedAt));
            }
            if (criteria.getMessageId() != null) {
                specification = specification.and(buildSpecification(criteria.getMessageId(),
                    root -> root.join(MessageDeleteDetails_.message, JoinType.LEFT).get(Message_.id)));
            }
            if (criteria.getDeletedById() != null) {
                specification = specification.and(buildSpecification(criteria.getDeletedById(),
                    root -> root.join(MessageDeleteDetails_.deletedBy, JoinType.LEFT).get(UserProfile_.id)));
            }
        }
        return specification;
    }
}
