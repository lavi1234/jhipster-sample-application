package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class EmailTrackingMapperTest {

    private EmailTrackingMapper emailTrackingMapper;

    @BeforeEach
    public void setUp() {
        emailTrackingMapper = new EmailTrackingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(emailTrackingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(emailTrackingMapper.fromId(null)).isNull();
    }
}
