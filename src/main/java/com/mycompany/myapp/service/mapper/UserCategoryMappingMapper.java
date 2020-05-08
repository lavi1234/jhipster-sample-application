package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.UserCategoryMappingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserCategoryMapping} and its DTO {@link UserCategoryMappingDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, CategoryMapper.class})
public interface UserCategoryMappingMapper extends EntityMapper<UserCategoryMappingDTO, UserCategoryMapping> {


    @Mapping(target = "removeUserProfile", ignore = true)
    @Mapping(target = "removeCategory", ignore = true)

    default UserCategoryMapping fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserCategoryMapping userCategoryMapping = new UserCategoryMapping();
        userCategoryMapping.setId(id);
        return userCategoryMapping;
    }
}
