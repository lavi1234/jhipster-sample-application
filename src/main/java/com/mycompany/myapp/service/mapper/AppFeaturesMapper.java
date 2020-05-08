package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.AppFeaturesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppFeatures} and its DTO {@link AppFeaturesDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocalizationMapper.class})
public interface AppFeaturesMapper extends EntityMapper<AppFeaturesDTO, AppFeatures> {

    @Mapping(source = "name.id", target = "nameId")
    AppFeaturesDTO toDto(AppFeatures appFeatures);

    @Mapping(source = "nameId", target = "name")
    @Mapping(target = "subsriptionPlanFeatures", ignore = true)
    @Mapping(target = "removeSubsriptionPlanFeature", ignore = true)
    AppFeatures toEntity(AppFeaturesDTO appFeaturesDTO);

    default AppFeatures fromId(Long id) {
        if (id == null) {
            return null;
        }
        AppFeatures appFeatures = new AppFeatures();
        appFeatures.setId(id);
        return appFeatures;
    }
}
