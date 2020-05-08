package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class AppFeaturesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppFeatures.class);
        AppFeatures appFeatures1 = new AppFeatures();
        appFeatures1.setId(1L);
        AppFeatures appFeatures2 = new AppFeatures();
        appFeatures2.setId(appFeatures1.getId());
        assertThat(appFeatures1).isEqualTo(appFeatures2);
        appFeatures2.setId(2L);
        assertThat(appFeatures1).isNotEqualTo(appFeatures2);
        appFeatures1.setId(null);
        assertThat(appFeatures1).isNotEqualTo(appFeatures2);
    }
}
