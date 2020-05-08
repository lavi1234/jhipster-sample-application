package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CartDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cart} and its DTO {@link CartDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnquiryMapper.class, UserProfileMapper.class})
public interface CartMapper extends EntityMapper<CartDTO, Cart> {

    @Mapping(source = "enquiry.id", target = "enquiryId")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "createdBy.id", target = "createdById")
    CartDTO toDto(Cart cart);

    @Mapping(source = "enquiryId", target = "enquiry")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "createdById", target = "createdBy")
    Cart toEntity(CartDTO cartDTO);

    default Cart fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setId(id);
        return cart;
    }
}
