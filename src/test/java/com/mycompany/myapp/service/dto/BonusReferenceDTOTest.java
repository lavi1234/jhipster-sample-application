package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BonusReferenceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonusReferenceDTO.class);
        BonusReferenceDTO bonusReferenceDTO1 = new BonusReferenceDTO();
        bonusReferenceDTO1.setId(1L);
        BonusReferenceDTO bonusReferenceDTO2 = new BonusReferenceDTO();
        assertThat(bonusReferenceDTO1).isNotEqualTo(bonusReferenceDTO2);
        bonusReferenceDTO2.setId(bonusReferenceDTO1.getId());
        assertThat(bonusReferenceDTO1).isEqualTo(bonusReferenceDTO2);
        bonusReferenceDTO2.setId(2L);
        assertThat(bonusReferenceDTO1).isNotEqualTo(bonusReferenceDTO2);
        bonusReferenceDTO1.setId(null);
        assertThat(bonusReferenceDTO1).isNotEqualTo(bonusReferenceDTO2);
    }
}
