package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EmailTrackingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailTracking.class);
        EmailTracking emailTracking1 = new EmailTracking();
        emailTracking1.setId(1L);
        EmailTracking emailTracking2 = new EmailTracking();
        emailTracking2.setId(emailTracking1.getId());
        assertThat(emailTracking1).isEqualTo(emailTracking2);
        emailTracking2.setId(2L);
        assertThat(emailTracking1).isNotEqualTo(emailTracking2);
        emailTracking1.setId(null);
        assertThat(emailTracking1).isNotEqualTo(emailTracking2);
    }
}
