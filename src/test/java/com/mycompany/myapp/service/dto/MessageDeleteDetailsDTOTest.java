package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class MessageDeleteDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageDeleteDetailsDTO.class);
        MessageDeleteDetailsDTO messageDeleteDetailsDTO1 = new MessageDeleteDetailsDTO();
        messageDeleteDetailsDTO1.setId(1L);
        MessageDeleteDetailsDTO messageDeleteDetailsDTO2 = new MessageDeleteDetailsDTO();
        assertThat(messageDeleteDetailsDTO1).isNotEqualTo(messageDeleteDetailsDTO2);
        messageDeleteDetailsDTO2.setId(messageDeleteDetailsDTO1.getId());
        assertThat(messageDeleteDetailsDTO1).isEqualTo(messageDeleteDetailsDTO2);
        messageDeleteDetailsDTO2.setId(2L);
        assertThat(messageDeleteDetailsDTO1).isNotEqualTo(messageDeleteDetailsDTO2);
        messageDeleteDetailsDTO1.setId(null);
        assertThat(messageDeleteDetailsDTO1).isNotEqualTo(messageDeleteDetailsDTO2);
    }
}
