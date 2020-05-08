package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.LocalizationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Localization} and its DTO {@link LocalizationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LocalizationMapper extends EntityMapper<LocalizationDTO, Localization> {



    default Localization fromId(Long id) {
        if (id == null) {
            return null;
        }
        Localization localization = new Localization();
        localization.setId(id);
        return localization;
    }
}
