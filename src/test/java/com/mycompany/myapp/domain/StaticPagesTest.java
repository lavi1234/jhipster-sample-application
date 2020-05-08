package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class StaticPagesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StaticPages.class);
        StaticPages staticPages1 = new StaticPages();
        staticPages1.setId(1L);
        StaticPages staticPages2 = new StaticPages();
        staticPages2.setId(staticPages1.getId());
        assertThat(staticPages1).isEqualTo(staticPages2);
        staticPages2.setId(2L);
        assertThat(staticPages1).isNotEqualTo(staticPages2);
        staticPages1.setId(null);
        assertThat(staticPages1).isNotEqualTo(staticPages2);
    }
}
