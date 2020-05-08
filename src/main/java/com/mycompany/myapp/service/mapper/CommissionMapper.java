package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CommissionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commission} and its DTO {@link CommissionDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, OrderMapper.class})
public interface CommissionMapper extends EntityMapper<CommissionDTO, Commission> {

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "order.id", target = "orderId")
    CommissionDTO toDto(Commission commission);

    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "orderId", target = "order")
    Commission toEntity(CommissionDTO commissionDTO);

    default Commission fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commission commission = new Commission();
        commission.setId(id);
        return commission;
    }
}
