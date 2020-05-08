package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.NotificationReceiver} entity.
 */
public class NotificationReceiverDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Boolean readStatus;

    @NotNull
    private Instant updatedAt;


    private Long notificationId;

    private Long userProfileId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificationReceiverDTO notificationReceiverDTO = (NotificationReceiverDTO) o;
        if (notificationReceiverDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notificationReceiverDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotificationReceiverDTO{" +
            "id=" + getId() +
            ", readStatus='" + isReadStatus() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", notificationId=" + getNotificationId() +
            ", userProfileId=" + getUserProfileId() +
            "}";
    }
}
