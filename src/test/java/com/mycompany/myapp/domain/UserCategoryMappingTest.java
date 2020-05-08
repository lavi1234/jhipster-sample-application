package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class UserCategoryMappingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCategoryMapping.class);
        UserCategoryMapping userCategoryMapping1 = new UserCategoryMapping();
        userCategoryMapping1.setId(1L);
        UserCategoryMapping userCategoryMapping2 = new UserCategoryMapping();
        userCategoryMapping2.setId(userCategoryMapping1.getId());
        assertThat(userCategoryMapping1).isEqualTo(userCategoryMapping2);
        userCategoryMapping2.setId(2L);
        assertThat(userCategoryMapping1).isNotEqualTo(userCategoryMapping2);
        userCategoryMapping1.setId(null);
        assertThat(userCategoryMapping1).isNotEqualTo(userCategoryMapping2);
    }
}
