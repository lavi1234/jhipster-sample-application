package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnquiryMaterialMapperTest {

    private EnquiryMaterialMapper enquiryMaterialMapper;

    @BeforeEach
    public void setUp() {
        enquiryMaterialMapper = new EnquiryMaterialMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enquiryMaterialMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enquiryMaterialMapper.fromId(null)).isNull();
    }
}
