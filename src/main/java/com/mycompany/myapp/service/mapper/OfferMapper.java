package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OfferDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offer} and its DTO {@link OfferDTO}.
 */
@Mapper(componentModel = "spring", uses = {SupplierEnquiryMappingMapper.class, UserProfileMapper.class})
public interface OfferMapper extends EntityMapper<OfferDTO, Offer> {

    @Mapping(source = "supplierEnquiry.id", target = "supplierEnquiryId")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "updatedBy.id", target = "updatedById")
    OfferDTO toDto(Offer offer);

    @Mapping(source = "supplierEnquiryId", target = "supplierEnquiry")
    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "updatedById", target = "updatedBy")
    Offer toEntity(OfferDTO offerDTO);

    default Offer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Offer offer = new Offer();
        offer.setId(id);
        return offer;
    }
}
