package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SupplierEnquiryMappingMapperTest {

    private SupplierEnquiryMappingMapper supplierEnquiryMappingMapper;

    @BeforeEach
    public void setUp() {
        supplierEnquiryMappingMapper = new SupplierEnquiryMappingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(supplierEnquiryMappingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(supplierEnquiryMappingMapper.fromId(null)).isNull();
    }
}
