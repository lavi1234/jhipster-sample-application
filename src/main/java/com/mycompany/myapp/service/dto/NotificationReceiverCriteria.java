package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.NotificationReceiver} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.NotificationReceiverResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notification-receivers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NotificationReceiverCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter readStatus;

    private InstantFilter updatedAt;

    private LongFilter notificationId;

    private LongFilter userProfileId;

    public NotificationReceiverCriteria() {
    }

    public NotificationReceiverCriteria(NotificationReceiverCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.readStatus = other.readStatus == null ? null : other.readStatus.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.notificationId = other.notificationId == null ? null : other.notificationId.copy();
        this.userProfileId = other.userProfileId == null ? null : other.userProfileId.copy();
    }

    @Override
    public NotificationReceiverCriteria copy() {
        return new NotificationReceiverCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(BooleanFilter readStatus) {
        this.readStatus = readStatus;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LongFilter getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(LongFilter notificationId) {
        this.notificationId = notificationId;
    }

    public LongFilter getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(LongFilter userProfileId) {
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
        final NotificationReceiverCriteria that = (NotificationReceiverCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(readStatus, that.readStatus) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(notificationId, that.notificationId) &&
            Objects.equals(userProfileId, that.userProfileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        readStatus,
        updatedAt,
        notificationId,
        userProfileId
        );
    }

    @Override
    public String toString() {
        return "NotificationReceiverCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (readStatus != null ? "readStatus=" + readStatus + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (notificationId != null ? "notificationId=" + notificationId + ", " : "") +
                (userProfileId != null ? "userProfileId=" + userProfileId + ", " : "") +
            "}";
    }

}
