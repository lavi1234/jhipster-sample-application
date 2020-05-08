package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BonusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bonus} and its DTO {@link BonusDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, OrderMapper.class})
public interface BonusMapper extends EntityMapper<BonusDTO, Bonus> {

    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "order.id", target = "orderId")
    BonusDTO toDto(Bonus bonus);

    @Mapping(source = "buyerId", target = "buyer")
    @Mapping(source = "orderId", target = "order")
    Bonus toEntity(BonusDTO bonusDTO);

    default Bonus fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bonus bonus = new Bonus();
        bonus.setId(id);
        return bonus;
    }
}
