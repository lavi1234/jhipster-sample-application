package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EnquiryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enquiry.class);
        Enquiry enquiry1 = new Enquiry();
        enquiry1.setId(1L);
        Enquiry enquiry2 = new Enquiry();
        enquiry2.setId(enquiry1.getId());
        assertThat(enquiry1).isEqualTo(enquiry2);
        enquiry2.setId(2L);
        assertThat(enquiry1).isNotEqualTo(enquiry2);
        enquiry1.setId(null);
        assertThat(enquiry1).isNotEqualTo(enquiry2);
    }
}
