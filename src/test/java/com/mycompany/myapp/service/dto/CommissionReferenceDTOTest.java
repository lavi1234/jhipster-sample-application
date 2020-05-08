package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CommissionReferenceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommissionReferenceDTO.class);
        CommissionReferenceDTO commissionReferenceDTO1 = new CommissionReferenceDTO();
        commissionReferenceDTO1.setId(1L);
        CommissionReferenceDTO commissionReferenceDTO2 = new CommissionReferenceDTO();
        assertThat(commissionReferenceDTO1).isNotEqualTo(commissionReferenceDTO2);
        commissionReferenceDTO2.setId(commissionReferenceDTO1.getId());
        assertThat(commissionReferenceDTO1).isEqualTo(commissionReferenceDTO2);
        commissionReferenceDTO2.setId(2L);
        assertThat(commissionReferenceDTO1).isNotEqualTo(commissionReferenceDTO2);
        commissionReferenceDTO1.setId(null);
        assertThat(commissionReferenceDTO1).isNotEqualTo(commissionReferenceDTO2);
    }
}
