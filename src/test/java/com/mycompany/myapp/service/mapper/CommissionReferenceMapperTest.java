package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommissionReferenceMapperTest {

    private CommissionReferenceMapper commissionReferenceMapper;

    @BeforeEach
    public void setUp() {
        commissionReferenceMapper = new CommissionReferenceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commissionReferenceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commissionReferenceMapper.fromId(null)).isNull();
    }
}
