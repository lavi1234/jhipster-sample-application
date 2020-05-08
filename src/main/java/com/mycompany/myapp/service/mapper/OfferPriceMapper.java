package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OfferPriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OfferPrice} and its DTO {@link OfferPriceDTO}.
 */
@Mapper(componentModel = "spring", uses = {OfferMapper.class, EnquiryMapper.class, EnquiryDetailsMapper.class})
public interface OfferPriceMapper extends EntityMapper<OfferPriceDTO, OfferPrice> {

    @Mapping(source = "offer.id", target = "offerId")
    @Mapping(source = "enquiry.id", target = "enquiryId")
    @Mapping(source = "enquiryDetails.id", target = "enquiryDetailsId")
    OfferPriceDTO toDto(OfferPrice offerPrice);

    @Mapping(source = "offerId", target = "offer")
    @Mapping(source = "enquiryId", target = "enquiry")
    @Mapping(source = "enquiryDetailsId", target = "enquiryDetails")
    OfferPrice toEntity(OfferPriceDTO offerPriceDTO);

    default OfferPrice fromId(Long id) {
        if (id == null) {
            return null;
        }
        OfferPrice offerPrice = new OfferPrice();
        offerPrice.setId(id);
        return offerPrice;
    }
}
