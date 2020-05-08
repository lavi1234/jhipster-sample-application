package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TradingHoursDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TradingHours} and its DTO {@link TradingHoursDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface TradingHoursMapper extends EntityMapper<TradingHoursDTO, TradingHours> {

    @Mapping(source = "company.id", target = "companyId")
    TradingHoursDTO toDto(TradingHours tradingHours);

    @Mapping(source = "companyId", target = "company")
    TradingHours toEntity(TradingHoursDTO tradingHoursDTO);

    default TradingHours fromId(Long id) {
        if (id == null) {
            return null;
        }
        TradingHours tradingHours = new TradingHours();
        tradingHours.setId(id);
        return tradingHours;
    }
}
