package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.TransactionHistory;
import com.mycompany.myapp.repository.TransactionHistoryRepository;
import com.mycompany.myapp.service.dto.TransactionHistoryDTO;
import com.mycompany.myapp.service.mapper.TransactionHistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TransactionHistory}.
 */
@Service
@Transactional
public class TransactionHistoryService {

    private final Logger log = LoggerFactory.getLogger(TransactionHistoryService.class);

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final TransactionHistoryMapper transactionHistoryMapper;

    public TransactionHistoryService(TransactionHistoryRepository transactionHistoryRepository, TransactionHistoryMapper transactionHistoryMapper) {
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.transactionHistoryMapper = transactionHistoryMapper;
    }

    /**
     * Save a transactionHistory.
     *
     * @param transactionHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    public TransactionHistoryDTO save(TransactionHistoryDTO transactionHistoryDTO) {
        log.debug("Request to save TransactionHistory : {}", transactionHistoryDTO);
        TransactionHistory transactionHistory = transactionHistoryMapper.toEntity(transactionHistoryDTO);
        transactionHistory = transactionHistoryRepository.save(transactionHistory);
        return transactionHistoryMapper.toDto(transactionHistory);
    }

    /**
     * Get all the transactionHistories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionHistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TransactionHistories");
        return transactionHistoryRepository.findAll(pageable)
            .map(transactionHistoryMapper::toDto);
    }

    /**
     * Get one transactionHistory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TransactionHistoryDTO> findOne(Long id) {
        log.debug("Request to get TransactionHistory : {}", id);
        return transactionHistoryRepository.findById(id)
            .map(transactionHistoryMapper::toDto);
    }

    /**
     * Delete the transactionHistory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TransactionHistory : {}", id);
        transactionHistoryRepository.deleteById(id);
    }
}
