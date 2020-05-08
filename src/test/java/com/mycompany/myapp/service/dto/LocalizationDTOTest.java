package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class LocalizationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocalizationDTO.class);
        LocalizationDTO localizationDTO1 = new LocalizationDTO();
        localizationDTO1.setId(1L);
        LocalizationDTO localizationDTO2 = new LocalizationDTO();
        assertThat(localizationDTO1).isNotEqualTo(localizationDTO2);
        localizationDTO2.setId(localizationDTO1.getId());
        assertThat(localizationDTO1).isEqualTo(localizationDTO2);
        localizationDTO2.setId(2L);
        assertThat(localizationDTO1).isNotEqualTo(localizationDTO2);
        localizationDTO1.setId(null);
        assertThat(localizationDTO1).isNotEqualTo(localizationDTO2);
    }
}
