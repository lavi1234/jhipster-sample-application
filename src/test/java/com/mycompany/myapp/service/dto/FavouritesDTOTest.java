package com.mycompany.myapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class FavouritesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavouritesDTO.class);
        FavouritesDTO favouritesDTO1 = new FavouritesDTO();
        favouritesDTO1.setId(1L);
        FavouritesDTO favouritesDTO2 = new FavouritesDTO();
        assertThat(favouritesDTO1).isNotEqualTo(favouritesDTO2);
        favouritesDTO2.setId(favouritesDTO1.getId());
        assertThat(favouritesDTO1).isEqualTo(favouritesDTO2);
        favouritesDTO2.setId(2L);
        assertThat(favouritesDTO1).isNotEqualTo(favouritesDTO2);
        favouritesDTO1.setId(null);
        assertThat(favouritesDTO1).isNotEqualTo(favouritesDTO2);
    }
}
