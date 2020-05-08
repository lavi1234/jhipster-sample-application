package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnquiryMapperTest {

    private EnquiryMapper enquiryMapper;

    @BeforeEach
    public void setUp() {
        enquiryMapper = new EnquiryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enquiryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enquiryMapper.fromId(null)).isNull();
    }
}
