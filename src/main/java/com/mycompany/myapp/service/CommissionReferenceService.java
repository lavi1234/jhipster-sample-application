package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CommissionReference;
import com.mycompany.myapp.repository.CommissionReferenceRepository;
import com.mycompany.myapp.service.dto.CommissionReferenceDTO;
import com.mycompany.myapp.service.mapper.CommissionReferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CommissionReference}.
 */
@Service
@Transactional
public class CommissionReferenceService {

    private final Logger log = LoggerFactory.getLogger(CommissionReferenceService.class);

    private final CommissionReferenceRepository commissionReferenceRepository;

    private final CommissionReferenceMapper commissionReferenceMapper;

    public CommissionReferenceService(CommissionReferenceRepository commissionReferenceRepository, CommissionReferenceMapper commissionReferenceMapper) {
        this.commissionReferenceRepository = commissionReferenceRepository;
        this.commissionReferenceMapper = commissionReferenceMapper;
    }

    /**
     * Save a commissionReference.
     *
     * @param commissionReferenceDTO the entity to save.
     * @return the persisted entity.
     */
    public CommissionReferenceDTO save(CommissionReferenceDTO commissionReferenceDTO) {
        log.debug("Request to save CommissionReference : {}", commissionReferenceDTO);
        CommissionReference commissionReference = commissionReferenceMapper.toEntity(commissionReferenceDTO);
        commissionReference = commissionReferenceRepository.save(commissionReference);
        return commissionReferenceMapper.toDto(commissionReference);
    }

    /**
     * Get all the commissionReferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CommissionReferenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CommissionReferences");
        return commissionReferenceRepository.findAll(pageable)
            .map(commissionReferenceMapper::toDto);
    }

    /**
     * Get one commissionReference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CommissionReferenceDTO> findOne(Long id) {
        log.debug("Request to get CommissionReference : {}", id);
        return commissionReferenceRepository.findById(id)
            .map(commissionReferenceMapper::toDto);
    }

    /**
     * Delete the commissionReference by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CommissionReference : {}", id);
        commissionReferenceRepository.deleteById(id);
    }
}
