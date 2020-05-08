package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EnquiryMaterialTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnquiryMaterial.class);
        EnquiryMaterial enquiryMaterial1 = new EnquiryMaterial();
        enquiryMaterial1.setId(1L);
        EnquiryMaterial enquiryMaterial2 = new EnquiryMaterial();
        enquiryMaterial2.setId(enquiryMaterial1.getId());
        assertThat(enquiryMaterial1).isEqualTo(enquiryMaterial2);
        enquiryMaterial2.setId(2L);
        assertThat(enquiryMaterial1).isNotEqualTo(enquiryMaterial2);
        enquiryMaterial1.setId(null);
        assertThat(enquiryMaterial1).isNotEqualTo(enquiryMaterial2);
    }
}
