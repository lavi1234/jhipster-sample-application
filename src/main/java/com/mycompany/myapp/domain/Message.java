package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;

/**
 * A Message.
 */
@Entity
@Table(name = "message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "subject", length = 200, nullable = false)
    private String subject;

    @Size(max = 255)
    @Column(name = "message", length = 255)
    private String message;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Size(max = 255)
    @Column(name = "api_response", length = 255)
    private String apiResponse;

    @NotNull
    @Size(max = 200)
    @Column(name = "discussion_type", length = 200, nullable = false)
    private String discussionType;

    @Column(name = "read_status")
    private Boolean readStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("messages")
    private UserProfile from;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("messages")
    private UserProfile to;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("messages")
    private UserProfile createdBy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("messages")
    private Enquiry enquiry;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("messages")
    private Order order;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("messages")
    private Offer offer;

    @ManyToOne
    @JsonIgnoreProperties("messages")
    private Message replyMessage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public Message subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public Message message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Message createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getApiResponse() {
        return apiResponse;
    }

    public Message apiResponse(String apiResponse) {
        this.apiResponse = apiResponse;
        return this;
    }

    public void setApiResponse(String apiResponse) {
        this.apiResponse = apiResponse;
    }

    public String getDiscussionType() {
        return discussionType;
    }

    public Message discussionType(String discussionType) {
        this.discussionType = discussionType;
        return this;
    }

    public void setDiscussionType(String discussionType) {
        this.discussionType = discussionType;
    }

    public Boolean isReadStatus() {
        return readStatus;
    }

    public Message readStatus(Boolean readStatus) {
        this.readStatus = readStatus;
        return this;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public UserProfile getFrom() {
        return from;
    }

    public Message from(UserProfile userProfile) {
        this.from = userProfile;
        return this;
    }

    public void setFrom(UserProfile userProfile) {
        this.from = userProfile;
    }

    public UserProfile getTo() {
        return to;
    }

    public Message to(UserProfile userProfile) {
        this.to = userProfile;
        return this;
    }

    public void setTo(UserProfile userProfile) {
        this.to = userProfile;
    }

    public UserProfile getCreatedBy() {
        return createdBy;
    }

    public Message createdBy(UserProfile userProfile) {
        this.createdBy = userProfile;
        return this;
    }

    public void setCreatedBy(UserProfile userProfile) {
        this.createdBy = userProfile;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public Message enquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
        return this;
    }

    public void setEnquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
    }

    public Order getOrder() {
        return order;
    }

    public Message order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Offer getOffer() {
        return offer;
    }

    public Message offer(Offer offer) {
        this.offer = offer;
        return this;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public Message getReplyMessage() {
        return replyMessage;
    }

    public Message replyMessage(Message message) {
        this.replyMessage = message;
        return this;
    }

    public void setReplyMessage(Message message) {
        this.replyMessage = message;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", subject='" + getSubject() + "'" +
            ", message='" + getMessage() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", apiResponse='" + getApiResponse() + "'" +
            ", discussionType='" + getDiscussionType() + "'" +
            ", readStatus='" + isReadStatus() + "'" +
            "}";
    }
}
