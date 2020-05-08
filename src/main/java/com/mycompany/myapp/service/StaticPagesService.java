package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.StaticPages;
import com.mycompany.myapp.repository.StaticPagesRepository;
import com.mycompany.myapp.service.dto.StaticPagesDTO;
import com.mycompany.myapp.service.mapper.StaticPagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StaticPages}.
 */
@Service
@Transactional
public class StaticPagesService {

    private final Logger log = LoggerFactory.getLogger(StaticPagesService.class);

    private final StaticPagesRepository staticPagesRepository;

    private final StaticPagesMapper staticPagesMapper;

    public StaticPagesService(StaticPagesRepository staticPagesRepository, StaticPagesMapper staticPagesMapper) {
        this.staticPagesRepository = staticPagesRepository;
        this.staticPagesMapper = staticPagesMapper;
    }

    /**
     * Save a staticPages.
     *
     * @param staticPagesDTO the entity to save.
     * @return the persisted entity.
     */
    public StaticPagesDTO save(StaticPagesDTO staticPagesDTO) {
        log.debug("Request to save StaticPages : {}", staticPagesDTO);
        StaticPages staticPages = staticPagesMapper.toEntity(staticPagesDTO);
        staticPages = staticPagesRepository.save(staticPages);
        return staticPagesMapper.toDto(staticPages);
    }

    /**
     * Get all the staticPages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<StaticPagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StaticPages");
        return staticPagesRepository.findAll(pageable)
            .map(staticPagesMapper::toDto);
    }

    /**
     * Get one staticPages by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StaticPagesDTO> findOne(Long id) {
        log.debug("Request to get StaticPages : {}", id);
        return staticPagesRepository.findById(id)
            .map(staticPagesMapper::toDto);
    }

    /**
     * Delete the staticPages by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StaticPages : {}", id);
        staticPagesRepository.deleteById(id);
    }
}
