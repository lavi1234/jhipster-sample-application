package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EnquiryMaterialDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnquiryMaterial} and its DTO {@link EnquiryMaterialDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnquiryDetailsMapper.class})
public interface EnquiryMaterialMapper extends EntityMapper<EnquiryMaterialDTO, EnquiryMaterial> {

    @Mapping(source = "enquiryDetails.id", target = "enquiryDetailsId")
    EnquiryMaterialDTO toDto(EnquiryMaterial enquiryMaterial);

    @Mapping(source = "enquiryDetailsId", target = "enquiryDetails")
    EnquiryMaterial toEntity(EnquiryMaterialDTO enquiryMaterialDTO);

    default EnquiryMaterial fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnquiryMaterial enquiryMaterial = new EnquiryMaterial();
        enquiryMaterial.setId(id);
        return enquiryMaterial;
    }
}
