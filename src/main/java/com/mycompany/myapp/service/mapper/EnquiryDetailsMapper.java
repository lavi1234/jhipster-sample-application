package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EnquiryDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnquiryDetails} and its DTO {@link EnquiryDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnquiryMapper.class, CategoryMapper.class, UserProfileMapper.class, OfferMapper.class})
public interface EnquiryDetailsMapper extends EntityMapper<EnquiryDetailsDTO, EnquiryDetails> {

    @Mapping(source = "enquiry.id", target = "enquiryId")
    @Mapping(source = "print.id", target = "printId")
    @Mapping(source = "finishing.id", target = "finishingId")
    @Mapping(source = "handling.id", target = "handlingId")
    @Mapping(source = "packaging.id", target = "packagingId")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "offer.id", target = "offerId")
    EnquiryDetailsDTO toDto(EnquiryDetails enquiryDetails);

    @Mapping(source = "enquiryId", target = "enquiry")
    @Mapping(source = "printId", target = "print")
    @Mapping(source = "finishingId", target = "finishing")
    @Mapping(source = "handlingId", target = "handling")
    @Mapping(source = "packagingId", target = "packaging")
    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "offerId", target = "offer")
    EnquiryDetails toEntity(EnquiryDetailsDTO enquiryDetailsDTO);

    default EnquiryDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnquiryDetails enquiryDetails = new EnquiryDetails();
        enquiryDetails.setId(id);
        return enquiryDetails;
    }
}
