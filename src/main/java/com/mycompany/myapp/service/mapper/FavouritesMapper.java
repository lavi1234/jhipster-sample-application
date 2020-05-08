package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.FavouritesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Favourites} and its DTO {@link FavouritesDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class})
public interface FavouritesMapper extends EntityMapper<FavouritesDTO, Favourites> {

    @Mapping(source = "fromProfile.id", target = "fromProfileId")
    @Mapping(source = "toProfile.id", target = "toProfileId")
    FavouritesDTO toDto(Favourites favourites);

    @Mapping(source = "fromProfileId", target = "fromProfile")
    @Mapping(source = "toProfileId", target = "toProfile")
    Favourites toEntity(FavouritesDTO favouritesDTO);

    default Favourites fromId(Long id) {
        if (id == null) {
            return null;
        }
        Favourites favourites = new Favourites();
        favourites.setId(id);
        return favourites;
    }
}
