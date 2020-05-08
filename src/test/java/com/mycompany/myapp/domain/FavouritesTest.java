package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class FavouritesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Favourites.class);
        Favourites favourites1 = new Favourites();
        favourites1.setId(1L);
        Favourites favourites2 = new Favourites();
        favourites2.setId(favourites1.getId());
        assertThat(favourites1).isEqualTo(favourites2);
        favourites2.setId(2L);
        assertThat(favourites1).isNotEqualTo(favourites2);
        favourites1.setId(null);
        assertThat(favourites1).isNotEqualTo(favourites2);
    }
}
