package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.UserCategoryMapping;
import com.mycompany.myapp.repository.UserCategoryMappingRepository;
import com.mycompany.myapp.service.dto.UserCategoryMappingDTO;
import com.mycompany.myapp.service.mapper.UserCategoryMappingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserCategoryMapping}.
 */
@Service
@Transactional
public class UserCategoryMappingService {

    private final Logger log = LoggerFactory.getLogger(UserCategoryMappingService.class);

    private final UserCategoryMappingRepository userCategoryMappingRepository;

    private final UserCategoryMappingMapper userCategoryMappingMapper;

    public UserCategoryMappingService(UserCategoryMappingRepository userCategoryMappingRepository, UserCategoryMappingMapper userCategoryMappingMapper) {
        this.userCategoryMappingRepository = userCategoryMappingRepository;
        this.userCategoryMappingMapper = userCategoryMappingMapper;
    }

    /**
     * Save a userCategoryMapping.
     *
     * @param userCategoryMappingDTO the entity to save.
     * @return the persisted entity.
     */
    public UserCategoryMappingDTO save(UserCategoryMappingDTO userCategoryMappingDTO) {
        log.debug("Request to save UserCategoryMapping : {}", userCategoryMappingDTO);
        UserCategoryMapping userCategoryMapping = userCategoryMappingMapper.toEntity(userCategoryMappingDTO);
        userCategoryMapping = userCategoryMappingRepository.save(userCategoryMapping);
        return userCategoryMappingMapper.toDto(userCategoryMapping);
    }

    /**
     * Get all the userCategoryMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserCategoryMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserCategoryMappings");
        return userCategoryMappingRepository.findAll(pageable)
            .map(userCategoryMappingMapper::toDto);
    }

    /**
     * Get all the userCategoryMappings with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UserCategoryMappingDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userCategoryMappingRepository.findAllWithEagerRelationships(pageable).map(userCategoryMappingMapper::toDto);
    }

    /**
     * Get one userCategoryMapping by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserCategoryMappingDTO> findOne(Long id) {
        log.debug("Request to get UserCategoryMapping : {}", id);
        return userCategoryMappingRepository.findOneWithEagerRelationships(id)
            .map(userCategoryMappingMapper::toDto);
    }

    /**
     * Delete the userCategoryMapping by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserCategoryMapping : {}", id);
        userCategoryMappingRepository.deleteById(id);
    }
}
