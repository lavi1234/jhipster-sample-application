package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EnquiryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Enquiry} and its DTO {@link EnquiryDTO}.
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class, AddressMapper.class})
public interface EnquiryMapper extends EntityMapper<EnquiryDTO, Enquiry> {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "deliveryAddress.id", target = "deliveryAddressId")
    EnquiryDTO toDto(Enquiry enquiry);

    @Mapping(source = "productId", target = "product")
    @Mapping(source = "deliveryAddressId", target = "deliveryAddress")
    Enquiry toEntity(EnquiryDTO enquiryDTO);

    default Enquiry fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enquiry enquiry = new Enquiry();
        enquiry.setId(id);
        return enquiry;
    }
}
