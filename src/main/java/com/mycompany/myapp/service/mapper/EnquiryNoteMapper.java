package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EnquiryNoteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnquiryNote} and its DTO {@link EnquiryNoteDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnquiryDetailsMapper.class})
public interface EnquiryNoteMapper extends EntityMapper<EnquiryNoteDTO, EnquiryNote> {

    @Mapping(source = "enquiryDetails.id", target = "enquiryDetailsId")
    EnquiryNoteDTO toDto(EnquiryNote enquiryNote);

    @Mapping(source = "enquiryDetailsId", target = "enquiryDetails")
    EnquiryNote toEntity(EnquiryNoteDTO enquiryNoteDTO);

    default EnquiryNote fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnquiryNote enquiryNote = new EnquiryNote();
        enquiryNote.setId(id);
        return enquiryNote;
    }
}
