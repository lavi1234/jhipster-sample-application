package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.RatingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rating} and its DTO {@link RatingDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, OrderMapper.class})
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {

    @Mapping(source = "fromProfile.id", target = "fromProfileId")
    @Mapping(source = "toProfile.id", target = "toProfileId")
    @Mapping(source = "order.id", target = "orderId")
    RatingDTO toDto(Rating rating);

    @Mapping(source = "fromProfileId", target = "fromProfile")
    @Mapping(source = "toProfileId", target = "toProfile")
    @Mapping(source = "orderId", target = "order")
    Rating toEntity(RatingDTO ratingDTO);

    default Rating fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rating rating = new Rating();
        rating.setId(id);
        return rating;
    }
}
