package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EmailTrackingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTrackingDTO.class);
        EmailTrackingDTO emailTrackingDTO1 = new EmailTrackingDTO();
        emailTrackingDTO1.setId(1L);
        EmailTrackingDTO emailTrackingDTO2 = new EmailTrackingDTO();
        assertThat(emailTrackingDTO1).isNotEqualTo(emailTrackingDTO2);
        emailTrackingDTO2.setId(emailTrackingDTO1.getId());
        assertThat(emailTrackingDTO1).isEqualTo(emailTrackingDTO2);
        emailTrackingDTO2.setId(2L);
        assertThat(emailTrackingDTO1).isNotEqualTo(emailTrackingDTO2);
        emailTrackingDTO1.setId(null);
        assertThat(emailTrackingDTO1).isNotEqualTo(emailTrackingDTO2);
    }
}
