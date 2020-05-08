package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Enquiry;
import com.mycompany.myapp.repository.EnquiryRepository;
import com.mycompany.myapp.service.dto.EnquiryDTO;
import com.mycompany.myapp.service.mapper.EnquiryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Enquiry}.
 */
@Service
@Transactional
public class EnquiryService {

    private final Logger log = LoggerFactory.getLogger(EnquiryService.class);

    private final EnquiryRepository enquiryRepository;

    private final EnquiryMapper enquiryMapper;

    public EnquiryService(EnquiryRepository enquiryRepository, EnquiryMapper enquiryMapper) {
        this.enquiryRepository = enquiryRepository;
        this.enquiryMapper = enquiryMapper;
    }

    /**
     * Save a enquiry.
     *
     * @param enquiryDTO the entity to save.
     * @return the persisted entity.
     */
    public EnquiryDTO save(EnquiryDTO enquiryDTO) {
        log.debug("Request to save Enquiry : {}", enquiryDTO);
        Enquiry enquiry = enquiryMapper.toEntity(enquiryDTO);
        enquiry = enquiryRepository.save(enquiry);
        return enquiryMapper.toDto(enquiry);
    }

    /**
     * Get all the enquiries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EnquiryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enquiries");
        return enquiryRepository.findAll(pageable)
            .map(enquiryMapper::toDto);
    }

    /**
     * Get one enquiry by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnquiryDTO> findOne(Long id) {
        log.debug("Request to get Enquiry : {}", id);
        return enquiryRepository.findById(id)
            .map(enquiryMapper::toDto);
    }

    /**
     * Delete the enquiry by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Enquiry : {}", id);
        enquiryRepository.deleteById(id);
    }
}
