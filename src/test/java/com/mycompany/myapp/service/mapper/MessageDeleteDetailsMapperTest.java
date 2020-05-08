package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageDeleteDetailsMapperTest {

    private MessageDeleteDetailsMapper messageDeleteDetailsMapper;

    @BeforeEach
    public void setUp() {
        messageDeleteDetailsMapper = new MessageDeleteDetailsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(messageDeleteDetailsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(messageDeleteDetailsMapper.fromId(null)).isNull();
    }
}
