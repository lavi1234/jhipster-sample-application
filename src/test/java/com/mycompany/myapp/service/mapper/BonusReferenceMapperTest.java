package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BonusReferenceMapperTest {

    private BonusReferenceMapper bonusReferenceMapper;

    @BeforeEach
    public void setUp() {
        bonusReferenceMapper = new BonusReferenceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bonusReferenceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bonusReferenceMapper.fromId(null)).isNull();
    }
}
