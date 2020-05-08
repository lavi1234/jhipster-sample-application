package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.NotificationReceiver;
import com.mycompany.myapp.repository.NotificationReceiverRepository;
import com.mycompany.myapp.service.dto.NotificationReceiverDTO;
import com.mycompany.myapp.service.mapper.NotificationReceiverMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NotificationReceiver}.
 */
@Service
@Transactional
public class NotificationReceiverService {

    private final Logger log = LoggerFactory.getLogger(NotificationReceiverService.class);

    private final NotificationReceiverRepository notificationReceiverRepository;

    private final NotificationReceiverMapper notificationReceiverMapper;

    public NotificationReceiverService(NotificationReceiverRepository notificationReceiverRepository, NotificationReceiverMapper notificationReceiverMapper) {
        this.notificationReceiverRepository = notificationReceiverRepository;
        this.notificationReceiverMapper = notificationReceiverMapper;
    }

    /**
     * Save a notificationReceiver.
     *
     * @param notificationReceiverDTO the entity to save.
     * @return the persisted entity.
     */
    public NotificationReceiverDTO save(NotificationReceiverDTO notificationReceiverDTO) {
        log.debug("Request to save NotificationReceiver : {}", notificationReceiverDTO);
        NotificationReceiver notificationReceiver = notificationReceiverMapper.toEntity(notificationReceiverDTO);
        notificationReceiver = notificationReceiverRepository.save(notificationReceiver);
        return notificationReceiverMapper.toDto(notificationReceiver);
    }

    /**
     * Get all the notificationReceivers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NotificationReceiverDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NotificationReceivers");
        return notificationReceiverRepository.findAll(pageable)
            .map(notificationReceiverMapper::toDto);
    }

    /**
     * Get one notificationReceiver by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NotificationReceiverDTO> findOne(Long id) {
        log.debug("Request to get NotificationReceiver : {}", id);
        return notificationReceiverRepository.findById(id)
            .map(notificationReceiverMapper::toDto);
    }

    /**
     * Delete the notificationReceiver by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NotificationReceiver : {}", id);
        notificationReceiverRepository.deleteById(id);
    }
}
