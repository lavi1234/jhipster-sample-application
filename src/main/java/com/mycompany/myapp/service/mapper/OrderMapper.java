package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.OrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {OfferMapper.class, UserProfileMapper.class, EnquiryMapper.class, EnquiryDetailsMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

    @Mapping(source = "offer.id", target = "offerId")
    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "enquiry.id", target = "enquiryId")
    @Mapping(source = "enquiryDetails.id", target = "enquiryDetailsId")
    OrderDTO toDto(Order order);

    @Mapping(source = "offerId", target = "offer")
    @Mapping(source = "buyerId", target = "buyer")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "enquiryId", target = "enquiry")
    @Mapping(source = "enquiryDetailsId", target = "enquiryDetails")
    Order toEntity(OrderDTO orderDTO);

    default Order fromId(Long id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
