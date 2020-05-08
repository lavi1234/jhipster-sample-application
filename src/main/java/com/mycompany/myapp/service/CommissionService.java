package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Commission;
import com.mycompany.myapp.repository.CommissionRepository;
import com.mycompany.myapp.service.dto.CommissionDTO;
import com.mycompany.myapp.service.mapper.CommissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Commission}.
 */
@Service
@Transactional
public class CommissionService {

    private final Logger log = LoggerFactory.getLogger(CommissionService.class);

    private final CommissionRepository commissionRepository;

    private final CommissionMapper commissionMapper;

    public CommissionService(CommissionRepository commissionRepository, CommissionMapper commissionMapper) {
        this.commissionRepository = commissionRepository;
        this.commissionMapper = commissionMapper;
    }

    /**
     * Save a commission.
     *
     * @param commissionDTO the entity to save.
     * @return the persisted entity.
     */
    public CommissionDTO save(CommissionDTO commissionDTO) {
        log.debug("Request to save Commission : {}", commissionDTO);
        Commission commission = commissionMapper.toEntity(commissionDTO);
        commission = commissionRepository.save(commission);
        return commissionMapper.toDto(commission);
    }

    /**
     * Get all the commissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Commissions");
        return commissionRepository.findAll(pageable)
            .map(commissionMapper::toDto);
    }

    /**
     * Get one commission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommissionDTO> findOne(Long id) {
        log.debug("Request to get Commission : {}", id);
        return commissionRepository.findById(id)
            .map(commissionMapper::toDto);
    }

    /**
     * Delete the commission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Commission : {}", id);
        commissionRepository.deleteById(id);
    }
}
