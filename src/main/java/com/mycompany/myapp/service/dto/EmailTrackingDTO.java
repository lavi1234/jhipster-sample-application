package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.EmailTracking} entity.
 */
public class EmailTrackingDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 200)
    private String toEmail;

    @Size(max = 500)
    private String subject;

    @Size(max = 255)
    private String message;

    @NotNull
    @Size(max = 200)
    private String type;

    @NotNull
    private Instant createdAt;


    private Long receiverId;

    private Long createdById;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long userProfileId) {
        this.receiverId = userProfileId;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long userProfileId) {
        this.createdById = userProfileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailTrackingDTO emailTrackingDTO = (EmailTrackingDTO) o;
        if (emailTrackingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailTrackingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailTrackingDTO{" +
            "id=" + getId() +
            ", toEmail='" + getToEmail() + "'" +
            ", subject='" + getSubject() + "'" +
            ", message='" + getMessage() + "'" +
            ", type='" + getType() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", receiverId=" + getReceiverId() +
            ", createdById=" + getCreatedById() +
            "}";
    }
}
