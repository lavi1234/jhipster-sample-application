package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EnquiryDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnquiryDetails.class);
        EnquiryDetails enquiryDetails1 = new EnquiryDetails();
        enquiryDetails1.setId(1L);
        EnquiryDetails enquiryDetails2 = new EnquiryDetails();
        enquiryDetails2.setId(enquiryDetails1.getId());
        assertThat(enquiryDetails1).isEqualTo(enquiryDetails2);
        enquiryDetails2.setId(2L);
        assertThat(enquiryDetails1).isNotEqualTo(enquiryDetails2);
        enquiryDetails1.setId(null);
        assertThat(enquiryDetails1).isNotEqualTo(enquiryDetails2);
    }
}
