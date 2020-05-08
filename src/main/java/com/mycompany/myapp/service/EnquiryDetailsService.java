package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EnquiryDetails;
import com.mycompany.myapp.repository.EnquiryDetailsRepository;
import com.mycompany.myapp.service.dto.EnquiryDetailsDTO;
import com.mycompany.myapp.service.mapper.EnquiryDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EnquiryDetails}.
 */
@Service
@Transactional
public class EnquiryDetailsService {

    private final Logger log = LoggerFactory.getLogger(EnquiryDetailsService.class);

    private final EnquiryDetailsRepository enquiryDetailsRepository;

    private final EnquiryDetailsMapper enquiryDetailsMapper;

    public EnquiryDetailsService(EnquiryDetailsRepository enquiryDetailsRepository, EnquiryDetailsMapper enquiryDetailsMapper) {
        this.enquiryDetailsRepository = enquiryDetailsRepository;
        this.enquiryDetailsMapper = enquiryDetailsMapper;
    }

    /**
     * Save a enquiryDetails.
     *
     * @param enquiryDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    public EnquiryDetailsDTO save(EnquiryDetailsDTO enquiryDetailsDTO) {
        log.debug("Request to save EnquiryDetails : {}", enquiryDetailsDTO);
        EnquiryDetails enquiryDetails = enquiryDetailsMapper.toEntity(enquiryDetailsDTO);
        enquiryDetails = enquiryDetailsRepository.save(enquiryDetails);
        return enquiryDetailsMapper.toDto(enquiryDetails);
    }

    /**
     * Get all the enquiryDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EnquiryDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EnquiryDetails");
        return enquiryDetailsRepository.findAll(pageable)
            .map(enquiryDetailsMapper::toDto);
    }

    /**
     * Get one enquiryDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnquiryDetailsDTO> findOne(Long id) {
        log.debug("Request to get EnquiryDetails : {}", id);
        return enquiryDetailsRepository.findById(id)
            .map(enquiryDetailsMapper::toDto);
    }

    /**
     * Delete the enquiryDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnquiryDetails : {}", id);
        enquiryDetailsRepository.deleteById(id);
    }
}
