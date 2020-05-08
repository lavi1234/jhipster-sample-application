package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class SubsriptionPlanFeatureTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubsriptionPlanFeature.class);
        SubsriptionPlanFeature subsriptionPlanFeature1 = new SubsriptionPlanFeature();
        subsriptionPlanFeature1.setId(1L);
        SubsriptionPlanFeature subsriptionPlanFeature2 = new SubsriptionPlanFeature();
        subsriptionPlanFeature2.setId(subsriptionPlanFeature1.getId());
        assertThat(subsriptionPlanFeature1).isEqualTo(subsriptionPlanFeature2);
        subsriptionPlanFeature2.setId(2L);
        assertThat(subsriptionPlanFeature1).isNotEqualTo(subsriptionPlanFeature2);
        subsriptionPlanFeature1.setId(null);
        assertThat(subsriptionPlanFeature1).isNotEqualTo(subsriptionPlanFeature2);
    }
}
