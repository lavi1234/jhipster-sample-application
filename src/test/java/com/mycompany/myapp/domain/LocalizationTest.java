package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LocalizationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Localization.class);
        Localization localization1 = new Localization();
        localization1.setId(1L);
        Localization localization2 = new Localization();
        localization2.setId(localization1.getId());
        assertThat(localization1).isEqualTo(localization2);
        localization2.setId(2L);
        assertThat(localization1).isNotEqualTo(localization2);
        localization1.setId(null);
        assertThat(localization1).isNotEqualTo(localization2);
    }
}
