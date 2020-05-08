package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Rating} entity.
 */
public class RatingDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer rating;

    @NotNull
    private Instant createdAt;

    @Size(max = 255)
    private String remarks;


    private Long fromProfileId;

    private Long toProfileId;

    private Long orderId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getFromProfileId() {
        return fromProfileId;
    }

    public void setFromProfileId(Long userProfileId) {
        this.fromProfileId = userProfileId;
    }

    public Long getToProfileId() {
        return toProfileId;
    }

    public void setToProfileId(Long userProfileId) {
        this.toProfileId = userProfileId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RatingDTO ratingDTO = (RatingDTO) o;
        if (ratingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ratingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RatingDTO{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", fromProfileId=" + getFromProfileId() +
            ", toProfileId=" + getToProfileId() +
            ", orderId=" + getOrderId() +
            "}";
    }
}
