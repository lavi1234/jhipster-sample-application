package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class OfferPriceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferPrice.class);
        OfferPrice offerPrice1 = new OfferPrice();
        offerPrice1.setId(1L);
        OfferPrice offerPrice2 = new OfferPrice();
        offerPrice2.setId(offerPrice1.getId());
        assertThat(offerPrice1).isEqualTo(offerPrice2);
        offerPrice2.setId(2L);
        assertThat(offerPrice1).isNotEqualTo(offerPrice2);
        offerPrice1.setId(null);
        assertThat(offerPrice1).isNotEqualTo(offerPrice2);
    }
}
