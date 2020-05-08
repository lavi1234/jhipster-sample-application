package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class OfferPriceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferPriceDTO.class);
        OfferPriceDTO offerPriceDTO1 = new OfferPriceDTO();
        offerPriceDTO1.setId(1L);
        OfferPriceDTO offerPriceDTO2 = new OfferPriceDTO();
        assertThat(offerPriceDTO1).isNotEqualTo(offerPriceDTO2);
        offerPriceDTO2.setId(offerPriceDTO1.getId());
        assertThat(offerPriceDTO1).isEqualTo(offerPriceDTO2);
        offerPriceDTO2.setId(2L);
        assertThat(offerPriceDTO1).isNotEqualTo(offerPriceDTO2);
        offerPriceDTO1.setId(null);
        assertThat(offerPriceDTO1).isNotEqualTo(offerPriceDTO2);
    }
}
