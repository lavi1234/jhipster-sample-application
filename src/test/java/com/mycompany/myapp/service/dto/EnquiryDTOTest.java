package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EnquiryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnquiryDTO.class);
        EnquiryDTO enquiryDTO1 = new EnquiryDTO();
        enquiryDTO1.setId(1L);
        EnquiryDTO enquiryDTO2 = new EnquiryDTO();
        assertThat(enquiryDTO1).isNotEqualTo(enquiryDTO2);
        enquiryDTO2.setId(enquiryDTO1.getId());
        assertThat(enquiryDTO1).isEqualTo(enquiryDTO2);
        enquiryDTO2.setId(2L);
        assertThat(enquiryDTO1).isNotEqualTo(enquiryDTO2);
        enquiryDTO1.setId(null);
        assertThat(enquiryDTO1).isNotEqualTo(enquiryDTO2);
    }
}
