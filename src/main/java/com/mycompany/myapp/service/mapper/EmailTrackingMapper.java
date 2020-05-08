package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EmailTrackingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmailTracking} and its DTO {@link EmailTrackingDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class})
public interface EmailTrackingMapper extends EntityMapper<EmailTrackingDTO, EmailTracking> {

    @Mapping(source = "receiver.id", target = "receiverId")
    @Mapping(source = "createdBy.id", target = "createdById")
    EmailTrackingDTO toDto(EmailTracking emailTracking);

    @Mapping(source = "receiverId", target = "receiver")
    @Mapping(source = "createdById", target = "createdBy")
    EmailTracking toEntity(EmailTrackingDTO emailTrackingDTO);

    default EmailTracking fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmailTracking emailTracking = new EmailTracking();
        emailTracking.setId(id);
        return emailTracking;
    }
}
