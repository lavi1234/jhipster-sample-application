package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.StaticPagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StaticPages} and its DTO {@link StaticPagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StaticPagesMapper extends EntityMapper<StaticPagesDTO, StaticPages> {



    default StaticPages fromId(Long id) {
        if (id == null) {
            return null;
        }
        StaticPages staticPages = new StaticPages();
        staticPages.setId(id);
        return staticPages;
    }
}
