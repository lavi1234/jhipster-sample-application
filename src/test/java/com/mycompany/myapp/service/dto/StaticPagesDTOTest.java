package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class StaticPagesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticPagesDTO.class);
        StaticPagesDTO staticPagesDTO1 = new StaticPagesDTO();
        staticPagesDTO1.setId(1L);
        StaticPagesDTO staticPagesDTO2 = new StaticPagesDTO();
        assertThat(staticPagesDTO1).isNotEqualTo(staticPagesDTO2);
        staticPagesDTO2.setId(staticPagesDTO1.getId());
        assertThat(staticPagesDTO1).isEqualTo(staticPagesDTO2);
        staticPagesDTO2.setId(2L);
        assertThat(staticPagesDTO1).isNotEqualTo(staticPagesDTO2);
        staticPagesDTO1.setId(null);
        assertThat(staticPagesDTO1).isNotEqualTo(staticPagesDTO2);
    }
}
