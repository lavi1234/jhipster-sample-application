package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.BonusReferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BonusReference} and its DTO {@link BonusReferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BonusReferenceMapper extends EntityMapper<BonusReferenceDTO, BonusReference> {



    default BonusReference fromId(Long id) {
        if (id == null) {
            return null;
        }
        BonusReference bonusReference = new BonusReference();
        bonusReference.setId(id);
        return bonusReference;
    }
}
