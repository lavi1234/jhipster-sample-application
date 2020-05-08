package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EnquiryNoteMapperTest {

    private EnquiryNoteMapper enquiryNoteMapper;

    @BeforeEach
    public void setUp() {
        enquiryNoteMapper = new EnquiryNoteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(enquiryNoteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(enquiryNoteMapper.fromId(null)).isNull();
    }
}
