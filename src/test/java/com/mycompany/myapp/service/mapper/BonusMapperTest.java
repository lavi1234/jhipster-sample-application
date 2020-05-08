package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BonusMapperTest {

    private BonusMapper bonusMapper;

    @BeforeEach
    public void setUp() {
        bonusMapper = new BonusMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bonusMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bonusMapper.fromId(null)).isNull();
    }
}
