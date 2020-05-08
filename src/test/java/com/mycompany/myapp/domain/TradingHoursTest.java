package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TradingHoursTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TradingHours.class);
        TradingHours tradingHours1 = new TradingHours();
        tradingHours1.setId(1L);
        TradingHours tradingHours2 = new TradingHours();
        tradingHours2.setId(tradingHours1.getId());
        assertThat(tradingHours1).isEqualTo(tradingHours2);
        tradingHours2.setId(2L);
        assertThat(tradingHours1).isNotEqualTo(tradingHours2);
        tradingHours1.setId(null);
        assertThat(tradingHours1).isNotEqualTo(tradingHours2);
    }
}
