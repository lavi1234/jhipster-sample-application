package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EnquiryNote;
import com.mycompany.myapp.repository.EnquiryNoteRepository;
import com.mycompany.myapp.service.dto.EnquiryNoteDTO;
import com.mycompany.myapp.service.mapper.EnquiryNoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EnquiryNote}.
 */
@Service
@Transactional
public class EnquiryNoteService {

    private final Logger log = LoggerFactory.getLogger(EnquiryNoteService.class);

    private final EnquiryNoteRepository enquiryNoteRepository;

    private final EnquiryNoteMapper enquiryNoteMapper;

    public EnquiryNoteService(EnquiryNoteRepository enquiryNoteRepository, EnquiryNoteMapper enquiryNoteMapper) {
        this.enquiryNoteRepository = enquiryNoteRepository;
        this.enquiryNoteMapper = enquiryNoteMapper;
    }

    /**
     * Save a enquiryNote.
     *
     * @param enquiryNoteDTO the entity to save.
     * @return the persisted entity.
     */
    public EnquiryNoteDTO save(EnquiryNoteDTO enquiryNoteDTO) {
        log.debug("Request to save EnquiryNote : {}", enquiryNoteDTO);
        EnquiryNote enquiryNote = enquiryNoteMapper.toEntity(enquiryNoteDTO);
        enquiryNote = enquiryNoteRepository.save(enquiryNote);
        return enquiryNoteMapper.toDto(enquiryNote);
    }

    /**
     * Get all the enquiryNotes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EnquiryNoteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EnquiryNotes");
        return enquiryNoteRepository.findAll(pageable)
            .map(enquiryNoteMapper::toDto);
    }

    /**
     * Get one enquiryNote by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnquiryNoteDTO> findOne(Long id) {
        log.debug("Request to get EnquiryNote : {}", id);
        return enquiryNoteRepository.findById(id)
            .map(enquiryNoteMapper::toDto);
    }

    /**
     * Delete the enquiryNote by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnquiryNote : {}", id);
        enquiryNoteRepository.deleteById(id);
    }
}
