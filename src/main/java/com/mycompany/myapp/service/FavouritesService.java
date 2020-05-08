package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Favourites;
import com.mycompany.myapp.repository.FavouritesRepository;
import com.mycompany.myapp.service.dto.FavouritesDTO;
import com.mycompany.myapp.service.mapper.FavouritesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Favourites}.
 */
@Service
@Transactional
public class FavouritesService {

    private final Logger log = LoggerFactory.getLogger(FavouritesService.class);

    private final FavouritesRepository favouritesRepository;

    private final FavouritesMapper favouritesMapper;

    public FavouritesService(FavouritesRepository favouritesRepository, FavouritesMapper favouritesMapper) {
        this.favouritesRepository = favouritesRepository;
        this.favouritesMapper = favouritesMapper;
    }

    /**
     * Save a favourites.
     *
     * @param favouritesDTO the entity to save.
     * @return the persisted entity.
     */
    public FavouritesDTO save(FavouritesDTO favouritesDTO) {
        log.debug("Request to save Favourites : {}", favouritesDTO);
        Favourites favourites = favouritesMapper.toEntity(favouritesDTO);
        favourites = favouritesRepository.save(favourites);
        return favouritesMapper.toDto(favourites);
    }

    /**
     * Get all the favourites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FavouritesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Favourites");
        return favouritesRepository.findAll(pageable)
            .map(favouritesMapper::toDto);
    }

    /**
     * Get one favourites by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FavouritesDTO> findOne(Long id) {
        log.debug("Request to get Favourites : {}", id);
        return favouritesRepository.findById(id)
            .map(favouritesMapper::toDto);
    }

    /**
     * Delete the favourites by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Favourites : {}", id);
        favouritesRepository.deleteById(id);
    }
}
