package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SubsriptionPlanFeatureMapperTest {

    private SubsriptionPlanFeatureMapper subsriptionPlanFeatureMapper;

    @BeforeEach
    public void setUp() {
        subsriptionPlanFeatureMapper = new SubsriptionPlanFeatureMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(subsriptionPlanFeatureMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(subsriptionPlanFeatureMapper.fromId(null)).isNull();
    }
}
