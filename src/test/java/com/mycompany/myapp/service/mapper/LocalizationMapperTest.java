package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LocalizationMapperTest {

    private LocalizationMapper localizationMapper;

    @BeforeEach
    public void setUp() {
        localizationMapper = new LocalizationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(localizationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(localizationMapper.fromId(null)).isNull();
    }
}
