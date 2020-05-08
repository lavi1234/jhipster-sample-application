package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class EnquiryMaterialDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnquiryMaterialDTO.class);
        EnquiryMaterialDTO enquiryMaterialDTO1 = new EnquiryMaterialDTO();
        enquiryMaterialDTO1.setId(1L);
        EnquiryMaterialDTO enquiryMaterialDTO2 = new EnquiryMaterialDTO();
        assertThat(enquiryMaterialDTO1).isNotEqualTo(enquiryMaterialDTO2);
        enquiryMaterialDTO2.setId(enquiryMaterialDTO1.getId());
        assertThat(enquiryMaterialDTO1).isEqualTo(enquiryMaterialDTO2);
        enquiryMaterialDTO2.setId(2L);
        assertThat(enquiryMaterialDTO1).isNotEqualTo(enquiryMaterialDTO2);
        enquiryMaterialDTO1.setId(null);
        assertThat(enquiryMaterialDTO1).isNotEqualTo(enquiryMaterialDTO2);
    }
}
