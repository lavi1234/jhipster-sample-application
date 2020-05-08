package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserCategoryMappingMapperTest {

    private UserCategoryMappingMapper userCategoryMappingMapper;

    @BeforeEach
    public void setUp() {
        userCategoryMappingMapper = new UserCategoryMappingMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userCategoryMappingMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userCategoryMappingMapper.fromId(null)).isNull();
    }
}
