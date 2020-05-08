package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MessageDeleteDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MessageDeleteDetails} and its DTO {@link MessageDeleteDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {MessageMapper.class, UserProfileMapper.class})
public interface MessageDeleteDetailsMapper extends EntityMapper<MessageDeleteDetailsDTO, MessageDeleteDetails> {

    @Mapping(source = "message.id", target = "messageId")
    @Mapping(source = "deletedBy.id", target = "deletedById")
    MessageDeleteDetailsDTO toDto(MessageDeleteDetails messageDeleteDetails);

    @Mapping(source = "messageId", target = "message")
    @Mapping(source = "deletedById", target = "deletedBy")
    MessageDeleteDetails toEntity(MessageDeleteDetailsDTO messageDeleteDetailsDTO);

    default MessageDeleteDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        MessageDeleteDetails messageDeleteDetails = new MessageDeleteDetails();
        messageDeleteDetails.setId(id);
        return messageDeleteDetails;
    }
}
