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
 * A MessageDeleteDetails.
 */
@Entity
@Table(name = "message_delete_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MessageDeleteDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "deleted_at", nullable = false)
    private Instant deletedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("messageDeleteDetails")
    private Message message;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("messageDeleteDetails")
    private UserProfile deletedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public MessageDeleteDetails deletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Message getMessage() {
        return message;
    }

    public MessageDeleteDetails message(Message message) {
        this.message = message;
        return this;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public UserProfile getDeletedBy() {
        return deletedBy;
    }

    public MessageDeleteDetails deletedBy(UserProfile userProfile) {
        this.deletedBy = userProfile;
        return this;
    }

    public void setDeletedBy(UserProfile userProfile) {
        this.deletedBy = userProfile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageDeleteDetails)) {
            return false;
        }
        return id != null && id.equals(((MessageDeleteDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MessageDeleteDetails{" +
            "id=" + getId() +
            ", deletedAt='" + getDeletedAt() + "'" +
            "}";
    }
}
