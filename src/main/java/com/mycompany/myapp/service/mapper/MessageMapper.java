package com.mycompany.myapp.service.mapper;


import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.MessageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserProfileMapper.class, EnquiryMapper.class, OrderMapper.class, OfferMapper.class})
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {

    @Mapping(source = "from.id", target = "fromId")
    @Mapping(source = "to.id", target = "toId")
    @Mapping(source = "createdBy.id", target = "createdById")
    @Mapping(source = "enquiry.id", target = "enquiryId")
    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "offer.id", target = "offerId")
    @Mapping(source = "replyMessage.id", target = "replyMessageId")
    MessageDTO toDto(Message message);

    @Mapping(source = "fromId", target = "from")
    @Mapping(source = "toId", target = "to")
    @Mapping(source = "createdById", target = "createdBy")
    @Mapping(source = "enquiryId", target = "enquiry")
    @Mapping(source = "orderId", target = "order")
    @Mapping(source = "offerId", target = "offer")
    @Mapping(source = "replyMessageId", target = "replyMessage")
    Message toEntity(MessageDTO messageDTO);

    default Message fromId(Long id) {
        if (id == null) {
            return null;
        }
        Message message = new Message();
        message.setId(id);
        return message;
    }
}
