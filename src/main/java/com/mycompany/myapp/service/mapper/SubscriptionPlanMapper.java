package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SubscriptionPlanDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubscriptionPlan} and its DTO {@link SubscriptionPlanDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocalizationMapper.class})
public interface SubscriptionPlanMapper extends EntityMapper<SubscriptionPlanDTO, SubscriptionPlan> {

    @Mapping(source = "name.id", target = "nameId")
    @Mapping(source = "description.id", target = "descriptionId")
    SubscriptionPlanDTO toDto(SubscriptionPlan subscriptionPlan);

    @Mapping(source = "nameId", target = "name")
    @Mapping(source = "descriptionId", target = "description")
    @Mapping(target = "subsriptionPlanFeatures", ignore = true)
    @Mapping(target = "removeSubsriptionPlanFeature", ignore = true)
    SubscriptionPlan toEntity(SubscriptionPlanDTO subscriptionPlanDTO);

    default SubscriptionPlan fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
        subscriptionPlan.setId(id);
        return subscriptionPlan;
    }
}
