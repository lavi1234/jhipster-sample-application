package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TradingHoursMapperTest {

    private TradingHoursMapper tradingHoursMapper;

    @BeforeEach
    public void setUp() {
        tradingHoursMapper = new TradingHoursMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tradingHoursMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tradingHoursMapper.fromId(null)).isNull();
    }
}
