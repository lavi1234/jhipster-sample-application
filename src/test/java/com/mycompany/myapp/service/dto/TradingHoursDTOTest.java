package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class TradingHoursDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TradingHoursDTO.class);
        TradingHoursDTO tradingHoursDTO1 = new TradingHoursDTO();
        tradingHoursDTO1.setId(1L);
        TradingHoursDTO tradingHoursDTO2 = new TradingHoursDTO();
        assertThat(tradingHoursDTO1).isNotEqualTo(tradingHoursDTO2);
        tradingHoursDTO2.setId(tradingHoursDTO1.getId());
        assertThat(tradingHoursDTO1).isEqualTo(tradingHoursDTO2);
        tradingHoursDTO2.setId(2L);
        assertThat(tradingHoursDTO1).isNotEqualTo(tradingHoursDTO2);
        tradingHoursDTO1.setId(null);
        assertThat(tradingHoursDTO1).isNotEqualTo(tradingHoursDTO2);
    }
}
