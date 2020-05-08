package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnquiryDetailsMapperTest {

    private EnquiryDetailsMapper enquiryDetailsMapper;

    @BeforeEach
    public void setUp() {
        enquiryDetailsMapper = new EnquiryDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enquiryDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enquiryDetailsMapper.fromId(null)).isNull();
    }
}
