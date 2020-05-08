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
 * Criteria class for the {@link com.mycompany.myapp.domain.MessageDeleteDetails} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.MessageDeleteDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /message-delete-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MessageDeleteDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter deletedAt;

    private LongFilter messageId;

    private LongFilter deletedById;

    public MessageDeleteDetailsCriteria() {
    }

    public MessageDeleteDetailsCriteria(MessageDeleteDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.deletedAt = other.deletedAt == null ? null : other.deletedAt.copy();
        this.messageId = other.messageId == null ? null : other.messageId.copy();
        this.deletedById = other.deletedById == null ? null : other.deletedById.copy();
    }

    @Override
    public MessageDeleteDetailsCriteria copy() {
        return new MessageDeleteDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(InstantFilter deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LongFilter getMessageId() {
        return messageId;
    }

    public void setMessageId(LongFilter messageId) {
        this.messageId = messageId;
    }

    public LongFilter getDeletedById() {
        return deletedById;
    }

    public void setDeletedById(LongFilter deletedById) {
        this.deletedById = deletedById;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MessageDeleteDetailsCriteria that = (MessageDeleteDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(deletedAt, that.deletedAt) &&
            Objects.equals(messageId, that.messageId) &&
            Objects.equals(deletedById, that.deletedById);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        deletedAt,
        messageId,
        deletedById
        );
    }

    @Override
    public String toString() {
        return "MessageDeleteDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (deletedAt != null ? "deletedAt=" + deletedAt + ", " : "") +
                (messageId != null ? "messageId=" + messageId + ", " : "") +
                (deletedById != null ? "deletedById=" + deletedById + ", " : "") +
            "}";
    }

}
