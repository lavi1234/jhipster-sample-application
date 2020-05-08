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

import com.mycompany.myapp.domain.TradingHours;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.TradingHoursRepository;
import com.mycompany.myapp.service.dto.TradingHoursCriteria;
import com.mycompany.myapp.service.dto.TradingHoursDTO;
import com.mycompany.myapp.service.mapper.TradingHoursMapper;

/**
 * Service for executing complex queries for {@link TradingHours} entities in the database.
 * The main input is a {@link TradingHoursCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TradingHoursDTO} or a {@link Page} of {@link TradingHoursDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TradingHoursQueryService extends QueryService<TradingHours> {

    private final Logger log = LoggerFactory.getLogger(TradingHoursQueryService.class);

    private final TradingHoursRepository tradingHoursRepository;

    private final TradingHoursMapper tradingHoursMapper;

    public TradingHoursQueryService(TradingHoursRepository tradingHoursRepository, TradingHoursMapper tradingHoursMapper) {
        this.tradingHoursRepository = tradingHoursRepository;
        this.tradingHoursMapper = tradingHoursMapper;
    }

    /**
     * Return a {@link List} of {@link TradingHoursDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TradingHoursDTO> findByCriteria(TradingHoursCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TradingHours> specification = createSpecification(criteria);
        return tradingHoursMapper.toDto(tradingHoursRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TradingHoursDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TradingHoursDTO> findByCriteria(TradingHoursCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TradingHours> specification = createSpecification(criteria);
        return tradingHoursRepository.findAll(specification, page)
            .map(tradingHoursMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TradingHoursCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TradingHours> specification = createSpecification(criteria);
        return tradingHoursRepository.count(specification);
    }

    /**
     * Function to convert {@link TradingHoursCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TradingHours> createSpecification(TradingHoursCriteria criteria) {
        Specification<TradingHours> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TradingHours_.id));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDay(), TradingHours_.day));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartTime(), TradingHours_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndTime(), TradingHours_.endTime));
            }
            if (criteria.getCompanyId() != null) {
                specification = specification.and(buildSpecification(criteria.getCompanyId(),
                    root -> root.join(TradingHours_.company, JoinType.LEFT).get(Company_.id)));
            }
        }
        return specification;
    }
}
