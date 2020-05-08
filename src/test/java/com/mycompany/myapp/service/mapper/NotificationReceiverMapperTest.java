package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NotificationReceiverMapperTest {

    private NotificationReceiverMapper notificationReceiverMapper;

    @BeforeEach
    public void setUp() {
        notificationReceiverMapper = new NotificationReceiverMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(notificationReceiverMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(notificationReceiverMapper.fromId(null)).isNull();
    }
}
