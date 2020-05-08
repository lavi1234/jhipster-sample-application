package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommissionMapperTest {

    private CommissionMapper commissionMapper;

    @BeforeEach
    public void setUp() {
        commissionMapper = new CommissionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commissionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commissionMapper.fromId(null)).isNull();
    }
}
