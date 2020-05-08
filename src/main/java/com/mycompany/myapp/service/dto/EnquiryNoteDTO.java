package com.mycompany.myapp.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.EnquiryNote} entity.
 */
public class EnquiryNoteDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 255)
    private String note;

    @NotNull
    private Instant createdAt;


    private Long enquiryDetailsId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getEnquiryDetailsId() {
        return enquiryDetailsId;
    }

    public void setEnquiryDetailsId(Long enquiryDetailsId) {
        this.enquiryDetailsId = enquiryDetailsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnquiryNoteDTO enquiryNoteDTO = (EnquiryNoteDTO) o;
        if (enquiryNoteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enquiryNoteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnquiryNoteDTO{" +
            "id=" + getId() +
            ", note='" + getNote() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", enquiryDetailsId=" + getEnquiryDetailsId() +
            "}";
    }
}
