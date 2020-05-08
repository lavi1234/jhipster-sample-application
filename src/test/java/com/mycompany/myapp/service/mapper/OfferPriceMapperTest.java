package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OfferPriceMapperTest {

    private OfferPriceMapper offerPriceMapper;

    @BeforeEach
    public void setUp() {
        offerPriceMapper = new OfferPriceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(offerPriceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(offerPriceMapper.fromId(null)).isNull();
    }
}
