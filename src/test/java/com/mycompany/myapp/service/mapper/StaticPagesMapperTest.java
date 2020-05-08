package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StaticPagesMapperTest {

    private StaticPagesMapper staticPagesMapper;

    @BeforeEach
    public void setUp() {
        staticPagesMapper = new StaticPagesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(staticPagesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(staticPagesMapper.fromId(null)).isNull();
    }
}
