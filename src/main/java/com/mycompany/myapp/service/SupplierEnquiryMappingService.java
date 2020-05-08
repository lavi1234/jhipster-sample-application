package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SupplierEnquiryMapping;
import com.mycompany.myapp.repository.SupplierEnquiryMappingRepository;
import com.mycompany.myapp.service.dto.SupplierEnquiryMappingDTO;
import com.mycompany.myapp.service.mapper.SupplierEnquiryMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SupplierEnquiryMapping}.
 */
@Service
@Transactional
public class SupplierEnquiryMappingService {

    private final Logger log = LoggerFactory.getLogger(SupplierEnquiryMappingService.class);

    private final SupplierEnquiryMappingRepository supplierEnquiryMappingRepository;

    private final SupplierEnquiryMappingMapper supplierEnquiryMappingMapper;

    public SupplierEnquiryMappingService(SupplierEnquiryMappingRepository supplierEnquiryMappingRepository, SupplierEnquiryMappingMapper supplierEnquiryMappingMapper) {
        this.supplierEnquiryMappingRepository = supplierEnquiryMappingRepository;
        this.supplierEnquiryMappingMapper = supplierEnquiryMappingMapper;
    }

    /**
     * Save a supplierEnquiryMapping.
     *
     * @param supplierEnquiryMappingDTO the entity to save.
     * @return the persisted entity.
     */
    public SupplierEnquiryMappingDTO save(SupplierEnquiryMappingDTO supplierEnquiryMappingDTO) {
        log.debug("Request to save SupplierEnquiryMapping : {}", supplierEnquiryMappingDTO);
        SupplierEnquiryMapping supplierEnquiryMapping = supplierEnquiryMappingMapper.toEntity(supplierEnquiryMappingDTO);
        supplierEnquiryMapping = supplierEnquiryMappingRepository.save(supplierEnquiryMapping);
        return supplierEnquiryMappingMapper.toDto(supplierEnquiryMapping);
    }

    /**
     * Get all the supplierEnquiryMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SupplierEnquiryMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SupplierEnquiryMappings");
        return supplierEnquiryMappingRepository.findAll(pageable)
            .map(supplierEnquiryMappingMapper::toDto);
    }

    /**
     * Get one supplierEnquiryMapping by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SupplierEnquiryMappingDTO> findOne(Long id) {
        log.debug("Request to get SupplierEnquiryMapping : {}", id);
        return supplierEnquiryMappingRepository.findById(id)
            .map(supplierEnquiryMappingMapper::toDto);
    }

    /**
     * Delete the supplierEnquiryMapping by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SupplierEnquiryMapping : {}", id);
        supplierEnquiryMappingRepository.deleteById(id);
    }
}
