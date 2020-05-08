package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AppFeaturesMapperTest {

    private AppFeaturesMapper appFeaturesMapper;

    @BeforeEach
    public void setUp() {
        appFeaturesMapper = new AppFeaturesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(appFeaturesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(appFeaturesMapper.fromId(null)).isNull();
    }
}
