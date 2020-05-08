package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EnquiryNoteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnquiryNoteDTO.class);
        EnquiryNoteDTO enquiryNoteDTO1 = new EnquiryNoteDTO();
        enquiryNoteDTO1.setId(1L);
        EnquiryNoteDTO enquiryNoteDTO2 = new EnquiryNoteDTO();
        assertThat(enquiryNoteDTO1).isNotEqualTo(enquiryNoteDTO2);
        enquiryNoteDTO2.setId(enquiryNoteDTO1.getId());
        assertThat(enquiryNoteDTO1).isEqualTo(enquiryNoteDTO2);
        enquiryNoteDTO2.setId(2L);
        assertThat(enquiryNoteDTO1).isNotEqualTo(enquiryNoteDTO2);
        enquiryNoteDTO1.setId(null);
        assertThat(enquiryNoteDTO1).isNotEqualTo(enquiryNoteDTO2);
    }
}
