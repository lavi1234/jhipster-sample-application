package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EnquiryNoteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnquiryNote.class);
        EnquiryNote enquiryNote1 = new EnquiryNote();
        enquiryNote1.setId(1L);
        EnquiryNote enquiryNote2 = new EnquiryNote();
        enquiryNote2.setId(enquiryNote1.getId());
        assertThat(enquiryNote1).isEqualTo(enquiryNote2);
        enquiryNote2.setId(2L);
        assertThat(enquiryNote1).isNotEqualTo(enquiryNote2);
        enquiryNote1.setId(null);
        assertThat(enquiryNote1).isNotEqualTo(enquiryNote2);
    }
}
