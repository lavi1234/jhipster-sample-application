package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class NotificationReceiverDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotificationReceiverDTO.class);
        NotificationReceiverDTO notificationReceiverDTO1 = new NotificationReceiverDTO();
        notificationReceiverDTO1.setId(1L);
        NotificationReceiverDTO notificationReceiverDTO2 = new NotificationReceiverDTO();
        assertThat(notificationReceiverDTO1).isNotEqualTo(notificationReceiverDTO2);
        notificationReceiverDTO2.setId(notificationReceiverDTO1.getId());
        assertThat(notificationReceiverDTO1).isEqualTo(notificationReceiverDTO2);
        notificationReceiverDTO2.setId(2L);
        assertThat(notificationReceiverDTO1).isNotEqualTo(notificationReceiverDTO2);
        notificationReceiverDTO1.setId(null);
        assertThat(notificationReceiverDTO1).isNotEqualTo(notificationReceiverDTO2);
    }
}
