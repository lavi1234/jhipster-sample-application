package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.OfferPrice;
import com.mycompany.myapp.repository.OfferPriceRepository;
import com.mycompany.myapp.service.dto.OfferPriceDTO;
import com.mycompany.myapp.service.mapper.OfferPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OfferPrice}.
 */
@Service
@Transactional
public class OfferPriceService {

    private final Logger log = LoggerFactory.getLogger(OfferPriceService.class);

    private final OfferPriceRepository offerPriceRepository;

    private final OfferPriceMapper offerPriceMapper;

    public OfferPriceService(OfferPriceRepository offerPriceRepository, OfferPriceMapper offerPriceMapper) {
        this.offerPriceRepository = offerPriceRepository;
        this.offerPriceMapper = offerPriceMapper;
    }

    /**
     * Save a offerPrice.
     *
     * @param offerPriceDTO the entity to save.
     * @return the persisted entity.
     */
    public OfferPriceDTO save(OfferPriceDTO offerPriceDTO) {
        log.debug("Request to save OfferPrice : {}", offerPriceDTO);
        OfferPrice offerPrice = offerPriceMapper.toEntity(offerPriceDTO);
        offerPrice = offerPriceRepository.save(offerPrice);
        return offerPriceMapper.toDto(offerPrice);
    }

    /**
     * Get all the offerPrices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OfferPriceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OfferPrices");
        return offerPriceRepository.findAll(pageable)
            .map(offerPriceMapper::toDto);
    }

    /**
     * Get one offerPrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OfferPriceDTO> findOne(Long id) {
        log.debug("Request to get OfferPrice : {}", id);
        return offerPriceRepository.findById(id)
            .map(offerPriceMapper::toDto);
    }

    /**
     * Delete the offerPrice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OfferPrice : {}", id);
        offerPriceRepository.deleteById(id);
    }
}
