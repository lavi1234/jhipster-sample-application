package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.BonusReference;
import com.mycompany.myapp.repository.BonusReferenceRepository;
import com.mycompany.myapp.service.dto.BonusReferenceDTO;
import com.mycompany.myapp.service.mapper.BonusReferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BonusReference}.
 */
@Service
@Transactional
public class BonusReferenceService {

    private final Logger log = LoggerFactory.getLogger(BonusReferenceService.class);

    private final BonusReferenceRepository bonusReferenceRepository;

    private final BonusReferenceMapper bonusReferenceMapper;

    public BonusReferenceService(BonusReferenceRepository bonusReferenceRepository, BonusReferenceMapper bonusReferenceMapper) {
        this.bonusReferenceRepository = bonusReferenceRepository;
        this.bonusReferenceMapper = bonusReferenceMapper;
    }

    /**
     * Save a bonusReference.
     *
     * @param bonusReferenceDTO the entity to save.
     * @return the persisted entity.
     */
    public BonusReferenceDTO save(BonusReferenceDTO bonusReferenceDTO) {
        log.debug("Request to save BonusReference : {}", bonusReferenceDTO);
        BonusReference bonusReference = bonusReferenceMapper.toEntity(bonusReferenceDTO);
        bonusReference = bonusReferenceRepository.save(bonusReference);
        return bonusReferenceMapper.toDto(bonusReference);
    }

    /**
     * Get all the bonusReferences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BonusReferenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BonusReferences");
        return bonusReferenceRepository.findAll(pageable)
            .map(bonusReferenceMapper::toDto);
    }

    /**
     * Get one bonusReference by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BonusReferenceDTO> findOne(Long id) {
        log.debug("Request to get BonusReference : {}", id);
        return bonusReferenceRepository.findById(id)
            .map(bonusReferenceMapper::toDto);
    }

    /**
     * Delete the bonusReference by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BonusReference : {}", id);
        bonusReferenceRepository.deleteById(id);
    }
}
