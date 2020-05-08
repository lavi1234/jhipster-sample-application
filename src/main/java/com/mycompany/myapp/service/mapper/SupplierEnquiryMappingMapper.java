package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.SupplierEnquiryMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupplierEnquiryMapping} and its DTO {@link SupplierEnquiryMappingDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnquiryMapper.class, UserProfileMapper.class})
public interface SupplierEnquiryMappingMapper extends EntityMapper<SupplierEnquiryMappingDTO, SupplierEnquiryMapping> {

    @Mapping(source = "enquiry.id", target = "enquiryId")
    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "createdBy.id", target = "createdById")
    SupplierEnquiryMappingDTO toDto(SupplierEnquiryMapping supplierEnquiryMapping);

    @Mapping(source = "enquiryId", target = "enquiry")
    @Mapping(source = "supplierId", target = "supplier")
    @Mapping(source = "createdById", target = "createdBy")
    SupplierEnquiryMapping toEntity(SupplierEnquiryMappingDTO supplierEnquiryMappingDTO);

    default SupplierEnquiryMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        SupplierEnquiryMapping supplierEnquiryMapping = new SupplierEnquiryMapping();
        supplierEnquiryMapping.setId(id);
        return supplierEnquiryMapping;
    }
}
