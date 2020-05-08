package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SupplierEnquiryMappingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierEnquiryMappingDTO.class);
        SupplierEnquiryMappingDTO supplierEnquiryMappingDTO1 = new SupplierEnquiryMappingDTO();
        supplierEnquiryMappingDTO1.setId(1L);
        SupplierEnquiryMappingDTO supplierEnquiryMappingDTO2 = new SupplierEnquiryMappingDTO();
        assertThat(supplierEnquiryMappingDTO1).isNotEqualTo(supplierEnquiryMappingDTO2);
        supplierEnquiryMappingDTO2.setId(supplierEnquiryMappingDTO1.getId());
        assertThat(supplierEnquiryMappingDTO1).isEqualTo(supplierEnquiryMappingDTO2);
        supplierEnquiryMappingDTO2.setId(2L);
        assertThat(supplierEnquiryMappingDTO1).isNotEqualTo(supplierEnquiryMappingDTO2);
        supplierEnquiryMappingDTO1.setId(null);
        assertThat(supplierEnquiryMappingDTO1).isNotEqualTo(supplierEnquiryMappingDTO2);
    }
}
