package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Bonus;
import com.mycompany.myapp.repository.BonusRepository;
import com.mycompany.myapp.service.dto.BonusDTO;
import com.mycompany.myapp.service.mapper.BonusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Bonus}.
 */
@Service
@Transactional
public class BonusService {

    private final Logger log = LoggerFactory.getLogger(BonusService.class);

    private final BonusRepository bonusRepository;

    private final BonusMapper bonusMapper;

    public BonusService(BonusRepository bonusRepository, BonusMapper bonusMapper) {
        this.bonusRepository = bonusRepository;
        this.bonusMapper = bonusMapper;
    }

    /**
     * Save a bonus.
     *
     * @param bonusDTO the entity to save.
     * @return the persisted entity.
     */
    public BonusDTO save(BonusDTO bonusDTO) {
        log.debug("Request to save Bonus : {}", bonusDTO);
        Bonus bonus = bonusMapper.toEntity(bonusDTO);
        bonus = bonusRepository.save(bonus);
        return bonusMapper.toDto(bonus);
    }

    /**
     * Get all the bonuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BonusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bonuses");
        return bonusRepository.findAll(pageable)
            .map(bonusMapper::toDto);
    }

    /**
     * Get one bonus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BonusDTO> findOne(Long id) {
        log.debug("Request to get Bonus : {}", id);
        return bonusRepository.findById(id)
            .map(bonusMapper::toDto);
    }

    /**
     * Delete the bonus by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Bonus : {}", id);
        bonusRepository.deleteById(id);
    }
}
