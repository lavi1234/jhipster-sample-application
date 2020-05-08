package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {LocalizationMapper.class})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "name.id", target = "nameId")
    @Mapping(source = "description.id", target = "descriptionId")
    @Mapping(source = "parent.id", target = "parentId")
    CategoryDTO toDto(Category category);

    @Mapping(source = "nameId", target = "name")
    @Mapping(source = "descriptionId", target = "description")
    @Mapping(source = "parentId", target = "parent")
    @Mapping(target = "userCategoryMappings", ignore = true)
    @Mapping(target = "removeUserCategoryMapping", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
