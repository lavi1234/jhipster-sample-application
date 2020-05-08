package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.MessageDeleteDetails;
import com.mycompany.myapp.repository.MessageDeleteDetailsRepository;
import com.mycompany.myapp.service.dto.MessageDeleteDetailsDTO;
import com.mycompany.myapp.service.mapper.MessageDeleteDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MessageDeleteDetails}.
 */
@Service
@Transactional
public class MessageDeleteDetailsService {

    private final Logger log = LoggerFactory.getLogger(MessageDeleteDetailsService.class);

    private final MessageDeleteDetailsRepository messageDeleteDetailsRepository;

    private final MessageDeleteDetailsMapper messageDeleteDetailsMapper;

    public MessageDeleteDetailsService(MessageDeleteDetailsRepository messageDeleteDetailsRepository, MessageDeleteDetailsMapper messageDeleteDetailsMapper) {
        this.messageDeleteDetailsRepository = messageDeleteDetailsRepository;
        this.messageDeleteDetailsMapper = messageDeleteDetailsMapper;
    }

    /**
     * Save a messageDeleteDetails.
     *
     * @param messageDeleteDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public MessageDeleteDetailsDTO save(MessageDeleteDetailsDTO messageDeleteDetailsDTO) {
        log.debug("Request to save MessageDeleteDetails : {}", messageDeleteDetailsDTO);
        MessageDeleteDetails messageDeleteDetails = messageDeleteDetailsMapper.toEntity(messageDeleteDetailsDTO);
        messageDeleteDetails = messageDeleteDetailsRepository.save(messageDeleteDetails);
        return messageDeleteDetailsMapper.toDto(messageDeleteDetails);
    }

    /**
     * Get all the messageDeleteDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MessageDeleteDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MessageDeleteDetails");
        return messageDeleteDetailsRepository.findAll(pageable)
            .map(messageDeleteDetailsMapper::toDto);
    }

    /**
     * Get one messageDeleteDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MessageDeleteDetailsDTO> findOne(Long id) {
        log.debug("Request to get MessageDeleteDetails : {}", id);
        return messageDeleteDetailsRepository.findById(id)
            .map(messageDeleteDetailsMapper::toDto);
    }

    /**
     * Delete the messageDeleteDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MessageDeleteDetails : {}", id);
        messageDeleteDetailsRepository.deleteById(id);
    }
}
