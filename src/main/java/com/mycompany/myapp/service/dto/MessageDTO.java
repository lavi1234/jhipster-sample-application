package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Message} entity.
 */
public class MessageDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 200)
    private String subject;

    @Size(max = 255)
    private String message;

    @NotNull
    private Instant createdAt;

    @Size(max = 255)
    private String apiResponse;

    @NotNull
    @Size(max = 200)
    private String discussionType;

    private Boolean readStatus;


    private Long fromId;

    private Long toId;

    private Long createdById;

    private Long enquiryId;

    private Long orderId;

    private Long offerId;

    private Long replyMessageId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(String apiResponse) {
        this.apiResponse = apiResponse;
    }

    public String getDiscussionType() {
        return discussionType;
    }

    public void setDiscussionType(String discussionType) {
        this.discussionType = discussionType;
    }

    public Boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long userProfileId) {
        this.fromId = userProfileId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long userProfileId) {
        this.toId = userProfileId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long userProfileId) {
        this.createdById = userProfileId;
    }

    public Long getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Long enquiryId) {
        this.enquiryId = enquiryId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Long getReplyMessageId() {
        return replyMessageId;
    }

    public void setReplyMessageId(Long messageId) {
        this.replyMessageId = messageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;
        if (messageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageDTO{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", message='" + getMessage() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", apiResponse='" + getApiResponse() + "'" +
            ", discussionType='" + getDiscussionType() + "'" +
            ", readStatus='" + isReadStatus() + "'" +
            ", fromId=" + getFromId() +
            ", toId=" + getToId() +
            ", createdById=" + getCreatedById() +
            ", enquiryId=" + getEnquiryId() +
            ", orderId=" + getOrderId() +
            ", offerId=" + getOfferId() +
            ", replyMessageId=" + getReplyMessageId() +
            "}";
    }
}
