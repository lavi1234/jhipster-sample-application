package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SubsriptionPlanFeatureDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubsriptionPlanFeature} and its DTO {@link SubsriptionPlanFeatureDTO}.
 */
@Mapper(componentModel = "spring", uses = {SubscriptionPlanMapper.class, AppFeaturesMapper.class})
public interface SubsriptionPlanFeatureMapper extends EntityMapper<SubsriptionPlanFeatureDTO, SubsriptionPlanFeature> {


    @Mapping(target = "removeSubscriptionPlan", ignore = true)
    @Mapping(target = "removeAppFeature", ignore = true)

    default SubsriptionPlanFeature fromId(Long id) {
        if (id == null) {
            return null;
        }
        SubsriptionPlanFeature subsriptionPlanFeature = new SubsriptionPlanFeature();
        subsriptionPlanFeature.setId(id);
        return subsriptionPlanFeature;
    }
}
