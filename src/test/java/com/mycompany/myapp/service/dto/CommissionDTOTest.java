package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CommissionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommissionDTO.class);
        CommissionDTO commissionDTO1 = new CommissionDTO();
        commissionDTO1.setId(1L);
        CommissionDTO commissionDTO2 = new CommissionDTO();
        assertThat(commissionDTO1).isNotEqualTo(commissionDTO2);
        commissionDTO2.setId(commissionDTO1.getId());
        assertThat(commissionDTO1).isEqualTo(commissionDTO2);
        commissionDTO2.setId(2L);
        assertThat(commissionDTO1).isNotEqualTo(commissionDTO2);
        commissionDTO1.setId(null);
        assertThat(commissionDTO1).isNotEqualTo(commissionDTO2);
    }
}
