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
 * A Favourites.
 */
@Entity
@Table(name = "favourites")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Favourites implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Size(max = 255)
    @Column(name = "remarks", length = 255)
    private String remarks;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("favourites")
    private UserProfile fromProfile;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("favourites")
    private UserProfile toProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Favourites createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public Favourites remarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public UserProfile getFromProfile() {
        return fromProfile;
    }

    public Favourites fromProfile(UserProfile userProfile) {
        this.fromProfile = userProfile;
        return this;
    }

    public void setFromProfile(UserProfile userProfile) {
        this.fromProfile = userProfile;
    }

    public UserProfile getToProfile() {
        return toProfile;
    }

    public Favourites toProfile(UserProfile userProfile) {
        this.toProfile = userProfile;
        return this;
    }

    public void setToProfile(UserProfile userProfile) {
        this.toProfile = userProfile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Favourites)) {
            return false;
        }
        return id != null && id.equals(((Favourites) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Favourites{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", remarks='" + getRemarks() + "'" +
            "}";
    }
}
