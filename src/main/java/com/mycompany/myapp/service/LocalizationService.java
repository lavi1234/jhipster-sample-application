package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Localization;
import com.mycompany.myapp.repository.LocalizationRepository;
import com.mycompany.myapp.service.dto.LocalizationDTO;
import com.mycompany.myapp.service.mapper.LocalizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Localization}.
 */
@Service
@Transactional
public class LocalizationService {

    private final Logger log = LoggerFactory.getLogger(LocalizationService.class);

    private final LocalizationRepository localizationRepository;

    private final LocalizationMapper localizationMapper;

    public LocalizationService(LocalizationRepository localizationRepository, LocalizationMapper localizationMapper) {
        this.localizationRepository = localizationRepository;
        this.localizationMapper = localizationMapper;
    }

    /**
     * Save a localization.
     *
     * @param localizationDTO the entity to save.
     * @return the persisted entity.
     */
    public LocalizationDTO save(LocalizationDTO localizationDTO) {
        log.debug("Request to save Localization : {}", localizationDTO);
        Localization localization = localizationMapper.toEntity(localizationDTO);
        localization = localizationRepository.save(localization);
        return localizationMapper.toDto(localization);
    }

    /**
     * Get all the localizations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LocalizationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Localizations");
        return localizationRepository.findAll(pageable)
            .map(localizationMapper::toDto);
    }

    /**
     * Get one localization by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LocalizationDTO> findOne(Long id) {
        log.debug("Request to get Localization : {}", id);
        return localizationRepository.findById(id)
            .map(localizationMapper::toDto);
    }

    /**
     * Delete the localization by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Localization : {}", id);
        localizationRepository.deleteById(id);
    }
}
