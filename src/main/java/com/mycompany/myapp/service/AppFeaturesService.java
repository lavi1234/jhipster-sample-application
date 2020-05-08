package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.AppFeatures;
import com.mycompany.myapp.repository.AppFeaturesRepository;
import com.mycompany.myapp.service.dto.AppFeaturesDTO;
import com.mycompany.myapp.service.mapper.AppFeaturesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AppFeatures}.
 */
@Service
@Transactional
public class AppFeaturesService {

    private final Logger log = LoggerFactory.getLogger(AppFeaturesService.class);

    private final AppFeaturesRepository appFeaturesRepository;

    private final AppFeaturesMapper appFeaturesMapper;

    public AppFeaturesService(AppFeaturesRepository appFeaturesRepository, AppFeaturesMapper appFeaturesMapper) {
        this.appFeaturesRepository = appFeaturesRepository;
        this.appFeaturesMapper = appFeaturesMapper;
    }

    /**
     * Save a appFeatures.
     *
     * @param appFeaturesDTO the entity to save.
     * @return the persisted entity.
     */
    public AppFeaturesDTO save(AppFeaturesDTO appFeaturesDTO) {
        log.debug("Request to save AppFeatures : {}", appFeaturesDTO);
        AppFeatures appFeatures = appFeaturesMapper.toEntity(appFeaturesDTO);
        appFeatures = appFeaturesRepository.save(appFeatures);
        return appFeaturesMapper.toDto(appFeatures);
    }

    /**
     * Get all the appFeatures.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AppFeaturesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AppFeatures");
        return appFeaturesRepository.findAll(pageable)
            .map(appFeaturesMapper::toDto);
    }

    /**
     * Get one appFeatures by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AppFeaturesDTO> findOne(Long id) {
        log.debug("Request to get AppFeatures : {}", id);
        return appFeaturesRepository.findById(id)
            .map(appFeaturesMapper::toDto);
    }

    /**
     * Delete the appFeatures by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AppFeatures : {}", id);
        appFeaturesRepository.deleteById(id);
    }
}
