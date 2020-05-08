package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class UserCategoryMappingDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCategoryMappingDTO.class);
        UserCategoryMappingDTO userCategoryMappingDTO1 = new UserCategoryMappingDTO();
        userCategoryMappingDTO1.setId(1L);
        UserCategoryMappingDTO userCategoryMappingDTO2 = new UserCategoryMappingDTO();
        assertThat(userCategoryMappingDTO1).isNotEqualTo(userCategoryMappingDTO2);
        userCategoryMappingDTO2.setId(userCategoryMappingDTO1.getId());
        assertThat(userCategoryMappingDTO1).isEqualTo(userCategoryMappingDTO2);
        userCategoryMappingDTO2.setId(2L);
        assertThat(userCategoryMappingDTO1).isNotEqualTo(userCategoryMappingDTO2);
        userCategoryMappingDTO1.setId(null);
        assertThat(userCategoryMappingDTO1).isNotEqualTo(userCategoryMappingDTO2);
    }
}
