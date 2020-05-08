package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SupplierEnquiryMappingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierEnquiryMapping.class);
        SupplierEnquiryMapping supplierEnquiryMapping1 = new SupplierEnquiryMapping();
        supplierEnquiryMapping1.setId(1L);
        SupplierEnquiryMapping supplierEnquiryMapping2 = new SupplierEnquiryMapping();
        supplierEnquiryMapping2.setId(supplierEnquiryMapping1.getId());
        assertThat(supplierEnquiryMapping1).isEqualTo(supplierEnquiryMapping2);
        supplierEnquiryMapping2.setId(2L);
        assertThat(supplierEnquiryMapping1).isNotEqualTo(supplierEnquiryMapping2);
        supplierEnquiryMapping1.setId(null);
        assertThat(supplierEnquiryMapping1).isNotEqualTo(supplierEnquiryMapping2);
    }
}
