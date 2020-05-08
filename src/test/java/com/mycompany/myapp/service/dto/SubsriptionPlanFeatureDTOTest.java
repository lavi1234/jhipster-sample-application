package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SubsriptionPlanFeatureDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubsriptionPlanFeatureDTO.class);
        SubsriptionPlanFeatureDTO subsriptionPlanFeatureDTO1 = new SubsriptionPlanFeatureDTO();
        subsriptionPlanFeatureDTO1.setId(1L);
        SubsriptionPlanFeatureDTO subsriptionPlanFeatureDTO2 = new SubsriptionPlanFeatureDTO();
        assertThat(subsriptionPlanFeatureDTO1).isNotEqualTo(subsriptionPlanFeatureDTO2);
        subsriptionPlanFeatureDTO2.setId(subsriptionPlanFeatureDTO1.getId());
        assertThat(subsriptionPlanFeatureDTO1).isEqualTo(subsriptionPlanFeatureDTO2);
        subsriptionPlanFeatureDTO2.setId(2L);
        assertThat(subsriptionPlanFeatureDTO1).isNotEqualTo(subsriptionPlanFeatureDTO2);
        subsriptionPlanFeatureDTO1.setId(null);
        assertThat(subsriptionPlanFeatureDTO1).isNotEqualTo(subsriptionPlanFeatureDTO2);
    }
}
