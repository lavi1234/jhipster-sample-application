package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Favourites} entity.
 */
public class FavouritesDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Instant createdAt;

    @Size(max = 255)
    private String remarks;


    private Long fromProfileId;

    private Long toProfileId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FavouritesDTO favouritesDTO = (FavouritesDTO) o;
        if (favouritesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), favouritesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FavouritesDTO{" +
            "id=" + getId() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", remarks='" + getRemarks() + "'" +
            ", fromProfileId=" + getFromProfileId() +
            ", toProfileId=" + getToProfileId() +
            "}";
    }
}
