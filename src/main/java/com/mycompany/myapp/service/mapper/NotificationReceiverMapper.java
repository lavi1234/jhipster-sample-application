package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.NotificationReceiverDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NotificationReceiver} and its DTO {@link NotificationReceiverDTO}.
 */
@Mapper(componentModel = "spring", uses = {NotificationMapper.class, UserProfileMapper.class})
public interface NotificationReceiverMapper extends EntityMapper<NotificationReceiverDTO, NotificationReceiver> {

    @Mapping(source = "notification.id", target = "notificationId")
    @Mapping(source = "userProfile.id", target = "userProfileId")
    NotificationReceiverDTO toDto(NotificationReceiver notificationReceiver);

    @Mapping(source = "notificationId", target = "notification")
    @Mapping(source = "userProfileId", target = "userProfile")
    NotificationReceiver toEntity(NotificationReceiverDTO notificationReceiverDTO);

    default NotificationReceiver fromId(Long id) {
        if (id == null) {
            return null;
        }
        NotificationReceiver notificationReceiver = new NotificationReceiver();
        notificationReceiver.setId(id);
        return notificationReceiver;
    }
}
