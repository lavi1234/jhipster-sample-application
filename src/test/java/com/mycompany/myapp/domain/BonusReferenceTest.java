package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BonusReferenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BonusReference.class);
        BonusReference bonusReference1 = new BonusReference();
        bonusReference1.setId(1L);
        BonusReference bonusReference2 = new BonusReference();
        bonusReference2.setId(bonusReference1.getId());
        assertThat(bonusReference1).isEqualTo(bonusReference2);
        bonusReference2.setId(2L);
        assertThat(bonusReference1).isNotEqualTo(bonusReference2);
        bonusReference1.setId(null);
        assertThat(bonusReference1).isNotEqualTo(bonusReference2);
    }
}
