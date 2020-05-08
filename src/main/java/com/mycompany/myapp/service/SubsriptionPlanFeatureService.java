package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.SubsriptionPlanFeature;
import com.mycompany.myapp.repository.SubsriptionPlanFeatureRepository;
import com.mycompany.myapp.service.dto.SubsriptionPlanFeatureDTO;
import com.mycompany.myapp.service.mapper.SubsriptionPlanFeatureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SubsriptionPlanFeature}.
 */
@Service
@Transactional
public class SubsriptionPlanFeatureService {

    private final Logger log = LoggerFactory.getLogger(SubsriptionPlanFeatureService.class);

    private final SubsriptionPlanFeatureRepository subsriptionPlanFeatureRepository;

    private final SubsriptionPlanFeatureMapper subsriptionPlanFeatureMapper;

    public SubsriptionPlanFeatureService(SubsriptionPlanFeatureRepository subsriptionPlanFeatureRepository, SubsriptionPlanFeatureMapper subsriptionPlanFeatureMapper) {
        this.subsriptionPlanFeatureRepository = subsriptionPlanFeatureRepository;
        this.subsriptionPlanFeatureMapper = subsriptionPlanFeatureMapper;
    }

    /**
     * Save a subsriptionPlanFeature.
     *
     * @param subsriptionPlanFeatureDTO the entity to save.
     * @return the persisted entity.
     */
    public SubsriptionPlanFeatureDTO save(SubsriptionPlanFeatureDTO subsriptionPlanFeatureDTO) {
        log.debug("Request to save SubsriptionPlanFeature : {}", subsriptionPlanFeatureDTO);
        SubsriptionPlanFeature subsriptionPlanFeature = subsriptionPlanFeatureMapper.toEntity(subsriptionPlanFeatureDTO);
        subsriptionPlanFeature = subsriptionPlanFeatureRepository.save(subsriptionPlanFeature);
        return subsriptionPlanFeatureMapper.toDto(subsriptionPlanFeature);
    }

    /**
     * Get all the subsriptionPlanFeatures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SubsriptionPlanFeatureDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SubsriptionPlanFeatures");
        return subsriptionPlanFeatureRepository.findAll(pageable)
            .map(subsriptionPlanFeatureMapper::toDto);
    }

    /**
     * Get all the subsriptionPlanFeatures with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SubsriptionPlanFeatureDTO> findAllWithEagerRelationships(Pageable pageable) {
        return subsriptionPlanFeatureRepository.findAllWithEagerRelationships(pageable).map(subsriptionPlanFeatureMapper::toDto);
    }

    /**
     * Get one subsriptionPlanFeature by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SubsriptionPlanFeatureDTO> findOne(Long id) {
        log.debug("Request to get SubsriptionPlanFeature : {}", id);
        return subsriptionPlanFeatureRepository.findOneWithEagerRelationships(id)
            .map(subsriptionPlanFeatureMapper::toDto);
    }

    /**
     * Delete the subsriptionPlanFeature by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SubsriptionPlanFeature : {}", id);
        subsriptionPlanFeatureRepository.deleteById(id);
    }
}
