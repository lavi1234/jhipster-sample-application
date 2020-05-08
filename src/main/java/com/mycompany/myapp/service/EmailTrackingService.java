package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EmailTracking;
import com.mycompany.myapp.repository.EmailTrackingRepository;
import com.mycompany.myapp.service.dto.EmailTrackingDTO;
import com.mycompany.myapp.service.mapper.EmailTrackingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmailTracking}.
 */
@Service
@Transactional
public class EmailTrackingService {

    private final Logger log = LoggerFactory.getLogger(EmailTrackingService.class);

    private final EmailTrackingRepository emailTrackingRepository;

    private final EmailTrackingMapper emailTrackingMapper;

    public EmailTrackingService(EmailTrackingRepository emailTrackingRepository, EmailTrackingMapper emailTrackingMapper) {
        this.emailTrackingRepository = emailTrackingRepository;
        this.emailTrackingMapper = emailTrackingMapper;
    }

    /**
     * Save a emailTracking.
     *
     * @param emailTrackingDTO the entity to save.
     * @return the persisted entity.
     */
    public EmailTrackingDTO save(EmailTrackingDTO emailTrackingDTO) {
        log.debug("Request to save EmailTracking : {}", emailTrackingDTO);
        EmailTracking emailTracking = emailTrackingMapper.toEntity(emailTrackingDTO);
        emailTracking = emailTrackingRepository.save(emailTracking);
        return emailTrackingMapper.toDto(emailTracking);
    }

    /**
     * Get all the emailTrackings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmailTrackingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailTrackings");
        return emailTrackingRepository.findAll(pageable)
            .map(emailTrackingMapper::toDto);
    }

    /**
     * Get one emailTracking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmailTrackingDTO> findOne(Long id) {
        log.debug("Request to get EmailTracking : {}", id);
        return emailTrackingRepository.findById(id)
            .map(emailTrackingMapper::toDto);
    }

    /**
     * Delete the emailTracking by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EmailTracking : {}", id);
        emailTrackingRepository.deleteById(id);
    }
}
