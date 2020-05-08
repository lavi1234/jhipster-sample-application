package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CommissionReferenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommissionReference.class);
        CommissionReference commissionReference1 = new CommissionReference();
        commissionReference1.setId(1L);
        CommissionReference commissionReference2 = new CommissionReference();
        commissionReference2.setId(commissionReference1.getId());
        assertThat(commissionReference1).isEqualTo(commissionReference2);
        commissionReference2.setId(2L);
        assertThat(commissionReference1).isNotEqualTo(commissionReference2);
        commissionReference1.setId(null);
        assertThat(commissionReference1).isNotEqualTo(commissionReference2);
    }
}
