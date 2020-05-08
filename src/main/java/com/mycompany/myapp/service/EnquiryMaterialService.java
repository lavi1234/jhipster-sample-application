package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EnquiryMaterial;
import com.mycompany.myapp.repository.EnquiryMaterialRepository;
import com.mycompany.myapp.service.dto.EnquiryMaterialDTO;
import com.mycompany.myapp.service.mapper.EnquiryMaterialMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EnquiryMaterial}.
 */
@Service
@Transactional
public class EnquiryMaterialService {

    private final Logger log = LoggerFactory.getLogger(EnquiryMaterialService.class);

    private final EnquiryMaterialRepository enquiryMaterialRepository;

    private final EnquiryMaterialMapper enquiryMaterialMapper;

    public EnquiryMaterialService(EnquiryMaterialRepository enquiryMaterialRepository, EnquiryMaterialMapper enquiryMaterialMapper) {
        this.enquiryMaterialRepository = enquiryMaterialRepository;
        this.enquiryMaterialMapper = enquiryMaterialMapper;
    }

    /**
     * Save a enquiryMaterial.
     *
     * @param enquiryMaterialDTO the entity to save.
     * @return the persisted entity.
     */
    public EnquiryMaterialDTO save(EnquiryMaterialDTO enquiryMaterialDTO) {
        log.debug("Request to save EnquiryMaterial : {}", enquiryMaterialDTO);
        EnquiryMaterial enquiryMaterial = enquiryMaterialMapper.toEntity(enquiryMaterialDTO);
        enquiryMaterial = enquiryMaterialRepository.save(enquiryMaterial);
        return enquiryMaterialMapper.toDto(enquiryMaterial);
    }

    /**
     * Get all the enquiryMaterials.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EnquiryMaterialDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EnquiryMaterials");
        return enquiryMaterialRepository.findAll(pageable)
            .map(enquiryMaterialMapper::toDto);
    }

    /**
     * Get one enquiryMaterial by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnquiryMaterialDTO> findOne(Long id) {
        log.debug("Request to get EnquiryMaterial : {}", id);
        return enquiryMaterialRepository.findById(id)
            .map(enquiryMaterialMapper::toDto);
    }

    /**
     * Delete the enquiryMaterial by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnquiryMaterial : {}", id);
        enquiryMaterialRepository.deleteById(id);
    }
}
