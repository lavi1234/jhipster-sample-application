package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserSubscriptionMapperTest {

    private UserSubscriptionMapper userSubscriptionMapper;

    @BeforeEach
    public void setUp() {
        userSubscriptionMapper = new UserSubscriptionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userSubscriptionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userSubscriptionMapper.fromId(null)).isNull();
    }
}
