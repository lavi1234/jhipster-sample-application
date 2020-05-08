package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CommissionReferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CommissionReference} and its DTO {@link CommissionReferenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CommissionReferenceMapper extends EntityMapper<CommissionReferenceDTO, CommissionReference> {



    default CommissionReference fromId(Long id) {
        if (id == null) {
            return null;
        }
        CommissionReference commissionReference = new CommissionReference();
        commissionReference.setId(id);
        return commissionReference;
    }
}
