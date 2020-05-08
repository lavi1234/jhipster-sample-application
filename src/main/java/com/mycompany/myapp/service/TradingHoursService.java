package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TradingHours;
import com.mycompany.myapp.repository.TradingHoursRepository;
import com.mycompany.myapp.service.dto.TradingHoursDTO;
import com.mycompany.myapp.service.mapper.TradingHoursMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TradingHours}.
 */
@Service
@Transactional
public class TradingHoursService {

    private final Logger log = LoggerFactory.getLogger(TradingHoursService.class);

    private final TradingHoursRepository tradingHoursRepository;

    private final TradingHoursMapper tradingHoursMapper;

    public TradingHoursService(TradingHoursRepository tradingHoursRepository, TradingHoursMapper tradingHoursMapper) {
        this.tradingHoursRepository = tradingHoursRepository;
        this.tradingHoursMapper = tradingHoursMapper;
    }

    /**
     * Save a tradingHours.
     *
     * @param tradingHoursDTO the entity to save.
     * @return the persisted entity.
     */
    public TradingHoursDTO save(TradingHoursDTO tradingHoursDTO) {
        log.debug("Request to save TradingHours : {}", tradingHoursDTO);
        TradingHours tradingHours = tradingHoursMapper.toEntity(tradingHoursDTO);
        tradingHours = tradingHoursRepository.save(tradingHours);
        return tradingHoursMapper.toDto(tradingHours);
    }

    /**
     * Get all the tradingHours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TradingHoursDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TradingHours");
        return tradingHoursRepository.findAll(pageable)
            .map(tradingHoursMapper::toDto);
    }

    /**
     * Get one tradingHours by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TradingHoursDTO> findOne(Long id) {
        log.debug("Request to get TradingHours : {}", id);
        return tradingHoursRepository.findById(id)
            .map(tradingHoursMapper::toDto);
    }

    /**
     * Delete the tradingHours by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TradingHours : {}", id);
        tradingHoursRepository.deleteById(id);
    }
}
