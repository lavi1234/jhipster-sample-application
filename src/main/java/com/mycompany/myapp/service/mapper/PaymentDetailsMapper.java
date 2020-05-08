package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.PaymentDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentDetails} and its DTO {@link PaymentDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class})
public interface PaymentDetailsMapper extends EntityMapper<PaymentDetailsDTO, PaymentDetails> {

    @Mapping(source = "userProfile.id", target = "userProfileId")
    PaymentDetailsDTO toDto(PaymentDetails paymentDetails);

    @Mapping(source = "userProfileId", target = "userProfile")
    PaymentDetails toEntity(PaymentDetailsDTO paymentDetailsDTO);

    default PaymentDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setId(id);
        return paymentDetails;
    }
}
