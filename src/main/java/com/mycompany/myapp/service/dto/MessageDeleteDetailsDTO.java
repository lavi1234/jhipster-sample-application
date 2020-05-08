package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.MessageDeleteDetails} entity.
 */
public class MessageDeleteDetailsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant deletedAt;


    private Long messageId;

    private Long deletedById;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getDeletedById() {
        return deletedById;
    }

    public void setDeletedById(Long userProfileId) {
        this.deletedById = userProfileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MessageDeleteDetailsDTO messageDeleteDetailsDTO = (MessageDeleteDetailsDTO) o;
        if (messageDeleteDetailsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), messageDeleteDetailsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MessageDeleteDetailsDTO{" +
            "id=" + getId() +
            ", deletedAt='" + getDeletedAt() + "'" +
            ", messageId=" + getMessageId() +
            ", deletedById=" + getDeletedById() +
            "}";
    }
}
