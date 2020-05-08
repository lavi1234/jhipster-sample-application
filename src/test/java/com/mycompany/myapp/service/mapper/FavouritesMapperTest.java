package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FavouritesMapperTest {

    private FavouritesMapper favouritesMapper;

    @BeforeEach
    public void setUp() {
        favouritesMapper = new FavouritesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(favouritesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(favouritesMapper.fromId(null)).isNull();
    }
}
