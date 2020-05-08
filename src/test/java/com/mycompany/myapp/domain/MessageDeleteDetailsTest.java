package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class MessageDeleteDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageDeleteDetails.class);
        MessageDeleteDetails messageDeleteDetails1 = new MessageDeleteDetails();
        messageDeleteDetails1.setId(1L);
        MessageDeleteDetails messageDeleteDetails2 = new MessageDeleteDetails();
        messageDeleteDetails2.setId(messageDeleteDetails1.getId());
        assertThat(messageDeleteDetails1).isEqualTo(messageDeleteDetails2);
        messageDeleteDetails2.setId(2L);
        assertThat(messageDeleteDetails1).isNotEqualTo(messageDeleteDetails2);
        messageDeleteDetails1.setId(null);
        assertThat(messageDeleteDetails1).isNotEqualTo(messageDeleteDetails2);
    }
}
