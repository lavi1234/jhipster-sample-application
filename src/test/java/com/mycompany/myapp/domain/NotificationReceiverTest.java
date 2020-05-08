package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class NotificationReceiverTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationReceiver.class);
        NotificationReceiver notificationReceiver1 = new NotificationReceiver();
        notificationReceiver1.setId(1L);
        NotificationReceiver notificationReceiver2 = new NotificationReceiver();
        notificationReceiver2.setId(notificationReceiver1.getId());
        assertThat(notificationReceiver1).isEqualTo(notificationReceiver2);
        notificationReceiver2.setId(2L);
        assertThat(notificationReceiver1).isNotEqualTo(notificationReceiver2);
        notificationReceiver1.setId(null);
        assertThat(notificationReceiver1).isNotEqualTo(notificationReceiver2);
    }
}
