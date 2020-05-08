package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AppFeaturesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppFeaturesDTO.class);
        AppFeaturesDTO appFeaturesDTO1 = new AppFeaturesDTO();
        appFeaturesDTO1.setId(1L);
        AppFeaturesDTO appFeaturesDTO2 = new AppFeaturesDTO();
        assertThat(appFeaturesDTO1).isNotEqualTo(appFeaturesDTO2);
        appFeaturesDTO2.setId(appFeaturesDTO1.getId());
        assertThat(appFeaturesDTO1).isEqualTo(appFeaturesDTO2);
        appFeaturesDTO2.setId(2L);
        assertThat(appFeaturesDTO1).isNotEqualTo(appFeaturesDTO2);
        appFeaturesDTO1.setId(null);
        assertThat(appFeaturesDTO1).isNotEqualTo(appFeaturesDTO2);
    }
}
