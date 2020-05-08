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
 * Criteria class for the {@link com.mycompany.myapp.domain.EmailTracking} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EmailTrackingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /email-trackings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmailTrackingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter toEmail;

    private StringFilter subject;

    private StringFilter message;

    private StringFilter type;

    private InstantFilter createdAt;

    private LongFilter receiverId;

    private LongFilter createdById;

    public EmailTrackingCriteria() {
    }

    public EmailTrackingCriteria(EmailTrackingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.toEmail = other.toEmail == null ? null : other.toEmail.copy();
        this.subject = other.subject == null ? null : other.subject.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.receiverId = other.receiverId == null ? null : other.receiverId.copy();
        this.createdById = other.createdById == null ? null : other.createdById.copy();
    }

    @Override
    public EmailTrackingCriteria copy() {
        return new EmailTrackingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getToEmail() {
        return toEmail;
    }

    public void setToEmail(StringFilter toEmail) {
        this.toEmail = toEmail;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public StringFilter getMessage() {
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public LongFilter getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(LongFilter receiverId) {
        this.receiverId = receiverId;
    }

    public LongFilter getCreatedById() {
        return createdById;
    }

    public void setCreatedById(LongFilter createdById) {
        this.createdById = createdById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmailTrackingCriteria that = (EmailTrackingCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(toEmail, that.toEmail) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(message, that.message) &&
            Objects.equals(type, that.type) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(receiverId, that.receiverId) &&
            Objects.equals(createdById, that.createdById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        toEmail,
        subject,
        message,
        type,
        createdAt,
        receiverId,
        createdById
        );
    }

    @Override
    public String toString() {
        return "EmailTrackingCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (toEmail != null ? "toEmail=" + toEmail + ", " : "") +
                (subject != null ? "subject=" + subject + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (receiverId != null ? "receiverId=" + receiverId + ", " : "") +
                (createdById != null ? "createdById=" + createdById + ", " : "") +
            "}";
    }

}
