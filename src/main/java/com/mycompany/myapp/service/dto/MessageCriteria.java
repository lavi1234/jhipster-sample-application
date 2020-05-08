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
 * Criteria class for the {@link com.mycompany.myapp.domain.Message} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.MessageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /messages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MessageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter subject;

    private StringFilter message;

    private InstantFilter createdAt;

    private StringFilter apiResponse;

    private StringFilter discussionType;

    private BooleanFilter readStatus;

    private LongFilter fromId;

    private LongFilter toId;

    private LongFilter createdById;

    private LongFilter enquiryId;

    private LongFilter orderId;

    private LongFilter offerId;

    private LongFilter replyMessageId;

    public MessageCriteria() {
    }

    public MessageCriteria(MessageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.subject = other.subject == null ? null : other.subject.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.apiResponse = other.apiResponse == null ? null : other.apiResponse.copy();
        this.discussionType = other.discussionType == null ? null : other.discussionType.copy();
        this.readStatus = other.readStatus == null ? null : other.readStatus.copy();
        this.fromId = other.fromId == null ? null : other.fromId.copy();
        this.toId = other.toId == null ? null : other.toId.copy();
        this.createdById = other.createdById == null ? null : other.createdById.copy();
        this.enquiryId = other.enquiryId == null ? null : other.enquiryId.copy();
        this.orderId = other.orderId == null ? null : other.orderId.copy();
        this.offerId = other.offerId == null ? null : other.offerId.copy();
        this.replyMessageId = other.replyMessageId == null ? null : other.replyMessageId.copy();
    }

    @Override
    public MessageCriteria copy() {
        return new MessageCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
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

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public StringFilter getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(StringFilter apiResponse) {
        this.apiResponse = apiResponse;
    }

    public StringFilter getDiscussionType() {
        return discussionType;
    }

    public void setDiscussionType(StringFilter discussionType) {
        this.discussionType = discussionType;
    }

    public BooleanFilter getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(BooleanFilter readStatus) {
        this.readStatus = readStatus;
    }

    public LongFilter getFromId() {
        return fromId;
    }

    public void setFromId(LongFilter fromId) {
        this.fromId = fromId;
    }

    public LongFilter getToId() {
        return toId;
    }

    public void setToId(LongFilter toId) {
        this.toId = toId;
    }

    public LongFilter getCreatedById() {
        return createdById;
    }

    public void setCreatedById(LongFilter createdById) {
        this.createdById = createdById;
    }

    public LongFilter getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(LongFilter enquiryId) {
        this.enquiryId = enquiryId;
    }

    public LongFilter getOrderId() {
        return orderId;
    }

    public void setOrderId(LongFilter orderId) {
        this.orderId = orderId;
    }

    public LongFilter getOfferId() {
        return offerId;
    }

    public void setOfferId(LongFilter offerId) {
        this.offerId = offerId;
    }

    public LongFilter getReplyMessageId() {
        return replyMessageId;
    }

    public void setReplyMessageId(LongFilter replyMessageId) {
        this.replyMessageId = replyMessageId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MessageCriteria that = (MessageCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(subject, that.subject) &&
            Objects.equals(message, that.message) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(apiResponse, that.apiResponse) &&
            Objects.equals(discussionType, that.discussionType) &&
            Objects.equals(readStatus, that.readStatus) &&
            Objects.equals(fromId, that.fromId) &&
            Objects.equals(toId, that.toId) &&
            Objects.equals(createdById, that.createdById) &&
            Objects.equals(enquiryId, that.enquiryId) &&
            Objects.equals(orderId, that.orderId) &&
            Objects.equals(offerId, that.offerId) &&
            Objects.equals(replyMessageId, that.replyMessageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        subject,
        message,
        createdAt,
        apiResponse,
        discussionType,
        readStatus,
        fromId,
        toId,
        createdById,
        enquiryId,
        orderId,
        offerId,
        replyMessageId
        );
    }

    @Override
    public String toString() {
        return "MessageCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (subject != null ? "subject=" + subject + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (apiResponse != null ? "apiResponse=" + apiResponse + ", " : "") +
                (discussionType != null ? "discussionType=" + discussionType + ", " : "") +
                (readStatus != null ? "readStatus=" + readStatus + ", " : "") +
                (fromId != null ? "fromId=" + fromId + ", " : "") +
                (toId != null ? "toId=" + toId + ", " : "") +
                (createdById != null ? "createdById=" + createdById + ", " : "") +
                (enquiryId != null ? "enquiryId=" + enquiryId + ", " : "") +
                (orderId != null ? "orderId=" + orderId + ", " : "") +
                (offerId != null ? "offerId=" + offerId + ", " : "") +
                (replyMessageId != null ? "replyMessageId=" + replyMessageId + ", " : "") +
            "}";
    }

}
