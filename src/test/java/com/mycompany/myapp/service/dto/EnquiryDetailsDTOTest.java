package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EnquiryDetailsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnquiryDetailsDTO.class);
        EnquiryDetailsDTO enquiryDetailsDTO1 = new EnquiryDetailsDTO();
        enquiryDetailsDTO1.setId(1L);
        EnquiryDetailsDTO enquiryDetailsDTO2 = new EnquiryDetailsDTO();
        assertThat(enquiryDetailsDTO1).isNotEqualTo(enquiryDetailsDTO2);
        enquiryDetailsDTO2.setId(enquiryDetailsDTO1.getId());
        assertThat(enquiryDetailsDTO1).isEqualTo(enquiryDetailsDTO2);
        enquiryDetailsDTO2.setId(2L);
        assertThat(enquiryDetailsDTO1).isNotEqualTo(enquiryDetailsDTO2);
        enquiryDetailsDTO1.setId(null);
        assertThat(enquiryDetailsDTO1).isNotEqualTo(enquiryDetailsDTO2);
    }
}
