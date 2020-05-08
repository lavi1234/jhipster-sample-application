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

import com.mycompany.myapp.domain.Message;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.MessageRepository;
import com.mycompany.myapp.service.dto.MessageCriteria;
import com.mycompany.myapp.service.dto.MessageDTO;
import com.mycompany.myapp.service.mapper.MessageMapper;

/**
 * Service for executing complex queries for {@link Message} entities in the database.
 * The main input is a {@link MessageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MessageDTO} or a {@link Page} of {@link MessageDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MessageQueryService extends QueryService<Message> {

    private final Logger log = LoggerFactory.getLogger(MessageQueryService.class);

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    public MessageQueryService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    /**
     * Return a {@link List} of {@link MessageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MessageDTO> findByCriteria(MessageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Message> specification = createSpecification(criteria);
        return messageMapper.toDto(messageRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MessageDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageDTO> findByCriteria(MessageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Message> specification = createSpecification(criteria);
        return messageRepository.findAll(specification, page)
            .map(messageMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MessageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Message> specification = createSpecification(criteria);
        return messageRepository.count(specification);
    }

    /**
     * Function to convert {@link MessageCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Message> createSpecification(MessageCriteria criteria) {
        Specification<Message> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Message_.id));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), Message_.subject));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), Message_.message));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Message_.createdAt));
            }
            if (criteria.getApiResponse() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApiResponse(), Message_.apiResponse));
            }
            if (criteria.getDiscussionType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDiscussionType(), Message_.discussionType));
            }
            if (criteria.getReadStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getReadStatus(), Message_.readStatus));
            }
            if (criteria.getFromId() != null) {
                specification = specification.and(buildSpecification(criteria.getFromId(),
                    root -> root.join(Message_.from, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getToId() != null) {
                specification = specification.and(buildSpecification(criteria.getToId(),
                    root -> root.join(Message_.to, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getCreatedById() != null) {
                specification = specification.and(buildSpecification(criteria.getCreatedById(),
                    root -> root.join(Message_.createdBy, JoinType.LEFT).get(UserProfile_.id)));
            }
            if (criteria.getEnquiryId() != null) {
                specification = specification.and(buildSpecification(criteria.getEnquiryId(),
                    root -> root.join(Message_.enquiry, JoinType.LEFT).get(Enquiry_.id)));
            }
            if (criteria.getOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderId(),
                    root -> root.join(Message_.order, JoinType.LEFT).get(Order_.id)));
            }
            if (criteria.getOfferId() != null) {
                specification = specification.and(buildSpecification(criteria.getOfferId(),
                    root -> root.join(Message_.offer, JoinType.LEFT).get(Offer_.id)));
            }
            if (criteria.getReplyMessageId() != null) {
                specification = specification.and(buildSpecification(criteria.getReplyMessageId(),
                    root -> root.join(Message_.replyMessage, JoinType.LEFT).get(Message_.id)));
            }
        }
        return specification;
    }
}
